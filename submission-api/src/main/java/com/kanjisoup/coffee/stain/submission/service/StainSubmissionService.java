package com.kanjisoup.coffee.stain.submission.service;

import com.kanjisoup.coffee.stain.submission.client.StainQueueClient;
import com.kanjisoup.coffee.stain.submission.config.StainSubmissionServiceConfig;
import com.kanjisoup.coffee.stain.submission.domain.SubmissionResponse;
import com.kanjisoup.coffee.stain.submission.exception.SubmissionFailedException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

@Component
public class StainSubmissionService {

  private static final String IMAGE_SUFFIX = "png";
  private StainQueueClient stainQueueClient;
  private StainSubmissionServiceConfig config;

  private Path rootDir;
  private Path submittedDir;
  private Path completedDir;

  @Autowired
  public StainSubmissionService(StainQueueClient stainQueueClient,
      StainSubmissionServiceConfig config) {
    this.stainQueueClient = stainQueueClient;
    this.config = config;

    this.rootDir = Path.of(config.getRootDir());
    createDirIfNotExists(rootDir);
    this.submittedDir = rootDir.resolve(config.getSubmittedDir());
    createDirIfNotExists(submittedDir);
    this.completedDir = rootDir.resolve(config.getCompletedDir());
    createDirIfNotExists(completedDir);
  }

  public SubmissionResponse enqueueStain(MultipartFile file, String uuid) {
    try {
      if (!file.isEmpty()) {
        byte[] bytes = file.getBytes();

        Path filePath = submittedDir.resolve(uuid);
        Files.write(filePath, bytes);

        SubmissionResponse response = new SubmissionResponse(uuid);

        stainQueueClient.submitStain(filePath.toAbsolutePath().toString(), uuid);

        return response;
      } else {
        throw new IllegalArgumentException("Submitted file is empty.");
      }
    } catch (IOException | SubmissionFailedException e) {
      throw new ServerErrorException("Error submitting file to queue", e);
    }
  }

  public byte[] fetchMap(String uuid) throws IOException {
    File f = Path.of(config.getRootDir(), config.getCompletedDir(), uuid + "." + IMAGE_SUFFIX)
        .toFile();
    if (f.exists()) {
      return StreamUtils.copyToByteArray(new FileInputStream(f));
    }

    throw new FileNotFoundException("File with uuid " + uuid + "may not be ready yet.");
  }

  private void createDirIfNotExists(Path path) {
    File file = path.toFile();
    if (!file.exists()) {
      file.mkdir();
    }
  }
}

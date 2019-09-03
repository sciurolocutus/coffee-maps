package com.kanjisoup.coffee.stain.submission.controller;

import com.kanjisoup.coffee.stain.submission.domain.StainSubmissionPostDto;
import com.kanjisoup.coffee.stain.submission.domain.SubmissionResponse;
import com.kanjisoup.coffee.stain.submission.exception.SubmissionFailedException;
import com.kanjisoup.coffee.stain.submission.service.StainSubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController("/")
public class StainSubmissionController {
    private StainSubmissionService stainSubmissionService;

    public StainSubmissionController(StainSubmissionService stainSubmissionService) {
        this.stainSubmissionService = stainSubmissionService;
    }

    @GetMapping("completed/{uuid}")
    public ResponseEntity<byte[]> getMap(HttpServletResponse response, @PathParam("uuid") String uuid) throws IOException {

        byte[] image = stainSubmissionService.fetchMap(uuid);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }

    @PostMapping("/mapMe")
    public SubmissionResponse mapMe(@RequestPart("file") MultipartFile file) {

        String uuid = UUID.randomUUID().toString();

        log.info("Adding new queue event: {} : {}", file.getName(), uuid);

        return stainSubmissionService.enqueueStain(file, uuid);
    }
}

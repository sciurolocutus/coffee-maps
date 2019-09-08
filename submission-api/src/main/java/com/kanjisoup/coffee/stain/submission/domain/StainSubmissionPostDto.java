package com.kanjisoup.coffee.stain.submission.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StainSubmissionPostDto {

  private MultipartFile image;
}

package com.kanjisoup.coffee.stain.sdk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StainQueueDto {

  private String filePath;
  private String uuid;
}

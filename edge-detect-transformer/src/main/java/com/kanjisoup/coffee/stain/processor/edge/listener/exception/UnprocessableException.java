package com.kanjisoup.coffee.stain.processor.edge.listener.exception;

public class UnprocessableException extends RuntimeException {

  public UnprocessableException(String e) {
    super(e);
  }

  public UnprocessableException(String s, Exception e) {
    super(s, e);
  }
}

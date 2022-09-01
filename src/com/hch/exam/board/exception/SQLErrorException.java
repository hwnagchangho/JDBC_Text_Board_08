package com.hch.exam.board.exception;

public class SQLErrorException extends RuntimeException {
  private Exception origin;

  public SQLErrorException(String message, Exception origin) {
    super(message); // 얜 뭐지? message 는 conn?
    this.origin = origin;
  }

  public Exception getOrigin() {
    return origin;
  }
}
package com.hch.exam.board;

import com.hch.exam.board.exception.SQLErrorException;

public class Main {
  public static void main(String[] args) {
    try {
      new App().run();
    }
    catch (SQLErrorException e) {
      System.err.println(e.getMessage());
      e.getOrigin().printStackTrace();
    }
  }
}
package com.hch.exam.board.controller;

import com.hch.exam.board.Rq;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Scanner sc; // 자식클래스에서 접근하기 위해서는 protected로 바꾸어주어야한다.
  protected Rq rq;

  public Controller(Rq rq, Scanner sc) {
    this.rq = rq;
    this.sc = sc;
  }

}

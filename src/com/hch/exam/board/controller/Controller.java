package com.hch.exam.board.controller;

import com.hch.exam.board.Rq;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Connection conn; // 자식클래스에서 접근하기 위해서는 protected로 바꾸어주어야한다.
  protected Scanner sc;
  protected Rq rq;
  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public void setScanner(Scanner sc) {
    this.sc = sc;
  }

  public void setRq(Rq rq) {
    this.rq = rq;
  }
}

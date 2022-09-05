package com.hch.exam.board;

public class Member {
  int id;
  String regDate;
  String updateDate;
  String loginId;
  String loginPw;
  String name;

  public Member(int id, String regDate, String updateDate, String loginId, String loginPw, String name){
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.loginId = loginId;
    this.loginPw = loginPw;
    this.name = name;
  }
}

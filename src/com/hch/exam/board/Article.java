package com.hch.exam.board;

public class Article {
  int id;

  String regDate;

  String updateDate;

  String title;

  String body;

  public Article(int id, String regDate, String updateDate, String title, String body){
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.body = body;
  }



  public String toString(){
    return String.format("{id : %d, regDate : %s, updateDate : %s, title : \"%s\", body : \"%s\"}",
        id, regDate, updateDate, title, body);
  }
}

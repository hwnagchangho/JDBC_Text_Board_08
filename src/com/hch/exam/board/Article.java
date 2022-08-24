package com.hch.exam.board;

public class Article {
  int id;

  String title;

  String body;

  Article(int id, String body, String title){
    this.id = id;
    this.title = title;
    this.body = body;
  }

  public String toString(){
    return String.format("{id : %d , title : \"%s\", body : \"%s\" }", id, title, body);
  }
}

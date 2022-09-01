package com.hch.exam.board;

import java.util.Map;

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

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");
  }


  public String toString(){
    return String.format("{id : %d, regDate : %s, updateDate : %s, title : \"%s\", body : \"%s\"}",
        id, regDate, updateDate, title, body);
  }
}

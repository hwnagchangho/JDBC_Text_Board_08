package com.hch.exam.board.controller;

import com.hch.exam.board.Article;
import com.hch.exam.board.Rq;
import com.hch.exam.board.util.DBUtil;
import com.hch.exam.board.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController extends Controller{
  public void add() {
    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

//        int id = ++articleLastId; => 데이터베이스에서 AUTO_INCREMENT로 자동으로 올려주기때문에 필요가 없다.
    SecSql sql = new SecSql();

    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()"); // 여기서 띄어쓰기 안해도되는 이유가 sql append()에 그렇게만들어놔서?
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title); // 이렇게 쓸수 있는 이유는?
    sql.append(", `body` = ?", body);

    int id = DBUtil.insert(conn, sql);

    System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

  }

  public void list() {
    System.out.println("==게시물 리스트==");
    System.out.println("=================");
    System.out.println(" 번호 / 제목 / 내용 ");

    List<Article> articles = new ArrayList<>();

    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

    for(Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    if(articles.size() == 0){
      System.out.println("게시물이 존재하지 않습니다.");
    }

    for( Article article : articles ){
      System.out.printf("%d / %s / %s\n", article.id, article.title, article.body);
    }


    System.out.println("=================");
  }

  public void detail(Rq rq) {
    System.out.println("== 게시물 상세보기 ==");

    int id = rq.getIntParam("id", 0);

    if( id == 0 ){
      System.out.println("id를 다시 입력해주세요");
      return;
    }

    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

    if(articleMap.isEmpty()) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    Article article = new Article(articleMap);

    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("등록날짜 : %s\n", article.regDate);
    System.out.printf("수정날짜 : %s\n", article.updateDate);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.body);
  }

  public void delete(Rq rq) {
    System.out.println("== 게시물 삭제 ==");
    int id = rq.getIntParam("id", 0);

    if( id == 0 ){
      System.out.println("id를 다시 입력해주세요");
      return;
    }

    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);
    int articlesCount = DBUtil.selectRowIntValue(conn, sql);

    if (articlesCount == 0) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(conn, sql);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

  }

  public void modify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if( id == 0 ){
      System.out.println("id를 다시 입력해주세요");  // id=4 햇을때 4번게시물이 없는데 수정이 되는 오류가 있어서 getIntParam에서 고쳤는데 다른방법은없냐? 자바2에서처럼
      return; //이렇게 바꾸면 delete에서 안나온다.
    }
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    int articlesCount = DBUtil.selectRowIntValue(conn, sql);

    if (articlesCount == 0) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    String title = sc.nextLine();
    System.out.printf("새 내용 : ");
    String body = sc.nextLine();

    sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append(" WHERE id = " + id);

    DBUtil.update(conn, sql);

    System.out.println("게시물이 수정되었습니다.");

  }
}

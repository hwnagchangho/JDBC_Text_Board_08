package com.hch.exam.board.controller;

import com.hch.exam.board.container.Container;
import com.hch.exam.board.dto.Article;
import com.hch.exam.board.Rq;
import com.hch.exam.board.service.ArticleService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller{
  ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
  }

  public void add() {
    if(Container.session.isLogined() == false){
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int memberId = Container.session.loginedMemberId;

    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

//        int id = ++articleLastId; => 데이터베이스에서 AUTO_INCREMENT로 자동으로 올려주기때문에 필요가 없다.

    int id = articleService.add(memberId, title, body);

    System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

  }

  public void showList() {
    System.out.println("==게시물 리스트==");
    System.out.println("=================");
    System.out.println(" 번호 / 제목 / 내용 ");

    List<Article> articles = articleService.getArticles();


    if(articles.size() == 0){
      System.out.println("게시물이 존재하지 않습니다.");
    }

    for( Article article : articles ){
      System.out.printf("%d / %s / %s / %s\n", article.id, article.title, article.memberId, article.extra__writer, article.body);
    }


    System.out.println("=================");
  }

  public void showDetail(Rq rq) {
    System.out.println("== 게시물 상세보기 ==");

    int id = rq.getIntParam("id", 0);

    if( id == 0 ){
      System.out.println("id를 다시 입력해주세요");
      return;
    }

    articleService.hitIncrease(id);
    Article article = articleService.getArticleById(id);

    if(article == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("조회수 : %s\n", article.hit);
    System.out.printf("등록날짜 : %s\n", article.regDate);
    System.out.printf("수정날짜 : %s\n", article.updateDate);
    System.out.printf("작성자 : %s\n", article.extra__writer);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.body);
  }

  public void delete(Rq rq) {
    if(Container.session.isLogined() == false){
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    System.out.println("== 게시물 삭제 ==");
    int id = rq.getIntParam("id", 0);

    if( id == 0 ){
      System.out.println("id를 다시 입력해주세요");
      return;
    }

    Article article = articleService.getArticleById(id);

    if(article == null){
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }
    if(article.memberId != Container.session.loginedMemberId){
      System.out.println("권한이 없습니다.");
      return;
    }

    boolean articleExists = articleService.articleExists(id);

    if (articleExists == false) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

  }

  public void modify(Rq rq) {
    if(Container.session.isLogined() == false){
      System.out.println("로그인 후 이용해주세요.");
      return;
    }

    int id = rq.getIntParam("id", 0);

    if( id == 0 ){
      System.out.println("id를 다시 입력해주세요");  // id=4 햇을때 4번게시물이 없는데 수정이 되는 오류가 있어서 getIntParam에서 고쳤는데 다른방법은없냐? 자바2에서처럼
      return; //이렇게 바꾸면 delete에서 안나온다.
    }

    Article article = articleService.getArticleById(id);

    if(article == null){
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }
    if(article.memberId != Container.session.loginedMemberId){
      System.out.println("권한이 없습니다.");
      return;
    }

    boolean articleExists = articleService.articleExists(id);

    if (articleExists == false) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    String title = sc.nextLine();
    System.out.printf("새 내용 : ");
    String body = sc.nextLine();

    articleService.update(id, title, body);

    System.out.println("게시물이 수정되었습니다.");

  }
}

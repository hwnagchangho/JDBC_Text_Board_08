package com.hch.exam.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  public void run(){
    Scanner sc = Container.sc;

    int articleLastId = 0;

    List<Article> articles = new ArrayList<>();

    while(true){

      System.out.printf("명령어)");
      String cmd = sc.nextLine().trim();

      if(cmd.equals("/usr/article/write")){
        System.out.println("== 게시물 등록 ==");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        int id = ++articleLastId;

        Article article = new Article(id, title, body);

        articles.add(article);

        System.out.println("입력받은 객체 : " + article);
        System.out.printf("%d번 게시물이 입력되었습니다.\n", article.id);
      }

      else if(cmd.equals("/usr/article/list")){
        System.out.println("==게시물 리스트==");
        System.out.println("=================");
        System.out.println(" 번호 / 제목 / 내용 ");

        if(articles.size() == 0){
          System.out.println("게시물이 존재하지 않습니다.");
        }

        for( Article article : articles ){
          System.out.printf("%d / %s / %s\n", article.id, article.title, article.body);
        }


        System.out.println("=================");
      }

      else if(cmd.equals("exit")){
        System.out.println("프로그램 종료!");
        break;
      }
    }
    sc.close();
  }
}

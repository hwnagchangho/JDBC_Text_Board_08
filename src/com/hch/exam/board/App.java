package com.hch.exam.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        Connection conn = null;
        PreparedStatement pstat = null;

        try{
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "changho", "dhtwo19843"); // 여권이라 보면된다 이게 있으면 데이터베이스에 콘을 통해서 말할 수 있다.
          System.out.println("연결 성공!"); //접속

          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()"; // 한칸 뛰어주는거랑 , 처리 잘해야된다 안하면 오류난다.
          sql += ", updateDate = NOW()";
          sql += ", title = \"" + title + "\"";
          sql += ", `body` = \"" + body + "\"";


          pstat = conn.prepareStatement(sql); //쿼리 전달
          int affectedRows = pstat.executeUpdate(); //쿼리 실행

          System.out.println("affectedRows : " + affectedRows);

        }
        catch(ClassNotFoundException e){
          System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
          System.out.println("에러: " + e);
        }
        finally{ //마지막에 conn은 수동으로 꺼줘야된다. 걍 닥치고 이코드 다 외워라
          try{
            if( conn != null && !conn.isClosed()){
              conn.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
          try{
            if(pstat != null && !pstat.isClosed()){
              pstat.close();
            }
          }catch (SQLException e){
            e.printStackTrace();
          }
        }

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

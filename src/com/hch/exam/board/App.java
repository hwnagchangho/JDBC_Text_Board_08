package com.hch.exam.board;

import com.hch.exam.board.Test.JDBCSelectTest;
import com.hch.exam.board.util.DBUtil;
import com.hch.exam.board.util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

  public void run(){

    Scanner sc = Container.sc;
    while(true){

      System.out.printf("명령어)");
      String cmd = sc.nextLine().trim();

      Rq rq = new Rq(cmd);

      Connection conn = null;

      try {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/text_board1?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

        conn = DriverManager.getConnection(url, "changho", "dhtwo19843"); // 여권이라 보면된다 이게 있으면 데이터베이스에 콘을 통해서 말할 수 있다.
        System.out.println("연결 성공!"); //접속
        doAction(rq, conn, sc, cmd);

      } catch (ClassNotFoundException e) {
        System.err.println("예외 : MySQL 드라이버 클래스가 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      }catch(SQLException e){
        System.err.println("예외 : DB에 연결할 수 없습니다..");
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      finally { //마지막에 conn은 수동으로 꺼줘야된다. 걍 닥치고 이코드 다 외워라
        try {
          if (conn != null && !conn.isClosed()) {
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    sc.close();
  }

  private void doAction(Rq rq, Connection conn, Scanner sc, String cmd) {
    if(rq.getUrlPath().equals("/usr/article/write")){
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

      System.out.printf("%d번 게시물이 생성되었습니다.", id);

   }

    else if(rq.getUrlPath().equals("/usr/article/list")){
      System.out.println("==게시물 리스트==");
      System.out.println("=================");
      System.out.println(" 번호 / 제목 / 내용 ");

      List<Article> articles = new ArrayList<>(); // DB에 저장하므로 따로 저장할필요가 없어서 LIST를 받아올때만 필요하기 때문에 if문안에다 작성해준다.

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
    else if(rq.getUrlPath().equals("/usr/article/modify")){

      int id = rq.getIntParam("id", 0);

      if( id == 0 ){
        System.out.println("id를 다시 입력해주세요");  // id=4 햇을때 4번게시물이 없는데 수정이 되는 오류가 있어서 getIntParam에서 고쳤는데 다른방법은없냐? 자바2에서처럼
        return;
      }

      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);
      sql.append(" WHERE id = " + id);

      DBUtil.update(conn, sql);

      System.out.println("게시물이 수정되었습니다.\n");

    }
    else if(cmd.equals("exit")){
      System.out.println("프로그램 종료!");
      System.exit(0);
    }
  }
}

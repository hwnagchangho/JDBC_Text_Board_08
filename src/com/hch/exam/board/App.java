package com.hch.exam.board;

import com.hch.exam.board.container.Container;
import com.hch.exam.board.controller.ArticleController;
import com.hch.exam.board.controller.MemberController;

import java.sql.*;
import java.util.*;

public class App {

  public void run(){

    Container.scanner = new Scanner(System.in);

    Container.init();

    while(true){

      System.out.printf("명령어)");
      String cmd = Container.scanner.nextLine().trim();

      Rq rq = new Rq(cmd);

      Connection conn = null;

      try {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/text_board1?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

        conn = DriverManager.getConnection(url, "changho", "dhtwo19843"); // 여권이라 보면된다 이게 있으면 데이터베이스에 콘을 통해서 말할 수 있다.

        Container.conn = conn;
        action(rq, cmd);

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
    Container.scanner.close();
  }

  private void action(Rq rq, String cmd) {
    ArticleController articleController = new ArticleController();

    MemberController memberController = new MemberController();

    if (rq.getUrlPath().equals("/usr/member/whoami")) {
      memberController.whoami();
    } else if (rq.getUrlPath().equals("/usr/member/join")) {
      memberController.join();
    } else if (rq.getUrlPath().equals("/usr/member/login")) {
      memberController.login();
    } else if (rq.getUrlPath().equals("/usr/member/logout")) {
      memberController.logout();
    } else if (rq.getUrlPath().equals("/usr/article/write")) {
      articleController.add();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      articleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      articleController.showDetail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      articleController.delete(rq);
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      articleController.modify(rq);
    }
    else if(cmd.equals("exit")){
      System.out.println("프로그램 종료!");
      System.exit(0);
    }
  }
}

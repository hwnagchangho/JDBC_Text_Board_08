package com.hch.exam.board;

import com.hch.exam.board.Test.JDBCSelectTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

  public void run(){
    Scanner sc = Container.sc;

    int articleLastId = 0;


    while(true){

      System.out.printf("명령어)");
      String cmd = sc.nextLine().trim();

      Rq rq = new Rq(cmd);

      if(rq.getUrlPath().equals("/usr/article/write")){
        System.out.println("== 게시물 등록 ==");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

//        int id = ++articleLastId; => 데이터베이스에서 AUTO_INCREMENT로 자동으로 올려주기때문에 필요가 없다.

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
          sql += ", title = \"" + title + "\""; // DB에 저장해주므로 따로 List에 저장해서 가져올 필요가 없다.
          sql += ", `body` = \"" + body + "\"";


          pstat = conn.prepareStatement(sql); //쿼리 전달
          pstat.executeUpdate(); //쿼리 실행


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
          } catch( SQLException e){
            e.printStackTrace();
          }
          try{
            if(pstat != null && !pstat.isClosed()){
              pstat.close();
            }
          } catch (SQLException e){
            e.printStackTrace();
          }
        }
      }

      else if(rq.getUrlPath().equals("/usr/article/list")){
        System.out.println("==게시물 리스트==");
        System.out.println("=================");
        System.out.println(" 번호 / 제목 / 내용 ");

        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        List<Article> articles = new ArrayList<>(); // DB에 저장하므로 따로 저장할필요가 없어서 LIST를 받아올때만 필요하기 때문에 if문안에다 작성해준다.

        try{
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "changho", "dhtwo19843"); // 여권이라 보면된다 이게 있으면 데이터베이스에 콘을 통해서 말할 수 있다.
          System.out.println("연결 성공!"); //접속

          String sql = "SELECT *";
          sql += " FROM article"; // 한칸 뛰어주는거랑 , 처리 잘해야된다 안하면 오류난다.
          sql += " ORDER BY id DESC";

          pstat = conn.prepareStatement(sql); //쿼리 전달
          rs = pstat.executeQuery(sql); //Select 문에서만 실행, 데이터베이스에서 데이터를 가져와 결과집합 //리턴받아서 데이터베이스 값을 가져오는거다.

          while(rs.next()){
            int id = rs.getInt("id");
            String regDate = rs.getString("regDate");
            String updateDate = rs.getString("updateDate");
            String title = rs.getString("title");
            String body = rs.getString("body");

            Article article = new Article(id, regDate, updateDate, title, body);
            articles.add(article);
          }


        }
        catch(ClassNotFoundException e){
          System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
          System.out.println("에러: " + e);
        }
        finally{ //마지막에 conn은 수동으로 꺼줘야된다. 걍 닥치고 이코드 다 외워라
          try{
            if(rs != null && !rs.isClosed()){
              rs.close();
            }
          }catch (SQLException e){
            e.printStackTrace();
          }
          try{
            if(pstat != null && !pstat.isClosed()){
              pstat.close();
            }
          }catch (SQLException e){
            e.printStackTrace();
          }
          try{
            if( conn != null && !conn.isClosed()){
              conn.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
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
          System.out.println("id를 다시 입력해주세요");
          continue;
        }

        System.out.printf("새 제목 : ");
        String title = sc.nextLine();
        System.out.printf("새 내용 : ");
        String body = sc.nextLine();

        Connection conn = null;
        PreparedStatement pstat = null;

        try{
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "changho", "dhtwo19843");
          System.out.println("연결 성공!");

          String sql = "UPDATE article";
          sql += " SET updateDate = NOW()";
          sql += ", title = \"" + title + "\"";
          sql += ", `body` = \"" + body + "\"";
          sql += " WHERE id = " + id;

          pstat = conn.prepareStatement(sql);
          pstat.executeUpdate();


        }
        catch(ClassNotFoundException e){
          System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
          System.out.println("에러: " + e);
        }
        finally{ //마지막에 conn은 수동으로 꺼줘야된다. 걍 닥치고 이코드 다 외워라
          try{
            if(pstat != null && !pstat.isClosed()){
              pstat.close();
            }
          }catch (SQLException e){
            e.printStackTrace();
          }
          try{
            if( conn != null && !conn.isClosed()){
              conn.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
        }
      }

      else if(cmd.equals("exit")){
        System.out.println("프로그램 종료!");
        break;
      }
    }
    sc.close();
  }
}

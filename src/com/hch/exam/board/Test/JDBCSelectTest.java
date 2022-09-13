package com.hch.exam.board.Test;

import com.hch.exam.board.dto.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {

    Connection conn = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

    try{
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      conn = DriverManager.getConnection(url, "changho", "dhtwo19843"); // 여권이라 보면된다 이게 있으면 데이터베이스에 콘을 통해서 말할 수 있다.
      System.out.println("연결 성공!"); //접속

      String sql = "SELECT *";
      sql += " FROM article"; // 한칸 뛰어주는거랑 , 처리 잘해야된다 안하면 오류난다.
      sql += " ORDER BY id DESC";

      pstat = conn.prepareStatement(sql); //쿼리 전달
      rs = pstat.executeQuery(sql); //Select 문에서만 실행, 데이터베이스에서 데이터를 가져와 결과집합

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
  }
}

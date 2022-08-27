package com.hch.exam.board.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
  public static void main(String[] args) {

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
      sql += ", title = CONCAT(\"제목\", RAND())";
      sql += ", `body` = CONCAT(\"내용\", RAND())";

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
  }
}

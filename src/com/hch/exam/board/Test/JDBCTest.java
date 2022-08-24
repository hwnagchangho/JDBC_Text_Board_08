package com.hch.exam.board.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCTest {
      public static void main(String[] args) {

        Connection conn = null;

        try{
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "changho", "dhtwo19843"); // 여권이라 보면된다 이게 있으면 데이터베이스에 콘을 통해서 말할 수 있다.
          System.out.println("연결 성공!");

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
        }
      }
  }

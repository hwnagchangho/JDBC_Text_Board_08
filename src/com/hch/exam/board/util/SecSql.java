package com.hch.exam.board.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SecSql {
  private StringBuilder sqlBuilder;
  private List<Object> datas;

  @Override
  public String toString() {
    return "sql=" + getFormat() + ", data=" + datas;
  }

  public SecSql() {
    sqlBuilder = new StringBuilder();
    datas = new ArrayList<>();
  }

  public boolean isInsert() {
    return getFormat().startsWith("INSERT");
  }

  public SecSql append(Object... args) {    //(타입... 변수명 ): 일정하지 않은 개수의 파라미터 (Arbitrary Number of Arguments )
    if (args.length > 0) {                  //    일정한 형의 변수를 여러 개  전달해야 할 때 사용한다.
      String sqlBit = (String) args[0];     //    메소드 호출시, 일반 파라미터 처럼 넘기고
      sqlBuilder.append(sqlBit + " ");      //    메소드에서 받은 파라미터는 배열로 사용한다.
    } // 그럼 이거는 쿼리를 받아와서 저장해주고 String 클래스의 append()함수를이용해 이어붙혀서 쿼리를 작성해주는것???

    for (int i = 1; i < args.length; i++) {
      datas.add(args[i]); // sqlBuilder 와 datas에 왜 둘다저장?
    }

    return this; // 여기서 this는 무엇을 가르키나?
  }

  public PreparedStatement getPreparedStatement(Connection dbConn) throws SQLException {
    PreparedStatement stmt = null;  //    https://cocodo.tistory.com/11 PreparedStatement 사용법? 데이터 전달하는 객체?

    if (isInsert()) {
      stmt = dbConn.prepareStatement(getFormat(), Statement.RETURN_GENERATED_KEYS);
    } else {
      stmt = dbConn.prepareStatement(getFormat());
    }

    for (int i = 0; i < datas.size(); i++) {
      Object data = datas.get(i);
      int parameterIndex = i + 1;

      if (data instanceof Integer) {
        stmt.setInt(parameterIndex, (int) data);
      } else if (data instanceof String) {
        stmt.setString(parameterIndex, (String) data);
      }
    }

    return stmt;
  }

  public String getFormat() {
    return sqlBuilder.toString(); // 왜 sqlBuilder를 붙힌거지
  }

  public static SecSql from(String sql) {
    return new SecSql().append(sql);
  }
}
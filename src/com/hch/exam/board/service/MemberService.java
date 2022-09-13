package com.hch.exam.board.service;

import com.hch.exam.board.Rq;
import com.hch.exam.board.dao.MemberDao;
import com.hch.exam.board.dto.Member;

import java.sql.Connection;
import java.util.Scanner;

public class MemberService {
  MemberDao memberDao;
  public MemberService(Connection conn){
    memberDao = new MemberDao(conn);
  }

  public boolean isLoginedDup(String loginId) {
    return memberDao.isLoginedDup(loginId);
  }

  public int join(String loginId, String loginPw, String name) {
    return memberDao.join(loginId, loginPw, name);
  }

  public Member getMemberByLoginId(String loginId) {
    return memberDao.getMemberByLoginId(loginId);
  }
}

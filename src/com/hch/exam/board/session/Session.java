package com.hch.exam.board.session;

import com.hch.exam.board.container.Container;
import com.hch.exam.board.dto.Member;

public class Session {
  public int loginedMemberId;
  public Member loginedMember;

  public Session() {
    loginedMemberId = -1;
  }

  public boolean isLogined() {
    return loginedMemberId != -1;
  }

  public void login(Member member){
    loginedMemberId = member.id;
    loginedMember = member;
  }

  public void logout() {
    loginedMember = null;
    loginedMemberId = -1;
  }

}

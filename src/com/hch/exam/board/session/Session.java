package com.hch.exam.board.session;

import com.hch.exam.board.dto.Member;

public class Session {
  public int loginedMemberId;
  public Member loginedMember;

  public Session() {
    loginedMemberId = -1;
  }
}

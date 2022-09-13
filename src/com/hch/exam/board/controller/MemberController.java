package com.hch.exam.board.controller;

import com.hch.exam.board.Rq;
import com.hch.exam.board.dto.Member;
import com.hch.exam.board.service.MemberService;


import java.sql.Connection;
import java.util.Scanner;

public class MemberController extends Controller{
  private MemberService memberService;

  public MemberController(Connection conn, Rq rq, Scanner sc){
    super(rq, sc);
    memberService = new MemberService(conn);
  }
  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println(" == 회원가입 ==");
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = sc.nextLine().trim();

      if(loginId.length() == 0){
        System.out.println("아이디를 입력해주세요.");
        continue;
      }

      boolean isLoginedDup = memberService.isLoginedDup(loginId);

      if(isLoginedDup) {
        System.out.printf("%s(은)는 이미 사용중인 로그인 아이디입니다.\n", loginId);
        continue;
      }
      break;
    }

    while(true){

      System.out.printf("로그인 비밀번호 : ");
      loginPw = sc.nextLine().trim();

      if(loginPw.length() == 0){
        System.out.println("비밀번호를 입력해주세요.");
      }

      boolean loginPwConfirmSame = true;

      while(true){
        System.out.printf("로그인 비밀번호확인 : ");
        loginPwConfirm = sc.nextLine().trim();

        if(loginPwConfirm.length() == 0){
          System.out.println("비밀번호확인을 입력해주세요.");
        }

        if(loginPw.equals(loginPwConfirm) == false){
          System.out.println("비밀번호가 일치하지 않습니다.");
          loginPwConfirmSame = false;
          break;
        }
        break;
      }
      if(loginPwConfirmSame){
        break;
      }

    }
    while(true){
      System.out.printf("이름 : ");
      name = sc.nextLine().trim();

      if(name.length() == 0){
        System.out.println("이름을 입력해주세요.");
        continue;
      }
      break;
    }

    int id = memberService.join(loginId, loginPw, name);

    System.out.printf("%s님 환영합니다.\n", name);
  }

  public void login() {
    String loginId;
    String loginPw;
    System.out.println(" == 로그인 == ");

    while(true){
      System.out.printf("로그인 아이디:");
      loginId = sc.next().trim();

      if(loginId.length() == 0){
        System.out.println("아이디를 입력해주세요.");
        continue;
      }

      boolean isLoginedDup = memberService.isLoginedDup(loginId);

      if(isLoginedDup == false) {
        System.out.printf("%s(은)는 존재하지 않는 로그인 아이디입니다.\n", loginId);
        continue;
      }

      break;
    }

    Member member = memberService.getMemberByLoginId(loginId);

    int tryMaxCount = 3;
    int tryCount = 0;

    while(true){
      if( tryCount >= tryMaxCount){
        System.out.println("비밀번호를 확인 후 다시 이용해주세요.");
        break;
      }

      System.out.printf("로그인 비밀전호:");
      loginPw = sc.next().trim();

      if(loginPw.length() == 0){
        System.out.println("비밀번호를 입력해주세요.");
        continue;
      }

      if(member.loginPw.equals(loginPw) == false) {
        tryCount++;
        System.out.println("비밀번호가 일치하지 않습니다.");
        continue;
      }

      System.out.printf("%s님 환영합니다.\n", member.name);
      break;
    }

  }
}

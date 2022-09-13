package com.hch.exam.board.container;

import com.hch.exam.board.controller.ArticleController;
import com.hch.exam.board.controller.MemberController;
import com.hch.exam.board.dao.ArticleDao;
import com.hch.exam.board.dao.MemberDao;
import com.hch.exam.board.service.ArticleService;
import com.hch.exam.board.service.MemberService;
import com.hch.exam.board.session.Session;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
  public static ArticleController articleController;
  public static MemberController memberController;

  public static ArticleService articleService;
  public static MemberService memberService;

  public static ArticleDao articleDao;
  public static MemberDao memberDao;

  public static Session session;

  public static Scanner scanner;
  public static Connection conn;

  public static void init() {
    session = new Session();
    scanner = new Scanner(System.in);

    articleDao = new ArticleDao();
    memberDao = new MemberDao();

    articleService = new ArticleService();
    memberService = new MemberService();

    articleController = new ArticleController();
    memberController = new MemberController();
  }
}

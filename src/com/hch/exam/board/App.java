package com.hch.exam.board;

import java.util.Scanner;

public class App {

  public void run(){
    Scanner sc = Container.sc;

    while(true){

      System.out.printf("명령어)");
      String cmd = sc.nextLine().trim();

      if(cmd.equals("/usr/article/write")){

      }

      else if(cmd.equals("/usr/article/list")){
        System.out.println("==게시물 리스트==");
      }

      else if(cmd.equals("exit")){
        System.out.println("프로그램 종료!");
        break;
      }
    }
    sc.close();
  }
}

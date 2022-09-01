package com.hch.exam.board.util;

import java.util.HashMap;
import java.util.Map;

public class Util {
  public static Map<String, String> getParamsFromUrl(String url){
    Map<String, String> params = new HashMap<>();
    String[] urlBits = url.split("\\?",2);

    if(urlBits.length == 1){
      return params;
    }

    String queryStr = urlBits[1];
    for( String bit : urlBits[1].split("&")){
      String[] bits = bit.split("=",2);
      if(bits.length == 1){
        continue;
      }
      params.put(bits[0], bits[1]);
    }

    return params;
  }

  public static String getPathFromUrl(String url){
    return url.split("\\?",2)[0];
  }
}

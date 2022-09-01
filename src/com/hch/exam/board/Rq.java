package com.hch.exam.board;

import com.hch.exam.board.util.Util;

import java.util.Map;

public class Rq {
  String url;
  Map<String, String> params;
  String path;

  Rq(String url){
    this.url = url;
    this.params = Util.getParamsFromUrl(url);
    this.path = Util.getPathFromUrl(url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String getUrlPath() {
    return path;
  }

  public int getIntParam(String paramsName, int defaultValue) {

    if ( params.containsKey(paramsName) == false) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramsName));
    }
    catch ( NumberFormatException e) {
      return defaultValue;
    }



  }

  public String getParam(String paramsName, String defaultValue) {

    if ( params.containsKey(paramsName) == false ) {
      return defaultValue;
    }

    try {
      return params.get(paramsName);
    }
    catch ( NumberFormatException e) {
      return defaultValue;
    }
  }
}

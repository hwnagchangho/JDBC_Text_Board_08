package com.hch.exam.board.service;

import com.hch.exam.board.container.Container;
import com.hch.exam.board.dto.Article;
import com.hch.exam.board.dao.ArticleDao;

import java.util.List;

public class ArticleService {
  ArticleDao articleDao;
  public ArticleService() {
    articleDao = Container.articleDao;
  }

  public int add(int memberId, String title, String body) {
    return articleDao.add(memberId, title, body);
  }

  public List<Article> getArticles() {
    return articleDao.getArticles();
  }

  public Article getArticleById(int id) {
    return articleDao.getArticleById(id);
  }

  public boolean articleExists(int id) {
    return articleDao.articleExists(id);
  }

  public void delete(int id) {
    articleDao.delete(id);
  }

  public void update(int id, String title, String body) {
    articleDao.update(id, title, body);
  }

  public void hitIncrease(int id) {
    articleDao.hitIncrease(id);
  }
}

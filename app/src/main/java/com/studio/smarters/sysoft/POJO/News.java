package com.studio.smarters.sysoft.POJO;

public class News {
    private String news_title,news_desc,news_pic;

    public News() {
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_desc() {
        return news_desc;
    }

    public void setNews_desc(String news_desc) {
        this.news_desc = news_desc;
    }

    public String getNews_pic() {
        return news_pic;
    }

    public void setNews_pic(String news_pic) {
        this.news_pic = news_pic;
    }

    public News(String news_title, String news_desc, String news_pic) {

        this.news_title = news_title;
        this.news_desc = news_desc;
        this.news_pic = news_pic;
    }
}

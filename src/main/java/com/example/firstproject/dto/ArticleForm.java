package com.example.firstproject.dto;

import com.example.firstproject.entitiy.Article;

public class ArticleForm {
    private String title; //제목을 받을 필드
    private String content; //내용을 받을 필드

    //전송받은 제목과 내용을 필드에 저장하는 생성자
    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
/*

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
*/

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Article toEntity() {
        return new Article(null, title, content);
    }
}

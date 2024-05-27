package com.example.firstproject.dto;

import com.example.firstproject.entitiy.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //클래스 안쪽의 모든 필드를 매개변수로 하는 생성자를 자동으로 만들어주는 어노테이션(lombok사용시)
@ToString //toString() 메소드를 자동으로 생성해주는 어노테이션(lombok사용시)
public class ArticleForm {
    private Long id; //id필드 추가
    private String title; //제목을 받을 필드
    private String content; //내용을 받을 필드

    public Article toEntity() {
        return new Article(id, title, content); //null -> id로 수정
    }
}

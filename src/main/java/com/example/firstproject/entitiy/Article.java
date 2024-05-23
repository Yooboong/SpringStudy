package com.example.firstproject.entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //클래스 안쪽의 모든 필드를 매개변수로 하는 생성자를 자동으로 만들어주는 어노테이션(lombok사용시)
@ToString //toString() 메소드를 자동으로 생성해주는 어노테이션(lombok사용시)
@Entity //엔티티 선언
public class Article {

    @Id //엔티티의 대표값 지정
    @GeneratedValue //자동 생성 기능 추가(숫자가 자동으로 매겨짐)
    private Long id;

    @Column //title 필드 선언, DB테이블의 title열과 연결됨
    private String title;

    @Column //content 필드 선언, DB테이블의 content열과 연결됨
    private String content;

}

package com.example.firstproject.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //클래스 안쪽의 모든 필드를 매개변수로 하는 생성자를 자동으로 만들어주는 어노테이션(lombok사용시)
@NoArgsConstructor //기본 생성자 추가 어노테이션 (객체 생성시 내부적으로 기본 생성자로 생성하고 setter로 필드 값을 설정한다고함, 따라서 반드시 명시해야됨)
@ToString //toString() 메소드를 자동으로 생성해주는 어노테이션(lombok사용시)
@Entity //엔티티 선언
@Getter //getter 추가
public class Article {

/*  기본키를 자동으로 생성하는 방법 4가지
    기본키를 자동으로 생성할 때에는 @Id와 @GeneratedValue 어노테이션이 함께 사용되어야 한다.

    1. IDENTITY - @GeneratedValue(strategy = GenerationType.IDENTITY)
       기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도
       데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성해준다.

    2. SEQUENCE - @GeneratedValue(strategy = GenerationType.SEQUENCE)
    3. TABLE - @GeneratedValue(strategy = GenerationType.TABLE)
    4. AUTO - @GeneratedValue(strategy = GenerationType.AUTO)
*/

    @Id //엔티티의 대표값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id 자동 생성
    private Long id;

    @Column //title 필드 선언, DB테이블의 title열과 연결됨
    private String title;

    @Column //content 필드 선언, DB테이블의 content열과 연결됨
    private String content;

    public void patch(Article article) { //article(수정 엔티티)
        if (article.title != null) {
            this.title = article.title;
        }
        if (article.content != null) {
            this.content = article.content;
        }
    }
}

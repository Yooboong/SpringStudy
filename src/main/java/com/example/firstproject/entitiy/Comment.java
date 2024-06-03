package com.example.firstproject.entitiy;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity //해당 클래스가 엔티티임을 선언, 클래스 필드를 바탕으로 DB에 테이블 생성
@NoArgsConstructor //매개변수가 아예 없는 기본 생성자 자동 생성
@AllArgsConstructor //모든 필드를 매개변수로 갖는 생성자 자동 생성
@ToString //모든 필드를 출력할 수 있는 toString 메소드 자동 생성
@Getter //각 필드 값을 조회할 수 있는 Getter 메소드 자동 생성
public class Comment {
    @Id //대표키(Primary Key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 자동으로 1씩 증가
    private Long id; //대표키(PK)

    @ManyToOne //Comment 엔티티와 Article 엔티티를 다대일 관계로 설정
    @JoinColumn(name = "article_id") // 외래키(FK) 생성, Article 엔티티의 기본키(id)와 매핑
    //@JoinColumn(name="외래키_이름") 으로 외래키를 매핑하고 name 속성으로 매핑할 외래키 이름을 지정
    private Article article; //해당 댓글의 부모 게시글

    @Column //해당 필드를 테이블의 속성으로 매핑
    private String nickname; //댓글을 단 사람

    @Column //해당 필드를 테이블의 속성으로 매핑
    private String body; //댓글 본문

    public static Comment createComment(CommentDto dto, Article article) {
        //예외 발생
        if (dto.getId() != null) //dto에 id가 존재하면 오류 발생(엔티티의 id는 DB가 자동 생성하므로)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId()) //게시글이 일치하지 않는 경우
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        
        //엔티티 생성 및 변환
        return new Comment(
                dto.getId(), //댓글 아이디
                article, //부모 게시글
                dto.getNickname(), //댓글 닉네임
                dto.getBody() //댓글 본문
        );
    }

    public void patch(CommentDto dto) {
        //예외 발생
        if (this.id != dto.getId()) //댓글 수정 요청 시 url에 있는 id와 JSON 데이터의 id가 다른 경우 오류 발생
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");

        //객체 갱신
        if (dto.getNickname() != null) //수정할 닉네임 데이터가 있다면
            this.nickname = dto.getNickname(); //내용 반영
        if (dto.getBody() != null) //수정할 본문 데이터가 있다면
            this.body = dto.getBody(); //내용 반영
    }
}
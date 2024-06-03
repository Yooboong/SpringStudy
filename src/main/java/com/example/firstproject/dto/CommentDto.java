package com.example.firstproject.dto;

import com.example.firstproject.entitiy.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id; //댓글의 id
    /*
    JSON데이터의 key 이름과, 이를 받아 저장하는 DTO에 선언된 필드명이 다를 경우
    DTO 필드 위에 @JsonProperty("키_이름")을 작성해 줘야 한다.
    이렇게 하면 해당 키와 변수가 자동으로 매핑됨.
    */
    //@JsonProperty("article_id") //이 경우 JSON 데이터는 "article_id": 형식
    private Long articleId; //댓글의 부모 id
    private String nickname; //댓글 작성자
    private String body; //댓글 본문

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(), //댓글 엔티티의 id
                comment.getArticle().getId(), //댓글 엔티티가 속한 부모 게시글의 id
                comment.getNickname(), //댓글 엔티티의 nickname
                comment.getBody() //댓글 엔티티의 body
        );
    }
}

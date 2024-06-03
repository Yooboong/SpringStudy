package com.example.firstproject.repository;

import com.example.firstproject.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
    Repository : 최상위 Repository 인터페이스
    CrudRepository 및 ListCrudRepository : 엔티티의 CRUD 기능 제공
    PagingAndSortingRepository 및 ListPagingAndSortingRepository : 엔티티의 페이징 및 정렬 기능 제공
    JpaRepository : 엔티티의 CRUD 기능과 페이징 및 정렬 기능뿐만 아니라 JPA에 특화된 기능을 추가로 제공
*/
public interface CommentRepository extends JpaRepository<Comment, Long> { //JpaRepository<대상 엔티티, 대표키 값의 타입>
    //특정 게시글의 모든 댓글 조회
    //직접 작성한 쿼리를 메소드로 실행하기위해 @Query 어노테이션을 사용
    @Query(value = "SELECT * " +
            "FROM COMMENT " +
            "WHERE ARTICLE_ID = :articleId",
            nativeQuery = true) //value 속성에 실행하려는 쿼리 작성, 매개변수 앞에는 꼭 콜론(:)을 작성
    List<Comment> findByArticleId(Long articleId);

    //특정 닉네임의 모든 댓글 조회
    /*
        직접 작성한 쿼리를 메소드로 실행하기위한 두번째 방법
        "src/main/resource"에 "META-INF" 디렉토리를 만들고
        그 안에 orm.xml 파일을 작성하는 방법
    */
    List<Comment> findByNickname(String nickname);
}
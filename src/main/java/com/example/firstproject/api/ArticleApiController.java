package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entitiy.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    REST 컨트롤러는 JSON이나 텍스트같은 데이터를 반환하는 반면,
    일반 컨트롤러는 뷰 페이지를 반환
*/
@Slf4j //로그를 사용하기 위한 어노테이션 추가
@RestController //REST API용 컨트롤러 선언
public class ArticleApiController {

    @Autowired //게시글 service 주입
    private ArticleService articleService;

    //GET
    @GetMapping("/api/articles") //URL 요청 접수
    public List<Article> index() { //모든 데이터 가져오기
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable("id") Long id) { //특정 데이터 가져오기
        return articleService.show(id);
    }

    //POST
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { //데이터 생성
        //@RequestBody는 요청 시 본문(BODY)에 실어 보내는 JSON 데이터를 매개변수로 받아올 수 있도록 하는 어노테이션
        Article created = articleService.create(dto);
        return (created != null) ? //생성하면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.CREATED).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @RequestBody ArticleForm dto) { //데이터 수정
        Article updated = articleService.update(id, dto); //서비스를 통해 게시글 수정
        return (updated != null) ? //수정되면 정상, 안되면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable("id") Long id) { //데이터 삭제
        Article deleted = articleService.delete(id); //서비스를 통해 게시글 삭제
        return (deleted != null) ? //삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        /*
            ResponseEntity의 build() 메소드는 HTTP 응답의 body가 없는 ResponseEntity 객체를 생성.
            따라서 build() 메소드로 생성된 객체는 body(null)의 결과와 같음
        */
    }

}

package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entitiy.Article;
import com.example.firstproject.repository.ArticleRepository;
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

    @Autowired //게시글 repository 주입
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("/api/articles") //URL 요청 접수
    public List<Article> index() { //모든 데이터 가져오기
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable("id") Long id) { //특정 데이터 가져오기
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("api/articles")
    public Article create(@RequestBody ArticleForm dto) { //데이터 생성
        //@RequestBody는 요청 시 본문(BODY)에 실어 보내는 JSON 데이터를 매개변수로 받아올 수 있도록 하는 어노테이션
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @RequestBody ArticleForm dto) { //데이터 수정
        //DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        //타깃 조회
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if (target == null || id != article.getId()) { //대상 엔티티가 없거나 수정 요청 id와 본문 id가 다를 경우
            //400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            /*
                ResponseEntity는 REST 컨트롤러의 반환형, 즉 REST API의 응답을 위해 사용하는 클래스.
                REST API의 요청을 받아 응답할 때 이 클래스에 HTTP 상태 코드, 헤더, 본문을 실어 보낼 수 있음.

                HttpStatus는 HTTP 상태 코드를 관리하는 클래스로, 다양한 Enum 타입과 관련한 메소드를 가짐.
                상태코드 200은 HttpStatus.OK, 201은 HttpStatus.CREATED
                400은 HttpStatus.BAD_REQUEST를 의미함.
            */
        }

        //업데이트 및 정상 응답(200)하기
        target.patch(article); //기존 데이터에 새 데이터를 붙이는 patch() 메소드 구현하기(이거 없이 데이터 일부만 수정 불가)
        //patch() 메소드를 사용하지 않으면 데이터의 일부만 수정할 때, 수정하지 않는 다른 값들이 날아감(null이 됨)
        Article updated = articleRepository.save(target); //수정 내용 DB에 최종 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable("id") Long id) { //데이터 삭제
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //대상 삭제
        articleRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.OK).build();
        /*
            ResponseEntity의 build() 메소드는 HTTP 응답의 body가 없는 ResponseEntity 객체를 생성.
            따라서 build() 메소드로 생성된 객체는 body(null)의 결과와 같음
        */
    }

}

package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entitiy.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j //로그를 사용하기 위한 어노테이션
@Service //서비스 임을 명시하는 어노테이션
public class ArticleService {

    @Autowired //게시글 repository 주입
    private ArticleRepository articleRepository;

    public List<Article> index() { //모든 데이터 가져오기
        return articleRepository.findAll();
    }

    public Article show(Long id) { //특정 데이터 가져오기
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) { //데이터 생성
        Article article = dto.toEntity(); //dto -> 엔티티로 변환 후 article에 저장
        if (article.getId() != null) { //id는 DB가 알아서 생성하기 때문에 데이터 생성 시 굳이 넣을 필요 없음.
            //따라서 article 객체에 id값이 존재한다면 null 반환
            return null;
        }
        return articleRepository.save(article); //article을 DB에 저장
    }

    public Article update(Long id, ArticleForm dto) { //데이터 수정
        //DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        //타깃 조회
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if (target == null || id != article.getId()) { //대상 엔티티가 없거나 수정 요청 id와 본문 id가 다를 경우
            //400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null; //응답은 컨트롤러가 하므로 여기서는 null 반환
        }

        //업데이트하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated; //응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환
    }

    public Article delete(Long id) { //데이터 삭제
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if (target == null) {
            return null; //응답은 컨트롤러가 하므로 여기서는 null 반환
        }

        //대상 삭제
        articleRepository.delete(target);
        return target; //DB에서 삭제한 대상을 컨트롤러에 반환
    }

    /*
        트랜잭션은 보통 서비스에서 관리.
        서비스의 메소드에 @Transactional을 붙이면 해당 메소드는 하나의 트랜잭션으로 묶임
        메소드가 중간에 실패하더라도 롤백을 통해 이전 상태로 돌아갈 수 있음
        Transactional은 org.springframework.transaction.annotation의 것을 import
    */
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음을 엔티티 묶음으로 반환하기
        /* 스트림(stream)문법 사용시
        List<Article> articleList = dtos.stream() //dto를 스트림화
                .map(dto -> dto.toEntity()) //map()으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행하여 매핑
                .collect(Collectors.toList()); //매핑한 것을 리스트로 묶음
        */

        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            ArticleForm dto = dtos.get(i);
            Article entity = dto.toEntity();
            articleList.add(entity);
        }

        //2. 엔티티 묶음을 DB에 저장하기
        /* 스트림(stream)문법 사용시
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        */

        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            articleRepository.save(article);
        }

        //3. 강제 예외 발생시키기
        articleRepository.findById(-1L) //findById()로 id가 -1인 데이터를 찾아 강제로 예외 발생시키기(-1인 id는 없음)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        //orElseThrow() 메소드는 값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 전달값으로 보낸 예외를 발생시킴.
        //IllegalArgumentException은 전달값이 없거나 유효하지 않은 경우를 뜻함

        //4. 결과 값 반환하기
        return articleList;
    }

}
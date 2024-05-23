package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entitiy.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j //로깅 기능을 위한 어노테이션 추가(lombok 사용시) 로그를 찍을때는 log.info()문 사용
@Controller
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해 놓은 레포지토리 주입(DI), ArticleRepository는 인터페이스지만 스프링의 IOC 컨테이너에서 자동으로 객체를 만들어 넣어줌
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString()); //로깅 코드 추가

        // 1.DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString()); //로깅 코드 추가

        // 2.레포지토리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString()); //로깅 코드 추가

        return "";
    }

}

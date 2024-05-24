package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entitiy.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

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

    @GetMapping("/articles/{id}") //중괄호{} 안의 값은 변수로 사용한다는 뜻
    public String show(@PathVariable Long id, Model model) { //@PathVariable은 url 요청으로 들어온 전달 값을 컨트롤러의 매개변수로 가져오는 어노테이션
        //매개변수로 id 받아오기

        log.info("id = " + id); //id를 잘 받았는지 확인하는 로그

        //id를 조회하여 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //id 값으로 데이터를 찾을 때 해당 id 값이 없으면 null을 반환하라는 뜻

        //모델에 데이터 등록(MVC 패턴에 따라 데이터를 뷰 페이지에서 사용하기 위함)
        model.addAttribute("article", articleEntity);

        //뷰 페이지 반환
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        //모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        //모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        return "articles/index";
    }

}

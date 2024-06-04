package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entitiy.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j //로깅 기능을 위한 어노테이션 추가(lombok 사용시) 로그를 찍을때는 log.info()문 사용
@Controller
public class ArticleController {

    @Autowired //스프링 부트가 미리 생성해 놓은 레포지토리 주입(DI), ArticleRepository는 인터페이스지만 스프링의 IOC 컨테이너에서 자동으로 객체를 만들어 넣어줌
    private ArticleRepository articleRepository;

    @Autowired //서비스 객체 주입
    private CommentService commentService;

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

        return "redirect:/articles/" + saved.getId(); //리다이렉트 (return "redirect:URL_주소";)
    }

    @GetMapping("/articles/{id}") //중괄호{} 안의 값은 변수로 사용한다는 뜻
    public String show(@PathVariable Long id, Model model) { //@PathVariable은 url 요청으로 들어온 전달 값을 컨트롤러의 매개변수로 가져오는 어노테이션
        //매개변수로 id 받아오기

        log.info("id = " + id); //id를 잘 받았는지 확인하는 로그

        //id를 조회하여 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //id 값으로 데이터를 찾을 때 해당 id 값이 없으면 null을 반환하라는 뜻

        List<CommentDto> commentDtos = commentService.comments(id);

        //모델에 데이터 등록(MVC 패턴에 따라 데이터를 뷰 페이지에서 사용하기 위함)
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos); //댓글 목록 모델에 등록

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

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        //뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) { //매개변수로 DTO 받아오기
        log.info(form.toString());

        //1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        //2. 엔티티를 DB에 저장하기
        //2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        //2-2. 기존 데이터 값을 갱신하기
        if (target != null) {
            articleRepository.save(articleEntity); //엔티티를 DB에 저장(갱신)
        }

        //3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes rttr) { //RedirectAttributes는 리다이렉트된 페이지에서 사용할 일회성 데이터를 등록할 객체
        log.info("삭제 요청이 들어왔습니다!");

        //삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        //대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!"); //리다이렉트 시점에 한번만 사용할 데이터를 등록
        }

        //결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }

}

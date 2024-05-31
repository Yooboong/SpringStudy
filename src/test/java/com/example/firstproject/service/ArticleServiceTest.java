package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entitiy.Article;
import org.junit.jupiter.api.Test; //Test 패키지 import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; //assertEquals(expected, actual)을 사용하기위한 패키지 import

@SpringBootTest //해당 클래스를 스프링부트와 연동해 테스트
class ArticleServiceTest {

    @Autowired
    ArticleService articleService; //articleService 객체 주입

    @Test //해당 메소드가 테스트 코드임을 선언
    void index() {
        //1. 예상 데이터 작성하기
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
//        Article c = new Article(4L, "다다다다", "3333"); //일치하지 않는경우 테스트
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        /*
        Arrays.asList() 메소드는 입력된 배열 또는 2개 이상의 동일한 타입 데이터를
        정적 리스트로 만들어 반환.
        정적 리스트는 고정 크기이므로 add()나 remove() 메소드를 사용할 수 없음.

        만약 정적 리스트에 add()나 remove() 메소드를 사용하려면,
        위와 같이 정적 리스트를 새 ArrayList로 만들어야함.
        */

        //2. 실제 데이터
        List<Article> articles = articleService.index();

        //3. 비교 및 검증
        assertEquals(expected.toString(), articles.toString());
        /*
        assertEquals(expected, actual)
        기대값과 실제값이 같은지 검증하는 메소드
        */
    }

    @Test
    void showSuccess() {
        //1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가","1111");
        //2. 실제 데이터
        Article article = articleService.show(id);
        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void showFail() { //실패 case
        //1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        //2. 실제 데이터
        Article article = articleService.show(id);
        //3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional //테스트가 끝나면 롤백
    /*
    데이터를 조회(Read)하는 경우를 제외한 생성(Create), 수정(Update), 삭제(Delete)하는 테스트를 할 때는
    반드시 해당 테스트를 트랜잭션으로 묶어 테스트가 종료된 후 원래대로 돌아갈 수 있게    롤백 처리를 해야한다.
    */
    void createSuccess() { //title과 content만 있는 dto를 입력했을 때
        //1. 예상 데이터
        String title = "라라라라"; //title 값 임의 배정
        String content = "4444"; //content 값 임의 배정
        ArticleForm dto = new ArticleForm(null, title, content); //dto 생성
        Article expected = new Article(4L, title, content); //예상 데이터 저장
        //2. 실제 데이터
        Article article = articleService.create(dto); //실제 데이터 저장
        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString()); //비교
    }

    @Test
    @Transactional //테스트가 끝나면 롤백
    void createFail() { //id가 포함된 dto를 입력했을 때
        //1. 예상 데이터
        Long id = 4L; //id 값 임의 배정
        String title = "라라라라"; //title 값 임의 배정
        String content = "4444"; //content 값 임의 배정
        ArticleForm dto = new ArticleForm(id, title, content); //dto 생성
        Article expected = null; //예상 데이터 저장
        //2. 실제 데이터
        Article article = articleService.create(dto); //실제 생성 결과 저장
        //3. 비교 및 검증
        assertEquals(expected, article); //비교
    }

    @Test
    @Transactional
    void updateSuccessCase1() { //id, title, content 모두 존재하는 dto 입력
        //1. 예상 데이터
        Long id = 1L;
        String title = "가나다라";
        String content = "1234";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);
        //2. 실제 데이터
        Article article = articleService.update(id, dto);
        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void updateSuccessCase2() { //id, title만 존재하는 dto 입력
        //1. 예상 데이터
        Long id = 1L;
        String title = "AAAA";
        String content = null;
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, "1111");
        //2. 실제 데이터
        Article article = articleService.update(id, dto);
        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void updateFail() { //존재하지 않는 id의 dto 입력
        //1. 예상 데이터
        Long id = -1L;
        String title = "가나다라";
        String content = "1234";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        //2. 실제 데이터
        Article article = articleService.update(id, dto);
        //3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void deleteSuccess() { //존재하는 id 입력
        //1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        //2. 실제 데이터
        Article article = articleService.delete(id);
        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void deleteFail() { //존재하지 않는 id 입력
        //1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        //2. 실제 데이터
        Article article = articleService.delete(id);
        //3. 비교 및 검증
        assertEquals(expected, article);
    }

}
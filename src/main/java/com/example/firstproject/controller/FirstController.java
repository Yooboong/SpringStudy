package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi") //url (/hi) 읽는다
    public String niceToMeetYou(Model model) { //model 객체 받아오기
        model.addAttribute("username", "유붕");
        return "greetings"; //반환할 템플릿파일 이름
    }

    @GetMapping("/bye") //url (/bye) 읽는다
    public String goodBye(Model model) { //url상에서 localhost:8080/bye?username=이름 으로 작성

        model.addAttribute("username", "유붕");
        return "goodbye"; //반환할 템플릿파일 이름
    }

    /*
    @GetMapping("/bye") //url (/bye) 읽는다
    public String goodBye(@RequestParam("username") String username, Model model) { //url상에서 localhost:8080/bye?username=이름 으로 작성
        model.addAttribute("username", username);
        return "goodbye"; //반환할 템플릿파일 이름
    }
    */

}

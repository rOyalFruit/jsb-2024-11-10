package com.mysite.sbb.global.initData;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseInitData {

    private final QuestionService questionService;

    @Bean
    public ApplicationRunner BaseInitDataApplicationRunner(){
        return args -> {
            if(questionService.count() > 0) return;

            Question question1 = questionService.create("제목1", "내용1");
            Question question2 = questionService.create("제목2", "내용2");
        };
    }
}

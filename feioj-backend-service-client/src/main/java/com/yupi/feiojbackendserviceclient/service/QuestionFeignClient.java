package com.yupi.feiojbackendserviceclient.service;


import com.yupi.feiojbackendmodel.model.entity.Question;
import com.yupi.feiojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @author 1208628168
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-03-25 18:20:17
*/
@FeignClient(name = "feioj-backend-question-service",path = "/api/question/inner")
public interface QuestionFeignClient{

    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionsubmitId") long questionsubmitId);

    @PostMapping("question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}

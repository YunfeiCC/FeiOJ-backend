package com.yupi.feiojbackendquestionservice.controller.inner;

import com.yupi.feiojbackendmodel.model.entity.Question;
import com.yupi.feiojbackendmodel.model.entity.QuestionSubmit;
import com.yupi.feiojbackendquestionservice.service.QuestionService;
import com.yupi.feiojbackendquestionservice.service.QuestionSubmitService;
import com.yupi.feiojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 该服务仅供内部调用
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId){
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionsubmitId") long questionsubmitId){
        return questionSubmitService.getById(questionsubmitId);
    }

    @Override
    @PostMapping("question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }

}

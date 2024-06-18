package com.yupi.feiojbackendjudgeservice.judge;


import com.yupi.feiojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId 题目id
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}

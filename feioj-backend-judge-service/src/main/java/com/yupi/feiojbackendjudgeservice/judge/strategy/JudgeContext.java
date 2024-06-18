package com.yupi.feiojbackendjudgeservice.judge.strategy;


import com.yupi.feiojbackendmodel.model.codesandbox.JudgeInfo;
import com.yupi.feiojbackendmodel.model.dto.question.JudgeCase;
import com.yupi.feiojbackendmodel.model.entity.Question;
import com.yupi.feiojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（定义传参）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputlist;

    private List<String> outputlist;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;
}

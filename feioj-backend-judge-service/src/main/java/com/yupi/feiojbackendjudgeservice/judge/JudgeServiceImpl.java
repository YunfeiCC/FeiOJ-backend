package com.yupi.feiojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.feiojbackendcommon.common.ErrorCode;
import com.yupi.feiojbackendcommon.exception.BusinessException;
import com.yupi.feiojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yupi.feiojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.yupi.feiojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.yupi.feiojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yupi.feiojbackendjudgeservice.judge.strategy.JudgeManager;
import com.yupi.feiojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.yupi.feiojbackendmodel.model.codesandbox.JudgeInfo;
import com.yupi.feiojbackendmodel.model.dto.question.JudgeCase;
import com.yupi.feiojbackendmodel.model.entity.Question;
import com.yupi.feiojbackendmodel.model.entity.QuestionSubmit;
import com.yupi.feiojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.yupi.feiojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目信息不存在");
        }
        //如果题目状态不为等待中
        if(!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WATTING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean result = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新失败");
        }
        //调用代码沙箱 获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        //获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputlist(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputlist = executeCodeResponse.getOutputlist();

        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeinfo());
        judgeContext.setInputlist(inputList);
        judgeContext.setOutputlist(outputlist);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //修改数据库中判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        result = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新失败");
        }
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        return questionSubmitResult;
    }
}

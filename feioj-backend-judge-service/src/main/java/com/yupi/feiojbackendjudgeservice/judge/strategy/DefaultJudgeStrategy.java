package com.yupi.feiojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yupi.feiojbackendmodel.model.codesandbox.JudgeInfo;
import com.yupi.feiojbackendmodel.model.dto.question.JudgeCase;
import com.yupi.feiojbackendmodel.model.dto.question.JudgeConfig;
import com.yupi.feiojbackendmodel.model.entity.Question;
import com.yupi.feiojbackendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputlist = judgeContext.getInputlist();
        List<String> outputlist = judgeContext.getOutputlist();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
        List<String> ansList = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();

        JudgeInfo judgeInforesponse = new JudgeInfo();
        judgeInforesponse.setMemory(memory);
        judgeInforesponse.setTime(time);
        judgeInforesponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());


        //根局沙箱执行结果，设置题目判题状态信息
        if (outputlist.size() != ansList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInforesponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInforesponse;
        }
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (judgeCase.getOutput().equals(outputlist.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInforesponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInforesponse;
            }
        }
        //判断题目限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long timeLimit = judgeConfig.getTimeLimit();
        if(memory>memoryLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInforesponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInforesponse;
        }
        if(time>timeLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInforesponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInforesponse;
        }
        judgeInforesponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInforesponse;
    }
}

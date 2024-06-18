package com.yupi.feiojbackendquestionservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.feiojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.feiojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yupi.feiojbackendmodel.model.entity.QuestionSubmit;
import com.yupi.feiojbackendmodel.model.entity.User;
import com.yupi.feiojbackendmodel.model.vo.QuestionSubmitVO;

/**
* @author 1208628168
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-03-25 18:21:57
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginuser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginuser);

    /**
     * 分页获取帖子封装
     *
     * @param questionSubmit
     * @param loginuser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmit, User loginuser);
}

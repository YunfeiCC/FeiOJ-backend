package com.yupi.feiojbackendmodel.model.dto.questionsubmit;

import com.yupi.feiojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询提交题目请求
 *
 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目id
     */
    private Long questionId;

    /**
     * 用户id
     */
    private Long UserId;

    private static final long serialVersionUID = 1L;
}
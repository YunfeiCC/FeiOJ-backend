package com.yupi.feiojbackendmodel.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    private List<String> outputlist;

    /**
     * 接口信息
     */
    private String message;

    /**
     * 执行状态
     */
    private String status;

    /**
     * 程序执行信息
     */
    private JudgeInfo judgeinfo;

}

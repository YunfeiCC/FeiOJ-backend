package com.yupi.feiojbackendjudgeservice.judge.codesandbox;


import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandbox {


    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

package com.yupi.feiojbackendjudgeservice.judge.codesandbox.impl;


import com.yupi.feiojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        return null;
    }
}

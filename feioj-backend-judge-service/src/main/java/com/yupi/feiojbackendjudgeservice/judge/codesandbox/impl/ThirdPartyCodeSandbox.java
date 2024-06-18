package com.yupi.feiojbackendjudgeservice.judge.codesandbox.impl;


import com.yupi.feiojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.yupi.feiojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}

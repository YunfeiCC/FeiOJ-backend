package com.yupi.feiojbackendjudgeservice.judge.codesandbox;


import com.yupi.feiojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yupi.feiojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yupi.feiojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂 根局字符串参数创建指定代码沙箱实例
 */
public class CodeSandboxFactory {


    /**
     * 创建代码沙箱实例
     * @param type 代码沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}

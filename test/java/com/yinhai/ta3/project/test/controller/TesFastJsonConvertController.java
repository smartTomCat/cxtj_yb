package com.yinhai.ta3.project.test.controller;

import com.yinhai.core.app.ta3.web.controller.BaseController;
import com.yinhai.modules.org.api.vo.user.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * maxp on 2017/4/20. 測試FastJson（1.2.28版本）結合SpringMVC的消息轉換
 */
@Controller
public class TesFastJsonConvertController extends BaseController {

    //返回UserVo的JSON数据
    @ResponseBody
    @RequestMapping("tesFastJsonConvertController!writeJsonToClient.do")
    public UserVo writeJsonToClient(){
        UserVo userVo = new UserVo();
        userVo.setCreatetime(new Date());//日期
        userVo.setCreateuser(123L);//數字
        userVo.setDepartId("技術研發部");//字符串
        userVo.setPasswordfaultnum(new Integer(2));
        return userVo;
    }
}

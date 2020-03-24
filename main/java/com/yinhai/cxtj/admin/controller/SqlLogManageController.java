package com.yinhai.cxtj.admin.controller;

import com.yinhai.core.app.ta3.web.controller.BaseController;
import com.yinhai.core.common.api.util.DateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.controller.CxtjBaseController;
import com.yinhai.cxtj.admin.service.SqlLogManageService;
import com.yinhai.sysframework.persistence.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhaohs on 2019/7/3.
 */
@Controller
@RequestMapping("/sqlLogManage")
public class SqlLogManageController extends CxtjBaseController{

    @Resource
    SqlLogManageService sqlLogManageService;

    @RequestMapping("sqlLogManageController.do")
    public String execute() throws Exception {
        List ztList= sqlLogManageService.queryZt(getTaDto());
        setSelectInputList("yzb612",ztList);
        return "cxtj/admin/sqllogmg/sqlLogMg";
    }

    @RequestMapping("sqlLogManageController!queryFaByZt.do")
    public String queryFaByZt() throws Exception {
        TaParamDto dto = getTaDto();
        List faList= sqlLogManageService.queryFa(dto);
        setSelectInputList("yzb711",faList);
        return JSON;
    }

    @RequestMapping("sqlLogManageController!queryLog.do")
    public String queryLog() throws Exception {
        TaParamDto dto = getTaDto();
        dto.put("yzb612",dto.getAsString("yzb612_desc"));
        dto.put("yzb711",dto.getAsString("yzb711_desc"));
        PageBean pageBean = sqlLogManageService.queryLog(dto);
        setList("grid1",pageBean);
        return JSON;
    }


    @RequestMapping("sqlLogManageController!sqlQueryWindow.do")
    public String sqlQueryWindow() throws Exception {
        TaParamDto dto = getTaDto();
        String errorMsg = dto.get("yzb995").toString().replaceAll("\r\n|\r|\n"," ");
        String[] sqls= dto.getAsString("yzb996").split("\\^"+"\\|"+"\\^");
        String sql1 = sqls[0];
        if(sqls[0].indexOf('\'')!= -1){
            sql1 = sqls[0].replace("'","\"");
        }
        String sql2 = "";
        if(sqls.length > 1){
            sql2 = sqls[1];
            if(sqls[1].indexOf('\'')!= -1){
                sql2 = sqls[1].replace("'","\"");
            }
        }
        if("0".equals(dto.getAsString("yzb994"))){
            setShow("sql1,sql2");
            setData("sql1",sql1);
            setData("sql2",sql2);
        }else if("1".equals(dto.getAsString("yzb994"))){
            setShow("sqlError,sql1,sql2");
            setData("sql1",sql1);
            setData("sql2",sql2);
            setData("sqlError",errorMsg);
        }
        setData(dto,false);
        return "cxtj/admin/sqllogmg/sqlLogQuery";
    }

}

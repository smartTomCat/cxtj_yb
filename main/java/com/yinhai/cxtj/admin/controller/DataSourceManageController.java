package com.yinhai.cxtj.admin.controller;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.controller.CxtjBaseController;
import com.yinhai.cxtj.admin.domain.Zb67Domain;
import com.yinhai.cxtj.admin.service.DataSourceManageService;
import com.yinhai.cxtj.admin.util.Base64Util;
import com.yinhai.sysframework.persistence.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2019/7/3.
 * 数据源配置管理
 */
@Controller
@RequestMapping("/datasource")
public class DataSourceManageController extends CxtjBaseController{

    @Resource
    DataSourceManageService dataSourceManageService;

    @RequestMapping("dataSourceManageController.do")
    public String execute() throws Exception {
        return "cxtj/admin/datasourcemanage/dataSourceManage";
    }

    @RequestMapping("dataSourceManageController!toAdd.do")
    public String toAdd() throws Exception {
        return "cxtj/admin/datasourcemanage/dataSourceEdit";
    }

    /**
     * 查询数据源数据
     * @return
     * @throws Exception
     */
    @RequestMapping("dataSourceManageController!queryList.do")
    public String queryList() throws Exception {
        TaParamDto dto = getTaDto();
        PageBean pageBean = dataSourceManageService.queryList(dto);
        setList("dataSourceGrid",pageBean);
        return JSON;
    }

    @RequestMapping("dataSourceManageController!checkNameExist.do")
    public String checkNameExist() throws Exception{
        TaParamDto dto = getTaDto();
        Boolean b= dataSourceManageService.checkNameExist(dto);
        if(b){
            setMessage("该数据源名称已存在","error");
            setData("yzb672","");
        }
        return JSON;
    }

    @RequestMapping("dataSourceManageController!testConnect.do")
    public String testConnect() throws  Exception{
        TaParamDto dto = getTaDto();
        //替换驱动类名
        dto.put("yzb676",dto.getAsString("yzb676_desc"));
        Boolean b= dataSourceManageService.testConnect(dto);
        if(!b){
            setMessage("连接失败，请检查数据源配置","error");
        }else{
            setMessage("连接成功","success");
            setEnable("save");
        }
        return JSON;
    }

    @RequestMapping("dataSourceManageController!saveDataSource.do")
    public String saveDataSource() throws Exception {
        TaParamDto dto = getTaDto();
        //替换驱动类名
        dto.put("yzb676",dto.getAsString("yzb676_desc"));
        dto.put("yzb674",dto.getAsString("yzb674_desc"));
        dataSourceManageService.saveDataSource(dto);
        setMessage("保存成功！","success");
        setDisabled("save");
        return JSON;
    }



    @RequestMapping("dataSourceManageController!editDataSource.do")
    public String editDataSource() throws Exception {
        TaParamDto dto = getTaDto();
        Map m = dataSourceManageService.queryItemDs(dto);
        //解密
        m.put("yzb679", Base64Util.decodeBase64((String)m.get("yzb679")));
        setData(m,true);
        setReadOnly("save,yzb671,yzb672,yzb674");
        return "cxtj/admin/datasourcemanage/dataSourceEdit";
    }

    /**
     * 更改数据源配置
     * @return
     * @throws Exception
     */
    @RequestMapping("dataSourceManageController!updateDataSource.do")
    public String updateDataSource() throws Exception {
        TaParamDto dto = getTaDto();
        dto.put("yzb676",dto.getAsString("yzb676_desc"));
        dto.put("yzb674",dto.getAsString("yzb674_desc"));
        dataSourceManageService.updateDataSource(dto);
        setMessage("更改成功！","success");
        setDisabled("save");
        return JSON;
    }

    @RequestMapping("dataSourceManageController!deleteDataSource.do")
    public String deleteDataSource() throws Exception {
        List list = getGridSelected("dataSourceGrid");
        Boolean b = dshasZt(list);
        if(b){
            setMessage("请先删除该数据源下的主题！","error");
        }else{
            dataSourceManageService.deleteDataSource(list);
            setMessage("删除成功！","success");
        }
        return JSON;
    }

    /**
     * 判断选择删除的一批数据源中是否存在主题
     * @return
     * @throws Exception
     */
    private boolean dshasZt(List list) throws Exception {
        return dataSourceManageService.dshasZt(list);
    }
}

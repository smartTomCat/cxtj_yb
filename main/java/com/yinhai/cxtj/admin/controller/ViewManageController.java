package com.yinhai.cxtj.admin.controller;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.Constants;
import com.yinhai.cxtj.admin.base.controller.CxtjBaseController;
import com.yinhai.cxtj.admin.service.SetSearchService;
import com.yinhai.cxtj.admin.service.ViewManageService;
import com.yinhai.sysframework.persistence.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2020/3/17.
 * 视图管理Controller
 */
@Controller
@RequestMapping("/view")
public class ViewManageController extends CxtjBaseController {

    @Resource
    SetSearchService setSearchService;

    @Resource
    ViewManageService viewManageService;

    @RequestMapping("viewManageController.do")
    public String execute() throws Exception {
        TaParamDto dto = getTaDto();
        setYzb670selectInput(dto);
        PageBean pageBean = viewManageService.queryViews(dto);
        setList("grid1", pageBean);
        return "cxtj/admin/viewmanage/viewmanage";
    }

    /**
     * 新增|编辑页面
     *
     * @return
     * @throws Exception
     * @author
     */
    @RequestMapping("viewManageController!toEdit.do")
    public String toEdit() throws Exception {
        TaParamDto dto = getTaDto();
        setYzb670selectInput(dto);
        //ta 前端组件textarea 的文本显示内容不能带'' \n 符号， 在此将sql中的'' 批量替换为 ""
        String sql = dto.getAsString("yzb682");
        if (sql.contains("'")) {
            sql = sql.replaceAll("'", "\"");

        }
        if (sql.contains("\n")) {
            sql = sql.replaceAll("\n","  ");
        }
        dto.put("yzb682", sql);
        setData(dto, true);
        return "cxtj/admin/viewmanage/viewEdit";
    }

    /**
     * 条件查询数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("viewManageController!queryViews.do")
    public String queryViews() throws Exception {
        PageBean pageBean = viewManageService.queryViews(getTaDto());
        setList("grid1", pageBean);
        return JSON;
    }

    /**
     * 视图保存
     *
     * @return
     * @throws Exception
     * @author
     */
    @RequestMapping("viewManageController!toSave.do")
    public String saveView() throws Exception {
        TaParamDto dto = getTaDto();
        String yzb680 = viewManageService.toSave(dto);
        setData("yzb680", yzb680);
        setDisabled("form1");
        setMessage("保存成功", "success");
        return JSON;
    }

    /**
     * 视图删除
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("viewManageController!deleteViews.do")
    public String deleteViews() throws Exception {
        TaParamDto dto = getTaDto();
        List list = getGridSelected("grid1");
        viewManageService.toRemove(dto, list);
        setMessage("删除成功", "success");
        return JSON;
    }

    /**
     * 检查视图是否已存在
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("viewManageController!checkNameExist.do")
    public String checkNameExist() throws Exception {
        TaParamDto dto = getTaDto();
        Boolean b = viewManageService.checkNameExist(dto);
        if (b) {
            setMessage("该视图已存在", "error");
            setData("yzb681", "");
        }
        return JSON;
    }

    private void setYzb670selectInput(TaParamDto dto) throws Exception {
        //查询数据源 并赋值下拉选
        List list = setSearchService.queryDataSource(dto);
        //加入框架启动数据源可供选择
        Map m = new HashMap<>();
        m.put("id", Constants.DEFAULT_DS_NO);
        m.put("name", "框架数据源");
        list.add(0, m);
        setSelectInputList("yzb670", list);
    }

}

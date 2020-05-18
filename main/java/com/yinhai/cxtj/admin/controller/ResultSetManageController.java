package com.yinhai.cxtj.admin.controller;

import com.yinhai.core.app.api.util.JSonFactory;
import com.yinhai.core.common.api.dto.IDto;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaBaseDto;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.Constants;
import com.yinhai.cxtj.admin.base.controller.CxtjBaseController;
import com.yinhai.cxtj.admin.service.ResultSetManageService;
import com.yinhai.cxtj.admin.service.SetSearchService;
import com.yinhai.sysframework.persistence.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/resultset")
public class ResultSetManageController extends CxtjBaseController {

    @Resource
    ResultSetManageService resultSetManageService;

    @Resource
    SetSearchService setSearchService;

    @RequestMapping("resultSetManageController.do")
    public String execute() throws Exception {
        TaParamDto dto = getTaDto();
        setYzb670selectInput(dto);
        return "cxtj/admin/resultsetmanage/resultsetmanage";
    }

    /**
     * 查询
     * @return
     * @throws Exception
     */
    @RequestMapping("resultSetManageController!queryResultSets.do")
    public String queryResultSets() throws Exception {
        PageBean pageBean = resultSetManageService.queryResultSets(getTaDto());
        setList("grid1",pageBean);
        return JSON;
    }

    /**
     * 新增|编辑页面
     *
     * @return
     * @throws Exception
     * @author
     */
    @RequestMapping("resultSetManageController!toEdit.do")
    public String toEdit() throws Exception {
        TaParamDto dto = getTaDto();
        setYzb670selectInput(dto);
        setData("yzb690",dto.getAsString("yzb690"));
        return "cxtj/admin/resultsetmanage/resultsetedit";
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("resultSetManageController!queryEditAll.do")
    public String queryEditAll () throws Exception {
        TaParamDto dto = getTaDto();
        List<Map> zb69List= resultSetManageService.queryZb69ByYzb690(dto.getAsString("yzb690"));
        setData(zb69List.get(0),true);
        List<Map> zb89List= resultSetManageService.queryZb89ByYzb690(dto.getAsString("yzb690"));
        setList("grid1",zb89List);
        return JSON;
    }

    /**
     * 查询对应数据源的实体表
     * @return
     * @throws Exception
     */
    @RequestMapping("resultSetManageController!genTableTre.do")
    public String genTableTree() throws Exception {
        TaParamDto dto = getTaDto();
        if (ValidateUtil.isEmpty(dto.getAsString("yzb670")) || ValidateUtil.isEmpty(dto.getAsString("yzb672"))) {
            setMessage("未获取到数据源信息,请核实选择","warn");
        }else{
            List tableTreeList = resultSetManageService.genTableTree(dto.getAsString("yzb670"),dto.getAsString("yzb672"));
            setData("treeData",tableTreeList);
        }
        return JSON;
    }


    /**
     * 查询实体表列信息
     * @return
     * @throws Exception
     */
    @RequestMapping("resultSetManageController!queryTableColumns.do")
    public String queryTableColumns() throws Exception {
        TaParamDto dto = getTaDto();
        if (ValidateUtil.isEmpty(dto.getAsString("yzb670"))) {
            setMessage("未获取到相应数据源信息","warn");
            return JSON;
        }
        if (ValidateUtil.isEmpty(dto.getAsStringArray("tables[]"))) {
            setMessage("未获取已选择的表信息","warn");
            return JSON;
        }
        List columnsList = resultSetManageService.queryTableColumns(dto.getAsString("yzb670"),dto.getAsStringArray("tables[]"));
        setList("grid1",columnsList);
        return JSON;
    }

    /**
     * 保存
     * @return
     * @throws Exception
     */
    @RequestMapping("resultSetManageController!saveResultSet.do")
    public String saveResultSet() throws Exception {
        TaParamDto dto = getTaDto();
        List<IDto> columnList = (List<IDto>) getGridSelected("grid1");
        if (ValidateUtil.isNotEmpty(columnList)) {
            for (int i = 0; i < columnList.size(); i++) {
                for (int j = 0; j <columnList.size() ; j++) {
                    if ( i!=j && columnList.get(i).getAsString("yzb892").equals(columnList.get(j).getAsString("yzb892"))) {
                        setMessage("存在重复字段："+columnList.get(j).getAsString("yzb892"),"warn");
                        return JSON;
                    }
                }
            }
            resultSetManageService.saveResultSet(dto, (List)getGridSelected("grid1"));
        }
        setMessage("保存成功", "success");
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

    /**
     * 删除数据集
     * @return
     * @throws Exception
     */
    @RequestMapping("resultSetManageController!deleteResultSets.do")
    public String deleteResultSets() throws Exception {
        TaParamDto dto = getTaDto();
        List list = getGridSelected("grid1");
        resultSetManageService.deleteResultSets(list);
        setMessage("删除成功", "success");
        return JSON;
    }

    /**
     * 数据集名称查重
     * @return
     * @throws Exception
     */
    @RequestMapping("resultSetManageController!checkNameExist.do")
    public String checkNameExist() throws Exception {
        if (ValidateUtil.isNotEmpty(getTaDto().getAsString("yzb691"))) {
            boolean b = resultSetManageService.checkNameExist(getTaDto().getAsString("yzb691"));
            if (b) {
                setMessage("该数据集名称已存在", "error");
                setData("yzb681", "");
            }
        }
         return JSON;
    }

}

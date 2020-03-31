package com.yinhai.cxtj.front.controller;

import com.alibaba.fastjson.JSONArray;
import com.yinhai.core.app.api.util.JSonFactory;
import com.yinhai.core.app.api.web.resultbaen.IResultBean;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.controller.CxtjBaseController;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.ApptreecodeDomain;
import com.yinhai.cxtj.admin.domain.Zb61Domain;
import com.yinhai.cxtj.admin.domain.Zb62Domain;
import com.yinhai.cxtj.admin.domain.Zb99Domain;
import com.yinhai.cxtj.admin.service.TreeCodeService;
import com.yinhai.cxtj.front.Constants;
import com.yinhai.cxtj.front.domain.Zb71Domain;
import com.yinhai.cxtj.front.service.*;
import com.yinhai.cxtj.front.util.ExportExcel;
import com.yinhai.sysframework.persistence.ibatis.IDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.*;

import static com.yinhai.modules.codetable.api.util.CodeTableUtil.getCodeList;

/**
 * 自定义查询
 *
 * @author: 银海人事人才项目组(XXX)
 * @Copyright: 2016-2017 银海软件所有
 * @date: 2016-07-15 下午7:38:10
 * @vesion: 1.0
 */
@Controller
@RequestMapping("/query")
public class CustomizeQueryController extends CxtjBaseController {
    @Resource
    CustomizeQueryService customizeQueryService;
    @Resource
    CxtjBaseService cxtjBaseService;
    @Resource
    TreeCodeService treeCodeService;
    @Resource
    OracleCustomizeQueryService oracleCustomizeQueryService;
    @Resource
    MysqlCustomizeQueryService mysqlCustomizeQueryService;
    @Resource
    PgCustomizeQueryService pgCustomizeQueryService;
    @Resource
    Gbase8aCustomizeQueryService gbase8aCustomizeQueryService;
    @Resource
    CommonCustomizeQueryService commonCustomizeQueryService;

    @RequestMapping("customizeQueryAction.do")
    public String execute() throws Exception {
        TaParamDto dto = getTaDto();
        //查询项目列表(作为查询条件)
        dto.put("yzb611", dto.getAsString("ztdm"));
        //根据主题查询yzb617 是统计还是查询
        Zb61Domain zb61= customizeQueryService.getDomainObjectByYzb611(dto.getAsString("yzb611"));
        //查询统计标识
        String yzb617;
        if(ValidateUtil.isEmpty(zb61)){
            //主题已被删除 进入相应菜单 在页面给与提示
            return "cxtj/front/query/customizeQueryMain";
        }else{
            HttpServletRequest  request =  getRequest();
            request.setAttribute("ztxmList", customizeQueryService.getCxztxmList(dto));
            //查询可统计项目
            request.setAttribute("enableTjItems", customizeQueryService.getKtjcxztxmList(dto));
            //查询可分组计算的指标项目
            request.setAttribute("tjzbhsList", customizeQueryService.getFzzbxmList(dto));
            //查询默认分组计算的项目、默认统计方式（可分组计算的指标项目）
            request.setAttribute("mrfzjsxmList", customizeQueryService.getMrfzjsxmList(dto));
            //查询可排序的项目(明细用)
            request.setAttribute("orderXms", customizeQueryService.getEnablePxxmList(dto));
            //设置基础条件环境变量
            setBaseCondition();
            setData(dto, false);
            yzb617 = zb61.getYzb617();
            setData("yzb617",yzb617);
            setData("yzb610",zb61.getYzb610());
            setData("yzb612",zb61.getYzb612());
            setData("yzb670",zb61.getYzb670());
        }
        if(Constants.YZB617_1.equals(yzb617)){
            return "cxtj/front/query/customizeQueryMain";
        }else if (Constants.YZB617_2.equals(yzb617)) {
            return "cxtj/front/query/customizeQueryMain_count";
        }else{
            return "cxtj/front/query/customizeQueryMain";
        }
    }

    //设置基础条件环境变量值
    private void setBaseCondition() {
        TaParamDto dto = getTaDto();
        Map map = new HashMap();
        //map.put("aae017", ExtUserUtil.getAae017(dto));//经办机构
        map.put("yae116", dto.getUser().getUserId());//经办人Id
        //map.put("yb0162", ExtUserUtil.getYb0162(dto));//行政区划
        //map.put("b0100", ExtUserUtil.getB0100(dto));//单位Id
        map.putAll(dto.getDtoAsMap());
        String json = JSonFactory.bean2json(map);
        if (ValidateUtil.isNotEmpty(json))
//            setData("jctj", json.replaceAll("\"", "'"));//基础条件
            setData("jctj", json);//基础条件 change by zhaohs
    }

    /**
     * 导出当前excel数据
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!exportExcel.do")
    public String exportExcel() throws Exception{
        TaParamDto dto = getTaDto();
        String parm = dto.getAsString("parm");
        List list = JSONArray.parseArray(parm);
        List listResult = new ArrayList();
        listResult = setDgHead(listResult,list);
        HttpServletResponse response = getResponse();
        ExportExcel.downloadExcel(null,"export.xlsx","",response,listResult);
        return null;
    }

    /**
     * 导出所有统计数据
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!exportExcelAll.do")
    public String exportExcelAll() throws Exception {
        TaParamDto dto = getTaDto();
        String parm = dto.getAsString("parm");
        String xml = dto.getAsString("xml");
        dto.put("limit", 50000);
        dto.put("start", 0);
        if (ValidateUtil.isNotEmpty(xml)) {
            Map outputDomain = getOutputDomain_queryPage(dto.getAsString("yzb670"), dto);
            if ("-1".equals(outputDomain.get("appCode"))) {
                setSuccess(false);
                setMessage((String) outputDomain.get("appMsg"));
                return JSON;
            }
            List listResult = new ArrayList();
            //表头处理
            List listDgHead = JSONArray.parseArray(parm);
            listResult = setDgHead(listResult,listDgHead);
            //数据处理
            List<Map> listDgData = (List<Map>) outputDomain.get("list");
            listResult = setDgData(listResult,listDgData);

            HttpServletResponse response = getResponse();
            ExportExcel.downloadExcel(null,"export.xlsx","",response,listResult);
        }
        return null;
    }

    /**
     * 导出所有详细数据
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!exportDetailExcelAll.do")
    public String exportDetailExcelAll() throws Exception{
        TaParamDto dto = getTaDto();
        String parm = dto.getAsString("parm");
        //表格数据
        String xml = dto.getAsString("xml");
        dto.put("limit", 50000);
        dto.put("start", 0);
        if (ValidateUtil.isNotEmpty(xml)) {
            Map outputDomain = getOutputDomain_queryDeatail(dto.getAsString("yzb670"),dto);
            if ("-1".equals(outputDomain.get("appCode"))) {
                setSuccess(false);
                setMessage((String) outputDomain.get("appMsg"));
                return JSON;
            }
            List listResult = new ArrayList();
            //表头处理
            List listDgHead = JSONArray.parseArray(parm);
            listResult = setDgHead(listResult,listDgHead);
            //数据处理
            List<Map> listDgData = (List<Map>) outputDomain.get("list");
            listResult = setDgData(listResult,listDgData);
            HttpServletResponse response = getResponse();
            ExportExcel.downloadExcel(null,"export.xlsx","",response,listResult);
        }
        return null;
    }

    /**
     * 表头处理
     * @param listResult
     * @param listDgHead
     * @return listResult
     * @throws Exception
     */
    public List setDgHead(List listResult,List listDgHead) throws Exception {
        //表头处理
        for(int i=0;i<listDgHead.size();i++){
            Map m = (Map)listDgHead.get(i);
            JSONArray ja = JSONArray.parseArray(m.get("v").toString());
            String[] str_ary = new String[ja.size()];
            for(int j=0;j<ja.size();j++){
                str_ary[j] = ja.getString(j);
            }
            ExportExcel.setExcelRowDataOld(listResult,i,0,str_ary);
        }
        return listResult;
    }

    /**
     * 表格数据处理
     * @param listResult
     * @param listDgData
     * @return
     * @throws Exception
     */
    public List setDgData(List listResult,List<Map> listDgData) throws Exception {
        for(int k =0;k<listDgData.size();k++){
            Map m2 = listDgData.get(k);
            String[] str_ary2 = new String[m2.size()];
            int index = 0;
            for(Object o : m2.values()){
                str_ary2[index] = String.valueOf(o);
                index++;
            }
            ExportExcel.setExcelRowDataOld(listResult,k+1,0,str_ary2);
        }
        return listResult;
    }

    /**
     * 获取项目关系符
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!getItemGxf.do")
    public String getItemGxf() throws Exception {
        TaParamDto dto = getTaDto();
        //查询项目关系符信息
        setData("xmgxysfList", customizeQueryService.getXmgxysfList(dto));

        //查询项目详细信息
        Map map = customizeQueryService.getXmxx(dto);
        setData("zb62Map", map);

        //如果平铺表示是码值
        if (map != null) {
            String yzb62a = (String) map.get("yzb62a");
            String yzb628 = (String) map.get("yzb628");
            if (ValidateUtil.isNotEmpty(yzb62a) && "21".equals(yzb62a) && ValidateUtil.isNotEmpty(yzb628)) {
                List codeList = commonCustomizeQueryService.queryCodeList(dto.getAsString("yzb670"),yzb628);
                setData("codeList", codeList);
            }
            if (ValidateUtil.isNotEmpty(yzb62a) && "22".equals(yzb62a) && ValidateUtil.isNotEmpty(yzb628)) {//其它的树
                setData("codeList", treeCodeService.getTreeDataByType(yzb628,dto.getAsString("yzb670")));
            }

        }
        return JSON;
    }

    public String getBase() {
        return getRequest().getContextPath();
    }

    /**
     * 获取项目支持的统计运算
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!getXmzcys.do")
    public String getXmzcys() throws Exception {
        TaParamDto dto = getTaDto();
        //查询项目关系符信息
        setData("xmgxysfList", customizeQueryService.getXmzctjys(dto));
        return JSON;
    }

    /**
     * 点击统计查询按钮 (分页查询)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!queryPage.do")
    public String queryPage() throws Exception {
        TaParamDto dto = getTaDto();
        dto.put("limit", dto.getLimit("queryResultDgd"));
        dto.put("start", dto.getStart("queryResultDgd"));
        String xml = dto.getAsString("xml");
        if (ValidateUtil.isNotEmpty(xml)) {
            Map outputDomain = getOutputDomain_queryPage(dto.getAsString("yzb670"),dto);
            //有错误消息
            if ("-1".equals(outputDomain.get("appCode"))) {
                setSuccess(false);
                setMessage((String) outputDomain.get("appMsg"));
                return JSON;
            }
            List list = (List) outputDomain.get("list");
            if (ValidateUtil.isNotEmpty(list)) {
                setData("queryResultDgd", list);
                Map map = (Map) outputDomain.get("fieldData");
                if (null != map) {
                    setData(map, false);
                }
            } else {
                Map map = (Map) outputDomain.get("fieldData");
                if (null != map) {
                    setData(map, false);
                }
                setMessage("未查询到数据！");
            }
        }
        return JSON;
    }

    /**
     * 点击统计查询按钮 (非分页，图表用) 未用
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!query.do")
    public String query() throws Exception {
        TaParamDto dto = getTaDto();
        String xml = dto.getAsString("xml");
        if (ValidateUtil.isNotEmpty(xml)) {
            Map outputDomain = getOutputDomain_query(dto.getAsString("yzb670"),dto);
            //有错误消息
            if ("-1".equals(outputDomain.get("appCode"))) {
                setSuccess(false);
                setMessage((String) outputDomain.get("appMsg"));
                return JSON;
            }
            List list = (List) outputDomain.get("list");
            if (ValidateUtil.isNotEmpty(list)) {
                setData("queryResultDgd", list);
                Map map = (Map) outputDomain.get("fieldData");
                if (null != map) {
                    setData(map, false);
                }
            } else {
                Map map = (Map) outputDomain.get("fieldData");
                if (null != map) {
                    setData(map, false);
                }
                setMessage("未查询到数据！");
            }
        }
        return JSON;
    }

    /**
     * 点击统计数字查询详细信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!queryDetail.do")
    public String queryDetail() throws Exception {
        TaParamDto dto = getTaDto();

        dto.put("limit", dto.getLimit("deailInfoDgd"));
        dto.put("start", dto.getStart("deailInfoDgd"));

        String xml = dto.getAsString("xml");
        if (ValidateUtil.isNotEmpty(xml)) {

            Map outputDomain = getOutputDomain_queryDeatail(dto.getAsString("yzb670"),dto);

            if ("-1".equals(outputDomain.get("appCode"))) {//有错误消息
                setSuccess(false);
                setMessage((String) outputDomain.get("appMsg"));
                return JSON;
            }

            List list = (List) outputDomain.get("list");
            if (ValidateUtil.isNotEmpty(list)) {
                setData("deailInfoDgd", list);
                Map map = (Map) outputDomain.get("fieldData");
                if (null != map) {
                    setData(map, false);
                }
            } else {
                Map map = (Map) outputDomain.get("fieldData");
                if (null != map) {
                    setData(map, false);
                }
                setMessage("未查询到数据！");
            }
        }
        return JSON;
    }

    /**
     * 到设置分组项目排序页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!toSetGroupByFiledOrder.do")
    public String toSetGroupByFiledOrder() throws Exception {
        TaParamDto dto = getTaDto();
        String json = dto.getAsString("json");
        if (ValidateUtil.isNotEmpty(json)) {
            List<Map> list = JSonFactory.json2bean(URLDecoder.decode(json, "UTF-8"), List.class);
            IResultBean resultBean= setData("fzxms", list);
            Map m = resultBean.getFieldsData();
            getRequest().setAttribute("fzxms",m.get("fzxms"));
        }
        return "cxtj/front/query/setGroupByFiledOrderPage";
    }

    /**
     * 到设置详细字段显示页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!toSetDetailFiledShow.do")
    public String toSetDetailFiledShow() throws Exception {
        return "cxtj/front/query/setDetailFiledShow";
    }

    /**
     * 查询显示字段的详细信息
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!queryDetailFiled.do")
    public String queryDetailFiled() throws Exception {
        TaParamDto dto = getTaDto();
        String json = dto.getAsString("json");
        if (ValidateUtil.isNotEmpty(json)) {
            List<Map> list = JSonFactory.json2bean(URLDecoder.decode(json, "UTF-8"), List.class);
            List l = new ArrayList();
            for (Map m : list) {
                l.add(m.get("ztxmlsh"));
            }
            dto.put("yzb620s", l);
            List _list = customizeQueryService.getItemYsf(dto);
            Map m0 = null;
            String ztxmlsh = "";
            for (Map m : list) {
                ztxmlsh = (String) m.get("ztxmlsh");
                for (int i = 0; i < _list.size(); i++) {
                    m0 = (Map) _list.get(i);
                    if (!ValidateUtil.isEmpty(m0.get("yzb620")) && ztxmlsh.equals(m0.get("yzb620").toString())) {
                        m.put("selfysf", m0.get("yzb641s"));
                        m.put("selfysfz", m0.get("aaa103s"));
                    }
                }
            }
            setList("dgsort", list);
        }
        return JSON;
    }


    /**
     * 到设置详细字段排序页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!toSetDetailFiledOrder.do")
    public String toSetDetailFiledOrder() throws Exception {
        TaParamDto dto = getTaDto();

        String json = dto.getAsString("json");
        if (ValidateUtil.isNotEmpty(json)) {
            List<Map> list = JSonFactory.json2bean(URLDecoder.decode(json, "UTF-8"), List.class);
            //设置的排序数据  change by zhaohs
            IResultBean resultBean= setData("setOrderData", list);
            Map m = resultBean.getFieldsData();
            getRequest().setAttribute("setOrderData", m.get("setOrderData"));
        }

        //查询项目列表
        dto.put("yzb611", dto.getAsString("ztdm"));
        Map baseData = new HashMap();
        baseData.put("orderXms", customizeQueryService.getEnablePxxmList(dto));//需排序的项目
        baseData.put("orderPxfs", getCodeList("YZB652", null));//排序方式列表
        //基础数据 change by zhaohs
        IResultBean resultBean2 = setData("baseData", baseData);
        Map m2 = resultBean2.getFieldsData();
        getRequest().setAttribute("baseData", m2.get("baseData"));
        return "cxtj/front/query/setDetailFiledOrder";
    }

    /**
     * 到保存统计方案页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!toSaveTjfa.do")
    public String toSaveTjfa() throws Exception {
        setData("yzb617",getTaDto().getAsString("yzb617"));
        setData("yzb610",getTaDto().getAsString("yzb610"));
        return "cxtj/front/query/saveTjfaPage";
    }

    /**
     * 到调出统计方案页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!toCalloutTjfa.do")
    public String toCalloutTjfa() throws Exception {
        //查询统计方式 1查询 2统计
        setData("yzb617",getTaDto().getAsString("yzb617"));
        setData("yzb610",getTaDto().getAsString("yzb610"));
        setData("yzb670",getTaDto().getAsString("yzb670"));
        return "cxtj/front/query/calloutTjfaPage";
    }

    /**
     * 保存统计方案
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!save.do")
    public String save() throws Exception {
        TaParamDto dto = getTaDto();
        Zb71Domain zb71Domain = new Zb71Domain();
        String tjfalsh = dto.getAsString("tjfalsh");//方案流水号
        Map outputDomain = null;
        if (ValidateUtil.isNotEmpty(tjfalsh)) {//更新方案
            zb71Domain.setYzb710(new BigDecimal(tjfalsh));

            //change by zhaohs
            dto.put("yzb710", new BigDecimal(tjfalsh));

            outputDomain = customizeQueryService.updateFa(dto);
            if ("-1".equals(outputDomain.get("appCode"))) {
                setMessage("修改方案失败！");
            } else {
                setMessage("修改方案成功！");
            }
        } else {//新增方案
            outputDomain = customizeQueryService.saveFa(dto);
            if ("-1".equals(outputDomain.get("appCode"))) {
                setMessage("保存方案失败！");
            } else {
                setMessage("保存方案成功！");
            }
        }
        return JSON;
    }

    /**
     * 调出统计方案
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!callout.do")
    public String callout() throws Exception {
        TaParamDto dto = getTaDto();
        HttpServletRequest request = getRequest();
        //方案流水号
        String tjfalsh = dto.getAsString("tjfalsh");
        //主題流水号
        String yzb610 = dto.getAsString("yzb610");
        Zb61Domain zb61= customizeQueryService.getDomainObjectByYzb610(yzb610);
        //默认为查询
        String yzb617 = "1";
        if(!ValidateUtil.isEmpty(zb61)){
            yzb617 = zb61.getYzb617();
            setData("yzb612",zb61.getYzb612());
            setData("yzb670",zb61.getYzb670());
            request.setAttribute("yzb670", zb61.getYzb670());
        }
        //查询项目列表
        Map outputDomain = customizeQueryService.getAllData(dto);
        Map map = (Map) outputDomain.get("fieldData");
        setData(dto, false);
        if (null != map) {
            IResultBean resultBean = setData(map, false);
            Map m = resultBean.getFieldsData();
            request.setAttribute("orsMap", m.get("orsMap"));
            request.setAttribute("mxxsxms", m.get("mxxsxms"));
            request.setAttribute("mxpxfss", m.get("mxpxfss"));
            request.setAttribute("tjlx_rows", m.get("tjlx_rows"));
            request.setAttribute("fzxms", m.get("fzxms"));
            setData("tjfalsh", tjfalsh);
        }
        //基础条件
        String jctj = dto.getAsString("jctj");
        if (ValidateUtil.isNotEmpty(jctj)) {
            setData("jctj", URLDecoder.decode(jctj, "UTF-8"));
        }
        setData("yzb617",yzb617);
        setData("yzb610",yzb610);

        if(Constants.YZB617_1.equals(yzb617)){
            return "cxtj/front/query/customizeQueryEdit";
        }else if (Constants.YZB617_2.equals(yzb617)) {
            return "cxtj/front/query/customizeQueryEdit_count";
        }else{
            return "cxtj/front/query/customizeQueryEdit";
        }
    }

    /**
     * 到调出统计方案页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!getTjfaInfo.do")
    public String getTjfaInfo() throws Exception {
        TaParamDto dto = getTaDto();
        dto.put("limit", dto.getLimit("grid"));
        dto.put("start", dto.getStart("grid"));
        dto.put("yae116", dto.getUser().getUserid());
        setList("grid", customizeQueryService.getTjfa(dto));
        return JSON;
    }

    /**
     * 删除统计方案
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("customizeQueryAction!delTjfa.do")
    public String delTjfa() throws Exception {
        TaParamDto dto = getTaDto();
        Map odomian = customizeQueryService.deleteTjfaByYzb710(dto);
        if ("1".equals(odomian.get("appCode"))) {
            setSuccess(true);
            setMessage("删除成功！");
        }
        return JSON;
    }


    /**
     * 根据数据源流水号获取数据库类型
     * @param yzb670
     * @return
     * @throws Exception
     */
    private String  getDsType(String yzb670) throws Exception {
        Connection connection = null;
        try {
            IDao dynamicDao = cxtjBaseService.getDynamicDao(yzb670);
            connection = dynamicDao.getConnection();
            String dsName = connection.getMetaData().getDatabaseProductName();
            if(ValidateUtil.isNotEmpty(dsName)){
                return dsName.toUpperCase();
            }else{
                return Constants.DSTYPE_ORACLE;
            }
        }catch (Exception e){
            throw new AppException("数据源获取失败，请检查数据源配置");
        }finally {
            if(connection != null){
                connection.close();
            }
        }
    }

    /**
     * 根据数据库类型 返回查询结果
     * @param yzb670
     * @return
     */
    private Map getOutputDomain_queryPage(String yzb670,TaParamDto dto) throws Exception{
        String dsType = cxtjBaseService.getDsType(yzb670);
        if (Constants.DSTYPE_ORACLE.equals(dsType)) {
            return oracleCustomizeQueryService.getPageStatisticalData(dto);
        }else if (Constants.DSTYPE_MYSQL.equals(dsType)) {
            return mysqlCustomizeQueryService.getPageStatisticalData(dto);
        }else if (Constants.DSTYPE_POSTGRESQL.equals(dsType)) {
            return pgCustomizeQueryService.getPageStatisticalData(dto);
        }else if (Constants.DSTYPE_GBASE8A.equals(dsType)) {
            return gbase8aCustomizeQueryService.getPageStatisticalData(dto);
        }else{
            return oracleCustomizeQueryService.getPageStatisticalData(dto);
        }

    }

    /**
     * 根据数据库类型 返回查询结果
     * @param yzb670
     * @return
     */
    private Map getOutputDomain_queryDeatail(String yzb670,TaParamDto dto) throws Exception{
        String dsType= cxtjBaseService.getDsType(yzb670);
        if(Constants.DSTYPE_ORACLE.equals(dsType)){
            return oracleCustomizeQueryService.getPageDetailInfoData(dto);
        }else if(Constants.DSTYPE_MYSQL.equals(dsType)) {
            return mysqlCustomizeQueryService.getPageDetailInfoData(dto);
        }else if(Constants.DSTYPE_POSTGRESQL.equals(dsType)){
            return pgCustomizeQueryService.getPageDetailInfoData(dto);
        }else if(Constants.DSTYPE_GBASE8A.equals(dsType)){
            return gbase8aCustomizeQueryService.getPageDetailInfoData(dto);
        }else {
            return oracleCustomizeQueryService.getPageDetailInfoData(dto);
        }

    }

    /**
     * 根据数据库类型 返回查询结果 未用
     * @param yzb670
     * @return
     */
    private Map getOutputDomain_query(String yzb670,TaParamDto dto) throws Exception{
        String dsType= cxtjBaseService.getDsType(yzb670);
        if(Constants.DSTYPE_ORACLE.equals(dsType) ){
            return oracleCustomizeQueryService.getStatisticalData(dto);
        }else if(Constants.DSTYPE_MYSQL.equals(dsType)) {
            return mysqlCustomizeQueryService.getStatisticalData(dto);
        }else if(Constants.DSTYPE_POSTGRESQL.equals(dsType)){
            return pgCustomizeQueryService.getStatisticalData(dto);
        }else if(Constants.DSTYPE_GBASE8A.equals(dsType)){
            return gbase8aCustomizeQueryService.getStatisticalData(dto);
        }else {
            return oracleCustomizeQueryService.getStatisticalData(dto);
        }

    }

}

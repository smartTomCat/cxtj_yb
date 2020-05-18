package com.yinhai.cxtj.front.service.impl;

import com.alibaba.fastjson.JSON;
import com.yinhai.core.app.api.util.JSonFactory;
import com.yinhai.core.app.api.util.ServiceLocator;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.Constants;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.*;
import com.yinhai.cxtj.admin.service.SearchParamService;
import com.yinhai.cxtj.admin.service.TreeCodeService;
import com.yinhai.cxtj.front.domain.*;
import com.yinhai.cxtj.front.service.CommonCustomizeQueryService;
import com.yinhai.modules.codetable.api.domain.vo.AppCodeVo;
import com.yinhai.modules.codetable.api.util.CodeTableUtil;
import com.yinhai.sysframework.persistence.PageBean;
import com.yinhai.sysframework.persistence.ibatis.IDao;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yinhai.modules.codetable.api.util.CodeTableUtil.getCodeList;
import static com.yinhai.modules.codetable.api.util.CodeTableUtil.getDesc;

/**
 * Created by zhaohs on 2019/7/24.
 */
public class CommonCustomizeQueryServiceImpl extends CxtjBaseServiceImpl implements CommonCustomizeQueryService {

    @Resource
    SearchParamService searchParamService;

    @Override
    public List getEnablePxxmList(TaParamDto para) throws Exception {
        String yzb611  = (String)para.getAsString("yzb611");
        if(ValidateUtil.isNotEmpty(yzb611)){
            Map map  = new HashMap();
            map.put("yzb611", yzb611);
            List <Map> list = dao.queryForList("searchparam.getEnablePxxms", map);
            if(ValidateUtil.isNotEmpty(list)){
                return list;
            }
        }
        return null;
    }
    /* 查询默认分组计算的统计项目
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getMrfzjsxmList(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public List getMrfzjsxmList(TaParamDto para) throws Exception {
        String yzb611  = (String)para.getAsString("yzb611");
        if(ValidateUtil.isNotEmpty(yzb611)){
            Map map  = new HashMap();
            map.put("yzb611", yzb611);
            List list1 = new ArrayList();
            list1.add("1");//默认计算的分组项目
            map.put("yzb62cs", list1);
            List <Map> list = dao.queryForList("searchparam.getEnableFzjsxms", map);
            if(ValidateUtil.isNotEmpty(list)){
                Map zb62Map = null;
                for(int i=0;i<list.size();i++){
                    zb62Map = list.get(i);
                    if(zb62Map.get("yzb620")!=null){//查询可支持的统计函数
                        List <Zb64Domain> zb64List =searchParamService.getSearchModeByYZB620((BigDecimal)zb62Map.get("yzb620"));
                        zb62Map.put("collection", zb64List);
                    }
                }
                return list;
            }
        }
        return null;
    }
    @Override
    public List getCxztxmList(TaParamDto para) throws Exception {
        List list = searchParamService.getSearchItemByYZB611((String)para.get("yzb611"));
        return list;
    }
    @Override
    public List getXmgxysfList(TaParamDto para) throws Exception{
        String yzb611  = (String)para.get("yzb611");
        if(ValidateUtil.isNotEmpty(yzb611)){
            List list = searchParamService.getSearchSignByYZB620(new BigDecimal(yzb611));
            return list;
        }
        return null;
    }
    @Override
    public Map getXmxx(TaParamDto para) throws Exception {
        String yzb611  = (String)para.get("yzb611");
        if(ValidateUtil.isNotEmpty(yzb611)){
            Zb62Domain outDomain =searchParamService.getSearchItemByYZB620(new BigDecimal(yzb611));
            if(null!=outDomain){
                return outDomain.toMap();
            }
        }
        return null;
    }
    @Override
    //获取可用于分组的项目
    public List getKtjcxztxmList(TaParamDto para) throws Exception {
        String yzb611  = (String)para.get("yzb611");
        if(ValidateUtil.isNotEmpty(yzb611)){
            Map map  = new HashMap();
            map.put("yzb611", yzb611);
            List <Zb62Domain> list = dao.queryForList("searchparam.getEnableFzxms", map);
            if(null!=list){
                return list;
            }
        }
        return null;
    }
    @Override
    public List getFzzbxmList(TaParamDto para) throws Exception {
        String yzb611  = (String)para.get("yzb611");
        if(ValidateUtil.isNotEmpty(yzb611)){
            Map map  = new HashMap();
            map.put("yzb611", yzb611);

            List list1 = new ArrayList();
            list1.add("1");//默认计算
            list1.add("2");//默认不计算
            map.put("yzb62cs", list1);

            List <Map> list = dao.queryForList("searchparam.getEnableFzjsxms", map);
            if(null!=list){
                return list;
            }
        }
        return null;
    }
    @Override
    public List getXmzctjys(TaParamDto para) throws Exception{
        String yzb620  = (String)para.get("yzb620");
        if(ValidateUtil.isNotEmpty(yzb620)){
            List <Zb64Domain> list =searchParamService.getSearchModeByYZB620(new BigDecimal(yzb620));
            if(null!=list){
                return list;
            }
        }
        return null;
    }





    /**
     * xml2obj(点击查询统计时)
     * @param xml
     * @return
     * @throws Exception
     */
    protected Map xml2Obj(String xml) throws Exception {
        System.out.println(xml);
        Map data = new HashMap();
        List groupFieldList = null,funcitonFieldList = null,showFieldList = null,orList = new ArrayList();//分组字段、函数字段、or条件
        Document doc = DocumentHelper.parseText(xml);
        Element scheme = doc.getRootElement();
        Element statisticsQueryElement = scheme.element("statistics_query");
        Attribute ztdmAttribute = scheme.attribute("ztdm");//主题代码
        Attribute ztlshAttribute = scheme.attribute("ztlsh");//主题流水号
        Attribute tjfalshAttribute = scheme.attribute("tjfalsh");//方案流水号
        Attribute tjfanameAttribute = scheme.attribute("tjfaname");//方案流水号
        Attribute jctjAttribute = scheme.attribute("jctj");//基础条件
        if(null != ztdmAttribute){
            data.put("ztdm",ztdmAttribute.getText());//主题代码
        }
        if(null != ztlshAttribute){
            data.put("ztlsh",ztlshAttribute.getText());//主题流水号
        }
        if(null != tjfalshAttribute){
            data.put("tjfalsh",tjfalshAttribute.getText());//统计方案流水号
        }
        if(null != tjfanameAttribute){
            data.put("tjfaname",tjfanameAttribute.getText());//统计方案名称
        }
        if(null != jctjAttribute){
            data.put("jctj",jctjAttribute.getText());//基础条件值
        }
        Element fields =  statisticsQueryElement.element("fields");

        /***********************分组字段*************************************/
        Element groupField =  fields.element("group_field");
        if(null != groupField){
            groupFieldList = new ArrayList();
            Iterator groupFieldIterator = groupField.elementIterator("field");
            Map groupFieldMap = null;
            while (groupFieldIterator.hasNext()){
                groupFieldMap= new HashMap();
                Element fieldElement = (Element) groupFieldIterator.next();
                groupFieldMap.put("name", fieldElement.attribute("name").getValue());
                groupFieldMap.put("id", fieldElement.attribute("id").getValue());
                groupFieldMap.put("sn", fieldElement.attribute("sn").getValue());
                groupFieldMap.put("ztxmlsh", fieldElement.attribute("ztxmlsh").getValue());
                groupFieldMap.put("datatype", fieldElement.attribute("datatype").getValue());
                groupFieldList.add(groupFieldMap);
                data.put("groupFieldList", groupFieldList);
            }
        }


        /***********************函数字段*************************************/
        Element functionFiled =  fields.element("function_field");
        if(null != functionFiled){
            funcitonFieldList = new ArrayList();
            Iterator functionFiledIterator = functionFiled.elementIterator("field");
            Map functionFiledMap = null;
            Element fieldElement = null;
            while (functionFiledIterator.hasNext()) {
                functionFiledMap= new HashMap();
                fieldElement = (Element) functionFiledIterator.next();
                functionFiledMap.put("name", fieldElement.attribute("name").getValue());
                functionFiledMap.put("id", fieldElement.attribute("id").getValue());
                functionFiledMap.put("sn", fieldElement.attribute("sn").getValue());
                functionFiledMap.put("ztxmlsh", fieldElement.attribute("ztxmlsh").getValue());
                //functionFiledMap.put("datatype", fieldElement.attribute("datatype").getValue());
                functionFiledMap.put("dbtype", fieldElement.attribute("dbtype").getValue());//函数字段数据库类型（生成SQL判空用）
                functionFiledMap.put("tjfs", fieldElement.attribute("tjfs").getValue());     //统计方式
                funcitonFieldList.add(functionFiledMap);
                data.put("funcitonFieldList", funcitonFieldList);
            }
        }

        /***********************页面上设置的显示字段,包括选择的统计方式(弹框设置)*************************************/
        Element showFiled =  fields.element("show_field");//页面上设置的显示字段（查看明细用）
        if(null != showFiled){
            showFieldList = new ArrayList();funcitonFieldList = new ArrayList();
            Iterator showFiledIterator = showFiled.elementIterator("field");
            Map showFiledMap = null,functionFiledMap = null;
            Element fieldElement = null;
            while (showFiledIterator.hasNext()) {
                showFiledMap= new HashMap();
                fieldElement = (Element) showFiledIterator.next();
                showFiledMap.put("name", fieldElement.attribute("name").getValue());
                showFiledMap.put("datatype", fieldElement.attribute("datatype").getValue());
                showFiledMap.put("id", fieldElement.attribute("id").getValue());
                showFiledMap.put("sn", fieldElement.attribute("sn").getValue());
                showFiledMap.put("ztxmlsh", fieldElement.attribute("ztxmlsh").getValue());
                String tjfs = fieldElement.attribute("tjfs").getValue();
                showFiledMap.put("tjfs", tjfs);
                showFiledMap.put("tjfsname", getDesc("YZB641", tjfs, null));    //统计方式中文

                showFiledMap.put("dbtype", fieldElement.attribute("dbtype").getValue());//函数字段数据库类型（生成SQL判空用）
                showFiledMap.put("ischecked", fieldElement.attribute("ischecked").getValue());//是否显示
                showFieldList.add(showFiledMap);//显示字段

                String ischecked = fieldElement.attribute("ischecked").getValue();
                if("true".equals(ischecked)){//显示字段设置了统计的
                    functionFiledMap= new HashMap();
                    functionFiledMap.put("ztxmlsh", fieldElement.attribute("ztxmlsh").getValue());
                    functionFiledMap.put("sn", fieldElement.attribute("sn").getValue());
                    functionFiledMap.put("name", fieldElement.attribute("id").getValue());
                    functionFiledMap.put("tjfs",tjfs);    //统计方式
                    functionFiledMap.put("tjfsname", getDesc("YZB641", tjfs, null));    //统计方式中文
                    functionFiledMap.put("dbtype", fieldElement.attribute("dbtype").getValue());//函数字段数据库类型（生成SQL判空用）
                    funcitonFieldList.add(functionFiledMap);//统计字段
                }
            }
            data.put("showFieldList", showFieldList);
            data.put("showFuncitonFieldList", funcitonFieldList);//设置明细显示字段统计方式
        }

        /***********************where条件*************************************/
        Element whereElement = statisticsQueryElement.element("where");


        /***********************ands查询条件(点击统计数字带的查询条件)*************************************/
        Element _andsElement = whereElement.element("ands");
        if(null != _andsElement){
            Map andMap = null;
            Element andElement = null;
            List andList = new ArrayList();
            Iterator andIterator = _andsElement.elementIterator("and");
            while (andIterator.hasNext()) {
                andMap= new HashMap();
                andElement = (Element) andIterator.next();
                andMap.put("id", andElement.attribute("id").getValue());
                andMap.put("value", andElement.attribute("value").getValue());
                andMap.put("sn", andElement.attribute("sn").getValue());
                andList.add(andMap);
            }
            data.put("andList", andList);
        }

        /***********************ors 查询条件(点击统计数字带的查询条件)*************************************/
        Element orsElement = whereElement.element("ors");
        Iterator orIterator  = orsElement.elementIterator("or");
        Element orElement = null,andsElement = null,andElement = null;
        List andList = null;//存放多个and
        Map andMap = null;
        Map orMap = null;//存放多个or
        while (orIterator.hasNext()) {
            andList = new ArrayList();
            orMap =  new HashMap() ;
            orElement = (Element) orIterator.next();
            orMap.put("sn", orElement.attribute("sn").getValue());
            andsElement = orElement.element("ands");
            Iterator andIterator  = andsElement.elementIterator("and");
            while (andIterator.hasNext()) {
                andMap = new HashMap();
                andElement = (Element) andIterator.next();
                andMap.put("id", andElement.attribute("id").getValue());
                andMap.put("ztxmlsh", andElement.attribute("ztxmlsh").getValue());
                andMap.put("gxysf", andElement.attribute("gxysf").getValue());
                andMap.put("dataType", andElement.attribute("datatype").getValue());
                andMap.put("sn", andElement.attribute("sn").getValue());
                String vtype = andElement.attribute("vtype").getValue();
                andMap.put("vtype", vtype);
                if("1".equals(vtype)){//值类型
                    andMap.put("value", andElement.attribute("value").getValue());
                }else if("2".equals(vtype)){//项目类型
                    andMap.put("vfield", andElement.attribute("vfield").getValue());//选择的字段
                }
                andList.add(andMap);
                orMap.put("andList", andList);
            }
            orList.add(orMap);
        }
        data.put("orList", orList);

        /***********************order 字段(详细信息用)*************************************/
        Element orderElement = statisticsQueryElement.element("orders");
        if(null != orderElement){
            Map orderMap = null;
            Element fieldElement = null;
            List orderList = new ArrayList();
            Iterator fieldIterator = orderElement.elementIterator("field");
            while (fieldIterator.hasNext()) {
                orderMap= new HashMap();
                fieldElement = (Element) fieldIterator.next();
                orderMap.put("name", fieldElement.attribute("name").getValue());
                orderMap.put("id", fieldElement.attribute("id").getValue());
                orderMap.put("sn", fieldElement.attribute("sn").getValue());
                orderMap.put("ztxmlsh", fieldElement.attribute("ztxmlsh").getValue());
                orderMap.put("pxfs", fieldElement.attribute("pxfs").getValue());
                orderMap.put("dbtype", fieldElement.attribute("dbtype").getValue());
                orderMap.put("datatype", fieldElement.attribute("datatype").getValue());

                orderList.add(orderMap);
            }
            data.put("orderList", orderList);
        }

        return data;
    }



    /**
     * 查询sql执行日志表
     * @param dto
     * @param yzb991
     * @param yzb992
     * @param yzb993
     * @param yzb994
     * @param yzb995
     * @param yzb996
     */
    protected void insertZb99(TaParamDto dto,Timestamp yzb991,Timestamp yzb992,Double yzb993,String yzb994,String yzb995,String yzb996) throws Exception{
        Zb99Domain zb99Domain = new Zb99Domain();
        zb99Domain.setYzb990(Integer.valueOf(getStringSeq("seq_yzb990")));
        zb99Domain.setYzb991(yzb991);
        zb99Domain.setYzb992(yzb992);
        zb99Domain.setYzb993(yzb993);
        zb99Domain.setYzb994(yzb994);
        zb99Domain.setYzb995(yzb995);
        zb99Domain.setYzb996(yzb996);
        zb99Domain.setYzb612(dto.getAsString("yzb612"));
        zb99Domain.setYzb711(dto.getAsString("yzb711"));
        zb99Domain.setAae011(dto.getUser().getName());
        zb99Domain.setYae116(Integer.valueOf(dto.getUser().getUserId()));
        zb99Domain.setAae017(Integer.valueOf(dto.getUser().getOrgId()));
        zb99Domain.setAae036(super.getSysTimestamp());
        dao.insert("zb99.insert",zb99Domain);
    }




    /*
     * 根据方案ID删除统计方案
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#deleteTjfaById(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public Map deleteTjfaByYzb710(TaParamDto para) throws Exception {
        Map outputDomain= new HashMap();
        if(para.get("yzb710")!=null){
            Map mapIn = new HashMap();
            mapIn.put("yzb710", para.get("yzb710"));
            dao.delete("zb71.delete", mapIn);//删除方案
            dao.delete("zb72.deleteZb72ByYzb710", mapIn);//删除where条件组
            dao.delete("zb74.deleteZb74ByYzb710", mapIn);//删除where条件的代码值
            dao.delete("zb73.deleteZb73ByYzb710", mapIn);//删除where条件
            dao.delete("zb76.deleteZb76ByYzb710", mapIn);//删除统计项目
            dao.delete("zb77.deleteZb77ByYzb710", mapIn);//删除排序项目
            dao.delete("zb78.deleteZb78ByYzb710", mapIn);//删除分组统计项目
            dao.delete("zb79.deleteZb79ByYzb710", mapIn);//删除分组统计值
        }else{
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", "yzb710不能为空！");
        }
        return outputDomain;
    }
    /*
     * 保存方案
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#saveFa(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.query.domain.Zb71Domain)
     */
    @Override
    public Map saveFa(TaParamDto para) throws Exception{
        Map outputDomain= new HashMap();
        String xml  = (String)para.get("xml");
        Map map = xml2Obj(xml);//xml2obj 对象
        try {
            /********************************保存查询统计方案***********************************/
            BigDecimal yzb710 = null; Timestamp aae036= super.getSysTimestamp();
            if(para.get("yzb710") == null){//如果为空新方案信息
                Zb71Domain zb71Domain  = new Zb71Domain();
                yzb710 = new BigDecimal(super.getSequence("SEQ_YZB710"));//查询统计方案流水号
                String ztdm = (String)map.get("ztdm");
                Zb61Domain zb61Domain  = searchParamService.getSearchByYZB611(ztdm);
                if(null == zb61Domain){
                    return outputDomain;
                }
                zb71Domain.setYzb710(yzb710);
                zb71Domain.setYzb610(zb61Domain.getYzb610());
                zb71Domain.setYzb711((String)map.get("tjfaname"));
                zb71Domain.setYzb617(zb61Domain.getYzb617());//统计类型：分组统计
                zb71Domain.setYzb713("1");//保存期限为永久
                zb71Domain.setAae011(para.getUser().getName());
                zb71Domain.setYae116(para.getUser().getUserid());
                zb71Domain.setAae017(Long.valueOf(para.getUser().getOrgId()));
                zb71Domain.setAae036(aae036);
                dao.insert("zb71.insert", zb71Domain);//保存统计方案
            }else{//如果不为空表示已有方案信息 (保存到已有方案操作时用)
                yzb710 = (BigDecimal)para.get("yzb710");
            }
            List orList  = (List)map.get("orList");
            if(ValidateUtil.isNotEmpty(orList)){
                Zb72Domain zb72Domain = null;Map outMap = null;
                for(int i=0;i<orList.size();i++){
                    outMap = (Map)orList.get(i);
                    /********************************保存where条件组（or条件组）***********************************/
                    zb72Domain = new Zb72Domain();
                    BigDecimal yzb720 = new BigDecimal(super.getSequence("SEQ_YZB720"));
                    zb72Domain.setYzb720(yzb720);
                    zb72Domain.setYzb710(yzb710);
                    zb72Domain.setAae036(aae036);
                    dao.insert("zb72.insert", zb72Domain);//保存where条件组

                    List andList = (List)outMap.get("andList");
                    if(ValidateUtil.isNotEmpty(andList)){
                        List listDomain = new ArrayList();
                        Map andMap =  null;
                        for(int j=0;j<andList.size();j++){
                            andMap = (Map)andList.get(j);
                            /********************************保存统计方案WHERE条件（每个and）***********************************/
                            Zb73Domain zb73Domain =  new Zb73Domain();
                            BigDecimal yzb730 = new BigDecimal(super.getSequence("SEQ_YZB730"));
                            zb73Domain.setYzb730(yzb730);
                            zb73Domain.setYzb710(yzb710);
                            zb73Domain.setYzb720(yzb720);
                            String sn =(String)andMap.get("sn");
                            if(ValidateUtil.isNotEmpty(sn)){
                                zb73Domain.setYzb731(Integer.parseInt(sn));  //分组内排序号
                            }else{
                                zb73Domain.setYzb731(j);
                            }
                            zb73Domain.setYzb620(new BigDecimal((String)andMap.get("ztxmlsh")));//项目流水号
                            zb73Domain.setYzb631((String)andMap.get("gxysf"));
                            String value = (String)andMap.get("value");
                            String vtype = (String)andMap.get("vtype");
                            if("1".equals(vtype)){//为值类型时
                                if("21".equals(andMap.get("dataType")) || "22".equals(andMap.get("dataType")) || "23".equals(andMap.get("dataType"))){//代码值、树、datagrid
                                    if(ValidateUtil.isNotEmpty(value)){
                                        String[] values = value.split(",");
                                        if(null!=values && values.length >0){
                                            /********************************保存统计方案WHERE条件（and的代码值）***********************************/
                                            Zb74Domain zb74Domain = null;
                                            List listDomain1 = new ArrayList();
                                            for(int k=0;k<values.length;k++){
                                                zb74Domain = new Zb74Domain();
                                                zb74Domain.setYzb740(new BigDecimal(super.getSequence("SEQ_YZB740")));
                                                zb74Domain.setYzb730(yzb730);
                                                zb74Domain.setYzb741(values[k].replace("'", ""));//代码值
                                                zb74Domain.setAae036(aae036);
                                                listDomain1.add(zb74Domain);
                                            }
                                            if(ValidateUtil.isNotEmpty(listDomain1)){
                                                dao.insertBatch("zb74.insert",listDomain1);
                                            }
                                        }
                                    }
                                }else{//非码值
                                    zb73Domain.setYzb733((String)andMap.get("value"));//输入框值
                                }
                            }else if("2".equals(vtype)){//为字段类型时
                                zb73Domain.setYzb733((String)andMap.get("vfield"));//下拉选择的字段
                            }
                            zb73Domain.setYzb734(vtype);//值类型  1固定值2项目

                            zb73Domain.setAae036(aae036);
                            listDomain.add(zb73Domain);
                        }
                        if(ValidateUtil.isNotEmpty(listDomain)){
                            dao.insertBatch("zb73.insert",listDomain);//批量保存
                        }
                    }
                }
            }


            /********************************保存统计方案分组统计项目 ZB78 ***********************************/
            List groupFieldList = (List)map.get("groupFieldList");
            if(ValidateUtil.isNotEmpty(groupFieldList)){
                Zb78Domain zb78Domain = null;
                List listDomain = new ArrayList();
                Map groupFieldMap = null;
                String yzb731 = null;
                for(int x=0; x < groupFieldList.size(); x++){
                    groupFieldMap = (Map)groupFieldList.get(x);
                    zb78Domain = new Zb78Domain();
                    BigDecimal yzb780 = new BigDecimal(super.getSequence("SEQ_YZB780"));
                    zb78Domain.setYzb780(yzb780);
                    zb78Domain.setYzb710(yzb710);
                    yzb731 =(String)groupFieldMap.get("sn");//排序号
                    if(ValidateUtil.isNotEmpty(yzb731)){
                        zb78Domain.setYzb781(Integer.parseInt(yzb731));  //排序号
                    }else{
                        zb78Domain.setYzb781(x);
                    }
                    zb78Domain.setYzb620(new BigDecimal((String)groupFieldMap.get("ztxmlsh")));//项目流水号
                    zb78Domain.setAae036(aae036);
                    listDomain.add(zb78Domain);
                }
                if(ValidateUtil.isNotEmpty(listDomain)){
                    dao.insertBatch("zb78.insert",listDomain);
                }
            }


            /********************************保存统计方案分组统计函数 ZB79 ***********************************/
            List functionFieldList = (List)map.get("funcitonFieldList");
            if(ValidateUtil.isNotEmpty(functionFieldList)){
                Map functionFieldMap = null;Zb79Domain zb79Domain = null;String yzb791 = null;
                List listDomain = new ArrayList();
                for(int z=0; z < functionFieldList.size(); z++){
                    functionFieldMap = (Map)functionFieldList.get(z);
                    zb79Domain = new Zb79Domain();
                    BigDecimal yzb790 = new BigDecimal(super.getSequence("SEQ_YZB790"));
                    zb79Domain.setYzb790(yzb790);
                    zb79Domain.setYzb710(yzb710);
                    yzb791 =(String)functionFieldMap.get("sn");//排序号
                    if(ValidateUtil.isNotEmpty(yzb791)){
                        zb79Domain.setYzb791(Integer.parseInt(yzb791));  //排序号
                    }else{
                        zb79Domain.setYzb791(z);
                    }
                    zb79Domain.setYzb620(new BigDecimal((String)functionFieldMap.get("ztxmlsh")));//项目流水号
                    zb79Domain.setYzb641((String)functionFieldMap.get("tjfs"));//统计方式
                    zb79Domain.setAae036(aae036);
                    listDomain.add(zb79Domain);
                }
                if(ValidateUtil.isNotEmpty(listDomain)){
                    dao.insertBatch("zb79.insert",listDomain);//批量保存
                }
            }
            /********************************保存统计方案查询统计的项目 ZB76 （明细信息）***********************************/
            List showFieldList = (List)map.get("showFieldList");
            if(ValidateUtil.isNotEmpty(showFieldList)){//已设置数据
                Map showFieldMap = null;Zb76Domain zb76Domain = null;String yzb761 = null,isChecked = null;
                List listDomain = new ArrayList();
                for(int z=0; z < showFieldList.size(); z++){
                    showFieldMap = (Map)showFieldList.get(z);
                    isChecked = (String)showFieldMap.get("ischecked");//是否显示
                    if("true".equals(isChecked)){//页面上勾选了显示的则保存
                        zb76Domain = new Zb76Domain();
                        zb76Domain.setYzb760(new BigDecimal(super.getSequence("SEQ_YZB760")));
                        zb76Domain.setYzb710(yzb710);//统计方案流水号
                        yzb761 =(String)showFieldMap.get("sn");//排序号
                        if(ValidateUtil.isNotEmpty(yzb761)){
                            zb76Domain.setYzb761(Integer.parseInt(yzb761));  //排序号
                        }else{
                            zb76Domain.setYzb761(z);
                        }
                        zb76Domain.setYzb620(new BigDecimal((String)showFieldMap.get("ztxmlsh")));//项目流水号
                        zb76Domain.setYzb641((String)showFieldMap.get("tjfs"));//统计方式
                        zb76Domain.setAae036(aae036);
                        listDomain.add(zb76Domain);
                    }
                }
                if(ValidateUtil.isNotEmpty(listDomain)){
                    dao.insertBatch("zb76.insert",listDomain);//批量保存
                }
            }else{//没有点击数字查看详情(没生成设置显示字段列表)就从配置表里读取插入
                Map inMap = new HashMap();
                inMap.put("yzb611", (String)map.get("ztdm"));
                List<Map> zb62lst = getDao().queryForList("searchparam.getMrxsls",inMap);//从数据库中查询默认配置显示的列
                if(ValidateUtil.isNotEmpty(zb62lst)){
                    Zb76Domain zb76Domain = null;Map zb62Map = null;
                    List listDomain = new ArrayList();
                    for(int i=0;i< zb62lst.size();i++){
                        zb62Map  = (Map)zb62lst.get(i);
                        zb76Domain = new Zb76Domain();
                        zb76Domain.setYzb760(new BigDecimal(super.getSequence("SEQ_YZB760")));
                        zb76Domain.setYzb710(yzb710);//统计方案流水号
                        if(zb62Map.get("yzb621")!=null){
                            zb76Domain.setYzb761(((BigDecimal)zb62Map.get("yzb621")).intValue());  //排序号
                        }else{
                            zb76Domain.setYzb761(i);
                        }
                        zb76Domain.setYzb620((BigDecimal)zb62Map.get("yzb620"));//项目流水号
                        zb76Domain.setYzb641(null);//统计方式
                        zb76Domain.setAae036(aae036);
                        listDomain.add(zb76Domain);
                    }
                    if(ValidateUtil.isNotEmpty(listDomain)){
                        dao.insertBatch("zb76.insert",listDomain);//批量保存
                    }
                }
            }
            /********************************保存统计方案查询统计的项目 ZB77 （排序项目）***********************************/
            List orderList = (List)map.get("orderList");
            if(ValidateUtil.isNotEmpty(orderList)){
                Map orderMap = null;Zb77Domain zb77Domain = null;String yzb771 = null;
                List listDomain = new ArrayList();
                for(int i=0;i<orderList.size();i++){
                    orderMap = (Map)orderList.get(i);
                    zb77Domain = new Zb77Domain();
                    zb77Domain.setYzb770(new BigDecimal(super.getSequence("SEQ_YZB770")));
                    zb77Domain.setYzb710(yzb710);

                    yzb771 =(String)orderMap.get("sn");//排序号
                    if(ValidateUtil.isNotEmpty(yzb771)){
                        zb77Domain.setYzb771(Integer.parseInt(yzb771));  //排序号
                    }else{
                        zb77Domain.setYzb771(i);
                    }
                    zb77Domain.setYzb620(new BigDecimal((String)orderMap.get("ztxmlsh")));//项目流水号
                    zb77Domain.setYzb652((String)orderMap.get("pxfs"));//排序方式
                    zb77Domain.setAae036(aae036);
                    listDomain.add(zb77Domain);
                }
                if(ValidateUtil.isNotEmpty(listDomain)){
                    dao.insertBatch("zb77.insert",listDomain);//批量保存
                }
            }


        } catch (Exception e) {
            outputDomain.put("appCode","-1");
            outputDomain.put("appMsg","保存方案信息出错！");
            System.out.println(e.getMessage());
            throw new AppException("保存方案信息出错，"+e.getMessage());
        }
        return outputDomain;
    }
    /*
     * 更新方案
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#updateFa(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.query.domain.Zb71Domain)
     */
    @Override
    public Map updateFa(TaParamDto para) throws Exception {
        Map outputDomain= new HashMap();
        if(para.get("yzb710") == null){
            outputDomain.put("appCode","-1");
            outputDomain.put("appMsg","yzb710不能为空！");
            return outputDomain;
        }
        Map mapIn = new HashMap();
        mapIn.put("yzb710", para.get("yzb710"));
        //dao.delete("zb71.delete", mapIn);//删除方案 (以前的老方案信息不需要删除)
        dao.delete("zb72.deleteZb72ByYzb710", mapIn);//删除where条件组
        dao.delete("zb74.deleteZb74ByYzb710", mapIn);//删除where条件的代码值
        dao.delete("zb73.deleteZb73ByYzb710", mapIn);//删除where条件
        dao.delete("zb76.deleteZb76ByYzb710", mapIn);//删除统计项目
        dao.delete("zb77.deleteZb77ByYzb710", mapIn);//删除排序项目
        dao.delete("zb78.deleteZb78ByYzb710", mapIn);//删除分组统计项目
        dao.delete("zb79.deleteZb79ByYzb710", mapIn);//删除分组统计值
        return this.saveFa(para);
    }
    /*
     * 获取项目运算符列表信息
     *  (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getItemYsf(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public List getItemYsf(TaParamDto para) throws Exception {
        List list =  dao.queryForList("zb64.getYsf",para);
        return list;
    }
    /*
     * 调出方案所有数据
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getAllData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.query.domain.Zb71Domain)
     */
    @Override
    public Map getAllData(TaParamDto para) throws Exception {
        Map odomain= new HashMap();
        Map fieldData = new HashMap();
        String yzb710 = (String)para.get("tjfalsh");
        if(ValidateUtil.isEmpty(yzb710)){
            return odomain;
        }
        TreeCodeService treeCodeService = (TreeCodeService) ServiceLocator.getService("treeCodeService");
        /********************************统计方案信息***********************************/
        Map inMap = new HashMap();
        inMap.put("yzb710", yzb710);
        Map zb71Map = (Map)dao.queryForObject("zb71.getMap", inMap);

        if(null != zb71Map){
            fieldData.put("tjfaxx",zb71Map);//存放方案信息
            fieldData.put("ztdm", zb71Map.get("yzb611"));//主题代码
        }


        /********************************and条件信息***********************************/
        Map zb73Map = new HashMap();
        List zb73List =  dao.queryForList("zb73.getAndList", inMap);//where 条件


        /********************************查询条件下拉项目选择框数据***********************************/
        BigDecimal yzb610 = (BigDecimal)zb71Map.get("yzb610");//展现形式
        List<Map> cxxmList = null;
        if(yzb610 != null){
            Map map62In = new HashMap();
            map62In.put("yzb610", yzb610);
            cxxmList = dao.queryForList("zb73.getCxxmMapList", map62In);
        }

        if(ValidateUtil.isNotEmpty(zb73List)){
            /********************************where条件***********************************/
            BigDecimal firstYzb720 = null; //每组的组号（唯一）
            List andList = null;Map zb73 = null;String yzb628 = null;
            for(int i=0;i<zb73List.size();i++){
                zb73 = (Map)zb73List.get(i);
                if(null != firstYzb720 && firstYzb720.equals(zb73.get("yzb720"))){
                    andList.add(zb73);
                }else{
                    andList = new ArrayList();
                    firstYzb720 =  (BigDecimal)zb73.get("yzb720");
                    andList.add(zb73);
                    zb73Map.put("key_" + firstYzb720, andList);
                }

                zb73.put("cxxms", cxxmList);//存放查询项目下拉信息

                /********************************项目支持的关系查询便于生成下拉关系***********************************/
                BigDecimal yzb620 = (BigDecimal)zb73.get("yzb620");//展现形式
                Map map63In = new HashMap();
                map63In.put("yzb620", yzb620);
                List<Map> gxList = dao.queryForList("zb73.getGxysMap", map63In);
                zb73.put("gxyss",gxList);//存放关系运算列表信息


                /********************************获取码值信息（已标识选中数据）***************************************************/
                String yzb62a = (String)zb73.get("yzb62a");//展现形式
                if(ValidateUtil.isNotEmpty(yzb62a) && ("21".equals(yzb62a) || "23".equals(yzb62a))){//平铺、树、datagrid
                    BigDecimal yzb730 = (BigDecimal)zb73.get("yzb730");
                    Map map73In = new HashMap();
                    map73In.put("yzb730", yzb730);
                    List<Map> list = dao.queryForList("zb74.getCodeMapList", map73In);
                    zb73.put("codes",list);//存放码值列表信息
                }else if("22".equals(yzb62a)){//树数据
                    yzb628 = (String)zb73.get("yzb628");
                    if(ValidateUtil.isNotEmpty(yzb62a)&& "22".equals(yzb62a) && ValidateUtil.isNotEmpty(yzb628)){//其它的树
                        zb73.put("treeData",JSonFactory.bean2json(treeCodeService.getTreeDataByType(yzb628,para.getAsString("yzb670"))));//存放完整树数据
                    }
                    //获取选择的树数据
                    BigDecimal yzb730 = (BigDecimal)zb73.get("yzb730");
                    Map map73In = new HashMap();
                    map73In.put("yzb730", yzb730);
                    List<Map> list = dao.queryForList("zb74.getMapList", map73In);
                    zb73.put("chekedTreeData",JSonFactory.bean2json(list));//选择的树数据
                }
            }
            fieldData.put("orsMap", zb73Map);//orsMap分组存放and信息
        }else{//没有查询条件时默认生成一组or条件(条件下拉框数据源)
            fieldData.put("cxxms", cxxmList);//存放查询项目下拉信息
        }
        /********************************分组项目信息(zb78)***********************************/
        List<Map> mapList = dao.queryForList("zb78.getFzxmxxMapList", inMap);//根据统计方案流水号(yzb710)获取分组统计项目
        if(ValidateUtil.isNotEmpty(mapList)){
            fieldData.put("fzxms", mapList);//分项目列表信息
        }

        /********************************统计类型信息(zb79)***********************************/
        List<Map> zb79List = dao.queryForList("zb79.getMapList", inMap);//根据统计方案流水号(yzb710)获取分组统计项目值信息
        /********************************获取可统计的项目**************************************/

        Map paraMap = new TaParamDto();
        paraMap.put("yzb610",zb71Map.get("yzb610"));
        //List<Map> tjxmList = dao.queryForList("zb79.getSearchItemByYZB611", paraMap);//统计项目list
        List<Map> tjxmList = dao.queryForList("zb79.getEnableTjjsxms", paraMap);//可统计计算的项目list
        if(ValidateUtil.isNotEmpty(zb79List)){
            Map zb79Map = null,inMap1 = null;List <Map> list = null;
            for(int j=0;j < zb79List.size(); j++){
                zb79Map = zb79List.get(j);
                zb79Map.put("tjxms", tjxmList);//统计项目后台配置列表

                /********************************获取配置的统计方式***********************************/
                inMap1 = new HashMap();
                inMap1.put("yzb620", zb79Map.get("yzb620"));
                list =dao.queryForList("zb79.getSearchModeByYZB620",inMap1);
                zb79Map.put("tjlxs", list);//统计类型后台配置列表
            }
            fieldData.put("tjlx_rows", zb79List);//统计类型列表上选择的值
        }
        /********************************获取明细显示项目信息zb76(包含统计方式)***********************************/
        List <Map> mxxsxmList =dao.queryForList("zb76.getDetailList",inMap);//根据查询统计方案流水号获取明细显示项目信息
        fieldData.put("mxxsxms", mxxsxmList);//明细显示项目信息

        /********************************获取明细定义的项目排序信息***********************************/
        List <Map> mxpxfsList =dao.queryForList("zb77.getDetailOrderByMap",inMap);//根据查询统计方案流水号获取明细项目排序信息
        fieldData.put("mxpxfss", mxpxfsList);//明细排序方式信息

        odomain.put("fieldData", fieldData);
        return odomain;
    }
    /*
     * 获取统计方案列表信息
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getTjfa(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.scacomm.InputDomain)
     */
    @Override
    public PageBean getTjfa(TaParamDto para) throws Exception {
        Integer start = para.getAsInteger("start");
        Integer limit = para.getAsInteger("limit");
        PageBean pb =  dao.queryForPageWithCount("zb71.getList",para,start,limit);
        return pb;
    }

    /**
     *
     * @param yzb611
     * @return
     * @throws Exception
     */
    @Override
    public Zb61Domain getDomainObjectByYzb611(String yzb611) throws Exception {
        if (!ValidateUtil.isEmpty(yzb611)) {
//			Key key = new Key();
            //change by zhaohs
            Map key = new HashMap();
            key.put("yzb611", yzb611);
            return (Zb61Domain) dao.queryForObject("zb61.getList", key);
        }
        return null;
    }

    @Override
    public Zb61Domain getDomainObjectByYzb610(String yzb610) throws Exception {
        if (!ValidateUtil.isEmpty(yzb610)) {
//			Key key = new Key();
            //change by zhaohs
            Map key = new HashMap();
            key.put("yzb610", yzb610);
            return (Zb61Domain) dao.queryForObject("zb61.getList", key);
        }
        return null;
    }


    /**
     * 根据数据源查找码表中对应的码值集合
     * @param yzb670 数据源id
     * @param aaa100 码值代号
     * @return 码值集合JSON 字串
     * @throws Exception
     */
    @Override
    public String queryCollectionData(String yzb670, String aaa100) throws Exception {
        String result = "";
        if (Constants.DEFAULT_DS_NO.equals(yzb670)) {
            result = CodeTableUtil.getCodeListJson(aaa100,null);
            return result;
        }
        IDao iDao = super.getDynamicDao(yzb670);
        List list= iDao.queryForList("zb62.queryAa10a1",aaa100);
        if (ValidateUtil.isNotEmpty(list)) {
            result = JSON.toJSONString(list);
        }
        return result;
    }

    @Override
    public List queryCodeList(String yzb670, String aaa100) throws Exception {
        List<AppCodeVo> codeList;
        if (Constants.DEFAULT_DS_NO.equals(yzb670)) {
            codeList = getCodeList(aaa100,null);
            return codeList;
        }
        IDao iDao = super.getDynamicDao(yzb670);
        codeList= iDao.queryForList("zb62.queryCodeList",aaa100);
        return codeList;
    }

}

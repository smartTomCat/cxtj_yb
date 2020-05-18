package com.yinhai.cxtj.front.service.impl;

import com.yinhai.core.app.api.util.JSonFactory;
import com.yinhai.core.app.api.util.ServiceLocator;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.core.service.ta3.domain.service.TaBaseService;
import com.yinhai.cxtj.admin.Constants;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.*;
import com.yinhai.cxtj.admin.service.SearchParamService;
import com.yinhai.cxtj.admin.service.TreeCodeService;
import com.yinhai.cxtj.front.domain.*;
import com.yinhai.cxtj.front.service.CustomizeQueryService;
import com.yinhai.modules.codetable.api.util.CodeTableUtil;
import com.yinhai.sysframework.persistence.PageBean;
import com.yinhai.sysframework.persistence.ibatis.IDao;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yinhai.modules.codetable.api.util.CodeTableUtil.getDesc;


/**
 * 自定义查询接口实现
 *
 * @author: 银海人事人才项目组(XXX)
 * @Copyright: 2016-2017 银海软件所有
 * @date: 2016-07-15 下午7:38:10
 * @vesion: 1.0
 */
public class CustomizeQueryServiceImpl extends CxtjBaseServiceImpl implements CustomizeQueryService {
    private SearchParamService searchParamService;

    public void setSearchParamService(SearchParamService searchParamService) {
        this.searchParamService = searchParamService;
    }

    @Override
    public List getEnablePxxmList(TaParamDto para) throws Exception {
        String yzb611 = (String) para.getAsString("yzb611");
        if (ValidateUtil.isNotEmpty(yzb611)) {
            Map map = new HashMap();
            map.put("yzb611", yzb611);
            List<Map> list = dao.queryForList("searchparam.getEnablePxxms", map);
            if (ValidateUtil.isNotEmpty(list)) {
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
        String yzb611 = (String) para.getAsString("yzb611");
        if (ValidateUtil.isNotEmpty(yzb611)) {
            Map map = new HashMap();
            map.put("yzb611", yzb611);
            List list1 = new ArrayList();
            list1.add("1");//默认计算的分组项目
            map.put("yzb62cs", list1);
            List<Map> list = dao.queryForList("searchparam.getEnableFzjsxms", map);
            if (ValidateUtil.isNotEmpty(list)) {
                Map zb62Map = null;
                for (int i = 0; i < list.size(); i++) {
                    zb62Map = list.get(i);
                    if (zb62Map.get("yzb620") != null) {//查询可支持的统计函数
                        List<Zb64Domain> zb64List = searchParamService.getSearchModeByYZB620((BigDecimal) zb62Map.get("yzb620"));
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
        List list = searchParamService.getSearchItemByYZB611((String) para.get("yzb611"));
        return list;
    }

    @Override
    public List getXmgxysfList(TaParamDto para) throws Exception {
        String yzb611 = (String) para.get("yzb611");
        if (ValidateUtil.isNotEmpty(yzb611)) {
            List list = searchParamService.getSearchSignByYZB620(new BigDecimal(yzb611));
            return list;
        }
        return null;
    }

    @Override
    public Map getXmxx(TaParamDto para) throws Exception {
        String yzb611 = (String) para.get("yzb611");
        if (ValidateUtil.isNotEmpty(yzb611)) {
            Zb62Domain outDomain = searchParamService.getSearchItemByYZB620(new BigDecimal(yzb611));
            if (null != outDomain) {
                return outDomain.toMap();
            }
        }
        return null;
    }

    @Override
    //获取可用于分组的项目
    public List getKtjcxztxmList(TaParamDto para) throws Exception {
        String yzb611 = (String) para.get("yzb611");
        if (ValidateUtil.isNotEmpty(yzb611)) {
            Map map = new HashMap();
            map.put("yzb611", yzb611);
            List<Zb62Domain> list = dao.queryForList("searchparam.getEnableFzxms", map);
            if (null != list) {
                return list;
            }
        }
        return null;
    }

    @Override
    public List getFzzbxmList(TaParamDto para) throws Exception {
        String yzb611 = (String) para.get("yzb611");
        if (ValidateUtil.isNotEmpty(yzb611)) {
            Map map = new HashMap();
            map.put("yzb611", yzb611);

            List list1 = new ArrayList();
            list1.add("1");//默认计算
            list1.add("2");//默认不计算
            map.put("yzb62cs", list1);

            List<Map> list = dao.queryForList("searchparam.getEnableFzjsxms", map);
            if (null != list) {
                return list;
            }
        }
        return null;
    }

    @Override
    public List getXmzctjys(TaParamDto para) throws Exception {
        String yzb620 = (String) para.get("yzb620");
        if (ValidateUtil.isNotEmpty(yzb620)) {
            List<Zb64Domain> list = searchParamService.getSearchModeByYZB620(new BigDecimal(yzb620));
            if (null != list) {
                return list;
            }
        }
        return null;
    }

    /**
     * 生成统计类型字段SQL(初使统计查询时用)
     *
     * @param data
     * @return
     * @throws Exception
     */
    private Map generateStatisticalFiledSql(Map data) throws Exception {
        Map outMap = new HashMap();
        if (null != data) {
//			CodeTableLocator codeTableLocator = CodeTableLocator.getInstance();//获取码值
            CodeTableUtil codeTableLocator = new CodeTableUtil(); // change by zhaohs
            List filedInfoList = new ArrayList();   //存放界面展示信息
            StringBuilder sql = new StringBuilder();//sql 串
            StringBuilder groupBy = new StringBuilder();
            String ztdm = (String) data.get("ztdm");
            if (ValidateUtil.isNotEmpty(ztdm)) {
                //select
                /*************************分组字段*************************************/
                List groupFieldList = (List) data.get("groupFieldList");
                if (ValidateUtil.isNotEmpty(groupFieldList)) {
                    Map field = null;
                    for (int i = 0; i < groupFieldList.size(); i++) {
                        field = (Map) groupFieldList.get(i);
                        if (i == 0) {
                            sql.append(field.get("id")).append(" AS ").append(field.get("id"));
                            groupBy.append(field.get("id"));
                        } else {
                            sql.append(",").append(field.get("id")).append(" AS ").append(field.get("id"));
                            groupBy.append(",").append(field.get("id"));
                        }

                        String dataType = (String) field.get("datatype");
                        if (ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType) || "22".equals(dataType) || "23".equals(dataType))) {//代码平铺、树、datagrid
                            field.put("collectionData", codeTableLocator.getCodeListJson((String) field.get("id"), null));
                        }
                        filedInfoList.add(field);//生成datagrid信息（分组）
                    }
                    outMap.put("groupBySql", groupBy.toString());
                }


                /*************************函数字段*************************************/
                List funcitonFieldList = (List) data.get("funcitonFieldList");
                String dbtype = null, filedName = null, tjfs = null;
                if (ValidateUtil.isNotEmpty(funcitonFieldList)) {
                    Map field = null;
                    for (int i = 0; i < funcitonFieldList.size(); i++) {
                        field = (Map) funcitonFieldList.get(i);
                        tjfs = (String) field.get("tjfs");
                        if (i != 0) {
                            sql.append(",");
                        } else {//等于0时判断是否已拼分组字段，如果已拼也需要加逗号
                            if (ValidateUtil.isNotEmpty(groupFieldList)) {
                                sql.append(",");
                            }
                        }
                        if ("1".equals(tjfs)) {//计数
                            sql.append("COUNT(");
                            dbtype = (String) field.get("dbtype");
                            filedName = (String) field.get("id");
                            if ("1".equals(dbtype)) {      //字符型
                                sql.append("NVL(").append(filedName).append(",'0'").append(")");
                            } else if ("2".equals(dbtype)) {//数字型
                                sql.append("NVL(").append(filedName).append(",0").append(")");
                            } else if ("3".equals(dbtype)) {//日期型
                                sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                            } else {
                                sql.append(filedName);
                            }
                            sql.append(")");
                        } else if ("2".equals(tjfs)) {//去重
                            sql.append("COUNT(DISTINCT ");
                            dbtype = (String) field.get("dbtype");
                            filedName = (String) field.get("id");
                            if ("1".equals(dbtype)) {      //字符型
                                sql.append("NVL(").append(filedName).append(",'0'").append(")");
                            } else if ("2".equals(dbtype)) {//数字型
                                sql.append("NVL(").append(filedName).append(",0").append(")");
                            } else if ("3".equals(dbtype)) {//日期型
                                sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                            } else {
                                sql.append(filedName);
                            }
                            sql.append(")");
                        } else if ("3".equals(tjfs)) {//求和
                            sql.append("SUM(").append(field.get("id")).append(")");
                        } else if ("4".equals(tjfs)) {//求和
                            dbtype = (String) field.get("dbtype");
                            if ("2".equals(dbtype)) {//数字型
                                sql.append("AVG(NVL(").append(field.get("id")).append(",0))");
                            } else {
                                sql.append("AVG(").append(field.get("id")).append(")");
                            }
                        } else if ("5".equals(tjfs)) {//最大
                            sql.append("MAX(").append(field.get("id")).append(")");
                        } else if ("6".equals(tjfs)) {//最小
                            sql.append("MIN(").append(field.get("id")).append(")");
                        }
                        sql.append(" AS ").append(field.get("id")).append("_FN");
                        field.put("id", field.get("id") + "_FN");
                        filedInfoList.add(field);//生成datagrid信息（分组）
                    }
                }
                //如果没有设置分组项目和统计项目则默认查询总数
                if (ValidateUtil.isEmpty(groupFieldList) && ValidateUtil.isEmpty(funcitonFieldList)) {
                    sql.append(" COUNT(1) AS COUNT_FN");
                    Map field = new HashMap();
                    field.put("id", "COUNT_FN");
                    field.put("name", "总数");
                    filedInfoList.add(field);
                }
                outMap.put("filedInfoList", filedInfoList);//字段title信息lst
            }
            outMap.put("queryFiledSql", sql.toString());//查询字段SQL
        }
        return outMap;
    }

    /**
     * 生成明细查询字段SQL（详细信息显示字段用）
     *
     * @param data
     * @return
     * @throws Exception
     */
    private Map generateDetailInfoFiledSql(Map data) throws Exception {
        Map outMap = new HashMap();
        if (null != data) {
//			CodeTableLocator codeTableLocator = CodeTableLocator.getInstance();//获取码值
            CodeTableUtil codeTableLocator = new CodeTableUtil(); // change by zhaohs
            List filedInfoList = new ArrayList();   //存放界面展示信息
            StringBuilder sql = new StringBuilder();//sql 串
            String ztdm = (String) data.get("ztdm");
            if (ValidateUtil.isNotEmpty(ztdm)) {
                //select
                /*************************前台设置字段（明细显示字段）*************************************/
                List setFieldList = (List) data.get("showFieldList");
                if (ValidateUtil.isNotEmpty(setFieldList)) {
                    Map field = null;
                    int count = 0;
                    String ischecked = null;
                    for (int i = 0; i < setFieldList.size(); i++) {
                        field = (Map) setFieldList.get(i);
                        ischecked = (String) field.get("ischecked");
                        if ("true".equals(ischecked)) {
                            if (count != 0) {
                                sql.append(",");
                            }
                            sql.append(field.get("id")).append(" AS ").append(field.get("id"));
                            String dataType = (String) field.get("datatype");
                            if (ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType) || "22".equals(dataType) || "23".equals(dataType))) {//代码平铺、树、datagrid
                                field.put("collectionData", CodeTableUtil.getCodeListJson((String) field.get("id"), null));
                            }
                            count++;
                        }
                        field.put("id", field.get("id"));
                        field.put("ztxmlsh", field.get("ztxmlsh"));//查询主题项目流水号
                        field.put("name", field.get("name"));
                        field.put("datatype", field.get("datatype"));
                        field.put("dbtype", field.get("dbtype"));//数据库字段类型
                        field.put("ischecked", field.get("ischecked"));
                        field.put("tjfs", field.get("tjfs"));

                        filedInfoList.add(field);//生成datagrid信息（包含不显示的字段，所以页面生成datagrid列头要判断，解决了生成页面设置显示字段的问题）
                    }
                } else {
                    String tjfalsh = (String) data.get("tjfalsh");//方案流水号
                    if (ValidateUtil.isEmpty(tjfalsh)) {//不存在方案流水号 表示还未保存的方案，数据库后台配置表查询   (点击数字反查时执行)
                        /*************************数据库初使配置字段（明细显示字段）*************************************/
                        if (ValidateUtil.isEmpty(setFieldList)) {
                            Map inMap = new HashMap();
                            inMap.put("yzb611", ztdm);
                            List<Zb62Domain> zb62lst = getDao().queryForList("searchparam.getEnableCxxsxms", inMap);
                            Zb62Domain zb62Domain = null;
                            Map field = null;
                            String yzb62f = null;
                            if (ValidateUtil.isNotEmpty(zb62lst)) {
                                for (int i = 0; i < zb62lst.size(); i++) {
                                    field = new HashMap();
                                    zb62Domain = (Zb62Domain) zb62lst.get(i);
                                    if (i != 0) {
                                        sql.append(",");
                                    } else {//等于0时判断是否已拼分组字段，如果已拼也需要加逗号
                                        if (ValidateUtil.isNotEmpty(setFieldList)) {
                                            sql.append(",");
                                        }
                                    }
                                    sql.append(zb62Domain.getYzb623()).append(" AS ").append(zb62Domain.getYzb623());
                                    field.put("id", zb62Domain.getYzb623());
                                    field.put("ztxmlsh", zb62Domain.getYzb620());//查询主题项目流水号
                                    field.put("name", zb62Domain.getYzb625());
                                    field.put("datatype", zb62Domain.getYzb62a());
                                    field.put("dbtype", zb62Domain.getYzb626());//数据库字段类型
                                    yzb62f = zb62Domain.getYzb62f();
                                    if ("1".equals(yzb62f)) {//默认显示
                                        field.put("ischecked", "true");
                                    } else if ("2".equals(yzb62f)) {//默认不显示
                                        field.put("ischecked", "false");
                                    }
                                    String dataType = zb62Domain.getYzb62a();
                                    if (ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType) || "22".equals(dataType) || "23".equals(dataType))) {//代码平铺、树、datagrid
                                        field.put("collectionData", codeTableLocator.getCodeListJson((String) zb62Domain.getYzb628(), null));
                                    }
                                    filedInfoList.add(field);//生成datagrid信息（分组）
                                }
                            }
                        }
                    } else {//存在方案流水号
                        /*************************存在方案流水号 表示以有保存的方案然 就从保存的方案表查询显示字段 及 配置统计函数*************************************/
                        Map mapIn = new HashMap();
                        mapIn.put("yzb710", tjfalsh);
                        List<Map> domianList = getDao().queryForList("zb76.getDetailList", mapIn);
                        if (ValidateUtil.isNotEmpty(domianList)) {
                            Map field = null;
                            int count = 0;
                            String ischecked = null;
                            for (int i = 0; i < domianList.size(); i++) {
                                field = (Map) domianList.get(i);
                                ischecked = (String) field.get("ischecked");
                                if ("true".equals(ischecked)) {
                                    if (count != 0) {
                                        sql.append(",");
                                    }
                                    sql.append(field.get("yzb623")).append(" AS ").append(field.get("yzb624"));

                                    String dataType = (String) field.get("yzb62a");
                                    if (ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType) || "22".equals(dataType) || "23".equals(dataType))) {//代码平铺、树、datagrid
                                        field.put("collectionData", codeTableLocator.getCodeListJson((String) field.get("yzb628"), null));
                                    }
                                    count++;
                                }
                                field.put("id", field.get("yzb624"));
                                field.remove("yzb624");
                                field.put("ztxmlsh", field.get("yzb610"));
                                field.remove("yzb610");//查询主题项目流水号
                                field.put("name", field.get("yzb625"));
                                field.remove("yzb625");
                                field.put("datatype", field.get("yzb62a"));
                                field.remove("yzb62a");
                                field.put("dbtype", field.get("yzb626"));
                                field.remove("yzb626");//数据库字段类型
                                field.put("tjfs", field.get("yzb641"));
                                field.remove("yzb641");//统计方式
                                field.put("ischecked", field.get("ischecked"));
                                field.remove("ischecked");//是否显示字段

                                filedInfoList.add(field);//生成datagrid信息（包含不显示的字段，所以页面生成datagrid列头要判断，解决了生成页面设置显示字段的问题）
                            }
                        } else {//存在方案流水号，但是数据库没有设置的显示数据（可能是保存的时候没有点击数字查看详细）
                            /*************************数据库初使配置字段（明细显示字段）*************************************/
                            if (ValidateUtil.isEmpty(setFieldList)) {
                                Map inMap = new HashMap();
                                inMap.put("yzb611", ztdm);
                                List<Zb62Domain> zb62lst = getDao().queryForList("searchparam.getEnableCxxsxms", inMap);
                                Zb62Domain zb62Domain = null;
                                Map field = null;
                                String yzb62f = null;
                                if (ValidateUtil.isNotEmpty(zb62lst)) {
                                    for (int i = 0; i < zb62lst.size(); i++) {
                                        field = new HashMap();
                                        zb62Domain = (Zb62Domain) zb62lst.get(i);
                                        if (i != 0) {
                                            sql.append(",");
                                        } else {//等于0时判断是否已拼分组字段，如果已拼也需要加逗号
                                            if (ValidateUtil.isNotEmpty(setFieldList)) {
                                                sql.append(",");
                                            }
                                        }
                                        sql.append(zb62Domain.getYzb623()).append(" AS ").append(zb62Domain.getYzb623());
                                        field.put("id", zb62Domain.getYzb623());
                                        field.put("ztxmlsh", zb62Domain.getYzb620());//查询主题项目流水号
                                        field.put("name", zb62Domain.getYzb625());
                                        field.put("datatype", zb62Domain.getYzb62a());
                                        field.put("dbtype", zb62Domain.getYzb626());//数据库字段类型

                                        yzb62f = zb62Domain.getYzb62f();
                                        if ("1".equals(yzb62f)) {//默认显示
                                            field.put("ischecked", "true");
                                        } else if ("2".equals(yzb62f)) {//默认不显示
                                            field.put("ischecked", "false");
                                        }

                                        String dataType = zb62Domain.getYzb62a();
                                        if (ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType) || "22".equals(dataType) || "23".equals(dataType))) {//代码平铺、树、datagrid
                                            field.put("collectionData", codeTableLocator.getCodeListJson((String) zb62Domain.getYzb628(), null));
                                        }
                                        filedInfoList.add(field);//生成datagrid信息（分组）
                                    }
                                }
                            }
                        }
                    }

                }
                outMap.put("filedInfoList", filedInfoList);//字段title信息lst
            }
            outMap.put("queryFiledSql", sql.toString());//查询字段SQL
        }
        return outMap;
    }

    /**
     * 生成order by sql
     *
     * @param map
     * @return
     * @throws Exception
     */
    private Map generateOrdersSql(Map map) throws Exception {
        Map outMap = new HashMap();
        StringBuilder sql = new StringBuilder();
        List ordersInfoList = new ArrayList();
        if (null != map) {
            List orderList = (List) map.get("orderList");
            if (ValidateUtil.isNotEmpty(orderList)) {//不为空表示页面上设置的详细排序规则
                /*************************明细 页面设置的排序数据*************************************/
                if (ValidateUtil.isNotEmpty(orderList)) {
                    Map orderField = null;
                    Map field = null;
                    for (int i = 0; i < orderList.size(); i++) {
                        orderField = new HashMap();
                        field = (Map) orderList.get(i);
                        if (i != 0) {
                            sql.append(",");
                        }
                        sql.append(field.get("id"));
                        String pxfs = (String) field.get("pxfs");
                        if (ValidateUtil.isNotEmpty(pxfs)) {
                            if ("1".equals(pxfs)) {       //升序
                                sql.append(" ASC");
                            } else if ("2".equals(pxfs)) { //降序
                                sql.append(" DESC");
                            }
                        }

                        orderField.put("id", field.get("id"));
                        orderField.put("ztxmlsh", field.get("ztxmlsh"));//查询主题项目流水号
                        orderField.put("name", field.get("name"));
                        orderField.put("datatype", field.get("datatype"));
                        orderField.put("dbtype", field.get("dbtype"));//数据库字段类型
                        orderField.put("pxfs", field.get("pxfs"));
                        ordersInfoList.add(orderField);//生成datagrid信息
                    }
                    outMap.put("orderBySql", sql.toString());
                }

            } else {//为空表示可能初次查询 或者 有保存方案
                String tjfalsh = (String) map.get("tjfalsh");//方案流水号
                if (ValidateUtil.isNotEmpty(tjfalsh)) {//有方案则从之前保存的方案中获取详细排序信息
                    /*************************明细排序数据(存在方案流水号 就从保存的方案表查询排序数据)*************************************/
                    Map mapIn = new HashMap();
                    mapIn.put("yzb710", tjfalsh);
                    List<Map> ordersList = getDao().queryForList("zb77.getDetailOrderByMap", mapIn);
                    if (ValidateUtil.isNotEmpty(ordersList)) {
                        Map outZb77Map = null;
                        Map orderField = null;
                        for (int i = 0; i < ordersList.size(); i++) {
                            orderField = new HashMap();
                            outMap = ordersList.get(i);
                            if (i != 0) {
                                sql.append(",");
                            }
                            sql.append(outZb77Map.get("yzb623"));
                            String yzb652 = (String) outZb77Map.get("yzb652");
                            if (ValidateUtil.isNotEmpty(yzb652)) {
                                if ("1".equals(yzb652)) {       //升序
                                    sql.append(" ASC");
                                } else if ("2".equals(yzb652)) { //降序
                                    sql.append(" DESC");
                                }
                            }

                            orderField.put("id", outZb77Map.get("yzb624"));
                            orderField.put("ztxmlsh", outZb77Map.get("yzb610"));//查询主题项目流水号
                            orderField.put("name", outZb77Map.get("yzb625"));
                            orderField.put("pxfs", outZb77Map.get("yzb652"));
                            //orderField.put("datatype",outZb77Map.get("yzb62a"));
                            //orderField.put("dbtype",outZb77Map.get("yzb626"));//数据库字段类型

                            ordersInfoList.add(orderField);//生成详细排序信息
                        }
                        outMap.put("orderBySql", sql.toString());
                    }
                }
            }
            outMap.put("ordersInfoList", ordersInfoList);
        }
        return outMap;
    }

    /**
     * 生成基础条件SQL
     *
     * @param map
     * @return
     */
    private Map generateBaseWhereSql(Map map, String jctjTemplate) {
        StringBuilder msg = new StringBuilder(), sql = new StringBuilder();//错误消息,sql 串
        Map outMap = new HashMap();
        Map jctjMap = null;
        String jctj = (String) map.get("jctj");//基础条件值
        if (ValidateUtil.isNotEmpty(jctj)) {
            jctjMap = JSonFactory.json2bean(jctj, Map.class);
        }
        if (ValidateUtil.isNotEmpty(jctjTemplate)) {//b0100=#b0100# and aae017=#aae017# and yb0162=#yb0162#
            Pattern p = Pattern.compile("(#[0-9a-zA-Z]{1,10}#)");
            Matcher m = p.matcher(jctjTemplate);
            String key = null, newKey = null, value = null;
            int i = 0;
            while (m.find()) {
                key = m.group(1);
                if (ValidateUtil.isNotEmpty(key)) {
                    newKey = key.replaceAll("#", "").toLowerCase();
                    if (null != jctjMap) {
                        value = (String) jctjMap.get(newKey);
                        if (ValidateUtil.isEmpty(value)) {
                            if (i == 0) {
                                msg.append(newKey);
                            } else {
                                msg.append(",").append(newKey);
                            }
                            i++;
                        } else {
                            jctjTemplate = jctjTemplate.replace(key, "'" + (String) jctjMap.get(newKey) + "'");
                        }
                    }
                }
            }
            if (i > 0) {
                msg.append("，这些字段值需通过菜单URL配置传入才能进行查询！");
                outMap.put("msg", msg.toString());
                return outMap;
            }
            sql.append(" AND ").append(jctjTemplate);
            outMap.put("sql", sql.toString());
        }
        return outMap;
    }

    /**
     * 生成from和where SQL
     *
     * @param map
     * @return
     * @throws Exception
     */
    private Map generateFromWhereSql(Map map) throws Exception {
        Map outMap = new HashMap();
        StringBuilder sql = new StringBuilder();//sql 串
        String ztdm = (String) map.get("ztdm");
        if (ValidateUtil.isNotEmpty(ztdm)) {
            //from
            Zb61Domain zb61Domain = searchParamService.getSearchByYZB611(ztdm);
            if (null != zb61Domain) {
                sql.append(" FROM ").append(zb61Domain.getYzb613());
                //where
                sql.append(" WHERE 1=1");


                //配置的基础条件SQL
                if (zb61Domain.getYzb615() != null) {
                    Map jctjMap = this.generateBaseWhereSql(map, zb61Domain.getYzb615());
                    if (ValidateUtil.isNotEmpty((String) jctjMap.get("sql"))) {
                        sql.append(jctjMap.get("sql"));
                    } else {
                        if (ValidateUtil.isNotEmpty((String) jctjMap.get("msg"))) {//有错误消息
                            return jctjMap;
                        }
                    }
                }

                List _andList = (List) map.get("andList");
                Map outAndMap = null;
                String id = null, value = null;

                //前台点击数字带的参数
                if (ValidateUtil.isNotEmpty(_andList)) {
                    for (int j = 0; j < _andList.size(); j++) {
                        outAndMap = (Map) _andList.get(j);
                        id = (String) outAndMap.get("id");
                        value = (String) outAndMap.get("value");
                        if (ValidateUtil.isNotEmpty(id) && ValidateUtil.isNotEmpty(value)) {
                            sql.append(" AND ").append(id.toUpperCase());
                            if ("null".equals(value)) {
                                sql.append(" IS NULL");
                            } else {
                                sql.append(" = ").append("'").append(value).append("'");
                            }
                        }
                    }
                }

                List orList = (List) map.get("orList");
                //前台or条件组
                if (ValidateUtil.isNotEmpty(orList)) {
                    List andList = null;
                    Map map1 = null;
                    Map andMap = null;
                    String datatype = null, vtype = null;

                    //查找是否有and数据
                    Boolean hasAnd = false;
                    Map tempMap = (Map) orList.get(0);
                    if (tempMap != null) {
                        List temAndList = (List) tempMap.get("andList");
                        if (ValidateUtil.isNotEmpty(temAndList)) {
                            hasAnd = true;
                        }
                    }

                    if (hasAnd) {//有and数据
                        sql.append(" AND (");//AND 起始括号   诸如：AND((1=1 AND 2=2) OR (3=3 AND 4=4) OR (5=5))

                        for (int i = 0; i < orList.size(); i++) {

                            map1 = (Map) orList.get(i);
                            if (null == map1 || ValidateUtil.isEmpty((List) map1.get("andList"))) {//判断是否有andlist 没有就下一个
                                continue;
                            }

                            if (i != 0) {
                                sql.append(" OR (");//OR 起始括号(非第一个加or)
                            } else {
                                sql.append("(");//OR 起始括号(第一个不加or)
                            }

                            if (null != map1) {
                                andList = (List) map1.get("andList");
                                if (ValidateUtil.isNotEmpty(andList)) {
                                    for (int j = 0; j < andList.size(); j++) {
                                        andMap = (Map) andList.get(j);
                                        datatype = (String) andMap.get("dataType");
                                        if (j != 0) {
                                            sql.append(" AND ");
                                        }
                                        if (ValidateUtil.isNotEmpty(datatype)) {
                                            vtype = (String) andMap.get("vtype");
                                            if ("1".equals(vtype)) {//值类型
                                                if ("11".equals(datatype)) {//文本
                                                    if ("11".equals(andMap.get("gxysf"))) {//等于
                                                        sql.append(andMap.get("id"));
                                                        sql.append(" ='").append(andMap.get("value")).append("'");
                                                    } else if ("12".equals(andMap.get("gxysf"))) {//不等于
                                                        sql.append(andMap.get("id"));
                                                        sql.append(" !='").append(andMap.get("value")).append("'");
                                                    } else if ("41".equals(andMap.get("gxysf"))) {//包含
                                                        sql.append("(").append(andMap.get("id"));
                                                        sql.append(" LIKE '%").append(andMap.get("value")).append("%'");
                                                        Pattern p = Pattern.compile("\\s+");
                                                        String _value = (String) andMap.get("value");
                                                        String _valueA[] = _value.split("\\s+");
                                                        if (p.matcher(_value).find()) {
                                                            for (int k = 0; k < _valueA.length; k++) {
                                                                if (ValidateUtil.isNotEmpty(_valueA[k])) {
                                                                    sql.append(" OR").append(" INSTR(").append(andMap.get("id")).append(",'").append(_valueA[k]).append("') > 0");
                                                                }
                                                            }
                                                        }
                                                        sql.append(")");
                                                    } else if ("42".equals(andMap.get("gxysf"))) {//不包含
                                                        sql.append("(").append(andMap.get("id"));
                                                        sql.append(" NOT LIKE '%").append(andMap.get("value")).append("%'");
                                                        Pattern p = Pattern.compile("\\s+");
                                                        String _value = (String) andMap.get("value");
                                                        String _valueA[] = _value.split("\\s+");
                                                        if (p.matcher(_value).find()) {
                                                            for (int k = 0; k < _valueA.length; k++) {
                                                                if (ValidateUtil.isNotEmpty(_valueA[k])) {
                                                                    sql.append(" AND").append(" INSTR(").append(andMap.get("id")).append(",'").append(_valueA[k]).append("') = 0");
                                                                }
                                                            }
                                                        }
                                                        sql.append(")");
                                                    }
                                                } else if ("12".equals(andMap.get("dataType"))) {//年月
                                                    sql.append("TO_CHAR(").append(andMap.get("id")).append(",'YYYY-MM')");
                                                    if ("11".equals(andMap.get("gxysf"))) {//等于
                                                        sql.append(" = ");
                                                    } else if ("12".equals(andMap.get("gxysf"))) {//不等于
                                                        sql.append(" != ");
                                                    } else if ("21".equals(andMap.get("gxysf"))) {//大于
                                                        sql.append(" > ");
                                                    } else if ("22".equals(andMap.get("gxysf"))) {//大于等于
                                                        sql.append(" >= ");
                                                    } else if ("31".equals(andMap.get("gxysf"))) {//小于
                                                        sql.append(" < ");
                                                    } else if ("32".equals(andMap.get("gxysf"))) {//小于等于
                                                        sql.append(" <= ");
                                                    }
                                                    sql.append("'").append(andMap.get("value")).append("'");
                                                } else if ("13".equals(andMap.get("dataType"))) {//日期
                                                    sql.append("TO_CHAR(").append(andMap.get("id")).append(",'YYYY-MM-DD')");
                                                    if ("11".equals(andMap.get("gxysf"))) {//等于
                                                        sql.append(" = ");
                                                    } else if ("12".equals(andMap.get("gxysf"))) {//不等于
                                                        sql.append(" != ");
                                                    } else if ("21".equals(andMap.get("gxysf"))) {//大于
                                                        sql.append(" > ");
                                                    } else if ("22".equals(andMap.get("gxysf"))) {//大于等于
                                                        sql.append(" >= ");
                                                    } else if ("31".equals(andMap.get("gxysf"))) {//小于
                                                        sql.append(" < ");
                                                    } else if ("32".equals(andMap.get("gxysf"))) {//小于等于
                                                        sql.append(" <= ");
                                                    }
                                                    sql.append("'").append(andMap.get("value")).append("'");
                                                } else if ("14".equals(andMap.get("dataType"))) {//数字
                                                    sql.append(andMap.get("id"));
                                                    if ("11".equals(andMap.get("gxysf"))) {//等于
                                                        sql.append(" = ");
                                                    } else if ("12".equals(andMap.get("gxysf"))) {//不等于
                                                        sql.append(" != ");
                                                    } else if ("21".equals(andMap.get("gxysf"))) {//大于
                                                        sql.append(" > ");
                                                    } else if ("22".equals(andMap.get("gxysf"))) {//大于等于
                                                        sql.append(" >= ");
                                                    } else if ("31".equals(andMap.get("gxysf"))) {//小于
                                                        sql.append(" < ");
                                                    } else if ("32".equals(andMap.get("gxysf"))) {//小于等于
                                                        sql.append(" <= ");
                                                    }
                                                    sql.append(andMap.get("value"));
                                                } else if ("21".equals(andMap.get("dataType")) || "22".equals(andMap.get("dataType")) || "23".equals(andMap.get("dataType"))) {//代码值、树、datagrid
                                                    sql.append(andMap.get("id"));
                                                    if ("11".equals(andMap.get("gxysf")) || "41".equals(andMap.get("gxysf"))) {//等于 || 包含
                                                        sql.append(" IN(");
                                                    } else if ("12".equals(andMap.get("gxysf")) || "42".equals(andMap.get("gxysf"))) {//不等于 || 不包含
                                                        sql.append(" NOT IN(");
                                                    }
                                                    sql.append(andMap.get("value")).append(")");
                                                }
                                            } else if ("2".equals(vtype)) {//值类型为选择的字段时
                                                sql.append("TO_CHAR(").append(andMap.get("id")).append(")");
                                                if ("11".equals(andMap.get("gxysf"))) {//等于
                                                    sql.append(" = ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                } else if ("12".equals(andMap.get("gxysf"))) {//不等于
                                                    sql.append(" != ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                } else if ("21".equals(andMap.get("gxysf"))) {//大于
                                                    sql.append(" > ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                } else if ("22".equals(andMap.get("gxysf"))) {//大于等于
                                                    sql.append(" >= ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                } else if ("31".equals(andMap.get("gxysf"))) {//小于
                                                    sql.append(" < ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                } else if ("32".equals(andMap.get("gxysf"))) {//小于等于
                                                    sql.append(" <= ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                } else if ("41".equals(andMap.get("gxysf"))) {//包含
                                                    sql.append(" IN(TO_CHAR(").append(andMap.get("vfield")).append("))");
                                                } else if ("42".equals(andMap.get("gxysf"))) {//不包含
                                                    sql.append(" NOT IN(TO_CHAR(").append(andMap.get("vfield")).append("))");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            sql.append(")");   //OR 收尾括号
                        }
                        sql.append(")");//AND 收尾括号
                    }
                }
            }
        }
        outMap.put("sql", sql.toString());
        return outMap;
    }

    /**
     * 获取生成的统计SQL(统计查询SQL)
     *
     * @return
     */
    private Map getGenerateSql(String xml) throws Exception {
        Map outMap = new HashMap();//返回SQL和字段title信息
        StringBuilder sql = new StringBuilder();//sql 串
        if (xml != null) {
            Map map = xml2Obj(xml);//xml2obj 对象
            Map outFiledMap = this.generateStatisticalFiledSql(map);//生成filed 字段SQL
            String ztdm = (String) map.get("ztdm");
            if (ValidateUtil.isNotEmpty(ztdm)) {
                sql.append("SELECT ");

                //fileds
                String queryFiledSql = (String) outFiledMap.get("queryFiledSql");
                if (ValidateUtil.isNotEmpty(queryFiledSql)) {
                    sql.append(queryFiledSql);
                }

                //from table where ...
                Map generateFromWhereMap = generateFromWhereSql(map);
                String generateFromWhereSql = (String) generateFromWhereMap.get("sql");
                if (ValidateUtil.isNotEmpty(generateFromWhereSql)) {
                    sql.append(generateFromWhereSql);
                } else {
                    outMap.put("msg", generateFromWhereMap.get("msg"));//错误消息
                }

                //group by(分组时用)
                String groupBySql = (String) outFiledMap.get("groupBySql");
                if (ValidateUtil.isNotEmpty(groupBySql)) {
                    sql.append(" GROUP BY ").append(groupBySql);
                    //order by(分组时用)
                    sql.append(" ORDER BY ").append(groupBySql);//分组时生成order by
                }

                //order by(详细信息用的)
                String orderBySql = (String) outFiledMap.get("orderBySql");
                if (ValidateUtil.isNotEmpty(orderBySql)) {
                    sql.append(" ORDER BY ").append(orderBySql);
                    outMap.put("order_fileds", (List) outFiledMap.get("ordersInfoList"));
                }
            }
            outMap.put("fileds", (List) outFiledMap.get("filedInfoList"));
            outMap.put("tjfalsh", map.get("tjfalsh"));//方案流水号
        }
        outMap.put("sql", sql.toString());

        return outMap;
    }

    /**
     * xml2obj(点击查询统计时)
     *
     * @param xml
     * @return
     * @throws Exception
     */
    private Map xml2Obj(String xml) throws Exception {
        System.out.println(xml);
        Map data = new HashMap();
        List groupFieldList = null, funcitonFieldList = null, showFieldList = null, orList = new ArrayList();//分组字段、函数字段、or条件
        Document doc = DocumentHelper.parseText(xml);
        Element scheme = doc.getRootElement();
        Element statisticsQueryElement = scheme.element("statistics_query");
        Attribute ztdmAttribute = scheme.attribute("ztdm");//主题代码
        Attribute ztlshAttribute = scheme.attribute("ztlsh");//主题流水号
        Attribute tjfalshAttribute = scheme.attribute("tjfalsh");//方案流水号
        Attribute tjfanameAttribute = scheme.attribute("tjfaname");//方案流水号
        Attribute jctjAttribute = scheme.attribute("jctj");//基础条件
        if (null != ztdmAttribute) {
            data.put("ztdm", ztdmAttribute.getText());//主题代码
        }
        if (null != ztlshAttribute) {
            data.put("ztlsh", ztlshAttribute.getText());//主题流水号
        }
        if (null != tjfalshAttribute) {
            data.put("tjfalsh", tjfalshAttribute.getText());//统计方案流水号
        }
        if (null != tjfanameAttribute) {
            data.put("tjfaname", tjfanameAttribute.getText());//统计方案名称
        }
        if (null != jctjAttribute) {
            data.put("jctj", jctjAttribute.getText());//基础条件值
        }
        Element fields = statisticsQueryElement.element("fields");

        /***********************分组字段*************************************/
        Element groupField = fields.element("group_field");
        if (null != groupField) {
            groupFieldList = new ArrayList();
            Iterator groupFieldIterator = groupField.elementIterator("field");
            Map groupFieldMap = null;
            while (groupFieldIterator.hasNext()) {
                groupFieldMap = new HashMap();
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
        Element functionFiled = fields.element("function_field");
        if (null != functionFiled) {
            funcitonFieldList = new ArrayList();
            Iterator functionFiledIterator = functionFiled.elementIterator("field");
            Map functionFiledMap = null;
            Element fieldElement = null;
            while (functionFiledIterator.hasNext()) {
                functionFiledMap = new HashMap();
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
        Element showFiled = fields.element("show_field");//页面上设置的显示字段（查看明细用）
        if (null != showFiled) {
            showFieldList = new ArrayList();
            funcitonFieldList = new ArrayList();
            Iterator showFiledIterator = showFiled.elementIterator("field");
            Map showFiledMap = null, functionFiledMap = null;
            Element fieldElement = null;
            while (showFiledIterator.hasNext()) {
                showFiledMap = new HashMap();
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
                if ("true".equals(ischecked)) {//显示字段设置了统计的
                    functionFiledMap = new HashMap();
                    functionFiledMap.put("ztxmlsh", fieldElement.attribute("ztxmlsh").getValue());
                    functionFiledMap.put("sn", fieldElement.attribute("sn").getValue());
                    functionFiledMap.put("name", fieldElement.attribute("id").getValue());
                    functionFiledMap.put("tjfs", tjfs);    //统计方式
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
        if (null != _andsElement) {
            Map andMap = null;
            Element andElement = null;
            List andList = new ArrayList();
            Iterator andIterator = _andsElement.elementIterator("and");
            while (andIterator.hasNext()) {
                andMap = new HashMap();
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
        Iterator orIterator = orsElement.elementIterator("or");
        Element orElement = null, andsElement = null, andElement = null;
        List andList = null;//存放多个and
        Map andMap = null;
        Map orMap = null;//存放多个or
        while (orIterator.hasNext()) {
            andList = new ArrayList();
            orMap = new HashMap();
            orElement = (Element) orIterator.next();
            orMap.put("sn", orElement.attribute("sn").getValue());
            andsElement = orElement.element("ands");
            Iterator andIterator = andsElement.elementIterator("and");
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
                if ("1".equals(vtype)) {//值类型
                    andMap.put("value", andElement.attribute("value").getValue());
                } else if ("2".equals(vtype)) {//项目类型
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
        if (null != orderElement) {
            Map orderMap = null;
            Element fieldElement = null;
            List orderList = new ArrayList();
            Iterator fieldIterator = orderElement.elementIterator("field");
            while (fieldIterator.hasNext()) {
                orderMap = new HashMap();
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

    /*
     * 获取查询统计数据
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getStatisticalData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public Map getStatisticalData(TaParamDto para) throws Exception {
        Map outputDomain = new HashMap();
        String xml = (String) para.get("xml");
        Map map = new HashMap();
        Map outmap = getGenerateSql(xml);

        if (ValidateUtil.isNotEmpty((String) outmap.get("msg"))) {//有错误消息
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", (String) outmap.get("msg"));
            return outputDomain;
        }

        if (ValidateUtil.isNotEmpty((String) outmap.get("sql"))) {
            System.out.println(outmap.get("sql"));
            map.put("sql", outmap.get("sql"));
            List list = getDao().queryForList("query.excuteSql", map);
            if (ValidateUtil.isNotEmpty(list)) {
                outputDomain.put("list", list);
            }
            Map fieldData = new HashMap();
            fieldData.put("fileds", outmap.get("fileds"));
            fieldData.put("tjfalsh", outmap.get("tjfalsh"));//方案流水号
            outputDomain.put("fieldData", fieldData);//返回页面构件datagrid
        }
        return outputDomain;
    }

    /*
     * 获取分页查询统计数据
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getStatisticalData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public Map getPageStatisticalData(TaParamDto para) throws Exception {
        Map outputDomain = new HashMap();
        String xml = (String) para.get("xml");
        Map map = new HashMap();
        Map outmap = getGenerateSql(xml);

        if (ValidateUtil.isNotEmpty((String) outmap.get("msg"))) {//有错误消息
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", (String) outmap.get("msg"));
            return outputDomain;
        }

        if (ValidateUtil.isNotEmpty((String) outmap.get("sql"))) {
            String sql = (String) outmap.get("sql");
            String countSql = null;
            Integer start = para.getAsInteger("start");
            Integer limit = para.getAsInteger("limit");
            if (null != start && null != limit) {
                countSql = generatePageCountSql(sql);
                sql = generatePageSql(sql, start, limit);
                System.out.println(sql);
                System.out.println(countSql);
            }
            map.put("sql", sql);

            IDao dynamicDao = super.getDynamicDao(para.getAsString("yzb670"));

            //sql 开始时间
            Map outMap1 = null;
            //是否出错  默认为否
            String yzb994 = "0";
            //执行sql集
            String yzb996 = "" + map.get("sql");
            //错误信息
            String yzb995 = "";
            //耗时
            Double yzb993 = null;
            Timestamp timestampStart = getSysTimestamp();
            Timestamp timestampEnd = null;
            try {
                //查询页数据
                List list = dynamicDao.queryForList("query.excuteSql", map);
                if (ValidateUtil.isNotEmpty(list)) {
                    outputDomain.put("list", list);
                }
                map.put("sql", countSql);
                //查询总条数
                outMap1 = (Map) dynamicDao.queryForObject("query.excuteSql", map);
                //sql 结束时间
                timestampEnd = getSysTimestamp();
                //耗时
                yzb993 = (timestampEnd.getTime() - timestampStart.getTime()) / 1000.00;
                yzb996 += "&" + map.get("sql").toString();
            } catch (Exception e) {
                yzb994 = "1";
                yzb995 = e.toString();
                e.printStackTrace();
                throw new AppException("sql查询执行失败!!!");
            } finally {
                insertZb99(para, timestampStart, timestampEnd, yzb993, yzb994, yzb995, yzb996);
            }

            Map fieldData = new HashMap();
            if (null != outMap1.get("count")) {
                fieldData.put("datacount", outMap1.get("count"));
            }

            fieldData.put("fileds", outmap.get("fileds"));
            fieldData.put("tjfalsh", outmap.get("tjfalsh"));//方案流水号
            outputDomain.put("fieldData", fieldData);           //返回页面构件datagrid
        }
        return outputDomain;
    }

    /**
     * 查询sql执行日志表
     *
     * @param dto
     * @param yzb991
     * @param yzb992
     * @param yzb993
     * @param yzb994
     * @param yzb995
     * @param yzb996
     */
    private void insertZb99(TaParamDto dto, Timestamp yzb991, Timestamp yzb992, Double yzb993, String yzb994, String yzb995, String yzb996) {
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
        dao.insert("zb99.insert", zb99Domain);
    }

    /**
     * 生成分页SQL语句
     *
     * @param sql
     * @param start
     * @param limit
     * @return
     */
    private String generatePageSql(String sql, Integer start, Integer limit) {
        if (null != start && null != limit) {
            StringBuilder newSql = new StringBuilder();
            newSql.append("SELECT * FROM (SELECT ROWNUM AS MYROWNUM, C.* FROM (")
                    .append(sql)
                    .append(") C) WHERE MYROWNUM <= ")
                    .append(limit + start).append(" AND MYROWNUM > ")
                    .append(start);
            return newSql.toString();
        } else {
            return sql;
        }
    }

    /**
     * 生成分页SQL总条数语句
     *
     * @param sql
     * @return
     */
    private String generatePageCountSql(String sql) {
        if (ValidateUtil.isNotEmpty(sql)) {
            StringBuilder newSql = new StringBuilder();
            newSql.append("SELECT COUNT(1) AS COUNT FROM (").append(sql).append(") A");
            return newSql.toString();
        }
        return sql;
    }

    /*
     * 获取详细信息(点击数字、设置显示字段、设置排序字段)
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getDetailInfoData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public Map getDetailInfoData(TaParamDto para) throws Exception {

        Map outputDomain = new HashMap();
        String xml = (String) para.get("xml");
        Map map = new HashMap();
        Map outmap = getGenerateDetailInfoSql(xml);

        if (ValidateUtil.isNotEmpty((String) outmap.get("msg"))) {//有错误消息
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", (String) outmap.get("msg"));
            return outputDomain;
        }

        Map fieldData = new HashMap();
        List list = null;
        //明细sql执行
        if (ValidateUtil.isNotEmpty((String) outmap.get("sql"))) {        //标准SQL
            System.out.println(outmap.get("sql"));
            map.put("sql", outmap.get("sql"));
            list = getDao().queryForList("query.excuteSql", map);
        }

        //统计sql执行
        if (ValidateUtil.isNotEmpty((String) outmap.get("_sql"))) {        //统计SQL
            map.put("sql", outmap.get("_sql"));
            List _list = getDao().queryForList("query.excuteSql", map);
            if (ValidateUtil.isNotEmpty(_list)) {
                if (null != fieldData) {
                    Map tjdata = (Map) _list.get(0), tjcodes = (Map) outmap.get("tjcodes");//统计行数据 || 统计名称
                    Map.Entry item = null;
                    if (null != tjdata) {
                        Iterator iterator = tjdata.entrySet().iterator();
                        while (iterator.hasNext()) {
                            item = (Map.Entry) iterator.next();
                            item.setValue((String) tjcodes.get(item.getKey()) + ":" + item.getValue());//统计行数据
                        }
                        if (list != null) {
                            list.add(tjdata);
                        }
                    }
                }
            }
        }
        if (ValidateUtil.isNotEmpty(list)) {
            outputDomain.put("list", list);
        }
        fieldData.put("fileds", outmap.get("fileds"));//显示字段（明细）
        fieldData.put("order_fileds", outmap.get("order_fileds"));//设置的排序字段（明细）
        fieldData.put("tjfalsh", outmap.get("tjfalsh"));//方案流水号
        outputDomain.put("fieldData", fieldData);//返回页面构件datagrid

        return outputDomain;
    }

    /*
     * 获取分页详细信息(点击数字、设置显示字段、设置排序字段)
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getDetailInfoData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public Map getPageDetailInfoData(TaParamDto para) throws Exception {
        Map outputDomain = new HashMap();
        String xml = (String) para.get("xml");
        Map map = new HashMap();

        Map outmap = getGenerateDetailInfoSql(xml);

        if (ValidateUtil.isNotEmpty((String) outmap.get("msg"))) {//有错误消息
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", (String) outmap.get("msg"));
            return outputDomain;
        }

        Map fieldData = new HashMap();
        List list = null;
        //获取dao
        IDao dynamicDao = super.getDynamicDao(para.getAsString("yzb670"));
        //明细sql执行
        //标准SQL
        if (ValidateUtil.isNotEmpty((String) outmap.get("sql"))) {
            String sql = (String) outmap.get("sql");
            String countSql = null;
            Integer start = para.getAsInteger("start");
            Integer limit = para.getAsInteger("limit");
            if (null != start && null != limit) {
                countSql = generatePageCountSql(sql);
                sql = generatePageSql(sql, start, limit);
                System.out.println(sql);
                System.out.println(countSql);
            }
            map.put("sql", sql);

            Map outMap1 = null;
            //是否出错  默认为否
            String yzb994 = "0";
            //执行sql集
            String yzb996 = "" + map.get("sql");
            //错误信息
            String yzb995 = "";
            //耗时
            Double yzb993 = null;
            Timestamp timestampStart = getSysTimestamp();
            Timestamp timestampEnd = null;
            try {
                //分页数据
                list = dynamicDao.queryForList("query.excuteSql", map);
                map.put("sql", countSql);
                //查询总条数
                outMap1 = (Map) dynamicDao.queryForObject("query.excuteSql", map);
                //sql 结束时间
                timestampEnd = getSysTimestamp();
                //耗时
                yzb993 = (timestampEnd.getTime() - timestampStart.getTime()) / 1000.00;
                yzb996 += "&" + map.get("sql").toString();

            } catch (Exception e) {
                yzb994 = "1";
                yzb995 = e.toString();
                e.printStackTrace();
                throw new AppException("执行sql查询出错");
            } finally {
                insertZb99(para, timestampStart, timestampEnd, yzb993, yzb994, yzb995, yzb996);
            }

            if (null != outMap1.get("count")) {
                fieldData.put("datacount", outMap1.get("count"));
            }
        }

        //统计sql执行
        if (ValidateUtil.isNotEmpty((String) outmap.get("_sql"))) {        //统计SQL
            System.out.println(outmap.get("_sql"));
            map.put("sql", outmap.get("_sql"));
            List _list = dynamicDao.queryForList("query.excuteSql", map);
            if (ValidateUtil.isNotEmpty(_list)) {
                if (null != fieldData) {
                    Map tjdata = (Map) _list.get(0), tjcodes = (Map) outmap.get("tjcodes");//统计行数据 || 统计名称
                    Map.Entry item = null;
                    if (null != tjdata) {
                        Iterator iterator = tjdata.entrySet().iterator();
                        while (iterator.hasNext()) {
                            item = (Map.Entry) iterator.next();
                            item.setValue((String) tjcodes.get(item.getKey()) + ":" + item.getValue());//统计行数据
                        }
                        if (list != null) {
                            list.add(tjdata);
                        }
                    }
                }
            }
        }
        if (ValidateUtil.isNotEmpty(list)) {
            outputDomain.put("list", list);
        }
        fieldData.put("fileds", outmap.get("fileds"));//显示字段（明细）
        fieldData.put("order_fileds", outmap.get("order_fileds"));//设置的排序字段（明细）
        fieldData.put("tjfalsh", outmap.get("tjfalsh"));//方案流水号
        outputDomain.put("fieldData", fieldData);//返回页面构件datagrid

        return outputDomain;
    }

    /**
     * 生成详细信息SQl，详细信息统计SQL
     *
     * @param xml
     * @return
     * @throws Exception
     */
    private Map getGenerateDetailInfoSql(String xml) throws Exception {
        Map outMap = new HashMap();//返回SQL和字段title信息
        StringBuilder sql = new StringBuilder();//详细信息sql 串
        StringBuilder _sql = new StringBuilder();//详细信息sql 串

        if (xml != null) {
            Map map = xml2Obj(xml);//xml2obj 对象
            Map outFiledMap = this.generateDetailInfoFiledSql(map);//生成filed 字段SQL
            String ztdm = (String) map.get("ztdm");
            if (ValidateUtil.isNotEmpty(ztdm)) {

                /****************************************生成详细信息SQL************************************************/
                sql.append("SELECT ");

                //fileds
                String queryFiledSql = (String) outFiledMap.get("queryFiledSql");
                if (ValidateUtil.isNotEmpty(queryFiledSql)) {
                    sql.append(queryFiledSql);
                }

                //from table where ...
                Map generateFromWhereMap = generateFromWhereSql(map);
                String generateFromWhereSql = (String) generateFromWhereMap.get("sql");
                if (ValidateUtil.isNotEmpty(generateFromWhereSql)) {
                    sql.append(generateFromWhereSql);
                } else {
                    outMap.put("msg", generateFromWhereMap.get("msg"));//错误消息
                }

                //order by(详细信息用的)
                List orderList = (List) map.get("orderList");
                if (ValidateUtil.isNotEmpty(orderList)) {
                    Map outOrderMap = generateOrdersSql(map);
                    String orderBySql = (String) outOrderMap.get("orderBySql");
                    if (ValidateUtil.isNotEmpty(orderBySql)) {
                        sql.append(" ORDER BY ").append(orderBySql);
                        outMap.put("order_fileds", (List) outOrderMap.get("ordersInfoList"));//生成order by字段在页面上显示
                    }
                }

                outMap.put("fileds", (List) outFiledMap.get("filedInfoList"));          //生成详细信息title字段(包含统计函数)在页面上显示
                outMap.put("tjfalsh", map.get("tjfalsh"));//方案流水号

                if (ValidateUtil.isEmpty(queryFiledSql)) {//没选择详细信息显示的字段时直接返回，不执行SQL
                    return outMap;
                }
                outMap.put("sql", sql.toString());//详细信息SQL

                /****************************************生成详细信息统计SQL************************************************/


                Map _outMap = generateStatisticalQueryFiledSql(map);
                String statisticalQueryFiledSql = (String) _outMap.get("sql");
                if (ValidateUtil.isNotEmpty(statisticalQueryFiledSql)) {//表示没统计指标项目

                    _sql.append("SELECT ");

                    //fileds
                    _sql.append(statisticalQueryFiledSql);

                    //from table where ...
                    if (ValidateUtil.isNotEmpty(generateFromWhereSql)) {
                        _sql.append(generateFromWhereSql);
                    }

                    outMap.put("tjcodes", _outMap.get("tjcodes"));//统计方式中文名称
                    outMap.put("_sql", _sql.toString());//详细信息SQL
                }
            }
        }
        return outMap;
    }

    /**
     * 生成详细信息统计查询SQL字段
     *
     * @param data
     * @return
     */
    private Map generateStatisticalQueryFiledSql(Map data) {
        /*************************前台设置字段（明细显示字段）*************************************/
        StringBuilder sql = new StringBuilder();
        Map outMap = new HashMap(), fieldMap = new HashMap();
        List setFieldList = (List) data.get("showFieldList");
        if (ValidateUtil.isNotEmpty(setFieldList)) {
            Map field = null;
            int count = 0;
            String ischecked = null, tjfs = null, dbtype = null, filedName = null;
            for (int i = 0; i < setFieldList.size(); i++) {
                field = (Map) setFieldList.get(i);
                ischecked = (String) field.get("ischecked");
                tjfs = (String) field.get("tjfs");
                dbtype = (String) field.get("dbtype");
                filedName = (String) field.get("id");
                if ("true".equals(ischecked) && ValidateUtil.isNotEmpty(tjfs)) {
                    if (count != 0) {
                        sql.append(",");
                    }

                    if ("1".equals(tjfs)) {//计数
                        sql.append("COUNT(");

                        if ("1".equals(dbtype)) {      //字符型
                            sql.append("NVL(").append(filedName).append(",'0'").append(")");
                        } else if ("2".equals(dbtype)) {//数字型
                            sql.append("NVL(").append(filedName).append(",0").append(")");
                        } else if ("3".equals(dbtype)) {//日期型
                            sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                        } else {
                            sql.append(filedName);
                        }
                        sql.append(")");
                    } else if ("2".equals(tjfs)) {//去重
                        sql.append("COUNT(DISTINCT ");
                        dbtype = (String) field.get("dbtype");
                        filedName = (String) field.get("id");
                        if ("1".equals(dbtype)) {      //字符型
                            sql.append("NVL(").append(filedName).append(",'0'").append(")");
                        } else if ("2".equals(dbtype)) {//数字型
                            sql.append("NVL(").append(filedName).append(",0").append(")");
                        } else if ("3".equals(dbtype)) {//日期型
                            sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                        } else {
                            sql.append(filedName);
                        }
                        sql.append(")");
                    } else if ("3".equals(tjfs)) {//求和
                        sql.append("SUM(").append(field.get("id")).append(")");
                    } else if ("4".equals(tjfs)) {//求和
                        sql.append("AVG(").append(field.get("id")).append(")");
                    } else if ("5".equals(tjfs)) {//最大
                        sql.append("MAX(").append(field.get("id")).append(")");
                    } else if ("6".equals(tjfs)) {//最小
                        sql.append("MIN(").append(field.get("id")).append(")");
                    }
                    sql.append(" AS ").append(field.get("id"));
                    fieldMap.put(filedName.toLowerCase(), field.get("tjfsname"));
                    count++;
                }
            }
            outMap.put("sql", sql.toString());
            outMap.put("tjcodes", fieldMap);//统计方式中文
        }
        return outMap;
    }

    /*
     * 根据方案ID删除统计方案
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#deleteTjfaById(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
     */
    @Override
    public Map deleteTjfaByYzb710(TaParamDto para) throws Exception {
        Map outputDomain = new HashMap();
        if (para.get("yzb710") != null) {
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
        } else {
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
    public Map saveFa(TaParamDto para) throws Exception {
        Map outputDomain = new HashMap();
        String xml = (String) para.get("xml");
        Map map = xml2Obj(xml);//xml2obj 对象
        try {
            /********************************保存查询统计方案***********************************/
            BigDecimal yzb710 = null;
            Timestamp aae036 = super.getSysTimestamp();
            if (para.get("yzb710") == null) {//如果为空新方案信息
                Zb71Domain zb71Domain = new Zb71Domain();
                yzb710 = new BigDecimal(super.getSequence("SEQ_YZB710"));//查询统计方案流水号
                String ztdm = (String) map.get("ztdm");
                Zb61Domain zb61Domain = searchParamService.getSearchByYZB611(ztdm);
                if (null == zb61Domain) {
                    return outputDomain;
                }
                zb71Domain.setYzb710(yzb710);
                zb71Domain.setYzb610(zb61Domain.getYzb610());
                zb71Domain.setYzb711((String) map.get("tjfaname"));
                zb71Domain.setYzb617(zb61Domain.getYzb617());//统计类型：分组统计
                zb71Domain.setYzb713("1");//保存期限为永久
                zb71Domain.setAae011(para.getUser().getName());
                zb71Domain.setYae116(para.getUser().getUserid());
                zb71Domain.setAae017(Long.valueOf(para.getUser().getOrgId()));
                zb71Domain.setAae036(aae036);
                dao.insert("zb71.insert", zb71Domain);//保存统计方案
            } else {//如果不为空表示已有方案信息 (保存到已有方案操作时用)
                yzb710 = (BigDecimal) para.get("yzb710");
            }
            List orList = (List) map.get("orList");
            if (ValidateUtil.isNotEmpty(orList)) {
                Zb72Domain zb72Domain = null;
                Map outMap = null;
                for (int i = 0; i < orList.size(); i++) {
                    outMap = (Map) orList.get(i);
                    /********************************保存where条件组（or条件组）***********************************/
                    zb72Domain = new Zb72Domain();
                    BigDecimal yzb720 = new BigDecimal(super.getSequence("SEQ_YZB720"));
                    zb72Domain.setYzb720(yzb720);
                    zb72Domain.setYzb710(yzb710);
                    zb72Domain.setAae036(aae036);
                    dao.insert("zb72.insert", zb72Domain);//保存where条件组

                    List andList = (List) outMap.get("andList");
                    if (ValidateUtil.isNotEmpty(andList)) {
                        List listDomain = new ArrayList();
                        Map andMap = null;
                        for (int j = 0; j < andList.size(); j++) {
                            andMap = (Map) andList.get(j);
                            /********************************保存统计方案WHERE条件（每个and）***********************************/
                            Zb73Domain zb73Domain = new Zb73Domain();
                            BigDecimal yzb730 = new BigDecimal(super.getSequence("SEQ_YZB730"));
                            zb73Domain.setYzb730(yzb730);
                            zb73Domain.setYzb710(yzb710);
                            zb73Domain.setYzb720(yzb720);
                            String sn = (String) andMap.get("sn");
                            if (ValidateUtil.isNotEmpty(sn)) {
                                zb73Domain.setYzb731(Integer.parseInt(sn));  //分组内排序号
                            } else {
                                zb73Domain.setYzb731(j);
                            }
                            zb73Domain.setYzb620(new BigDecimal((String) andMap.get("ztxmlsh")));//项目流水号
                            zb73Domain.setYzb631((String) andMap.get("gxysf"));
                            String value = (String) andMap.get("value");
                            String vtype = (String) andMap.get("vtype");
                            if ("1".equals(vtype)) {//为值类型时
                                if ("21".equals(andMap.get("dataType")) || "22".equals(andMap.get("dataType")) || "23".equals(andMap.get("dataType"))) {//代码值、树、datagrid
                                    if (ValidateUtil.isNotEmpty(value)) {
                                        String[] values = value.split(",");
                                        if (null != values && values.length > 0) {
                                            /********************************保存统计方案WHERE条件（and的代码值）***********************************/
                                            Zb74Domain zb74Domain = null;
                                            List listDomain1 = new ArrayList();
                                            for (int k = 0; k < values.length; k++) {
                                                zb74Domain = new Zb74Domain();
                                                zb74Domain.setYzb740(new BigDecimal(super.getSequence("SEQ_YZB740")));
                                                zb74Domain.setYzb730(yzb730);
                                                zb74Domain.setYzb741(values[k].replace("'", ""));//代码值
                                                zb74Domain.setAae036(aae036);
                                                listDomain1.add(zb74Domain);
                                            }
                                            if (ValidateUtil.isNotEmpty(listDomain1)) {
                                                dao.insertBatch("zb74.insert", listDomain1);
                                            }
                                        }
                                    }
                                } else {//非码值
                                    zb73Domain.setYzb733((String) andMap.get("value"));//输入框值
                                }
                            } else if ("2".equals(vtype)) {//为字段类型时
                                zb73Domain.setYzb733((String) andMap.get("vfield"));//下拉选择的字段
                            }
                            zb73Domain.setYzb734(vtype);//值类型  1固定值2项目

                            zb73Domain.setAae036(aae036);
                            listDomain.add(zb73Domain);
                        }
                        if (ValidateUtil.isNotEmpty(listDomain)) {
                            dao.insertBatch("zb73.insert", listDomain);//批量保存
                        }
                    }
                }
            }


            /********************************保存统计方案分组统计项目 ZB78 ***********************************/
            List groupFieldList = (List) map.get("groupFieldList");
            if (ValidateUtil.isNotEmpty(groupFieldList)) {
                Zb78Domain zb78Domain = null;
                List listDomain = new ArrayList();
                Map groupFieldMap = null;
                String yzb731 = null;
                for (int x = 0; x < groupFieldList.size(); x++) {
                    groupFieldMap = (Map) groupFieldList.get(x);
                    zb78Domain = new Zb78Domain();
                    BigDecimal yzb780 = new BigDecimal(super.getSequence("SEQ_YZB780"));
                    zb78Domain.setYzb780(yzb780);
                    zb78Domain.setYzb710(yzb710);
                    yzb731 = (String) groupFieldMap.get("sn");//排序号
                    if (ValidateUtil.isNotEmpty(yzb731)) {
                        zb78Domain.setYzb781(Integer.parseInt(yzb731));  //排序号
                    } else {
                        zb78Domain.setYzb781(x);
                    }
                    zb78Domain.setYzb620(new BigDecimal((String) groupFieldMap.get("ztxmlsh")));//项目流水号
                    zb78Domain.setAae036(aae036);
                    listDomain.add(zb78Domain);
                }
                if (ValidateUtil.isNotEmpty(listDomain)) {
                    dao.insertBatch("zb78.insert", listDomain);
                }
            }


            /********************************保存统计方案分组统计函数 ZB79 ***********************************/
            List functionFieldList = (List) map.get("funcitonFieldList");
            if (ValidateUtil.isNotEmpty(functionFieldList)) {
                Map functionFieldMap = null;
                Zb79Domain zb79Domain = null;
                String yzb791 = null;
                List listDomain = new ArrayList();
                for (int z = 0; z < functionFieldList.size(); z++) {
                    functionFieldMap = (Map) functionFieldList.get(z);
                    zb79Domain = new Zb79Domain();
                    BigDecimal yzb790 = new BigDecimal(super.getSequence("SEQ_YZB790"));
                    zb79Domain.setYzb790(yzb790);
                    zb79Domain.setYzb710(yzb710);
                    yzb791 = (String) functionFieldMap.get("sn");//排序号
                    if (ValidateUtil.isNotEmpty(yzb791)) {
                        zb79Domain.setYzb791(Integer.parseInt(yzb791));  //排序号
                    } else {
                        zb79Domain.setYzb791(z);
                    }
                    zb79Domain.setYzb620(new BigDecimal((String) functionFieldMap.get("ztxmlsh")));//项目流水号
                    zb79Domain.setYzb641((String) functionFieldMap.get("tjfs"));//统计方式
                    zb79Domain.setAae036(aae036);
                    listDomain.add(zb79Domain);
                }
                if (ValidateUtil.isNotEmpty(listDomain)) {
                    dao.insertBatch("zb79.insert", listDomain);//批量保存
                }
            }
            /********************************保存统计方案查询统计的项目 ZB76 （明细信息）***********************************/
            List showFieldList = (List) map.get("showFieldList");
            if (ValidateUtil.isNotEmpty(showFieldList)) {//已设置数据
                Map showFieldMap = null;
                Zb76Domain zb76Domain = null;
                String yzb761 = null, isChecked = null;
                List listDomain = new ArrayList();
                for (int z = 0; z < showFieldList.size(); z++) {
                    showFieldMap = (Map) showFieldList.get(z);
                    isChecked = (String) showFieldMap.get("ischecked");//是否显示
                    if ("true".equals(isChecked)) {//页面上勾选了显示的则保存
                        zb76Domain = new Zb76Domain();
                        zb76Domain.setYzb760(new BigDecimal(super.getSequence("SEQ_YZB760")));
                        zb76Domain.setYzb710(yzb710);//统计方案流水号
                        yzb761 = (String) showFieldMap.get("sn");//排序号
                        if (ValidateUtil.isNotEmpty(yzb761)) {
                            zb76Domain.setYzb761(Integer.parseInt(yzb761));  //排序号
                        } else {
                            zb76Domain.setYzb761(z);
                        }
                        zb76Domain.setYzb620(new BigDecimal((String) showFieldMap.get("ztxmlsh")));//项目流水号
                        zb76Domain.setYzb641((String) showFieldMap.get("tjfs"));//统计方式
                        zb76Domain.setAae036(aae036);
                        listDomain.add(zb76Domain);
                    }
                }
                if (ValidateUtil.isNotEmpty(listDomain)) {
                    dao.insertBatch("zb76.insert", listDomain);//批量保存
                }
            } else {//没有点击数字查看详情(没生成设置显示字段列表)就从配置表里读取插入
                Map inMap = new HashMap();
                inMap.put("yzb611", (String) map.get("ztdm"));
                List<Map> zb62lst = getDao().queryForList("searchparam.getMrxsls", inMap);//从数据库中查询默认配置显示的列
                if (ValidateUtil.isNotEmpty(zb62lst)) {
                    Zb76Domain zb76Domain = null;
                    Map zb62Map = null;
                    List listDomain = new ArrayList();
                    for (int i = 0; i < zb62lst.size(); i++) {
                        zb62Map = (Map) zb62lst.get(i);
                        zb76Domain = new Zb76Domain();
                        zb76Domain.setYzb760(new BigDecimal(super.getSequence("SEQ_YZB760")));
                        zb76Domain.setYzb710(yzb710);//统计方案流水号
                        if (zb62Map.get("yzb621") != null) {
                            zb76Domain.setYzb761(((BigDecimal) zb62Map.get("yzb621")).intValue());  //排序号
                        } else {
                            zb76Domain.setYzb761(i);
                        }
                        zb76Domain.setYzb620((BigDecimal) zb62Map.get("yzb620"));//项目流水号
                        zb76Domain.setYzb641(null);//统计方式
                        zb76Domain.setAae036(aae036);
                        listDomain.add(zb76Domain);
                    }
                    if (ValidateUtil.isNotEmpty(listDomain)) {
                        dao.insertBatch("zb76.insert", listDomain);//批量保存
                    }
                }
            }
            /********************************保存统计方案查询统计的项目 ZB77 （排序项目）***********************************/
            List orderList = (List) map.get("orderList");
            if (ValidateUtil.isNotEmpty(orderList)) {
                Map orderMap = null;
                Zb77Domain zb77Domain = null;
                String yzb771 = null;
                List listDomain = new ArrayList();
                for (int i = 0; i < orderList.size(); i++) {
                    orderMap = (Map) orderList.get(i);
                    zb77Domain = new Zb77Domain();
                    zb77Domain.setYzb770(new BigDecimal(super.getSequence("SEQ_YZB770")));
                    zb77Domain.setYzb710(yzb710);

                    yzb771 = (String) orderMap.get("sn");//排序号
                    if (ValidateUtil.isNotEmpty(yzb771)) {
                        zb77Domain.setYzb771(Integer.parseInt(yzb771));  //排序号
                    } else {
                        zb77Domain.setYzb771(i);
                    }
                    zb77Domain.setYzb620(new BigDecimal((String) orderMap.get("ztxmlsh")));//项目流水号
                    zb77Domain.setYzb652((String) orderMap.get("pxfs"));//排序方式
                    zb77Domain.setAae036(aae036);
                    listDomain.add(zb77Domain);
                }
                if (ValidateUtil.isNotEmpty(listDomain)) {
                    dao.insertBatch("zb77.insert", listDomain);//批量保存
                }
            }


        } catch (Exception e) {
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", "保存方案信息出错！");
            System.out.println(e.getMessage());
            throw new AppException("保存方案信息出错，" + e.getMessage());
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
        Map outputDomain = new HashMap();
        if (para.get("yzb710") == null) {
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", "yzb710不能为空！");
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
        //此处查询语句用到了分组聚合函数  各数据库有差异
        String dsType = getDsType(Constants.DEFAULT_DS_NO);
        List list;
        if (com.yinhai.cxtj.front.Constants.DSTYPE_ORACLE.equals(dsType)) {
            list = dao.queryForList("zb64.getYsfByOracle", para);
        } else if (com.yinhai.cxtj.front.Constants.DSTYPE_MYSQL.equals(dsType)) {
            list = dao.queryForList("zb64.getYsfByMysql", para);
        } else if (com.yinhai.cxtj.front.Constants.DSTYPE_POSTGRESQL.equals(dsType)) {
            list = dao.queryForList("zb64.getYsfByPG", para);
        } else if (com.yinhai.cxtj.front.Constants.DSTYPE_GBASE8A.equals(dsType)) {
            list = dao.queryForList("zb64.getYsfBy8A", para);
        } else {
            list = dao.queryForList("zb64.getYsfByOracle", para);
        }
        return list;
    }

    /*
     * 调出方案所有数据
     * (non-Javadoc)
     * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getAllData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.query.domain.Zb71Domain)
     */
    @Override
    public Map getAllData(TaParamDto para) throws Exception {
        Map odomain = new HashMap();
        Map fieldData = new HashMap();
        BigDecimal yzb710 = para.getAsBigDecimal("tjfalsh");
        if (ValidateUtil.isEmpty(yzb710)) {
            return odomain;
        }
        TreeCodeService treeCodeService = (TreeCodeService) ServiceLocator.getService("treeCodeService");
        /********************************统计方案信息***********************************/
        Map inMap = new HashMap();
        inMap.put("yzb710", yzb710);
        Map zb71Map = (Map) dao.queryForObject("zb71.getMap", inMap);

        if (null != zb71Map) {
            fieldData.put("tjfaxx", zb71Map);//存放方案信息
            fieldData.put("ztdm", zb71Map.get("yzb611"));//主题代码
        }


        /********************************and条件信息***********************************/
        Map zb73Map = new HashMap();
        List zb73List = dao.queryForList("zb73.getAndList", inMap);//where 条件


        /********************************查询条件下拉项目选择框数据***********************************/
        BigDecimal yzb610 = (BigDecimal) zb71Map.get("yzb610");//展现形式
        List<Map> cxxmList = null;
        if (yzb610 != null) {
            Map map62In = new HashMap();
            map62In.put("yzb610", yzb610);
            cxxmList = dao.queryForList("zb73.getCxxmMapList", map62In);
        }

        if (ValidateUtil.isNotEmpty(zb73List)) {
            /********************************where条件***********************************/
            BigDecimal firstYzb720 = null; //每组的组号（唯一）
            List andList = null;
            Map zb73 = null;
            String yzb628 = null;
            for (int i = 0; i < zb73List.size(); i++) {
                zb73 = (Map) zb73List.get(i);
                if (null != firstYzb720 && firstYzb720.equals(zb73.get("yzb720"))) {
                    andList.add(zb73);
                } else {
                    andList = new ArrayList();
                    firstYzb720 = (BigDecimal) zb73.get("yzb720");
                    andList.add(zb73);
                    zb73Map.put("key_" + firstYzb720, andList);
                }

                zb73.put("cxxms", cxxmList);//存放查询项目下拉信息

                /********************************项目支持的关系查询便于生成下拉关系***********************************/
                BigDecimal yzb620 = (BigDecimal) zb73.get("yzb620");//展现形式
                Map map63In = new HashMap();
                map63In.put("yzb620", yzb620);
                List<Map> gxList = dao.queryForList("zb73.getGxysMap", map63In);
                zb73.put("gxyss", gxList);//存放关系运算列表信息


                /********************************获取码值信息（已标识选中数据）***************************************************/
                String yzb62a = (String) zb73.get("yzb62a");//展现形式
                IDao idao = super.getDynamicDao(para.getAsString("yzb670"));
                if (ValidateUtil.isNotEmpty(yzb62a) && ("21".equals(yzb62a) || "23".equals(yzb62a))) {//平铺、树、datagrid
                    BigDecimal yzb730 = (BigDecimal) zb73.get("yzb730");
                    Map map73In = new HashMap();
                    map73In.put("yzb730", yzb730);
					//对应的码值应来源于对应的数据源中的aa10a1
                    String aaa100 = (String) dao.queryForObject("zb74.querycode1", map73In);
                    List<Map> codeList = idao.queryForList("zb74.querycode2", aaa100);
                    for (Map map : codeList) {
                        map.put("yzb730", yzb730);
                        Integer checked = (Integer) dao.queryForObject("zb74.querycode3", map);
                        checked = checked == 1 ? 1 : null;
                        map.put("checked",checked);
                    }
					//存放码值列表信息
                    zb73.put("codes", codeList);
                } else if ("22".equals(yzb62a)) {//树数据
                    yzb628 = (String) zb73.get("yzb628");
                    if (ValidateUtil.isNotEmpty(yzb62a) && "22".equals(yzb62a) && ValidateUtil.isNotEmpty(yzb628)) {//其它的树
                        zb73.put("treeData", JSonFactory.bean2json(treeCodeService.getTreeDataByType(yzb628, para.getAsString("yzb670"))));//存放完整树数据
                    }
                    //获取选择的树数据
                    BigDecimal yzb730 = (BigDecimal) zb73.get("yzb730");
                    Map map73In = new HashMap();
                    map73In.put("yzb730", yzb730);
                    List<Map> list = dao.queryForList("zb74.getMapList", map73In);
                    //选择的树数据对应的码值应该来源于对应的数据源的视图apptreecod
                    for (Map m : list) {
                        String yzb741_name = (String) idao.queryForObject("zb74.queryTreecode", m);
                        m.put("yzb741_name", yzb741_name);
                    }
                    zb73.put("chekedTreeData", JSonFactory.bean2json(list));//选择的树数据
                }
            }
            fieldData.put("orsMap", zb73Map);//orsMap分组存放and信息
        } else {//没有查询条件时默认生成一组or条件(条件下拉框数据源)
            fieldData.put("cxxms", cxxmList);//存放查询项目下拉信息
        }
        /********************************分组项目信息(zb78)***********************************/
        List<Map> mapList = dao.queryForList("zb78.getFzxmxxMapList", inMap);//根据统计方案流水号(yzb710)获取分组统计项目
        if (ValidateUtil.isNotEmpty(mapList)) {
            fieldData.put("fzxms", mapList);//分项目列表信息
        }

        /********************************统计类型信息(zb79)***********************************/
        List<Map> zb79List = dao.queryForList("zb79.getMapList", inMap);//根据统计方案流水号(yzb710)获取分组统计项目值信息
        /********************************获取可统计的项目**************************************/

        Map paraMap = new TaParamDto();
        paraMap.put("yzb610", zb71Map.get("yzb610"));
        //List<Map> tjxmList = dao.queryForList("zb79.getSearchItemByYZB611", paraMap);//统计项目list
        List<Map> tjxmList = dao.queryForList("zb79.getEnableTjjsxms", paraMap);//可统计计算的项目list
        if (ValidateUtil.isNotEmpty(zb79List)) {
            Map zb79Map = null, inMap1 = null;
            List<Map> list = null;
            for (int j = 0; j < zb79List.size(); j++) {
                zb79Map = zb79List.get(j);
                zb79Map.put("tjxms", tjxmList);//统计项目后台配置列表

                /********************************获取配置的统计方式***********************************/
                inMap1 = new HashMap();
                inMap1.put("yzb620", zb79Map.get("yzb620"));
                list = dao.queryForList("zb79.getSearchModeByYZB620", inMap1);
                zb79Map.put("tjlxs", list);//统计类型后台配置列表
            }
            fieldData.put("tjlx_rows", zb79List);//统计类型列表上选择的值
        }
        /********************************获取明细显示项目信息zb76(包含统计方式)***********************************/
        List<Map> mxxsxmList = dao.queryForList("zb76.getDetailList", inMap);//根据查询统计方案流水号获取明细显示项目信息
        fieldData.put("mxxsxms", mxxsxmList);//明细显示项目信息

        /********************************获取明细定义的项目排序信息***********************************/
        List<Map> mxpxfsList = dao.queryForList("zb77.getDetailOrderByMap", inMap);//根据查询统计方案流水号获取明细项目排序信息
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
        para.put("yzb610", para.getAsBigDecimal("yzb610"));
        para.put("yzb710", para.getAsBigDecimal("yzb710"));
        Integer start = para.getAsInteger("start");
        Integer limit = para.getAsInteger("limit");
        PageBean pb = dao.queryForPageWithCount("zb71.getList", para, start, limit);
        return pb;
    }

    /**
     * @param yzb611
     * @return
     * @throws Exception
     */
    @Override
    public Zb61Domain getDomainObjectByYzb611(String yzb611) throws Exception {
        if (!ValidateUtil.isEmpty(yzb611)) {
            Map key = new HashMap();
            key.put("yzb611", yzb611);
            return (Zb61Domain) dao.queryForObject("zb61.getList", key);
        }
        return null;
    }

    @Override
    public Zb61Domain getDomainObjectByYzb610(String yzb610) throws Exception {
        if (!ValidateUtil.isEmpty(yzb610)) {
            Map key = new HashMap();
            key.put("yzb610", new BigDecimal(yzb610));
            return (Zb61Domain) dao.queryForObject("zb61.getList", key);
        }
        return null;
    }
}

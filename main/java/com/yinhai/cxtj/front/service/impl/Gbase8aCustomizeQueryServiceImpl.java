package com.yinhai.cxtj.front.service.impl;

import com.yinhai.core.app.api.util.JSonFactory;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.domain.Zb61Domain;
import com.yinhai.cxtj.admin.domain.Zb62Domain;
import com.yinhai.cxtj.front.service.Gbase8aCustomizeQueryService;
import com.yinhai.modules.codetable.api.util.CodeTableUtil;
import com.yinhai.sysframework.persistence.ibatis.IDao;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhaohs on 2019/7/24.
 */
public class Gbase8aCustomizeQueryServiceImpl extends CommonCustomizeQueryServiceImpl implements Gbase8aCustomizeQueryService {

    /*
	 * 获取分页查询统计数据
	 * (non-Javadoc)
	 * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getStatisticalData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
	 */
    @Override
    public Map getPageStatisticalData(TaParamDto para) throws Exception {
        Map  outputDomain = new HashMap();
        String xml  = (String)para.get("xml");
        Map map = new HashMap();
        Map outmap = getGenerateSql(xml);

        if(ValidateUtil.isNotEmpty((String)outmap.get("msg"))){//有错误消息
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", (String)outmap.get("msg"));
            return outputDomain;
        }

        if(ValidateUtil.isNotEmpty((String)outmap.get("sql"))){
            String sql = (String)outmap.get("sql");
            String countSql = null;
            Integer start = para.getAsInteger("start");
            Integer limit = para.getAsInteger("limit");
            if(null != start && null != limit){
                countSql = generatePageCountSql(sql);
                sql = generatePageSql(sql,start,limit);
                System.out.println(sql);System.out.println(countSql);
            }
            map.put("sql",sql);

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
            Timestamp timestampStart= getSysTimestamp();
            Timestamp timestampEnd = null;
            try{
                //查询页数据
                List list = dynamicDao.queryForList("query.excuteSql", map);
                if (ValidateUtil.isNotEmpty(list)) {
                    outputDomain.put("list",list);
                }
                map.put("sql",countSql);
                //查询总条数
                outMap1 = (Map)dynamicDao.queryForObject("query.excuteSql", map);
                //sql 结束时间
                timestampEnd= getSysTimestamp();
                //耗时
                yzb993 = (timestampEnd.getTime() - timestampStart.getTime())/1000.00;
                yzb993 = (yzb993 < 0) ? 0.11F: yzb993;
                yzb996 += "&" + map.get("sql").toString();
            }catch (Exception e) {
                yzb994 = "1";
                yzb995 = e.toString();
                e.printStackTrace();
                throw new AppException("sql查询执行失败!!!");
            }finally {
                super.insertZb99(para,timestampStart,timestampEnd,yzb993,yzb994,yzb995,yzb996);
            }

            Map fieldData =  new HashMap();
            if(null!=outMap1.get("count")){
                fieldData.put("datacount",outMap1.get("count"));
            }

            fieldData.put("fileds",outmap.get("fileds"));
            fieldData.put("tjfalsh", outmap.get("tjfalsh"));//方案流水号
            outputDomain.put("fieldData",fieldData);           //返回页面构件datagrid
        }
        return outputDomain;
    }



    /**
     * 获取生成的统计SQL(统计查询SQL)
     * @return
     */
    private  Map getGenerateSql(String xml) throws Exception{
        Map outMap = new HashMap();//返回SQL和字段title信息
        StringBuilder sql = new StringBuilder();//sql 串
        if(xml!=null){
            Map map = super.xml2Obj(xml);//xml2obj 对象
            Map outFiledMap = this.generateStatisticalFiledSql(map);//生成filed 字段SQL
            String ztdm = (String)map.get("ztdm");
            if(ValidateUtil.isNotEmpty(ztdm)){
                sql.append("SELECT ");

                //fileds
                String queryFiledSql = (String)outFiledMap.get("queryFiledSql");
                if(ValidateUtil.isNotEmpty(queryFiledSql)){
                    sql.append(queryFiledSql);
                }

                //from table where ...
                Map generateFromWhereMap = generateFromWhereSql(map);
                String generateFromWhereSql = (String)generateFromWhereMap.get("sql");
                if(ValidateUtil.isNotEmpty(generateFromWhereSql)){
                    sql.append(generateFromWhereSql);
                }else{
                    outMap.put("msg", generateFromWhereMap.get("msg"));//错误消息
                }

                //group by(分组时用)
                String groupBySql = (String)outFiledMap.get("groupBySql");
                if(ValidateUtil.isNotEmpty(groupBySql)){
                    sql.append(" GROUP BY ").append(groupBySql);
                    //order by(分组时用)
                    sql.append(" ORDER BY ").append(groupBySql);//分组时生成order by
                }

                //order by(详细信息用的)
                String orderBySql = (String)outFiledMap.get("orderBySql");
                if(ValidateUtil.isNotEmpty(orderBySql)){
                    sql.append(" ORDER BY ").append(orderBySql);
                    outMap.put("order_fileds",(List)outFiledMap.get("ordersInfoList"));
                }
            }
            outMap.put("fileds",(List)outFiledMap.get("filedInfoList"));
            outMap.put("tjfalsh", map.get("tjfalsh"));//方案流水号
        }
        outMap.put("sql", sql.toString());

        return outMap;
    }



    /*
	 * 获取查询统计数据
	 * (non-Javadoc)
	 * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getStatisticalData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
	 */
    @Override
    public Map getStatisticalData(TaParamDto para) throws Exception {
        Map  outputDomain = new HashMap();
        String xml  = (String)para.get("xml");
        Map map = new HashMap();
        Map outmap = getGenerateSql(xml);

        if(ValidateUtil.isNotEmpty((String)outmap.get("msg"))){//有错误消息
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", (String)outmap.get("msg"));
            return outputDomain;
        }

        if(ValidateUtil.isNotEmpty((String)outmap.get("sql"))){
            System.out.println(outmap.get("sql"));
            map.put("sql",outmap.get("sql"));
            List list = getDao().queryForList("query.excuteSql", map);
            if (ValidateUtil.isNotEmpty(list)) {
                outputDomain.put("list",list);
            }
            Map fieldData =  new HashMap();
            fieldData.put("fileds",outmap.get("fileds"));
            fieldData.put("tjfalsh", outmap.get("tjfalsh"));//方案流水号
            outputDomain.put("fieldData",fieldData);//返回页面构件datagrid
        }
        return outputDomain;
    }


    /*
	 * 获取分页详细信息(点击数字、设置显示字段、设置排序字段)
	 * (non-Javadoc)
	 * @see com.yinhai.rsgl.search.query.service.CustomizeQueryService#getDetailInfoData(com.yinhai.scacomm.domain.component.comm.AccessInputDomain, com.yinhai.rsgl.search.setSearch.domain.Zb62Domain)
	 */
    @Override
    public Map getPageDetailInfoData(TaParamDto para) throws Exception {
        Map  outputDomain = new HashMap();
        String xml  = (String)para.get("xml");
        Map map = new HashMap();

        Map outmap = getGenerateDetailInfoSql(xml);

        if(ValidateUtil.isNotEmpty((String)outmap.get("msg"))){//有错误消息
            outputDomain.put("appCode", "-1");
            outputDomain.put("appMsg", (String)outmap.get("msg"));
            return outputDomain;
        }

        Map fieldData =  new HashMap();
        List list = null;
        //获取dao
        IDao dynamicDao = super.getDynamicDao(para.getAsString("yzb670"));
        //明细sql执行
        //标准SQL
        if(ValidateUtil.isNotEmpty((String)outmap.get("sql"))){
            String sql = (String)outmap.get("sql");
            String countSql = null;
            Integer start = para.getAsInteger("start");
            Integer limit = para.getAsInteger("limit");
            if(null != start && null != limit){
                countSql = generatePageCountSql(sql);
                sql = generatePageSql(sql,start,limit);
                System.out.println(sql);System.out.println(countSql);
            }
            map.put("sql",sql);

            Map outMap1 = null;
            //是否出错  默认为否
            String yzb994 = "0";
            //执行sql集
            String yzb996 = "" + map.get("sql");
            //错误信息
            String yzb995 = "";
            //耗时
            Double yzb993 = null;
            Timestamp timestampStart= getSysTimestamp();
            Timestamp timestampEnd = null;
            try{
                //分页数据
                list = dynamicDao.queryForList("query.excuteSql", map);
                map.put("sql",countSql);
                //查询总条数
                outMap1 = (Map)dynamicDao.queryForObject("query.excuteSql", map);
                //sql 结束时间
                timestampEnd= getSysTimestamp();
                //耗时
                yzb993 = (timestampEnd.getTime() - timestampStart.getTime())/1000.00;
                yzb993 = (yzb993 < 0) ? 0.11F: yzb993;
                yzb996 += "&" + map.get("sql").toString();

            }catch (Exception e) {
                yzb994 = "1";
                yzb995 = e.toString();
                e.printStackTrace();
                throw new AppException("执行sql查询出错");
            }finally {
                insertZb99(para,timestampStart,timestampEnd,yzb993,yzb994,yzb995,yzb996);
            }

            if(null != outMap1.get("count")){
                fieldData.put("datacount",outMap1.get("count"));
            }
        }

        //统计sql执行
        if(ValidateUtil.isNotEmpty((String)outmap.get("_sql"))){        //统计SQL
            System.out.println(outmap.get("_sql"));
            map.put("sql",outmap.get("_sql"));
            List _list = dynamicDao.queryForList("query.excuteSql", map);
            if (ValidateUtil.isNotEmpty(_list)) {
                if(null != fieldData){
                    Map tjdata = (Map)_list.get(0),tjcodes =  (Map)outmap.get("tjcodes");//统计行数据 || 统计名称
                    Map.Entry item = null;
                    if(null != tjdata){
                        Iterator iterator = tjdata.entrySet().iterator();
                        while(iterator.hasNext()){
                            item = (Map.Entry) iterator.next();
                            item.setValue((String)tjcodes.get(item.getKey()) + ":" + item.getValue());//统计行数据
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
        fieldData.put("fileds",outmap.get("fileds"));//显示字段（明细）
        fieldData.put("order_fileds",outmap.get("order_fileds"));//设置的排序字段（明细）
        fieldData.put("tjfalsh", outmap.get("tjfalsh"));//方案流水号
        outputDomain.put("fieldData",fieldData);//返回页面构件datagrid

        return outputDomain;
    }


    /**
     * 生成详细信息SQl，详细信息统计SQL
     * @param xml
     * @return
     * @throws Exception
     */
    private  Map getGenerateDetailInfoSql(String xml)throws Exception{
        Map outMap = new HashMap();//返回SQL和字段title信息
        StringBuilder sql = new StringBuilder();//详细信息sql 串
        StringBuilder _sql = new StringBuilder();//详细信息sql 串

        if(xml!=null){
            Map map = xml2Obj(xml);//xml2obj 对象
            Map outFiledMap = this.generateDetailInfoFiledSql(map);//生成filed 字段SQL
            String ztdm = (String)map.get("ztdm");
            if(ValidateUtil.isNotEmpty(ztdm)){

                /****************************************生成详细信息SQL************************************************/
                sql.append("SELECT ");

                //fileds
                String queryFiledSql = (String)outFiledMap.get("queryFiledSql");
                if(ValidateUtil.isNotEmpty(queryFiledSql)){
                    sql.append(queryFiledSql);
                }

                //from table where ...
                Map generateFromWhereMap = generateFromWhereSql(map);
                String generateFromWhereSql = (String)generateFromWhereMap.get("sql");
                if(ValidateUtil.isNotEmpty(generateFromWhereSql)){
                    sql.append(generateFromWhereSql);
                }else{
                    outMap.put("msg", generateFromWhereMap.get("msg"));//错误消息
                }

                //order by(详细信息用的)
                List orderList = (List)map.get("orderList");
                if(ValidateUtil.isNotEmpty(orderList)){
                    Map outOrderMap = generateOrdersSql(map);
                    String orderBySql = (String)outOrderMap.get("orderBySql");
                    if(ValidateUtil.isNotEmpty(orderBySql)){
                        sql.append(" ORDER BY ").append(orderBySql);
                        outMap.put("order_fileds",(List)outOrderMap.get("ordersInfoList"));//生成order by字段在页面上显示
                    }
                }

                outMap.put("fileds",(List)outFiledMap.get("filedInfoList"));          //生成详细信息title字段(包含统计函数)在页面上显示
                outMap.put("tjfalsh", map.get("tjfalsh"));//方案流水号

                if(ValidateUtil.isEmpty(queryFiledSql)){//没选择详细信息显示的字段时直接返回，不执行SQL
                    return outMap;
                }
                outMap.put("sql", sql.toString());//详细信息SQL

                /****************************************生成详细信息统计SQL************************************************/


                Map _outMap = generateStatisticalQueryFiledSql(map);
                String statisticalQueryFiledSql = (String)_outMap.get("sql");
                if(ValidateUtil.isNotEmpty(statisticalQueryFiledSql)){//表示没统计指标项目

                    _sql.append("SELECT ");

                    //fileds
                    _sql.append(statisticalQueryFiledSql);

                    //from table where ...
                    if(ValidateUtil.isNotEmpty(generateFromWhereSql)){
                        _sql.append(generateFromWhereSql);
                    }

                    outMap.put("tjcodes",_outMap.get("tjcodes"));//统计方式中文名称
                    outMap.put("_sql", _sql.toString());//详细信息SQL
                }
            }
        }
        return outMap;
    }


    //统计使用------------start

    /**
     * 生成统计类型字段SQL (初使统计查询时用)
     * @param data
     * @return
     * @throws Exception
     */
    private Map generateStatisticalFiledSql(Map data) throws Exception{
        Map outMap = new HashMap();
        if(null!=data){
            //存放界面展示信息
            List filedInfoList = new ArrayList();
            //sql 串
            StringBuilder sql = new StringBuilder();
            StringBuilder groupBy = new StringBuilder();
            String ztdm = (String)data.get("ztdm");
            if(ValidateUtil.isNotEmpty(ztdm)){
                //select
                /*************************分组字段*************************************/
                List groupFieldList = (List)data.get("groupFieldList");
                if (ValidateUtil.isNotEmpty(groupFieldList)) {
                    Map field = null;
                    for(int i=0; i< groupFieldList.size(); i++){
                        field = (Map)groupFieldList.get(i);
                        if(i==0){
                            sql.append(field.get("id")).append(" AS ").append(field.get("id"));
                            groupBy.append(field.get("id"));
                        }else{
                            sql.append(",").append(field.get("id")).append(" AS ").append(field.get("id"));
                            groupBy.append(",").append(field.get("id"));
                        }

                        String dataType = (String)field.get("datatype");
                        if(ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType)|| "22".equals(dataType) || "23".equals(dataType))){//代码平铺、树、datagrid
                            field.put("collectionData", CodeTableUtil.getCodeListJson((String)field.get("id"), null));
                        }
                        filedInfoList.add(field);//生成datagrid信息（分组）
                    }
                    outMap.put("groupBySql", groupBy.toString());
                }


                /*************************函数字段*************************************/
                List funcitonFieldList = (List)data.get("funcitonFieldList");
                String dbtype = null,filedName =null,tjfs = null;
                if (ValidateUtil.isNotEmpty(funcitonFieldList)) {
                    Map field = null;
                    for(int i=0;i<funcitonFieldList.size();i++){
                        field = (Map)funcitonFieldList.get(i);
                        tjfs = (String)field.get("tjfs");
                        if(i!=0){
                            sql.append(",");
                        }else{//等于0时判断是否已拼分组字段，如果已拼也需要加逗号
                            if(ValidateUtil.isNotEmpty(groupFieldList)){
                                sql.append(",");
                            }
                        }
                        if("1".equals(tjfs)){//计数
                            sql.append("COUNT(");
                            dbtype = (String)field.get("dbtype");
                            filedName = (String)field.get("id");
                            if("1".equals(dbtype)){      //字符型
                                sql.append("NVL(").append(filedName).append(",'0'").append(")");
                            }else if("2".equals(dbtype)){//数字型
                                sql.append("NVL(").append(filedName).append(",0").append(")");
                            }else if("3".equals(dbtype)){//日期型
                                sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                            }else{
                                sql.append(filedName);
                            }
                            sql.append(")");
                        }else if("2".equals(tjfs)){//去重
                            sql.append("COUNT(DISTINCT ");
                            dbtype = (String)field.get("dbtype");
                            filedName = (String)field.get("id");
                            if("1".equals(dbtype)){      //字符型
                                sql.append("NVL(").append(filedName).append(",'0'").append(")");
                            }else if("2".equals(dbtype)){//数字型
                                sql.append("NVL(").append(filedName).append(",0").append(")");
                            }else if("3".equals(dbtype)){//日期型
                                sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                            }else{
                                sql.append(filedName);
                            }
                            sql.append(")");
                        }else if("3".equals(tjfs)){//求和
                            sql.append("SUM(").append(field.get("id")).append(")");
                        }else if("4".equals(tjfs)){//求和
                            dbtype = (String)field.get("dbtype");
                            if("2".equals(dbtype)){//数字型
                                sql.append("AVG(NVL(").append(field.get("id")).append(",0))");
                            }else {
                                sql.append("AVG(").append(field.get("id")).append(")");
                            }
                        }else if("5".equals(tjfs)){//最大
                            sql.append("MAX(").append(field.get("id")).append(")");
                        }else if("6".equals(tjfs)){//最小
                            sql.append("MIN(").append(field.get("id")).append(")");
                        }
                        sql.append(" AS ").append(field.get("id")).append("_FN");
                        field.put("id", field.get("id")+"_FN");
                        filedInfoList.add(field);//生成datagrid信息（分组）
                    }
                }
                //如果没有设置分组项目和统计项目则默认查询总数
                if(ValidateUtil.isEmpty(groupFieldList) && ValidateUtil.isEmpty(funcitonFieldList)){
                    sql.append(" COUNT(1) AS COUNT_FN");
                    Map field = new HashMap();
                    field.put("id", "COUNT_FN");
                    field.put("name", "总数");
                    filedInfoList.add(field);
                }
                outMap.put("filedInfoList", filedInfoList);//字段title信息lst
            }
            outMap.put("queryFiledSql",sql.toString());//查询字段SQL
        }
        return outMap;
    }

    /**
     * 生成from和where SQL
     * @param map
     * @return
     * @throws Exception
     */
    private Map generateFromWhereSql(Map map) throws Exception{
        Map outMap = new HashMap();
        StringBuilder sql = new StringBuilder();//sql 串
        String ztdm = (String)map.get("ztdm");
        if(ValidateUtil.isNotEmpty(ztdm)){
            //from
            Zb61Domain zb61Domain  = searchParamService.getSearchByYZB611(ztdm);
            if(null!=zb61Domain){
                sql.append(" FROM ").append(zb61Domain.getYzb613());
                //where
                sql.append(" WHERE 1=1");


                //配置的基础条件SQL
                if(zb61Domain.getYzb615()!=null){
                    Map jctjMap = this.generateBaseWhereSql(map, zb61Domain.getYzb615());
                    if(ValidateUtil.isNotEmpty((String)jctjMap.get("sql"))){
                        sql.append(jctjMap.get("sql"));
                    }else{
                        if(ValidateUtil.isNotEmpty((String)jctjMap.get("msg"))){//有错误消息
                            return jctjMap;
                        }
                    }
                }

                List _andList = (List)map.get("andList");
                Map outAndMap = null;
                String id = null,value =null;

                //前台点击数字带的参数
                if (ValidateUtil.isNotEmpty(_andList)) {
                    for(int j=0;j<_andList.size();j++){
                        outAndMap = (Map)_andList.get(j);
                        id = (String)outAndMap.get("id");
                        value = (String)outAndMap.get("value");
                        if(ValidateUtil.isNotEmpty(id) && ValidateUtil.isNotEmpty(value)){
                            sql.append(" AND ").append(id.toUpperCase());
                            if("null".equals(value)){
                                sql.append(" IS NULL");
                            }else{
                                sql.append(" = ").append("'").append(value).append("'");
                            }
                        }
                    }
                }

                List orList = (List)map.get("orList");
                //前台or条件组
                if (ValidateUtil.isNotEmpty(orList)) {
                    List andList = null;Map map1 = null;
                    Map andMap = null;String datatype= null,vtype=null;

                    //查找是否有and数据
                    Boolean hasAnd = false;
                    Map tempMap = (Map)orList.get(0);
                    if(tempMap!=null){
                        List temAndList = (List)tempMap.get("andList");
                        if(ValidateUtil.isNotEmpty(temAndList)){
                            hasAnd = true;
                        }
                    }

                    if(hasAnd){//有and数据
                        sql.append(" AND (");//AND 起始括号   诸如：AND((1=1 AND 2=2) OR (3=3 AND 4=4) OR (5=5))

                        for(int i=0;i<orList.size();i++){

                            map1 = (Map)orList.get(i);
                            if(null == map1 || ValidateUtil.isEmpty((List)map1.get("andList"))) {//判断是否有andlist 没有就下一个
                                continue;
                            }

                            if(i != 0){
                                sql.append(" OR (");//OR 起始括号(非第一个加or)
                            }else{
                                sql.append("(");//OR 起始括号(第一个不加or)
                            }

                            if(null!=map1){
                                andList = (List)map1.get("andList");
                                if (ValidateUtil.isNotEmpty(andList)) {
                                    for(int j=0;j< andList.size();j++){
                                        andMap = (Map)andList.get(j);
                                        datatype= (String)andMap.get("dataType");
                                        if(j!=0){
                                            sql.append(" AND ");
                                        }
                                        if(ValidateUtil.isNotEmpty(datatype)){
                                            vtype= (String)andMap.get("vtype");
                                            if("1".equals(vtype)){//值类型
                                                if("11".equals(datatype)){//文本
                                                    if("11".equals(andMap.get("gxysf"))){//等于
                                                        sql.append(andMap.get("id"));
                                                        sql.append(" ='").append(andMap.get("value")).append("'");
                                                    }else if("12".equals(andMap.get("gxysf"))){//不等于
                                                        sql.append(andMap.get("id"));
                                                        sql.append(" !='").append(andMap.get("value")).append("'");
                                                    }else if("41".equals(andMap.get("gxysf"))){//包含
                                                        sql.append("(").append(andMap.get("id"));
                                                        sql.append(" LIKE '%").append(andMap.get("value")).append("%'");
                                                        Pattern p = Pattern.compile("\\s+");
                                                        String _value = (String)andMap.get("value");
                                                        String _valueA[] = _value.split("\\s+");
                                                        if(p.matcher(_value).find()){
                                                            for(int k = 0; k < _valueA.length; k++){
                                                                if(ValidateUtil.isNotEmpty(_valueA[k])){
                                                                    sql.append(" OR").append(" INSTR(").append(andMap.get("id")).append(",'").append(_valueA[k]).append("') > 0");
                                                                }
                                                            }
                                                        }
                                                        sql.append(")");
                                                    }else if("42".equals(andMap.get("gxysf"))){//不包含
                                                        sql.append("(").append(andMap.get("id"));
                                                        sql.append(" NOT LIKE '%").append(andMap.get("value")).append("%'");
                                                        Pattern  p = Pattern.compile("\\s+");
                                                        String _value = (String)andMap.get("value");
                                                        String _valueA[] = _value.split("\\s+");
                                                        if(p.matcher(_value).find()){
                                                            for(int k = 0; k < _valueA.length; k++){
                                                                if(ValidateUtil.isNotEmpty(_valueA[k])){
                                                                    sql.append(" AND").append(" INSTR(").append(andMap.get("id")).append(",'").append(_valueA[k]).append("') = 0");
                                                                }
                                                            }
                                                        }
                                                        sql.append(")");
                                                    }
                                                }else if("12".equals(andMap.get("dataType"))){//年月
                                                    sql.append("TO_CHAR(").append(andMap.get("id")).append(",'YYYY-MM')");
                                                    if("11".equals(andMap.get("gxysf"))){//等于
                                                        sql.append(" = ");
                                                    }else if("12".equals(andMap.get("gxysf"))){//不等于
                                                        sql.append(" != ");
                                                    }else if("21".equals(andMap.get("gxysf"))){//大于
                                                        sql.append(" > ");
                                                    }else if("22".equals(andMap.get("gxysf"))){//大于等于
                                                        sql.append(" >= ");
                                                    }else if("31".equals(andMap.get("gxysf"))){//小于
                                                        sql.append(" < ");
                                                    }else if("32".equals(andMap.get("gxysf"))){//小于等于
                                                        sql.append(" <= ");
                                                    }
                                                    sql.append("'").append(andMap.get("value")).append("'");
                                                }else if("13".equals(andMap.get("dataType"))){//日期
                                                    sql.append("TO_CHAR(").append(andMap.get("id")).append(",'YYYY-MM-DD')");
                                                    if("11".equals(andMap.get("gxysf"))){//等于
                                                        sql.append(" = ");
                                                    }else if("12".equals(andMap.get("gxysf"))){//不等于
                                                        sql.append(" != ");
                                                    }else if("21".equals(andMap.get("gxysf"))){//大于
                                                        sql.append(" > ");
                                                    }else if("22".equals(andMap.get("gxysf"))){//大于等于
                                                        sql.append(" >= ");
                                                    }else if("31".equals(andMap.get("gxysf"))){//小于
                                                        sql.append(" < ");
                                                    }else if("32".equals(andMap.get("gxysf"))){//小于等于
                                                        sql.append(" <= ");
                                                    }
                                                    sql.append("'").append(andMap.get("value")).append("'");
                                                }else if("14".equals(andMap.get("dataType"))){//数字
                                                    sql.append(andMap.get("id"));
                                                    if("11".equals(andMap.get("gxysf"))){//等于
                                                        sql.append(" = ");
                                                    }else if("12".equals(andMap.get("gxysf"))){//不等于
                                                        sql.append(" != ");
                                                    }else if("21".equals(andMap.get("gxysf"))){//大于
                                                        sql.append(" > ");
                                                    }else if("22".equals(andMap.get("gxysf"))){//大于等于
                                                        sql.append(" >= ");
                                                    }else if("31".equals(andMap.get("gxysf"))){//小于
                                                        sql.append(" < ");
                                                    }else if("32".equals(andMap.get("gxysf"))){//小于等于
                                                        sql.append(" <= ");
                                                    }
                                                    sql.append(andMap.get("value"));
                                                }else if("21".equals(andMap.get("dataType")) || "22".equals(andMap.get("dataType")) || "23".equals(andMap.get("dataType"))){//代码值、树、datagrid
                                                    sql.append(andMap.get("id"));
                                                    if("11".equals(andMap.get("gxysf")) || "41".equals(andMap.get("gxysf"))){//等于 || 包含
                                                        sql.append(" IN(");
                                                    }else if("12".equals(andMap.get("gxysf"))|| "42".equals(andMap.get("gxysf"))){//不等于 || 不包含
                                                        sql.append(" NOT IN(");
                                                    }
                                                    sql.append(andMap.get("value")).append(")");
                                                }
                                            }else if("2".equals(vtype)){//值类型为选择的字段时
                                                sql.append("TO_CHAR(").append(andMap.get("id")).append(")");
                                                if("11".equals(andMap.get("gxysf"))){//等于
                                                    sql.append(" = ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                }else if("12".equals(andMap.get("gxysf"))){//不等于
                                                    sql.append(" != ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                }else if("21".equals(andMap.get("gxysf"))){//大于
                                                    sql.append(" > ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                }else if("22".equals(andMap.get("gxysf"))){//大于等于
                                                    sql.append(" >= ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                }else if("31".equals(andMap.get("gxysf"))){//小于
                                                    sql.append(" < ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                }else if("32".equals(andMap.get("gxysf"))){//小于等于
                                                    sql.append(" <= ");
                                                    sql.append("TO_CHAR(").append(andMap.get("vfield")).append(")");
                                                }else if("41".equals(andMap.get("gxysf"))){//包含
                                                    sql.append(" IN(TO_CHAR(").append(andMap.get("vfield")).append("))");
                                                }else if("42".equals(andMap.get("gxysf"))){//不包含
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
        outMap.put("sql",sql.toString());
        return outMap;
    }

    //统计使用-------------end


    //查询明细-------------start

    /**
     * 生成明细查询字段SQL（详细信息显示字段用）
     * @param data
     * @return
     * @throws Exception
     */
    private  Map generateDetailInfoFiledSql(Map data) throws Exception{
        Map outMap = new HashMap();
        if(null!=data){
            List filedInfoList = new ArrayList();   //存放界面展示信息
            StringBuilder sql = new StringBuilder();//sql 串
            String ztdm = (String)data.get("ztdm");
            if(ValidateUtil.isNotEmpty(ztdm)){
                //select
                /*************************前台设置字段（明细显示字段）*************************************/
                List setFieldList = (List)data.get("showFieldList");
                if (ValidateUtil.isNotEmpty(setFieldList)) {
                    Map field = null;int count =0;String ischecked=null;
                    for(int i=0; i< setFieldList.size(); i++){
                        field = (Map)setFieldList.get(i);
                        ischecked = (String)field.get("ischecked");
                        if("true".equals(ischecked)){
                            if(count != 0){
                                sql.append(",");
                            }
                            sql.append(field.get("id")).append(" AS ").append(field.get("id"));
                            String dataType = (String)field.get("datatype");
                            if(ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType)|| "22".equals(dataType) || "23".equals(dataType))){//代码平铺、树、datagrid
                                field.put("collectionData", CodeTableUtil.getCodeListJson((String)field.get("id"), null));
                            }
                            count ++;
                        }
                        field.put("id", field.get("id"));
                        field.put("ztxmlsh", field.get("ztxmlsh"));//查询主题项目流水号
                        field.put("name",field.get("name"));
                        field.put("datatype",field.get("datatype"));
                        field.put("dbtype",field.get("dbtype"));//数据库字段类型
                        field.put("ischecked",field.get("ischecked"));
                        field.put("tjfs",field.get("tjfs"));

                        filedInfoList.add(field);//生成datagrid信息（包含不显示的字段，所以页面生成datagrid列头要判断，解决了生成页面设置显示字段的问题）
                    }
                }else{
                    String tjfalsh = (String)data.get("tjfalsh");//方案流水号
                    if(ValidateUtil.isEmpty(tjfalsh)){//不存在方案流水号 表示还未保存的方案，数据库后台配置表查询   (点击数字反查时执行)
                        /*************************数据库初使配置字段（明细显示字段）*************************************/
                        if(ValidateUtil.isEmpty(setFieldList)){
                            Map inMap = new HashMap();
                            inMap.put("yzb611", ztdm);
                            List<Zb62Domain> zb62lst = getDao().queryForList("searchparam.getEnableCxxsxms",inMap);
                            Zb62Domain zb62Domain = null;
                            Map field = null;String yzb62f = null;
                            if(ValidateUtil.isNotEmpty(zb62lst)){
                                for(int i=0;i< zb62lst.size();i++){
                                    field = new HashMap();
                                    zb62Domain = (Zb62Domain)zb62lst.get(i);
                                    if(i!=0){
                                        sql.append(",");
                                    }else{//等于0时判断是否已拼分组字段，如果已拼也需要加逗号
                                        if(ValidateUtil.isNotEmpty(setFieldList)){
                                            sql.append(",");
                                        }
                                    }
                                    sql.append(zb62Domain.getYzb623()).append(" AS ").append(zb62Domain.getYzb623());
                                    field.put("id", zb62Domain.getYzb623());
                                    field.put("ztxmlsh", zb62Domain.getYzb620());//查询主题项目流水号
                                    field.put("name",zb62Domain.getYzb625());
                                    field.put("datatype",zb62Domain.getYzb62a());
                                    field.put("dbtype",zb62Domain.getYzb626());//数据库字段类型
                                    yzb62f = zb62Domain.getYzb62f();
                                    if("1".equals(yzb62f)){//默认显示
                                        field.put("ischecked","true");
                                    }else if("2".equals(yzb62f)){//默认不显示
                                        field.put("ischecked","false");
                                    }
                                    String dataType = zb62Domain.getYzb62a();
                                    if(ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType)|| "22".equals(dataType) || "23".equals(dataType))){//代码平铺、树、datagrid
                                        field.put("collectionData", CodeTableUtil.getCodeListJson((String)zb62Domain.getYzb628(), null));
                                    }
                                    filedInfoList.add(field);//生成datagrid信息（分组）
                                }
                            }
                        }
                    }else{//存在方案流水号
                        /*************************存在方案流水号 表示以有保存的方案然 就从保存的方案表查询显示字段 及 配置统计函数*************************************/
                        Map mapIn = new HashMap();
                        mapIn.put("yzb710", tjfalsh);
                        List <Map> domianList = getDao().queryForList("zb76.getDetailList", mapIn);
                        if(ValidateUtil.isNotEmpty(domianList)){
                            Map field = null;int count =0;String ischecked=null;
                            for(int i=0;i<domianList.size();i++){
                                field = (Map)domianList.get(i);
                                ischecked = (String)field.get("ischecked");
                                if("true".equals(ischecked)){
                                    if(count != 0){
                                        sql.append(",");
                                    }
                                    sql.append(field.get("yzb623")).append(" AS ").append(field.get("yzb624"));

                                    String dataType = (String)field.get("yzb62a");
                                    if(ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType)|| "22".equals(dataType) || "23".equals(dataType))){//代码平铺、树、datagrid
                                        field.put("collectionData", CodeTableUtil.getCodeListJson((String)field.get("yzb628"), null));
                                    }
                                    count ++;
                                }
                                field.put("id", field.get("yzb624"));field.remove("yzb624");
                                field.put("ztxmlsh", field.get("yzb610"));field.remove("yzb610");//查询主题项目流水号
                                field.put("name",field.get("yzb625"));field.remove("yzb625");
                                field.put("datatype",field.get("yzb62a"));field.remove("yzb62a");
                                field.put("dbtype",field.get("yzb626"));field.remove("yzb626");//数据库字段类型
                                field.put("tjfs",field.get("yzb641"));field.remove("yzb641");//统计方式
                                field.put("ischecked",field.get("ischecked"));field.remove("ischecked");//是否显示字段

                                filedInfoList.add(field);//生成datagrid信息（包含不显示的字段，所以页面生成datagrid列头要判断，解决了生成页面设置显示字段的问题）
                            }
                        }else{//存在方案流水号，但是数据库没有设置的显示数据（可能是保存的时候没有点击数字查看详细）
                            /*************************数据库初使配置字段（明细显示字段）*************************************/
                            if(ValidateUtil.isEmpty(setFieldList)){
                                Map inMap = new HashMap();
                                inMap.put("yzb611", ztdm);
                                List<Zb62Domain> zb62lst = getDao().queryForList("searchparam.getEnableCxxsxms",inMap);
                                Zb62Domain zb62Domain = null;
                                Map field = null;String yzb62f = null;
                                if(ValidateUtil.isNotEmpty(zb62lst)){
                                    for(int i=0;i< zb62lst.size();i++){
                                        field = new HashMap();
                                        zb62Domain = (Zb62Domain)zb62lst.get(i);
                                        if(i!=0){
                                            sql.append(",");
                                        }else{//等于0时判断是否已拼分组字段，如果已拼也需要加逗号
                                            if(ValidateUtil.isNotEmpty(setFieldList)){
                                                sql.append(",");
                                            }
                                        }
                                        sql.append(zb62Domain.getYzb623()).append(" AS ").append(zb62Domain.getYzb623());
                                        field.put("id", zb62Domain.getYzb623());
                                        field.put("ztxmlsh", zb62Domain.getYzb620());//查询主题项目流水号
                                        field.put("name",zb62Domain.getYzb625());
                                        field.put("datatype",zb62Domain.getYzb62a());
                                        field.put("dbtype",zb62Domain.getYzb626());//数据库字段类型

                                        yzb62f = zb62Domain.getYzb62f();
                                        if("1".equals(yzb62f)){//默认显示
                                            field.put("ischecked","true");
                                        }else if("2".equals(yzb62f)){//默认不显示
                                            field.put("ischecked","false");
                                        }

                                        String dataType = zb62Domain.getYzb62a();
                                        if(ValidateUtil.isNotEmpty(dataType) && ("21".equals(dataType)|| "22".equals(dataType) || "23".equals(dataType))){//代码平铺、树、datagrid
                                            field.put("collectionData", CodeTableUtil.getCodeListJson((String)zb62Domain.getYzb628(), null));
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
            outMap.put("queryFiledSql",sql.toString());//查询字段SQL
        }
        return outMap;
    }



    /**
     * 生成order by sql
     * @param map
     * @return
     * @throws Exception
     */
    private Map generateOrdersSql(Map map)throws Exception{
        Map outMap = new HashMap();
        StringBuilder sql = new StringBuilder();
        List ordersInfoList = new ArrayList();
        if(null != map){
            List orderList = (List)map.get("orderList");
            if(ValidateUtil.isNotEmpty(orderList)){//不为空表示页面上设置的详细排序规则
                /*************************明细 页面设置的排序数据*************************************/
                if (ValidateUtil.isNotEmpty(orderList)) {
                    Map orderField = null; Map field = null;
                    for(int i=0; i< orderList.size(); i++){
                        orderField = new HashMap();
                        field = (Map)orderList.get(i);
                        if(i!= 0){
                            sql.append(",");
                        }
                        sql.append(field.get("id"));
                        String pxfs = (String)field.get("pxfs");
                        if(ValidateUtil.isNotEmpty(pxfs)){
                            if("1".equals(pxfs)){       //升序
                                sql.append(" ASC");
                            }else if("2".equals(pxfs)){ //降序
                                sql.append(" DESC");
                            }
                        }

                        orderField.put("id", field.get("id"));
                        orderField.put("ztxmlsh", field.get("ztxmlsh"));//查询主题项目流水号
                        orderField.put("name",field.get("name"));
                        orderField.put("datatype",field.get("datatype"));
                        orderField.put("dbtype",field.get("dbtype"));//数据库字段类型
                        orderField.put("pxfs",field.get("pxfs"));
                        ordersInfoList.add(orderField);//生成datagrid信息
                    }
                    outMap.put("orderBySql", sql.toString());
                }

            }else{//为空表示可能初次查询 或者 有保存方案
                String tjfalsh = (String)map.get("tjfalsh");//方案流水号
                if(ValidateUtil.isNotEmpty(tjfalsh)){//有方案则从之前保存的方案中获取详细排序信息
                    /*************************明细排序数据(存在方案流水号 就从保存的方案表查询排序数据)*************************************/
                    Map mapIn = new HashMap();
                    mapIn.put("yzb710", tjfalsh);
                    List <Map> ordersList = getDao().queryForList("zb77.getDetailOrderByMap", mapIn);
                    if(ValidateUtil.isNotEmpty(ordersList)){
                        Map outZb77Map = null;Map orderField = null;
                        for(int i=0;i<ordersList.size();i++){
                            orderField = new HashMap();
                            outMap = ordersList.get(i);
                            if(i!=0){
                                sql.append(",");
                            }
                            sql.append(outZb77Map.get("yzb623"));
                            String yzb652 = (String)outZb77Map.get("yzb652");
                            if(ValidateUtil.isNotEmpty(yzb652)){
                                if("1".equals(yzb652)){       //升序
                                    sql.append(" ASC");
                                }else if("2".equals(yzb652)){ //降序
                                    sql.append(" DESC");
                                }
                            }

                            orderField.put("id", outZb77Map.get("yzb624"));
                            orderField.put("ztxmlsh", outZb77Map.get("yzb610"));//查询主题项目流水号
                            orderField.put("name",outZb77Map.get("yzb625"));
                            orderField.put("pxfs",outZb77Map.get("yzb652"));
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
     *
     * 生成详细信息统计查询SQL字段
     * @param data
     * @return
     */
    private  Map generateStatisticalQueryFiledSql(Map data){
        /*************************前台设置字段（明细显示字段）*************************************/
        StringBuilder sql = new StringBuilder();Map outMap = new HashMap(),fieldMap  = new HashMap();
        List setFieldList = (List)data.get("showFieldList");
        if (ValidateUtil.isNotEmpty(setFieldList)) {
            Map field = null;int count =0;String ischecked=null,tjfs=null,dbtype=null,filedName=null;
            for(int i=0; i< setFieldList.size(); i++){
                field = (Map)setFieldList.get(i);
                ischecked = (String)field.get("ischecked");
                tjfs = (String)field.get("tjfs");
                dbtype = (String)field.get("dbtype");
                filedName = (String)field.get("id");
                if("true".equals(ischecked) && ValidateUtil.isNotEmpty(tjfs)){
                    if(count != 0){
                        sql.append(",");
                    }

                    if("1".equals(tjfs)){//计数
                        sql.append("COUNT(");

                        if("1".equals(dbtype)){      //字符型
                            sql.append("NVL(").append(filedName).append(",'0'").append(")");
                        }else if("2".equals(dbtype)){//数字型
                            sql.append("NVL(").append(filedName).append(",0").append(")");
                        }else if("3".equals(dbtype)){//日期型
                            sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                        }else{
                            sql.append(filedName);
                        }
                        sql.append(")");
                    }else if("2".equals(tjfs)){//去重
                        sql.append("COUNT(DISTINCT ");
                        dbtype = (String)field.get("dbtype");
                        filedName = (String)field.get("id");
                        if("1".equals(dbtype)){      //字符型
                            sql.append("NVL(").append(filedName).append(",'0'").append(")");
                        }else if("2".equals(dbtype)){//数字型
                            sql.append("NVL(").append(filedName).append(",0").append(")");
                        }else if("3".equals(dbtype)){//日期型
                            sql.append("NVL(").append(filedName).append(",SYSDATE").append(")");
                        }else{
                            sql.append(filedName);
                        }
                        sql.append(")");
                    }else if("3".equals(tjfs)){//求和
                        sql.append("SUM(").append(field.get("id")).append(")");
                    }else if("4".equals(tjfs)){//求和
                        sql.append("AVG(").append(field.get("id")).append(")");
                    }else if("5".equals(tjfs)){//最大
                        sql.append("MAX(").append(field.get("id")).append(")");
                    }else if("6".equals(tjfs)){//最小
                        sql.append("MIN(").append(field.get("id")).append(")");
                    }
                    sql.append(" AS ").append(field.get("id"));
                    fieldMap.put(filedName.toLowerCase(), field.get("tjfsname"));
                    count ++;
                }
            }
            outMap.put("sql", sql.toString());
            outMap.put("tjcodes", fieldMap);//统计方式中文
        }
        return outMap;
    }


    //查询明细-------------end


    /**
     * 生成分页SQL语句
     * @param sql
     * @param start
     * @param limit
     * @return
     */
    private String generatePageSql(String sql,Integer start,Integer limit){
        if(null != start && null != limit){
            StringBuilder newSql = new StringBuilder();
            newSql.append("SELECT * FROM (SELECT ROWNUM AS MYROWNUM, C.* FROM (")
                    .append(sql)
                    .append(") C) WHERE MYROWNUM <= ")
                    .append(limit + start).append(" AND MYROWNUM > ")
                    .append(start);
            return newSql.toString();
        }else{
            return sql;
        }
    }
    /**
     * 生成分页SQL总条数语句
     * @param sql
     * @return
     */
    private String generatePageCountSql(String sql){
        if(ValidateUtil.isNotEmpty(sql)){
            StringBuilder newSql = new StringBuilder();
            newSql.append("SELECT COUNT(1) AS COUNT FROM (").append(sql).append(") A");
            return newSql.toString();
        }
        return sql;
    }


    /**
     * 生成基础条件SQL
     * @param map
     * @return
     */
    private Map generateBaseWhereSql(Map map,String jctjTemplate){
        StringBuilder msg = new StringBuilder(),sql = new StringBuilder();//错误消息,sql 串
        Map outMap  = new HashMap();Map jctjMap =  null;
        String jctj = (String)map.get("jctj");//基础条件值
        if(ValidateUtil.isNotEmpty(jctj)){
            jctjMap = JSonFactory.json2bean(jctj, Map.class);
        }
        if(ValidateUtil.isNotEmpty(jctjTemplate)){//b0100=#b0100# and aae017=#aae017# and yb0162=#yb0162#
            Pattern  p = Pattern.compile("(#[0-9a-zA-Z]{1,10}#)");
            Matcher m = p.matcher(jctjTemplate);String key = null,newKey=null,value=null;int i=0;
            while(m.find()){
                key = m.group(1);
                if(ValidateUtil.isNotEmpty(key)){
                    newKey = key.replaceAll("#", "").toLowerCase();
                    if(null != jctjMap){
                        value = (String)jctjMap.get(newKey);
                        if(ValidateUtil.isEmpty(value)){
                            if(i==0){
                                msg.append(newKey);
                            }else{
                                msg.append(",").append(newKey);
                            }
                            i++;
                        }else{
                            jctjTemplate = jctjTemplate.replace(key,"'"+(String)jctjMap.get(newKey)+"'");
                        }
                    }
                }
            }
            if(i>0){
                msg.append("，这些字段值需通过菜单URL配置传入才能进行查询！");
                outMap.put("msg",msg.toString());
                return outMap;
            }
            sql.append(" AND ").append(jctjTemplate);
            outMap.put("sql",sql.toString());
        }
        return outMap;
    }
}

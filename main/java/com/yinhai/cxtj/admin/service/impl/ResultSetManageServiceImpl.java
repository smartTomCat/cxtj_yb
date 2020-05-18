package com.yinhai.cxtj.admin.service.impl;

import com.yinhai.core.common.api.util.ReflectUtil;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.Zb69Domain;
import com.yinhai.cxtj.admin.service.ResultSetManageService;
import com.yinhai.cxtj.front.Constants;
import com.yinhai.sysframework.persistence.PageBean;
import com.yinhai.sysframework.persistence.ibatis.IDao;
import java.util.List;
import java.util.Map;

public class ResultSetManageServiceImpl extends CxtjBaseServiceImpl implements ResultSetManageService {

    @Override
    public PageBean queryResultSets(TaParamDto dto) throws Exception {
        PageBean pageBean = dao.queryForPageWithCount("grid1", "zb69.queryResultSets", dto, dto);
        return pageBean;
    }

    @Override
    public List<Map> genTableTree(String yzb670, String yzb672) throws Exception {
        String datasourceType = getDsType(yzb670);
        IDao iDao = getDynamicDao(yzb670);
        List<Map> tableList;
        if(Constants.DSTYPE_ORACLE.equals(datasourceType)) {
            tableList = iDao.queryForList("zb69.queryAllTableByOracle");
            return tableList;
        }
        if(Constants.DSTYPE_MYSQL.equals(datasourceType)) {
            tableList = iDao.queryForList("zb69.queryAllTableByMysql");
            return tableList;
        }
        tableList = iDao.queryForList("zb69.queryAllTableByMysql");
        return tableList;
    }

    @Override
    public List<Map> queryTableColumns(String yzb670, String[] tableNames) throws Exception {
        String datasourceType = getDsType(yzb670);
        IDao iDao = getDynamicDao(yzb670);
        List<Map> columnList;
        if(Constants.DSTYPE_ORACLE.equals(datasourceType)) {
            columnList = iDao.queryForList("zb69.queryTableColumnsByOracleget", tableNames);
            return columnList;
        }
        if(Constants.DSTYPE_MYSQL.equals(datasourceType)) {
            columnList = iDao.queryForList("zb69.queryTableColumnsByMysql", tableNames);
            return columnList;
        }
        columnList = iDao.queryForList("zb69.queryTableColumnsByMysql", tableNames);
        return columnList;
    }

    @Override
    public void saveResultSet(TaParamDto dto, List<Map> columnsList) throws Exception {
        Zb69Domain zb69Domain = new Zb69Domain();
        String yzb694 = genSql(columnsList, dto.getAsString("yzb695"), dto.getAsString("yzb693"));
        zb69Domain.setYzb694(yzb694);
        zb69Domain.setAae011(dto.getUser().getName());
        zb69Domain.setYae116(dto.getUser().getUserid());
        zb69Domain.setAae017(Long.valueOf(dto.getUser().getOrgId()));
        zb69Domain.setAae036(super.getSysTimestamp());
        ReflectUtil.copyMapToObject(dto, zb69Domain, true);
        String yzb690 = dto.getAsString("yzb690");
        if (ValidateUtil.isEmpty(yzb690)) {
            //新增 zb69
            yzb690 = super.getSequence("SEQ_YZB690");
            zb69Domain.setYzb690(yzb690);
            dao.insert("zb69.insert", zb69Domain);
        } else {
            //编辑zb69
            dao.update("zb69.updateNotEmpty",zb69Domain);
            //清zb89
            dao.delete("zb89.deleteByYzb690", dto);
        }
        //新增 zb89
        for (Map m : columnsList) {
            String yzb890 = super.getSequence("SEQ_YZB890");
            m.put("yzb890", yzb890);
            m.put("yzb690", yzb690);
        }
        dao.insertBatch("zb89.insert", columnsList);

    }

    /**
     * 构造数据集sql
     *
     * @param columnsList 列
     * @param yzb695      表
     * @param yzb693      关联条件
     * @return
     */
    private String genSql(List<Map> columnsList, String yzb695, String yzb693) {
        StringBuilder resultSetSql = new StringBuilder("select ");
        for (Map m : columnsList) {
            String col1 = (String) m.get("yzb891");
            String col2 = (String) m.get("yzb892");
            //select emp.empid,emp.deptno,dept.deptname,
            resultSetSql.append(col1).append(".").append(col2).append(",");
        }
        resultSetSql.setCharAt(resultSetSql.lastIndexOf(","), ' ');
        resultSetSql.append(" from ").append(yzb695);
        resultSetSql.append(" where ").append(yzb693);
        return resultSetSql.toString();
    }


    @Override
    public void deleteResultSets(List<Map> list) throws Exception {
        if (ValidateUtil.isNotEmpty(list)) {
            for (Map m : list) {
                dao.delete("zb69.deleteById", m);
                dao.delete("zb89.deleteByYzb690", m);
            }
        }
    }

    @Override
    public List<Map> queryZb69ByYzb690(String yzb690) throws Exception {
        return dao.queryForList("zb69.queryZb69ByYzb690",yzb690);
    }

    @Override
    public List<Map> queryZb89ByYzb690(String yzb690) throws Exception {
        return dao.queryForList("zb89.queryZb89ByYzb690",yzb690);
    }

    @Override
    public Boolean checkNameExist(String yzb691) throws Exception {
        List list = dao.queryForList("zb69.checkNameExist",yzb691);
        return list.size() != 0 ? true : false;
    }
}

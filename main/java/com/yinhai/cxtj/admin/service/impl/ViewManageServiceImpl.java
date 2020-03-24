package com.yinhai.cxtj.admin.service.impl;

import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ReflectUtil;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.Zb68Domain;
import com.yinhai.cxtj.admin.service.ViewManageService;
import com.yinhai.cxtj.front.Constants;
import com.yinhai.sysframework.persistence.PageBean;
import com.yinhai.sysframework.persistence.ibatis.IDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2020/3/17.
 */
public class ViewManageServiceImpl extends CxtjBaseServiceImpl implements ViewManageService {

    /**
     * 视图保存 新增保存|编辑保存
     *
     * @param dto
     * @return yzb680
     * @throws Exception
     */
    @Override
    public String toSave(TaParamDto dto) throws Exception {
        Zb68Domain zb68Domain = new Zb68Domain();
        ReflectUtil.copyMapToObject(dto, zb68Domain, true);
        zb68Domain.setAae011(dto.getUser().getName());
        zb68Domain.setYae116(dto.getUser().getUserid());
        zb68Domain.setAae017(Long.valueOf(dto.getUser().getOrgId()));
        zb68Domain.setAae036(super.getSysTimestamp());

        String yzb680 = dto.getAsString("yzb680");
        if (ValidateUtil.isEmpty(yzb680)) {
            //新增
            String _yzb610 = super.getSequence("SEQ_YZB680");
            zb68Domain.setYzb680(new BigDecimal(_yzb610));
            dao.insert("zb68.insert", zb68Domain);
            //去相应数据源创建改视图
            creatView(dto);
        } else {
            //修改
            dao.update("zb68.update", zb68Domain);
            //去相应数据源修改视图
            creatView(dto);
        }

        return null;
    }

    /**
     * 去相应数据源创建|修改改视图
     *
     * @param dto
     * @throws Exception
     */
    private void creatView(TaParamDto dto) throws Exception {
        IDao iDao = getDynamicDao(dto.getAsString("yzb670"));
        //建表语句需处理; 切分成多条语句
        String sql = dto.getAsString("yzb682");
        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        if (sql.contains(";")) {
            String[] sqls = sql.split(";");
            for (String sqlItem : sqls) {
                if ("\n".equals(sqlItem) || "\r".equals(sqlItem)) {
                    continue;
                }
                try {
                    iDao.update("zb68.dllView", sqlItem);
                } catch (Exception e) {
                    throw new AppException("sql执行出错，请检查sql语句规范");
                }
            }
        } else {
            try {
                iDao.update("zb68.dllView", sql);
            } catch (Exception e) {
                throw new AppException("sql执行出错，请检查sql语句规范");
            }
        }

    }


    @Override
    public void toRemove(TaParamDto dto, List list) throws Exception {
        if (ValidateUtil.isNotEmpty(list)) {
            Map m = null;
            for (int i = 0; i < list.size(); i++) {
                m = (Map) list.get(i);
                dao.delete("zb68.delete", m);
                //去相应数据源删除视图
                dropView(m);
            }
        }
    }

    /**
     * 去相应数据源删除视图
     * @param m
     * @throws Exception
     */
    private void dropView(Map m) throws Exception {
        IDao iDao = getDynamicDao(String.valueOf(m.get("yzb670")));
        //获取视图名称
        String viewName = String.valueOf(m.get("yzb681"));
        String sql = "drop view " + viewName;
        //TODO 异常处理
        iDao.update("zb68.dllView", sql);
    }


    @Override
    public PageBean queryViews(TaParamDto dto) throws Exception {
        PageBean bean = dao.queryForPageWithCount("grid1", "zb68.queryViews", dto, dto);
        return bean;
    }


    /**
     * 视图名称是否已存在
     *
     * @param dto
     * @return true 存在
     * @throws Exception
     */
    @Override
    public Boolean checkNameExist(TaParamDto dto) throws Exception {
        //先检查平台框架库内zb68同一数据源是否已经存在该视图名
        Integer count1 = (Integer) dao.queryForObject("zb68.checkZb68", dto);
        if (0 != count1) {
            return true;
        }

        //再检查是否存在对应数据源中的库中
        String dsType = getDsType(dto.getAsString("yzb670"));
        String namespace;
        if (Constants.DSTYPE_ORACLE.equals(dsType)) {
            namespace = "zb68.checkViewNameByOracle";
        } else if (Constants.DSTYPE_MYSQL.equals(dsType)) {
            namespace = "zb68.checkViewNameByMysql";
        } else if (Constants.DSTYPE_POSTGRESQL.equals(dsType)) {
            namespace = "zb68.checkViewNameByPg";
        } else if (Constants.DSTYPE_GBASE8A.equals(dsType)) {
            namespace = "zb68.checkViewNameByGbase";
        } else {
            namespace = "zb68.checkViewNameByOracle";
        }
        IDao iDao = getDynamicDao(dto.getAsString("yzb670"));
        Integer count2 = (Integer) iDao.queryForObject(namespace, dto);
        if (0 != count2) {
            return true;
        }

        //都不存在
        return false;
    }

}

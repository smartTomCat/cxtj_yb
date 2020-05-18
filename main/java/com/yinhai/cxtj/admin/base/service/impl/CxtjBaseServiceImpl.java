package com.yinhai.cxtj.admin.base.service.impl;

import com.yinhai.core.app.api.util.ServiceLocator;
import com.yinhai.core.common.api.config.impl.SysConfig;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.core.service.ta3.domain.service.TaBaseService;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.Zb67Domain;
import com.yinhai.cxtj.admin.service.SearchParamService;
import com.yinhai.cxtj.admin.util.DynamicDataLoad;
import com.yinhai.cxtj.front.Constants;
import com.yinhai.sysframework.persistence.ibatis.IDao;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

public class CxtjBaseServiceImpl extends TaBaseService  implements CxtjBaseService {

    @Resource
    DynamicDataLoad dynamicDataLoad;

    public Boolean testConnection(Zb67Domain zb67Domain) throws Exception{
        Boolean b = dynamicDataLoad.testConnection(zb67Domain);
        return b;
    }

    @Override
    public IDao getDynamicDao(String yzb670) throws Exception {
        IDao dynamicDao = dynamicDataLoad.getDynamicDao(yzb670);
        return dynamicDao;
    }

    public void creatDynamicDao(Zb67Domain zb67Domain) throws Exception {
        dynamicDataLoad.creatDao(zb67Domain);
    }

    public void updateDynamicDao(Zb67Domain zb67Domain) throws Exception {
        dynamicDataLoad.updateDaoDataSource(zb67Domain);
    }

    public void removeDynamicDao(String yzb670) throws Exception {
        dynamicDataLoad.destroyDaoDataSource(yzb670);
    }

    /**
     * 根据数据源流水号获取数据库类型
     * @param yzb670
     * @return
     * @throws Exception
     */
    @Override
    public String  getDsType(String yzb670) throws Exception {
        Connection connection = null;
        try {
            IDao dynamicDao = getDynamicDao(yzb670);
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


    @Override
    public String getSequence(String seqName) {
        if ("rds".equals(SysConfig.getSysConfig("idGenerator"))) {
            return String.valueOf(dao.queryForObject("zb62.genIdByRds",seqName));
        }
        return super.getSequence(seqName);
    }
}

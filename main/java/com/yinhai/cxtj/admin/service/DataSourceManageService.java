package com.yinhai.cxtj.admin.service;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.Zb67Domain;
import com.yinhai.sysframework.persistence.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2019/7/17.
 */
public interface DataSourceManageService extends CxtjBaseService {

    public PageBean queryList(TaParamDto dto) throws Exception;

    public Boolean checkNameExist(TaParamDto dto) throws Exception;

    public Boolean testConnect(TaParamDto dto) throws Exception;

    public Zb67Domain saveDataSource(TaParamDto dto) throws Exception;

    public Map queryItemDs(TaParamDto dto) throws Exception;

    public void updateDataSource(TaParamDto dto) throws Exception;

    public void deleteDataSource(List list) throws  Exception;

    public Boolean dshasZt(List list) throws Exception;

}

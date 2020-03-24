package com.yinhai.cxtj.admin.service;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.Zb99Domain;
import com.yinhai.sysframework.persistence.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2019/7/19.
 */
public interface SqlLogManageService extends CxtjBaseService{

    public List<Map> queryZt(TaParamDto dto) throws Exception;

    public List<Map> queryFa(TaParamDto dto) throws Exception;

    public PageBean queryLog(TaParamDto dto) throws Exception;

}

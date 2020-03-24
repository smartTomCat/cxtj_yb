package com.yinhai.cxtj.admin.service.impl;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.Zb99Domain;
import com.yinhai.cxtj.admin.service.SqlLogManageService;
import com.yinhai.sysframework.persistence.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2019/7/19.
 */
public class SqlLogManageServiceImpl extends CxtjBaseServiceImpl implements SqlLogManageService {

    @Override
    public List<Map> queryZt(TaParamDto dto) throws Exception {
        List<Map> ztList = dao.queryForList("zb61.queryZt",dto);
        return ztList;
    }

    @Override
    public List<Map> queryFa(TaParamDto dto) throws Exception {
        List<Map> faList = dao.queryForList("zb71.queryFaByZt",dto);
        return faList;
    }

    @Override
    public PageBean queryLog(TaParamDto dto) throws Exception {
        PageBean pageBean = dao.queryForPageWithCount("grid1","zb99.getList",dto,dto);
        return pageBean;
    }


}

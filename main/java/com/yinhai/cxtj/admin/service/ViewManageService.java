package com.yinhai.cxtj.admin.service;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.sysframework.persistence.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2020/3/17.
 */
public interface ViewManageService extends CxtjBaseService {

    /**
     * 保存
     * @param dto
     * @return
     * @throws Exception
     */
    String toSave(TaParamDto dto) throws Exception;

    /**
     * 删除
     * @param dto
     * @throws Exception
     */
    void toRemove(TaParamDto dto,List list) throws Exception;

    /**
     * 查询
     * @param dto
     * @return
     * @throws Exception
     */
    PageBean queryViews(TaParamDto dto) throws Exception;


    /**
     * 检查视图名称是否已存在
     * @param dto
     * @return
     * @throws Exception
     */
    public Boolean checkNameExist(TaParamDto dto) throws Exception;
}

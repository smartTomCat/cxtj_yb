package com.yinhai.cxtj.admin.base.service;

import com.yinhai.core.domain.api.domain.service.IService;
import com.yinhai.sysframework.persistence.ibatis.IDao;

public interface CxtjBaseService extends IService {

    IDao getDynamicDao(String yzb670) throws Exception;

    String  getDsType(String yzb670) throws Exception;

}

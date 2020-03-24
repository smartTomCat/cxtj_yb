package com.yinhai.cxtj.front.service;

import com.yinhai.core.common.ta3.dto.TaParamDto;

import java.util.Map;

/**
 * Created by zhaohs on 2019/7/24.
 */
public interface Gbase8aCustomizeQueryService extends CommonCustomizeQueryService {

    /**
     * 获取统计数据
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public Map getStatisticalData(TaParamDto para) throws Exception ;
    /**
     * 获取分页统计数据
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public Map getPageStatisticalData(TaParamDto para) throws Exception;

    /**
     * 获取分页详细信息数据
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public Map getPageDetailInfoData(TaParamDto para) throws Exception;

}

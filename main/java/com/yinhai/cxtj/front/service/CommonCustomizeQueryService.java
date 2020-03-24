package com.yinhai.cxtj.front.service;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.Zb61Domain;
import com.yinhai.sysframework.persistence.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2019/7/24.
 */
public interface CommonCustomizeQueryService extends CxtjBaseService {


    /**
     * 获取条件项目
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     * @throws Exception
     */
    public List getCxztxmList(TaParamDto para) throws Exception;

    public List getXmgxysfList(TaParamDto para) throws Exception;

    public Map getXmxx(TaParamDto para) throws Exception;

    /**
     * 根据主键删除统计方案
     * @param accessInputDomain
     * @param zb71Domain
     * @return
     * @throws Exception
     */
    public Map deleteTjfaByYzb710(TaParamDto para) throws Exception;

    /**
     * 查询可统计主题项目
     * @param accessInputDomain
     * @param zb62Domain
     * @throws Exception
     */
    public List getKtjcxztxmList(TaParamDto para) throws Exception;

    /**
     * 查询可分组计算的指标项目
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public List getFzzbxmList(TaParamDto para) throws Exception;

    /**
     * 获取支持分组计算的函数
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public List getXmzctjys(TaParamDto para) throws Exception;


    /**
     * 获取统计项目支持的运算符
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public List getItemYsf(TaParamDto para)throws Exception;

    /**
     * 更新方案
     * @param accessInputDomain
     * @param zb71Domain
     * @return
     * @throws Exception
     */
    public Map updateFa(TaParamDto para) throws Exception;

    /**
     * 保存方案
     * @param accessInputDomain
     * @param zb71Domain
     * @return
     */
    public Map saveFa(TaParamDto para) throws Exception;

    /**
     * 调出方案时获取所有方案信息
     * @param accessInputDomain
     * @param zb71Domain
     * @return
     */
    public Map getAllData(TaParamDto para) throws Exception;

    /**
     * 查询统计方案
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public PageBean getTjfa(TaParamDto para) throws Exception;

    /**
     * 查询默认分组计算的项目
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     * @throws Exception
     */
    public List getMrfzjsxmList(TaParamDto para) throws Exception;

    /**
     * 获取可排序的项目(明细)
     * @param accessInputDomain
     * @param zb62Domain
     * @return
     */
    public List getEnablePxxmList(TaParamDto para) throws Exception;

    /**
     *
     * @param yzb611
     * @return
     * @throws Exception
     */
    public Zb61Domain getDomainObjectByYzb611(String yzb611) throws Exception;


    public Zb61Domain getDomainObjectByYzb610(String yzb610) throws Exception;

}

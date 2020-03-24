package com.yinhai.cxtj.front.service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.Zb61Domain;
import com.yinhai.sysframework.persistence.PageBean;

/**
 * 自定义查询接口
 * @author: 银海人事人才项目组(XXX)
 * @Copyright: 2016-2017 银海软件所有
 * @date: 2016-07-15 下午7:38:10
 * @vesion: 1.0
 */
public interface CustomizeQueryService extends CxtjBaseService{
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
	 * 获取详细信息数据
	 * @param accessInputDomain
	 * @param zb62Domain
	 * @return
	 */
	public Map getDetailInfoData(TaParamDto para) throws Exception ;
	/**
	 * 获取分页详细信息数据
	 * @param accessInputDomain
	 * @param zb62Domain
	 * @return
	 */
	public Map getPageDetailInfoData(TaParamDto para) throws Exception;
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

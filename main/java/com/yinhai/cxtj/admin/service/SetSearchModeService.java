package com.yinhai.cxtj.admin.service;

import java.math.BigDecimal;
import java.util.Map;

import com.yinhai.core.common.api.context.ISysUser;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;


/**
 * 查询统计主题项目支持的计算方式
 * 
 * @author 
 */
public interface SetSearchModeService extends CxtjBaseService {

	/**
	 * 查询支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	public Map querySearchMode(BigDecimal yzb620) throws Exception;

	/**
	 * 保存支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @param yzb641
	 *            支持的计算方式
	 * @param user
	 * @throws Exception
	 */
	public void saveSearchMode(BigDecimal yzb620, String[] yzb641, ISysUser user) throws Exception;

	/**
	 * 删除支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	public int removeSearch(BigDecimal yzb620) throws Exception;

}
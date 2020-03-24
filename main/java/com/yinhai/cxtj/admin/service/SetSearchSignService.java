package com.yinhai.cxtj.admin.service;

import java.math.BigDecimal;
import java.util.Map;


import com.yinhai.core.common.api.context.ISysUser;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;


/**
 * 查询统计主题项目支持的关系符
 * 
 * @author 
 */
public interface SetSearchSignService extends CxtjBaseService {

	/**
	 * 查询支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	public Map querySearchSign(BigDecimal yzb620) throws Exception;

	/**
	 * 保存支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @param yzb631
	 *            支持的关系符
	 * @param user
	 * @throws Exception
	 */
	public void saveSearchSign(BigDecimal yzb620, String[] yzb631, ISysUser user) throws Exception;

	/**
	 * 删除支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	public int removeSearch(BigDecimal yzb620) throws Exception;

}
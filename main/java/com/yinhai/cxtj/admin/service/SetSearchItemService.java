package com.yinhai.cxtj.admin.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.yinhai.core.common.ta3.dto.TaParamDto;

import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.Zb62Domain;


/**
 * 查询统计主题的项目
 * 
 * @author 何涛
 */
public interface SetSearchItemService extends CxtjBaseService {

	/**
	 * 查询配制项目
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	public Zb62Domain getDomainObjectById(BigDecimal yzb620) throws Exception;

	/**
	 * 查询配制项目
	 * 
	 * @author 
	 * @param yzb610
	 *            查询统计主题流水号
	 * @return
	 * @throws Exception
	 */
	public List querySearchItems(BigDecimal yzb610) throws Exception;

	/**
	 * 保存批量配制项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto saveSearchItems(TaParamDto dto) throws Exception;

	/**
	 * 查询获取项目或表达式选择
	 * 
	 * @author 
	 * @param yzb613 库表名称
	 * @param yzb672 数据源名称
	 * @return
	 * @throws Exception
	 */
	public List querySearchItemSelect(String yzb613,String yzb672) throws Exception;

	/**
	 * 查询配制项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto querySearchItem(TaParamDto dto) throws Exception;

	/**
	 * 保存配制项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto saveSearchItem(TaParamDto dto) throws Exception;

	/**
	 * 删除配制项目
	 * 
	 * @author 
	 * @param lst
	 * @return
	 * @throws Exception
	 */
	public TaParamDto removeSearchItem(List lst) throws Exception;

	/**
	 * 删除配制项目
	 * 
	 * @author 
	 * @param key
	 * @throws Exception
	 */
	public void removeSearchItem(Map key) throws Exception;

}
package com.yinhai.cxtj.admin.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.Zb61Domain;
import com.yinhai.sysframework.persistence.PageBean;


/**
 * 查询统计主题
 * 
 * @author 
 */
public interface SetSearchService extends CxtjBaseService {

	/**
	 * 查询流程定义
	 * 
	 * @author 
	 * @param yzb610
	 *            查询统计主题流水号
	 * @return
	 * @throws Exception
	 */
	public Zb61Domain getDomainObjectById(BigDecimal yzb610) throws Exception;

	/**
	 * 保存主题
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto saveSearch(TaParamDto dto) throws Exception;

	/**
	 * 删除主题
	 * 
	 * @author 
	 * @param lst
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto removeSearch(List lst,TaParamDto dto) throws Exception;

	/**
	 * 查询主题
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageBean querySearchs(TaParamDto dto) throws Exception;


	/**
	 * 查询配置的数据源
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List queryDataSource(TaParamDto dto) throws Exception;

	/**
	 * 查询相应数据源下的数据集
	 * @param yzb670
	 * @return
	 * @throws Exception
	 */
	List<Map> queryResultSetsByYzb670(String yzb670) throws Exception;

}
package com.yinhai.cxtj.admin.service;

import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.CxtjBaseService;

import java.math.BigDecimal;
import java.util.List;


/**
 * 查询统计主题缺省排序项目
 * 
 * @author 
 */
public interface SetSearchOrderService extends CxtjBaseService {

	/**
	 * 查询配制项目排序
	 * 
	 * @author 
	 * @param yzb610
	 *            查询统计主题流水号
	 * @return
	 * @throws Exception
	 */
	public List querySearchOrders(BigDecimal yzb610) throws Exception;

	/**
	 * 查询待选项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List query1(TaParamDto dto) throws Exception;

	/**
	 * 查询已选项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List query2(TaParamDto dto) throws Exception;

	/**
	 * 选择待选项目
	 * 
	 * @author 
	 * @param lst
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto select1(List lst, TaParamDto dto) throws Exception;

	/**
	 * 选择已选项目
	 * 
	 * @author 
	 * @param lst
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto select2(List lst, TaParamDto dto) throws Exception;

	/**
	 * 设置排序方式
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto saveYzb652Radio(TaParamDto dto) throws Exception;

	/**
	 * 设置排序
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public TaParamDto saveYzb651updown(TaParamDto dto) throws Exception;

	/**
	 * 删除配制项目排序
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	public int removeSearch(BigDecimal yzb620) throws Exception;

}
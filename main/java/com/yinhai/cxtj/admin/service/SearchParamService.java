package com.yinhai.cxtj.admin.service;

import java.math.BigDecimal;
import java.util.List;

import com.yinhai.cxtj.admin.base.service.CxtjBaseService;
import com.yinhai.cxtj.admin.domain.*;


/**
 * 功能开发设置查询
 * 
 * @author 
 */
public interface SearchParamService extends CxtjBaseService{

	/**
	 * 根据查询统计主题代码获取查询统计主题
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计主题代码
	 * @return zb61Domain
	 * @throws Exception
	 */
	public Zb61Domain getSearchByYZB611(String yzb611) throws Exception;

	/**
	 * 根据查询统计主题代码获取查询统计主题的项目
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计主题代码
	 * @return List(zb62Domain)
	 * @throws Exception
	 */
	public List<Zb62Domain> getSearchItemByYZB611(String yzb611) throws Exception;

	/**
	 * 根据分组控制获取查询统计主题的项目
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计主题代码
	 * @param yzb62b
	 *            分组控制
	 * @return List(zb62Domain)
	 * @throws Exception
	 */
	public List<Zb62Domain> getSearchItemByYZB62B(String yzb611, String yzb62b) throws Exception;

	/**
	 * 根据分组计算控制获取用于统计计算的项目
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计主题代码
	 * @param yzb62c
	 *            分组计算控制
	 * @return List(zb62Domain)
	 * @throws Exception
	 */
	public List<Zb62Domain> getSearchItemByYZB62C(String yzb611, String yzb62c) throws Exception;

	/**
	 * 根据查询统计项目流水号获取查询统计主题的项目
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return zb62Domain
	 * @throws Exception
	 */
	public Zb62Domain getSearchItemByYZB620(BigDecimal yzb620) throws Exception;

	/**
	 * 根据查询统计项目流水号获取查询统计主题项目支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return List(zb63Domain)
	 * @throws Exception
	 */
	public List<Zb63Domain> getSearchSignByYZB620(BigDecimal yzb620) throws Exception;

	/**
	 * 根据查询统计项目流水号获取查询统计主题项目支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return List(zb64Domain)
	 * @throws Exception
	 */
	public List<Zb64Domain> getSearchModeByYZB620(BigDecimal yzb620) throws Exception;

	/**
	 * 根据查询统计主题代码获取查询统计主题缺省排序项目
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计项目流水号
	 * @return List(zb65Domain)
	 * @throws Exception
	 */
	public List<Zb65Domain> getSearchOrderByYZB611(String yzb611) throws Exception;

}
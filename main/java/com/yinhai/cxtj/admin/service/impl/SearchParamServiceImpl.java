package com.yinhai.cxtj.admin.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.core.service.ta3.domain.service.TaBaseService;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.*;
import com.yinhai.cxtj.admin.service.*;


/**
 * 功能开发设置查询
 * 
 * @author 
 */
public class SearchParamServiceImpl extends CxtjBaseServiceImpl implements SearchParamService {

	public SetSearchService getSetSearchService() {
		return setSearchService;
	}

	public void setSetSearchService(SetSearchService setSearchService) {
		this.setSearchService = setSearchService;
	}

	public SetSearchItemService getSetSearchItemService() {
		return setSearchItemService;
	}

	public void setSetSearchItemService(SetSearchItemService setSearchItemService) {
		this.setSearchItemService = setSearchItemService;
	}

	public SetSearchSignService getSetSearchSignService() {
		return setSearchSignService;
	}

	public void setSetSearchSignService(SetSearchSignService setSearchSignService) {
		this.setSearchSignService = setSearchSignService;
	}

	public SetSearchModeService getSetSearchModeService() {
		return setSearchModeService;
	}

	public void setSetSearchModeService(SetSearchModeService setSearchModeService) {
		this.setSearchModeService = setSearchModeService;
	}

	public SetSearchOrderService getSetSearchOrderService() {
		return setSearchOrderService;
	}

	public void setSetSearchOrderService(SetSearchOrderService setSearchOrderService) {
		this.setSearchOrderService = setSearchOrderService;
	}

	private SetSearchService setSearchService;

	private SetSearchItemService setSearchItemService;

	private SetSearchSignService setSearchSignService;

	private SetSearchModeService setSearchModeService;

	private SetSearchOrderService setSearchOrderService;

	/**
	 * 根据查询统计主题代码获取查询统计主题
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计主题代码
	 * @return zb61Domain
	 * @throws Exception
	 */
	@Override
	public Zb61Domain getSearchByYZB611(String yzb611) throws Exception {
		if (ValidateUtil.isNotEmpty(yzb611)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb611", yzb611);
			List zb61lst = getDao().queryForList("searchparam.getSearchByYZB611", d);
			if (ValidateUtil.isNotEmpty(zb61lst)) {
				return (Zb61Domain) zb61lst.get(0);
			}
		}
		return null;
	}

	/**
	 * 根据查询统计主题代码获取查询统计主题的项目
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计主题代码
	 * @return List(zb62Domain)
	 * @throws Exception
	 */
	@Override
	public List<Zb62Domain> getSearchItemByYZB611(String yzb611) throws Exception {
		Zb61Domain zb61Domain = getSearchByYZB611(yzb611);
		if (!ValidateUtil.isEmpty(zb61Domain)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb610", zb61Domain.getYzb610());
			List<Zb62Domain> zb62lst = getDao().queryForList("searchparam.getSearchItemByYZB611", d);
			return zb62lst;
		}
		return null;
	}

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
	@Override
	public List<Zb62Domain> getSearchItemByYZB62B(String yzb611, String yzb62b) throws Exception {
		Zb61Domain zb61Domain = getSearchByYZB611(yzb611);
		if (!ValidateUtil.isEmpty(zb61Domain)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb610", zb61Domain.getYzb610());
			d.append("yzb62b", yzb62b);
			List<Zb62Domain> zb62lst = getDao().queryForList("searchparam.getSearchItemByYZB62B", d);
			return zb62lst;
		}
		return null;
	}

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
	@Override
	public List<Zb62Domain> getSearchItemByYZB62C(String yzb611, String yzb62c) throws Exception {
		Zb61Domain zb61Domain = getSearchByYZB611(yzb611);
		if (!ValidateUtil.isEmpty(zb61Domain)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb610", zb61Domain.getYzb610());
			d.append("yzb62c", yzb62c);
			List<Zb62Domain> zb62lst = getDao().queryForList("searchparam.getSearchItemByYZB62C", d);
			return zb62lst;
		}
		return null;
	}

	/**
	 * 根据查询统计项目流水号获取查询统计主题的项目
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return zb62Domain
	 * @throws Exception
	 */
	@Override
	public Zb62Domain getSearchItemByYZB620(BigDecimal yzb620) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620)) {
//			Key key = new Key();
			//change by zhaohs
			Map key = new HashMap<>();
			key.put("yzb620", yzb620);
			return (Zb62Domain) getDao().queryForObject("searchparam.getSearchItemByYZB620", key);
		}
		return null;
	}

	/**
	 * 根据查询统计项目流水号获取查询统计主题项目支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return List(zb63Domain)
	 * @throws Exception
	 */
	@Override
	public List<Zb63Domain> getSearchSignByYZB620(BigDecimal yzb620) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb620", yzb620);
			List<Zb63Domain> zb63lst = getDao().queryForList("searchparam.getSearchSignByYZB620", d);
			return zb63lst;
		}
		return null;
	}

	/**
	 * 根据查询统计项目流水号获取查询统计主题项目支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return List(zb64Domain)
	 * @throws Exception
	 */
	@Override
	public List<Zb64Domain> getSearchModeByYZB620(BigDecimal yzb620) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb620", yzb620);
			List<Zb64Domain> zb64lst = getDao().queryForList("searchparam.getSearchModeByYZB620", d);
			return zb64lst;
		}
		return null;
	}

	/**
	 * 根据查询统计主题代码获取查询统计主题缺省排序项目
	 * 
	 * @author 
	 * @param yzb611
	 *            查询统计项目流水号
	 * @return List(zb65Domain)
	 * @throws Exception
	 */
	@Override
	public List<Zb65Domain> getSearchOrderByYZB611(String yzb611) throws Exception {
		Zb61Domain zb61Domain = getSearchByYZB611(yzb611);
		if (!ValidateUtil.isEmpty(zb61Domain)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb610", zb61Domain.getYzb610());
			List<Zb65Domain> zb65lst = getDao().queryForList("searchparam.getSearchOrderByYZB611", d);
			return zb65lst;
		}
		return null;
	}

}

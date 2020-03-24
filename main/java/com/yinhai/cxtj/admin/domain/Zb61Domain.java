package com.yinhai.cxtj.admin.domain;

import com.yinhai.cxtj.front.domain.InputDomain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Zb61Domain extends InputDomain {

	private static final long serialVersionUID = -2825986173840058354L;
	/** 查询统计主题流水号 */
	private BigDecimal yzb610;

	/** 查询统计类型（1查询2统计） */
	private String yzb617;

	/** 查询统计主题代码 */
	private String yzb611;

	/** 查询统计主题名称 */
	private String yzb612;

	/** 查询统计主题库表 */
	private String yzb613;

	/** 基础WHERE条件 */
	private String yzb615;

	/** 基础WHERE条件描述 */
	private String yzb616;

	/** 经办人 */
	private String aae011;

	/** 经办人编号 */
	private Long yae116;

	/** 经办机构 */
	private Long aae017;

	/** 经办日期 */
	private Timestamp aae036;

	private BigDecimal yzb670;

	/** 挂靠菜单id */
	private String yzb618;

	public Zb61Domain() {
	}

	public Zb61Domain(BigDecimal yzb610) {
		this.yzb610 = yzb610;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb610() == null) {
//			// throw new IllegalArgumentException("主键yzb610不能为空。");
//		}
//		key.put("yzb610", this.getYzb610());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb610() == null) {
			// throw new IllegalArgumentException("主键yzb610不能为空。");
		}
		key.put("yzb610", this.getYzb610());
		return key;
	}

	public Zb61Domain(BigDecimal yzb610, String yzb617, String aae011, Long yae116, Long aae017, Timestamp aae036) {
		this.yzb610 = yzb610;
		this.yzb617 = yzb617;
		this.aae011 = aae011;
		this.yae116 = yae116;
		this.aae017 = aae017;
		this.aae036 = aae036;
	}

	/**
	 * 设置 yzb610 查询统计主题流水号
	 * 
	 * @param yzb610
	 *            查询统计主题流水号
	 */
	public void setYzb610(BigDecimal yzb610) {
		this.yzb610 = yzb610;
	}

	/**
	 * 获取 yzb610 查询统计主题流水号
	 * 
	 * @return 查询统计主题流水号
	 */
	public BigDecimal getYzb610() {
		return this.yzb610;
	}

	/**
	 * 设置 yzb617 查询统计类型（1查询2统计）
	 * 
	 * @param yzb617
	 *            查询统计类型（1查询2统计）
	 */
	public void setYzb617(String yzb617) {
		this.yzb617 = yzb617;
	}

	/**
	 * 获取 yzb617 查询统计类型（1查询2统计）
	 * 
	 * @return 查询统计类型（1查询2统计）
	 */
	public String getYzb617() {
		return this.yzb617;
	}

	/**
	 * 设置 yzb611 查询统计主题代码
	 * 
	 * @param yzb611
	 *            查询统计主题代码
	 */
	public void setYzb611(String yzb611) {
		this.yzb611 = yzb611;
	}

	/**
	 * 获取 yzb611 查询统计主题代码
	 * 
	 * @return 查询统计主题代码
	 */
	public String getYzb611() {
		return this.yzb611;
	}

	/**
	 * 设置 yzb612 查询统计主题名称
	 * 
	 * @param yzb612
	 *            查询统计主题名称
	 */
	public void setYzb612(String yzb612) {
		this.yzb612 = yzb612;
	}

	/**
	 * 获取 yzb612 查询统计主题名称
	 * 
	 * @return 查询统计主题名称
	 */
	public String getYzb612() {
		return this.yzb612;
	}

	/**
	 * 设置 yzb613 查询统计主题库表
	 * 
	 * @param yzb613
	 *            查询统计主题库表
	 */
	public void setYzb613(String yzb613) {
		this.yzb613 = yzb613;
	}

	/**
	 * 获取 yzb613 查询统计主题库表
	 * 
	 * @return 查询统计主题库表
	 */
	public String getYzb613() {
		return this.yzb613;
	}

	/**
	 * 设置 yzb615 基础WHERE条件
	 * 
	 * @param yzb615
	 *            基础WHERE条件
	 */
	public void setYzb615(String yzb615) {
		this.yzb615 = yzb615;
	}

	/**
	 * 获取 yzb615 基础WHERE条件
	 * 
	 * @return 基础WHERE条件
	 */
	public String getYzb615() {
		return this.yzb615;
	}

	/**
	 * 设置 yzb616 基础WHERE条件描述
	 * 
	 * @param yzb616
	 *            基础WHERE条件描述
	 */
	public void setYzb616(String yzb616) {
		this.yzb616 = yzb616;
	}

	/**
	 * 获取 yzb616 基础WHERE条件描述
	 * 
	 * @return 基础WHERE条件描述
	 */
	public String getYzb616() {
		return this.yzb616;
	}

	/**
	 * 设置 aae011 经办人
	 * 
	 * @param aae011
	 *            经办人
	 */
	@Override
	public void setAae011(String aae011) {
		this.aae011 = aae011;
	}

	/**
	 * 获取 aae011 经办人
	 * 
	 * @return 经办人
	 */
	@Override
	public String getAae011() {
		return this.aae011;
	}

	/**
	 * 设置 yae116 经办人编号
	 * 
	 * @param yae116
	 *            经办人编号
	 */
	@Override
	public void setYae116(Long yae116) {
		this.yae116 = yae116;
	}

	/**
	 * 获取 yae116 经办人编号
	 * 
	 * @return 经办人编号
	 */
	@Override
	public Long getYae116() {
		return this.yae116;
	}

	/**
	 * 设置 aae017 经办机构
	 * 
	 * @param aae017
	 *            经办机构
	 */
	@Override
	public void setAae017(Long aae017) {
		this.aae017 = aae017;
	}

	/**
	 * 获取 aae017 经办机构
	 * 
	 * @return 经办机构
	 */
	@Override
	public Long getAae017() {
		return this.aae017;
	}

	/**
	 * 设置 aae036 经办日期
	 * 
	 * @param aae036
	 *            经办日期
	 */
	@Override
	public void setAae036(Timestamp aae036) {
		this.aae036 = aae036;
	}

	/**
	 * 获取 aae036 经办日期
	 * 
	 * @return 经办日期
	 */
	@Override
	public Timestamp getAae036() {
		return this.aae036;
	}

	public BigDecimal getYzb670() {
		return yzb670;
	}

	public void setYzb670(BigDecimal yzb670) {
		this.yzb670 = yzb670;
	}

	public String getYzb618() {
		return yzb618;
	}

	public void setYzb618(String yzb618) {
		this.yzb618 = yzb618;
	}

	/**
	 * 转换为map对象
	 * 
	 * @return Map
	 */
	public Map toMap() {
		Map map = new HashMap();
		map.put("yzb610", getYzb610()); // 查询统计主题流水号
		map.put("yzb617", getYzb617()); // 查询统计类型（1查询2统计）
		map.put("yzb611", getYzb611()); // 查询统计主题代码
		map.put("yzb612", getYzb612()); // 查询统计主题名称
		map.put("yzb613", getYzb613()); // 查询统计主题库表
		map.put("yzb615", getYzb615()); // 基础WHERE条件
		map.put("yzb616", getYzb616()); // 基础WHERE条件描述
		map.put("aae011", getAae011()); // 经办人
		map.put("yae116", getYae116()); // 经办人编号
		map.put("aae017", getAae017()); // 经办机构
		map.put("aae036", getAae036()); // 经办日期
		map.put("yzb670", getYzb670()); // 经办日期
		map.put("yzb618", getYzb618()); // 经办日期

		return map;
	}

}

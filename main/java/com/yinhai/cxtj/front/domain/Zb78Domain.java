package com.yinhai.cxtj.front.domain;


import java.util.HashMap;
import java.util.Map;

public class Zb78Domain extends InputDomain {

	private static final long serialVersionUID = -3938681457584917013L;
	/** 查询统计方案分组统计项目流水号 */
	private java.math.BigDecimal yzb780;

	/** 查询统计方案流水号 */
	private java.math.BigDecimal yzb710;

	/** 排序号 */
	private Integer yzb781;

	/** 查询统计主题的项目流水号 */
	private java.math.BigDecimal yzb620;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	public Zb78Domain() {
	}

	public Zb78Domain(java.math.BigDecimal yzb780) {
		this.yzb780 = yzb780;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb780() == null) {
//			throw new IllegalArgumentException("主键yzb780不能为空。");
//		}
//		key.put("yzb780", this.getYzb780());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb780() == null) {
			throw new IllegalArgumentException("主键yzb780不能为空。");
		}
		key.put("yzb780", this.getYzb780());
		return key;
	}

	public Zb78Domain(java.math.BigDecimal yzb780, java.math.BigDecimal yzb710,
			java.math.BigDecimal yzb620, java.sql.Timestamp aae036) {
		this.yzb780 = yzb780;
		this.yzb710 = yzb710;
		this.yzb620 = yzb620;
		this.aae036 = aae036;
	}

	/**
	 * 设置 yzb780 查询统计方案分组统计项目流水号
	 * @param yzb780 查询统计方案分组统计项目流水号
	 */
	public void setYzb780(java.math.BigDecimal yzb780) {
		this.yzb780 = yzb780;
	}

	/**
	 * 获取 yzb780 查询统计方案分组统计项目流水号
	 * @return 查询统计方案分组统计项目流水号
	 */
	public java.math.BigDecimal getYzb780() {
		return this.yzb780;
	}

	/**
	 * 设置 yzb710 查询统计方案流水号
	 * @param yzb710 查询统计方案流水号
	 */
	public void setYzb710(java.math.BigDecimal yzb710) {
		this.yzb710 = yzb710;
	}

	/**
	 * 获取 yzb710 查询统计方案流水号
	 * @return 查询统计方案流水号
	 */
	public java.math.BigDecimal getYzb710() {
		return this.yzb710;
	}

	/**
	 * 设置 yzb781 排序号
	 * @param yzb781 排序号
	 */
	public void setYzb781(Integer yzb781) {
		this.yzb781 = yzb781;
	}

	/**
	 * 获取 yzb781 排序号
	 * @return 排序号
	 */
	public Integer getYzb781() {
		return this.yzb781;
	}

	/**
	 * 设置 yzb620 查询统计主题的项目流水号
	 * @param yzb620 查询统计主题的项目流水号
	 */
	public void setYzb620(java.math.BigDecimal yzb620) {
		this.yzb620 = yzb620;
	}

	/**
	 * 获取 yzb620 查询统计主题的项目流水号
	 * @return 查询统计主题的项目流水号
	 */
	public java.math.BigDecimal getYzb620() {
		return this.yzb620;
	}

	/**
	 * 设置 aae036 经办日期
	 * @param aae036 经办日期
	 */
	@Override
	public void setAae036(java.sql.Timestamp aae036) {
		this.aae036 = aae036;
	}

	/**
	 * 获取 aae036 经办日期
	 * @return 经办日期
	 */
	@Override
	public java.sql.Timestamp getAae036() {
		return this.aae036;
	}

	/**
	 * 转换为map对象
	 * @return Map
	 */
	public Map toMap() {
		Map map = new HashMap();
		map.put("yzb780", getYzb780()); //查询统计方案分组统计项目流水号
		map.put("yzb710", getYzb710()); //查询统计方案流水号
		map.put("yzb781", getYzb781()); //排序号
		map.put("yzb620", getYzb620()); //查询统计主题的项目流水号
		map.put("aae036", getAae036()); //经办日期

		return map;
	}
}

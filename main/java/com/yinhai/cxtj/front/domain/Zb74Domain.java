package com.yinhai.cxtj.front.domain;


import java.util.HashMap;
import java.util.Map;

public class Zb74Domain extends InputDomain {

	private static final long serialVersionUID = -4234313164932425244L;
	/** 查询统计方案WHERE条件代码值流水号 */
	private java.math.BigDecimal yzb740;

	/** 查询统计方案的WHERE条件流水号 */
	private java.math.BigDecimal yzb730;

	/** 代码值 */
	private String yzb741;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	public Zb74Domain() {
	}

	public Zb74Domain(java.math.BigDecimal yzb740, java.math.BigDecimal yzb730) {
		this.yzb740 = yzb740;
		this.yzb730 = yzb730;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb740() == null) {
//			throw new IllegalArgumentException("主键yzb740不能为空。");
//		}
//		key.put("yzb740", this.getYzb740());
//		if (this.getYzb730() == null) {
//			throw new IllegalArgumentException("主键yzb730不能为空。");
//		}
//		key.put("yzb730", this.getYzb730());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb740() == null) {
			throw new IllegalArgumentException("主键yzb740不能为空。");
		}
		key.put("yzb740", this.getYzb740());
		if (this.getYzb730() == null) {
			throw new IllegalArgumentException("主键yzb730不能为空。");
		}
		key.put("yzb730", this.getYzb730());
		return key;
	}

	public Zb74Domain(java.math.BigDecimal yzb740, java.math.BigDecimal yzb730,
			java.sql.Timestamp aae036) {
		this.yzb740 = yzb740;
		this.yzb730 = yzb730;
		this.aae036 = aae036;
	}

	/**
	 * 设置 yzb740 查询统计方案WHERE条件代码值流水号
	 * @param yzb740 查询统计方案WHERE条件代码值流水号
	 */
	public void setYzb740(java.math.BigDecimal yzb740) {
		this.yzb740 = yzb740;
	}

	/**
	 * 获取 yzb740 查询统计方案WHERE条件代码值流水号
	 * @return 查询统计方案WHERE条件代码值流水号
	 */
	public java.math.BigDecimal getYzb740() {
		return this.yzb740;
	}

	/**
	 * 设置 yzb730 查询统计方案的WHERE条件流水号
	 * @param yzb730 查询统计方案的WHERE条件流水号
	 */
	public void setYzb730(java.math.BigDecimal yzb730) {
		this.yzb730 = yzb730;
	}

	/**
	 * 获取 yzb730 查询统计方案的WHERE条件流水号
	 * @return 查询统计方案的WHERE条件流水号
	 */
	public java.math.BigDecimal getYzb730() {
		return this.yzb730;
	}

	/**
	 * 设置 yzb741 代码值
	 * @param yzb741 代码值
	 */
	public void setYzb741(String yzb741) {
		this.yzb741 = yzb741;
	}

	/**
	 * 获取 yzb741 代码值
	 * @return 代码值
	 */
	public String getYzb741() {
		return this.yzb741;
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
		map.put("yzb740", getYzb740()); //查询统计方案WHERE条件代码值流水号
		map.put("yzb730", getYzb730()); //查询统计方案的WHERE条件流水号
		map.put("yzb741", getYzb741()); //代码值
		map.put("aae036", getAae036()); //经办日期

		return map;
	}
}

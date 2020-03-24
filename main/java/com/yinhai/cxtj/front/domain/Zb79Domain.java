package com.yinhai.cxtj.front.domain;


import java.util.HashMap;
import java.util.Map;

public class Zb79Domain extends InputDomain {

	private static final long serialVersionUID = 6216320821140940595L;
	/** 查询统计方案统计值流水号 */
	private java.math.BigDecimal yzb790;

	/** 查询统计方案流水号 */
	private java.math.BigDecimal yzb710;

	/** 排序号 */
	private Integer yzb791;

	/** 查询统计主题的项目流水号 */
	private java.math.BigDecimal yzb620;

	/** 统计方式（1计数2去重后计数3求和4求平均5最大值6最小值） */
	private String yzb641;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	public Zb79Domain() {
	}

	public Zb79Domain(java.math.BigDecimal yzb790) {
		this.yzb790 = yzb790;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb790() == null) {
//			throw new IllegalArgumentException("主键yzb790不能为空。");
//		}
//		key.put("yzb790", this.getYzb790());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb790() == null) {
			throw new IllegalArgumentException("主键yzb790不能为空。");
		}
		key.put("yzb790", this.getYzb790());
		return key;
	}

	public Zb79Domain(java.math.BigDecimal yzb790, java.math.BigDecimal yzb710,
			java.math.BigDecimal yzb620, java.sql.Timestamp aae036) {
		this.yzb790 = yzb790;
		this.yzb710 = yzb710;
		this.yzb620 = yzb620;
		this.aae036 = aae036;
	}

	/**
	 * 设置 yzb790 查询统计方案统计值流水号
	 * @param yzb790 查询统计方案统计值流水号
	 */
	public void setYzb790(java.math.BigDecimal yzb790) {
		this.yzb790 = yzb790;
	}

	/**
	 * 获取 yzb790 查询统计方案统计值流水号
	 * @return 查询统计方案统计值流水号
	 */
	public java.math.BigDecimal getYzb790() {
		return this.yzb790;
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
	 * 设置 yzb791 排序号
	 * @param yzb791 排序号
	 */
	public void setYzb791(Integer yzb791) {
		this.yzb791 = yzb791;
	}

	/**
	 * 获取 yzb791 排序号
	 * @return 排序号
	 */
	public Integer getYzb791() {
		return this.yzb791;
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
	 * 设置 yzb641 统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * @param yzb641 统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 */
	public void setYzb641(String yzb641) {
		this.yzb641 = yzb641;
	}

	/**
	 * 获取 yzb641 统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * @return 统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 */
	public String getYzb641() {
		return this.yzb641;
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
		map.put("yzb790", getYzb790()); //查询统计方案统计值流水号
		map.put("yzb710", getYzb710()); //查询统计方案流水号
		map.put("yzb791", getYzb791()); //排序号
		map.put("yzb620", getYzb620()); //查询统计主题的项目流水号
		map.put("yzb641", getYzb641()); //统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
		map.put("aae036", getAae036()); //经办日期

		return map;
	}
}

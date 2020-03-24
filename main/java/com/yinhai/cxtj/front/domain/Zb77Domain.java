package com.yinhai.cxtj.front.domain;


import java.util.HashMap;
import java.util.Map;

public class Zb77Domain extends InputDomain {

	private static final long serialVersionUID = 6857787120161130413L;
	/** 查询统计方案排序项目流水号 */
	private java.math.BigDecimal yzb770;

	/** 查询统计方案流水号 */
	private java.math.BigDecimal yzb710;

	/** 排序号 */
	private Integer yzb771;

	/** 查询统计主题的项目流水号 */
	private java.math.BigDecimal yzb620;

	/** 排序方式（1升序2降序） */
	private String yzb652;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	public Zb77Domain() {
	}

	public Zb77Domain(java.math.BigDecimal yzb770) {
		this.yzb770 = yzb770;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb770() == null) {
//			throw new IllegalArgumentException("主键yzb770不能为空。");
//		}
//		key.put("yzb770", this.getYzb770());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb770() == null) {
			throw new IllegalArgumentException("主键yzb770不能为空。");
		}
		key.put("yzb770", this.getYzb770());
		return key;
	}

	public Zb77Domain(java.math.BigDecimal yzb770, java.math.BigDecimal yzb710,
			java.math.BigDecimal yzb620, java.sql.Timestamp aae036) {
		this.yzb770 = yzb770;
		this.yzb710 = yzb710;
		this.yzb620 = yzb620;
		this.aae036 = aae036;
	}

	/**
	 * 设置 yzb770 查询统计方案排序项目流水号
	 * @param yzb770 查询统计方案排序项目流水号
	 */
	public void setYzb770(java.math.BigDecimal yzb770) {
		this.yzb770 = yzb770;
	}

	/**
	 * 获取 yzb770 查询统计方案排序项目流水号
	 * @return 查询统计方案排序项目流水号
	 */
	public java.math.BigDecimal getYzb770() {
		return this.yzb770;
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
	 * 设置 yzb771 排序号
	 * @param yzb771 排序号
	 */
	public void setYzb771(Integer yzb771) {
		this.yzb771 = yzb771;
	}

	/**
	 * 获取 yzb771 排序号
	 * @return 排序号
	 */
	public Integer getYzb771() {
		return this.yzb771;
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
	 * 设置 yzb652 排序方式（1升序2降序）
	 * @param yzb652 排序方式（1升序2降序）
	 */
	public void setYzb652(String yzb652) {
		this.yzb652 = yzb652;
	}

	/**
	 * 获取 yzb652 排序方式（1升序2降序）
	 * @return 排序方式（1升序2降序）
	 */
	public String getYzb652() {
		return this.yzb652;
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
		map.put("yzb770", getYzb770()); //查询统计方案排序项目流水号
		map.put("yzb710", getYzb710()); //查询统计方案流水号
		map.put("yzb771", getYzb771()); //排序号
		map.put("yzb620", getYzb620()); //查询统计主题的项目流水号
		map.put("yzb652", getYzb652()); //排序方式（1升序2降序）
		map.put("aae036", getAae036()); //经办日期

		return map;
	}

}

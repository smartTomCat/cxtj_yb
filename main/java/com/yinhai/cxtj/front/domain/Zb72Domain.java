package com.yinhai.cxtj.front.domain;


import java.util.HashMap;
import java.util.Map;

public class Zb72Domain  extends InputDomain {

	private static final long serialVersionUID = 4895229003068208994L;
	/** 查询统计方案WHERE条件分组流水号 */
	private java.math.BigDecimal yzb720;

	/** 查询统计方案流水号 */
	private java.math.BigDecimal yzb710;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	public Zb72Domain() {
	}

	public Zb72Domain(java.math.BigDecimal yzb720) {
		this.yzb720 = yzb720;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb720() == null) {
//			throw new IllegalArgumentException("主键yzb720不能为空。");
//		}
//		key.put("yzb720", this.getYzb720());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb720() == null) {
			//throw new IllegalArgumentException("主键yzb710不能为空。");
		}
		key.put("yzb720", this.getYzb720());
		return key;
	}

	public Zb72Domain(java.math.BigDecimal yzb720, java.math.BigDecimal yzb710,
			java.sql.Timestamp aae036) {
		this.yzb720 = yzb720;
		this.yzb710 = yzb710;
		this.aae036 = aae036;
	}

	/**
	 * 设置 yzb720 查询统计方案WHERE条件分组流水号
	 * @param yzb720 查询统计方案WHERE条件分组流水号
	 */
	public void setYzb720(java.math.BigDecimal yzb720) {
		this.yzb720 = yzb720;
	}

	/**
	 * 获取 yzb720 查询统计方案WHERE条件分组流水号
	 * @return 查询统计方案WHERE条件分组流水号
	 */
	public java.math.BigDecimal getYzb720() {
		return this.yzb720;
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
		map.put("yzb720", getYzb720()); //查询统计方案WHERE条件分组流水号
		map.put("yzb710", getYzb710()); //查询统计方案流水号
		map.put("aae036", getAae036()); //经办日期

		return map;
	}

}

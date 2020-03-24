package com.yinhai.cxtj.front.domain;


public class Zb76Domain extends InputDomain {

	private static final long serialVersionUID = 2141249309813647965L;
	/** 查询统计方案查询统计项目流水号 */
	private java.math.BigDecimal yzb760;

	/** 查询统计方案流水号 */
	private java.math.BigDecimal yzb710;

	/** 排序号 */
	private Integer yzb761;

	/** 查询统计主题的项目流水号 */
	private java.math.BigDecimal yzb620;

	/** 查询统计结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值） */
	private String yzb641;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	/**
	 * 设置 yzb760 查询统计方案查询统计项目流水号
	 * @param yzb760 查询统计方案查询统计项目流水号
	 */
	public void setYzb760(java.math.BigDecimal yzb760) {
		this.yzb760 = yzb760;
	}

	/**
	 * 获取 yzb760 查询统计方案查询统计项目流水号
	 * @return 查询统计方案查询统计项目流水号
	 */
	public java.math.BigDecimal getYzb760() {
		return this.yzb760;
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
	 * 设置 yzb761 排序号
	 * @param yzb761 排序号
	 */
	public void setYzb761(Integer yzb761) {
		this.yzb761 = yzb761;
	}

	/**
	 * 获取 yzb761 排序号
	 * @return 排序号
	 */
	public Integer getYzb761() {
		return this.yzb761;
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
	 * 设置 yzb641 查询统计结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * @param yzb641 查询统计结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 */
	public void setYzb641(String yzb641) {
		this.yzb641 = yzb641;
	}

	/**
	 * 获取 yzb641 查询统计结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * @return 查询统计结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
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
	public java.util.Map toMap() {
		java.util.Map map = new java.util.HashMap();
		map.put("yzb760", getYzb760()); //查询统计方案查询统计项目流水号
		map.put("yzb710", getYzb710()); //查询统计方案流水号
		map.put("yzb761", getYzb761()); //排序号
		map.put("yzb620", getYzb620()); //查询统计主题的项目流水号
		map.put("yzb641", getYzb641()); //查询统计结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
		map.put("aae036", getAae036()); //经办日期

		return map;
	}
}

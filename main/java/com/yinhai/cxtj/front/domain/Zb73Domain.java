package com.yinhai.cxtj.front.domain;



import java.util.HashMap;
import java.util.Map;

public class Zb73Domain extends InputDomain {

	private static final long serialVersionUID = -4939556050180342416L;
	/** 查询统计方案WHERE条件流水号 */
	private java.math.BigDecimal yzb730;

	/** 查询统计方案流水号 */
	private java.math.BigDecimal yzb710;

	/** 查询统计方案WHERE条件分组流水号 */
	private java.math.BigDecimal yzb720;

	/** 分组内排序号 */
	private Integer yzb731;

	/** 查询统计主题的项目流水号 */
	private java.math.BigDecimal yzb620;

	/** 选择的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含） */
	private String yzb631;

	/** 输入框中录入的值 */
	private String yzb733;

	/** 值对象（1固定值2项目） */
	private String yzb734;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	public Zb73Domain() {
	}

	public Zb73Domain(java.math.BigDecimal yzb730, java.math.BigDecimal yzb720) {
		this.yzb730 = yzb730;
		this.yzb720 = yzb720;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb730() == null) {
//			//throw new IllegalArgumentException("主键yzb730不能为空。");
//		}
//		key.put("yzb730", this.getYzb730());
//		if (this.getYzb720() == null) {
//			//throw new IllegalArgumentException("主键yzb720不能为空。");
//		}
//		key.put("yzb720", this.getYzb720());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb730() == null) {
			//throw new IllegalArgumentException("主键yzb730不能为空。");
		}
		key.put("yzb730", this.getYzb730());
		if (this.getYzb720() == null) {
			//throw new IllegalArgumentException("主键yzb720不能为空。");
		}
		key.put("yzb720", this.getYzb720());
		return key;
	}

	public Zb73Domain(java.math.BigDecimal yzb730, java.math.BigDecimal yzb710,
			java.math.BigDecimal yzb720, java.math.BigDecimal yzb620,
			java.sql.Timestamp aae036) {
		this.yzb730 = yzb730;
		this.yzb710 = yzb710;
		this.yzb720 = yzb720;
		this.yzb620 = yzb620;
		this.aae036 = aae036;
	}

	/**
	 * 设置 yzb730 查询统计方案WHERE条件流水号
	 * @param yzb730 查询统计方案WHERE条件流水号
	 */
	public void setYzb730(java.math.BigDecimal yzb730) {
		this.yzb730 = yzb730;
	}

	/**
	 * 获取 yzb730 查询统计方案WHERE条件流水号
	 * @return 查询统计方案WHERE条件流水号
	 */
	public java.math.BigDecimal getYzb730() {
		return this.yzb730;
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
	 * 设置 yzb731 分组内排序号
	 * @param yzb731 分组内排序号
	 */
	public void setYzb731(Integer yzb731) {
		this.yzb731 = yzb731;
	}

	/**
	 * 获取 yzb731 分组内排序号
	 * @return 分组内排序号
	 */
	public Integer getYzb731() {
		return this.yzb731;
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
	 * 设置 yzb631 选择的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 * @param yzb631 选择的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 */
	public void setYzb631(String yzb631) {
		this.yzb631 = yzb631;
	}

	/**
	 * 获取 yzb631 选择的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 * @return 选择的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 */
	public String getYzb631() {
		return this.yzb631;
	}

	/**
	 * 设置 yzb733 输入框中录入的值
	 * @param yzb733 输入框中录入的值
	 */
	public void setYzb733(String yzb733) {
		this.yzb733 = yzb733;
	}

	/**
	 * 获取 yzb733 输入框中录入的值
	 * @return 输入框中录入的值
	 */
	public String getYzb733() {
		return this.yzb733;
	}

	/**
	 * 设置 yzb734 值对象（1固定值2项目）
	 * @param yzb734 值对象（1固定值2项目）
	 */
	public void setYzb734(String yzb734) {
		this.yzb734 = yzb734;
	}

	/**
	 * 获取 yzb734 值对象（1固定值2项目）
	 * @return 值对象（1固定值2项目）
	 */
	public String getYzb734() {
		return this.yzb734;
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
		map.put("yzb730", getYzb730()); //查询统计方案WHERE条件流水号
		map.put("yzb710", getYzb710()); //查询统计方案流水号
		map.put("yzb720", getYzb720()); //查询统计方案WHERE条件分组流水号
		map.put("yzb731", getYzb731()); //分组内排序号
		map.put("yzb620", getYzb620()); //查询统计主题的项目流水号
		map.put("yzb631", getYzb631()); //选择的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
		map.put("yzb733", getYzb733()); //输入框中录入的值
		map.put("yzb734", getYzb734()); //值对象（1固定值2项目）
		map.put("aae036", getAae036()); //经办日期

		return map;
	}
}

package com.yinhai.cxtj.admin.domain;


import com.yinhai.cxtj.front.domain.InputDomain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Zb65Domain extends InputDomain {

	private static final long serialVersionUID = -1120080028623372636L;
	/** 查询统计主题排序项目流水号 */
	private BigDecimal yzb650;

	/** 排序号 */
	private Integer yzb651;

	/** 查询统计项目流水号 */
	private BigDecimal yzb620;

	/** 排序方式（1升序2降序） */
	private String yzb652;

	/** 查询统计主题流水号 */
	private BigDecimal yzb610;

	/** 数据库字段或表达式 */
	private String yzb623;

	/** 数据库字段AS别名 */
	private String yzb624;

	/** 数据库字段中文 */
	private String yzb625;

	/** 数据类型（1字符型2数字型3日期型） */
	private String yzb626;

	public Zb65Domain() {
	}

	public Zb65Domain(BigDecimal yzb650) {
		this.yzb650 = yzb650;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb650() == null) {
//			// throw new IllegalArgumentException("主键yzb650不能为空。");
//		}
//		key.put("yzb650", this.getYzb650());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb650() == null) {
			// throw new IllegalArgumentException("主键yzb650不能为空。");
		}
		key.put("yzb650", this.getYzb650());
		return key;
	}

	public Zb65Domain(BigDecimal yzb650, BigDecimal yzb620) {
		this.yzb650 = yzb650;
		this.yzb620 = yzb620;
	}

	/**
	 * 设置 yzb650 查询统计主题排序项目流水号
	 * 
	 * @param yzb650
	 *            查询统计主题排序项目流水号
	 */
	public void setYzb650(BigDecimal yzb650) {
		this.yzb650 = yzb650;
	}

	/**
	 * 获取 yzb650 查询统计主题排序项目流水号
	 * 
	 * @return 查询统计主题排序项目流水号
	 */
	public BigDecimal getYzb650() {
		return this.yzb650;
	}

	/**
	 * 设置 yzb651 排序号
	 * 
	 * @param yzb651
	 *            排序号
	 */
	public void setYzb651(Integer yzb651) {
		this.yzb651 = yzb651;
	}

	/**
	 * 获取 yzb651 排序号
	 * 
	 * @return 排序号
	 */
	public Integer getYzb651() {
		return this.yzb651;
	}

	/**
	 * 设置 yzb620 查询统计项目流水号
	 * 
	 * @param yzb620
	 *            查询统计项目流水号
	 */
	public void setYzb620(BigDecimal yzb620) {
		this.yzb620 = yzb620;
	}

	/**
	 * 获取 yzb620 查询统计项目流水号
	 * 
	 * @return 查询统计项目流水号
	 */
	public BigDecimal getYzb620() {
		return this.yzb620;
	}

	/**
	 * 设置 yzb652 排序方式（1升序2降序）
	 * 
	 * @param yzb652
	 *            排序方式（1升序2降序）
	 */
	public void setYzb652(String yzb652) {
		this.yzb652 = yzb652;
	}

	/**
	 * 获取 yzb652 排序方式（1升序2降序）
	 * 
	 * @return 排序方式（1升序2降序）
	 */
	public String getYzb652() {
		return this.yzb652;
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
	 * 设置 yzb623 数据库字段或表达式
	 * 
	 * @param yzb623
	 *            数据库字段或表达式
	 */
	public void setYzb623(String yzb623) {
		this.yzb623 = yzb623;
	}

	/**
	 * 获取 yzb623 数据库字段或表达式
	 * 
	 * @return 数据库字段或表达式
	 */
	public String getYzb623() {
		return this.yzb623;
	}

	/**
	 * 设置 yzb624 数据库字段AS别名
	 * 
	 * @param yzb624
	 *            数据库字段AS别名
	 */
	public void setYzb624(String yzb624) {
		this.yzb624 = yzb624;
	}

	/**
	 * 获取 yzb624 数据库字段AS别名
	 * 
	 * @return 数据库字段AS别名
	 */
	public String getYzb624() {
		return this.yzb624;
	}

	/**
	 * 设置 yzb625 数据库字段中文
	 * 
	 * @param yzb625
	 *            数据库字段中文
	 */
	public void setYzb625(String yzb625) {
		this.yzb625 = yzb625;
	}

	/**
	 * 获取 yzb625 数据库字段中文
	 * 
	 * @return 数据库字段中文
	 */
	public String getYzb625() {
		return this.yzb625;
	}

	/**
	 * 设置 yzb626 数据类型（1字符型2数字型3日期型）
	 * 
	 * @param yzb626
	 *            数据类型（1字符型2数字型3日期型）
	 */
	public void setYzb626(String yzb626) {
		this.yzb626 = yzb626;
	}

	/**
	 * 获取 yzb626 数据类型（1字符型2数字型3日期型）
	 * 
	 * @return 数据类型（1字符型2数字型3日期型）
	 */
	public String getYzb626() {
		return this.yzb626;
	}

	/**
	 * 转换为map对象
	 * 
	 * @return Map
	 */
	public Map toMap() {
		Map map = new HashMap();
		map.put("yzb650", getYzb650()); // 查询统计主题排序项目流水号
		map.put("yzb651", getYzb651()); // 排序号
		map.put("yzb620", getYzb620()); // 查询统计项目流水号
		map.put("yzb652", getYzb652()); // 排序方式（1升序2降序）

		map.put("yzb610", getYzb610()); // 查询统计主题流水号
		map.put("yzb623", getYzb623()); // 数据库字段或表达式
		map.put("yzb624", getYzb624()); // 数据库字段AS别名
		map.put("yzb625", getYzb625()); // 数据库字段中文
		map.put("yzb626", getYzb626()); // 数据类型（1字符型2数字型3日期型）
		return map;
	}

}

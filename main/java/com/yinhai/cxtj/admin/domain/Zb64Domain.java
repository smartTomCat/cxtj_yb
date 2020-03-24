package com.yinhai.cxtj.admin.domain;

import com.yinhai.cxtj.front.domain.InputDomain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Zb64Domain extends InputDomain {

	private static final long serialVersionUID = 645933687208382950L;
	/** 计算方式流水号 */
	private BigDecimal yzb640;

	/** 查询统计项目流水号 */
	private BigDecimal yzb620;

	/** 查询结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值） */
	private String yzb641;

	private String yzb641_desc;

	public Zb64Domain() {
	}

	public Zb64Domain(BigDecimal yzb640) {
		this.yzb640 = yzb640;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb640() == null) {
//			// throw new IllegalArgumentException("主键yzb640不能为空。");
//		}
//		key.put("yzb640", this.getYzb640());
//		return key;
//	}

	//change by zhaohss
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb640() == null) {
			// throw new IllegalArgumentException("主键yzb640不能为空。");
		}
		key.put("yzb640", this.getYzb640());
		return key;
	}

	public Zb64Domain(BigDecimal yzb640, BigDecimal yzb620) {
		this.yzb640 = yzb640;
		this.yzb620 = yzb620;
	}

	/**
	 * 设置 yzb640 计算方式流水号
	 * 
	 * @param yzb640
	 *            计算方式流水号
	 */
	public void setYzb640(BigDecimal yzb640) {
		this.yzb640 = yzb640;
	}

	/**
	 * 获取 yzb640 计算方式流水号
	 * 
	 * @return 计算方式流水号
	 */
	public BigDecimal getYzb640() {
		return this.yzb640;
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
	 * 设置 yzb641 查询结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * 
	 * @param yzb641
	 *            查询结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 */
	public void setYzb641(String yzb641) {
		this.yzb641 = yzb641;
	}

	/**
	 * 获取 yzb641 查询结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * 
	 * @return 查询结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 */
	public String getYzb641() {
		return this.yzb641;
	}

	public String getYzb641_desc() {
		return yzb641_desc;
	}

	public void setYzb641_desc(String yzb641_desc) {
		this.yzb641_desc = yzb641_desc;
	}

	/**
	 * 转换为map对象
	 * 
	 * @return Map
	 */
	public Map toMap() {
		Map map = new HashMap();
		map.put("yzb640", getYzb640()); // 计算方式流水号
		map.put("yzb620", getYzb620()); // 查询统计项目流水号
		map.put("yzb641", getYzb641()); // 查询结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）

		map.put("yzb641_desc", getYzb641_desc());
		return map;
	}

}

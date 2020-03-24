package com.yinhai.cxtj.admin.domain;

import com.yinhai.cxtj.front.domain.InputDomain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Zb63Domain extends InputDomain {

	private static final long serialVersionUID = 4278927716724135988L;
	/** 关系符流水号 */
	private BigDecimal yzb630;

	/** 查询统计项目流水号 */
	private BigDecimal yzb620;

	/** 支持的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含） */
	private String yzb631;

	private String yzb631_desc;

	public Zb63Domain() {
	}

	public Zb63Domain(BigDecimal yzb630) {
		this.yzb630 = yzb630;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb630() == null) {
//			// throw new IllegalArgumentException("主键yzb630不能为空。");
//		}
//		key.put("yzb630", this.getYzb630());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb630() == null) {
			// throw new IllegalArgumentException("主键yzb630不能为空。");
		}
		key.put("yzb630", this.getYzb630());
		return key;
	}

	public Zb63Domain(BigDecimal yzb630, BigDecimal yzb620, String yzb631) {
		this.yzb630 = yzb630;
		this.yzb620 = yzb620;
		this.yzb631 = yzb631;
	}

	/**
	 * 设置 yzb630 关系符流水号
	 * 
	 * @param yzb630
	 *            关系符流水号
	 */
	public void setYzb630(BigDecimal yzb630) {
		this.yzb630 = yzb630;
	}

	/**
	 * 获取 yzb630 关系符流水号
	 * 
	 * @return 关系符流水号
	 */
	public BigDecimal getYzb630() {
		return this.yzb630;
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
	 * 设置 yzb631 支持的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 * 
	 * @param yzb631
	 *            支持的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 */
	public void setYzb631(String yzb631) {
		this.yzb631 = yzb631;
	}

	/**
	 * 获取 yzb631 支持的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 * 
	 * @return 支持的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
	 */
	public String getYzb631() {
		return this.yzb631;
	}

	public String getYzb631_desc() {
		return yzb631_desc;
	}

	public void setYzb631_desc(String yzb631_desc) {
		this.yzb631_desc = yzb631_desc;
	}

	/**
	 * 转换为map对象
	 * 
	 * @return Map
	 */
	public Map toMap() {
		Map map = new HashMap();
		map.put("yzb630", getYzb630()); // 关系符流水号
		map.put("yzb620", getYzb620()); // 查询统计项目流水号
		map.put("yzb631", getYzb631()); // 支持的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）

		map.put("yzb631_desc", getYzb631_desc());
		return map;
	}

}

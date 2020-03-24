package com.yinhai.cxtj.admin.domain;

import com.yinhai.cxtj.front.domain.InputDomain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Zb62Domain extends InputDomain {

	private static final long serialVersionUID = -7382893282570418948L;
	/** 查询统计项目流水号 */
	private BigDecimal yzb620;

	/** 查询统计主题流水号 */
	private BigDecimal yzb610;

	/** 排序号 */
	private Integer yzb621;

	/** 数据库字段或表达式 */
	private String yzb623;

	/** 数据库字段AS别名 */
	private String yzb624;

	/** 数据库字段中文 */
	private String yzb625;

	/** 数据类型（1字符型2数字型3日期型） */
	private String yzb626;

	/** 代码类别 */
	private String yzb628;

	/** 是否作为查询条件（1是0否） */
	private String yzb62d;

	/** 查询条件的展现形式（11文本12年月13日期14数字21代码值平铺22树23DATAGRID中选择） */
	private String yzb62a;

	/** 分组控制（0不用于分组1默认选中2默认未选中） */
	private String yzb62b;

	/** 分组计算控制（0不用于计算1默认计算2默认不计算） */
	private String yzb62c;

	/** 默认统计方式（1计数2去重后计数3求和4求平均5最大值6最小值） */
	private String yzb641;

	/** 查询列控制（0不能查询1默认查询2默认不查询） */
	private String yzb62f;

	/** 有效标志（1有效0无效） */
	private String aae100;

	private String yzb626_desc;

	private String yzb62d_desc;

	private String yzb62a_desc;

	private String yzb62b_desc;

	private String yzb62c_desc;

	private String yzb641_desc;

	private String yzb62f_desc;

	private String aae100_desc;

	public Zb62Domain() {
	}

	public Zb62Domain(BigDecimal yzb620) {
		this.yzb620 = yzb620;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb620() == null) {
//			// throw new IllegalArgumentException("主键yzb620不能为空。");
//		}
//		key.put("yzb620", this.getYzb620());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb620() == null) {
			// throw new IllegalArgumentException("主键yzb620不能为空。");
		}
		key.put("yzb620", this.getYzb620());
		return key;
	}

	public Zb62Domain(BigDecimal yzb620, BigDecimal yzb610) {
		this.yzb620 = yzb620;
		this.yzb610 = yzb610;
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
	 * 设置 yzb621 排序号
	 * 
	 * @param yzb621
	 *            排序号
	 */
	public void setYzb621(Integer yzb621) {
		this.yzb621 = yzb621;
	}

	/**
	 * 获取 yzb621 排序号
	 * 
	 * @return 排序号
	 */
	public Integer getYzb621() {
		return this.yzb621;
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
	 * 设置 yzb628 代码类别
	 * 
	 * @param yzb628
	 *            代码类别
	 */
	public void setYzb628(String yzb628) {
		this.yzb628 = yzb628;
	}

	/**
	 * 获取 yzb628 代码类别
	 * 
	 * @return 代码类别
	 */
	public String getYzb628() {
		return this.yzb628;
	}

	/**
	 * 设置 yzb62d 是否作为查询条件（1是0否）
	 * 
	 * @param yzb62d
	 *            是否作为查询条件（1是0否）
	 */
	public void setYzb62d(String yzb62d) {
		this.yzb62d = yzb62d;
	}

	/**
	 * 获取 yzb62d 是否作为查询条件（1是0否）
	 * 
	 * @return 是否作为查询条件（1是0否）
	 */
	public String getYzb62d() {
		return this.yzb62d;
	}

	/**
	 * 设置 yzb62a 查询条件的展现形式（11文本12年月13日期14数字21代码值平铺22树23DATAGRID中选择）
	 * 
	 * @param yzb62a
	 *            查询条件的展现形式（11文本12年月13日期14数字21代码值平铺22树23DATAGRID中选择）
	 */
	public void setYzb62a(String yzb62a) {
		this.yzb62a = yzb62a;
	}

	/**
	 * 获取 yzb62a 查询条件的展现形式（11文本12年月13日期14数字21代码值平铺22树23DATAGRID中选择）
	 * 
	 * @return 查询条件的展现形式（11文本12年月13日期14数字21代码值平铺22树23DATAGRID中选择）
	 */
	public String getYzb62a() {
		return this.yzb62a;
	}

	/**
	 * 设置 yzb62b 分组控制（0不用于分组1默认选中2默认未选中）
	 * 
	 * @param yzb62b
	 *            分组控制（0不用于分组1默认选中2默认未选中）
	 */
	public void setYzb62b(String yzb62b) {
		this.yzb62b = yzb62b;
	}

	/**
	 * 获取 yzb62b 分组控制（0不用于分组1默认选中2默认未选中）
	 * 
	 * @return 分组控制（0不用于分组1默认选中2默认未选中）
	 */
	public String getYzb62b() {
		return this.yzb62b;
	}

	/**
	 * 设置 yzb62c 分组计算控制（0不用于计算1默认计算2默认不计算）
	 * 
	 * @param yzb62c
	 *            分组计算控制（0不用于计算1默认计算2默认不计算）
	 */
	public void setYzb62c(String yzb62c) {
		this.yzb62c = yzb62c;
	}

	/**
	 * 获取 yzb62c 分组计算控制（0不用于计算1默认计算2默认不计算）
	 * 
	 * @return 分组计算控制（0不用于计算1默认计算2默认不计算）
	 */
	public String getYzb62c() {
		return this.yzb62c;
	}

	/**
	 * 设置 yzb641 默认统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * 
	 * @param yzb641
	 *            默认统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 */
	public void setYzb641(String yzb641) {
		this.yzb641 = yzb641;
	}

	/**
	 * 获取 yzb641 默认统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 * 
	 * @return 默认统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
	 */
	public String getYzb641() {
		return this.yzb641;
	}

	/**
	 * 设置 yzb62f 查询列控制（0不能查询1默认查询2默认不查询）
	 * 
	 * @param yzb62f
	 *            查询列控制（0不能查询1默认查询2默认不查询）
	 */
	public void setYzb62f(String yzb62f) {
		this.yzb62f = yzb62f;
	}

	/**
	 * 获取 yzb62f 查询列控制（0不能查询1默认查询2默认不查询）
	 * 
	 * @return 查询列控制（0不能查询1默认查询2默认不查询）
	 */
	public String getYzb62f() {
		return this.yzb62f;
	}

	/**
	 * 设置 aae100 有效标志（1有效0无效）
	 * 
	 * @param aae100
	 *            有效标志（1有效0无效）
	 */
	public void setAae100(String aae100) {
		this.aae100 = aae100;
	}

	/**
	 * 获取 aae100 有效标志（1有效0无效）
	 * 
	 * @return 有效标志（1有效0无效）
	 */
	public String getAae100() {
		return this.aae100;
	}

	public String getYzb626_desc() {
		return yzb626_desc;
	}

	public void setYzb626_desc(String yzb626_desc) {
		this.yzb626_desc = yzb626_desc;
	}

	public String getYzb62d_desc() {
		return yzb62d_desc;
	}

	public void setYzb62d_desc(String yzb62d_desc) {
		this.yzb62d_desc = yzb62d_desc;
	}

	public String getYzb62a_desc() {
		return yzb62a_desc;
	}

	public void setYzb62a_desc(String yzb62a_desc) {
		this.yzb62a_desc = yzb62a_desc;
	}

	public String getYzb62b_desc() {
		return yzb62b_desc;
	}

	public void setYzb62b_desc(String yzb62b_desc) {
		this.yzb62b_desc = yzb62b_desc;
	}

	public String getYzb62c_desc() {
		return yzb62c_desc;
	}

	public void setYzb62c_desc(String yzb62c_desc) {
		this.yzb62c_desc = yzb62c_desc;
	}

	public String getYzb641_desc() {
		return yzb641_desc;
	}

	public void setYzb641_desc(String yzb641_desc) {
		this.yzb641_desc = yzb641_desc;
	}

	public String getYzb62f_desc() {
		return yzb62f_desc;
	}

	public void setYzb62f_desc(String yzb62f_desc) {
		this.yzb62f_desc = yzb62f_desc;
	}

	public String getAae100_desc() {
		return aae100_desc;
	}

	public void setAae100_desc(String aae100_desc) {
		this.aae100_desc = aae100_desc;
	}

	/**
	 * 转换为map对象
	 * 
	 * @return Map
	 */
	public Map toMap() {
		Map map = new HashMap();
		map.put("yzb620", getYzb620()); // 查询统计项目流水号
		map.put("yzb610", getYzb610()); // 查询统计主题流水号
		map.put("yzb621", getYzb621()); // 排序号
		map.put("yzb623", getYzb623()); // 数据库字段或表达式
		map.put("yzb624", getYzb624()); // 数据库字段AS别名
		map.put("yzb625", getYzb625()); // 数据库字段中文
		map.put("yzb626", getYzb626()); // 数据类型（1字符型2数字型3日期型）
		map.put("yzb628", getYzb628()); // 代码类别
		map.put("yzb62d", getYzb62d()); // 是否作为查询条件（1是0否）
		map.put("yzb62a", getYzb62a()); // 查询条件的展现形式（11文本12年月13日期14数字21代码值平铺22树23DATAGRID中选择）
		map.put("yzb62b", getYzb62b()); // 分组控制（0不用于分组1默认选中2默认未选中）
		map.put("yzb62c", getYzb62c()); // 分组计算控制（0不用于计算1默认计算2默认不计算）
		map.put("yzb641", getYzb641()); // 默认统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
		map.put("yzb62f", getYzb62f()); // 查询列控制（0不能查询1默认查询2默认不查询）
		map.put("aae100", getAae100()); // 有效标志（1有效0无效）

		map.put("yzb626_desc", getYzb626_desc());
		map.put("yzb62d_desc", getYzb62d_desc());
		map.put("yzb62a_desc", getYzb62a_desc());
		map.put("yzb62b_desc", getYzb62b_desc());
		map.put("yzb62c_desc", getYzb62c_desc());
		map.put("yzb641_desc", getYzb641_desc());
		map.put("yzb62f_desc", getYzb62f_desc());
		map.put("aae100_desc", getAae100_desc());
		return map;
	}

}

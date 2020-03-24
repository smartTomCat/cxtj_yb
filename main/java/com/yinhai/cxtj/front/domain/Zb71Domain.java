package com.yinhai.cxtj.front.domain;



import java.util.HashMap;
import java.util.Map;

public class Zb71Domain extends InputDomain {

	private static final long serialVersionUID = -8070204269406886653L;
	/** 查询统计方案流水号 */
	private java.math.BigDecimal yzb710;

	/** 查询统计主题流水号 */
	private java.math.BigDecimal yzb610;

	/** 查询统计方案名称 */
	private String yzb711;

	/** 查询统计方式（1查询统计2分组统计） */
	private String yzb617;

	/** 方案保存期限（1永久2临时） */
	private String yzb713;

	/** 经办人 */
	private String aae011;

	/** 经办人编号 */
	private Long yae116;

	/** 经办机构 */
	private Long aae017;

	/** 经办日期 */
	private java.sql.Timestamp aae036;

	public Zb71Domain() {
	}

	public Zb71Domain(java.math.BigDecimal yzb710) {
		this.yzb710 = yzb710;
	}

//	public Key getPK() {
//		Key key = new Key();
//		if (this.getYzb710() == null) {
//			//throw new IllegalArgumentException("主键yzb710不能为空。");
//		}
//		key.put("yzb710", this.getYzb710());
//		return key;
//	}

	//change by zhaohs
	public Map getPK() {
		Map key = new HashMap();
		if (this.getYzb710() == null) {
			//throw new IllegalArgumentException("主键yzb710不能为空。");
		}
		key.put("yzb710", this.getYzb710());
		return key;
	}

	public Zb71Domain(java.math.BigDecimal yzb710, java.math.BigDecimal yzb610,
			String aae011, Long yae116, Long aae017, java.sql.Timestamp aae036) {
		this.yzb710 = yzb710;
		this.yzb610 = yzb610;
		this.aae011 = aae011;
		this.yae116 = yae116;
		this.aae017 = aae017;
		this.aae036 = aae036;
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
	 * 设置 yzb610 查询统计主题流水号
	 * @param yzb610 查询统计主题流水号
	 */
	public void setYzb610(java.math.BigDecimal yzb610) {
		this.yzb610 = yzb610;
	}

	/**
	 * 获取 yzb610 查询统计主题流水号
	 * @return 查询统计主题流水号
	 */
	public java.math.BigDecimal getYzb610() {
		return this.yzb610;
	}

	/**
	 * 设置 yzb711 查询统计方案名称
	 * @param yzb711 查询统计方案名称
	 */
	public void setYzb711(String yzb711) {
		this.yzb711 = yzb711;
	}

	/**
	 * 获取 yzb711 查询统计方案名称
	 * @return 查询统计方案名称
	 */
	public String getYzb711() {
		return this.yzb711;
	}

	/**
	 * 设置 yzb617 查询统计方式（1查询统计2分组统计）
	 * @param yzb617 查询统计方式（1查询统计2分组统计）
	 */
	public void setYzb617(String yzb617) {
		this.yzb617 = yzb617;
	}

	/**
	 * 获取 yzb617 查询统计方式（1查询统计2分组统计）
	 * @return 查询统计方式（1查询统计2分组统计）
	 */
	public String getYzb617() {
		return this.yzb617;
	}

	/**
	 * 设置 yzb713 方案保存期限（1永久2临时）
	 * @param yzb713 方案保存期限（1永久2临时）
	 */
	public void setYzb713(String yzb713) {
		this.yzb713 = yzb713;
	}

	/**
	 * 获取 yzb713 方案保存期限（1永久2临时）
	 * @return 方案保存期限（1永久2临时）
	 */
	public String getYzb713() {
		return this.yzb713;
	}

	/**
	 * 设置 aae011 经办人
	 * @param aae011 经办人
	 */
	@Override
	public void setAae011(String aae011) {
		this.aae011 = aae011;
	}

	/**
	 * 获取 aae011 经办人
	 * @return 经办人
	 */
	@Override
	public String getAae011() {
		return this.aae011;
	}

	/**
	 * 设置 yae116 经办人编号
	 * @param yae116 经办人编号
	 */
	@Override
	public void setYae116(Long yae116) {
		this.yae116 = yae116;
	}

	/**
	 * 获取 yae116 经办人编号
	 * @return 经办人编号
	 */
	@Override
	public Long getYae116() {
		return this.yae116;
	}

	/**
	 * 设置 aae017 经办机构
	 * @param aae017 经办机构
	 */
	@Override
	public void setAae017(Long aae017) {
		this.aae017 = aae017;
	}

	/**
	 * 获取 aae017 经办机构
	 * @return 经办机构
	 */
	@Override
	public Long getAae017() {
		return this.aae017;
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
		map.put("yzb710", getYzb710()); //查询统计方案流水号
		map.put("yzb610", getYzb610()); //查询统计主题流水号
		map.put("yzb711", getYzb711()); //查询统计方案名称
		map.put("yzb617", getYzb617()); //查询统计方式（1查询统计2分组统计）
		map.put("yzb713", getYzb713()); //方案保存期限（1永久2临时）
		map.put("aae011", getAae011()); //经办人
		map.put("yae116", getYae116()); //经办人编号
		map.put("aae017", getAae017()); //经办机构
		map.put("aae036", getAae036()); //经办日期

		return map;
	}

}

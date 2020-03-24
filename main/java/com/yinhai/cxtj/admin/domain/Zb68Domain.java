package com.yinhai.cxtj.admin.domain;

import com.yinhai.cxtj.front.domain.InputDomain;

import java.math.BigDecimal;

/**
 * Created by zhaohs on 2020/3/17.
 */
public class Zb68Domain extends InputDomain {
    private static final long serialVersionUID = -1120080028623372636L;

    public Zb68Domain() {
    }

    /**
     * 视图配置管理流水号
     */
    private BigDecimal yzb680;
    /**
     * 视图名称
     */
    private String yzb681;
    /**
     * 视图sql
     */
    private String yzb682;
    /**
     * 数据源配置流水号
     */
    private BigDecimal yzb670;

    public BigDecimal getYzb680() {
        return yzb680;
    }

    public void setYzb680(BigDecimal yzb680) {
        this.yzb680 = yzb680;
    }

    public String getYzb681() {
        return yzb681;
    }

    public void setYzb681(String yzb681) {
        this.yzb681 = yzb681;
    }

    public String getYzb682() {
        return yzb682;
    }

    public void setYzb682(String yzb682) {
        this.yzb682 = yzb682;
    }

    public BigDecimal getYzb670() {
        return yzb670;
    }

    public void setYzb670(BigDecimal yzb670) {
        this.yzb670 = yzb670;
    }


}

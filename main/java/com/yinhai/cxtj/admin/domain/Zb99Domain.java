package com.yinhai.cxtj.admin.domain;
import com.yinhai.core.common.ta3.vo.BaseVo;

import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;
/**
 * Created by zhaohs on 2019/7/17.
 */
public class Zb99Domain extends BaseVo{
    private static final long serialVersionUID = -21319902075078719L;
    //查询统计方案统计指标流水号
    private Integer yzb990;
    //查询统计主题名称
    private String yzb612;
    //查询统计方案名称
    private String yzb711;
    //执行开始时间
    private Timestamp yzb991;
    //执行结束时间
    private Timestamp yzb992;
    //执行总耗时（秒）
    private Double yzb993;
    //是否出错（0否1是）
    private String yzb994;
    //错误消息
    private Object yzb995;
    //执行sql集
    private String yzb996;
    //经办人
    private String aae011;
    //经办人编号
    private Integer yae116;
    //经办机构
    private Integer aae017;
    //经办日期
    private Timestamp aae036;


    public Integer getYzb990() {
        return yzb990;
    }

    public void setYzb990(Integer yzb990) {
        this.yzb990 = yzb990;
    }

    public String getYzb612() {
        return yzb612;
    }

    public void setYzb612(String yzb612) {
        this.yzb612 = yzb612;
    }

    public String getYzb711() {
        return yzb711;
    }

    public void setYzb711(String yzb711) {
        this.yzb711 = yzb711;
    }

    public Timestamp getYzb991() {
        return yzb991;
    }

    public void setYzb991(Timestamp yzb991) {
        this.yzb991 = yzb991;
    }

    public Timestamp getYzb992() {
        return yzb992;
    }

    public void setYzb992(Timestamp yzb992) {
        this.yzb992 = yzb992;
    }

    public void setAae036(Timestamp aae036) {
        this.aae036 = aae036;
    }

    public Double getYzb993() {
        return yzb993;
    }

    public void setYzb993(Double yzb993) {
        this.yzb993 = yzb993;
    }

    public String getYzb994() {
        return yzb994;
    }

    public void setYzb994(String yzb994) {
        this.yzb994 = yzb994;
    }

    public Object getYzb995() {
        return yzb995;
    }

    public void setYzb995(Object yzb995) {
        this.yzb995 = yzb995;
    }

    public String getYzb996() {
        return yzb996;
    }

    public void setYzb996(String yzb996) {
        this.yzb996 = yzb996;
    }

    public String getAae011() {
        return aae011;
    }

    public void setAae011(String aae011) {
        this.aae011 = aae011;
    }

    public Integer getYae116() {
        return yae116;
    }

    public void setYae116(Integer yae116) {
        this.yae116 = yae116;
    }

    public Integer getAae017() {
        return aae017;
    }

    public void setAae017(Integer aae017) {
        this.aae017 = aae017;
    }


    public Map toMap() {
        Map map = new HashMap();
        map.put("yzb990", yzb990);
        map.put("yzb612", yzb612);
        map.put("yzb711", yzb711);
        map.put("yzb991", yzb991);
        map.put("yzb992", yzb992);
        map.put("yzb993", yzb993);
        map.put("yzb994", yzb994);
        map.put("yzb995", yzb995);
        map.put("yzb996", yzb996);
        map.put("aae011", aae011);
        map.put("yae116", yae116);
        map.put("aae017", aae017);
        map.put("aae036", aae036);
        return map;
    }

    @Override
    public String toString() {
        return "Zb99{" +
                ",yzb990 ='" + yzb990 + '\'' +
                ",yzb612 ='" + yzb612 + '\'' +
                ",yzb711 ='" + yzb711 + '\'' +
                ",yzb991 ='" + yzb991 + '\'' +
                ",yzb992 ='" + yzb992 + '\'' +
                ",yzb993 ='" + yzb993 + '\'' +
                ",yzb994 ='" + yzb994 + '\'' +
                ",yzb995 ='" + yzb995 + '\'' +
                ",yzb996 ='" + yzb996 + '\'' +
                ",aae011 ='" + aae011 + '\'' +
                ",yae116 ='" + yae116 + '\'' +
                ",aae017 ='" + aae017 + '\'' +
                ",aae036 ='" + aae036 + '\'' +
                '}';
    }

}

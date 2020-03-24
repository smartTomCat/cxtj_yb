package com.yinhai.cxtj.admin.domain;


import com.yinhai.core.common.ta3.vo.BaseVo;

import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by zhaohs on 2019/7/17.
 */
public class Zb67Domain extends BaseVo {

    public Zb67Domain() {
    }

    public Zb67Domain(Integer yzb670, String yzb671, String yzb672, String yzb673, String yzb674, String yzb675, String yzb676, String yzb677, String yzb678, String yzb679, Integer yzb681, Integer yzb682, Integer yzb683, Integer yzb684, Integer yzb685, Integer yzb686, Integer yzb687, Integer yzb688, String aae011, Integer yae116, Integer aae017, Timestamp aae036) {
        this.yzb670 = yzb670;
        this.yzb671 = yzb671;
        this.yzb672 = yzb672;
        this.yzb673 = yzb673;
        this.yzb674 = yzb674;
        this.yzb675 = yzb675;
        this.yzb676 = yzb676;
        this.yzb677 = yzb677;
        this.yzb678 = yzb678;
        this.yzb679 = yzb679;
        this.yzb681 = yzb681;
        this.yzb682 = yzb682;
        this.yzb683 = yzb683;
        this.yzb684 = yzb684;
        this.yzb685 = yzb685;
        this.yzb686 = yzb686;
        this.yzb687 = yzb687;
        this.yzb688 = yzb688;
        this.aae011 = aae011;
        this.yae116 = yae116;
        this.aae017 = aae017;
        this.aae036 = aae036;
    }

    private static final long serialVersionUID = -84804356947609390L;
    //数据源配置管理流水号
    private Integer yzb670;
    //数据源类型(01jdbc数据源02jndi数据源)
    private String yzb671;
    //数据源名称
    private String yzb672;
    //数据源描述
    private String yzb673;
    //数据库类型(01oracle,02postgresql,03gbase8a,04mysql)
    private String yzb674;
    //JNDI数据源名称
    private String yzb675;
    //数据库驱动
    private String yzb676;
    //连接URL
    private String yzb677;
    //用户名
    private String yzb678;
    //密码
    private String yzb679;
    //初始连接数
    private Integer yzb681;
    //最大连接数
    private Integer yzb682;
    //最大闲置秒数
    private Integer yzb683;
    //最大获取连接秒数
    private Integer yzb684;
    //连接失败重新获取秒数
    private Integer yzb685;
    //连接最大存活秒数
    private Integer yzb686;
    //连接回收执行间隔秒数
    private Integer yzb687;
    //最大获取数据秒数
    private Integer yzb688;
    //经办人
    private String aae011;
    //经办人编号
    private Integer yae116;
    //经办机构
    private Integer aae017;
    //经办日期
    private Timestamp aae036;


    public Integer getYzb670() {
        return yzb670;
    }

    public void setYzb670(Integer yzb670) {
        this.yzb670 = yzb670;
    }

    public String getYzb671() {
        return yzb671;
    }

    public void setYzb671(String yzb671) {
        this.yzb671 = yzb671;
    }

    public String getYzb672() {
        return yzb672;
    }

    public void setYzb672(String yzb672) {
        this.yzb672 = yzb672;
    }

    public String getYzb673() {
        return yzb673;
    }

    public void setYzb673(String yzb673) {
        this.yzb673 = yzb673;
    }

    public String getYzb674() {
        return yzb674;
    }

    public void setYzb674(String yzb674) {
        this.yzb674 = yzb674;
    }

    public String getYzb675() {
        return yzb675;
    }

    public void setYzb675(String yzb675) {
        this.yzb675 = yzb675;
    }

    public String getYzb676() {
        return yzb676;
    }

    public void setYzb676(String yzb676) {
        this.yzb676 = yzb676;
    }

    public String getYzb677() {
        return yzb677;
    }

    public void setYzb677(String yzb677) {
        this.yzb677 = yzb677;
    }

    public String getYzb678() {
        return yzb678;
    }

    public void setYzb678(String yzb678) {
        this.yzb678 = yzb678;
    }

    public String getYzb679() {
        return yzb679;
    }

    public void setYzb679(String yzb679) {
        this.yzb679 = yzb679;
    }

    public Integer getYzb681() {
        return yzb681;
    }

    public void setYzb681(Integer yzb681) {
        this.yzb681 = yzb681;
    }

    public Integer getYzb682() {
        return yzb682;
    }

    public void setYzb682(Integer yzb682) {
        this.yzb682 = yzb682;
    }

    public Integer getYzb683() {
        return yzb683;
    }

    public void setYzb683(Integer yzb683) {
        this.yzb683 = yzb683;
    }

    public Integer getYzb684() {
        return yzb684;
    }

    public void setYzb684(Integer yzb684) {
        this.yzb684 = yzb684;
    }

    public Integer getYzb685() {
        return yzb685;
    }

    public void setYzb685(Integer yzb685) {
        this.yzb685 = yzb685;
    }

    public Integer getYzb686() {
        return yzb686;
    }

    public void setYzb686(Integer yzb686) {
        this.yzb686 = yzb686;
    }

    public Integer getYzb687() {
        return yzb687;
    }

    public void setYzb687(Integer yzb687) {
        this.yzb687 = yzb687;
    }

    public Integer getYzb688() {
        return yzb688;
    }

    public void setYzb688(Integer yzb688) {
        this.yzb688 = yzb688;
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

    public Timestamp getAae036() {
        return aae036;
    }

    public void setAae036(Timestamp aae036) {
        this.aae036 = aae036;
    }

    public Map toMap() {
        Map map = new HashMap();
        map.put("yzb670", yzb670);
        map.put("yzb671", yzb671);
        map.put("yzb672", yzb672);
        map.put("yzb673", yzb673);
        map.put("yzb674", yzb674);
        map.put("yzb675", yzb675);
        map.put("yzb676", yzb676);
        map.put("yzb677", yzb677);
        map.put("yzb678", yzb678);
        map.put("yzb679", yzb679);
        map.put("yzb681", yzb681);
        map.put("yzb682", yzb682);
        map.put("yzb683", yzb683);
        map.put("yzb684", yzb684);
        map.put("yzb685", yzb685);
        map.put("yzb686", yzb686);
        map.put("yzb687", yzb687);
        map.put("yzb688", yzb688);
        map.put("aae011", aae011);
        map.put("yae116", yae116);
        map.put("aae017", aae017);
        map.put("aae036", aae036);
        return map;
    }
//
//    @Override
//    public String toString() {
//        return "Zb67{" +
//                ",yzb670 ='" + yzb670 + '\'' +
//                ",yzb671 ='" + yzb671 + '\'' +
//                ",yzb672 ='" + yzb672 + '\'' +
//                ",yzb673 ='" + yzb673 + '\'' +
//                ",yzb674 ='" + yzb674 + '\'' +
//                ",yzb675 ='" + yzb675 + '\'' +
//                ",yzb676 ='" + yzb676 + '\'' +
//                ",yzb677 ='" + yzb677 + '\'' +
//                ",yzb678 ='" + yzb678 + '\'' +
//                ",yzb679 ='" + yzb679 + '\'' +
//                ",yzb681 ='" + yzb681 + '\'' +
//                ",yzb682 ='" + yzb682 + '\'' +
//                ",yzb683 ='" + yzb683 + '\'' +
//                ",yzb684 ='" + yzb684 + '\'' +
//                ",yzb685 ='" + yzb685 + '\'' +
//                ",yzb686 ='" + yzb686 + '\'' +
//                ",yzb687 ='" + yzb687 + '\'' +
//                ",yzb688 ='" + yzb688 + '\'' +
//                ",aae011 ='" + aae011 + '\'' +
//                ",yae116 ='" + yae116 + '\'' +
//                ",aae017 ='" + aae017 + '\'' +
//                ",aae036 ='" + aae036 + '\'' +
//                '}';
//    }



}

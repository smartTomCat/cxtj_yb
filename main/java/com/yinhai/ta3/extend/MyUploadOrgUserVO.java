package com.yinhai.ta3.extend;

import com.yinhai.modules.org.api.vo.upload.UploadOrgUserVO;

/**
 * @author aolei 自定义扩展导入组织人员类
 */
public class MyUploadOrgUserVO extends UploadOrgUserVO {

    private String lxr;//联系人 组织属性
    private String idcard;//身份证号 人员属性

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * 导入组织跟人员,重写方法
     *
     * @return
     */
    @Override
    public String getOrgUserFieldsStr() {
        String str = super.getOrgUserFieldsStr();
        return str + ",lxr,idcard";//+扩展字段,注意第一个逗号
    }

    /**
     * 导入组织，重写方法
     *
     * @return
     */
    @Override
    public String getOrgFieldsStr() {
        String str = super.getOrgFieldsStr();
        return str + ",lxr";//+扩展字段,注意第一个逗号
    }

    /**
     * 导入组人员，重写方法
     *
     * @return
     */
    @Override
    public String getUserFieldsStr() {
        String str = super.getUserFieldsStr();
        return str + ",idcard";//+扩展字段,注意第一个逗号
    }
}

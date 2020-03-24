package com.yinhai.cxtj.admin.service.impl;

import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.Zb67Domain;
import com.yinhai.cxtj.admin.service.DataSourceManageService;
import com.yinhai.cxtj.admin.util.Base64Util;
import com.yinhai.sysframework.persistence.PageBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaohs on 2019/7/17.
 */
public class DataSourceManageServiceImpl extends CxtjBaseServiceImpl implements DataSourceManageService {

    @Override
    public PageBean queryList(TaParamDto dto) throws Exception {
        PageBean pageBean = dao.queryForPageWithCount("dataSourceGrid", "zb67.getList", dto, dto);
        return pageBean;
    }

    @Override
    public Boolean checkNameExist(TaParamDto dto) throws Exception {
        Integer count= (Integer) dao.queryForObject("zb67.valYzb672",dto);
        if (0 == count) {
            return false;
        }else {
            return true;
        }
    }

    @Override
    public Boolean testConnect(TaParamDto dto) throws Exception {
        Zb67Domain zb67Domain = new Zb67Domain();
        zb67Domain = (Zb67Domain )  dto.toVO(zb67Domain.getClass());
        Boolean b;
        try{
            b = super.testConnection(zb67Domain);
        }catch (Exception e){
            e.printStackTrace();
            throw new AppException("连接失败，请检查数据源配置 " + e.getMessage());
        }
        return b;
    }


    @Override
    public Zb67Domain saveDataSource(TaParamDto dto) throws Exception {
        Zb67Domain zb67Domain = new Zb67Domain();
        zb67Domain = (Zb67Domain) dto.toVO(zb67Domain.getClass());
        zb67Domain.setYzb670(Integer.valueOf(getStringSeq("seq_yzb670")));
        zb67Domain.setAae011(dto.getUser().getName());
        zb67Domain.setYae116(Integer.valueOf(dto.getUser().getUserId()));
        zb67Domain.setAae017(Integer.valueOf(dto.getUser().getOrgId()));
        zb67Domain.setAae036(super.getSysTimestamp());
        //密码明文
        String temp_Pw = zb67Domain.getYzb679();
        //数据库密码加密
        zb67Domain.setYzb679(Base64Util.encodeBase64(temp_Pw));
        dao.insert("zb67.insert",zb67Domain);
        //创建dao时传入明文
        zb67Domain.setYzb679(temp_Pw);
        super.creatDynamicDao(zb67Domain);
        return zb67Domain;
    }

    @Override
    public Map queryItemDs(TaParamDto dto) throws Exception {
        //postgresql数据库 在java层面字符串和数字不能互转
        dto.put("yzb670",dto.getAsLong("yzb670"));
        List<Zb67Domain> zb67DomainList= dao.queryForList("zb67.getList",dto);
        Map m = null;
        if(ValidateUtil.isNotEmpty(zb67DomainList)){
            m = zb67DomainList.get(0).toMap();
        }
        return m;
    }

    @Override
    public void updateDataSource(TaParamDto dto) throws Exception {
        //密码明文
        String temp_Pw = dto.getAsString("yzb679");
        //数据库密码加密
        dto.put("yzb679",Base64Util.encodeBase64(temp_Pw));
        //更改数据源配置表
        Zb67Domain zb67Domain = new Zb67Domain();
        zb67Domain = (Zb67Domain )  dto.toVO(zb67Domain.getClass());
        dao.update("zb67.updateNotEmpty",zb67Domain);
        //更改dao时传入明文
        zb67Domain.setYzb679(temp_Pw);
        super.updateDynamicDao(zb67Domain);
    }

    @Override
    public void deleteDataSource(List list) throws Exception {
        if(ValidateUtil.isNotEmpty(list)){
            Map m = null;
            for (int i = 0; i < list.size(); i++) {
                m = (Map) list.get(i);
                if(!ValidateUtil.isEmpty(m)){
                    m.put("yzb670",Long.valueOf(String.valueOf(m.get("yzb670"))));
                    dao.delete("zb67.delete",m);
                    super.removeDynamicDao(m.get("yzb670").toString());
                }
            }
        }
    }

    @Override
    public Boolean dshasZt(List list) throws Exception {
        if(ValidateUtil.isNotEmpty(list)){
            Map m = new HashMap();
            Map m2;
            Boolean b = false;
            for (int i = 0; i < list.size(); i++) {
                m2 = (Map) list.get(i);
                m.put("yzb670",Long.valueOf(String.valueOf(m2.get("yzb670"))));
                List listZb61 = dao.queryForList("zb61.getList",m);
                if (ValidateUtil.isNotEmpty(listZb61)) {
                    b = true;
                    break;
                }
            }
            return b;
        }else{
            return true;
        }
    }
}

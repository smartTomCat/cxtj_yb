package com.yinhai.cxtj.admin.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yinhai.core.common.api.config.impl.SysConfig;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ReflectUtil;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;


import com.yinhai.cxtj.admin.domain.Zb61Domain;
import com.yinhai.cxtj.admin.domain.Zb62Domain;
import com.yinhai.cxtj.admin.service.SetSearchItemService;
import com.yinhai.cxtj.admin.service.SetSearchService;
import com.yinhai.modules.authority.api.vo.MenuVo;
import com.yinhai.modules.authority.ta3.domain.blo.IMenuMgBlo;
import com.yinhai.modules.org.api.vo.user.UserVo;
import com.yinhai.modules.security.api.vo.UserAccountInfo;
import com.yinhai.sysframework.persistence.PageBean;

import javax.annotation.Resource;


/**
 * 查询统计主题
 *
 * @author
 */
public class SetSearchServiceImpl extends CxtjBaseServiceImpl implements SetSearchService {
    /**
     * 用于调用新增菜单接口
     */
    @Resource
    IMenuMgBlo menuMgBlo;

    public SetSearchItemService getSetSearchItemService() {
        return setSearchItemService;
    }

    public void setSetSearchItemService(SetSearchItemService setSearchItemService) {
        this.setSearchItemService = setSearchItemService;
    }

    private SetSearchItemService setSearchItemService;

    /**
     * 查询流程定义
     *
     * @param yzb610 查询统计主题流水号
     * @return
     * @throws Exception
     * @author
     */
    @Override
    public Zb61Domain getDomainObjectById(BigDecimal yzb610) throws Exception {
        if (!ValidateUtil.isEmpty(yzb610)) {
            Map key = new HashMap();
            key.put("yzb610", yzb610);
            return (Zb61Domain) dao.queryForObject("zb61.get", key);
        }
        return null;
    }

    /**
     * 保存主题
     *
     * @param dto
     * @return
     * @throws Exception
     * @author
     */
    @Override
    public TaParamDto saveSearch(TaParamDto dto) throws Exception {
        // 保存验证
        String msg = valSaveSearch(dto);
        if (ValidateUtil.isNotEmpty(msg)) {
            dto.append("b", false);
            dto.append("msg", msg);
            return dto;
        }
        BigDecimal yzb610 = dto.getAsBigDecimal("yzb610");
        if (ValidateUtil.isEmpty(yzb610)) {
            //新增
            Zb61Domain zb61Domain = new Zb61Domain();
            ReflectUtil.copyMapToObject(dto, zb61Domain, false);
            zb61Domain.setAae011(dto.getUser().getName());
            zb61Domain.setYae116(dto.getUser().getUserid());
            zb61Domain.setAae017(Long.valueOf(dto.getUser().getOrgId()));
            zb61Domain.setAae036(super.getSysTimestamp());
            String _yzb610 = super.getSequence("SEQ_YZB610");
            zb61Domain.setYzb610(new BigDecimal(_yzb610));
            //插入Tamenu
            String menuid = insertTaMenu(dto.getAsString("yzb611"),dto.getAsString("yzb612"),dto.getUser().getUserid());
            zb61Domain.setYzb618(menuid);
            //检查yzb615
            String yzb615 = zb61Domain.getYzb615();
            if(ValidateUtil.isNotEmpty(yzb615) && yzb615.contains("\"")){
                yzb615 = yzb615.replaceAll("\"","'");
            }
            zb61Domain.setYzb615(yzb615);
            //插入zb61
            dao.insert("zb61.insert", zb61Domain);
            dto.append("b", true);
            dto.append("msg", "新增成功！");
        } else {
            //更新
            dto.put("yzb670", dto.getAsLong("yzb670"));
            //检查yzb615
            String yzb615 = dto.getAsString("yzb615");
            if(ValidateUtil.isNotEmpty(yzb615) && yzb615.contains("\"")){
                yzb615 = yzb615.replaceAll("\"","'");
            }
            dto.put("yzb615",yzb615);
            int i = dao.update("zb61.updateAvailable", dto);
            if (i != 1) {
                throw new AppException("保存主题错误，请联系管理人员！");
            }
            //更改对应的菜单信息
            updateTamenu(dto.getAsString("yzb611"),dto.getAsString("yzb612"),dto.getUser().getUserid(),dto.getAsString("yzb618"));
            dto.append("b", true);
            dto.append("msg", "修改成功！");
        }
        return dto;
    }

    /**
     * 插入框架菜单表
     * @param yzb611 功能标识
     * @param yzb612 菜单名称
     * @param userId
     * @return menuid
     */
    private String insertTaMenu(String yzb611, String yzb612,Long userId) throws Exception{
        //从配置文件中获取挂靠的父菜单id
        String pmenuid = SysConfig.getSysConfig("menuid");
        if (ValidateUtil.isEmpty(pmenuid)) {
            //默认值
            pmenuid = "110532";
        }
        MenuVo menuVo = new MenuVo();
        //设置默认值
        menuVo.setMenuid(0L);
        menuVo.setPmenuid(Long.valueOf(pmenuid));
        menuVo.setMenuname(yzb612);
        menuVo.setUrl("query/customizeQueryAction.do?dto.ztdm="+yzb611);
        //挂靠的父集菜单信息
        MenuVo pmenu = this.menuMgBlo.getMenu(Long.valueOf(pmenuid));
        menuVo.setMenuidpath(pmenu.getMenuidpath());
        menuVo.setMenunamepath(pmenu.getMenunamepath());
        menuVo.setEffective("1");
        menuVo.setSecuritypolicy("1");
        menuVo.setIsdismultipos("1");
        menuVo.setMenulevel(pmenu.getMenulevel());
        menuVo.setMenutype("0");
        menuVo.setSyspath("sysmg");
        menuVo.setUseyab003("0");
        menuVo.setIsaudite("0");
        menuVo.setIsFiledsControl("0");
        MenuVo createMenu = this.menuMgBlo.createMenu(menuVo, "", userId);
        return String.valueOf(createMenu.getMenuid());
    }

    /**
     * 更改主题时 更改对应的菜单（只涉及到菜单名称 url）
     * @param yzb611
     * @param yzb612
     * @param userId
     * @param menuid
     * @throws Exception
     */
    private void updateTamenu(String yzb611, String yzb612,Long userId,String menuid) throws Exception {
        //兼容之前老数据 无菜单字段
        if(ValidateUtil.isNotEmpty(menuid)) {
            //菜单信息
            MenuVo menuVo = this.menuMgBlo.getMenu(Long.valueOf(menuid));
            menuVo.setMenuname(yzb612);
            menuVo.setUrl("query/customizeQueryAction.do?dto.ztdm="+yzb611);
            this.menuMgBlo.updateMenu(menuVo,"",userId);
        }
    }


    /**
     * 保存验证
     *
     * @param dto
     * @return 错误信息
     * @author
     */
    private String valSaveSearch(TaParamDto dto) {
        dto.put("yzb610", dto.getAsLong("yzb610"));
        // 验证代码
        Integer a = (Integer) getDao().queryForObject("zb61.valYzb611", dto);
        if (a.intValue() > 0) {
            return "主题代码已存在，请修改！";
        }

        // 验证名称
        Integer b = (Integer) getDao().queryForObject("zb61.valYzb612", dto);
        if (b.intValue() > 0) {
            return "主题名称已存在，请修改！";
        }

        dto.append("yzb613", dto.getAsString("yzb613").toUpperCase());
        return null;
    }

    /**
     * 删除主题
     *
     * @param lst
     * @return
     * @throws Exception
     * @author
     */
    @Override
    public TaParamDto removeSearch(List lst,TaParamDto dto) throws Exception {
        if (ValidateUtil.isNotEmpty(lst)) {
            Map key = null;
            for (int i = 0; i < lst.size(); i++) {
                key = (Map) lst.get(i);
                removeSearch(key,dto);
            }
        }
        TaParamDto d = new TaParamDto();
        d.append("b", true);
        d.append("msg", "删除成功！");
        return d;
    }

    /**
     * 删除主题
     *
     * @param key
     * @throws Exception
     * @author
     */
    private void removeSearch(Map key ,TaParamDto dto) throws Exception {
        int i = 0;
        key.put("yzb610", Integer.valueOf(String.valueOf(key.get("yzb610"))));
        List zb62lst = getDao().queryForList("zb62.getList", key);
        Zb62Domain zb62Domain = null;
        for (int j = 0; j < zb62lst.size(); j++) {
            zb62Domain = (Zb62Domain) zb62lst.get(j);
            //删除主题下的配制项目
            getSetSearchItemService().removeSearchItem(zb62Domain.getPK());
        }

        // 删除主题
        i = dao.delete("zb61.delete", key);
        if (i != 1) {
            throw new AppException("删除主题错误，请联系管理人员！");
        }
        //删除主题对应的菜单 兼容之前的老数据 无菜单id yzb618字段
        if(!ValidateUtil.isEmpty(key.get("yzb618"))){
            UserVo userVo = new UserVo();
            userVo.setLoginid(dto.getUser().getLoginid());
            UserAccountInfo userAccountInfo = new UserAccountInfo();
            userAccountInfo.setUser(userVo);
            menuMgBlo.removeMenu(Long.valueOf(key.get("yzb618")+""),userAccountInfo);
        }
    }

    @Override
    public PageBean querySearchs(TaParamDto dto) throws Exception {
        PageBean bean = dao.queryForPageWithCount("grid1", "zb61.queryList", dto, dto);
        return bean;
    }

    @Override
    public List queryDataSource(TaParamDto dto) throws Exception {
        return dao.queryForList("zb67.queryDataSource", dto);
    }
}

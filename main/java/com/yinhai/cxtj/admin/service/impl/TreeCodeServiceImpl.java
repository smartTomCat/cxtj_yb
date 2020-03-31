package com.yinhai.cxtj.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.yinhai.core.service.ta3.domain.service.TaBaseService;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.service.TreeCodeService;
import com.yinhai.sysframework.persistence.ibatis.IDao;


public class TreeCodeServiceImpl extends CxtjBaseServiceImpl implements TreeCodeService {
	@Override
	@Cacheable(cacheName="rsrcAppTreeCodeCache",keyGenerator= @KeyGenerator (
            name = "com.yinhai.rsgl.common.service.TreeCacheKeyGenerator"))
	public List getTreeDataByType(String type,String yzb670) throws Exception {
		IDao iDao = super.getDynamicDao(yzb670);
		Map map = new HashMap();
		map.put("type", type);
		List list = iDao.queryForList("apptreecode_query.getTreeList",map);
		return list;
	}

	@Override
	@Cacheable(cacheName="rsrcAppTreeCodeCache",keyGenerator= @KeyGenerator (
            name = "com.yinhai.rsgl.common.service.TreeCacheKeyGenerator"))
	public List getxzTreeByType() throws Exception {
		List list = dao.queryForList("apptreecode_query.getxzTreeList");
		return list;
	}
	@Override
	@Cacheable(cacheName="rsrcAppTreeCodeCache",keyGenerator= @KeyGenerator (
            name = "com.yinhai.rsgl.common.service.TreeCacheKeyGenerator"))
	public List getSxTreeData() throws Exception {
		// TODO Auto-generated method stub
		List list = dao.queryForList("apptreecode_query.getTreeSxList");
		return list;
	}
	
}

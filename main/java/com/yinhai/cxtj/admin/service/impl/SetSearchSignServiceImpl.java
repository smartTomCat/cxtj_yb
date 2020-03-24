package com.yinhai.cxtj.admin.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yinhai.core.common.api.context.ISysUser;
import com.yinhai.core.common.api.util.ReflectUtil;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.core.service.ta3.domain.service.TaBaseService;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.Zb63Domain;
import com.yinhai.cxtj.admin.service.SetSearchSignService;


/**
 * 查询统计主题项目支持的关系符
 * 
 * @author 
 */
public class SetSearchSignServiceImpl extends CxtjBaseServiceImpl implements SetSearchSignService {

	/**
	 * 查询支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map querySearchSign(BigDecimal yzb620) throws Exception {
		Map map = new HashMap();
		if (!ValidateUtil.isEmpty(yzb620)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb620", yzb620);
			List zb63lst = dao.queryForList("zb63.getList", d);
			Zb63Domain zb63Domain = null;
			for (int i = 0; i < zb63lst.size(); i++) {
				zb63Domain = (Zb63Domain) zb63lst.get(i);
				map.put("check_" + zb63Domain.getYzb631() + "_yzb631", zb63Domain.getYzb631());
			}
		}
		return map;
	}

	/**
	 * 保存支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @param yzb631
	 *            支持的关系符
	 * @param user
	 * @throws Exception
	 */
	@Override
	public void saveSearchSign(BigDecimal yzb620, String[] yzb631, ISysUser user) throws Exception {
		// 删除支持的关系符
		removeSearch(yzb620);
		// 保存支持的关系符
		for (int i = 0; i < yzb631.length; i++) {
			saveSearchSign(yzb620, yzb631[i], user);
		}
	}

	/**
	 * 删除支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	@Override
	public int removeSearch(BigDecimal yzb620) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb620", yzb620);
			return getDao().delete("zb63.removeSearch", d);
		}
		return 0;
	}

	/**
	 * 保存支持的关系符
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @param yzb631
	 *            支持的关系符
	 * @param user
	 * @throws Exception
	 */
	private void saveSearchSign(BigDecimal yzb620, String yzb631, ISysUser user) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620) && ValidateUtil.isNotEmpty(yzb631)) {
			TaParamDto d = new TaParamDto();
			d.setUser(user);
			d.append("yzb620", yzb620);
			d.append("yzb631", yzb631);
			
			Zb63Domain zb63Domain = new Zb63Domain();
			ReflectUtil.copyMapToObject(d, zb63Domain, false);
			String _yzb630 = super.getSequence("SEQ_YZB630");
			zb63Domain.setYzb630(new BigDecimal(_yzb630));
			dao.insert("zb63.insert", zb63Domain);
		}
	}

}

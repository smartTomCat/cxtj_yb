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
import com.yinhai.cxtj.admin.domain.Zb64Domain;
import com.yinhai.cxtj.admin.service.SetSearchModeService;


/**
 * 查询统计主题项目支持的计算方式
 * 
 * @author 
 */
public class SetSearchModeServiceImpl extends CxtjBaseServiceImpl implements SetSearchModeService {

	/**
	 * 查询支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map querySearchMode(BigDecimal yzb620) throws Exception {
		Map map = new HashMap();
		if (!ValidateUtil.isEmpty(yzb620)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb620", yzb620);
			List zb64lst = dao.queryForList("zb64.getList", d);;
			Zb64Domain zb64Domain = null;
			for (int i = 0; i < zb64lst.size(); i++) {
				zb64Domain = (Zb64Domain) zb64lst.get(i);
				map.put("check_" + zb64Domain.getYzb641() + "_yzb641all", zb64Domain.getYzb641());
			}
		}
		return map;
	}

	/**
	 * 保存支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @param yzb641
	 *            支持的计算方式
	 * @param user
	 * @throws Exception
	 */
	@Override
	public void saveSearchMode(BigDecimal yzb620, String[] yzb641, ISysUser user) throws Exception {
		// 删除支持的计算方式
		removeSearch(yzb620);
		// 保存支持的计算方式
		for (int i = 0; i < yzb641.length; i++) {
			saveSearchSign(yzb620, yzb641[i], user);
		}
	}

	/**
	 * 删除支持的计算方式
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
			return getDao().delete("zb64.removeSearch", d);
		}
		return 0;
	}

	/**
	 * 保存支持的计算方式
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @param yzb641
	 *            支持的计算方式
	 * @param user
	 * @throws Exception
	 */
	private void saveSearchSign(BigDecimal yzb620, String yzb641, ISysUser user) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620) && ValidateUtil.isNotEmpty(yzb641)) {
			TaParamDto d = new TaParamDto();
			d.setUser(user);
			d.append("yzb620", yzb620);
			d.append("yzb641", yzb641);
			Zb64Domain zb64Domain = new Zb64Domain();
			ReflectUtil.copyMapToObject(d, zb64Domain, false);
			
			String _yzb640 = super.getSequence("SEQ_YZB640");
			zb64Domain.setYzb640(new BigDecimal(_yzb640));
			dao.insert("zb64.insert", zb64Domain);
		}
	}

}

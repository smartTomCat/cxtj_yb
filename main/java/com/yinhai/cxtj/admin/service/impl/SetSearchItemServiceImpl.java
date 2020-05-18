package com.yinhai.cxtj.admin.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yinhai.core.common.api.context.ISysUser;
import com.yinhai.core.common.api.exception.AppException;
import com.yinhai.core.common.api.util.ReflectUtil;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.core.service.ta3.domain.service.TaBaseService;
import com.yinhai.cxtj.admin.Constants;
import com.yinhai.cxtj.admin.base.service.impl.CxtjBaseServiceImpl;
import com.yinhai.cxtj.admin.domain.Zb62Domain;
import com.yinhai.cxtj.admin.domain.Zb89Domain;
import com.yinhai.cxtj.admin.service.SetSearchItemService;
import com.yinhai.cxtj.admin.service.SetSearchModeService;
import com.yinhai.cxtj.admin.service.SetSearchOrderService;
import com.yinhai.cxtj.admin.service.SetSearchSignService;
import com.yinhai.sysframework.persistence.ibatis.IDao;


/**
 * 查询统计主题的项目
 * 
 * @author 
 */
public class SetSearchItemServiceImpl extends CxtjBaseServiceImpl implements SetSearchItemService {

	public SetSearchSignService getSetSearchSignService() {
		return setSearchSignService;
	}

	public void setSetSearchSignService(SetSearchSignService setSearchSignService) {
		this.setSearchSignService = setSearchSignService;
	}

	public SetSearchModeService getSetSearchModeService() {
		return setSearchModeService;
	}

	public void setSetSearchModeService(SetSearchModeService setSearchModeService) {
		this.setSearchModeService = setSearchModeService;
	}

	public SetSearchOrderService getSetSearchOrderService() {
		return setSearchOrderService;
	}

	public void setSetSearchOrderService(SetSearchOrderService setSearchOrderService) {
		this.setSearchOrderService = setSearchOrderService;
	}

	private SetSearchSignService setSearchSignService;

	private SetSearchModeService setSearchModeService;

	private SetSearchOrderService setSearchOrderService;

	/**
	 * 查询配制项目
	 * 
	 * @author 
	 * @param yzb620
	 *            查询统计项目流水号
	 * @return
	 * @throws Exception
	 */
	@Override
	public Zb62Domain getDomainObjectById(BigDecimal yzb620) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620)) {
//			Key key = new Key();
			Map key = new HashMap();
			key.put("yzb620", yzb620);
			return (Zb62Domain) dao.queryForObject("zb62.get", key);
		}
		return null;
	}

	/**
	 * 查询配制项目
	 * 
	 * @author 
	 * @param yzb610
	 *            查询统计主题流水号
	 * @return
	 * @throws Exception
	 */
	@Override
	public List querySearchItems(BigDecimal yzb610) throws Exception {
		if (!ValidateUtil.isEmpty(yzb610)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb610", yzb610);
			d.append("order", "order");
			return dao.queryForList("zb62.getList", d);
		}
		return null;
	}

	/**
	 * 保存批量配制项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto saveSearchItems(TaParamDto dto) throws Exception {
		BigDecimal yzb610 = dto.getAsBigDecimal("yzb610");
		List<Map> sellst = (List) dto.get("sellst");
		String yzb617 = dto.getAsString("yzb617");
		Map key = null;
		for (int i = 0; i < sellst.size(); i++) {
			key = sellst.get(i);
			saveSearchItems(yzb610, key, dto.getUser(),yzb617);
		}

		dto.append("b", true);
		dto.append("msg", "保存成功！");
		return dto;
	}

	/**
	 * 保存配制项目
	 * 
	 * @author 
	 * @param yzb610 主题流水号
	 * @param key
	 * @param user
	 * @throws Exception
	 */
	private void saveSearchItems(BigDecimal yzb610, Map key, ISysUser user,String yzb617) throws Exception {
		TaParamDto dto = new TaParamDto();
		dto.setUser(user);
		dto.append("yzb610", yzb610); // 查询统计主题流水号
		dto.append("yzb623", key.get("yzb623")); // 数据库字段或表达式
		dto.append("yzb624", key.get("yzb623")); // 数据库字段AS别名
		dto.append("yzb625", key.get("yzb625")); // 数据库字段中文
		dto.append("yzb62d", "1"); // 是否作为查询条件（1是0否）
		dto.append("yzb62f", "1"); // 查询列控制（0不能查询1默认查询2默认不查询）
		dto.append("aae100", "1"); // 有效标志（1有效0无效）
		// 排序号
//		Integer yzb621 = (Integer) getDao().queryForObject("zb62.getNextYzb621", dto);
//		dto.append("yzb621", yzb621.intValue());
		//涉及到分组聚合函数 各数据库的处理方式不同
		String dsType = getDsType(Constants.DEFAULT_DS_NO);
		Integer yzb621;
		if(com.yinhai.cxtj.front.Constants.DSTYPE_ORACLE.equals(dsType) ){
			yzb621 = (Integer)dao.queryForObject("zb62.getNextYzb621ByOracle",dto);
		}else if(com.yinhai.cxtj.front.Constants.DSTYPE_MYSQL.equals(dsType)) {
			yzb621 =  (Integer)dao.queryForObject("zb62.getNextYzb621ByMysql",dto);
		}else if(com.yinhai.cxtj.front.Constants.DSTYPE_POSTGRESQL.equals(dsType)){
			yzb621 =  (Integer)dao.queryForObject("zb62.getNextYzb621ByPG",dto);
		}else if(com.yinhai.cxtj.front.Constants.DSTYPE_GBASE8A.equals(dsType)){
			yzb621 =  (Integer)dao.queryForObject("zb62.getNextYzb621By8A",dto);
		}else {
			yzb621 = 1;
		}
		dto.append("yzb621", yzb621);
		// 数据类型（1字符型2数字型3日期型）
		String yzb626 = (String)key.get("yzb626");
		dto.append("yzb626", yzb626);

		// 代码类别
		String yzb628 = (String)key.get("yzb628");
		dto.append("yzb628", yzb628);

		// 查询条件的展现形式（11文本12年月13日期14数字21代码值平铺22树23DATAGRID中选择）
		// 分组控制（0不用于分组1默认选中2默认未选中）
		// 分组计算控制（0不用于计算1默认计算2默认不计算）
		// 默认统计方式（1计数2去重后计数3求和4求平均5最大值6最小值）
		if ("2".equals(yzb626)) {
			dto.append("yzb62a", "14");
			dto.append("yzb62b", "2");
			dto.append("yzb62c", "2");
			dto.append("yzb641", "3");
		} else if ("3".equals(yzb626)) {
			dto.append("yzb62a", "13");
			dto.append("yzb62b", "0");
			dto.append("yzb62c", "0");
		} else {
			if (ValidateUtil.isNotEmpty(yzb628)) {
				dto.append("yzb62a", "21");
				dto.append("yzb62b", "2");
				dto.append("yzb62c", "2");
				dto.append("yzb641", "1");
			} else {
				dto.append("yzb62a", "11");
				dto.append("yzb62b", "0");
				dto.append("yzb62c", "0");
			}
		}

		//对于配置的查询主题  设置默认不计算不分组 不设置默认统计方式
		if("1".equals(yzb617)){
			dto.append("yzb62b", "0");
			dto.append("yzb62c", "0");
			dto.remove("yzb641");
		}

		Zb62Domain zb62Domain = new Zb62Domain();
		ReflectUtil.copyMapToObject(dto, zb62Domain, false);
		// 新增
		//createDomainObject(dto);
		
		String _yzb620 = super.getSequence("SEQ_YZB620");
		zb62Domain.setYzb620(new BigDecimal(_yzb620));
		//change by zhaohs
		dto.append("yzb620",_yzb620);
		
		dao.insert("zb62.insert", zb62Domain);
		
		String[] yzb631 = null; // 支持的关系（11等于12不等于21大于22大于等于31小于32小于等于41包含42不包含）
		String[] yzb641all = null; // 查询结果的计算方式（1计数2去重后计数3求和4求平均5最大值6最小值）
		if ("2".equals(yzb626)) {
			yzb631 = new String[] { "11", "12", "21", "22", "31", "32" };
			yzb641all = new String[] { "1", "3", "4", "5", "6" };
		} else if ("3".equals(yzb626)) {
			yzb631 = new String[] { "11", "12", "21", "22", "31", "32" };
			yzb641all = new String[] { "5", "6" };
		} else {
			yzb631 = new String[] { "11", "12", "41", "42" };
			yzb641all = new String[] { "1", "2" };
		}
		// 新增支持的关系符
		getSetSearchSignService().saveSearchSign(dto.getAsBigDecimal("yzb620"), yzb631, user);
		// 新增支持的计算方式
		getSetSearchModeService().saveSearchMode(dto.getAsBigDecimal("yzb620"), yzb641all, user);
	}

	/**
	 * 查询获取项目或表达式选择
	 * 
	 * @author 
	 * @param d
	 * @return
	 * @throws Exception
	 */
	@Override
	public List querySearchItemSelect(TaParamDto d) throws Exception {
		if(ValidateUtil.isNotEmpty(d.getAsString("yzb670"))){
			IDao dynamicDao= super.getDynamicDao(d.getAsString("yzb670"));
			if (!ValidateUtil.isEmpty(d.getAsString("yzb613"))) {
				String dsType= super.getDsType(d.getAsString("yzb670"));
				if(com.yinhai.cxtj.front.Constants.DSTYPE_ORACLE.equals(dsType)){
					return dynamicDao.queryForList("zb62.getOracleSearchItemSelect", d);
				}else if(com.yinhai.cxtj.front.Constants.DSTYPE_MYSQL.equals(dsType)){
					return dynamicDao.queryForList("zb62.getMysqlSearchItemSelect", d);
				}else if(com.yinhai.cxtj.front.Constants.DSTYPE_POSTGRESQL.equals(dsType)){
					return dynamicDao.queryForList("zb62.getPGSearchItemSelect", d);
				}else if(com.yinhai.cxtj.front.Constants.DSTYPE_GBASE8A.equals(dsType)){
					return dynamicDao.queryForList("zb62.get8ASearchItemSelect", d);
				}else{
					return dynamicDao.queryForList("zb62.getOracleSearchItemSelect", d);
				}
			}
		}
		return null;
	}


	@Override
	public List querySearchItemInResultSet(TaParamDto dto) throws Exception {
		if (ValidateUtil.isNotEmpty(dto.getAsString("yzb690"))) {
            List<Zb89Domain> list = dao.queryForList("zb89.querySearchItemInResultSet",dto);
			return list;
		}
		return null;
	}

	/**
	 * 查询配制项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto querySearchItem(TaParamDto dto) throws Exception {
		BigDecimal yzb620 = dto.getAsBigDecimal("yzb620");
		dto.put("yzb610",dto.getAsBigDecimal("yzb610"));
		if (ValidateUtil.isEmpty(yzb620)) { // 新增
			//涉及到分组聚合函数 各数据库的处理方式不同
			String dsType = getDsType(Constants.DEFAULT_DS_NO);
			Integer yzb621;
			if(com.yinhai.cxtj.front.Constants.DSTYPE_ORACLE.equals(dsType) ){
				yzb621 = (Integer)dao.queryForObject("zb62.getNextYzb621ByOracle",dto);
			}else if(com.yinhai.cxtj.front.Constants.DSTYPE_MYSQL.equals(dsType)) {
				yzb621 =  (Integer)dao.queryForObject("zb62.getNextYzb621ByMysql",dto);
			}else if(com.yinhai.cxtj.front.Constants.DSTYPE_POSTGRESQL.equals(dsType)){
				yzb621 =  (Integer)dao.queryForObject("zb62.getNextYzb621ByPG",dto);
			}else if(com.yinhai.cxtj.front.Constants.DSTYPE_GBASE8A.equals(dsType)){
				yzb621 =  (Integer)dao.queryForObject("zb62.getNextYzb621By8A",dto);
			}else {
				yzb621 = 1;
			}
			dto.append("yzb621", yzb621);
			dto.append("aae100", "1"); // 有效标志【有效】
			dto.append("yzb62f", "1"); // 查询列控制【默认查询】
			dto.append("yzb62d", "1"); // 是否作为查询条件【是】
			dto.append("yzb62a", "11"); // 查询条件的的展现形式【文本】
			dto.append("yzb62b", "1"); // 分组控制【默认选中】
			dto.append("yzb62c", "1"); // 分组计算控制【默认计算】
			dto.append("yzb641", "1"); // 默认统计方式【计数】
		} else { // 编辑
			Zb62Domain zb62Domain = getDomainObjectById(yzb620);
			if (!ValidateUtil.isEmpty(zb62Domain)) {
				dto.clear();
				dto.putAll(zb62Domain.toMap());
				dto.putAll(getSetSearchModeService().querySearchMode(yzb620));
				dto.putAll(getSetSearchSignService().querySearchSign(yzb620));
			}
		}
		//配置的查询主题  设置默认不计算 默认不分组
		if("1".equals(dto.getAsString("yzb617"))){
			dto.append("yzb62b", "0"); // 分组控制【默认选中】
			dto.append("yzb62c", "0"); // 分组计算控制【默认计算】
			dto.remove("yzb641"); // 默认统计方式【计数】
		}


		return dto;
	}

	/**
	 * 保存配制项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto saveSearchItem(TaParamDto dto) throws Exception {

		// 保存验证
		String msg = valSearchItem(dto);
		if (ValidateUtil.isNotEmpty(msg)) {
			dto.append("b", false);
			dto.append("msg", msg);
			return dto;
		}

		BigDecimal yzb620 = dto.getAsBigDecimal("yzb620");
		if (ValidateUtil.isEmpty(yzb620)) { // 新增
			
			Zb62Domain zb62Domain = new Zb62Domain();
			ReflectUtil.copyMapToObject(dto, zb62Domain, false);
			
			String _yzb620 = super.getSequence("SEQ_YZB620");
			zb62Domain.setYzb620(new BigDecimal(_yzb620));
			dto.put("yzb620",new BigDecimal(_yzb620));
			dao.insert("zb62.insert", zb62Domain);
			
			dto.append("b", true);
			dto.append("msg", "新增成功！");
		} else { // 更新
			//int i = updateDomainObjectAvailable(dto);
			int i = dao.update("zb62.updateAvailable", dto);
			if (i != 1) {
				throw new AppException("保存流程步骤错误，请联系管理人员！");
			}
			dto.append("b", true);
			dto.append("msg", "修改成功！");
		}

		// 保存支持的关系符
		getSetSearchSignService().saveSearchSign(dto.getAsBigDecimal("yzb620"), dto.getAsStringArray("yzb631"), dto.getUser());
		// 保存支持的计算方式
		getSetSearchModeService().saveSearchMode(dto.getAsBigDecimal("yzb620"), dto.getAsStringArray("yzb641all"), dto.getUser());

		return dto;
	}

	/**
	 * 保存验证
	 * 
	 * @author 
	 * @param dto
	 * @return 错误信息
	 */
	private String valSearchItem(TaParamDto dto) {
		dto.put("yzb621",dto.getAsInteger("yzb621"));
		dto.put("yzb620",dto.getAsBigDecimal("yzb620"));
		dto.put("yzb610",dto.getAsBigDecimal("yzb610"));
		// 验证序号
		Integer a = (Integer) getDao().queryForObject("zb62.valYzb621", dto);
		if (a.intValue() > 0) {
			return "功能序号已存在，请修改！";
		}

		// 是否作为查询条件
		if ("1".equals(dto.getAsString("yzb62d"))) {
			if (ValidateUtil.isEmpty(dto.getAsString("yzb62a"))) {
				return "查询条件的展现形式不能为空，请修改！";
			}
			if (ValidateUtil.isEmpty(dto.getAsStringArray("yzb631"))) {
				return "支持的关系符不能为空，请修改！";
			}
		} else {
			dto.append("yzb62d", "0");
			dto.append("yzb62a", null);
		}
		
		// 分组控制
		if (!"1".equals(dto.getAsString("yzb62b")) && !"2".equals(dto.getAsString("yzb62b"))) {
			dto.append("yzb62b", "0");
		}

		// 分组计算控制
		if ("1".equals(dto.getAsString("yzb62c")) || "2".equals(dto.getAsString("yzb62c"))) {
			if (ValidateUtil.isEmpty(dto.getAsString("yzb641"))) {
				return "默认统计方式不能为空，请修改！";
			}
			if (ValidateUtil.isEmpty(dto.getAsStringArray("yzb641all"))) {
				return "支持的计算方式不能为空，请修改！";
			}
		} else {
			dto.append("yzb62c", "0");
			dto.append("yzb641", null);
		}

		return null;
	}

	/**
	 * 删除配制项目
	 * 
	 * @author 
	 * @param lst
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto removeSearchItem(List lst) throws Exception {
		TaParamDto dto = new TaParamDto();

		Map key = null;
		for (int i = 0; i < lst.size(); i++) {
			key = (Map) lst.get(i);
			removeSearchItem(key);
		}

		dto.append("b", true);
		dto.append("msg", "删除成功！");
		return dto;
	}

	/**
	 * 删除配制项目
	 * 
	 * @author 
	 * @param key
	 * @throws Exception
	 */
	@Override
	public void removeSearchItem(Map key) throws Exception {
		// 删除支持的计算方式
		getSetSearchModeService().removeSearch(new BigDecimal(key.get("yzb620").toString()));
		// 删除支持的关系符
		getSetSearchSignService().removeSearch(new BigDecimal(key.get("yzb620").toString()));
		// 删除配制项目排序
		getSetSearchOrderService().removeSearch(new BigDecimal(key.get("yzb620").toString()));
		// 删除配制项目
		key.put("yzb620",new BigDecimal(String.valueOf(key.get("yzb620"))));
		int i = dao.delete("zb62.delete", key);
		if (i != 1) {
			throw new AppException("配制项目删除错误，请联系管理人员！");
		}
	}

	@Override
	public List<Map> queryExistCols(TaParamDto dto) throws Exception {
		return dao.queryForList("zb62.queryExistCols",dto);
	}
}

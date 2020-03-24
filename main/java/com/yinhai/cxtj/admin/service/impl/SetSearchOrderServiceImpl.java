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
import com.yinhai.cxtj.admin.domain.Zb65Domain;
import com.yinhai.cxtj.admin.service.SetSearchOrderService;


/**
 * 查询统计主题缺省排序项目
 * 
 * @author 
 */
public class SetSearchOrderServiceImpl extends CxtjBaseServiceImpl implements SetSearchOrderService {

	/**
	 * 查询配制项目排序
	 * 
	 * @author 
	 * @param yzb610
	 *            查询统计主题流水号
	 * @return
	 * @throws Exception
	 */
	@Override
	public List querySearchOrders(BigDecimal yzb610) throws Exception {
		if (!ValidateUtil.isEmpty(yzb610)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb610", yzb610);
			d.append("order", "order");
			return dao.queryForList("zb65.getList", d);
		}
		return null;
	}

	/**
	 * 查询待选项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public List query1(TaParamDto dto) throws Exception {
		dto.put("yzb610",dto.getAsBigDecimal("yzb610"));
		return getDao().queryForList("zb65.query1", dto);
	}

	/**
	 * 查询已选项目
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public List query2(TaParamDto dto) throws Exception {
		dto.put("yzb610",dto.getAsBigDecimal("yzb610"));
		return getDao().queryForList("zb65.query2", dto);
	}

	/**
	 * 选择待选项目
	 * 
	 * @author 
	 * @param lst
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto select1(List lst, TaParamDto dto) throws Exception {
		BigDecimal yzb610 = dto.getAsBigDecimal("yzb610");
		if (!ValidateUtil.isEmpty(yzb610)) {
//			Key key = null;
			Map key = null;
			for (int i = 0; i < lst.size(); i++) {
				key = (Map) lst.get(i);
				createSearchOrder(yzb610, new BigDecimal(key.get("yzb620").toString()), dto.getUser());
			}
		}

		dto.append("b", true);
		dto.append("msg", "保存成功！");
		return dto;
	}

	/**
	 * 选择已选项目
	 * 
	 * @author 
	 * @param lst
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto select2(List lst, TaParamDto dto) throws Exception {
		BigDecimal yzb610 = dto.getAsBigDecimal("yzb610");
		if (!ValidateUtil.isEmpty(yzb610)) {
			removeSearchOrder(lst);
			refAllYzb651(yzb610);
		}

		dto.append("b", true);
		dto.append("msg", "删除成功！");
		return dto;
	}

	/**
	 * 刷新排序号
	 * 
	 * @author 
	 * @param yzb610
	 *            查询统计主题流水号
	 * @throws Exception
	 */
	private void refAllYzb651(BigDecimal yzb610) throws Exception {
		List zb65lst = querySearchOrders(yzb610);

		int yzb651 = 0;
		Zb65Domain zb65Domain = null;
		for (int i = 0; i < zb65lst.size(); i++) {
			yzb651++;
			zb65Domain = (Zb65Domain) zb65lst.get(i);
			refYzb651(zb65Domain, yzb651);
		}
	}

	/**
	 * 更新序号
	 * 
	 * @author 
	 * @param zb65Domain
	 * @param yzb651
	 * @throws Exception
	 */
	private void refYzb651(Zb65Domain zb65Domain, int yzb651) throws Exception {
		if (!ValidateUtil.isEmpty(zb65Domain) && !ValidateUtil.isEmpty(zb65Domain.getYzb651()) && zb65Domain.getYzb651().intValue() != yzb651) {
			TaParamDto d = new TaParamDto();
			d.append("yzb650", zb65Domain.getYzb650());
			d.append("yzb651", yzb651);
			int i = dao.update("zb65.updateAvailable", d);;
			if (i != 1) {
				throw new AppException("排序方式设置错误，请联系管理人员！");
			}
		}
	}

	/**
	 * 新增配制项目排序
	 * 
	 * @author 
	 * @param yzb610
	 * @param yzb620
	 * @param user
	 * @throws Exception
	 */
	private void createSearchOrder(BigDecimal yzb610, BigDecimal yzb620, ISysUser user) throws Exception {
		if (!ValidateUtil.isEmpty(yzb620) && !ValidateUtil.isEmpty(user)) {
			TaParamDto d = new TaParamDto();
			d.setUser(user);
			d.append("yzb610", yzb610);
			//涉及到分组聚合函数 各数据库的处理方式不同
			String dsType = getDsType(Constants.DEFAULT_DS_NO);
			Integer yzb651;
			if(com.yinhai.cxtj.front.Constants.DSTYPE_ORACLE.equals(dsType) ){
				yzb651 = (Integer)dao.queryForObject("zb65.getNextYzb651ByOracle",d);
			}else if(com.yinhai.cxtj.front.Constants.DSTYPE_MYSQL.equals(dsType)) {
				yzb651 =  (Integer)dao.queryForObject("zb65.getNextYzb651ByMysql",d);
			}else if(com.yinhai.cxtj.front.Constants.DSTYPE_POSTGRESQL.equals(dsType)){
				yzb651 =  (Integer)dao.queryForObject("zb65.getNextYzb651ByPG",d);
			}else if(com.yinhai.cxtj.front.Constants.DSTYPE_GBASE8A.equals(dsType)){
				yzb651 =  (Integer)dao.queryForObject("zb65.getNextYzb651By8A",d);
			}else {
				yzb651 = 1;
			}
			d.append("yzb651",yzb651);

			d.append("yzb620", yzb620);
			d.append("yzb652", "1");
			
			Zb65Domain zb65Domain = new Zb65Domain();
			ReflectUtil.copyMapToObject(d, zb65Domain, false);
			
			String _yzb650 = super.getSequence("SEQ_YZB650");
			zb65Domain.setYzb650(new BigDecimal(_yzb650));
			
			dao.insert("zb65.insert", zb65Domain);
		}
	}

	/**
	 * 删除配制项目排序
	 * 
	 * @author 
	 * @param lst
	 * @throws Exception
	 */
	private void removeSearchOrder(List lst) throws Exception {
		int success = 0;
		if (null != lst) {
			for (Map key : (List<Map>)lst) {
				if(!ValidateUtil.isEmpty(key.get("yzb650"))){
					key.put("yzb650",new BigDecimal(String.valueOf(key.get("yzb650"))));
				}
				dao.delete("zb65.delete", key);
				++success;
			}
			if (success != lst.size()) {
				throw new AppException("更新数据失败，请联系维护人员！");
			}
		}

	}

	/**
	 * 设置排序方式
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto saveYzb652Radio(TaParamDto dto) throws Exception {
		BigDecimal yzb650 = dto.getAsBigDecimal("yzb650");
		String yzb652 = dto.getAsString("yzb652");
		if (!ValidateUtil.isEmpty(yzb650) && ValidateUtil.isNotEmpty(yzb652)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb650", yzb650);
			d.append("yzb652", yzb652);
			int i = dao.update("zb65.updateAvailable", d);
			if (i != 1) {
				throw new AppException("排序方式设置错误，请联系管理人员！");
			}
		}
		return dto;
	}

	/**
	 * 设置排序
	 * 
	 * @author 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public TaParamDto saveYzb651updown(TaParamDto dto) throws Exception {
		BigDecimal yzb610 = dto.getAsBigDecimal("yzb610");
		BigDecimal yzb650 = dto.getAsBigDecimal("yzb650");
		String isup = dto.getAsString("isup");
		if (!ValidateUtil.isEmpty(yzb650) && !ValidateUtil.isEmpty(yzb610)) {
			Zb65Domain zb65Domain1 = null;
			Zb65Domain zb65Domain2 = null;
			// 移动项
//			Key key = new Key();
			Map key = new HashMap();
			key.put("yzb650", yzb650);
			zb65Domain1 = (Zb65Domain) dao.queryForObject("zb65.get", key);
			// 被移动项
			TaParamDto d = new TaParamDto();
			d.append("yzb610", yzb610);
			d.append("yzb650", yzb650);
			if ("1".equals(isup)) { // 上移
				List zb65lst = getDao().queryForList("zb65.getYzb651up", d);
				if (ValidateUtil.isNotEmpty(zb65lst)) {
					zb65Domain2 = (Zb65Domain) zb65lst.get(0);
				}
			} else { // 下移
				List zb65lst = getDao().queryForList("zb65.getYzb651down", d);
				if (ValidateUtil.isNotEmpty(zb65lst)) {
					zb65Domain2 = (Zb65Domain) zb65lst.get(0);
				}
			}

			saveYzb651updown(zb65Domain1, zb65Domain2);
		}
		return dto;
	}

	/**
	 * 设置排序
	 * 
	 * @author 
	 * @param zb65Domain1
	 *            移动项
	 * @param zb65Domain2
	 *            被移动项
	 * @throws Exception
	 */
	private void saveYzb651updown(Zb65Domain zb65Domain1, Zb65Domain zb65Domain2) throws Exception {
		if (!ValidateUtil.isEmpty(zb65Domain1) && !ValidateUtil.isEmpty(zb65Domain2)) {
			TaParamDto d = new TaParamDto();
			d.append("yzb650", zb65Domain1.getYzb650());
			d.append("yzb651", zb65Domain2.getYzb651());
			int i = dao.update("zb65.updateAvailable", d);;
			if (i != 1) {
				throw new AppException("排序方式设置错误，请联系管理人员！");
			}

			d.append("yzb650", zb65Domain2.getYzb650());
			d.append("yzb651", zb65Domain1.getYzb651());
			i = dao.update("zb65.updateAvailable", d);
			if (i != 1) {
				throw new AppException("排序方式设置错误，请联系管理人员！");
			}
		}
	}

	/**
	 * 删除配制项目排序
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
			return getDao().delete("zb65.removeSearch", d);
		}
		return 0;
	}

}

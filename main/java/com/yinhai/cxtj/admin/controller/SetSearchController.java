package com.yinhai.cxtj.admin.controller;

import com.yinhai.core.app.api.util.JSonFactory;
import com.yinhai.core.app.ta3.web.controller.BaseController;
import com.yinhai.core.common.api.util.ValidateUtil;
import com.yinhai.core.common.ta3.dto.TaParamDto;
import com.yinhai.cxtj.admin.Constants;
import com.yinhai.cxtj.admin.base.controller.CxtjBaseController;
import com.yinhai.cxtj.admin.domain.Zb61Domain;
import com.yinhai.cxtj.admin.service.SetSearchItemService;
import com.yinhai.cxtj.admin.service.SetSearchOrderService;
import com.yinhai.cxtj.admin.service.SetSearchService;
import com.yinhai.cxtj.admin.util.DynamicDataLoad;
import com.yinhai.sysframework.persistence.PageBean;
import com.yinhai.sysframework.persistence.ibatis.IDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * 查询统计主题定义
 * 
 * @author
 */
@Controller
@RequestMapping("/admin/setSearch")
public class SetSearchController extends CxtjBaseController {



	@Resource
	SetSearchService setSearchService;

	@RequestMapping("setSearchAction.do")
	public String execute() throws Exception {
		return "cxtj/admin/setsearch/setSearchMain";
	}

	/**
	 * 查询主题
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!querySearchs.do")
	public String querySearchs() throws Exception {
		TaParamDto dto = getTaDto();
		PageBean bean = setSearchService.querySearchs(dto);
		setList("grid1", bean);
		return JSON;
	}

	/**
	 * 主题定义页面
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!toEditSearch.do")
	public String toEditSearch() throws Exception {
		TaParamDto dto = getTaDto();
		//查询数据源 并赋值下拉选
		List list = setSearchService.queryDataSource(dto);
		//加入框架启动数据源可供选择
		Map m = new HashMap<>();
		m.put("id", Constants.DEFAULT_DS_NO);
		m.put("name","框架数据源");
		list.add(0,m);
		setSelectInputList("yzb670",list);
		Zb61Domain zb61Domain = (Zb61Domain) setSearchService.getDomainObjectById(dto.getAsBigDecimal("yzb610"));
		if (!ValidateUtil.isEmpty(zb61Domain)) {
			//ta 前端组件textarea 的文本显示内容不能带'' \n 符号， 在此将sql中的'' 批量替换为 ""
			String yzb615 = zb61Domain.getYzb615();
			if (ValidateUtil.isNotEmpty(yzb615) && yzb615.contains("'")) {
				yzb615 = yzb615.replaceAll("'", "\"");

			}
			if (ValidateUtil.isNotEmpty(yzb615) && yzb615.contains("\n")) {
				yzb615 = yzb615.replaceAll("\n","  ");
			}
			zb61Domain.setYzb615(yzb615);
			setData(zb61Domain.toMap(), false);
		}
		return "cxtj/admin/setsearch/setSearchEdit";
	}

	/**
	 * 保存主题
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!saveSearch.do")
	public String saveSearch() throws Exception {
		TaParamDto d = setSearchService.saveSearch(getTaDto());

		setData(d, false);
		
		String msg = d.getAsString("msg");
		if (ValidateUtil.isNotEmpty(msg)) {
			boolean b = (Boolean) d.get("b");
			if (b) {
				setMessage(msg, "success");
				setSuccess(true);
			} else {
				setMessage(msg, "error");
				setSuccess(false);
			}
		}
		
		return JSON;
	}

	/**
	 * 删除主题
	 *
	 * @author
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!removeSearch.do")
	public String removeSearch() throws Exception {
		TaParamDto dto = getTaDto();
		List lst = getGridSelected("grid1");
		TaParamDto d = setSearchService.removeSearch(lst,dto);

		String msg = d.getAsString("msg");
		if (ValidateUtil.isNotEmpty(msg)) {
			boolean b = (Boolean) d.get("b");
			if (b) {
				setMessage(msg, "success");
				setSuccess(true);
			} else {
				setMessage(msg, "error");
				setSuccess(false);
			}
		}
		return JSON;
	}

	/**
	 * 显示当前主题
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!getLabel.do")
	public String getLabel() throws Exception {
		Zb61Domain zb61Domain = setSearchService.getDomainObjectById(getTaDto().getAsBigDecimal("selyzb610"));
		if (!ValidateUtil.isEmpty(zb61Domain)) {
			setData("sellabel", zb61Domain.getYzb612() + ":");
		}
		return JSON;
	}

	@Resource
	SetSearchItemService setSearchItemService;

	/**
	 * 查询配制项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!querySearchItem.do")
	public String querySearchItem() throws Exception {
		List zb62lst = setSearchItemService.querySearchItems(getTaDto().getAsBigDecimal("yzb610"));
		if (ValidateUtil.isNotEmpty(zb62lst)) {
			setList("grid2", zb62lst);
		}
		return JSON;
	}

	/**
	 * 批量配制项目页面
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!toEditSearchItems.do")
	public String toEditSearchItems() throws Exception {
		setData(getTaDto(), false);
		return "cxtj/admin/setsearch/setSearchItemsEdit";
	}

	/**
	 * 保存批量配制项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!saveSearchItems.do")
	public String saveSearchItems() throws Exception {
		TaParamDto dto = getTaDto();
		dto.append("sellst", getGridSelected("grid1"));

		TaParamDto d = setSearchItemService.saveSearchItems(dto);
		boolean b = (Boolean) d.get("b");
		String msg = d.getAsString("msg");
		setData("msg", msg);
		setSuccess(b);
		return JSON;
	}

	/**
	 * 配制项目页面
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!toEditSearchItem.do")
	public String toEditSearchItem() throws Exception {
		setData(getTaDto(), false);
		if("1".equals(getTaDto().getAsString("yzb617"))){
			setHide("box_count");
		}
		return "cxtj/admin/setsearch/setSearchItemEdit";
	}

	/**
	 * 项目或表达式选择页面
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!toEditSearchItemSelect.do")
	public String toEditSearchItemSelect() throws Exception {
		setData(getTaDto(), false);
		return "cxtj/admin/setsearch/setSearchItemSelect";
	}


	/**
	 * 获取配制项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!getEditSearchItemSelect.do")
	public String getEditSearchItemSelect() throws Exception {
		String yzb690 = getTaDto().getAsString("yzb690");
		List list;
		//数据集
		if (ValidateUtil.isNotEmpty(yzb690)) {
			list = setSearchItemService.querySearchItemInResultSet(getTaDto().getAsString("yzb670"),yzb690);
		}else{
			list = setSearchItemService.querySearchItemSelect(getTaDto().getAsString("yzb613"),getTaDto().getAsString("yzb670"));
		}
		if (ValidateUtil.isEmpty(list)) {
			setMessage("表或视图不存在","warn");
		}
		setList("grid1", list);
		return JSON;
	}

	/**
	 * 获取项目或表达式选择
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!getEditSearchItem.do")
	public String getEditSearchItem() throws Exception {
		TaParamDto dto = setSearchItemService.querySearchItem(getTaDto());
		setData(dto, false);
		return JSON;
	}

	/**
	 * 保存配制项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!saveSearchItem.do")
	public String saveSearchItem() throws Exception {
		TaParamDto d = setSearchItemService.saveSearchItem(getTaDto());

		String msg = d.getAsString("msg");
		if (ValidateUtil.isNotEmpty(msg)) {
			boolean b = (Boolean) d.get("b");
			if (b) {
				setMessage(msg, "success");
				setSuccess(true);
			} else {
				setMessage(msg, "error");
				setSuccess(false);
			}
		}
		setData(d, false);
		return JSON;
	}

	/**
	 * 删除配制项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!removeSearchItem.do")
	public String removeSearchItem() throws Exception {
		List sel = getGridSelected("grid2");
		TaParamDto d = setSearchItemService.removeSearchItem(sel);
		
		String msg = d.getAsString("msg");
		if (ValidateUtil.isNotEmpty(msg)) {
			boolean b = (Boolean) d.get("b");
			if (b) {
				setMessage(msg, "success");
				setSuccess(true);
			} else {
				setMessage(msg, "error");
				setSuccess(false);
			}
		}
		return JSON;
	}

	@Resource
	SetSearchOrderService setSearchOrderService;

	/**
	 * 查询配制项目排序
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!querySearchOrder.do")
	public String querySearchOrder() throws Exception {
		List zb65lst = setSearchOrderService.querySearchOrders(getTaDto().getAsBigDecimal("yzb610"));
		if (ValidateUtil.isNotEmpty(zb65lst)) {
			setList("grid3", zb65lst);
		}
		return JSON;
	}

	/**
	 * 配制项目排序页面
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!toEditSearchOrder.do")
	public String toEditSearchOrder() throws Exception {
		setData(getTaDto(), false);
		return "cxtj/admin/setsearch/setSearchOrderEdit";
	}

	/**
	 * 查询待选项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!query1.do")
	public String query1() throws Exception {
		List lst = setSearchOrderService.query1(getTaDto());
		if (ValidateUtil.isNotEmpty(lst)) {
			setList("grid1", lst);
		}
		return JSON;
	}

	/**
	 * 查询已选项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!query2.do")
	public String query2() throws Exception {
		List lst = setSearchOrderService.query2(getTaDto());
		if (ValidateUtil.isNotEmpty(lst)) {
			setList("grid2", lst);
		}
		return JSON;
	}

	/**
	 * 选择待选项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!select1.do")
	public String select1() throws Exception {
		List lst = getGridSelected("grid1");
		setSearchOrderService.select1(lst, getTaDto());
		return JSON;
	}

	/**
	 * 选择已选项目
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!select2.do")
	public String select2() throws Exception {
		List lst = getGridSelected("grid2");
		setSearchOrderService.select2(lst, getTaDto());
		return JSON;
	}

	/**
	 * 删除配制项目排序
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!removeSearchOrder.do")
	public String removeSearchOrder() throws Exception {
		List lst = getGridSelected("grid3");
		TaParamDto d = setSearchOrderService.select2(lst, getTaDto());

		String msg = d.getAsString("msg");
		if (ValidateUtil.isNotEmpty(msg)) {
			boolean b = (Boolean) d.get("b");
			if (b) {
				setMessage(msg, "success");
				setSuccess(true);
			} else {
				setMessage(msg, "error");
				setSuccess(false);
			}
		}
		return JSON;
	}

	/**
	 * 设置排序
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!saveYzb651updown.do")
	public String saveYzb651updown() throws Exception {
		setSearchOrderService.saveYzb651updown(getTaDto());
		return JSON;
	}

	/**
	 * 设置排序方式
	 * 
	 * @author 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!saveYzb652Radio.do")
	public String saveYzb652Radio() throws Exception {
		setSearchOrderService.saveYzb652Radio(getTaDto());
		return JSON;
	}

	/**
	 * 查询相应数据源下的数据集
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setSearchAction!queryResultSetsByYzb670.do")
	public String queryResultSetsByYzb670() throws Exception {
		List<Map> resultSets= setSearchService.queryResultSetsByYzb670(getTaDto().getAsString("yzb670"));
		setSelectInputList("yzb690",resultSets);
		return JSON;
	}
}

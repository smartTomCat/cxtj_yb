package com.yinhai.ta3.extend;

import com.yinhai.core.common.ta3.vo.BaseVo;

public class MenuExtendVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6421407644644799989L;
	private String menuext;

	public MenuExtendVo(String menuext) {
		super();
		this.menuext = menuext;
	}

	public String getMenuext() {
		return menuext;
	}

	public void setMenuext(String menuext) {
		this.menuext = menuext;
	}

	public MenuExtendVo() {
		super();
	}
	
}

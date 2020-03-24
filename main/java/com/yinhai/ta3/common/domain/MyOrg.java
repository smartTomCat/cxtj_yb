package com.yinhai.ta3.common.domain;

import com.yinhai.modules.org.ta3.domain.po.impl.OrgPo;

/**
 * org 扩展
 * 
 * @author aolei
 *
 */
public class MyOrg extends OrgPo {
	private String idcard;

	public MyOrg(String idcard) {
		super();
		this.idcard = idcard;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public MyOrg() {
		super();
	}

}

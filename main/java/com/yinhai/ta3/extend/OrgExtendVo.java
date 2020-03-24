package com.yinhai.ta3.extend;

import com.yinhai.core.common.ta3.vo.BaseVo;

public class OrgExtendVo  extends BaseVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2602080865183540809L;
	private String idcard;


	public OrgExtendVo(String idcard) {
		super();
		this.idcard = idcard;
	}


	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public OrgExtendVo() {
		super();
	}

}

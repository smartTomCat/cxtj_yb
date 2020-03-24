package com.yinhai.ta3.extend;

import com.yinhai.core.common.ta3.vo.BaseVo;

public class PositionExtendVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946874540495326877L;
	private String positionext;

	public String getPositionext() {
		return positionext;
	}

	public void setPositionext(String positionext) {
		this.positionext = positionext;
	}

	public PositionExtendVo(String positionext) {
		super();
		this.positionext = positionext;
	}

	public PositionExtendVo() {
		super();
	}
	
}

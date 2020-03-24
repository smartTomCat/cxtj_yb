package com.yinhai.ta3.common.domain;

import com.yinhai.modules.org.ta3.domain.po.impl.PositionPo;

public class MyPosition extends PositionPo {

	private String positionext;

	public String getPositionext() {
		return positionext;
	}

	public void setPositionext(String positionext) {
		this.positionext = positionext;
	}

	public MyPosition(String positionext) {
		super();
		this.positionext = positionext;
	}

	public MyPosition() {
		super();
	}
	
	
}

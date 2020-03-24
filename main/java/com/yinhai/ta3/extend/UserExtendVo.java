package com.yinhai.ta3.extend;

import com.yinhai.core.common.ta3.vo.BaseVo;

public class UserExtendVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8756182804525014911L;
	private Integer age;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public UserExtendVo(Integer age) {
		super();
		this.age = age;
	}

	public UserExtendVo() {
		super();
	}

}

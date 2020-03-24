package com.yinhai.ta3.common.domain;

import com.yinhai.modules.org.ta3.domain.po.impl.UserPo;

public class MyUser extends UserPo {
	private Integer age;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public MyUser(Integer age) {
		super();
		this.age = age;
	}

	public MyUser() {
		super();
	}

}

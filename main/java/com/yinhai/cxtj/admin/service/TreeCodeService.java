package com.yinhai.cxtj.admin.service;

import com.yinhai.cxtj.admin.base.service.CxtjBaseService;

import java.util.List;



public interface TreeCodeService extends CxtjBaseService {
	public static final String SERVICEKEY="treeCodeService";
	
	public List getTreeDataByType(String type) throws Exception;
	public List getxzTreeByType() throws Exception;
	
	public List getSxTreeData() throws Exception ;
	
}

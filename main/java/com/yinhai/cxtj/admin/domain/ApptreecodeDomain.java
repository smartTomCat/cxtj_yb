package com.yinhai.cxtj.admin.domain;


import com.yinhai.cxtj.front.domain.InputDomain;

/**
 * @author zhaohs
 * @date 2019/8/19
 */
public class ApptreecodeDomain extends InputDomain {

	private static final long serialVersionUID = -1172301664301867580L;

	private String id; //节点ID
	
	private String pId; //父节点ID
	
	private String name; //节点名称
	
	private String isParent; //是否父节点
	
	private String type; //数据类型
	
	private String icon; //节点图标
	
	private String open; //是否展开
	
	private String iconSkin; // iconSkin
	
	private String py; // 拼音


	/**
	 * 设置 id 
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 id 
	 * @return 
	 */
	public String getId() {
		return this.id;
	}

	
	public String getPId() {
		return pId;
	}

	public void setPId(String id) {
		pId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

}

package com.yinhai.cxtj.front.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 构件输入domain *
 * */
public class InputDomain implements Serializable {

	private static final long serialVersionUID = -1010496093975328154L;
	/** 构件名 */
	private String servercode;
	/** 版本号 */
	private String ver;

	private String userId;

	private String orgId;

	/** 经办人姓名 */
	private String aae011;

	/** 经办人编号 */
	private Long yae116;

	/** 经办机构 */
	private Long aae017;

	/** UK持有经办人员 **/
	private String yae117;

	/** UK持有主管部门审核人员 **/
	private String yae118;

	/** UK持有行政部门审核人员 **/
	private String yae119;

	/** 经办日期 */
	private java.sql.Timestamp aae036;
	/** 开始条数 */
	protected Integer start;
	/** 页码 */
	protected Integer page;
	/** 每页多少条 */
	protected Integer limit;
	/** 总条数 */
	protected Integer total;
	/**
	 * @Fields paramMap :存放其它参数
	 */
	private Map paramMap = new HashMap();

	public InputDomain() {
		super();
	}

	/**
	 * length:10 <br/>
	 * name:版本号
	 * */
	public String getVer() {
		return ver;
	}

	/**
	 * <br/>
	 * length:10 <br/>
	 * name:版本号
	 * */
	public void setVer(String ver) {
		this.ver = ver;
	}

	/**
	 * length:50 <br/>
	 * name:构件名字
	 * */
	public String getServercode() {
		return servercode;
	}

	/**
	 * length:50 <br/>
	 * name:构件名字
	 * */
	public void setServercode(String servercode) {
		this.servercode = servercode;
	}

	public String getYae117() {
		return yae117;
	}

	public void setYae117(String yae117) {
		this.yae117 = yae117;
	}

	public String getYae118() {
		return yae118;
	}

	public void setYae118(String yae118) {
		this.yae118 = yae118;
	}

	public String getYae119() {
		return yae119;
	}

	public void setYae119(String yae119) {
		this.yae119 = yae119;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAae011() {
		return aae011;
	}

	public void setAae011(String aae011) {
		this.aae011 = aae011;
	}

	public Long getYae116() {
		return yae116;
	}

	public void setYae116(Long yae116) {
		this.yae116 = yae116;
	}

	public Long getAae017() {
		return aae017;
	}

	public void setAae017(Long aae017) {
		this.aae017 = aae017;
	}

	public java.sql.Timestamp getAae036() {
		return aae036;
	}

	public void setAae036(java.sql.Timestamp aae036) {
		this.aae036 = aae036;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

}

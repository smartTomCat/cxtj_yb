<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="com.yinhai.modules.org.api.util.IOrgConstants"%>
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<%
	String orgType_org = IOrgConstants.ORG_TYPE_ORG;
	String orgType_depart = IOrgConstants.ORG_TYPE_DEPART;
	String orgType_team = IOrgConstants.ORG_TYPE_TEAM;
%>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
	<title>新增人员</title>
	<%@ include file="/ta/inc.jsp"%>
</head>
<script type="text/javascript">
	function fnSaveSuccCb() {
		if (confirm("保存成功，是否继续新增人员？")) {
			Base.resetData("userForm");
			Base.focus("w_loginid");
			Base.setValue("w_sex","0");
            Base.setValue("authmode","01");
		} else {
			parent.Base.closeWindow('win');
		}
	}
</script>
<body class="no-scrollbar" style="padding:0px;margin:0px">
<div id='pageloading'></div>
<ta:form id="userForm" fit="true">
	<ta:panel id="userInfo" fit="true"  hasBorder="false" withButtonBar="true"  bodyStyle="padding: 10px 20px 12px 5px;">
		<ta:text id="selectOrgId" key="选中的组织ID" display="false"/>
		<ta:text id="orgnamepath" key="选中的组织路径" display="false"/>
		<ta:text id="w_userId" key="用户编号" readOnly="true" span="2" display="none"/>
		<ta:text id="w_loginid" key="登录帐号" required="true" span="2" bpopTipPosition="bottom" validType='[{type:"maxLength",param:[20],msg:"最大长度为20"}]'/>
		<ta:box span="2">
			<ta:selectTree fontCss="fnSetFont" url="orgUserMgAction!webQueryAsyncOrgTree.do" required="true" asyncParam="['orgid']"
						   selectTreeBeforeClick="fnselecttree"  selectTreeCallback="fnSelectTreeCallback" key="直属组织" nameKey="orgname"
						   idKey="orgid" parentKey="porgid" targetDESC="w_orgname"
						   treeId="w_orgTree" targetId="w_orgid"  textHelp="直属组织必须为机构和部门，<span style='color:red;font-size: 15px;font-weight: bold;'>红色</span>表示你当前岗位所不能操作的组织"/>
		</ta:box>
		<ta:box span="2">
			<ta:text id="w1_orgid" key="目标部门Id"  display="false"/>
			<ta:text id="w1_orgname" key="附属组织" onClick="showMenu();"/>
			<ta:box id ="menuContent" height="240px" cssClass="ffb" cssStyle="border-radius: 4px;box-shadow: 0 0 10px rgba(0,0,0,.5);padding:0px;margin:0px;background:white;display:none;width:312px;left:100px;top:28px;overflow:auto;position:absolute;z-index:1000;">
				<ta:panel fit="true" withButtonBar="true" bodyStyle="border:0px;overflow:auto" hasBorder="false">
					<ta:tree id="w1_orgTree" nameKey="orgname" childKey="orgid" parentKey="porgid" async="true" asyncUrl="orgUserMgAction!queryAsyncOrgTree.do" fontCss="fnSetFont"
							 asyncParam="['orgid']" checkable="true" beforeCheck="fnBeforeCheck" onCheck="onCheck"  chkboxType="{'Y':'','N':''}" />
					<ta:panelButtonBar align="center">
						<ta:button key="清除" icon="icon-refresh" id="menuBtn" onClick="fnFsRemove()"></ta:button>
						<ta:button key="关闭" icon="icon-close2" id="menuBtn1" onClick="hideMenu()"></ta:button>
					</ta:panelButtonBar>
				</ta:panel>
			</ta:box>
		</ta:box>
		<ta:text id="w_name" key="姓名" required="true" validType='[{type:"maxLength",param:[60],msg:"最大长度为60"}]'/>
		<ta:radiogroup key="性别" collection="SEX" id="w_sex" cols="8" filterOrg="false"/>
		<ta:text id="w_password" key="登录口令" required="true" span="2" type="password"  validType='[{type:"maxLength",param:[50],msg:"最大长度为50"}]'/>
		<ta:text id="w_rpassword" key="确认口令" required="true" span="2" type="password" validType='[{type:"maxLength",param:[50],msg:"最大长度为50"},{type:"compare",param:["=","w_password"]}]' />
		<ta:text id="tel" key="移动电话" validType='[{type:"mobile"}]'/>
		<ta:selectInput id="authmode" collection="authmode"  key="认证方式" value="01" readOnly="true" textHelp="认证方式,默认是用户名密码方式，还可以扩展为指纹识别、人脸识别、ca证书等" required="true" />
		<%-- 新增用户扩展jsp --%>
		<%@include file="../../org/orgextend/userMgExtend.jsp" %>
		<ta:panelButtonBar>
			<ta:button type="submit" id="saveUserBtn" key="保存[S]" icon="icon-addTo" hotKey="S"
					   submitIds="userInfo" url="orgUserMgAction!addUser.do"
					   successCallBack="fnSaveSuccCb"  />
			<ta:button id="closeWinBtn" key="关闭[X]" hotKey="X"  icon="icon-close2"
					   onClick="parent.Base.closeWindow('win');" />
		</ta:panelButtonBar>
	</ta:panel>
</ta:form>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function () {
		$("body").taLayout();
		Base.focus("w_loginid");
		Base.setValue("w_sex", "0");
        Base.setValue("w_orgid",Base.getValue("selectOrgId"));
        Base.setValue("w_orgname",Base.getValue("orgnamepath"));
		document.getElementById("w1_orgname").readOnly = true;
	});
	//单人更改设置选中后的样式
	function fnSetFont(treeId, treeNode){
		//if (treeNode.effective == 1 && treeNode.admin == true)return [{'color': 'red'},{'text-decoration':'line-through'}]
		if (treeNode.effective == 0) return {'text-decoration':'line-through'};
		if (!treeNode.admin) return {'color': 'red'};
		if (treeNode.orgtype == "<%=orgType_team%>"){
			return {"cursor":"not-allowed"};
		}
		return {};
	}
	//单人组织修改中的树
	function fnselecttree(treeId, treeNode) {
		//直属组织必须是部门或者机构
		if (treeNode.orgtype != "<%=orgType_org%>" && treeNode.orgtype != "<%=orgType_depart%>"){
			Base.msgTopTip("<div class='msgTopTip'>直属组织只能是部门或者机构，不能为组</div>");
			return false;
		}
		//是否有效
		if (treeNode.effective == 0) return false;
		//是否有管理权限
		if (!treeNode.admin) {
			Base.msgTopTip("<div class='msgTopTip'>你无权操作该组织</div>");
			return false;
		}
		var w_tree = $.fn.zTree.getZTreeObj("w_orgTree");
		var nodes = w_tree.getCheckedNodes(true);
		if(nodes){
			for(var i  = 0 ; i < nodes.length; i++){
				if(treeNode.orgid == nodes[i].orgid){
					Base.msgTopTip("附属组织已经有该组织，直属组织不能与附属组织相同",4000);
					return false;
				}
			}
		}
		return true;
	}
	//新增人员中树的节点点击事件
	function fnSelectTreeCallback(event, treeId, treeNode){
		Base.setValue("w_orgid",treeNode.orgid);
		Base.setValue("w_orgname",treeNode.orgnamepath);
// 	__fnHideSelectTree_w_orgTree();
		Base.getObj("w_orgTree").hideTree();
	}
	function fnFsRemove(){
		var zTree = $.fn.zTree.getZTreeObj("w1_orgTree");
		zTree.checkAllNodes(false);
		var cityObj = $("#w1_orgname");
		cityObj.attr("value", "");
		var targetDepartId1 = $("#w1_orgid");
		targetDepartId1.attr("value", "");
		Base.hideObj("menuContent");
	}
	function fnBeforeCheck(treeId, treeNode) {
		var w_orgid = Base.getValue("w_orgid");
		if(w_orgid && w_orgid != ""){
			if(treeNode.orgid == w_orgid){
				Base.alert("直属组织已经是该组织，附属组织不能选择为直属组织","warn");
				return false;
			}
		}else{
			Base.alert("请先选择直属组织","warn");
			return false;
		}
		if(!treeNode.admin){
			Base.msgTopTip("<div class='msgTopTip'>你无权操作该组织</div>");
			return false;
		}
		return true;
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("w1_orgTree"),
				nodes = zTree.getCheckedNodes(true),
				v = "",hv = "";

		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].orgname + ",";
			hv += nodes[i].orgid + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		if (hv.length > 0 ) hv = hv.substring(0, hv.length-1);
		var cityObj = $("#w1_orgname");
		cityObj.attr("value", v);
		var targetDepartId1 = $("#w1_orgid");
		targetDepartId1.attr("value", hv);
	}
	function showMenu() {
		$("#menuContent").slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "w1_orgname" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}

</script>
<%@ include file="/ta/incfooter.jsp"%>
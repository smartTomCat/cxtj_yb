<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
	<head>
	    <title>批量配制项目</title>
	    <%@ include file="/ta/inc.jsp" %>
	</head>
	<body style="overflow-x:hidden;padding-top: 0px!important;background-color: white!important;">
		<ta:pageloading/>
	    
	    <ta:form id="form1" fit="true" cssStyle="margin-top:10px;">
	    	<ta:number id="yzb610" display="false"/>
			<ta:text id="yzb670" display="false"/>
			<ta:text id="yzb690" display="false"/>
			<ta:text id="yzb613" display="false"/>
			<ta:text id="yzb617" display="false"/>
	    	<ta:text id="msg" display="false"/>
		    <ta:datagrid id="grid1" haveSn="true" snWidth="40" forceFitColumns="true" fit="true" heightDiff="50" selectType="checkbox" onSelectChange="fnQueryBack" columnFilter="true">
				<ta:datagridItem id="yzb623" key="数据库字段" showDetailed="true" align="center" dataAlign="center" width="100"/>
				<ta:datagridItem id="yzb625" key="数据库字段中文" showDetailed="true" align="center" dataAlign="center" width="100"/>
				<ta:datagridItem id="yzb626" key="数据类型" collection="YZB626" showDetailed="true" align="center" dataAlign="center" width="100"/>
				<ta:datagridItem id="yzb628" key="代码类别" showDetailed="true" align="center" dataAlign="center" width="100"/>
	   		</ta:datagrid>
	   		
	   		<ta:buttonGroup align="center" cssStyle="margin-top:10px;">
	    		<ta:button id="btnSelect" key="确定" onClick="fnSelect()" space="true" cssClass="btnmodify"/>
	    		<ta:button key="取消" onClick="fnClose()" cssClass="btnmodify"/>
	    	</ta:buttonGroup>
	    </ta:form>
	</body>
</html>
<script type="text/javascript">
$(document).ready(function () {
    $("body").taLayout();
    init();
});

function init(){
	fnQuery();
}

//查询
function fnQuery(){
	var _id = 'form1';
	var _url = 'setSearchAction!getEditSearchItemSelect.do';
	var _param = null;
	var _onsub = null;
	var _autoval = false;
	var _sucback = fnQueryBack;
	var _falback = fnQueryBack;
	Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
}
function fnQueryBack(){
	if(Base.getGridSelectedRows('grid1').length > 0){
		Base.setEnable('btnSelect');
	}else{
		Base.setDisabled('btnSelect');
	}
}

//确定
function fnSelect(){
	if(Base.getGridSelectedRows('grid1').length > 0){
		var _id = 'yzb610,msg,grid1,yzb617';
		var _url = 'setSearchAction!saveSearchItems.do';
		var _param = null;
		var _onsub = null;
		var _autoval = false;
		var _sucback = fnSelectbacksue;
		var _falback = fnSelectbackfal;
		Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
	}
}
function fnSelectbacksue(){
	Base.alert(Base.getValue('msg'), 'success', fnClose);
}
function fnSelectbackfal(){
	Base.alert(Base.getValue('msg'), 'error');
}

//关闭
function fnClose(){
	parent.Base.closeWindow('w_edit');
}
</script>
<%@ include file="/ta/incfooter.jsp" %>
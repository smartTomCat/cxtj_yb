<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
<title>日志等级管理</title>
<%@ include file="/ta/inc.jsp"%>
</head>
<body style="padding:0px;margin:0px" class="no-scrollbar" id="ww">
	<ta:pageloading />
	<ta:panel key="日志" cols="2">
		<ta:text id="logId" key="日志名:" readOnly="true"></ta:text>
		<ta:selectInput id="logLevel" key="日志等级:" collection="LOGLEVEL" readOnly="true"></ta:selectInput>
		<ta:button key="保存修改" onClick="modify()"></ta:button>
	</ta:panel>
	<ta:panel key="日志等級管理" fit="true">
		<ta:datagrid id="dgLog" fit="true" columnFilter="true" forceFitColumns="true" onRowClick="modifyLogLevel">
			<ta:datagridItem id="logId" key="日志名"></ta:datagridItem>
			<ta:datagridItem id="logLevel" key="日志等级"></ta:datagridItem>
		</ta:datagrid>
	</ta:panel>

</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		$("body").taLayout()
	});

	function modifyLogLevel(e,rowdata){
	    Base.setValue("logId",rowdata.logId);
        Base.setValue("logLevel",rowdata.logLevel);
	    Base.setEnable("logLevel",true);
	};

	function modify(){
	    if(Base.getValue("logLevel")!=''){
            Base.submit("",
                "logLevelController!modify.do",
                {"dto['logId']":Base.getValue("logId"),"dto['logLevel']":Base.getValue("logLevel")},
                null,
                false,
                function(data){
                    var obj = Base.getObj("dgLog");
                    Base.msgTopTip("修改成功");
                    obj.refreshGrid();
                }
            );
		}else {
            Base.msgTopTip("请选择修改的数据！");
		}
    };
</script>
<%@ include file="/ta/incfooter.jsp"%>
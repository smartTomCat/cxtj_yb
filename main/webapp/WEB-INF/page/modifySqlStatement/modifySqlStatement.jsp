<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
	<title>热修改SqlMap</title>
	<%@ include file="/ta/inc.jsp"%>
</head>
<body layout="border" class="no-scrollbar">
<ta:pageloading />
<ta:panel fit="true" key="热替换SQL">
	<ta:text id="dataSource" key="数据源名称" value="ta3" textHelp="框架默认数据源为ta3,如果需要修改其他数据源,请配置为其他数据源,如datasource.second.db.type,则设置为second" />
	<ta:fieldset key="替换整个sqlmap文件">
		<%--<ta:fileupload id="sqlMapFile" url="modifySqlStatementController!uploadSqlMapFile.do" key="鼠标点击上传需要修改sqlmap文件" autoSubmit="true" ></ta:fileupload>--%>
		<ta:uploader id="sqlMapFile" url="modifySqlStatementController!uploadSqlMapFile.do" submitIds="dataSource" autoSubmit="true" ></ta:uploader>
	</ta:fieldset>
	<ta:fieldset  key="替换单个statementId">
		<ta:textarea id="sqlStr" key="SqlMap内容" height="300"></ta:textarea>
		<ta:button type="submit" url="modifySqlStatementController!updateSql.do" submitIds="sqlStr,dataSource"  id="replaceBtn" key="替换"> </ta:button>
	</ta:fieldset>
</ta:panel>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        $("body").taLayout();
    });
</script>
<%@ include file="/ta/incfooter.jsp"%>
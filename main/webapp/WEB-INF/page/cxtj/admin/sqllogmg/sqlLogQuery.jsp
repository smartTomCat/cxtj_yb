<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>日志查询</title>
    <%@ include file="/ta/inc.jsp" %>
</head>
<body class="no-scrollbar" style="padding-top: 10px!important;background-color: white!important;">
<ta:pageloading/>
<ta:box >
    <ta:form id="form1" cols="4">
        <ta:text id="yzb612" key="主题名称" readOnly="true"/>
        <ta:text id="yzb711" key="方案名称" readOnly="true"/>
        <ta:text id="yzb993" key="耗时" readOnly="true"/>
    </ta:form>
    <ta:fieldset key="sql执行日志"
              cssStyle="margin-left:20px;box-shadow: 0px 2px 4px 0px rgba(221, 221, 221, 0.45);">
        <ta:textarea key="sql信息1" id="sql1" display="false" height="120"/>
        <ta:textarea key="sql信息2" id="sql2" display="false" height="120"/>
        <ta:textarea key="错误信息" id="sqlError" display="false" height="120"/>
    </ta:fieldset>
</ta:box>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        $("body").taLayout();

    });



</script>
<%@ include file="/ta/incfooter.jsp" %>

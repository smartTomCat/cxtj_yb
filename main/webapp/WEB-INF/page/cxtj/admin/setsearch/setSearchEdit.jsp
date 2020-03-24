<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
    <title>主题定义</title>
    <%@ include file="/ta/inc.jsp" %>
</head>
<body style="overflow-x:hidden;">
<ta:pageloading/>
<ta:form id="form1">
    <ta:box cols="2">
        <ta:number id="yzb610" display="false"/>
        <ta:text id="yzb612" key="功能菜单名称" span="2" required="true" labelStyle="margin-left: -10px;"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder="功能菜单描述"/>
        <ta:selectInput id="yzb617" key="查询统计类型" collection="YZB617" required="true"/>
        <ta:text id="yzb611" key="菜单代码" required="true" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]"
                 placeholder="菜单的唯一标识 英文数字下划线组成"/>
        <ta:selectInput id="yzb670" key="数据源" required="true" value="001" />
        <ta:text id="yzb613" key="主题库表" required="true" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]"/>
        <ta:text id="yzb618" key="对应菜单id" display="false"/>
    </ta:box>
    <ta:textarea id="yzb615" key="基础WHERE<br />条件" height="80px"
                 validType="[{type:'maxLength',param:[1000],msg:'最大长度为1000'}]"/>
    <ta:textarea id="yzb616" key="基础WHERE<br />条件变量说明" height="80px"
                 validType="[{type:'maxLength',param:[1000],msg:'最大长度为1000'}]"/>
    <ta:buttonGroup align="center" cssStyle="margin-top:20px">
        <ta:button id="btnNew" key="新增保存" space="true" onClick="fnSave()"/>
        <ta:button id="btnOld" key="修改保存" space="true" onClick="fnSave()"/>
        <ta:button key="关闭" onClick="fnClose()"/>
    </ta:buttonGroup>
</ta:form>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        $("body").taLayout();
        init();
    });

    function init() {
        fnSaveBack();
    }

    //保存
    function fnSave() {
        var _id = 'form1';
        var _url = 'setSearchAction!saveSearch.do';
        var _param = null;
        var _onsub = null;
        var _autoval = true;
        var _sucback = fnSaveBack;
        var _falback = fnSaveBack;
        Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
    }
    function fnSaveBack() {
        var yzb610 = Base.getValue('yzb610');
        if (yzb610) {
            Base.showObj('btnNew');
            Base.hideObj('btnOld');
        } else {
            Base.showObj('btnOld');
            Base.hideObj('btnNew');
        }
    }

    //关闭
    function fnClose() {
        parent.Base.closeWindow('w_edit')
    }
</script>
<%@ include file="/ta/incfooter.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
    <title>视图新增|编辑页面</title>
    <%@ include file="/ta/inc.jsp" %>
</head>
<body style="overflow-x:hidden;">
<ta:pageloading/>
<ta:form id="form1">
    <ta:box cols="2">
        <ta:number id="yzb680" display="false"/>
        <ta:selectInput id="yzb670" key="数据源" required="true" value="001" onSelect="fnSelect"/>
        <ta:text id="yzb681" key="视图名称" span="1" required="true"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder="只允许输入字母、数字和下划线"
                 onChange="fnCheckName(this)" readOnly="true"/>
    </ta:box>
    <ta:textarea id="yzb682" key="视图sql" height="300" placeholder="只允许使用 '' "
                 validType="[{type:'maxLength',param:[1000],msg:'最大长度为600'}]" required="true"/>
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
        var yzb680 = Base.getValue('yzb680');
        if (yzb680 == '') {
            //新增
            Base.showObj('btnNew');
            Base.hideObj('btnOld');
        } else {
            //编辑
            Base.showObj('btnOld');
            Base.hideObj('btnNew');
            Base.setReadOnly("yzb670,yzb681");
        }
    }

    //保存
    function fnSave() {
        beforeSave();
        var _id = 'form1';
        var _url = 'viewManageController!toSave.do';
        var _param = null;
        var _onsub = null;
        var _autoval = true;
        var _sucback = fnSaveBack;
        var _falback = null;
//        Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
    }


    function fnSaveBack() {
        fnClose();
    }

    //关闭
    function fnClose() {
        parent.Base.closeWindow('view_add')
    }

    /*检查视图名称是否存在*/
    function fnCheckName(obj) {
        /*检查系统标志格式和是否存在*/
        var name = obj.value.trim();
        if (!name || name == "") return;
        var reg = /^[A-Za-z0-9_]+$/;
        if (!reg.test(name)) {
            Base.alert("数据源名称格式不正确，只允许输入字母、数字和下划线！");
            Base.setValue("yzb681", "");
            return;
        }
        Base.submit("", "viewManageController!checkNameExist.do", {
            "dto['yzb670']": Base.getValue('yzb670'),
            "dto['yzb681']": obj.value
        });
    }

    //校验视图sql的格式
    function beforeSave() {
        var sql = Base.getValue("yzb682").toLowerCase();
        //1.以固定格式开头
        var reg1 = /^create or replace view */;
        if (!reg1.test(sql)) {
            Base.alert("请以 create or replace view 开头", "warn");
            return;
        }
        //不包含非法操作字符
        var reg2 = /(update|delete|drop|truncate|alter)/;
        if (reg2.test(sql)) {
            Base.alert("请勿包含以下关键字 update|delete|drop|truncate|alter 等", "warn");
            return;
        }
        //sql中的视图名称 必须和 输入框中的保持一致
        var index1 = sql.indexOf("view") + 4;
        var index2 = sql.indexOf("as");
        var viewName = sql.substring(index1, index2).trim();
        if (viewName != Base.getValue("yzb681")) {
            Base.alert("sql中的视图名称请与输入框保持一致", "warn");
            return;
        }
    }


    //逻辑解除置灰
    function fnSelect(key, value) {
        if (key) {
            Base.setEnable("yzb681");
        }
    }
</script>
<%@ include file="/ta/incfooter.jsp" %>
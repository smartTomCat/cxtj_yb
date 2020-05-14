<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
    <title>多表配置管理</title>
    <%@ include file="/ta/inc.jsp" %>
</head>
<body style="overflow: hidden">
<ta:pageloading/>
<ta:form id="form1" fit="true">
    <ta:box cols="3">
        <ta:selectInput id="yzb670" key="数据源"/>
        <ta:text id="yzb691" key="数据集名称"/>
        <ta:buttonLayout cssClass="btnlayout">
            <ta:button id="btnQuery" key="查询" onClick="fnQuery()" cssClass="btnmodify"/>
        </ta:buttonLayout>
    </ta:box>

    <%--    <ta:panel key="视图列表" fit="true">--%>
    <div class="tafieldset-header">
        <div class="fieldset-header-bg"></div>
        <h2>数据集列表</h2>
        <ta:buttonLayout cssClass="btnlayout">
            <ta:button id="btnCreate" key="新增" onClick="fnCreate()" cssClass="btnadd"/>
            <ta:button id="btnModify" key="编辑" onClick="fnModify()" cssClass="btnmodify"/>
            <ta:button id="btnRemove" key="删除" onClick="fnRemove()" cssClass="btndelete"/>
        </ta:buttonLayout>
    </div>
    <ta:datagrid id="grid1" haveSn="true" snWidth="40" forceFitColumns="true" fit="true" selectType="checkbox"
                 onSelectChange="fnShowBtn">
        <ta:datagridItem id="yzb690" key="数据集流水号" hiddenColumn="true"/>
        <ta:datagridItem id="yzb670" key="数据源配置流水号" hiddenColumn="true"/>
        <ta:datagridItem id="yzb672" key="数据源名称" dataAlign="center" formatter="setDefaultName"/>
        <ta:datagridItem id="yzb691" key="数据库字段" icon="icon-add2" dataAlign="center"/>
        <ta:datagridItem id="yzb692" key="数据集备注" showDetailed="true" align="center" dataAlign="center"/>
        <ta:datagridItem id="yzb695" key="表名集合" showDetailed="true" align="center" dataAlign="center"/>
        <ta:datagridItem id="yzb693" key="关联条件" dataAlign="center" showDetailed="true"/>
        <ta:datagridItem id="yzb694" key="数据集sql" dataAlign="center" showDetailed="true"/>
        <ta:dataGridToolPaging url="resultSetManageController!queryResultSets.do" submitIds="form1" pageSize="10"/>
    </ta:datagrid>
    <%--    </ta:panel>--%>


</ta:form>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        $("body").taLayout();
        fnQuery();
        Base.setDisabled("btnModify,btnRemove");
    });

    //查询
    function fnQuery() {
        Base.clearGridData('grid1');
        var _id = 'form1';
        var _url = 'resultSetManageController!queryResultSets.do';
        var _param = null;
        var _onsub = null;
        var _autoval = null;
        var _sucback = fnShowBtn;
        var _falback = fnShowBtn;
        Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
    }

    //显示btn
    function fnShowBtn() {
        if (Base.getGridSelectedRows('grid1').length == 1) {
            Base.setEnable('btnModify,btnRemove');
        } else if (Base.getGridSelectedRows('grid1').length > 1) {
            Base.setEnable('btnRemove');
            Base.setDisabled('btnModify');
        } else {
            Base.setDisabled('btnModify,btnRemove');
        }
    }

    //新增
    function fnCreate() {
        var _id = 'resultset_add';
        var _title = '新增数据集';
        var _url = 'resultSetManageController!toEdit.do';
        var _param = null;
        var _w = 1500;
        var _h = 800;
        var _load = null;
        var _close = fnQuery;
        var _iframe = true;
        Base.openWindow(_id, _title, _url, _param, _w, _h, _load, null, _iframe);
    }

    //编辑l
    function fnModify() {
        var data = Base.getGridSelectedRows('grid1');
        if (data.length > 0) {
            var _id = 'resultset_edit';
            var _title = '编辑视图';
            var _url = 'resultSetManageController!toEdit.do';
            var _param = {
                "dto.yzb690": data[0].yzb690
            };
            var _w = 1500;
            var _h = 800;
            var _load = null;
            var _close = fnQuery;
            var _iframe = true;
            Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
        }
    }

    //删除
    function fnRemove() {
        Base.confirm("确定要删除吗？", function (yes) {
            if (yes) {
                var data = Base.getGridSelectedRows('grid1');
                if (data.length > 0) {
                    var _id = 'grid1';
                    var _url = 'resultSetManageController!deleteResultSets.do';
                    var _param = null;
                    var _onsub = null;
                    var _autoval = false;
                    var _sucback = fnQuery;
                    var _falback = fnQuery;
                    Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
                }
            }
        });
    }


    //数据源名称为null补为框架数据源
    function setDefaultName(row, cell, value, columnDef, dataContext) {
        if (value) {
            return value;
        } else {
            return "框架数据源";
        }
    }
</script>
<%@ include file="/ta/incfooter.jsp" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
    <title>数据源管理</title>
    <%@ include file="/ta/inc.jsp"%>
</head>
<body class="no-scrollbar">
<ta:pageloading />
<ta:form id="form1" fit="true">
    <div class="tafieldset-header ">
        <div class="fieldset-header-bg"></div>
        <h2>数据源列表</h2>
        <ta:buttonLayout cssClass="btnlayout">
            <ta:button  key="新增" isok="true" onClick="fnToAdd()"/>
            <ta:button  key="查看/修改" isok="true"  onClick="fnToEdit()"/>
            <ta:button  key="删除" isok="true" onClick="fnDelete()"/>
        </ta:buttonLayout>
    </div>
    <ta:datagrid id="dataSourceGrid" fit="true" selectType="checkbox" forceFitColumns="true" haveSn="true">
        <ta:datagridItem id="yzb670" key="数据源配置管理流水号" hiddenColumn="true"/>
        <ta:datagridItem id="yzb672" key="数据源名称" dataAlign="center"/>
        <ta:datagridItem id="yzb673" key="数据源描述" dataAlign="center"/>
        <ta:datagridItem id="yzb671" key="数据源类型" dataAlign="center" collection="YZB671"/>
        <ta:datagridItem id="yzb674" key="数据库类型" dataAlign="center" collection="YZB674"/>
        <ta:dataGridToolPaging url="dataSourceManageController!queryList.do" submitIds="form1" pageSize="10" showToFull="false"/>
    </ta:datagrid>

<%--    <ta:panel id="panel2"  fit="true" key="数据源列表" withToolBar="true" hasBorder="true">--%>
<%--        <ta:panelToolBar>--%>
<%--            <ta:button  key="新增" isok="true" onClick="fnToAdd()"/>--%>
<%--            <ta:button  key="查看/修改" isok="true"  onClick="fnToEdit()"/>--%>
<%--            <ta:button  key="删除" isok="true" onClick="fnDelete()"/>--%>
<%--        </ta:panelToolBar>--%>

<%--        <ta:datagrid id="dataSourceGrid" fit="true" selectType="checkbox" forceFitColumns="true" haveSn="true">--%>
<%--            <ta:datagridItem id="yzb670" key="数据源配置管理流水号" hiddenColumn="true"/>--%>
<%--            <ta:datagridItem id="yzb672" key="数据源名称" dataAlign="center"/>--%>
<%--            <ta:datagridItem id="yzb673" key="数据源描述" dataAlign="center"/>--%>
<%--            <ta:datagridItem id="yzb671" key="数据源类型" dataAlign="center" collection="YZB671"/>--%>
<%--            <ta:datagridItem id="yzb674" key="数据库类型" dataAlign="center" collection="YZB674"/>--%>
<%--            <ta:dataGridToolPaging url="dataSourceManageController!queryList.do" submitIds="form1" pageSize="10"/>--%>
<%--        </ta:datagrid>--%>
<%--    </ta:panel>--%>
</ta:form>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        $("body").taLayout();
        fnQuery();
    });
    //查询
    function fnQuery(){
        Base.submit("form1","dataSourceManageController!queryList.do");
    }
    //新增
    function fnToAdd(){
        Base.openWindow("popWin","新增数据源","dataSourceManageController!toAdd.do",null, "50%",580,null,function(){
            fnQuery();
        },true);
    }
    //详情
    function fnToEdit(){
        var rows = Base.getGridSelectedRows("dataSourceGrid");
        if(rows.length != 1){
            Base.alert("请选择单行条目","warn");
            return false;
        }
        var params = {"dto['yzb670']":rows[0].yzb670};
        Base.openWindow("popWin","查看/修改数据源配置","dataSourceManageController!editDataSource.do", params, "50%",580,null,fnQuery,true);
    }
    //删除
    function fnDelete(){
        var rows = Base.getGridSelectedRows('dataSourceGrid');
        if(rows.length ==0){
            Base.alert("请选择至少一行数据！","warn");
            return false;
        }
        Base.confirm("确定要删除吗？",function(yes){
            if(yes){
                var data = Base.getGridSelectedRows('dataSourceGrid');
                if(data.length > 0){
                    var _id = 'dataSourceGrid';
                    var _url = 'dataSourceManageController!deleteDataSource.do';
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
</script>
<%@ include file="/ta/incfooter.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
    <title>方案管理</title>
    <%@ include file="/ta/inc.jsp"%>
</head>
<body class="no-scrollbar">
<ta:pageloading />
<ta:form id="form1" fit="true">
    <ta:text id="yzb612_name" display="false"/>
    <ta:box cols="3">
        <ta:text id="selyzb610" display="false"/>
        <ta:text id="sellabel" display="false"/>
        <ta:text id="yzb617" display="false"/>  <!-- change by zhaohs  查询统计类型 -->
        <ta:text id="yzb611" key="主题代码"/>
        <ta:text id="yzb612" key="主题名称"/>
        <ta:buttonLayout cssStyle="margin-left:50px;">
            <ta:button id="btnQuery"  key="查询" onClick="fnQuery()"/>
        </ta:buttonLayout>
    </ta:box>
    <ta:panel key="查询统计主题列表">
        <ta:datagrid id="grid1" haveSn="true" snWidth="40" forceFitColumns="true" height="200px" selectType="checkbox">
            <ta:datagridItem id="yzb610" hiddenColumn="true"/>
            <ta:datagridItem id="searchItem" key="查看方案" icon="icon-add2" click="queryPlan"/>
            <ta:datagridItem id="yzb611" key="主题代码" showDetailed="true" align="center" dataAlign="center" width="100" />
            <ta:datagridItem id="yzb612" key="主题名称" showDetailed="true" align="center" dataAlign="center" width="200"/>
            <ta:datagridItem id="yzb613" key="主题库表" collection="YZ0505" showDetailed="true" align="center" dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb617" key="查询统计类型（1查询2统计）" hiddenColumn="true"/>
            <ta:dataGridToolPaging url="setSearchAction!querySearchs.do" submitIds="form1" pageSize="10"/>
        </ta:datagrid>
    </ta:panel>
    <ta:panel id="pnl1" key="方案列表" fit="true" cols="2">
        <ta:buttonLayout align="right">
            <ta:button id="btnQue" key="刪除" onClick="" />
        </ta:buttonLayout>
        <ta:datagrid id="grid2" fit="true" heightDiff="30" forceFitColumns="true" haveSn="true" selectType="checkbox" span="2">
            <ta:datagridItem  id="yzb612" key="主题名称" formatter="setName"/>
            <ta:datagridItem  id="yzb711" key="统计方案名称"/>
            <ta:datagridItem  id="aae036" key="保存时间"/>
            <ta:dataGridToolPaging url="planManageController!queryPlan.do" pageSize="20" showCount="true" submitIds="form1" />
        </ta:datagrid>
    </ta:panel>
</ta:form>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        $("body").taLayout();
    });
    //查询主题
    function fnQuery(){
        Base.clearGridData('grid1');
        var _id = 'form1';
        var _url = '${basePath}/rsgl/search/setSearchAction!querySearchs.do';
        var _param = null;
        var _onsub = null;
        var _autoval = null;
        Base.submit(_id, _url, _param, _onsub, _autoval, null, null);
    }
    //查询主题下的方案
    function queryPlan(data, e) {
        Base.setValue("yzb612_name",data.yzb612);
        var yzb610 = data.yzb610;
        Base.clearGridData('grid2');
        Base.submit("", "planManageController!queryPlan.do", {'dto.yzb610':yzb610},null,null,function(){

        });
    }
    //
    function setName(row, cell, value, columnDef, dataContext){
        var name = Base.getValue("yzb612_name");
        return name;
    }
</script>
<%@ include file="/ta/incfooter.jsp"%>
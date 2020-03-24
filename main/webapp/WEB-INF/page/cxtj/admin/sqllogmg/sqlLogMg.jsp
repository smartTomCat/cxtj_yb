<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
    <title>日志查看</title>
    <%@ include file="/ta/inc.jsp"%>
</head>
<body class="no-scrollbar">
<ta:pageloading />
<ta:form id="form1" fit="true">
    <ta:box cols="6" cssStyle="margin-top:0px;">
        <ta:selectInput id="yzb612" key="主题名称" onSelect="queryFaByZt"/>
        <ta:selectInput id="yzb711" key="方案名称"/>
        <ta:date id="yzb991" key="开始时间" showSelectPanel="true"  />
        <ta:date id="yzb992" key="结束时间" showSelectPanel="true" />
        <ta:selectInput id="yzb994" key="是否出错" collection="YZB994"/>
        <ta:buttonLayout align="right" cssStyle="float:right">
            <ta:button id="btnQuery" key="查询" onClick="queryLog()" />
        </ta:buttonLayout>
    </ta:box>
    <ta:panel id="pnl1" key="日志列表" fit="true" cols="2">
        <ta:datagrid id="grid1" fit="true" heightDiff="30" forceFitColumns="true"   span="2" >
            <ta:datagridItem  id="yzb990" key="流水号" hiddenColumn="true"/>
            <ta:datagridItem  id="yzb612" key="主题名称" dataAlign="center"/>
            <ta:datagridItem  id="yzb711" key="统计方案名称" dataAlign="center"/>
            <ta:datagridItem  id="yzb991" key="执行开始时间" dataAlign="center"/>
            <ta:datagridItem  id="yzb992" key="执行结束时间" dataAlign="center"/>
            <ta:datagridItem  id="yzb993" key="总耗时(s)" dataAlign="center"/>
            <ta:datagridItem  id="yzb994" key="是否出错" collection="YZB994" dataAlign="center" formatter="hasError"/>
            <ta:datagridItem  id="detail" key="操作"  click="Detail" formatter="detail"  dataAlign="center"/>
            <ta:datagridItem  id="yzb995" key="执行sql集" hiddenColumn="true"/>
            <ta:datagridItem  id="yzb996" key="错误信息" hiddenColumn="true"/>
            <ta:dataGridToolPaging url="sqlLogManageController!queryLog.do" pageSize="20" showCount="true" submitIds="form1" />
        </ta:datagrid>
    </ta:panel>
</ta:form>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        $("body").taLayout();
        queryLog();
    });

    function queryFaByZt(key,value) {
        Base.setEnable("btnQuery");
        Base.submit("","sqlLogManageController!queryFaByZt.do",{"dto.yzb610":value});
    }


    //查询日志
    function queryLog(){
        var _id = 'form1';
        var _url = 'sqlLogManageController!queryLog.do';
        var _param = null;
        var _onsub = null;
        var _autoval = null;
        Base.submit(_id, _url, _param, _onsub, _autoval, null, null);
    }


    //日志查看弹窗
    function Detail(data,e) {
        var _id = '';
        var _title = "sql日志查看";
        var _url = 'sqlLogManageController!sqlQueryWindow.do';
        var _w = "1000";
        var _h = "550";
        var _load = null;
        var _iframe = true;
        Base.openWindow(_id, _title, _url, {'dto.yzb612':data.yzb612,'dto.yzb711':data.yzb711,'dto.yzb993':data.yzb993,'dto.yzb994':data.yzb994,'dto.yzb995':data.yzb995,'dto.yzb996':data.yzb996}, _w, _h, _load, function (data) {

        }, _iframe);
    }

    //是否出错列渲染
    function hasError(row, cell, value, columnDef, dataContext){
        if("是"== value){
            return "<span style='color: red'>" + value + "</span>";
        }else{
            return value;
        }
    }

    //操作列渲染
    function detail(row, cell, value, columnDef, dataContext) {
        return "<span style='color: blue;cursor: pointer;text-decoration: solid'>详情</span>";
    }

</script>
<%@ include file="/ta/incfooter.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
    <title>多表配置新增|编辑页面</title>
    <%@ include file="/ta/inc.jsp" %>
</head>
<body style="overflow-x:hidden;padding-top: 0!important;background-color: white!important;">
<ta:pageloading/>
<ta:form id="form1" fit="true">
    <ta:box cols="4">
        <ta:number id="yzb690" display="false"/>
        <ta:text id="yzb691" key="数据集名称" span="1" required="true"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder="只允许输入字母、数字和下划线"
                 onChange="fnCheckName(this)" readOnly="false"/>
        <ta:text id="yzb692" key="数据集备注" span="1" required="true"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder="只允许输入字母、数字和下划线"
                 onChange="fnCheckName(this)" readOnly="false"/>
        <ta:selectInput id="yzb670" key="数据源" required="true" value="001" onSelect="fnSelect"/>
        <ta:selectTree treeId="tableTree" url="" selectTreeCallback="genDataGrid"
                       targetDESC="yzb891_desc" targetId="yzb695" key="表" isMultiple="true"
        ></ta:selectTree>
        <ta:button id="loadtable" key="拉表" onClick="genDataGrid()" cssClass="btnmodify"/>
    </ta:box>
    <ta:box id="dgContainer" cols="1" fit="true" cssStyle="margin-top:10px">
        <ta:datagrid id="grid1" haveSn="true" snWidth="40" forceFitColumns="true" fit="true" selectType="checkbox">
            <ta:datagridItem id="yzb891" key="表名" showDetailed="true" align="center" dataAlign="center"/>
            <ta:datagridItem id="yzb892" key="字段名" showDetailed="true" align="center" dataAlign="center"/>
            <ta:datagridItem id="comments" key="字段备注" dataAlign="center" showDetailed="true"/>
        </ta:datagrid>
    </ta:box>
    <ta:box>
        <ta:text id="yzb693" key="关联条件" span="1" required="true"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder="别名为表名的全小写 emp.deptno = dept.deptno"/>
    </ta:box>
    <ta:buttonGroup align="center" cssStyle="margin-top:20px">
        <ta:button id="btnNew" key="新增保存" space="true" onClick="fnSave()" cssClass="btnmodify"/>
        <ta:button id="btnOld" key="修改保存" space="true" onClick="fnSave()" cssClass="btnmodify"/>
        <ta:button key="关闭" onClick="fnClose()" cssClass="btnmodify"/>
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
        var yzb690 = Base.getValue('yzb690');
        if (!yzb690) {
            //新增
            Base.showObj('btnNew');
            Base.hideObj('btnOld');
        } else {
            //编辑
            Base.showObj('btnOld');
            Base.hideObj('btnNew');
            Base.setReadOnly("yzb670,yzb691");
            queryEditAll(yzb690);
        }
    }

    function queryEditAll(yzb690) {
        Base.submit(null,"resultSetManageController!queryEditAll.do",{"dto.yzb690":yzb690});
    }

    //保存
    function fnSave() {
        var b = beforeSave();
        var b = 1;
        var columnsData  = Base.getGridSelectedRows('grid1');
        if (columnsData.length > 0 && b) {
            Base.submit("form1","resultSetManageController!saveResultSet.do",null,null,null,function () {
                
            });
        }
    }


    function fnSaveBack() {
        fnClose();
    }

    //关闭
    function fnClose() {
        parent.Base.closeWindow('view_add')
    }

    /*检查数据集名称是否存在 TODO*/
    function fnCheckName(obj) {

    }

    //校验视图关联语句的格式
    function beforeSave() {
        //TODO
    }


    /**
     * 数据源变更事件
     * @param key
     * @param value
     */
    function fnSelect(key, value) {
        if (value) {
            //查询该数据源下的表 TODO
            Base.submit(null, "resultSetManageController!genTableTre.do", {
                "dto.yzb670": value,
                "dto.yzb672": key
            }, null, null, function (data) {
                console.log(data);
                var settings = Ta.core.TaUIManager.getCmp("tableTree").getzTreeObj().setting;
                $.fn.zTree.init($("#tableTree"), settings, data.fieldData.treeData);
            });
        }
    }

    /**
     * 拉表
     * @param event
     * @param treeId
     * @param treeNode
     */
    function genDataGrid() {
        var checkedNodes = Base.getObj("tableTree").getCheckedNodes();
        var parms = [];
        for (var key in checkedNodes) {
            parms.push(checkedNodes[key].id);
        }
        Base.submit(null, "resultSetManageController!queryTableColumns.do", {"dto.tables": parms, "dto.yzb670": Base.getValue("yzb670")});
    }


</script>
<%@ include file="/ta/incfooter.jsp" %>
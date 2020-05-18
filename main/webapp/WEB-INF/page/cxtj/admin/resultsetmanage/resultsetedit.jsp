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
<ta:form id="form1">
    <ta:box cols="4">
        <ta:number id="yzb690" display="false"/>
        <ta:text id="yzb691" key="数据集名称" span="1" required="true"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder="只允许输入字母、数字和下划线"
                 onChange="fnCheckName(this)" readOnly="false"/>
        <ta:text id="yzb692" key="数据集备注" span="1" required="true"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder="只允许输入字母、数字和下划线"
                 onChange="fnCheckName(this)" readOnly="false"/>
        <ta:selectInput id="yzb670" key="数据源" required="true" value="001" onSelect="fnSelect"/>
        <ta:selectTree treeId="tableTree"  key="表"  url="" selectTreeCallback="genDataGrid"
                       targetDESC="yzb695_desc" targetId="yzb695" isMultiple="true" required="true"
        ></ta:selectTree>
    </ta:box>
    <div class="tafieldset-header">
        <div class="fieldset-header-bg"></div>
        <h2>表字段信息</h2>
        <ta:buttonLayout cssClass="btnlayout">
            <ta:button id="loadtable" key="拉表" onClick="genDataGrid()" cssClass="btnmodify"/>
            <ta:button id="cleartable" key="清空" onClick="clearDataGrid()" cssClass="btndelete"/>
        </ta:buttonLayout>
    </div>
    <ta:box id="dgContainer" cols="1"  cssStyle="margin-top:10px">
        <ta:datagrid id="grid1" haveSn="true" snWidth="40" forceFitColumns="true" height="550px"  selectType="checkbox">
            <ta:datagridItem id="yzb891" key="表名" showDetailed="true" align="center" dataAlign="center"/>
            <ta:datagridItem id="yzb892" key="字段名" showDetailed="true" align="center" dataAlign="center"/>
            <ta:datagridItem id="yzb893" key="字段备注" dataAlign="center" showDetailed="true"/>
            <ta:datagridItem id="yzb894" key="字段类型" dataAlign="center" showDetailed="true"/>
        </ta:datagrid>
    </ta:box>
    <ta:box>
        <ta:text id="yzb693" key="关联条件" span="1" required="true"
                 validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" placeholder=" emp.deptno = dept.deptno"/>
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
            Base.setDisabled("loadtable,cleartable");
            //多选下拉树置灰处理
            $(".selectTree-input-Container").addClass("readonly");
            queryEditAll(yzb690);
        }
    }

    function queryEditAll(yzb690) {
        Base.submit(null,"resultSetManageController!queryEditAll.do",{"dto.yzb690":yzb690}, null,null,function(data){
            Base.setValue("yzb695_desc",data.fieldData.yzb695);
            Base.getObj("grid1").selectAllData();
        });
    }

    //保存
    function fnSave() {
        var b = beforeSave();
        var columnsData  = Base.getGridSelectedRows('grid1');
        if (columnsData.length > 0 && b) {
            Base.submit("form1","resultSetManageController!saveResultSet.do",null,null,null,function () {
            });
        }
    }


    //关闭
    function fnClose() {
        parent.Base.closeWindow('resultset_edit')
    }

    /*检查数据集名称是否存在 TODO*/
    function fnCheckName(obj) {
        /*检查系统标志格式和是否存在*/
        var name = obj.value.trim();
        if (!name || name == "") return;
        Base.submit("", "resultSetManageController!checkNameExist.do", {
            "dto['yzb691']": obj.value
        });
    }

    //校验视图关联语句的格式
    function beforeSave() {
        var joinsql = Base.getValue("yzb693");
        //不包含非法操作字符
        var reg2 = /(update|delete|drop|truncate|alter)/;
        if (reg2.test(joinsql)) {
            Base.alert("请勿包含以下关键字 update|delete|drop|truncate|alter 等", "warn");
            return false;
        }
        return true;
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

    /**
     * 清空表数据
     */
    function clearDataGrid() {
        Base.clearGridData("grid1");
    }


</script>
<%@ include file="/ta/incfooter.jsp" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
    <title>配制项目排序</title>
    <%@ include file="/ta/inc.jsp" %>
    <style>
        .upimg {
            width: 16px;
            height: 16px;
            cursor: pointer;
            margin-left: 10px;
        }
    </style>
</head>
<body style="overflow-x:hidden;padding-top: 0px!important;background-color: white!important;">
<ta:pageloading/>
<ta:form id="form1">
    <ta:text id="yzb610" display="false"/>
    <ta:box cols="5">
        <ta:panel key="待选项目" span="2">
            <ta:box cols="3">
                <ta:text id="name_1" span="2.4"/>
                <ta:buttonLayout span="0.6" align="left">
                    <ta:button key="查询" onClick="fnQuery1()" cssClass="btnmodify"/>
                </ta:buttonLayout>
            </ta:box>
            <ta:datagrid id="grid1" forceFitColumns="true" height="365px" selectType="checkbox"
                         onSelectChange="fnShowBtn">
                <ta:datagridItem id="yzb620" hiddenColumn="true"/>
                <ta:datagridItem id="yzb621" key="排序号" showDetailed="true" align="center" dataAlign="center"
                                 width="60"/>
                <ta:datagridItem id="yzb623" key="数据库字段或表达式" showDetailed="true" align="center" dataAlign="center"
                                 width="150"/>
                <ta:datagridItem id="yzb624" key="数据库字段AS别名" showDetailed="true" align="center" dataAlign="center"
                                 width="150"/>
                <ta:datagridItem id="yzb625" key="数据库字段中文" showDetailed="true" align="center" dataAlign="center"
                                 width="100"/>
            </ta:datagrid>
        </ta:panel>

        <ta:box span="0.4">
            <table width="100%" height="450px" cellpadding="0" cellspacing="0">
                <tr>
                    <td valign="bottom" align="center">
                        <ta:button id="btnSelect1" key=">>>" onClick="fnSelect1()" cssStyle="margin-bottom: 10px;"/>
                    </td>
                </tr>
                <tr>
                    <td valign="top" align="center">
                        <ta:button id="btnSelect2" key="<<<" onClick="fnSelect2()" cssStyle="margin-top: 10px;"/>
                    </td>
                </tr>
                <tr>
                    <td valign="bottom" align="center">
                        <ta:button key="关闭" onClick="fnClose()" cssClass="btnmodify"/>
                    </td>
                </tr>
            </table>
        </ta:box>

        <ta:panel key="已选项目" span="2.6">
            <ta:box cols="3">
                <ta:text id="name_2" span="2.4"/>
                <ta:buttonLayout span="0.6" align="left">
                    <ta:button key="查询" onClick="fnQuery2()" cssClass="btnmodify"/>
                </ta:buttonLayout>
            </ta:box>
            <ta:datagrid id="grid2" forceFitColumns="true" height="365px" selectType="checkbox"
                         onSelectChange="fnShowBtn">
                <ta:datagridItem id="yzb650" hiddenColumn="true"/>
                <ta:datagridItem id="yzb651" key="排序号" formatter="fnFormatterYzb651" showDetailed="true" align="center"
                                 dataAlign="center" width="80"/>
                <ta:datagridItem id="yzb652" key="排序方式" formatter="fnFormatterYzb652" showDetailed="true" align="center"
                                 dataAlign="center" width="100"/>
                <ta:datagridItem id="yzb623" key="数据库字段或表达式" showDetailed="true" align="center" dataAlign="center"
                                 width="150"/>
                <ta:datagridItem id="yzb624" key="数据库字段AS别名" showDetailed="true" align="center" dataAlign="center"
                                 width="150"/>
                <ta:datagridItem id="yzb625" key="数据库字段中文" showDetailed="true" align="center" dataAlign="center"
                                 width="100"/>
            </ta:datagrid>
        </ta:panel>
    </ta:box>
</ta:form>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        $("body").taLayout();
        init();
    });

    function init() {
        fnQuery1();
        fnQuery2();
    }

    function fnFormatterYzb651(row, cell, value, columnDef, dataContext) {
        var lab = '';
        lab += "<table width='100%'>";
        lab += '<tr>';
        lab += "<td width='26px'>";
        lab += "<div class='upimg xui-icon-slideup' onclick='setYzb651updown(" + dataContext.yzb650 + ", 1)'></div>";
        lab += '</td>';
        lab += "<td algin='center'>";
        lab += value;
        lab += '</td>';
        lab += '</tr>';
        lab += '</table>';
        return lab;
    }

    //设置排序方式
    function setYzb651updown(yzb650, isup) {
        var _id = 'form1';
        var _url = 'setSearchAction!saveYzb651updown.do';
        var _param = {'dto.yzb650': yzb650, 'dto.isup': isup};
        var _onsub = null;
        var _autoval = false;
        var _sucback = fnQuery2;
        var _falback = fnQuery2;
        Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
    }


    function fnFormatterYzb652(row, cell, value, columnDef, dataContext) {
        if (value == "1") {
            var lab = '';
            lab += '<label>';
            lab += "<input type='radio' name=\"dto['yzb652_" + dataContext.yzb650 + "']\" checked='true' onclick='setYzb652Radio(" + dataContext.yzb650 + ", 1)'/>";
            lab += '升序';
            lab += '</label>';
            lab += ' ';
            lab += '<label>';
            lab += "<input type='radio' name=\"dto['yzb652_" + dataContext.yzb650 + "']\" onclick='setYzb652Radio(" + dataContext.yzb650 + ", 2)'/>";
            lab += '降序';
            lab += '</label>';
        } else {
            var lab = '';
            lab += '<label>';
            lab += "<input type='radio' name=\"dto['yzb652_" + dataContext.yzb650 + "']\" onclick='setYzb652Radio(" + dataContext.yzb650 + ", 1)'/>";
            lab += '升序';
            lab += '</label>';
            lab += ' ';
            lab += '<label>';
            lab += "<input type='radio' name=\"dto['yzb652_" + dataContext.yzb650 + "']\" checked='true' onclick='setYzb652Radio(" + dataContext.yzb650 + ", 2)'/>";
            lab += '降序';
            lab += '</label>';
        }
        return lab;
    }

    //设置排序方式
    function setYzb652Radio(yzb650, yzb652) {
        var _id = 'form1';
        var _url = 'setSearchAction!saveYzb652Radio.do';
        var _param = {'dto.yzb650': yzb650, 'dto.yzb652': yzb652};
        var _onsub = null;
        var _autoval = false;
        Base.submit(_id, _url, _param, _onsub, _autoval);
    }

    //查询1
    function fnQuery1() {
        Base.clearGridData('grid1');
        var _id = 'form1';
        var _url = 'setSearchAction!query1.do';
        var _param = null;
        var _onsub = null;
        var _autoval = null;
        var _sucback = fnShowBtn;
        var _falback = fnShowBtn;
        Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
    }

    //查询2
    function fnQuery2() {
        Base.clearGridData('grid2');
        var _id = 'form1';
        var _url = 'setSearchAction!query2.do';
        var _param = null;
        var _onsub = null;
        var _autoval = null;
        var _sucback = fnShowBtn;
        var _falback = fnShowBtn;
        Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
    }

    //显示btn
    function fnShowBtn() {
        if (Base.getGridSelectedRows('grid1').length > 0) {
            Base.setEnable('btnSelect1');
        } else {
            Base.setDisabled('btnSelect1');
        }

        if (Base.getGridSelectedRows('grid2').length > 0) {
            Base.setEnable('btnSelect2');
        } else {
            Base.setDisabled('btnSelect2');
        }
    }

    //选择1
    function fnSelect1() {
        if (Base.getGridSelectedRows('grid1').length > 0) {
//		var _id = 'form1,grid1';
            //change by zhaohs
            var _id = 'yzb610,grid1';
            var _url = 'setSearchAction!select1.do';
            var _param = null;
            var _onsub = null;
            var _autoval = null;
            var _sucback = fnSelect1back;
            var _falback = fnSelect1back;
            Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
        }
    }

    function fnSelect1back() {
        fnQuery1();
        fnQuery2();
    }

    //选择2
    function fnSelect2() {
        if (Base.getGridSelectedRows('grid2').length > 0) {
            var _id = 'yzb610,grid2';
            var _url = 'setSearchAction!select2.do';
            var _param = null;
            var _onsub = null;
            var _autoval = null;
            var _sucback = fnSelect1back;
            var _falback = fnSelect1back;
            Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
        }
    }

    //关闭
    function fnClose() {
        parent.Base.closeWindow('w_edit')
    }
</script>
<%@ include file="/ta/incfooter.jsp" %>
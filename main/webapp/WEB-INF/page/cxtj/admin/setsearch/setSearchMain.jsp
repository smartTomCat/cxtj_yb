<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
    <title>查询统计功能配置管理</title>
    <%@ include file="/ta/inc.jsp" %>
    <style>
        .labelfont {
            font-size: 13px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<ta:pageloading/>
<ta:text id="yzb617" display="false"/>
<ta:text id="yzb670" key="数据源流水号" display="false"/>
<ta:text id="yzb613" key="库表名称" display="false"/>
<ta:form id="form1">
    <ta:box cols="3">
        <ta:text id="selyzb610" display="false"/>
        <ta:text id="sellabel" display="false"/>
        <ta:text id="yzb611" key="菜单代码"/>
        <ta:text id="yzb612" key="功能菜单名称"/>
        <ta:buttonLayout cssStyle="margin-left:50px;">
            <ta:button id="btnQuery" key="查询" onClick="fnQuery()"/>
            <ta:button id="btnCreate" key="新增" onClick="fnCreate()"/>
            <ta:button id="btnModify" key="编辑" onClick="fnModify()"/>
            <ta:button id="btnRemove" key="删除" onClick="fnRemove()"/>
        </ta:buttonLayout>
    </ta:box>

    <ta:panel key="查询统计主题定义列表">
        <ta:datagrid id="grid1" haveSn="true" snWidth="40" forceFitColumns="true" height="365px" selectType="checkbox"
                     onSelectChange="fnShowBtn">
            <ta:datagridItem id="yzb610" hiddenColumn="true"/>
            <ta:datagridItem id="searchItem" key="配制项目" icon="icon-add2" click="fnSearchItem"/>
            <ta:datagridItem id="searchOrder" key="配制项目排序" icon="icon-edit" click="fnSearchOrder"/>
            <ta:datagridItem id="yzb611" key="主题代码" showDetailed="true" align="center" dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb612" key="主题名称" showDetailed="true" align="center" dataAlign="center" width="200"/>
            <ta:datagridItem id="yzb670" key="主题流水号" hiddenColumn="true"/>
            <ta:datagridItem id="yzb672" key="数据源名称" width="100" dataAlign="center" formatter="setDefaultName"/>
            <ta:datagridItem id="yzb613" key="主题库表" collection="YZ0505" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb617" key="查询统计类型（1查询2统计）" hiddenColumn="true"/>
            <ta:datagridItem id="yzb618" key="对应的菜单id" hiddenColumn="true"/>
            <ta:dataGridToolPaging url="setSearchAction!querySearchs.do" submitIds="form1" pageSize="10"/>
        </ta:datagrid>
    </ta:panel>


    <ta:panel id="palSearchItem" key="<label id='labelSearchItem' class='labelfont'></label>&nbsp;配制项目">
        <ta:buttonLayout align="right">
            <ta:button key="批量添加" onClick="fnCreateSearchItems()"/>
            <ta:button key="添加" onClick="fnCreateSearchItem()"/>
            <ta:button id="btnModifySearchItem" key="编辑" onClick="fnModifySearchItem()"/>
            <ta:button id="btnRemoveSearchItem" key="删除" onClick="fnRemoveSearchItem()"/>
        </ta:buttonLayout>
        <ta:datagrid id="grid2" forceFitColumns="true" height="365px" selectType="checkbox"
                     onSelectChange="fnShowSearchItemBtn">
            <ta:datagridItem id="yzb620" hiddenColumn="true"/>
            <ta:datagridItem id="yzb610" hiddenColumn="true"/>
            <ta:datagridItem id="yzb621" key="排序号" showDetailed="true" align="center" dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb623" key="数据库字段或表达式" showDetailed="true" align="center" dataAlign="center"
                             width="100"/>
            <ta:datagridItem id="yzb624" key="数据库字段AS别名" showDetailed="true" align="center" dataAlign="center"
                             width="100"/>
            <ta:datagridItem id="yzb625" key="数据库字段中文" showDetailed="true" align="center" dataAlign="center"
                             width="100"/>
            <ta:datagridItem id="yzb626" key="数据类型" collection="YZB626" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb628" key="代码类别" showDetailed="true" align="center" dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb62d" key="是否作为查询条件" collection="YZB62D" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb62a" key="查询条件的展现形式" collection="YZB62A" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb62b" key="分组控制" collection="YZB62B" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb62c" key="分组计算控制" collection="YZB62C" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb641" key="默认统计方式" collection="YZB641" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb62f" key="查询列控制" collection="YZB62F" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="aae100" key="有效标志" collection="AAE100" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:dataGridToolPaging showPagingBar="false"/>
        </ta:datagrid>
    </ta:panel>

    <ta:panel id="palSearchOrder" key="<label id='labelSearchOrder' class='labelfont'></label>&nbsp;配制项目排序">
        <ta:buttonLayout align="right">
            <ta:button key="添加" onClick="fnSelSearchOrder()"/>
            <ta:button key="删除" onClick="fnRemoveSearchOrder()"/>
        </ta:buttonLayout>
        <ta:datagrid id="grid3" forceFitColumns="true" height="365px" selectType="checkbox"
                     onSelectChange="fnShowSearchOrderBtn">
            <ta:datagridItem id="yzb650" hiddenColumn="true"/>
            <ta:datagridItem id="yzb620" hiddenColumn="true"/>
            <ta:datagridItem id="yzb651" key="排序号" showDetailed="true" align="center" dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb652" key="排序方式" collection="YZB652" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
            <ta:datagridItem id="yzb623" key="数据库字段或表达式" showDetailed="true" align="center" dataAlign="center"
                             width="100"/>
            <ta:datagridItem id="yzb624" key="数据库字段AS别名" showDetailed="true" align="center" dataAlign="center"
                             width="100"/>
            <ta:datagridItem id="yzb625" key="数据库字段中文" showDetailed="true" align="center" dataAlign="center"
                             width="100"/>
            <ta:datagridItem id="yzb626" key="数据类型" collection="YZB626" showDetailed="true" align="center"
                             dataAlign="center" width="100"/>
        </ta:datagrid>
    </ta:panel>

</ta:form>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        $("body").taLayout();
        init();
        Base.setDisabled("btnModify,btnRemove,btnModifySearchItem,btnRemoveSearchItem")
    });

    function fnGetW(w) {
        var all = document.body.scrollWidth * 0.8;
        if (all < w) {
            return all;
        }
        return w;
    }
    function fnGetH(h) {
        var all = document.body.scrollHeight * 0.8;
        if (all < h) {
            return all;
        }
        return h;
    }

    function init() {
        $('#btnCreate').css('margin-top', '6px');
        $('#btnModify').css('margin-top', '6px');
        $('#btnRemove').css('margin-top', '6px');
        $('#btnRemove').css('margin-right', '-20px');

        $('#btnCreateSearchItems').css('margin-top', '6px');
        $('#btnCreateSearchItem').css('margin-top', '6px');
        $('#btnModifySearchItem').css('margin-top', '6px');
        $('#btnRemoveSearchItem').css('margin-top', '6px');
        $('#btnRemoveSearchItem').css('margin-right', '-20px');

        $('#btnSelSearchOrder').css('margin-top', '6px');
        $('#btnRemoveSearchOrder').css('margin-top', '6px');
        $('#btnRemoveSearchOrder').css('margin-right', '-20px');
        fnQuery();
    }

    //查询
    function fnQuery() {
        Base.hideObj('palSearchItem,palSearchOrder');
        Base.clearGridData('grid1');
        var _id = 'form1';
        var _url = 'setSearchAction!querySearchs.do';
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
        var _id = 'w_edit';
        var _title = '查询统计主题定义';
        var _url = 'setSearchAction!toEditSearch.do';
        var _param = null;
        var _w = fnGetW(765);
        var _h = fnGetW(350);
        var _load = null;
        var _close = fnQuery;
        var _iframe = true;
        Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
    }

    //编辑
    function fnModify() {
        var data = Base.getGridSelectedRows('grid1');
        if (data.length > 0) {
            var _id = 'w_edit';
            var _title = '查询统计主题定义';
            var _url = 'setSearchAction!toEditSearch.do';
            var _param = {'dto.yzb610': data[0].yzb610};
            var _w = fnGetW(765);
            var _h = fnGetW(350);
            var _load = null;
            var _close = fnQuery;
            var _iframe = true;
            Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
        }
    }

    //删除
    function fnRemove() {
        var data = Base.getGridSelectedRows('grid1');
        if (data.length > 0) {
            var _id = 'grid1';
            var _url = 'setSearchAction!removeSearch.do';
            var _param = null;
            var _onsub = null;
            var _autoval = false;
            var _sucback = fnQuery;
            var _falback = fnQuery;
            Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
        }
    }

    //查询label
    function getLabel() {
        var _id = 'selyzb610';
        var _url = 'setSearchAction!getLabel.do';
        var _param = null;
        var _onsub = null;
        var _autoval = false;
        var _sucback = getLabelback;
        var _falback = getLabelback;
        Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
    }
    function getLabelback() {
        document.getElementById('labelSearchItem').innerHTML = Base.getValue('sellabel');
        document.getElementById('labelSearchOrder').innerHTML = Base.getValue('sellabel');
    }

    //记录配置的项目是查询类还是统计类,方便后续设置弹出配置界面的隐藏项
    function setYzb617(data) {
        Base.setValue("yzb617", data.yzb617);
    }
    //配制项目
    function fnSearchItem(data, e) {
        setYzb617(data);
        Base.setValue("yzb670", data.yzb670);
        Base.setValue("yzb613", data.yzb613);
        var selyzb610 = data.yzb610;
        if (selyzb610 != '') {
            Base.setValue('selyzb610', selyzb610);
            Base.hideObj('palSearchOrder');
            Base.showObj('palSearchItem');
            fnQuerySearchItem();
            getLabel();
        }
    }

    //查询配制项目
    function fnQuerySearchItem() {
        var selyzb610 = Base.getValue('selyzb610');
        if (selyzb610 != '') {
            Base.clearGridData('grid2');
            var _id = 'form1';
            var _url = 'setSearchAction!querySearchItem.do';
            var _param = {'dto.yzb610': selyzb610};
            var _onsub = null;
            var _autoval = null;
            var _sucback = fnShowSearchItemBtn;
            var _falback = fnShowSearchItemBtn;
            Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
        }
    }
    function fnShowSearchItemBtn() {
        if (Base.getGridSelectedRows('grid2').length == 1) {
            Base.setEnable('btnModifySearchItem,btnRemoveSearchItem');
        } else if (Base.getGridSelectedRows('grid2').length > 1) {
            Base.setEnable('btnRemoveSearchItem');
            Base.setDisabled('btnModifySearchItem');
        } else {
            Base.setDisabled('btnModifySearchItem,btnRemoveSearchItem');
        }
    }

    var _searchitemselect_ = '';

    //批量新增配制项目
    function fnCreateSearchItems() {
        var selyzb610 = Base.getValue('selyzb610');
        //数据源流水号
        var yzb670 = Base.getValue("yzb670");
        //库表名称
        var yzb613 = Base.getValue("yzb613");
        //查询统计方式
        var yzb617 = Base.getValue("yzb617");
        if (selyzb610 != '') {
            var _id = 'w_edit';
            var _title = '配制项目';
            var _url = 'setSearchAction!toEditSearchItems.do';
            var _param = {'dto.yzb610': selyzb610, 'dto.yzb670': yzb670, 'dto.yzb613': yzb613, 'dto.yzb617': yzb617};
            var _w = fnGetW(800);
            var _h = fnGetW(500);
            var _load = null;
            var _close = fnQuerySearchItem;
            var _iframe = true;
            Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
        }
    }

    //新增配制项目
    function fnCreateSearchItem() {
        //查询统计类型（1查询2统计）
        var yzb617 = Base.getValue("yzb617");
        var selyzb610 = Base.getValue('selyzb610');
        //数据源流水号
        var yzb670 = Base.getValue("yzb670");
        //库表名称
        var yzb613 = Base.getValue("yzb613");
        //查询统计方式
        var yzb617 = Base.getValue("yzb617");
        if (selyzb610 != '') {
            var _id = 'w_edit';
            var _title = '配制项目';
            var _url = 'setSearchAction!toEditSearchItem.do';
            var _param = {
                'dto.yzb610': selyzb610,
                'dto.yzb617': yzb617,
                'dto.yzb670': yzb670,
                'dto.yzb613': yzb613,
                'dto.yzb617': yzb617
            };
            var _w = fnGetW(1000);
            var _h = fnGetW(450);
            if ("2" == yzb617) {
                _h = fnGetW(600);
            }
            var _load = null;
            var _close = fnQuerySearchItem;
            var _iframe = true;
            Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
        }
    }

    //编辑配制项目
    function fnModifySearchItem() {
        //查询统计类型（1查询2统计）
        var yzb617 = Base.getValue("yzb617");
        var data = Base.getGridSelectedRows('grid2');
        if (data.length == 1) {
            var _id = 'w_edit';
            var _title = '配制项目';
            var _url = 'setSearchAction!toEditSearchItem.do';
            var _param = {'dto.yzb620': data[0].yzb620, 'dto.yzb617': yzb617};
            var _w = fnGetW(1000);
            var _h = fnGetW(450);
            if ("2" == yzb617) {
                _h = fnGetW(600);
            }
            var _load = null;
            var _close = fnQuerySearchItem;
            var _iframe = true;
            Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
        }
    }

    //删除配制项目
    function fnRemoveSearchItem() {
        var data = Base.getGridSelectedRows('grid2');
        if (data.length > 0) {
            var _id = 'grid2';
            var _url = 'setSearchAction!removeSearchItem.do';
            var _param = null;
            var _onsub = null;
            var _autoval = false;
            var _sucback = fnQuerySearchItem;
            var _falback = fnQuerySearchItem;
            Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
        }
    }

    //配制项目排序
    function fnSearchOrder(data, e) {
        var selyzb610 = data.yzb610;
        if (selyzb610 != '') {
            Base.setValue('selyzb610', selyzb610);
            Base.hideObj('palSearchItem');
            Base.showObj('palSearchOrder');
            fnQuerySearchOrder();
            getLabel();
        }
    }

    //查询配制项目排序
    function fnQuerySearchOrder() {
        var selyzb610 = Base.getValue('selyzb610');
        if (selyzb610 != '') {
            Base.clearGridData('grid3');
            var _id = 'form1';
            var _url = 'setSearchAction!querySearchOrder.do';
            var _param = {'dto.yzb610': selyzb610};
            var _onsub = null;
            var _autoval = null;
            var _sucback = fnShowSearchOrderBtn;
            var _falback = fnShowSearchOrderBtn;
            Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
        }
    }
    function fnShowSearchOrderBtn() {
        if (Base.getGridSelectedRows('grid3').length > 0) {
            Base.setEnable('btnRemoveSearchOrder');
        } else {
            Base.setDisabled('btnRemoveSearchOrder');
        }
    }

    //选择配制项目排序
    function fnSelSearchOrder() {
        var selyzb610 = Base.getValue('selyzb610');
        if (selyzb610 != '') {
            var _id = 'w_edit';
            var _title = '配制项目排序';
            var _url = 'setSearchAction!toEditSearchOrder.do';
            var _param = {'dto.yzb610': selyzb610};
            var _w = fnGetW(1300);
            var _h = fnGetW(550);
            var _load = null;
            var _close = fnQuerySearchOrder;
            var _iframe = true;
            Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
        }
    }

    //删除配制项目排序
    function fnRemoveSearchOrder() {
        var data = Base.getGridSelectedRows('grid3');
        var selyzb610 = Base.getValue('selyzb610');
        if (data.length > 0 && selyzb610 != '') {
            var _id = 'grid3';
            var _url = 'setSearchAction!removeSearchOrder.do';
            var _param = {'dto.yzb610': selyzb610};
            var _onsub = null;
            var _autoval = false;
            var _sucback = fnQuerySearchOrder;
            var _falback = fnQuerySearchOrder;
            Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
        }
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
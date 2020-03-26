<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
    <title>自定义查询</title>
    <%@ include file="/ta/inc.jsp" %>
    <link rel="stylesheet" type="text/css" href="${basePath}cxtj/css/customizeQuery.css"/>
</head>
<body style="padding-top: 5px!important;background-color: white!important;">
<ta:form>
<ta:box id="mainBox" fit="true" cols="4" heightDiff="40">
    <ta:box id="leftBox" span="3" height="200px" cssStyle="border: #9CB1EA 0px solid;overflow:auto;">
        <table id="datagrid_query_id" width="100%" align="center" cellpadding="2" cellspacing="0" border="0">
            <tbody>
            <tr id="datagrid_title_id">
                <td style="width:35px;">
                </td>
                <!--
                <td style="width:20px;">
                    排序
                </td> -->
                <td style="width:65%;">
                    项目名称
                </td>
                <!--
                <td style="width:30%">
                    是否进行分组
                </td>
                 -->
            </tr>
            <s:iterator value="#request.fzxms" id="item" status="st">
                <tr onclick="fnXzTr(this)">
                    <td style="width:35px;">
                        <input type="radio" name="tjxmRadio" onclick="fnSetBackgroudColor(this)"
                               datatype="<s:property value="#item.datatype"/>"
                               fname="<s:property value="#item.filedname" />"
                               ztxmlsh="<s:property value="#item.ztxmlsh" />"
                               value="<s:property value="#item.id" />"/>
                    </td>
                    <!--
                    <td style="width:20px;">
                       <div class="arrow_up" title="点击可上移一行"  onclick="fnUpRow(this);"></div>
                    </td>
                    -->
                    <td style="width:80%;">
                        <s:property value="#item.filedname"/>
                    </td>
                    <!--
					<td style="width:30%">
					     <input type="checkbox" name="tjxm"
					           datatype="<s:property value="#item.datatype"/>"  
					           fname="<s:property value="#item.filedname" />" 
					           ztxmlsh="<s:property value="#item.ztxmlsh" />" 
					           value="<s:property value="#item.ztxmlsh" />"
						       <s:if test="#item.checked == true"> checked="checked" </s:if>/>
					</td>
					 -->
                </tr>
            </s:iterator>
            </tbody>
        </table>
    </ta:box>
    <ta:box id="rightBox">
        <ta:buttonLayout id="btnLyt2" cssStyle="padding-top:5px">
            <ta:button id="btn2" key="&nbsp;&nbsp;置&nbsp;&nbsp;顶&nbsp;&nbsp; " onClick="fnTop()" cssClass="btnmodify"/>
        </ta:buttonLayout>
        <ta:buttonLayout id="btnLyt3" cssStyle="padding-top:5px">
            <ta:button id="btn3" key="&nbsp;&nbsp;上&nbsp;&nbsp;移&nbsp;&nbsp;" onClick="fnUp()" cssClass="btnmodify"/>
        </ta:buttonLayout>
        <ta:buttonLayout id="btnLyt4" cssStyle="padding-top:5px">
            <ta:button id="btn4" key="&nbsp;&nbsp;下&nbsp;&nbsp;移&nbsp;&nbsp;" onClick="fnDown()" cssClass="btnmodify"/>
        </ta:buttonLayout>
        <ta:buttonLayout id="btnLyt5" cssStyle="padding-top:5px">
            <ta:button id="btn5" key="&nbsp;&nbsp;置&nbsp;&nbsp;尾&nbsp;&nbsp;" onClick="fnEnd()" cssClass="btnmodify"/>
        </ta:buttonLayout>
    </ta:box>
</ta:box>
<ta:buttonGroup id="btnLyt6" align="center" span="2">
	<ta:button id="btn6" key="确定" onClick="fnOkSetGroupByFiledsOrder()" cssClass="btnmodify" space="true"/>
	<ta:button id="btn7" key="关闭" onClick="fnClose()" cssClass="btnmodify"/>
</ta:buttonGroup>
</ta:form>
</body>
</html>
<script type="text/javascript">
    function fnXzTr(_this) {
        $($(_this).find("td > [name='tjxmRadio']")[0]).attr("checked", 'checked');
        fnSetBackgroudColor(null);
    }

    function fnSetBackgroudColor(_this) {
        $.each($("input[name='tjxmRadio']"), function (sn, _o) {
            var curTr = $(_o).parent().parent();
            if (!$(_o).is(':checked')) {
                curTr.attr('style', '');
            } else {
                curTr.attr('style', 'background-color:#4dc863;');
            }
        });
    }

    //统计行上移
    function fnUpRow(_this) {
        var currTr = $(_this).parent().parent();
        if (currTr.index() != 1) {
            var cloneTr = currTr.clone();
            currTr.prev().before(cloneTr);
            currTr.remove();
        } else {
            Base.alert("第一行数据不能上移！");
        }
    }

    function fnOkSetGroupByFiledsOrder() {
        var chckedGroupFiled = [];
        $.each($("input[name='tjxmRadio']"), function (sn, _o) {
            _o_ = {};
            _o_['sn'] = ++sn;
            _o_['filedname'] = ($(_o).attr('fname') || '');
            _o_['ztxmlsh'] = ($(_o).attr('ztxmlsh') || '');
            _o_['datatype'] = ($(_o).attr('datatype') || '');
            _o_['id'] = ($(_o).attr('value') || '');
            _o_['checked'] = true;
            chckedGroupFiled.push(_o_);
        });
        window.parent.fnGenerateGroupByFiledsHtml(chckedGroupFiled);
        parent.Base.closeWindow('setGroupByFiledWindows');
    }

    function fnClose() {
        parent.Base.closeWindow('setGroupByFiledWindows');
    }

    $(document).ready(function () {
        $("body").taLayout();
    });

    //向上移动
    function fnUp() {
        var tjxmRadioChecked = $("input[name='tjxmRadio']:checked");
        if (tjxmRadioChecked.length == 0) {
            Base.alert("请先选择要上移的行！");
        } else {
            var currRadio = $(tjxmRadioChecked[0]);
            fnUpRow(currRadio);
        }
    }

    //向下移动
    function fnDown() {
        var tjxmRadioChecked = $("input[name='tjxmRadio']:checked");
        if (tjxmRadioChecked.length == 0) {
            Base.alert("请先选择要置顶的行！");
        } else {
            var currTr = $(tjxmRadioChecked[0]).parent().parent();
            var currRowNum = currTr.index();
            var tableRowNum = $("#datagrid_query_id>tbody tr").length;
            if ((currRowNum + 1) != tableRowNum) {
                var cloneTr = currTr.clone();
                currTr.next().after(cloneTr);
                currTr.remove();
            } else {
                Base.alert("你选择的行已经在最后一行了！");
            }
        }
    }

    //置顶
    function fnTop() {
        var tjxmRadioChecked = $("input[name='tjxmRadio']:checked");
        if (tjxmRadioChecked.length == 0) {
            Base.alert("请先选择要置顶的行！");
        } else {
            var currTr = $(tjxmRadioChecked[0]).parent().parent();
            $("#datagrid_query_id>tbody tr:eq(0)").after(currTr);
        }
    }

    //置尾
    function fnEnd() {
        var tjxmRadioChecked = $("input[name='tjxmRadio']:checked");
        if (tjxmRadioChecked.length == 0) {
            Base.alert("请先选择要置尾的行！");
        } else {
            var currTr = $(tjxmRadioChecked[0]).parent().parent();
            var currRowNum = currTr.index();
            var tableRowNum = $("#datagrid_query_id>tbody tr").length;
            if ((currRowNum + 1) != tableRowNum) {
                var cloneTr = currTr.clone();
                $("#datagrid_query_id>tbody tr:eq(" + (tableRowNum - 1) + ")").after(cloneTr);
                currTr.remove();
            } else {
                Base.alert("你选择的行已经在最后一行了！");
            }
        }
    }
</script>
<%@ include file="/ta/incfooter.jsp" %>
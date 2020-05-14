<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
    <title>自定义查询</title>
    <%@ include file="/ta/inc.jsp" %>
    <link rel="stylesheet" type="text/css" href="${basePath}cxtj/css/customizeQuery.css"/>
    <style type="">
        .header-bg {
            height: 14px;
            width: 4px;
            background-color: #0077e0;
            position: absolute;
            top: 14px;
        }
    </style>
    <script type="text/javascript">
        var tbtjPageSize = 100;//设置图表最多统计查询100条数据
        function onTreeCheck(event, treeId, treeNode) {
            if (treeNode) {
                var _treeId = treeNode.id;
                var _treeName = treeNode.name;
                var inputs = $("#" + treeId).parent().parent().prev().find("input");
                var _val = inputs.eq(0);
                var _val1 = _val.val();
                var _key = inputs.eq(1);
                var _key1 = _key.val();
                var _key0 = "";
                if (treeNode.checked) {
                    _val.val(_val1 + _treeId + ";");
                    _key.val(_key1 + _treeName + ";");

                } else {
                    var _val2 = _val1.replace(_treeId + ";", "");
                    var _key2 = _key1.replace(_treeName + ";", "");
                    _val.val(_val2);
                    _key.val(_key2);
                }
                _key.attr('title', _key.val());

            }
        }

        var setting = {
            view: {
                showIcon: true,
                showLine: true == false ? false : true,
                showTitle: true,
                expandSpeed: "fast",
                selectedMulti: false,
                autoCancelSelected: false
            },
            check: {
                enable: true,
                chkboxType: {"Y": "", "N": ""}
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {onCheck: onTreeCheck}
        };
    </script>
</head>
<body>
<ta:form id="queryId">
    <ta:text id="jctj" display="false"/>
    <ta:text id="ztdm" display="false"/>
    <ta:text id="tjfalsh" display="false"/>
    <ta:text id="tjfaname" display="false"/>
    <ta:text id="yzb617" display="false"/>
    <ta:text id="yzb610" display="false"/>
    <ta:text id="yzb612" display="false"/>
    <ta:text id="yzb670" display="false"/>
    <ta:text id="parm" display="false"/>
    <ta:text id="xml" display="false"/>
    <div layout="column">
        <div class="tafieldset_header">
            <div class="header-bg"></div>
            <h2 style="font-size: 16px;float: left;margin-left:10px;">统计条件</h2>&nbsp;【<a href="javascript:void(0);"
                                                                                         title='条件组内是并且关系。条件组之间是或者关系。比如：查询男年满60周岁或女年满55周岁，则2个条件组。1个为：性别=男、年龄>=60;另一个为：性别=女、年龄>=55'
                                                                                         onclick="addConditionGroup()">追加"或者"条件组</a>】
            <span class="to_right" style="line-height:35px;">
	    <ta:buttonLayout align="right">
            <ta:button key="保存统计方案" onClick="fnToSaveTjfa()" cssClass="btnmodify"/>
            <ta:button key="调出统计方案" onClick="fnToCalloutTjfa()" cssClass="btnmodify"/>
        </ta:buttonLayout>
	</span>
        </div>
        <div id="gruops_id">


                <%--<s:if test="#request.resultBean.fieldData.orsMap!=null">--%>
                <%--change by zhaohs--%>
            <s:if test="#request.orsMap!=null">
                <s:iterator value="#request.orsMap.keySet()" id="key" status="table_st">
                    <table id="datagrid_query_id" width="100%" align="center" cellpadding="2" cellspacing="0"
                           border="0">
                        <tbody>
                        <tr id="datagrid_title_id">
                            <td style="width:30px;">
                                操作
                            </td>
                            <td style="width:80px;">
                                项目名称
                            </td>
                            <td style="width:50px;">
                                运算符
                            </td>
                            <td style="width:70%">
                                内容
                            </td>
                        </tr>
                        <s:iterator value="#request.orsMap[#key]" id="and" status="tr_st">
                            <s:set name="_and" value="and"/>
                            <tr>
                                <td style="width:30px;">
                                    <a href="javascript:void(0);" onclick="deleteTr(this)">删除</a>
                                </td>
                                <td style="width:80px;">
                                    <select name="cxxm" style="width:97%;" onchange="getItemGxOrNr(this)">
                                        <option value=''>---请选择---</option>
                                        <s:iterator value="#_and['cxxms']" id="item" status="st">
                                            <option value='<s:property value="#item.yzb620"/>'
                                                    filed='<s:property value="#item.yzb623"/>'
                                                    <s:if test="#item.yzb620==#_and['yzb620']">selected="selected"</s:if>>
                                                <s:property value="#item.yzb625"/>
                                            </option>
                                        </s:iterator>
                                    </select>
                                </td>
                                <td style="width:50px;">
                                    <select name="gxysf" style="width:97%;">
                                        <s:iterator value="#_and['gxyss']" id="item" status="st">
                                            <option value='<s:property value="#item.yzb631"/>'
                                                    <s:if test="#item.yzb631==#_and['yzb631']">selected="selected"</s:if>>
                                                <s:property value="#item.yzb631_desc"/>
                                            </option>
                                        </s:iterator>
                                    </select>
                                </td>
                                <td id="nr_td_id" style="text-align: left;padding-left: 5px;width: 70%">
			   <span id='nrz_span_id' style='width:98%;
               <s:if test="#_and['yzb734'] == 1">display:inline;</s:if>
               <s:elseif test="#_and['yzb734'] == 2">display:none;</s:elseif>'>
			    <!-- 代码值平铺 -->
			    <s:if test="#_and['yzb62a']=='21'">
                    <s:iterator value="#_and['codes']" id="item" status="st">
				        <input name='nrz_checkbox' type='checkbox' value="<s:property value="#item.aaa102" />"
                               <s:if test="#item.checked == 1">checked="checked"</s:if>/>&nbsp;<s:property
                            value="#item.aaa103"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </s:iterator>
                </s:if>
                   <!-- 文本框 -->
			    <s:elseif test="#_and['yzb62a']=='11'">
                    <input name='nrz' type='text' style='width: 200px;'
                           value="<s:if test="#_and['yzb734'] == 1"><s:property value="#_and['yzb733']"/></s:if>"/>
                </s:elseif>
                   <!-- 日期 -->
			    <s:elseif test="#_and['yzb62a']=='13'">
                    <input name='nrz' type='text'
                           value="<s:if test="#_and['yzb734'] == 1"><s:property value="#_and['yzb733']" /></s:if>"
                           onfocus=WdatePicker({position:{top:6,left:-9},isShowWeek:false,dateFmt:'yyyy-MM-dd'})
                           class='Wdate' maxlength='10'/>
                </s:elseif>
                   <!-- 年月 -->
			    <s:elseif test="#_and['yzb62a']=='12'">
                    <input name='nrz' type='text'
                           value="<s:if test="#_and['yzb734'] == 1"><s:property value="#_and['yzb733']" /></s:if>"
                           onfocus=WdatePicker({position:{top:6,left:-9},isShowWeek:false,dateFmt:'yyyy-MM'})
                           class='Wdate' maxlength='10'/>
                </s:elseif>
                   <!-- 数字 -->
			    <s:elseif test="#_and['yzb62a']=='14'">
                    <input name='nrz' type='text' style='width: 200px;'
                           value="<s:if test="#_and['yzb734'] == 1"><s:property value="#_and['yzb733']" /></s:if>"/>
                </s:elseif>
                   <!-- datagrid -->
			    <s:elseif test="#_and['yzb62a']=='23'">
                </s:elseif>
                   <!-- 树 -->
			    <s:elseif test="#_and['yzb62a']=='22'">
                    <s:set name="treeId" value="'treeData_' + #table_st.index + '_' + #tr_st.index"/>
                    <div style='width:250px;position: relative;left: 0px;top: 0px;height: 20px;'>
		               <input name='nrz' id='nrz_<s:property value="treeId"/>' type='hidden'/>
                       <span class='innerIcon innerIcon_show' style='margin-top:2px' onclick='showMenu(this)'
                             title='点击展开下拉树'></span>
                       <span class='innerIcon innerIcon_delete' style='margin-top:2px'
                             onclick="fnClear(this,'<s:property value="treeId"/>')" title='清除'></span>
                       <input type='text' id='nrz_key_<s:property value="treeId"/>' style='width: 250px;'
                              readonly='true' title=''/></div>
                    <div name='menuContent'
                         style='display:none; position: absolute;z-index:1000;width:300px;height:350px;background: #fff;border:#D1D1D1 1px solid;overflow:auto;'>
                       <input type='text' name='queyName' style='width:150px;margin-left:5px;margin-top:5px;'/>
                       <a href='javascript:void(0);'><font size='2' style='cursor: pointer;margin-left:5px'
                                                           onclick=fnQueryData(this,'<s:property
                                                                   value="treeId"/>')>查询</font></a>&nbsp;&nbsp;
					   <a href='javascript:void(0);'><font size='2' style='cursor: pointer;'
                                                           onclick=fnQueryPre('<s:property value="treeId"/>')>上一个</font></a>&nbsp;&nbsp;
					   <a href='javascript:void(0);'><font size='2' style='cursor: pointer;'
                                                           onclick=fnQueryNext('<s:property
                                                                   value="treeId"/>')>下一个</font></a>&nbsp;&nbsp;
					   <div style='margin-top:5px;height:319.5px;width:299px'>
				       <ul id='<s:property value="treeId"/>' name='treeData' class='ztree'
                           style='margin-top:0;'></ul></div></div>
                    <script type="text/javascript">
			            var chekedTreeData = <s:property escapeHtml="false" value="#_and['chekedTreeData']"/>;
                        $(document).ready(function () {
//                            (function (fn) {
//                                (require(["jquery", "ztree.excheck.min", "ztree.exedit", "ztree.exhide.min"], fn));
//                            }(function ($) {
                            $.fn.zTree.init($('#<s:property value="treeId"/>'), setting, <s:property escapeHtml="false" value="#_and['treeData']"/>);
                            var treeObj = $.fn.zTree.getZTreeObj('<s:property value="treeId"/>');
                            $.each(chekedTreeData, function (sn, __o) {
                                var node = treeObj.getNodesByParam("id", __o.yzb741, null);
                                treeObj.checkNode(node[0], true, true);
                            });
//                            }));
                        });
                        //另提解决文本框延迟问题
                        var ids = '', idnames = '';
                        $.each(chekedTreeData, function (sn, __o) {
                            ids += __o.yzb741 + ';';
                            idnames += __o.yzb741_name + ';';
                        });
                        $('#nrz_<s:property value="treeId"/>').val(ids);
                        $('#nrz_key_<s:property value="treeId"/>').attr('title', idnames).val(idnames);
			          </script>
                </s:elseif>
			    
			    
			     <input name='datatype' value='<s:property value="#_and['yzb62a']" />' type='hidden'/>
			     <input name='vtype' value='<s:property value="#_and['yzb734']" />' type='hidden'/>
			   </span>

                                    <span id='nr_item_select_id' style='
                                    <s:if test="#_and['yzb734'] == 1">display:none;</s:if>
                                    <s:elseif test="#_and['yzb734'] == 2">display:inline;</s:elseif>'>
			        <s:if test="#_and['yzb734'] == 2">
			          <select name='nrxmz' style='width:250px;'>
		               <option value=''>---请选择---</option>
			           <s:iterator value="#_and['cxxms']" id="item" status="st">
			               <option value='<s:property value="#item.yzb620"/>' filed='<s:property value="#item.yzb623"/>'
                                   <s:if test="#item.yzb623==#_and['yzb733']">selected="selected"</s:if>>
			                   <s:property value="#item.yzb625"/>
			               </option>
                       </s:iterator>
			          </select>
                    </s:if>
			   </span>
                                    <span class='swichNr' onclick='fnSwichValueType(this)' title='点击可切换选择项目或输入值'></span>
                                </td>
                            </tr>
                        </s:iterator>

                        <tr>
                            <td style="width:30px;text-align: left;" colspan="4">
                                <ta:button key="增加条件" onClick="addCondition(this)" cssClass="btnadd"/>
                                <ta:button key="删除该条件组" onClick="deleteCondition(this)" cssClass="btndelete"/>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </s:iterator>
            </s:if>
            <!-- 没统计条件时默认生成一个组 -->
            <s:else>
                <table id="datagrid_query_id" width="100%" align="center" cellpadding="2" cellspacing="0" border="0">
                    <tbody>
                    <tr id="datagrid_title_id">
                        <td style="width:30px;">
                            操作
                        </td>
                        <td style="width:80px;">
                            项目名称
                        </td>
                        <td style="width:50px;">
                            运算符
                        </td>
                        <td style="width: 70%">
                            内容
                        </td>
                    </tr>
                    <tr>
                        <td style="width:30px;">
                            <a href="javascript:void(0);" onclick="deleteTr(this)">删除</a>
                        </td>
                        <td style="width:50px;">
                            <select name="cxxm" style="width:97%;" onchange="getItemGxOrNr(this)">
                                <option value=''>---请选择---</option>
                                <s:iterator value="resultBean.fieldData['cxxms']" id="item1" status="st">
                                    <option value='<s:property value="#item1.yzb620"/>'
                                            filed='<s:property value="#item1.yzb623"/>'>
                                        <s:property value="#item1.yzb625"/>
                                    </option>
                                </s:iterator>
                            </select>
                        </td>
                        <td style="width:50px;">
                            <select name="gxysf" style="width:97%;">
                                <option value=''>---请选择---</option>
                            </select>
                        </td>
                        <td id='nr_td_id' style="text-align: left;padding-left: 5px;width: 70%">
		   <span id='nrz_span_id' style='display:inline;width:98%;'>
		     <input type="text" name='nrz' style="width: 200px;"/>
		     <input name='vtype' value='1' type='hidden'/>
		   </span>
                            <span id='nr_item_select_id' style='display:none;'></span>
                            <span class='swichNr' onclick='fnSwichValueType(this)' title='点击可切换选择项目或输入值'></span>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:30px;text-align: left;" colspan="4">
                            <ta:button key="增加条件" onClick="addCondition(this)" cssClass="add"/>
                            <ta:button key="删除该条件组" onClick="deleteCondition(this)" cssClass="delete"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </s:else>


        </div>
    </div>
    <ta:box cols="5" cssStyle="display:none">

        <div class="ez-fl ez-negmx" style="width:54.0%;">
            <div id="tafieldset_49793242" layout="column" span="2.7">
                <div class="tafieldset_header ">
                    <h2>分组项</h2>&nbsp;【<a href="javascript:void(0);" title='设置分组统计结果的先后顺序'
                                          onclick="fnGroupItemsOrder()">分组项排序</a>】
                </div>
                <div id="groupItems_id_box" class="tafieldset_163">
                    <div id="groupByItemsId">
                        <s:iterator value="resultBean.fieldData.fzxms" id="item" status="st">
                            <div>
                                <input type="checkbox" name="tjxm"
                                       datatype="<s:property value="#item.yzb62a"/>"
                                       fname="<s:property value="#item.yzb625" />"
                                       ztxmlsh="<s:property value="#item.yzb620" />"
                                       value="<s:property value="#item.yzb623" />"
                                        <s:if test="#item.cheked == 1"> checked="checked" </s:if>/>
                                <s:property value="#item.yzb625"/>
                            </div>
                        </s:iterator>
                    </div>
                    <div style="clear:both"></div>
                </div>
            </div>
        </div>

        <ta:box span="0.15">
            <div style="width:100%;height:50px;"></div>
        </ta:box>

        <ta:fieldset key="统计指标" span="2.15" id="tjzb_id">
            <div style="height:158px;width:100%;overflow:auto;">
                <table id="datagrid_query_id" width="100%" align="center" cellpadding="2" cellspacing="0" border="0">
                    <tbody>
                    <tr id="datagrid_title_id">
                        <td style="width:50px;">
                            操作
                        </td>
                        <td style="width:250px;">
                            项目名称
                        </td>
                        <td style="width: 45%">
                            统计值
                        </td>
                    </tr>

                    <s:iterator value="#request.tjlx_rows" id="item_row" status="st">
                        <tr>
                            <td style="width:50px;">
                                <a href="javascript:void(0);" onclick="deleteTjzbhsTr(this)">删除</a>
                            </td>
                            <td style="width:250px;">
                                <select name="tjzb" style="width:97%;" onchange="getXmzcys(this)">
                                    <option value=''>---请选择---</option>
                                    <s:iterator value="#item_row['tjxms']" id="item" status="st">
                                        <option value='<s:property value="#item.yzb620"/>'
                                                <s:if test="#item.yzb620 == #item_row['yzb620']">selected="selected"</s:if>
                                                filed='<s:property value="#item.yzb623"/>'
                                                dbtype='<s:property value="#item.yzb626"/>'
                                                datatype='<s:property value="#item.yzb62a"/>'>
                                            <s:property value="#item.yzb625"/>
                                        </option>
                                    </s:iterator>
                                </select>
                            </td>
                            <td style="width:45%">
                                <select name="tjzbhs" style="width:97%;">
                                    <s:iterator value="#item_row['tjlxs']" id="item" status="st">
                                        <option value='<s:property value="#item.yzb641"/>'
                                                <s:if test="#item.yzb641 == #item_row['yzb641']"> selected="selected"</s:if>>
                                            <s:property value="#item.yzb641_desc"/>
                                        </option>
                                    </s:iterator>
                                </select>
                            </td>
                        </tr>
                    </s:iterator>

                    <tr>
                        <td style="width:30px;text-align: left;" colspan="3">
                            <ta:button key="增加统计指标" onClick="addTjzbhsTr(this)"/>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </ta:fieldset>
    </ta:box>
    <ta:box cols="2" cssStyle="display:none">
        <ta:box span="1">
            <ta:radiogroup id="showTypes" span="1" cols="4" cssStyle="float:right;">
                <ta:radio value="1" onClick="fnQuery()" key="表格&nbsp;&nbsp;" checked="true"></ta:radio>
                <ta:radio value="2" onClick="fnQuery()" key="柱状图" cssStyle="padding-right:40px;"></ta:radio>
                <ta:radio value="3" onClick="fnQuery()" key="饼图"></ta:radio>
                <ta:radio value="4" onClick="fnQuery()" key="折线图"></ta:radio>
            </ta:radiogroup>
        </ta:box>
        <ta:box span="1" cssStyle="margin-left:10px;margin-top:7px;">
            <ta:button key="&nbsp;&nbsp;&nbsp;&nbsp;统&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计&nbsp;&nbsp;&nbsp;&nbsp;"
                       onClick="fnQuery()"/>
        </ta:box>
    </ta:box>
</ta:form>

<div style="text-align: center"><ta:button id="myquery" key="查询" onClick="fnQueryDetailInfo()"
                                           cssClass="btnmodify"/></div>

<!-- 统计信息结果datagrid -->
<ta:fieldset id="statisticalInfoId" key="统计结果<span style=color:red;>(鼠标点击统计值，可反查明细)</span>" cssStyle="display:none">
    <ta:datagrid id="queryResultDgd" forceFitColumns="true" height="365px" haveSn="true">
        <ta:datagridItem id="tjsjts" sortable="true"
                         key="<span style=color:red;float:left>请先输入统计条件，选择统计项，统计指标，然后再点击“统计”按钮进行统计。</span>"/>
        <ta:dataGridToolPaging url="customizeQueryAction!query.do" pageSize="10" showExcel="false"/>
    </ta:datagrid>
    <div id='chartsId' style="display:none;"></div>
</ta:fieldset>

<!-- 点击数字时生成带参表单html -->
<div id="detailInfoParasId"></div>

<!-- 点击详细信息datagrid生成列头表单html -->
<div id="detailInfoFiledTitleId">
    <s:iterator value="#request.mxxsxms" id="item" status="st">
        <input type="hidden" name="detailShowFileds"
               <s:if test="#item.yzb761 != null && #item.yzb761 != ''">sn='<s:property value="#item.yzb761"/>'
        </s:if>
               <s:else>sn='<s:property value="#st.index + 1"/>'
        </s:else>
               filedname='<s:property value="#item.yzb625"/>'
               dbtype='<s:property value="#item.yzb626"/>'
               datatype='<s:property value="#item.yzb62a"/>'
               id='<s:property value="#item.yzb624"/>'
               ztxmlsh='<s:property value="#item.yzb620"/>'
               ischecked='<s:property value="#item.ischecked"/>'
               tjfs='<s:property value="#item.yzb641"/>'/>
    </s:iterator>
</div>

<!-- 点击详细信息生成列头排序html -->
<div id="detailInfoFiledOrderId">
    <s:iterator value="#request.mxpxfss" id="item" status="st">
        <input type="hidden" name="detailOrderFileds"
               sn='<s:property value="#item.yzb771"/>'
               filedname='<s:property value="#item.yzb625"/>'
               dbtype='<s:property value="#item.yzb626"/>'
               datatype='<s:property value="#item.yzb62a"/>'
               id='<s:property value="#item.yzb624"/>'
               ztxmlsh='<s:property value="#item.yzb620"/>'
               pxfs='<s:property value="#item.yzb652"/>'/>
    </s:iterator>
</div>

<!-- 详细信息datagrid -->
<div id="deailInfoId" layout="column">
    <div class="tafieldset_header" style="position:relative;background-color: white;margin-top: 6px;">
        <div class="header-bg"></div>
        <h2 style="font-size: 16px;float: left;margin-left:10px;">详细信息</h2>
        <span class="to_right" style="line-height:28px;">
	    <ta:buttonLayout align="right">
            <ta:button key="选择显示项目" onClick="fnOpenSetDetailShowFiledWindows()" cssClass="btnmodify"/>
            <ta:button key="定义排序方式" onClick="fnOpenSetDetailOrderFiledWindows()" cssClass="btnmodify"/>
            <ta:button key="导出查询结果" onClick="fnExportDetail()" cssClass="btnmodify"/>
        </ta:buttonLayout>
        <div id="query" class="slick-export-content exports2">
            <div class="slick-export-item faceIcon icon-excel exports_dangqian_" title="导出当前页">导出当前页</div>
            <%--<div  class="slick-export-item faceIcon icon-excel exports_xuanze_" title="导出选择数据">导出选择数据</div>--%>
            <div class="slick-export-item faceIcon icon-excel exports_quanbu_" title="导出全部数据">导出全部数据</div>
        </div>
	</span>
    </div>
    <ta:datagrid id="deailInfoDgd" height="365px" haveSn="true" forceFitColumns="true">
        <ta:dataGridToolPaging url="customizeQueryAction!queryDetail.do" pageSize="10" showExcel="false"/>
    </ta:datagrid>
</div>
</body>
</html>
<script type="text/javascript">
    var noChckedGroupFiled;//存放未选择分组字段
    var queryConditionXml; //存放查询条件的xml
    //获取页面的分组字段json数据
    function fnGetGroupByFiledJson() {
        noChckedGroupFiled = [];
        var json = "[", _o_ = null;
        $.each($("input[name='tjxm']"), function (sn, _o) {
            _o_ = {};
            _o_['sn'] = ++sn;
            _o_['filedname'] = ($(_o).attr('fname') || '');
            _o_['ztxmlsh'] = ($(_o).attr('ztxmlsh') || '');
            _o_['datatype'] = ($(_o).attr('datatype') || '');
            _o_['id'] = ($(_o).attr('value') || '');
            if ($(_o).is(':checked')) {
                _o_['checked'] = true;
                json += JSON.stringify(_o_);
            } else {
                _o_['checked'] = false;
                noChckedGroupFiled.push(_o_);
            }
        });
        json += "]";
        return json.replaceAll("\"", "'");
    }

    //生成分组字段平铺复选框
    function fnGenerateGroupByFiledsHtml(_o_) {
        if (_o_) {
            if (noChckedGroupFiled) _o_ = _o_.concat(noChckedGroupFiled);
            var str = "";
            $.each(_o_, function (sn, _o) {
                str += "<div>";
                str += "<input type=\"checkbox\"  name=\"tjxm\"";
                str += " datatype='" + _o.datatype + "'";
                str += " fname='" + _o.filedname + "'";
                str += " ztxmlsh='" + _o.ztxmlsh + "'";
                str += " value='" + _o.id + "'";
                if (_o.checked) {
                    str += "checked=\"checked\"";
                }
                str += "/>" + _o.filedname || '';
                str += "</div>";
            });
            $("#groupByItemsId").html(str);
        }
    }

    //分组项排序弹框
    function fnGroupItemsOrder() {
        var json = fnGetGroupByFiledJson();
        var _id = 'setGroupByFiledWindows';
        var _title = "分组项排序";
        var _url = '${basePath}/query/customizeQueryAction!toSetGroupByFiledOrder.do?dto.json=' + encodeURI(encodeURI(json));
        var _w = "600";
        var _h = "300";
        var _load = null;
        var _iframe = true;
        //alert(json)
        Base.openWindow(_id, _title, _url, null, _w, _h, _load, function (data) {
            //fnCommonQueryDetailInfo();//执行明细查询
        }, _iframe);
    }

    //获取保存数据的xml
    function getSaveDataXml() {
        var ztdm = Base.getValue('ztdm') || '';//主题代码
        var tjfalsh = Base.getValue('tjfalsh') || '';//方案流水号
        var tjfaname = Base.getValue('tjfaname') || '';//方案名称
        var jctj = Base.getValue('jctj') || '';//基础条件值
        jctj = jctj.replaceAll('"', "'");//change by zhaohs
        var ztlsh = "";
        var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += "<scheme ztdm=\"" + ztdm + "\" ztlsh=\"" + ztlsh + "\" tjfalsh=\"" + tjfalsh + "\" jctj=\"" + jctj + "\" tjfaname=\"" + tjfaname + "\">";
        xml += "<statistics_query>";
        xml += "<fields>";
        xml += fnQueryFileds();
        xml += fnQueryShowFileds();//详细信息、弹框设置的字段
        xml += "</fields>";
        xml += "<where>";
        xml += "<ands>";
        //xml += fnQueryAnds();//点击数字参数
        xml += "</ands>";
        xml += "<ors>";
        xml += fnQueryOrs();
        xml += "</ors>";
        xml += "</where>";
        xml += fnQueryOrders();//排序xml
        xml += "</statistics_query>";
        xml += "</scheme>";
        return xml;
    }

    //保存统计方案
    function fnSaveTjfa(_o) {
        if (_o) {
            if (_o.tjfalsh) {//表示更新
                Base.setValue('tjfalsh', _o.tjfalsh);
                Base.setValue('tjfaname', "");
            } else if (_o.tjfaname) {//表示新增
                Base.setValue('tjfalsh', "");
                Base.setValue('tjfaname', _o.tjfaname);
            }
            var xml = getSaveDataXml();
            Base.submit("tjfalsh", "${basePath}/query/customizeQueryAction!save.do", {'dto.xml': xml}, null, null, function (data) {
            }, null);
        }
    }

    //到保存统计方案页面
    function fnToSaveTjfa() {
        var _id = 'saveTjfaWindows';
        var _title = "保存统计方案";
        var _url = '${basePath}/query/customizeQueryAction!toSaveTjfa.do';
        var _w = "600";
        var _h = "450";
        var _load = null;
        var _iframe = true;
        Base.openWindow(_id, _title, _url, {
            "dto.yzb617": Base.getValue("yzb617"),
            "dto.yzb610": Base.getValue("yzb610")
        }, _w, _h, _load, function (data) {
        }, _iframe);
    }

    //调出统计方案
    function fnCalloutTjfa(_o) {
        var jctj = Base.getValue('jctj');
        if (_o) {
            if (_o.tjfalsh) {
                jctj = encodeURI(encodeURI(jctj));
                window.location.href = "${basePath}/query/customizeQueryAction!callout.do?dto.tjfalsh=" + _o.tjfalsh + "&dto.jctj=" + jctj + "&dto.yzb610=" + _o.yzb610 + "&dto.tjfaname=" + _o.tjfaname;
            } else {
                Base.alert("请先选择已有统计方案！");
            }
        }
    }

    //到调出统计方案页面
    function fnToCalloutTjfa() {
        var _id = 'calloutTjfaWindows';
        var _title = "调出统计方案";
        var _url = '${basePath}/query/customizeQueryAction!toCalloutTjfa.do';
        var _w = "600";
        var _h = "350";
        var _load = null;
        var _iframe = true;
        Base.openWindow(_id, _title, _url, {
            "dto.yzb617": Base.getValue("yzb617"),
            "dto.yzb610": Base.getValue("yzb610")
        }, _w, _h, _load, function (data) {
        }, _iframe);
    }

    //生成统计信息datagrid title
    function fnGenerateStatisticalDataGridTitle(datagirdId, data) {
        var grid = Ta.core.TaUIManager.getCmp(datagirdId), columns = [];
        columns.push(grid.getColumns()[0]);
        $.each(data.fieldData.fileds || {}, function (sn, _o) {
            if (_o) {
                var titleName = _o.name, filedName = _o.id.toLowerCase();
                if (filedName) {//生成统计数红色titile
                    if (filedName.indexOf('_fn') != -1) {
                        titleName = "<font color=red>" + titleName + "</font>";
                    }
                    ;
                }
                var _filed = {
                    'name': titleName,
                    'field': filedName,
                    'id': filedName,
                    'dataAlign': 'center',
                    'sortable': true,
                    'possation': ++sn
                };
                if (_o.datatype && _o.datatype != '') {
                    if (Number(_o.datatype) == 12) {//年月
                        _filed.formatter = function (row, cell, value, columnDef, dataContext) {
                            value = value || '';
                            if (value != '') {
                                var strs = value.split('-');
                                value = strs[0] + '-' + strs[1]
                            }
                            return value;
                        }
                    } else if (Number(_o.datatype) == 13) {//日期
                        _filed.dataType = "date";
                    } else if (Number(_o.datatype) == 14) {//数字
                        _filed.dataType = "number";
                    } else if (Number(_o.datatype) == 21 || Number(_o.datatype) == 22 || Number(_o.datatype) == 23) {//代码值平铺,树,datagrid
                        _filed.formatter = function (row, cell, value, columnDef, dataContext) {
                            var reData = value, data = eval(_o.collectionData), o = {};
                            data.column = filedName;
                            o.collectionsDataArrayObject = {};
                            o.collectionsDataArrayObject[filedName] = data;
                            for (var i = 0; i < data.length; i++) {
                                if (data[i].id == value) {
                                    reData = data[i].name;
                                }
                            }
                            return reData ? reData : "";
                        }
                        _filed.showDetailed = true;
                    }
                } else {//统计列渲染(数字超链接)
                    _filed.formatter = function (row, cell, value, columnDef, dataContext) {
                        dataContext = JSON.stringify(dataContext).replaceAll("\"", "'");
                        if (value != 'null' && value != null) {
                            var _id = columnDef.field;
                            if (filedName.indexOf('_fn') != -1) {
                                value = "<a href=\"javascript:void(0)\" onclick=\"fnGenerateDetailInfoParasHtml(" + dataContext + ")\">" + value + "</a>";
                            }
                        } else {
                            return '';
                        }
                        return value;
                    }
                }
                columns.push(_filed);
            }
        });
        grid.setColumns(columns);
    }

    //生成详细信息datagrid title
    function fnGenerateDetailDataGridTitle(datagirdId, data) {
        var grid = Ta.core.TaUIManager.getCmp(datagirdId), columns = [];
        columns.push(grid.getColumns()[0]);
        $.each(data.fieldData.fileds || {}, function (sn, _o) {
            if (_o) {
                var titleName = _o.name, filedName = _o.id.toLowerCase(), ischecked = _o.ischecked;
                if (ischecked == 'true') {
                    var _filed = {
                        'name': titleName,
                        'field': filedName,
                        'id': filedName,
                        'dataAlign': 'center',
                        'sortable': true,
                        'possation': ++sn
                    };
                    if (_o.datatype && _o.datatype != '') {
                        if (Number(_o.datatype) == 12) {//年月
                            _filed.formatter = function (row, cell, value, columnDef, dataContext) {
                                value = value || '';
                                if (value != '') {
                                    var strs = value.split('-');
                                    value = strs[0] + '-' + strs[1]
                                }
                                return value;
                            }
                        } else if (Number(_o.datatype) == 13) {//日期
                            _filed.dataType = "date";
                        } else if (Number(_o.datatype) == 14) {//数字
                            _filed.dataType = "number";
                        } else if (Number(_o.datatype) == 21 || Number(_o.datatype) == 22 || Number(_o.datatype) == 23) {//代码值平铺,树,datagrid
                            _filed.formatter = function (row, cell, value, columnDef, dataContext) {
                                var reData = value, data = eval(_o.collectionData), o = {};
                                data.column = filedName;
                                o.collectionsDataArrayObject = {};
                                o.collectionsDataArrayObject[filedName] = data;
                                for (var i = 0; i < data.length; i++) {
                                    if (data[i].id == value) {
                                        reData = data[i].name;
                                    }
                                }
                                return reData ? reData : "";
                            }
                            _filed.showDetailed = true;
                        }

                    }
                    columns.push(_filed);
                }
            }
        });
        grid.setColumns(columns);
    }

    //根据码值获取码中文
    function fnGetCodeDesc(c, v) {
        if (c && c.length > 0) {
            for (var i = 0; i < c.length; i++) {
                if (c[i].id == v) {
                    v = c[i].name;
                }
            }
        }
        return (v == null ? "其它" : v);
    }

    //验证选择项目(针对图表统计)
    function fnValidXm() {
        //分组字段
        var _tjxms = $("input[name='tjxm']:checked");
        if (_tjxms.length == 0) {
            Base.alert('必需选择一项【分组项】进行图表统计');
            return false;
        } else if (_tjxms.length >= 2) {
            Base.alert('只能选择一项【分组项】进行图表统计');
            return false;
        }

        //统计函数字段
        var _tjzbhs = $("select[name='tjzbhs']");//统计函数
        var _count = 0;
        $.each($("select[name='tjzb']"), function (sn, _o) {//统计指标
            _o = $(_o);
            var ztxmlsh = $(_o).val() || '';
            if (ztxmlsh != '') {
                var _select = $(_tjzbhs[sn]);
                var tjfs = _select.val() || '';
                if (tjfs != '') {
                    _count++;
                }
            }
        });
        if (_count == 0) {
            Base.alert('必需选择一项【统计指标】进行图表统计');
            return false;
        } else if (_count >= 2) {
            Base.alert('只能选择一项【统计指标】进行图表统计');
            return false;
        }
        return true;
    }

    //点击统计查询（统计）
    function fnQuery() {
        var ztdm = Base.getValue('ztdm');
        var yzb612 = Base.getValue('yzb612');
        var ztlsh = "";
        var tjfalsh = Base.getValue('tjfalsh') || '';//方案流水号
        var tjfaname = Base.getValue('tjfaname') || '';//方案名称
        var jctj = Base.getValue('jctj') || '';//基础条件值
        var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += "<scheme ztdm=\"" + ztdm + "\" ztlsh=\"" + ztlsh + "\" tjfalsh=\"" + tjfalsh + "\" jctj=\"" + jctj + "\" tjfaname=\"" + tjfaname + "\">";
        xml += "<statistics_query>";
        xml += "<fields>";
        xml += fnQueryFileds();
        xml += "</fields>";
        xml += "<where>";
        xml += "<ors>";
        queryConditionXml = fnQueryOrs();//全局存放(解决查看明细数据前修改查询条件--数据变化)
        xml += queryConditionXml;
        xml += "</ors>";
        xml += "</where>";
        xml += "</statistics_query>";
        xml += "</scheme>";
        var showType = $("input[name=\"dto[\'showTypes\']\"]:checked").val() || '0';
        if (showType == '2' || showType == '3' || showType == '4') {//图表类型
            //判断分组项及统计指标
            if (!fnValidXm()) {
                return;
            }
            var _url = "${basePath}/query/customizeQueryAction!queryPage.do";
            Base.submit(null, _url, {
                'dto.xml': xml,
                'gridInfo.queryResultDgd_start': 0,
                'gridInfo.queryResultDgd_limit': tbtjPageSize,
                'dto.yzb612': yzb612,
                'dto.tjfaname': tjfaname
            }, null, null, function (data) {
                var _title = data.fieldData.fileds;         //title
                var _data = data.fieldData.queryResultDgd; //数据
                (function (fn) {
                    (require(["jquery", "echarts-all"], fn));
                }(function ($) {
                    $("#queryResultDgd").hide();
                    $('#chartsId').attr("style", "width:100%;height:300px;").show();
                    var myChart = echarts.init(document.getElementById('chartsId'));
                    var option = null;
                    if (showType == '2') {//柱状图
                        var tb_data_title = [], tb_data_value = [], title_key = '', value_key = '', collectionData = '',
                            tjlx = '', count = 0;
                        if (_title && _title.length == 2) {
                            title_key = _title[0]['id'];//分类key
                            if (title_key) {
                                title_key = title_key.toLowerCase();
                            }
                            value_key = _title[1]['id'];//值key
                            if (value_key) {
                                value_key = value_key.toLowerCase();
                            }
                            collectionData = _title[0]['collectionData'];//码列表
                            if (collectionData) {
                                collectionData = eval(collectionData);
                            }
                            if (_data && _data.length > 0) {
                                for (var i = 0; i < _data.length; i++) {
                                    tb_data_title.push(fnGetCodeDesc(collectionData, _data[i][title_key]));
                                    count += Number(_data[i][value_key]);
                                    tb_data_value.push(_data[i][value_key]);
                                }
                            }
                        }
                        tjlx = [_title[1]['name'].split('：')[0]] + "(总数:" + count + ")";//统计指标
                        option = {
                            tooltip: {},
                            color: ['#3398DB'],
                            legend: {data: [tjlx]},
                            xAxis: {data: tb_data_title},
                            yAxis: {},
                            series: [{type: 'bar', data: tb_data_value}]
                        };
                    } else if (showType == '3') {//饼图
                        var tb_data = [], title_key = '', value_key = '', collectionData = '', tjlx = '', count = 0,
                            tbxm_title = [];
                        if (_title && _title.length == 2) {
                            title_key = _title[0]['id'];//分类key
                            if (title_key) {
                                title_key = title_key.toLowerCase();
                            }
                            value_key = _title[1]['id'];//值key
                            if (value_key) {
                                value_key = value_key.toLowerCase();
                            }
                            collectionData = _title[0]['collectionData'];//码列表
                            if (collectionData) {
                                collectionData = eval(collectionData);
                            }

                            tjlx = _title[1]['name'].split('：')[0];//统计指标

                            if (_data && _data.length > 0) {
                                for (var i = 0; i < _data.length; i++) {
                                    var tbTitle = fnGetCodeDesc(collectionData, _data[i][title_key]);
                                    tbxm_title.push(tbTitle);
                                    tb_data.push({'value': _data[i][value_key], 'name': tbTitle});
                                }
                            }
                        }
                        option = {
                            tooltip: {trigger: 'item', formatter: "{a} <br/>{b} : {c} ({d}%)"},
                            legend: {left: 'left', data: tbxm_title},
                            series: [{name: tjlx, type: 'pie', radius: '55%', center: ['50%', '60%'], data: tb_data}]
                        };

                    } else if (showType == '4') {//线图

                        var tb_data_title = [], tb_data_value = [], title_key = '', value_key = '', collectionData = '',
                            count = 0, tjlx = '';
                        if (_title && _title.length == 2) {
                            title_key = _title[0]['id'];//分类key
                            if (title_key) {
                                title_key = title_key.toLowerCase();
                            }
                            value_key = _title[1]['id'];//值key
                            if (value_key) {
                                value_key = value_key.toLowerCase();
                            }
                            collectionData = _title[0]['collectionData'];//码列表
                            if (collectionData) {
                                collectionData = eval(collectionData);
                            }
                            if (_data && _data.length > 0) {
                                for (var i = 0; i < _data.length; i++) {
                                    tb_data_title.push(fnGetCodeDesc(collectionData, _data[i][title_key]));
                                    count += Number(_data[i][value_key]);
                                    tb_data_value.push(_data[i][value_key]);
                                }
                            }
                        }
                        tjlx = _title[1]['name'].split('：')[0];//统计指标
                        option = {
                            tooltip: {trigger: 'axis'},
                            legend: {data: [tjlx], y: "top"},
                            //工具箱配置
                            /**
                             toolbox: {
				                    show : true,  
				                    feature : {  
				                        mark : {show: true}, // 辅助线标志，上图icon左数1/2/3，分别是启用，删除上一条，删除全部  
				                        dataView : {show: true, readOnly: false},// 数据视图，上图icon左数8，打开数据视图  
				                        magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},// 图表类型切换，当前仅支持直角系下的折线图、柱状图转换，上图icon左数6/7，分别是切换折线图，切换柱形图  
				                        restore : {show: true}, // 还原，复位原始图表，上图icon左数9，还原  
				                        saveAsImage : {show: true} // 保存为图片，上图icon左数10，保存  
				                    }  
				                },
                             **/
                            calculable: true,
                            xAxis: [{type: 'category', data: tb_data_title}],
                            yAxis: [{type: 'value', splitArea: {show: true}}],
                            series: [{name: tjlx, type: 'line', data: tb_data_value}]
                        };
                    }
                    myChart.setOption(option);
                }));

            }, null);


        } else {//默认表格显示
            $("#chartsId").hide();
            $("#queryResultDgd").show();

            var _url = "${basePath}/query/customizeQueryAction!queryPage.do";
            Base.submit("queryResultDgd", _url, {
                'dto.xml': xml,
                'dto.yzb612': yzb612,
                'dto.yzb711': tjfaname
            }, null, null, function (data) {
                fnGenerateStatisticalDataGridTitle("queryResultDgd", data);      //生成title
                var grid = Ta.core.TaUIManager.getCmp("queryResultDgd");

                //分页数据处理
                var _pager = grid.getPager();
                _pager.setPagerUrl(_url);
                if (_pager.setExeQueryFn) {
                    _pager.setExeQueryFn(fnQuery);
                }

                //设置datagrid数据
                if (grid) {
                    var dataView = grid.getDataView();
                    grid.clearDirtyWidthOutPager();
                    dataView.setItems(data.fieldData.queryResultDgd || {});
                    _pager.setStatus((data.fieldData.datacount || 0));
                    dataView.refresh();
                    grid.refreshGrid();
                }
            }, null);
        }
        $("#deailInfoId").hide();
    }


    //获取点击数字后的xml || 关闭显示字段设置窗口 || 关闭排序字段设置窗口
    function getDetailInfoXml() {
        var ztdm = Base.getValue('ztdm') || '';//主题代码
        var tjfalsh = Base.getValue('tjfalsh') || '';//方案流水号
        var tjfaname = Base.getValue('tjfaname') || '';//方案名称
        var jctj = Base.getValue('jctj') || '';//基础条件值
        jctj = jctj.replaceAll('"', "'");//change by zhaohs
        var ztlsh = "";
        var xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += "<scheme ztdm=\"" + ztdm + "\" ztlsh=\"" + ztlsh + "\" tjfalsh=\"" + tjfalsh + "\" jctj=\"" + jctj + "\" tjfaname=\"" + tjfaname + "\">";
        xml += "<statistics_query>";
        xml += "<fields>";
        xml += fnQueryShowFileds();//详细信息、弹框设置的字段
        xml += "</fields>";
        xml += "<where>";
        xml += "<ands>";
        xml += fnQueryAnds();//点击数字参数
        xml += "</ands>";
        xml += "<ors>";
        if (queryConditionXml) {//有数据直接拼(防止修改查询条件)
            xml += queryConditionXml;
        } else {
            xml += fnQueryOrs();
        }
        xml += "</ors>";
        xml += "</where>";
        xml += fnQueryOrders();//排序xml
        xml += "</statistics_query>";
        xml += "</scheme>";
        return xml;
    }

    //生成详细信息,弹框设置的字段(包括选择的统计方式)xml
    function fnQueryShowFileds() {
        //分组字段
        var xml = "<show_field>";
        $.each($("input[name='detailShowFileds']"), function (sn, _o) {
            _o = $(_o);
            xml += "<field  name=\"" + (_o.attr('filedname') || '');
            xml += "\" id=\"" + (_o.attr('id') || '');
            xml += "\" sn=\"" + (_o.attr('sn') || '');
            xml += "\" ztxmlsh=\"" + (_o.attr('ztxmlsh') || '');
            xml += "\" ischecked=\"" + (_o.attr('ischecked') || '');
            xml += "\" tjfs=\"" + (_o.attr('tjfs') || '');
            xml += "\" dbtype=\"" + (_o.attr('dbtype') || '');
            xml += "\" datatype=\"" + (_o.attr('datatype') || '');
            xml += "\"/>";
        });
        xml += "</show_field>";
        return xml;
    }

    //点击数字查看详细信息
    function fnQueryDetailInfo() {
        var xml = getDetailInfoXml();
        var yzb612 = Base.getValue('yzb612');
        var yzb670 = Base.getValue('yzb670');
        var tjfaname = Base.getValue('tjfaname') || '';//方案名称
        var _url = "${basePath}/query/customizeQueryAction!queryDetail.do";
        Base.submit("deailInfoDgd", _url, {
            'dto.xml': xml,
            'dto.yzb612': yzb612,
            'dto.yzb711': tjfaname,
            'dto.yzb670': yzb670
        }, null, null, function (data) {
            this.fnGenerateDetailDataGridTitle("deailInfoDgd", data);      //生成title
            var grid = Ta.core.TaUIManager.getCmp("deailInfoDgd");

            //分页数据处理
            var _pager = grid.getPager();
            _pager.setPagerUrl(_url);
            if (_pager.setExeQueryFn) {
                _pager.setExeQueryFn(fnQueryDetailInfo);
            }

            //设置datagrid数据
            if (grid) {
                var dataView = grid.getDataView();
                grid.clearDirtyWidthOutPager();
                dataView.setItems(data.fieldData.deailInfoDgd || {});
                _pager.setStatus((data.fieldData.datacount || 0));
                dataView.refresh();
                grid.refreshGrid();
            }
            fnGenerateDetailShowFiledsHtml(data.fieldData || {}); //生成明细显示项目表单
            fnGenerateDetailOrdersFiledsHtml(data.fieldData || {}); //生成明细排序项目表单

        }, null);
        $("#deailInfoId").show();//显示详细信息面板
    }

    //关闭设置显示字段窗口或关闭设置排序 触发执行查询
    function fnCommonQueryDetailInfo() {
        var xml = getDetailInfoXml();
        var yzb670 = Base.getValue('yzb670');
        var _url = "${basePath}/query/customizeQueryAction!queryDetail.do";
        Base.submit("deailInfoDgd", _url, {'dto.xml': xml, 'dto.yzb670': yzb670}, null, null, function (data) {
            this.fnGenerateDetailDataGridTitle("deailInfoDgd", data);               //生成title
            var grid = Ta.core.TaUIManager.getCmp("deailInfoDgd");

            //分页数据处理
            var _pager = grid.getPager();
            _pager.setPagerUrl(_url);
            if (_pager.setExeQueryFn) {
                _pager.setExeQueryFn(fnCommonQueryDetailInfo);
            }

            //设置datagrid数据
            if (grid) {
                var dataView = grid.getDataView();
                grid.clearDirtyWidthOutPager();
                dataView.setItems(data.fieldData.deailInfoDgd || {});
                _pager.setStatus((data.fieldData.datacount || 0));
                dataView.refresh();
                grid.refreshGrid();
            }
        }, null);
    }

    //将表头字段作为全局变量 供子窗口使用 openwindow 传参大小限制
    var dgHeadJson;

    //打开设置显示字段窗口
    function fnOpenSetDetailShowFiledWindows() {
        dgHeadJson = fnGetDetailShowFiledJson();
        var _id = 'setDetailShowFiledWindows';
        var _title = "选择显示项目";
        var _url = '${basePath}/query/customizeQueryAction!toSetDetailFiledShow.do';
        var _w = "700";
        var _h = "400";
        var _load = null;
        var _iframe = true;
        Base.openWindow(_id, _title, _url, {}, _w, _h, _load, function (data) {
            fnCommonQueryDetailInfo();//执行明细查询
        }, _iframe);
    }

    //获取明细统计行数据
    function fnGetStatisticalRow(_id, _o) {
        _o = _o.fieldData;
        var _tjdata = _o.tjdata, _tjcodes = _o.tjcodes, _o_ = null;
        if (_id && _tjdata) {
            _o_ = {};
            for (attr in _tjdata) {
                _o_[attr] = _tjcodes[attr] + ":" + _tjdata[attr];
            }
        }
        return _o_;
    }

    //打开设置显示字段窗口前 获取设置字段json串
    function fnGetDetailShowFiledJson() {
        var json = "[", _o_ = null;
        $.each($("input[name='detailShowFileds']"), function (sn, _o) {
            _o_ = {};
            _o_['sn'] = ($(_o).attr('sn') || '');
            _o_['filedname'] = ($(_o).attr('filedname') || '');
            _o_['id'] = ($(_o).attr('id') || '');
            _o_['ztxmlsh'] = ($(_o).attr('ztxmlsh') || '');
            _o_['tjfs'] = ($(_o).attr('tjfs') || '');
            _o_['dbtype'] = ($(_o).attr('dbtype') || '');
            _o_['datatype'] = ($(_o).attr('datatype') || '');
            _o_['ischecked'] = ($(_o).attr('ischecked') || '');
            json += JSON.stringify(_o_);
        });
        json += "]";
        return json.replaceAll("\"", "'");
    }

    //打开设置排序字段窗口前 获取排序字段json串
    function fnGetDetailOrderFiledJson() {
        var json = "[", _o_ = null;
        $.each($("input[name='detailOrderFileds']"), function (sn, _o) {
            _o_ = {};
            _o_['sn'] = ($(_o).attr('sn') || '');
            _o_['filedname'] = ($(_o).attr('filedname') || '');
            _o_['id'] = ($(_o).attr('id') || '');
            _o_['ztxmlsh'] = ($(_o).attr('ztxmlsh') || '');
            _o_['pxfs'] = ($(_o).attr('pxfs') || '');      //排序方式
            _o_['dbtype'] = ($(_o).attr('dbtype') || '');
            _o_['datatype'] = ($(_o).attr('datatype') || '');
            json += JSON.stringify(_o_);
        });
        json += "]";
        return json.replaceAll("\"", "'");
    }

    //生成明细显示项目表单
    function fnGenerateDetailShowFiledsHtml(_o) {
        var detailShowFiledsHtml = $('#detailInfoFiledTitleId');
        detailShowFiledsHtml.html('');
        var hasSn = false;
        if (_o.fileds) {
            _o = _o.fileds;
        } else if (_o.data) {
            hasSn = true;
            _o = _o.data;
        } else {
            _o = {};
        }
        $.each(_o, function (sn, _o) {
            var str = "<input type=\"hidden\" name=\"detailShowFileds\"";
            if (hasSn) {
                str += "\" sn=\"" + (_o.sn || '');
            } else {
                str += "\" sn=\"" + ++sn;
            }
            str += "\" filedname=\"" + (_o.name || '');
            str += "\" dbtype=\"" + (_o.dbtype || '');
            str += "\" datatype=\"" + (_o.datatype || '');
            str += "\" id=\"" + (_o.id || '');
            str += "\" ztxmlsh=\"" + (_o.ztxmlsh || '');
            str += "\" ischecked=\"" + (_o.ischecked || '');
            str += "\" tjfs=\"" + (_o.tjfs || '');
            str += "\"/>";
            detailShowFiledsHtml.html(detailShowFiledsHtml.html() + str);
        });
    }

    //生成明细排序项目表单
    function fnGenerateDetailOrdersFiledsHtml(_o) {
        var detailOrderFiledsHtml = $('#detailInfoFiledOrderId');
        detailOrderFiledsHtml.html('');
        var hasSn = false;
        if (_o.order_fileds) {
            _o = _o.order_fileds;
        } else if (_o.data) {
            hasSn = true;
            _o = _o.data;
        } else {
            _o = {};
        }
        $.each(_o, function (sn, _o) {
            var str = "<input type=\"hidden\" name=\"detailOrderFileds\"";
            if (hasSn) {
                str += "\" sn=\"" + (_o.sn || '');
            } else {
                str += "\" sn=\"" + ++sn;
            }
            str += "\" filedname=\"" + (_o.name || '');
            str += "\" dbtype=\"" + (_o.dbtype || '');
            str += "\" datatype=\"" + (_o.datatype || '');
            str += "\" id=\"" + (_o.id || '');
            str += "\" ztxmlsh=\"" + (_o.ztxmlsh || '');
            str += "\" pxfs=\"" + (_o.pxfs || '');        //排序方式
            str += "\"/>";
            detailOrderFiledsHtml.html(detailOrderFiledsHtml.html() + str);
        });
    }

    //获取详细信息查询排序字段xml
    function fnQueryOrders() {
        //排序字段
        var xml = "<orders>";
        $.each($("input[name='detailOrderFileds']"), function (sn, _o) {
            _o = $(_o);
            xml += "<field  name=\"" + (_o.attr('filedname') || '');
            xml += "\" id=\"" + (_o.attr('id') || '');
            xml += "\" sn=\"" + (_o.attr('sn') || '');
            xml += "\" ztxmlsh=\"" + (_o.attr('ztxmlsh') || '');
            xml += "\" pxfs=\"" + (_o.attr('pxfs') || '');
            xml += "\" dbtype=\"" + (_o.attr('dbtype') || '');
            xml += "\" datatype=\"" + (_o.attr('datatype') || '');
            xml += "\"/>";
        });
        xml += "</orders>";
        return xml;
    }

    //打开设置详细排序字段窗口
    function fnOpenSetDetailOrderFiledWindows() {
        var json = fnGetDetailOrderFiledJson();
        var _id = 'setDetailOrderFiledWindows';
        var _title = "定义排序方式";
        var _url = '${basePath}/query/customizeQueryAction!toSetDetailFiledOrder.do?dto.json=' + encodeURI(encodeURI(json));
        var _w = "450";
        var _h = "350";
        var _load = null;
        var _iframe = true;
        Base.openWindow(_id, _title, _url, {'dto.ztdm': Base.getValue('ztdm')}, _w, _h, _load, function () {
            fnCommonQueryDetailInfo();//执行明细查询
        }, _iframe);
    }

    //生成详细信息带的参数表单数据(点击数字查询用)
    function fnGenerateDetailInfoParasHtml(_o) {
        var parasHtml = $('#detailInfoParasId');
        parasHtml.html('');
        for (var attr in _o) {
            if (attr.indexOf('_fn') == -1 && attr != '__id___' && attr != 'myrownum') {//排除统计函数、datagrid行号、分页用的行标识
                parasHtml.html(parasHtml.html() + "<input type='hidden' name='statistical_paras' filed='" + attr + "' value='" + _o[attr] + "'/>");
            }
        }
        fnQueryDetailInfo();//执行详细信息查询
    }

    //获取点击数字带的参数xml(点击数字查询用)
    function fnQueryAnds() {
        var xml = "";
        $.each($("input[name='statistical_paras']"), function (sn, _o) {
            xml += "<and id=\"" + $(_o).attr('filed') || '';
            xml += "\" sn=\"" + ++sn;
            xml += "\" value=\"" + $(_o).attr('value') || '';
            xml += "\"/>";
        });
        return xml;
    }

    //查询字段（统计）
    function fnQueryFileds() {
        //分组字段
        var xml = "<group_field>";
        $.each($("input[name='tjxm']:checked"), function (sn, _o) {
            _o = $(_o);
            xml += "<field  name=\"" + _o.attr('fname') || '';
            xml += "\" id=\"" + _o.attr('value') || '';
            xml += "\" sn=\"" + ++sn;
            xml += "\" ztxmlsh=\"" + _o.attr('ztxmlsh') || '';
            xml += "\" datatype=\"" + _o.attr('datatype') || '';
            xml += "\"/>";
        });
        xml += "</group_field>";
        xml += "<function_field>";

        //统计函数字段
        var _tjzbhs = $("select[name='tjzbhs']"), ztxmlshs = [];//统计函数,已生成的项目
        $.each($("select[name='tjzb']"), function (sn, _o) {//统计指标
            _o = $(_o);
            var ztxmlsh = $(_o).val() || '';
            if (ztxmlsh != '' && $.inArray(ztxmlsh, ztxmlshs) == -1) {//项目编号不能为空 且 未生成过的项目
                ztxmlshs.push(ztxmlsh);
                var _select = $(_tjzbhs[sn]);
                var tjfs = _select.val();
                var _option = $(_o).find("option:selected");
                var id = _option.attr('filed');
                var datatype = _option.attr('datatype');
                var dbtype = _option.attr('dbtype');
                var name = $.trim($(_o).find("option:selected").text()) + "：" + $.trim(_select.find("option:selected").text());
                if (tjfs != '') {//统计方式不能为空
                    xml += "<field  name=\"" + name || '';
                    xml += "\" id=\"" + id || '';
                    xml += "\" sn=\"" + ++sn;
                    xml += "\" ztxmlsh=\"" + ztxmlsh || '';
                    //xml += "\" datatype=\"" + datatype||'';//加上会影响生成统计列数据
                    xml += "\" dbtype=\"" + dbtype || '';       //字段数据库的类型
                    xml += "\" tjfs=\"" + tjfs || '';
                    xml += "\"/>";
                }
            }
        });
        xml += "</function_field>";
        return xml;
    }

    //条件组 or（统计）
    function fnQueryOrs() {
        var xml = "";
        $.each($("#gruops_id > table"), function (sn, _o) {//条件组 ors
            var sn_local = 0;//手动设置排序号
            xml += "<or sn=\"" + ++sn + "\"><ands>";
            var gxysfs = $(_o).find("[name='gxysf']");//关系运算符
            var datatypes = $(_o).find("[name='datatype']");//数据类型
            $.each($(_o).find("[name='cxxm']"), function (sn, _o) {//查询项目
                var cxxm = $(_o).val() || '';
                var gxysf_o = $(gxysfs[sn]);
                if (cxxm != '' && (gxysf_o.val() || '') != '') {//项目！=空 且 关系运算！=空
                    var value = '', datatype = $(datatypes[sn]).val() || '';//内容值、数据类型
                    var id = $(_o).find("option:selected").attr('filed'); //字段名称
                    if (datatype == '11' || datatype == '12' || datatype == '13' || datatype == '14') {//文本框、年月、日期、数字
                        value = $(_o).parent().parent().find("[name='nrz']").val() || '';//查找当前行 内容值
                    } else if (datatype == '21') {//代码值平铺
                        $.each($(_o).parent().parent().find("[name='nrz_checkbox']:checked"), function (sn, _o) {//找到平铺 内容值
                            if (sn == 0) {
                                value += "'" + $(_o).attr('value') + "'";
                            } else {
                                value += ",'" + $(_o).attr('value') + "'";
                            }
                        });

                    } else if (datatype == '22') {//树
                        value = $(_o).parent().parent().find("[name='nrz']").val() || '';//查找当前行 内容值
                        if (value != '') {
                            var v_a = value.split(';');
                            value = '';
                            $.each(v_a, function (sn, _v) {
                                if (sn == 0) {
                                    value += "'" + _v + "'";
                                } else {
                                    if (_v != '')
                                        value += ",'" + _v + "'";
                                }
                            });
                        }
                    } else if (datatype == '23') {//datagrid

                    }
                    var curTr = $(_o).parent().parent();
                    var vtype = curTr.find("[name='vtype']").val() || '';//查找当前行 值类型
                    if ((vtype == '1' && value != '') || vtype == '2') {
                        if (vtype == '1' && value != '') {//固定值 且 值不能为空
                            xml += "<and id=\"" + id || '';
                            xml += "\" sn=\"" + ++sn_local;
                            xml += "\" ztxmlsh=\"" + cxxm || '';
                            xml += "\" gxysf=\"" + gxysf_o.val() || '';
                            xml += "\" value=\"" + value || '';
                            xml += "\" datatype=\"" + datatype || '';
                            xml += "\" vtype=\"" + vtype || '';
                            xml += "\"/>";
                        } else if (vtype == '2') { //选择的项目
                            var nrxmzSelectO = curTr.find("[name='nrxmz']");
                            //var item_value = nrxmzSelectO.val()||''; //查找当前行项目值
                            var vfield = $(nrxmzSelectO[0]).find("option:selected").attr('filed') || ''; //查找当前行项目字段名称
                            if (vfield != '') {//选择的字段不能为空
                                xml += "<and id=\"" + id || '';
                                xml += "\" sn=\"" + ++sn_local;
                                xml += "\" ztxmlsh=\"" + cxxm || '';
                                xml += "\" gxysf=\"" + gxysf_o.val() || '';
                                xml += "\" datatype=\"" + datatype || '';
                                xml += "\" vtype=\"" + vtype || '';
                                xml += "\" vfield=\"" + vfield || '';
                                xml += "\"/>";
                            }
                        }
                    }
                }
            });
            xml += "</ands></or>";
        });
        return xml;
    }

    //绑定分组项收缩事件
    function fnBindingGroupEvent() {
        $("#tjzb_id_img").unbind('click').click(function () {
            if ($(this).hasClass("img_open")) {
                $(this).removeClass("img_open").addClass("img_close");
                $("#tjzb_id_box").css("display", "none");
                $("#groupItems_id_box").css("display", "none");
            } else {
                $(this).removeClass("img_close").addClass("img_open");
                $("#tjzb_id_box").css("display", "block");
                $("#groupItems_id_box").css("display", "block");
            }
        });
    }

    $(document).ready(function () {
        $("body").taLayout();
        $("#deailInfoId").hide();//解决页面卡死问题
        fnBindingGroupEvent();   //绑定分组项收缩事件   change by zhaohs
        fnQueryDetailInfo();
    });

    //获取项目支持运算
    function getXmzcys(_this) {
        if (_this.value == '') {
            var _o = _tds.eq(2).children(':first');
            _o.empty();
            return;
        }
        Base.submit("", "${basePath}/query/customizeQueryAction!getXmzcys.do", {'dto.yzb620': _this.value}, null, null, function (data) {
            var _tds = $(_this).parent().parent().find("td");
            var _yxList = data.fieldData.xmgxysfList;
            if (_yxList) {
                var _o = _tds.eq(2).children(':first');
                _o.empty();
                for (var i = 0; i < _yxList.length; i++) {
                    var option = $("<option>").text(_yxList[i].yzb641_desc).val(_yxList[i].yzb641);
                    _o.append(option);
                }
            }
        }, null);
    }

    //删除统计指标行
    function deleteTjzbhsTr(_this) {
        var tr_length = $(_this).parent().parent().parent().find("tr").length;
        if (tr_length == 3) {
            Base.alert("不能删除，必需有一项统计指标项！");
            return;
        }
        $(_this).parent().parent().remove();
    }

    //增加统计指标行
    function addTjzbhsTr(_this) {
        var trs = $(_this).parent().parent().parent().find("tr");
        var cloneTr = trs.eq(1).clone();
        var tds = cloneTr.find("td");
        tds.eq(2).html("<select id='gxys_id' name='tjzbhs' style='width:97%;'><option value=''>---请选择---</option></select>");
        //tds.eq(3).html("<input type='text' style='width: 200px;'/>");
        $(trs.eq(trs.length - 1)).before(cloneTr);
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

    //删除或者条件组
    function deleteCondition(_this) {
        var gruops_length = $("#gruops_id").find("table").length;
        if (gruops_length == 1) {
            Base.alert("不能删除，必需设置一组条件！");
            return;
        }
        $(_this).parent().parent().parent().parent().remove();
    }

    //增加或者条件组
    function addConditionGroup() {
        var tabales = $("#gruops_id").find("table");
        var first_table = tabales.eq(0).clone();
        var trs = first_table.find("tr");
        $.each(trs, function (i, _o) {
            if (i == 0 || i == 1 || i == trs.length - 1) {
                if (i == 1) {
                    var tds = $(_o).find("td");
                    tds.eq(2).html("<select id='gxys_id' name='gxysf' style='width:97%;'><option value=''>---请选择---</option></select>");
                    var str = "<span id='nrz_span_id' style='display:inline;width:98%;'>"
                    str += "<input type='text' style='width: 200px;'/>"
                    str += "<input name='vtype' value='1' type='hidden'/>";      //值对象（1固定值2项目）
                    str += "</span>";

                    str += "<span id='nr_item_select_id' style='display:none;'></span>";

                    str += "<span class='swichNr' onclick='fnSwichValueType(this)' title='点击可切换选择项目或输入值'></span>";//切换内容
                    tds.eq(3).html(str);
                }
                return;
            }
            _o.remove();
        });
        tabales.eq(tabales.length - 1).after(first_table);
    }

    //删除tr条件
    function deleteTr(_this) {
        var tr_length = $(_this).parent().parent().parent().find("tr").length;
        if (tr_length == 3) {
            Base.alert("不能删除，必需设置一项查询条件！");
            return;
        }
        $(_this).parent().parent().remove();
    }

    //增加条件查询
    function addCondition(_this) {
        var trs = $(_this).parent().parent().parent().find("tr");
        var cloneTr = trs.eq(1).clone();
        //var cloneTr = $('#datagrid_title_id').next().clone();
        var tds = cloneTr.find("td");
        tds.eq(2).html("<select id='gxys_id' name='gxysf' style='width:97%;'><option value=''>---请选择---</option></select>");

        var str = "<span id='nrz_span_id' style='display:inline;width:98%;'>"
        str += "<input type='text' style='width: 200px;'/>"
        str += "<input name='vtype' value='1' type='hidden'/>";      //值对象（1固定值2项目）
        str += "</span>";
        str += "<span id='nr_item_select_id' style='display:none;'></span>";
        str += "<span class='swichNr' onclick='fnSwichValueType(this)' title='点击可切换选择项目或输入值'></span>";//切换内容
        tds.eq(3).html(str);

        $(trs.eq(trs.length - 1)).before(cloneTr);
    }

    //获取关系和内容
    function getItemGxOrNr(_this) {
        Base.submit("", "${basePath}/query/customizeQueryAction!getItemGxf.do", {
            'dto.yzb611': _this.value,
            'dto.yzb670': Base.getValue("yzb670")
        }, null, null, function (data) {
            var _tds = $(_this).parent().parent().find("td");
            //内容
            var nr_td_o = _tds.eq(3);
            nr_td_o.html('');
            var _map = data.fieldData.zb62Map;
            str = "";//内空HTML
            if (_map) {
                str += "<span id='nrz_span_id' style='display:inline;width:98%;'>";
                var yzb62a = _map.yzb62a;
                if (yzb62a == '21') {//代码值平铺
                    var codes = data.fieldData.codeList;
                    if (codes) {
                        $.each(codes, function (key, __o) {
                            str += "<div title='" + __o.codeDESC + "'><input id='nr_id' name='nrz_checkbox' type='checkbox'  value='" + __o.codeValue + "'/>&nbsp;" + __o.codeDESC + "</div>";
                        });
                    }
                } else if (yzb62a == '11') {//文本框
                    str += "<input name='nrz' type='text' style='width: 200px;'/>";
                } else if (yzb62a == '13') {//日期
                    str += "<input  name='nrz' type='text'  onfocus=WdatePicker({position:{top:6,left:-9},isShowWeek:false,dateFmt:'yyyy-MM-dd'}); class='Wdate' maxlength='10'/>";
                } else if (yzb62a == '12') {//年月
                    str += "<input name='nrz' type='text'  onfocus=WdatePicker({position:{top:6,left:-9},isShowWeek:false,dateFmt:'yyyy-MM'}); class='Wdate' maxlength='10'/>";
                } else if (yzb62a == '14') {//数字
                    str += "<input name='nrz' type='text' style='width: 200px;'/>";
                } else if (yzb62a == '23') {//datagrid
                    str += "<input name='nrz' type='hidden' style='width: 200px;'/><input type='text' style='width: 200px;' onclick=''/>";
                } else if (yzb62a == '22') {//树

                    var tr_index = nr_td_o.parent().index();
                    var tab_index = nr_td_o.parent().parent().parent().index();
                    var treeId = "treeData_" + tab_index + "_" + (tr_index - 1);

                    str += "<div style='width:250px;position: relative;left: 0px;top: 0px;height: 20px;'><input name='nrz' type='hidden' />"
                        + "<span class='innerIcon innerIcon_show' style='margin-top:2px' onclick='showMenu(this)' title='点击展开下拉树'></span>"
                        + "<span class='innerIcon innerIcon_delete' style='margin-top:2px'  onclick='fnClear(this,\"" + treeId + "\")' title='清除'></span>"
                        + "<input type='text' style='width: 250px;' readonly='true' title=''/></div>"
                        + "<div name='menuContent' style='display:none; position: absolute;z-index:1000;width:300px;height:350px;background: #fff;border:#D1D1D1 1px solid;overflow:auto;'>"
                        + "<input type='text' name='queyName' id='queyName' style='width:150px;margin-left:5px;margin-top:5px;'/>"
                        + "<a href='javascript:void(0);' ><font size='2' style='cursor: pointer;margin-left:5px' onclick=fnQueryData(this,\'" + treeId + "\')>查询</font></a>&nbsp;&nbsp;"
                        + "<a href='javascript:void(0);' ><font size='2' style='cursor: pointer;' onclick='fnQueryPre(\"" + treeId + "\")'>上一个</font></a>&nbsp;&nbsp;"
                        + "<a href='javascript:void(0);' ><font size='2'  style='cursor: pointer;' onclick='fnQueryNext(\"" + treeId + "\")'>下一个</font></a>&nbsp;&nbsp;"
                        + "<div style='margin-top:5px;height:319.5px;width:299px'>"
                        + "<ul id='" + treeId + "' name='treeData' class='ztree' style='margin-top:0;'></ul></div></div>";


                }
                str += "<input name='datatype' value='" + _map.yzb62a + "' type='hidden'/>";//数据类型(便于生成xml)
                str += "<input name='vtype' value='1' type='hidden'/>";                 //值对象（1固定值2项目）
                str += "</span>";

                str += "<span id='nr_item_select_id' style='display:none;'></span>";

                str += "<span class='swichNr' onclick='fnSwichValueType(this)' title='点击可切换选择项目或输入值'></span>";//切换内容
                nr_td_o.append(str);
                $.fn.zTree.init($("#" + treeId), setting, eval(data.fieldData.codeList));
            }
            //关系生成
            var _gxList = data.fieldData.xmgxysfList;
            if (_gxList) {
                var _o = _tds.eq(2).children(':first');
                _o.empty();
                for (var i = 0; i < _gxList.length; i++) {
                    var option = $("<option>").text(_gxList[i].yzb631_desc).val(_gxList[i].yzb631);
                    _o.append(option);
                }
            }
        }, null);
    }

    //切换值类型
    function fnSwichValueType(_this) {
        var itemSelectSpan = $(_this).prev();//选择项目span
        var nrSpan = itemSelectSpan.prev();  //内容值span
        var vtype_o = $(nrSpan.find("input[name='vtype']")[0]);
        var vtype = vtype_o.val();//值类型
        if (vtype == '1') {         //当前为值类型
            itemSelectSpan.attr('style', 'display:inline;');
            nrSpan.attr('style', 'display:none;');
            vtype_o.val('2');                     //设置成项目
            var itemHmtl = itemSelectSpan.html();
            if (itemHmtl == '') {
                var optionsHtml = $(nrSpan.parent().parent().find('td').eq(1).find('select')[0]).html();
                var str = "<select name='nrxmz' style='width:250px;'>";
                str += optionsHtml;
                str += "</select>";
                itemSelectSpan.html(str);
            }
        } else if (vtype == '2') {//当前为项目类型
            nrSpan.attr('style', 'display:inline;');
            itemSelectSpan.attr('style', 'display:none;');
            vtype_o.val('1');//设置值类型
        }
    }

    function fnClear(obj, treeId) {
        $(obj).next().val("");
        $(obj).prev().prev().val("");
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        var nodes = treeObj.getCheckedNodes(true);
        if (nodes && nodes.length > 0) {
            for (var i = 0; i < nodes.length; i++) {
                treeObj.checkNode(nodes[i], false, true, false);
            }
        }
    }

    function showMenu(obj) {
        var obj1 = $(obj).next().next();
        var objOffset = obj1.offset();
        var obj2 = $(obj).parent().next();
        $(obj2).css({
            left: objOffset.left - 9 + "px",
            top: objOffset.top + obj1.outerHeight() - 0.5 + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("[name='menuContent']").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.name == "menuContent" || $(event.target).parents("[name='menuContent']").length > 0)) {
            hideMenu();
        }
    }

    function fnQueryData(_this, treeId) {
        var queyName = $(_this).parent().prev().val() || '';
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        var nodes = treeObj.getNodesByParamFuzzy("name", queyName, null);
        if (nodes) {
            treeObj.selectNode(nodes[0]);
            queryResu = nodes;
            i = 0;
        }
    }

    function fnQueryPre(treeId) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        if (queryResu && i >= 0 && i < queryResu.length) {
            i--;
            if (i < 0) {
                i = 0;
            }
            treeObj.selectNode(queryResu[i]);
        }
    }

    function fnQueryNext(treeId) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        if (queryResu && i >= 0 && i < queryResu.length) {
            i++;
            if (i >= queryResu.length) {
                i = queryResu.length - 1;
            }
            treeObj.selectNode(queryResu[i]);
        }
    }

    //导出查询详细结果
    function fnExportDetail(e) {
        $("#query").show();
        var e = e || window.event;
        e.stopPropagation();
    }

    $("body").click(function () {
        $("#query").hide();
    })
    //导出当前页数据
    $(".exports_dangqian_").click(function () {
        var parentId = $(this).parent().attr("id");
        var gridId;
        if ("count" == parentId) {
            //统计
            gridId = "queryResultDgd";
        } else if ("query" == parentId) {
            //查询
            gridId = "deailInfoDgd";
        } else {
            return;
        }
        //参数处理
        var parm_ary = [];
        //表头数据处理
        var headName_obj = {};
        var headName_ary = [];
        var columns_ary = Ta.core.TaUIManager.getCmp(gridId).getColumns();
        console.log(columns_ary);
        for (var i = 1; i < columns_ary.length; i++) {
            headName_ary.push(columns_ary[i].name.replace(/<[^>]+>/g, ''));
        }
        headName_obj.t = "old";
        headName_obj.r = 0;
        headName_obj.c = 0;
        headName_obj.v = headName_ary;
        parm_ary.push(headName_obj);

        //表格数据处理
        var rowData = Base.getGridData(gridId); //获得表格选中行的JSON数组
        console.log(rowData);
        for (var i = 0; i < rowData.length; i++) {
            if (rowData.length > 500) {
                Base.alert("数据过大，请选择导出全部数据！");
                return;
            }
            var rowData_obj = rowData[i];
            var rowData_ary = [];
            var rowData_parm = {};
            //取出的表格数据 默认按id字母排序  需调整至于表头顺序一致
            for (var j = 1; j < columns_ary.length; j++) {
                for (var key in rowData_obj) {
                    if (key == columns_ary[j].id) {
                        rowData_ary.push(rowData_obj[key]);
                        break;
                    }

                }
            }
            rowData_parm.t = "old";
            rowData_parm.r = 0;
            rowData_parm.c = 0;
            rowData_parm.v = rowData_ary;
            parm_ary.push(rowData_parm);
        }
        var dataStr = JSON.stringify(parm_ary); //如果要把得到的数据传到后台，必须把json数组转换成字符串。
        Base.setValue("parm", dataStr);
        Base.submitForm("queryId", null, false, "customizeQueryAction!exportExcel.do");
        Base.hideMask();
    })

    //导出选择数据
    $(".exports_xuanze_").click(function () {
        var parentId = $(this).parent().attr("id");
        var gridId;
        var selectData;
        if ("count" == parentId) {
            //统计
            gridId = "queryResultDgd";
            selectData = Base.getGridSelectedRows("queryResultDgd");
            if (selectData.length < 1) {
                Base.alert("请选中数据后导出");
                return;
            }
        } else if ("query" == parentId) {
            //查询
            gridId = "deailInfoDgd";
            selectData = Base.getGridSelectedRows("deailInfoDgd");
            if (selectData.length < 1) {
                Base.alert("请选中数据后导出");
                return;
            }
        } else {
            return;
        }

    })

    //导出全部数据
    $(".exports_quanbu_").click(function () {
        var parentId = $(this).parent().attr("id");
        var gridId;
        var url;
        var xml;
        var ztdm = Base.getValue('ztdm');
        var yzb612 = Base.getValue('yzb612');
        var yzb670 = Base.getValue('yzb670');
        var ztlsh = "";
        var tjfalsh = Base.getValue('tjfalsh') || '';//方案流水号
        var tjfaname = Base.getValue('tjfaname') || '';//方案名称
        var jctj = Base.getValue('jctj') || '';//基础条件值
        jctj = jctj.replaceAll('"', "'");//change by zhaohs
        if ("count" == parentId) {
            //统计
            gridId = "queryResultDgd";
            url = "customizeQueryAction!exportExcelAll.do";
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            xml += "<scheme ztdm=\"" + ztdm + "\" ztlsh=\"" + ztlsh + "\" jctj=\"" + jctj + "\" tjfalsh=\"" + tjfalsh + "\" tjfaname=\"" + tjfaname + "\">";
            xml += "<statistics_query>";
            xml += "<fields>";
            xml += fnQueryFileds();
            xml += "</fields>";
            xml += "<where>";
            xml += "<ors>";
            queryConditionXml = fnQueryOrs();//全局存放(解决查看明细数据前修改查询条件--数据变化)
            xml += queryConditionXml;
            xml += "</ors>";
            xml += "</where>";
            xml += "</statistics_query>";
            xml += "</scheme>";
        } else if ("query" == parentId) {
            //查询
            gridId = "deailInfoDgd";
            url = "customizeQueryAction!exportDetailExcelAll.do";
            xml = getDetailInfoXml();
        } else {
            return;
        }
        //将表头数据组合传入后台
        //参数处理
        var parm_ary = [];
        //表头数据处理
        var headName_obj = {};
        var headName_ary = [];
        var columns_ary = Ta.core.TaUIManager.getCmp(gridId).getColumns();
        console.log(columns_ary);
        for (var i = 1; i < columns_ary.length; i++) {
            headName_ary.push(columns_ary[i].name.replace(/<[^>]+>/g, ''));
        }
        headName_obj.t = "old";
        headName_obj.r = 0;
        headName_obj.c = 0;
        headName_obj.v = headName_ary;
        parm_ary.push(headName_obj);
        var dataStr = JSON.stringify(parm_ary); //如果要把得到的数据传到后台，必须把json数组转换成字符串。
        //submitForm参数是拼在url后 将超长字符参数 放在文本框 post参数带走
        Base.setValue("parm", dataStr);
        Base.setValue("xml", xml);
        Base.submitForm("queryId", null, false, url, {
            'dto.yzb612': yzb612,
            'dto.yzb670': yzb670,
            'dto.yzb711': tjfaname
        });
        Base.hideMask();
    })
</script>
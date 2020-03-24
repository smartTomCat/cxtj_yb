<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<head>
<title>自定义查询</title>
<%@ include file="/ta/inc.jsp" %>
<link rel="stylesheet" type="text/css" href="${basePath}cxtj/css/customizeQuery.css" />
</head>
<body>
  <div style="height:200px;width:100%;overflow:auto;">
	   	  <table id="datagrid_query_id" width="100%" align="center" cellpadding="2" cellspacing="0" border="0">
			 <tbody>
			   <tr id="datagrid_title_id">
					<td style="width:50px;">
					       操作	
					</td>
					<td style="width:200px;">
						 项目名称
					</td>
					<td style="width: 45%">
						 排序方式
					</td>
				</tr>
				
			<s:if test="#request.setOrderData != null">
				<s:iterator value="#request.setOrderData" id="row" status="st">
					<tr>
						<td style="width:50px;">
						   <a href="javascript:void(0);" onclick="deleteTjzbhsTr(this)">删除</a>
						</td>
						<td style="width:200px;">
						   <select  name="xmxx" style="width:97%;">
					           <option value=''>---请选择---</option>
					           <s:iterator value="#request.baseData['orderXms']" id="item" status="st" >
					               <option value='<s:property value="#item.yzb620"/>'
					                       filed='<s:property value="#item.yzb623"/>' 
					                       dbtype='<s:property value="#item.yzb626"/>' 
					                       filedname='<s:property value="#item.yzb625"/>'
					                       datatype='<s:property value="#item.yzb62a"/>'
					                       <s:if test="#item.yzb620 == #row.ztxmlsh"> selected="selected"</s:if>>
					                       <s:property value="#item.yzb625" />
					               </option>
					           </s:iterator>
					       </select>
						</td>
						<td style="width: 45%">
						   <select  name="pxfs" style="width:97%;">
					           <option value=''>---请选择---</option>
					           <s:iterator value="#request.baseData['orderPxfs']" id="item" status="st" >
					               <option value='<s:property value="#item.codeValue"/>'
					                       <s:if test="#item.codeValue == #row.pxfs"> selected="selected"</s:if>>
					                       <s:property value="#item.codeDESC" />
					               </option>
					           </s:iterator>
					       </select>
						</td>
					</tr>
				</s:iterator>
				</s:if>
				<s:if test="#request.setOrderData == null || #request.setOrderData.size == 0">
				   <tr>
						<td style="width:50px;">
						   <a href="javascript:void(0);" onclick="deleteTjzbhsTr(this)">删除</a>
						</td>
						<td style="width:200px;">
						   <select  name="xmxx" style="width:97%;">
					           <option value=''>---请选择---</option>
					           <s:iterator value="#request.baseData['orderXms']" id="item" status="st" >
					               <option value='<s:property value="#item.yzb620"/>'
					                       filed='<s:property value="#item.yzb623"/>' 
					                       dbtype='<s:property value="#item.yzb626"/>'
					                       filedname='<s:property value="#item.yzb625"/>'
					                       datatype='<s:property value="#item.yzb62a"/>'>
					                       <s:property value="#item.yzb625" />
					               </option>
					           </s:iterator>
					       </select>
						</td>
						<td style="width:45%">
						   <select  name="pxfs" style="width:97%;">
					           <option value=''>---请选择---</option>
					           <s:iterator value="#request.baseData['orderPxfs']" id="item" status="st" >
					               <option value='<s:property value="#item.id.codeValue"/>'>
					                       <s:property value="#item.codeDESC" />
					               </option>
					           </s:iterator>
					       </select>
						</td>
					</tr>
			    </s:if>
				<td style="width:30px;text-align:left;" colspan="3">
				     <ta:button key="增加" onClick="addTjzbhsTr(this)"  />
				</td>
				</tbody>
			</table>
	</div>
   <ta:buttonGroup id="btnGroup5" cssStyle="padding-top:5px">
        <ta:button id="btn6" key="确定" onClick="fnOkSetOrderFields()" />
        <ta:button id="btn7" key="关闭" onClick="fnClose()" cssStyle="margin-left:8px;" />
   </ta:buttonGroup>
</body>
</html>
<script type="text/javascript">
//删除排序行
function deleteTjzbhsTr(_this){
   var tr_length = $(_this).parent().parent().parent().find("tr").length;
    if(tr_length == 3){
      Base.alert("不能删除，必需有一项排序项目！");
      return ;
    }
    $(_this).parent().parent().remove();
}
function fnClose(){
   parent.Base.closeWindow('setDetailOrderFiledWindows');
}
//增加排序行
function addTjzbhsTr(_this){
   var trs = $(_this).parent().parent().parent().find("tr");
    var cloneTr = trs.eq(1).clone();
    var tds = cloneTr.find("td");
    tds.eq(1).find("[name='xmxx']").val('');
    tds.eq(2).find("[name='pxfs']").val('');
    $(trs.eq(trs.length-1)).before(cloneTr);
}
function fnOkSetOrderFields(){
 // var _o = {'data':[{'sn':'1','name':'隶属系统','dbtype':'1','datatype':'21','id':'YB0106','ztxmlsh':'328','pxfs':'1'},
 //                   {'sn':'2','name':'单位级别','dbtype':'1','datatype':'21','id':'B0127','ztxmlsh':'324','pxfs':'1'}]
 //         };
    var _pxfs =  $("select[name='pxfs']");//排序方式 
    var _o={},data = [],_o_ = null;_sn = 0;//本地序列号
    $.each($("select[name='xmxx']"),function(sn,_o){_o_ = {};
        var _option = $(_o).find("option:selected");
        _o_['ztxmlsh'] = ($(_option).val() || '');
        _o_['pxfs'] = ($($(_pxfs[sn]).find("option:selected")).val() || '');      //排序方式
        if(_o_.ztxmlsh != '' && _o_.pxfs !=''){
          _o_['sn'] = ++ _sn;
          _o_['name'] = ($(_option).attr('filedname') || '');
          _o_['dbtype'] = ($(_option).attr('dbtype') || '');
          _o_['datatype'] = ($(_option).attr('datatype') || '');
          _o_['id'] = ($(_option).attr('filed') || '');
          data.push(_o_);
        }
    });
    if(data && data.length>0){
      _o['data'] = data;
    }
   window.parent.fnGenerateDetailOrdersFiledsHtml(_o);
   parent.Base.closeWindow('setDetailOrderFiledWindows');
}
$(document).ready(function (){
	 $("body").taLayout();
});
</script>
<%@ include file="/ta/incfooter.jsp"%>
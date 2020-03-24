<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
<title>自定义查询</title>
<%@ include file="/ta/inc.jsp" %>
</head>
<body >
   <ta:form id="frm" fit="true"  >
       <ta:panel id="pnl1" fit="true" cols="2" >
           <ta:text id="yzb617" display="false" />
           <ta:text id="yzb610" display="false" />
          <ta:text id="yzb711" key="统计方案名称" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" />
          <ta:buttonLayout align="left">
            <ta:button id="btnQue" key="查询" onClick="fnQuery()" />
          </ta:buttonLayout>
          <ta:datagrid id="grid" fit="true" heightDiff="20" forceFitColumns="true" haveSn="true" selectType="radio" span="2">
            <ta:datagridItem  id="yzb711" key="统计方案名称"/>
            <ta:datagridItem  id="aae036" key="保存时间"/>
            <ta:dataGridToolPaging url="customizeQueryAction!getTjfaInfo.do" pageSize="20" showCount="true" submitIds="frm" />
          </ta:datagrid>
           <ta:buttonGroup id="btnlyt5" span="2" align="center" cssStyle="margin-top:-5px">
               <ta:button id="btnOK" key="确定" onClick="fnCalloutTjfa()" space="true"/>
               <ta:button id="btnClose" key="关闭" onClick="fnClose()" />
           </ta:buttonGroup>
       </ta:panel>
   </ta:form>
</body>
</html>
<script type="text/javascript">
function fnCalloutTjfa(){
      var data  = Base.getGridSelectedRows("grid");
      if(data && data.length > 0){
         var _o = {'tjfalsh':data[0].yzb710,'yzb610':data[0].yzb610,'tjfaname': data[0].yzb711};
		 window.parent.fnCalloutTjfa(_o);      
		 parent.Base.closeWindow('calloutTjfaWindows');
      }else{
         Base.alert("请选择要调出的方案!","warn");
      }
   }
   $(document).ready(function () {
		$("body").taLayout();
		fnQuery();
	});
	
  function fnQuery(){
     Base.submit("frm","customizeQueryAction!getTjfaInfo.do");
  }	
  function fnClose(){
     parent.Base.closeWindow('calloutTjfaWindows');
  }
</script>
<%@ include file="/ta/incfooter.jsp"%>
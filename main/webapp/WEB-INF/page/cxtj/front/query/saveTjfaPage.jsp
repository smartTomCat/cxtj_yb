<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
<title>自定义查询</title>
<%@ include file="/ta/inc.jsp" %>
</head>
<body style="padding-top: 5px!important;background-color: white!important;">
    <ta:form id="frm" fit="true" >
        <ta:text id="yzb617" display="false"/>
        <ta:text id="yzb610" display="false"/>
       <ta:box>
          <h1>说明：您可以选择新建统计方案保存，也可以保存到已有统计方案并覆盖；</h1>
<%--
          <h1><span style="visibility:hidden;">说明:</span>2、调出的统计方案保存时，缺省为保存到已有统计方案。</h1>
--%>
       </ta:box>
       <ta:box cols="5">
	          <ta:radio id="newfa" value="1" name="radioGroup" key="新建统计方案" span="5" checked="true" />
	          <ta:text id="n_yzb711" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" onClick="setRadionChecked()" key="统计方案名称" span="4"/>
      		  <ta:button id="btnOK1" key="保存" onClick="fnSaveTjfa()" cssClass="btnmodify"/>
	          </p>
	          </br>
	          <ta:radio id="selfa" value="2" name="radioGroup" key="保存到已有统计方案" span="5"/>
<%--
	          <ta:text id="yzb711" maxLength="200" key="统计方案名称" span="3"/>
	          <ta:buttonLayout span="2">
	            <ta:button id="btnQue" key="查询" btnBgColor="blue" buttonLevel="1" onClick="fnQuery()" />
	          </ta:buttonLayout>
--%>
       </ta:box>
       <ta:box cssStyle="border: #9CB1EA 1px solid;" height="180px">
          <ta:datagrid id="grid" fit="true" height="180px" haveSn="true" selectType="radio" onSelectChange="fnOnSelectChange">
            <ta:datagridItem  id="edit" key="操作" width="50px" formatter="fnFormatter"/>
            <ta:datagridItem  id="yzb711" key="统计方案名称" width="250px"/>
            <ta:datagridItem  id="aae036" key="保存时间" width="150px" />
            <ta:dataGridToolPaging url="customizeQueryAction!getTjfaInfo.do" pageSize="20" showCount="true" showExcel="false" submitIds="frm" />
          </ta:datagrid>
       </ta:box>
       <ta:box cssStyle="padding-top:15px">
        <ta:buttonGroup align="center">
    		<ta:button id="btnOK" key="保存" onClick="fnSaveTjfa()" space="true" cssClass="btnmodify"/>
    		<ta:button id="btnClose" key="关闭" onClick="fnClose()" cssClass="btnmodify"/>
        </ta:buttonGroup>
       </ta:box>
    </ta:form>
</body>
</html>
<script type="text/javascript">
function fnOnSelectChange(rowsData,n){
   Base.setValue('newfa', "");
   Base.setValue('selfa',"2");
}
function setRadionChecked(){
   Base.setValue('newfa', "1");
   Base.setValue('selfa',"");
}
function fnSaveTjfa(){
   var radio = $(":checked").val();
   var _o ;
   if("1" == radio){
      var tjfaname = Base.getValue("n_yzb711");
      if(tjfaname){
	      _o = {'tjfalsh':'','tjfaname':tjfaname};
	      window.parent.fnSaveTjfa(_o);      
		  parent.Base.closeWindow('saveTjfaWindows');
      }else{
         Base.alert("请输入要保存的统计方案名称!","warn");
         return;    
      }
   }else{
      var data  = Base.getGridSelectedRows("grid");
      if(data && data.length > 0){
         _o = {'tjfalsh':data[0].yzb710,'tjfaname':''};
		 window.parent.fnSaveTjfa(_o);      
		 parent.Base.closeWindow('saveTjfaWindows');
      }else{
         Base.alert("请选择要保存到已有的方案!","warn");
         return;      
      }
      _o = {'tjfalsh':'','tjfaname':''};
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
     parent.Base.closeWindow('saveTjfaWindows');
  }
  function fnFormatter(row, cell, value,columnDef, dataContext){
      var yzb710 = dataContext.yzb710;
      return "<a href='javascript:void(0)' onclick='fnDel("+yzb710+")'>删除</a>";
  
  }
  function fnDel(yzb710){
      Base.confirm("确定删除此统计方案？",function(yes){
      
          if(yes){
          
             Base.submit("","customizeQueryAction!delTjfa.do",{"dto.yzb710":yzb710},null,false,fnQuery);
          
          }
      
      });
  
  }
</script>
<%@ include file="/ta/incfooter.jsp"%>
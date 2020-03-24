<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<head>
<title>自定义查询</title>
<%@ include file="/ta/inc.jsp" %>
</head>
<body style="overflow: hidden">
   <ta:box id="mainBox" fit="true" cols="4">
     <ta:box id="leftBox" span="3" height="300px" cssStyle="border: #9CB1EA 1px solid;margin-top:10px;" >
       <ta:datagrid id="dgsort" selectType="radio" forceFitColumns="true" haveSn="true" fit="true">
           <ta:datagridItem id="filedname" key="项目名称"  />
           <ta:datagridItem id="tjfs" key="是否对结果计算" formatter="fnFormatter1"  />
           <ta:datagridItem id="ischecked" key="是否显示" dataAlign="center" formatter="fnFormatter"  />
       </ta:datagrid>
     </ta:box>
     <ta:box id="rightBox" cssStyle="margin-left:30px;">
           <ta:buttonLayout id="btnLyt1" cssStyle="padding-top:20px">
               <ta:button id="btn1" key="已选项目前置" onClick="fnSelTop()" />
           </ta:buttonLayout>
           <ta:buttonLayout id="btnLyt2" cssStyle="padding-top:5px">
               <ta:button id="btn2" key="&nbsp;&nbsp;置&nbsp;&nbsp;顶&nbsp;&nbsp; " onClick="fnTop()"/>
           </ta:buttonLayout>
           <ta:buttonLayout id="btnLyt3" cssStyle="padding-top:5px">
           	   <ta:button id="btn3" key="&nbsp;&nbsp;上&nbsp;&nbsp;移&nbsp;&nbsp;"  onClick="fnUp()" />
           </ta:buttonLayout>
           <ta:buttonLayout id="btnLyt4" cssStyle="padding-top:5px">
               <ta:button id="btn4" key="&nbsp;&nbsp;下&nbsp;&nbsp;移&nbsp;&nbsp;"  onClick="fnDown()"/>
           </ta:buttonLayout>    
           <ta:buttonLayout id="btnLyt5" cssStyle="padding-top:5px">
               <ta:button id="btn5" key="&nbsp;&nbsp;置&nbsp;&nbsp;尾&nbsp;&nbsp;"  onClick="fnEnd()"/>
           </ta:buttonLayout>
     </ta:box>
     <ta:box span="4">
       <ta:buttonGroup id="btnLyt6" align="center" cssStyle="margin-top:10px;">
               <ta:button id="btn6" key="确定" onClick="fnOkSetShowFields()" space="true"/>
               <ta:button id="btn7" key="关闭" onClick="fnClose()"/>
       </ta:buttonGroup>
     </ta:box>
   </ta:box>
</body>
</html>
<script type="text/javascript">
    function fnOkSetShowFields(){
       var data  = Base.getGridData("dgsort");
       for (var i = 0;i<data.length;i++){
          data[i].sn = i+1;
          data[i].name = data[i].filedname;
       }
       var _o = {};
       _o.data = data;

       window.parent.fnGenerateDetailShowFiledsHtml(_o);
       parent.Base.closeWindow('setDetailShowFiledWindows');
    }
   function fnClose(){
      parent.Base.closeWindow('setDetailShowFiledWindows');
   }
   
   function fnSelTop(){
       var data  = Base.getGridData("dgsort");
       var selData = [];
       var unSelData = [];
       for (var i = 0;i<data.length;i++){
           if("true" == data[i].ischecked){
               selData.push(data[i]);
           }else{
               unSelData.push(data[i]);
           }
  	   }
  	   if(unSelData && unSelData.length > 0){
	  	   for(var j = 0;j <unSelData.length;j++){
	  	      selData.push(unSelData[j]);
	  	   }
  	   }
  	   Base.clearGridData("dgsort");
	   Base._setGridData("dgsort",selData);
	   Base.refreshGrid("dgsort");
   }   
   
   $(document).ready(function () {
		$("body").taLayout();
       initQuery();
	});

   //初始查询
   function initQuery(){
       var parmJson = parent.dgHeadJson;
       if(parmJson){
           Base.submit("","${basePath}/query/customizeQueryAction!queryDetailFiled.do",{"dto.json":parmJson});
       }
   }

	function fnFormatter(row, cell, value, columnDef, dataContext){
	     var ischecked = dataContext.ischecked;
	     var ztxmlsh = dataContext.ztxmlsh;
		 if("true" == ischecked ){
              return "<input type='checkbox' name='checkboxGroup' id='check"+ztxmlsh+"\' checked='checked' onclick='fnclick(event)' onchange='fnChange(this,"+row+","+cell+")' />";
	      }else{
	          return "<input type='checkbox' name='checkboxGroup' id='check"+ztxmlsh+"\' onclick='fnclick(event)' onchange='fnChange(this,"+row+","+cell+")' />";
	      }
	}
	
	function fnFormatter1(row, cell, value, columnDef, dataContext){
	   var selfysf = dataContext.selfysf;
	   var selfysfz = dataContext.selfysfz;
	   var tjfs = dataContext.tjfs;
	   var ztxmlsh = dataContext.ztxmlsh;
	   var str= "<select id='tjfs"+ztxmlsh+"\' name='tjfs' onchange='fnChange1(this,"+row+","+cell+")' style='width:97%;' onclick='fnclick(event)'>"
	            +"<option value=''>--请选择--</option>"; 
	   var option = "";
	   if(selfysf){
	       var num = selfysf.split(",");
	       var num1 = selfysfz.split(",");
	       for(var i = 0; i < num.length;i++){
	          if(tjfs == num[i]){
	            option="<option value=\'"+num[i]+"\' selected='true'>"+num1[i]+"</option>"; 
	          }else{
	            option="<option value=\'"+num[i]+"\'>"+num1[i]+"</option>";
	          }
	          str+=option;
	       }
	      return str+="</select>";
	   }else{
	      return str +="</select>";
	   }
	   
	}
	
	function fnChange(_o,row,cell){
	 
       if(_o.checked){
//     		Base.setGridCellData("dgsort",row+1,cell,"true");
     		//change by zhaohs
           Base.updateGridCellData("dgsort",row,cell,"true");
  	    }else{
//     		Base.setGridCellData("dgsort",row+1,cell,"false");
           //change by zhaohs
     		Base.updateGridCellData("dgsort",row,cell,"false");
  		}
	
	}
	
	function fnChange1(_o,row,cell){
	 
//       Base.setGridCellData("dgsort",row+1,cell,_o.value);
       //change by zhaohs
       Base.updateGridCellData("dgsort",row,cell,_o.value);

	}
	function fnclick(e){
	   if ( e && e.stopPropagation ){
	 　　    e.stopPropagation();
       }else{
 　 			window.event.cancelBubble = true;
       }
	   return false;
	}
	
	//向上移动
	function fnUp(){
	    
	    var selectData = Base.getGridSelectedRows("dgsort")[0];
	    
		if (selectData == null || selectData == ""){
		  Base.alert("请选择要移动的项目！","warn");
		  return;
		}else{
		  
		    var data = Base.getGridData("dgsort");
		    
		    var moveNum = 1;

		    if(moveNum >= data.length -1  ){ //改为置顶
		       fnTop();
		       return;
		    }
		    var tmpData=[]; 
		 	var newData=[];
		 	var isMove = true;
		 	var ztxmlsh = selectData.ztxmlsh;
		 	
		 	for(var i  = 0 ; i< data.length;i++){
		 	   if(data[i].ztxmlsh != ztxmlsh){
		 	       newData.push(data[i]);
		 	   }else{
		 	        if(i == 0){
		 	          isMove = false;
		 	          break;
		 	        }
		 	        
		 	        if(i <= moveNum  ){ //置顶
		 	           fnTop();
		 	           return;
		 	        }
		 	        
		 	        for(var j = 0;j < moveNum;j++){
		 	          
		 	           tmpData.push(newData.pop());
		 	        } 
		 	        
		 	        data[i].ischecked = selectData.ischecked;
		 	        data[i].gxysf = selectData.gxysf;
		 	        newData.push(data[i]);
		 	        
		 	        var length =  tmpData.length;
		 	        for(var z = 0;z < length;z++){
		 	             
	 	               newData.push(tmpData.pop());
		 	        }
		 	   }
		 	}
		 	if(isMove){
			 	Base.clearGridData("dgsort");
			 	Base._setGridData("dgsort",newData);
			 	Base.setSelectRowsByData("dgsort",[{ztxmlsh:ztxmlsh}]);
			 	Base.refreshGrid("dgsort");
		 	}
		  
		}
	
	 	
	}
	
	//向下移动
	function fnDown(){
	    var selectData = Base.getGridSelectedRows("dgsort")[0];
		if (selectData == null || selectData == ""){
		  Base.alert("请选择要移动的项目！","warn");
		  return;
		}else{
		       
		        var data = Base.getGridData("dgsort"); 
			 	var newData=[];
			 	var rowData={};
			 	var rownum = -99999;
			 	var isMove = true;
			 	var ztxmlsh = selectData.ztxmlsh;
			 	
			 	var moveNum = 1;

			    if(moveNum >= data.length -1  ){ //改为置尾
			       fnEnd();
			       return;
			    }
			 	
			 	var length =  data.length;
			 	
			 	for(var i  = 0 ; i< length;i++){
			 	   if(data[i].ztxmlsh != ztxmlsh){
			 	        if(i != Number(rownum) + Number(moveNum)){  
				 	     	newData.push(data[i]);
			 	        }else{
			 	        	newData.push(data[i],rowData);
			 	        }
			 	   }else{
			 	        if(i == length - 1){
			 	          isMove = false;
			 	          break;
			 	        }
			 	        if(Number(i) + Number(moveNum) >= (length - 1)){//置尾
			 	           fnEnd();
			 	           return;
			 	        }
			 	        
			 	        rownum = i; 
			 	        rowData = data[i];
			 	   }
			 	}
			 	if(isMove){
				 	Base.clearGridData("dgsort");
				 	Base._setGridData("dgsort",newData);
				 	Base.setSelectRowsByData("dgsort",[{ztxmlsh:ztxmlsh}]);
				 	Base.refreshGrid("dgsort");
			 	}
		}
		
	}
	
	//置顶
	function fnTop(){
	
		var rowData = Base.getGridSelectedRows("dgsort")[0];
		if (rowData == null || rowData == ""){
		  Base.alert("请选择要置顶的项目！","warn");
		  return;
		}else{
		
			  var ztxmlsh = rowData.ztxmlsh;
			  var isMove = true;
			  
			  var data = Base.getGridData("dgsort"); 
			  var newData = [];
			  for(var i  = 0 ; i< data.length;i++){
			     
			     if(data[i].ztxmlsh != ztxmlsh){
				     newData.push(data[i]);
				         
			     }else{
			        
			        if(i == 0){
			           
			           var isMove = false;
			           break;
			        }
			     }
			     
			  }
			     
			 
			  if(isMove){
				 	var _newData = [];
				 	_newData.push(rowData);
				 	
				 	for(var j = 0; j<newData.length;j++){
				 	   _newData.push(newData[j])
				 	}
				 	
				 	Base.clearGridData("dgsort");
				 	Base._setGridData("dgsort",_newData);
				 	Base.setSelectRowsByData("dgsort",[{ztxmlsh:ztxmlsh}]);
			        Base.refreshGrid("dgsort");
			  }
		
		} 
		
	}
	
	//置尾
	function fnEnd(){
	
		var rowData = Base.getGridSelectedRows("dgsort")[0];
		if (rowData == null || rowData == ""){
		  Base.alert("请选择要置尾的项目！","warn");
		  return;
		}else{
		
			  var ztxmlsh = rowData.ztxmlsh;
			  var isMove = true;
			  
			  var data = Base.getGridData("dgsort"); 
			  var newData = [];
			  for(var i  = 0 ; i< data.length;i++){
			     
			     if(data[i].ztxmlsh != ztxmlsh){
				     newData.push(data[i]);
				         
			     }else{
			        
			        if(i == data.length-1){
			           
			           var isMove = false;
			           break;
			        }
			     }
			     
			  }
			     
			 
			  if(isMove){
			       
				 	newData.push(rowData);
				 	Base.clearGridData("dgsort");
				 	Base._setGridData("dgsort",newData);
				 	Base.setSelectRowsByData("dgsort",[{ztxmlsh:ztxmlsh}]);
				 	Base.refreshGrid("dgsort");
			  }
		
		} 
		
	}
</script>
<%@ include file="/ta/incfooter.jsp"%>
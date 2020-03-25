<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
	<head>
	    <title>配制项目</title>
	    <%@ include file="/ta/inc.jsp" %>
	    <style>
			.b{
				font-weight:bold;
				letter-spacing: 5px;
			}
			.table{
				width: 100%;
				text-align: center;
				border: solid #D3E8F7; 
				border-width: 1px 0px 0px 1px;
			}
			.td1{
				border: solid #D3E8F7;
				width: 50%;
				height: 20px;
				border-width: 0px 1px 0px 0px;
			}
			.td2{
				border: solid #D3E8F7; 
				border-width: 0px 1px 1px 0px;
				padding-left: 50px;
				height: 80px;
			}
			.selcetimg{
	    		width: 16px;
	    		height: 16px;
	    		cursor: pointer;
	    		margin-top: -4px;
	    	}
		</style>
	</head>
	<body style="overflow-x:hidden;padding-top: 0px!important;background-color: white!important;">
		<ta:pageloading/>
	    <ta:form id="form1">
	    	<ta:box>
				<ta:text id="yzb670" display="false"/>
				<ta:text id="yzb613" display="false"/>
	    		<ta:number id="yzb620" display="false"/>
				<ta:number id="yzb610" display="false"/>
				<ta:text id="yzb617" display="false"/>
				<ta:radiogroup id="aae100" key="有效标志" collection="AAE100" cols="8" labelStyle="font-size:15px; font-weight:bold; text-align:center; letter-spacing:5px; color:red;"/>
	    	</ta:box>
	    	<ta:box cols="3">
				<ta:number id="yzb621" key="排序号" required="true" min="1" max="9999" labelWidth="150"/>
				<ta:box span="2">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td><ta:text id="yzb623" key="数据库字段或表达式" span="1.8" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" required="true" placeholder="可点击右侧查询按钮选择..." labelWidth="150"/></td>
							<td width="16px"><div class="selcetimg faceIcon icon-search" onclick="fnToEditSearchItemSelect()"/></td>
						</tr>
					</table>
				</ta:box>
				<ta:text id="yzb624" key="数据库字段AS别名" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" required="true" labelWidth="150"/>
				<ta:text id="yzb625" key="数据库字段中文" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" span="2" required="true" labelWidth="150"/>
				<ta:selectInput id="yzb626" key="数据类型" collection="YZB626" required="true" labelWidth="150"/>
				<ta:text id="yzb628" key="代码类别" validType="[{type:'maxLength',param:[200],msg:'最大长度为200'}]" labelWidth="150" span="2"/>
			</ta:box>
			
			<ta:box cssStyle="margin-top:20px">
				<ta:box cols="5">
					<ta:checkbox id="yzb62d" key="作为查询条件" span="2" value="1" onClick="fnClickYzb62d()" />
					<ta:box>&nbsp;</ta:box>
					<ta:radiogroup id="yzb62f" collection="YZB62F" span="2" cols="3" cssStyle="margin-left:-100px" required="true" />
				</ta:box>
				
				<table cellpadding="0" cellspacing="0" class="table">
					<tr>
						<td class="td1"><label id="labelyzb62a" class="b">查询条件的的展现形式</label></td>
						<td class="td1"><label id="labelyzb631" class="b">支持的关系符</label></td>
					</tr>
					<tr>
						<td class="td2">
							<ta:radiogroup id="yzb62a" collection="YZB62A" cols="4" cssStyle="text-align:left; margin-left:20px" required="true" />
						</td>
						<td class="td2">
							<ta:checkboxgroup id="yzb631" collection="YZB631" cols="4" cssStyle="text-align:left; margin-left:20px" required="true" />
						</td>
					</tr>
				</table>
			</ta:box>
			
			<ta:box id="box_count" cssStyle="margin-top:20px;">
				<ta:box cols="5">
					<ta:radiogroup id="yzb62c" cols="3" span="2" required="true" >
						<ta:radio key="不用于计算" value="0" onClick="fnClickYzb62c()" />
						<ta:radio key="默认计算" value="1" onClick="fnClickYzb62c()" />
						<ta:radio key="默认不计算" value="2" onClick="fnClickYzb62c()" />
					</ta:radiogroup>
					<ta:box>&nbsp;</ta:box>
					<ta:radiogroup id="yzb62b" cols="3" span="2" cssStyle="margin-left:-100px" required="true" >
						<ta:radio key="不用于分组" value="0" />
						<ta:radio key="默认选中" value="1"/>
						<ta:radio key="默认未选中" value="2"/>
					</ta:radiogroup>
				</ta:box>
				
				<table cellpadding="0" cellspacing="0" class="table">
					<tr>
						<td class="td1"><label id="labelyzb641" class="b">默认统计方式</label></td>
						<td class="td1"><label id="labelyzb641all" class="b">支持的计算方式</label></td>
					</tr>
					<tr>
						<td class="td2">
							<ta:radiogroup id="yzb641" cols="4" cssStyle="text-align:left; margin-left:20px">
								<ta:radio id="radio_1_yzb641" key="计数" value="1"/>
								<ta:radio id="radio_2_yzb641" key="去重后计数" value="2"/>
								<ta:radio id="radio_3_yzb641" key="求和" value="3"/>
								<ta:radio id="radio_4_yzb641" key="求平均" value="4"/>
								<ta:radio id="radio_5_yzb641" key="最大值" value="5"/>
								<ta:radio id="radio_6_yzb641" key="最小值" value="6"/>
							</ta:radiogroup>
						</td>
						<td class="td2">
							<ta:checkboxgroup id="yzb641all" cols="4" cssStyle="text-align:left; margin-left:20px">
								<ta:checkbox id="check_1_yzb641all" key="计数" value="1" onClick="fnClickYzb641all()"/>
								<ta:checkbox id="check_2_yzb641all" key="去重后计数" value="2" onClick="fnClickYzb641all()"/>
								<ta:checkbox id="check_3_yzb641all" key="求和" value="3" onClick="fnClickYzb641all()"/>
								<ta:checkbox id="check_4_yzb641all" key="求平均" value="4" onClick="fnClickYzb641all()"/>
								<ta:checkbox id="check_5_yzb641all" key="最大值" value="5" onClick="fnClickYzb641all()"/>
								<ta:checkbox id="check_6_yzb641all" key="最小值" value="6" onClick="fnClickYzb641all()"/>
							</ta:checkboxgroup>
						</td>
					</tr>
				</table>
			</ta:box>
			
	    	<ta:buttonGroup cssStyle="margin-top:20px;">
	    		<ta:button id="btnNew" key="新增保存" onClick="fnSave()" space="true" cssClass="btnmodify"/>
	    		<ta:button id="btnOld" key="修改保存" onClick="fnSave()" space="true" cssClass="btnmodify"/>
	    		<ta:button key="关闭" onClick="fnClose()" cssClass="btnmodify"/>
	    	</ta:buttonGroup>

	    </ta:form>
	</body>
</html>
<script type="text/javascript">
$(document).ready(function () {
    $("body").taLayout();
    init();
    $(".td2 .radiogroup-Container div:nth-child(7)").width("30%");
});

function init(){
	fnQuery();
}

//查询
function fnQuery(){
	var _id = 'form1';
	var _url = 'setSearchAction!getEditSearchItem.do';
	var _param = null;
	var _onsub = null;
	var _autoval = false;
	var _sucback = fnSaveBack;
	var _falback = fnSaveBack;
	Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
}

//选择作为查询条件
function fnClickYzb62d(){
	if(Base.getValue('yzb62d') == '1'){
		Base.showObj('labelyzb62a,yzb62a,labelyzb631,yzb631');
	}else{
		Base.hideObj('labelyzb62a,yzb62a,labelyzb631,yzb631');
	}
}

//选择分组计算控制
function fnClickYzb62c(){
	if(!Base.getValue('yzb62c') ||Base.getValue('yzb62c') == '0'){
		Base.hideObj('labelyzb641,yzb641,labelyzb641all,yzb641all');
	}else{
		Base.showObj('labelyzb641,yzb641,labelyzb641all,yzb641all');
	}
}

//选择支持的计算方式
function fnClickYzb641all(){
	for(var i = 1; i < 7; i++){
		Base.hideObj('radio_' + i + '_yzb641');
	}
	var yzb641 = Base.getValue('yzb641');
	var yzb641all = Base.getValue('yzb641all');
	var v = false;
	for(var i = 0; i < yzb641all.length; i++){
		Base.showObj('radio_' + yzb641all[i] + '_yzb641');
		if(yzb641 == yzb641all[i]){
			v = true;
		}
	}
	if(v){
		Base.setValue('yzb641', yzb641);
	}else{
		Base.setValue('yzb641', '');
	}
}

//项目选择
function fnToEditSearchItemSelect(){
	var yzb610 = Base.getValue('yzb610');
	var yzb670 = Base.getValue('yzb670');
	var yzb613 = Base.getValue('yzb613');
	if(yzb610 != ''){
		var _id = 'w_select';
		var _title = '项目选择';
		var _url = 'setSearchAction!toEditSearchItemSelect.do';
		var _param = {'dto.yzb610':yzb610,'dto.yzb670':yzb670,'dto.yzb613':yzb613};
		var _w = parent.fnGetW(1000);
		var _h = parent.fnGetW(600);
		var _load = null;
		var _close = fnToEditSearchItemSelectback;
		var _iframe = true;
		parent.Base.openWindow(_id, _title, _url, _param, _w, _h, _load, _close, _iframe);
	}
}
function fnToEditSearchItemSelectback(){
	var sel = parent._searchitemselect_;
	parent._searchitemselect_ = '';
	if(sel != ''){
		Base.setValue('yzb623', sel.yzb623);
		Base.setValue('yzb624', sel.yzb623);
		Base.setValue('yzb625', sel.yzb625);
		Base.setValue('yzb62d', '1');
		Base.setValue('yzb62f', '1');
		Base.setValue('aae100', '1');
		Base.setValue('yzb626', sel.yzb626);
		Base.setValue('yzb628', sel.yzb628);
		
		if('2' == sel.yzb626){
			Base.setValue('yzb62a', '14');
			Base.setValue('yzb62b', '1');
			Base.setValue('yzb62c', '1');
			Base.setValue('yzb641', '3');
		}else if('3' == sel.yzb626){
			Base.setValue('yzb62a', '13');
			Base.setValue('yzb62b', '0');
			Base.setValue('yzb62c', '0');
		}else{
			if(sel.yzb628){
				Base.setValue('yzb62a', '21');
				Base.setValue('yzb62b', '1');
				Base.setValue('yzb62c', '1');
				Base.setValue('yzb641', '1');
			}else{
				Base.setValue('yzb62a', '11');
				Base.setValue('yzb62b', '0');
				Base.setValue('yzb62c', '0');
			}
		}
		
		if(sel.yzb626 == '2'){
			setYzb641all(true, false, true, true, true, true);
			setYzb631(true, true, true, true, true, true, false, false);
		}else if(sel.yzb626 == '3'){
			setYzb641all(false, false, false, false, true, true);
			setYzb631(true, true, true, true, true, true, false, false);
		}else{
			setYzb641all(true, true, false, false, false, false);
			setYzb631(true, true, false, false, false, false, true, true);
		}

        //对于配置的查询主题 设置默认不分组 默认不计算
        if(1 == Base.getValue("yzb617")){
            Base.setValue('yzb62b', '0');
            Base.setValue('yzb62c', '0');
            setYzb641all(false, false, false, false, false, false);
        }
		
		fnClickYzb62d();
		fnClickYzb62c();
		fnClickYzb641all();
	}

}

function setYzb641all(a, b, c, d, e, f){
	if(a){Base.setValue('check_1_yzb641all', '1');}else{Base.setValue('check_1_yzb641all', '');}
	if(b){Base.setValue('check_2_yzb641all', '2');}else{Base.setValue('check_2_yzb641all', '');}
	if(c){Base.setValue('check_3_yzb641all', '3');}else{Base.setValue('check_3_yzb641all', '');}
	if(d){Base.setValue('check_4_yzb641all', '4');}else{Base.setValue('check_4_yzb641all', '');}
	if(e){Base.setValue('check_5_yzb641all', '5');}else{Base.setValue('check_5_yzb641all', '');}
	if(f){Base.setValue('check_6_yzb641all', '6');}else{Base.setValue('check_6_yzb641all', '');}
}
function setYzb631(a, b, c, d, e, f, g, h){
	if(a){Base.setValue('check_11_yzb631', '11');}else{Base.setValue('check_11_yzb631', '');}
	if(b){Base.setValue('check_12_yzb631', '12');}else{Base.setValue('check_12_yzb631', '');}
	if(c){Base.setValue('check_21_yzb631', '21');}else{Base.setValue('check_21_yzb631', '');}
	if(d){Base.setValue('check_22_yzb631', '22');}else{Base.setValue('check_22_yzb631', '');}
	if(e){Base.setValue('check_31_yzb631', '31');}else{Base.setValue('check_31_yzb631', '');}
	if(f){Base.setValue('check_32_yzb631', '32');}else{Base.setValue('check_32_yzb631', '');}
	if(g){Base.setValue('check_41_yzb631', '41');}else{Base.setValue('check_41_yzb631', '');}
	if(h){Base.setValue('check_42_yzb631', '42');}else{Base.setValue('check_42_yzb631', '');}
}

//保存
function fnSave(){
	var _id = 'form1';
	var _url = 'setSearchAction!saveSearchItem.do';
	var _param = null;
	var _onsub = null;
	var _autoval = true;
	var _sucback = fnSaveBack;
	var _falback = fnSaveBack;
	Base.submit(_id, _url, _param, _onsub, _autoval, _sucback, _falback);
}
function fnSaveBack(){
	var yzb620 = Base.getValue('yzb620');
	if(yzb620 == ''){
		Base.showObj('btnNew');
		Base.hideObj('btnOld');
	}else{
		Base.showObj('btnOld');
		Base.hideObj('btnNew');
	}
	fnClickYzb62d();
	fnClickYzb62c();
	fnClickYzb641all();
}

//关闭
function fnClose(){
	parent.Base.closeWindow('w_edit');
}
</script>
<%@ include file="/ta/incfooter.jsp" %>
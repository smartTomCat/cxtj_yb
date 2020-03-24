<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<%
	String isaudite = SysConfig.getSysConfig("isAudite", "false");
	String isOpenFieldAuthority = SysConfig.getSysConfig("isOpenFieldAuthority", "false");
%>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
	<title>菜单管理</title>
	<%@ include file="/ta/inc.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=facePath%>lib/ColorPicker/css/colpick.css" />
	<script src="<%=facePath%>lib/ColorPicker/js/colpick.js" type="text/javascript"></script>
	<style>
		.selectpic {
			padding-top: 10px;
			display: none;
			position: absolute !important;
			border: 1px solid #d5d7d8;
			box-shadow: 0px 0px 1px 1px rgba(140, 147, 152, 0.3);
			width: 400px;
			height: 200px;
			z-index: 100;
			background: #FFF;
			overflow-y: auto;
		}
		.selectpic div {
			display: inherit;
			float: left;
			height: 20px;
			width: 20px;
			padding: 5px;
		}
		.selectpic div li {
			display: inherit;
			height: 20px;
			width: 20px;
			text-align: center;
			color: #333;
		}
		.selectpic li span{
			-webkit-transition: font-size 0.25s ease-out 0s;
			-moz-transition: font-size 0.25s ease-out 0s;
			transition: font-size 0.25s ease-out 0s;
		}
		.selectpic li span:hover{
			font-size: 26px;
		}
		#imgbt{
			color: #333;
			font-size: 20px;
		}
		#imgbt:before{
			vertical-align: middle;
		}
		#iconSkinColor{
			border-right:20px solid #333;
			border-radius: 3px;
			box-sizing: border-box;
		}
	</style>
</head>
<body layout="border" layoutCfg="{leftWidth:375}" style="padding:0px;margin:0px" class="no-scrollbar">
<ta:pageloading />
<ta:box position="left" key="菜单维护">
	<ta:panel id="treePanel" withToolBar="true" hasBorder="false" bodyStyle="overflow:auto; border-top:none" fit="true">
		<ta:panelToolBar align="right" cssStyle="background-color: transparent;">
			<ta:button asToolBarItem="true" size="small" key="展开" id="expand" icon="icon-dbArrow_down" onClick="Base.expandTree('menuTree');" />
			<ta:button asToolBarItem="true" size="small" key="收起" id="collapse" icon="icon-dbArrow_up" onClick="Base.collapseTree('menuTree');" />
		</ta:panelToolBar>
		<ta:tree id="menuTree" showSearch="true" searchLabel="菜单搜索" searchPanelGrid="[{id:'menuname',key:'菜单名称',width:50},{id:'url',key:'URL',width:50},{id:'menunamepath',key:'菜单路径',width:80},{id:'syspath',key:'系统路径',width:40}]" fontCss="treeEffective"  showLine="true" nameKey="menuname" childKey="menuid" parentKey="pmenuid"  editable="true"
				 onDblClick="fnDblClk" onRightClick="fnOnRightClick" beforeEdit="fnToEdit" beforeRemove="fnBfRemove" onRemove="fnRemoveMenu" keepLeaf="true" keepParent="true" beforeDrop="fnBeforeDrop"
				 onDrop="fnOnDrop" onClick="fnClick" editTitle="编辑当前菜单" removeTitle="删除当前菜单" addTitle="添加子菜单" showAddBtn="true" onAdd="fnAddMenu" />
	</ta:panel>
</ta:box>
<ta:box position="center">
	<ta:box cols="2" fit="true">
		<ta:box id="menuPanel" fit="true" cssStyle="overflow:auto;padding:10px 40px 10px 30px;border-right:1px solid #ddd" layout="column" columnWidth="0.6">
			<ta:form id="menuForm" fit="true" cols="2">
				<ta:text id="menuid" key="功能编号" display="none" span="2" labelWidth="120"/>
				<ta:text id="menuname" key="功能名称" validType='[{type:"maxLength",param:[30],msg:"最大长度为30"}]' bpopTipPosition="bottom" required="true" span="2" columnWidth="0.9" labelWidth="120"/>
				<ta:text id="pmenuid" key="上级功能编号" display="none" span="2" value="0" columnWidth="0.9" labelWidth="120"/>
				<ta:text id="menunamepath" display="none" span="2" labelWidth="120"/>
				<ta:text id="menuidpath" display="none" required="true" readOnly="true" span="2" columnWidth="0.9" labelWidth="120"/>
				<ta:selectTree url="#" idKey="menuid" data="${pnameSelectTree}" labelWidth="120" parentKey="pmenuid" nameKey="menuname" targetId="selectmenuid"  targetDESC="selectmenuname"  treeId="selecttreepmenuname" key="上级功能" readOnly="true" span="2" columnWidth="0.9" selectTreeBeforeClick="fnClickPMenu"  />
				<ta:text  id="customencoding" key="自定义编码" span="2"  columnWidth="0.9" labelWidth="120"/>
				<ta:selectInput required="true" id="menutype" key="菜单类型" span="2" collection="MENUTYPE" filterOrg="false" columnWidth="0.9" labelWidth="120"/>
				<ta:text id="iconSkinIcon" key="菜单图片" span="2" columnWidth="0.9" onBlur="iconSkinSetValue()" onChange="iconSkinSetValue()" labelWidth="120"/>
				<div  style="float: left;">
					<span id="imgbt" class="button menuIcon icon-tree_doc" style="line-height: 28px"></span>
				</div>
				<ta:text id="iconSkinColor" key="菜单图片颜色" span="2"  columnWidth="0.9" onBlur="iconSkinSetValue()" onChange="iconSkinSetValue()" labelWidth="120"/>
				<ta:text id="iconSkin" value="" span="2" display="false" labelWidth="120"/>
				<ta:selectInput id="syspath" key="系统路径" span="2" required="true" display="none" labelWidth="120" bpopTipMsg="在config.properties中配置，默认值为config中的curSyspathId属性" columnWidth="0.9"/>
				<ta:text id="url" key="功能URL" span="2" columnWidth="0.9" labelWidth="120"/>
				<ta:text id="accesstimeel" key="限制时间" span="2" columnWidth="0.9" labelWidth="120"/>
				<ta:text id="methods" key="新增需授权方法" span="2" columnWidth="0.9" labelWidth="120"
						 textHelp="该输入框的输入值是以该Action下要进行单独授权的方法名（多个方法名之间以英文逗号分隔）组成， 例如“save,update”，这样该菜单下会再生成2个安全策略为要认证不显示的子菜单。比如：功能url为test/testAction.do,则在该菜单下的jsp页面中的按钮，如果id=“test.testAction.save”，则该按钮就具有了权限控制的功能" />
				<ta:radiogroup id="consolemodule" span="2" cols="2" key="工作台模块" columnWidth="0.9" labelWidth="120">
					<ta:radio key="是" value="1" ></ta:radio>
					<ta:radio key="否" value="0"  checked="true"></ta:radio>
				</ta:radiogroup>
				<ta:radiogroup id="securitypolicy" columnWidth="0.9" span="2" collection="POLICY" key="安全策略" cols="2" required="true" filterOrg="false" labelWidth="120"></ta:radiogroup>
				<ta:radiogroup key="数据区权限" id="useyab003" columnWidth="0.9" cols="2" span="2" labelWidth="120">
					<ta:radio key="启用" value="1"></ta:radio>
					<ta:radio key="禁用" value="0"></ta:radio>
				</ta:radiogroup>
				<ta:radiogroup key="经办人员岗位" id="isdismultipos" columnWidth="0.9" cols="2" span="2" required="true" labelWidth="120">
					<ta:radio key="明确" value="0"></ta:radio>
					<ta:radio key="不明确" value="1"></ta:radio>
				</ta:radiogroup>
				<ta:radiogroup key="权限审核" id="isaudite" cols="2" columnWidth="0.9" span="2" required="true" display="none" labelWidth="120">
					<ta:radio key="需要" value="1"></ta:radio>
					<ta:radio key="不需要" value="0"></ta:radio>
				</ta:radiogroup>
				<ta:radiogroup key="有效性" span="2" id="effective" columnWidth="0.9" collection="EFFECTIVE" cols="2" required="true" filterOrg="false" labelWidth="120"></ta:radiogroup>
				<%-- <ta:text id="menuext" key="扩展字段测试" span="2" columnWidth="0.9" /> --%>
				<ta:text id="menulevel" key="菜单层级" display="false"></ta:text>
				<%--<ta:text id="selectImage" key="选中菜单图片" />--%>
				<%--<ta:selectInput id="type" collection="YAE162" key="菜单类型" span="2" required="true"/> --%>
				<%-- <ta:text id="shortId" key="快捷访问码" span="2"/>目前没用到--%>
				<%--<ta:text id="orderId" key="排序号" readOnly="true" required="true" value="0" span="2"/> --%>
				<%-- <div span="3">
                <s:debug></s:debug>
                </div> --%>
				<ta:radiogroup id="isFiledsControl" display="false" span="2" columnWidth="0.9" cols="2" key="字段权限控制" labelWidth="120">
					<ta:radio key="是" value="1" onClick="fnFiledAuthorClick(1)"></ta:radio>
					<ta:radio key="否" value="0" onClick="fnFiledAuthorClick(0)" checked="true"></ta:radio>
				</ta:radiogroup>
				<ta:buttonGroup align="right" span="2" columnWidth="0.9">
					<ta:button type="submit" isIncludeNullFields="true" submitIds="menuPanel" url="menuMgAction!webUpdateMenu.do"  id="update" key="保存[S]" hotKey="S"
							   successCallBack="fnUpdateMenu" disabled="true" onSubmit="fnUpdateTamenuOnsubmit"
					/>
					<ta:button type="submit" submitIds="menuPanel" url="menuMgAction!webSaveMenu.do" id="save" key="保存[S]" hotKey="S" successCallBack="fnSaveSuccessCb"
							   display="false" disabled="true" />
					<ta:button key="取消[C]" id="cancel" hotKey="C"
							   onClick="fnDblClk(null, 'menuTree', Base.getValue('menuId')!=''?Base.getObj('menuTree').getNodeByParam('id',Base.getValue('menuId')):Base.getObj('menuTree').getSelectedNodes()[0]);"
							   disabled="true" />
					<%-- <ta:submit asToolBarItem="true" key="test" id="cance3l" url="menuMgAction!webGetSyncMenu.do" icon="icon-close2" parameter="{'profiling':'yes'}"/> --%>
				</ta:buttonGroup>
				<ta:text id="fieldData" display="false" />
				<%--保存字段权限数据 --%>
				<ta:text id="nowEditMenuId" display="false" />
				<%--保存左侧树 正在编辑的menuid --%>
			</ta:form>
		</ta:box>
		<ta:box fit="true" columnWidth="0.4" cssStyle="margin:10px 5px 0px 10px;" id="fieldBox">
			<ta:fieldset cols="2" key="采集需要权限控制的字段">
				<ta:text id="controlFiled" placeholder="录入需要控制的字段id" columnWidth="0.68" readOnly="true" required="true"/>
				<ta:text id="controlFiledname" placeholder="录入需要控制的字段name" columnWidth="0.68" readOnly="true" required="true"/>
				<ta:buttonGroup align="right" columnWidth="0.32">
					<ta:button id="btnAddField" key="保存" disabled="true" onClick="fnAddFiledToTree()"></ta:button>
					<ta:button  id="btnUpdateField" key="保存" disabled="true"  onClick="fnUpdateFiledToTree()" display="false"></ta:button>
				</ta:buttonGroup>
			</ta:fieldset>
			<ta:panel fit="true" key="采集字段">
				<ta:tree id="pageFieldGatherTree" beforeEdit="fnFiedTreeBeforeEdit"  beforeRemove="fnFieldTreeBeforeRemove" onClick="fnFieldTreeBeforeAdd" parentKey="pid" childKey="id" nameKey="fieldname" editable="true" showEditBtn="true" showRemoveBtn="true" showAddBtn="false" />
			</ta:panel>
		</ta:box>
		<ta:text id="editIdOfFied" display="false" key="记录正在编辑的字段ID"  />
	</ta:box>
</ta:box>
<div id="rm" style="width:150px;font-size:12px;">
	<div id="rm_add" class="btn-app">添加子菜单</div>
	<%--<div id="rm_add2">添加到常用菜单</div>--%>
	<div id="rm_modify">修改当前菜单</div>
	<div id="rm_del">删除当前菜单</div>
</div>
<div id="seldiv" class="selectpic">
</div>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function() {
        $("body").taLayout();
        Base.hideObj("fieldBox");

        $(".radiogroup").height("100%");
        var treeObj = Base.getObj("menuTree");
        var treeNode = treeObj.getNodeByTId("menuTree_1");
        treeObj.selectNode(treeNode); // 选中第一个节点
// 		$("#seldiv").resizable({handles:'se, sw'});
        new TaResizable($("#seldiv")).resizable({handles:'se, sw'});
        $("#iconSkinIcon").focus(function(){
            var s = $("#seldiv");
            var $text = $(this);
            s.css({left:($text.offset().left),top:($text.offset().top+32)});
            s.show();

            $(document).bind('click.selectdiv__',function(e){
// 				var isie = (document.all) ? true : false;
                var target = e.srcElement || e.target;
// 			    if(document.all ? false : true)target = e.target;
                if(target != s[0] && target != $text[0]){
                    s.hide();
                    $(">div li.keyover",s).removeClass('keyover');
                    $(document).unbind('.selectdiv__');
                }
            });
            $(">div li span",s).bind('click.selectdiv__',function(){
                $text.val($(this).attr("title"));
                $("#imgbt").removeClass().addClass("menuIcon button " + $(this).attr("title"));
                $(this.parentNode).addClass('selected');
                $(this.parentNode.parentNode).siblings().find('>li').removeClass('selected');
                $(">div li.keyover",s).removeClass('keyover');
                iconSkinSetValue();
                s.hide();
            });
            $(this).bind('keydown.selectdiv__',function(e){
                if(e.keyCode==39){//->
                    var selected = $(">div li.keyover",s);
                    if(selected.length==0){
                        selected = $(">div:first li",s);
                        $(selected[0]).addClass('keyover');
                    }else{
                        $(selected[0]).removeClass('keyover');
                        $(selected[0].parentNode).next().find('>li').addClass('keyover');
                    }
                    e.keyCode = 0;
                    e.cancelBubble = true;
                    e.returnValue = false;

                    // e.stopPropagation works in Firefox.
                    if (e.stopPropagation) {
                        e.stopPropagation();
                        e.preventDefault();
                    }
                }else if(e.keyCode==37){//<-
                    var selected = $(">div li.keyover",s);
                    if(selected.length==0){
                        selected = $(">div:last li",s);
                        $(selected[0]).addClass('keyover');
                    }else{
                        $(selected[0]).removeClass('keyover');
                        $(selected[0].parentNode).prev().find('>li').addClass('keyover');
                    }
                    e.keyCode = 0;
                    e.cancelBubble = true;
                    e.returnValue = false;

                    // e.stopPropagation works in Firefox.
                    if (e.stopPropagation) {
                        e.stopPropagation();
                        e.preventDefault();
                    }
                }else if(e.keyCode==13){//enter
                    $(">div li.keyover button",s).click();
                    $(">div li.keyover",s).removeClass('keyover');
                }else if(e.keyCode==40){//down
                    s.show();
                }else if(e.keyCode==27){//esc
                    s.hide();
                    $(">div li.keyover",s).removeClass('keyover');
                }else if(e.keyCode==8 || e.keyCode==38){//退格取消
                    e.keyCode = 0;
                    e.cancelBubble = true;
                    e.returnValue = false;

                    // e.stopPropagation works in Firefox.
                    if (e.stopPropagation) {
                        e.stopPropagation();
                        e.preventDefault();
                    }
                }
            });
        });
        $("#rm").menu();
        $("#menuTree").bind('contextmenu', function(e){
            $('#rm').menu('show', {left: e.pageX, top: e.pageY});
            return false;
        });
        window.setTimeout("fnPageGuide(parent.currentBuinessId)", 300);
        //add by cy 初始化图标选择框
        initSelectIcon();
        //add by cy 初始化颜色选择面板
        $('#iconSkinColor').colpick({
            submit:0,
            color:"#333333",
            onChange:function(hsb,hex,rgb,el,bySetColor) {
                $(el).css('border-color','#'+hex);
                if(!bySetColor) $(el).val("#"+hex);
                Base.setValue("iconSkin",Base.getValue("iconSkinIcon")+"?"+Base.getValue("iconSkinColor"));
                $("#imgbt").css("color",Base.getValue("iconSkinColor"))
            }
        }).keyup(function(){
            $(this).colpickSetColor(this.value);

        })
    });


    //提交前看是否需要修改menuname
    function fnUpdateTamenuOnsubmit() {
        var menunamepath = Base.getValue("menunamepath");
        var newMenuName=Base.getValue("menuname");
        var menuNamePathArr = menunamepath.split("/");
        var endName = menuNamePathArr[menuNamePathArr.length-1];
        if(endName!=newMenuName){
            menuNamePathArr[menuNamePathArr.length-1]=newMenuName;
            var newMenuNamePath = "";
            for(var i = 0 ; i < menuNamePathArr.length;i++){
                if(i!= menuNamePathArr.length-1){
                    newMenuNamePath+=menuNamePathArr[i]+"/";
                }
                else{
                    newMenuNamePath+=menuNamePathArr[i];
                }
            }
            Base.setValue("menunamepath",newMenuNamePath);
        }
        return true;
    }


    function iconSkinSetValue(){
        Base.setValue("iconSkin",Base.getValue("iconSkinIcon")?Base.getValue("iconSkinIcon")+"?"+Base.getValue("iconSkinColor"):"");
    }

    function initSelectIcon(){
        var $con=$("#seldiv"),i=1,len=74,ar=[];
        for(i;i<=len;i++){
            var str=("000"+i);
            str=str.substring(str.length-3);
            ar.push("<div><li><span class='button menuIcon icon-"+str+" ' title='icon-"+str+"'></span></li></div>");
        }

        ar.push("<div><li><span class='button menuIcon icon-tree_doc' title='icon-tree_doc'></span></li></div>");
        $con.html(ar.join(" "));
    }
    function fnPageGuide(currentBuinessId){
        var data = [{id:$("#menuTree_1_a"),
            message:"这里选择对菜单进行的操作！"
        },
            {id:$("#url"),
                message:"这里将填写菜单对应在项目中的地址!"
            },
            {id:$("#securitypolicy"),
                message:"这里选择使用的安全策略!"
            }
        ]
        new TaHelpTip($("body")).helpTip({
            replay 	: false,
            show 	: true,
            cookname: currentBuinessId,
            data 	: data
        });
    }
    //单击展开树
    function fnClick(e,treeId,treeNode) {
        var treeObj = Base.getObj("menuTree");
        treeObj.expandNode(treeNode, true, false, true);
    }
    // 双击进入菜单编辑状态
    function fnDblClk(e, treeId, treeNode) {
        if (treeNode.menutype == "0") Base.filterSelectInput("menutype", []);
        if (treeNode.menutype == "1") Base.filterSelectInput("menutype", [0,2]);
        if (treeNode.menutype == "2") Base.filterSelectInput("menutype", [1]);
        Base.resetData("menuForm");
        Base.setValue("nowEditMenuId",treeNode.menuid);//记录左侧正在编辑的树节点的menuid
        var treeObj = Base.getObj("menuTree");
        Base.submit("", "menuMgAction!webGetMenu.do", {"menuid":treeNode.menuid}, null, null, function(data){
            Base.hideObj("save");
            Base.showObj("update");
            Base.setEnable(["update","cancel"]); // 将组件设置为可用并不能改变它的隐藏属性，所以前面还要调用showObj()方法
            Base.setValue("pmenuname", treeNode.getParentNode() ? treeNode.getParentNode().menuname : "");

            Base.focus("menuname", 100);
            Base.setValue("iconSkinIcon",data.fieldData.iconSkin?data.fieldData.iconSkin.split("?")[0]:"");
            Base.setValue("iconSkinColor",data.fieldData.iconSkin? (data.fieldData.iconSkin.split("?")[1]?data.fieldData.iconSkin.split("?")[1]:"#333333"):null);
            $('#iconSkinColor').colpick({color:Base.getValue("iconSkinColor")?Base.getValue("iconSkinColor"):"#333333"});
            $("#iconSkinColor").colpickSetColor(Base.getValue("iconSkinColor"));
            $("#imgbt").removeClass().addClass("button menuIcon " + (treeNode.iconSkin?treeNode.iconSkin.split("?")[0]:"icon-tree_doc")).css("color",Base.getValue("iconSkinColor"))
// 			fnConsolemoduleClick(treeNode.consolemodule);
            if(1==data.fieldData.isFiledsControl && "true" == "<%=isOpenFieldAuthority%>"){
                //如果已经存在授权字段权限则对应的去展示
                fnShowAuthorFields(data.lists.fields.list);
            }
            else {
                fnResetFiledCollection();
            }
        }, null, false);

        if(e){
            e.keyCode = 0;
            e.cancelBubble = true;
            e.returnValue = false;
            if (e.stopPropagation) {
                e.stopPropagation();
                e.preventDefault();
            }
        }

    }
    // 点击编辑按钮编辑菜单
    function fnToEdit(treeId, treeNode) {
        Base.setEnable("selecttreepmenuname");
        return fnDblClk(null, treeId, treeNode), false;
    }
    // 添加子菜单
    function fnAddMenu(event, treeId, treeNode) {
        if (treeNode.menutype == "0") Base.filterSelectInput("menutype", []);
        if (treeNode.menutype == "1") Base.filterSelectInput("menutype", [0,2]);
        if (treeNode.menutype == "2") Base.filterSelectInput("menutype", [1]);
        Base.resetData("menuForm");
        Base.hideObj("update");
        Base.showObj("save");
        Base.setValue("pmenuid", treeNode.menuid);
        Base.setValue("menunamepath", treeNode.menunamepath);
        Base.setValue("pmenuname", treeNode.menuname);
        Base.setValue("menuidpath", treeNode.menuidpath);
        Base.setValue("menulevel",treeNode.menulevel);
        $("#imgbt").removeClass().addClass("button menuIcon" + "icon-tree_doc");
        Base.setValue("effective", "1");
        Base.setValue("securitypolicy", "1");
        Base.setValue("isdismultipos", "1");
        Base.setValue("syspath",treeNode.syspath);
        Base.setValue("useyab003",0);
        if("true" == "<%=isaudite%>"){
            //需要权限审核
            Base.setValue("isaudite", 1);
        } else {//不需要权限审核
            Base.setValue("isaudite", 0);
        }
        Base.setValue("consolemodule", "0");
// 		fnConsolemoduleClick(0);
        //Base.submit("parentMenuId", "menuMgAction!getMaxYAE114.do");
        Base.setEnable("save");
        Base.showObj("save");
        Base.setDisabled("cancel");
        Base.focus("menuname", 100);

        //增加新菜单,最右边重置
        fnResetFiledCollection();

        //
        Base.setValue("selecttreepmenuname",{"menuid":treeNode.menuid,"menuname":treeNode.menuname});
        Base.setDisabled("selecttreepmenuname");
    }
    // 添加到常用菜单
    function fnAdd2CommMenu(event, treeId, treeNode) {
        if (treeNode.isParent) {
            Base.alert("该菜单不允许添加到常用菜单！");
        } else {
            Base.submit(null, "comMenuAction!addComMenu.do", {
                menuid : treeNode.menuid
            }, false, false);
        }
    }
    // 右键点击菜单事件
    function fnOnRightClick(event, treeId, treeNode) {
        var treeObj = Base.getObj("menuTree");
        $("#rm_add").unbind("click").bind("click", function() {
            fnAddMenu(event, treeId, treeNode);
        });/*
						$("#rm_add2").unbind("click").bind("click", function(){
							fnAdd2CommMenu(event, treeId, treeNode);
						});*/
        $("#rm_modify").unbind("click").bind("click", function() {
            fnDblClk(event, treeId, treeNode)
        });
        $("#rm_del").unbind("click").bind("click", function() {
            if (!treeNode.pmenuid || treeNode.pmenuid == "") {
                Base.alert("不能删除根菜单！", "warn");
            } else if (treeNode.isParent) {
                Base.confirm("删除该菜单会把它的子菜单一并删除，确实要删除吗？", function(yes) {
                    if (yes)
                        fnRemoveMenu(event, treeId, treeNode);
                    //Base.submit(null, "menuMgAction!webDeleteMenu.do", {"dto['menuid']":treeNode.menuid}, false, false, function(){Base.refleshTree("menuTree", treeNode.pmenuid);});
                });
            } else {
                Base.confirm("确实要删除该菜单吗？", function(yes) {
                    if (yes)
                        fnRemoveMenu(event, treeId, treeNode);
                    //Base.submit(null, "menuMgAction!webDeleteMenu.do", {menuId:treeNode.menuid}, false, false, function(){Base.refleshTree("menuTree", treeNode.pmenuid);});
                });
            }
        });
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            treeObj.cancelSelectedNode();
        } else if (treeNode) {
            treeObj.selectNode(treeNode);
        }
    }
    // 新增菜单成功后的回调函数
    function fnSaveSuccessCb(data) {
        var node = Base.getObj("menuTree").getNodeByParam("menuid", Base.getValue("pmenuid"));
        var newdata = Base.getJson("menuMgAction!webGetAsyncAllMenu.do");
        Base.recreateTree("menuTree",null,newdata);
        //展开到指定node
        var verfyNode = Base.getObj("menuTree").getNodesByParam("menuid",data.fieldData.newMenuId,null)[0];
        //层层扩展
        expandNodeExit(verfyNode);
        if(expandNodes){
            for(var i = expandNodes.length-1;i>=0;i--){
                Base.getObj("menuTree").expandNode(expandNodes[i],true);
            }
        }
        if (confirm("新增菜单成功，是否继续新增？")) {
            fnAddMenu(null, "menuTree", node);
        } else {
// 			Base.hideObj("save");
// 			Base.showObj("update");
// 			Base.setEnable([ "update", "cancel" ]);
            Base.setValue("useyab003", 1);
            Base.setDisabled("save");
        }
        Base.setValue("nowEditMenuId",data.fieldData.newMenuId);

        recreateSelectTree(newdata);

    }
    var expandNodes = [];
    function expandNodeExit(node){
        if(node && node.getParentNode()!=null){
            expandNodes.push(node.getParentNode());
            var pnode = node.getParentNode();
            expandNodeExit(pnode);
        }
        return;
    }
    // 判断和提示能否删除菜单
    function fnBfRemove(treeId, treeNode) {
        if (!treeNode.pmenuid || treeNode.pmenuid == "") {
            return Base.alert("不能删除根菜单！", "warn"), false;
        } else if (treeNode.isParent) {
            return confirm("删除该菜单会把它的子菜单一并删除，确实要删除吗？");
        } else {
            return confirm("确实要删除该菜单吗？");
        }
    }
    // 删除菜单
    function fnRemoveMenu(event, treeId, treeNode) {
        Base.submit(null, "menuMgAction!webDeleteMenu.do", {
            "menuid" : treeNode.menuid
        }, false, false, function() {
            Base.msgTopTip("<div class='msgTopTip'>删除成功</div>");
            Base.resetData("menuForm");
            Base.setDisabled("save,update,cancel");
            var node = treeNode.getParentNode();
            if (node) {
                var children = node.children;
                if (children && children.length > 0) {
                    Base.refleshTree("menuTree", treeNode.pmenuid);
                } else {
                    Base.refleshTree("menuTree", node.pmenuid);
                }
            } else {
                Base.refleshTree("menuTree", 1);
            }
        });
    }
    // 判断和提示能否拖拽调整菜单顺序
    function fnBeforeDrop(treeId, treeNodes, targetNode, moveType) {
        var treeNode = treeNodes[0];
        if (treeNode.pmenuid != targetNode.pmenuid) {
            return Base.alert("非同级菜单间不支持排序！"), false;
        } else if (moveType == "inner") {
            return Base.alert("不支持改变菜单级次！"), false;
        }
        return confirm("是否保存对菜单顺序的修改？");
    }
    // 拖拽调整菜单顺序
    function fnOnDrop(event, treeId, treeNodes, targetNode, moveType) {
        var treeNode = treeNodes[0];
        Base.resetData("menuForm");
        Base.setDisabled("update,save,cancel");
        var pNode = treeNodes[0].getParentNode();
        var sortid = [];
        for (var i = 0; i < pNode.children.length; i++) {
            sortid.push({
                menuid : pNode.children[i].menuid
            });
        }
        Base.submit(null, "menuMgAction!webSortMenus.do", {
            sortMenuids : Ta.util.obj2string(sortid)
        }, false, false, function() {
            Base.refleshTree('menuTree', treeNode.pmenuid);
        });
    }
    // 	function fnConsolemoduleClick(type) {

    // 	}
    <%--字段权限控制--%>
    function fnFiledAuthorClick(type) {
        if (0 == type) {
            Base.hideObj("fieldBox");
        } else if (1 == type) {
            Base.showObj("fieldBox");
            Base.focus("controlFiled");
        }
    }
    <%--增加一个字段到树选中节点的子节点--%>
    function fnAddFiledToTree() {
        var treeObj = $.fn.zTree.getZTreeObj("pageFieldGatherTree");
        var selectNode = treeObj.getSelectedNodes()[0];
        var existNodes = treeObj.getNodesByFilter(filter);
        function filter(node) {
            return (node.pid == selectNode.id);
        }
        for (var i = 0; i < existNodes.length; i++) {
            if (existNodes[i].fieldid == Base.getValue("controlFiled")) {
                Base.msgTopTip("该字段已经存在");
                Base.focus("controlFiled");
                Base.setValue("controlFiled", "");
                Base.setValue("controlFiledname", "")
                return;
            }
        }
        var id = Base.getValue("controlFiled");
        if ("" == id) {
            Base.msgTopTip("录入需要控制的字段id");
            Base.focus("controlFiled");
            return;
        }
        var name = Base.getValue("controlFiledname");
        if ("" == name) {
            Base.msgTopTip("录入需要控制的字段name");
            Base.focus("controlFiledname");
            return;
        }
        var pid = selectNode.id
        Base.submit("", "menuMgAction!webSaveAuthorField.do", {
            "dto['menuid']" : Base.getValue("nowEditMenuId"),
            "dto['fieldid']" : Base.getValue("controlFiled"),
            "dto['fieldidname']" : Base.getValue("controlFiledname"),
            "dto['pid']" : pid,
            "dto['fieldlevel']" : selectNode.fieldlevel + 1
        }, false, false, function(data) {
            Base.msgTopTip("采集成功");
            treeObj.addNodes(selectNode, {
                pid : selectNode.id,
                fieldid : Base.getValue("controlFiled"),
                fieldname : Base.getValue("controlFiledname"),
                id : data.fieldData.newfield.id,
                fieldlevel : (selectNode.fieldlevel + 1)
            });

            Base.focus("controlFiled");
            Base.setValue("controlFiled", "");
            Base.setValue("controlFiledname", "");
        });

        return;
    }
    <%--判断字段权限流程--%>
    //1.字段权限控制选择是
    //2.未采集到任何字段数据
    function fnCheckFiledStatus() {
// 		if (0 == Base.getValue("isFiledsControl")) {
// 			Base.msgTopTip("请录入需要权限控制的字段");
// 			Base.focus("controlFiled");
// 			return false;
// 		} else {
// 			Base.setValue("fieldData", Ta.util.obj2string(Base.getGridAddedRows("fieldsGrid")));
// 			return true;
// 		}
        return true;
    }
    <%--重置最右边的信息--%>
    function fnResetFiledCollection() {
        Base.hideObj("fieldBox");
        var treeObj = $.fn.zTree.getZTreeObj("pageFieldGatherTree");
        var oldnodes = treeObj.transformToArray(treeObj.getNodes());
        for (var i = 0; i < oldnodes.length; i++) {
            treeObj.removeNode(oldnodes[i]);
        }
        treeObj.addNodes(null, {
            id : 1,
            pid : 1,
            fieldid : 'fieldGather',
            fieldname : '字段采集',
            fieldlevel : 0
        });
    }
    <%--左侧点击编辑按钮 最右侧显示对应的信息--%>
    function fnShowAuthorFields(data) {
        generatePageFieldGatherTreeByData(data);
        Base.showObj("fieldBox");
        Base.setDisabled("controlFiled,controlFiledname,btnAddField");
    }
    <%--根据List构造一棵树--%>
    function generatePageFieldGatherTreeByData(data) {
        var treeObj = $.fn.zTree.getZTreeObj("pageFieldGatherTree");
        var oldnodes = treeObj.transformToArray(treeObj.getNodes());
        //删除原来的树
        for (var i = 0; i < oldnodes.length; i++) {
            treeObj.removeNode(oldnodes[i]);
        }
        //新增加现在的树
        treeObj.addNodes(null, {
            id : 1,
            pid : 1,
            fieldid : 'fieldGather',
            fieldname : '字段采集',
            fieldlevel : 0
        });
        treeObj.addNodes(treeObj.getNodes()[0], data);
    }
    <%-- 点击增加按钮触发的事件  --%>
    function fnFieldTreeBeforeAdd(treeId, treeNode) {
        if(""==Base.getValue("nowEditMenuId")){
            return Base.msgTopTip("请先保存新增的菜单");
        }
        Base.setEnable("controlFiled,controlFiledname,btnAddField");
        Base.focus("controlFiled");
        Base.hideObj("btnUpdateField");
        Base.showObj("btnAddField");
        Base.setValue("controlFiled","");
        Base.setValue("controlFiledname","");
    }
    <%--点击删除节点触发事件--%>
    function fnFieldTreeBeforeRemove(treeId,treeNode){
        if(treeNode.id==1){
            Base.alert("不能删除根节点","warn");
            return false;
        }
        if (confirm("确认移除该授权字段？移除此节点则此节点的子节点也会被移除")) {
            var treeObj = $.fn.zTree.getZTreeObj("pageFieldGatherTree");
            var delNodes = treeObj.getNodesByParam("pid",treeNode.id,treeNode);
            var str = "";
            for (var i = 0; i < delNodes.length; i++) {
                str += "{\"id\":\"" + delNodes[i].id +
                    "\",\"menuid\":\"" + Base.getValue("nowEditMenuId") +
                    "\",\"fieldid\":\"" + delNodes[i].fieldid +
                    "\"},";
            }
            str += "{\"id\":\"" + treeNode.id +
                "\",\"menuid\":\"" + Base.getValue("nowEditMenuId") +
                "\",\"fieldid\":\"" + treeNode.fieldid +"\"},";
            if (str != "") {
                str = "[" + str.substr(0, str.length - 1) + "]";
            }
            Base.submit("", "menuMgAction!webDeleteAuthorField.do", {
                "dto.delFields" : str
            }, false, false, function() {
                Base.msgTopTip("移除成功");
            });
        }
        else{
            return false;
        }
    }

    function fnFiedTreeBeforeEdit(treeId,treeNode){
        if(treeNode.id==1){
            Base.alert("不能编辑根节点","warn");
            return false;
        }
        Base.setEnable("controlFiled,controlFiledname,btnUpdateField");
        Base.hideObj("btnAddField");
        Base.showObj("btnUpdateField")
        Base.setValue("controlFiled",treeNode.fieldid);
        Base.setValue("controlFiledname",treeNode.fieldname);
        Base.setValue("editIdOfFied",treeNode.id);
        return false;
    }

    <%--更新采集的字段--%>
    function fnUpdateFiledToTree(){
        Base.submit("", "menuMgAction!webUpdateAuthorField.do", {
            "dto['menuid']" : Base.getValue("nowEditMenuId"),
            "dto['id']" : Base.getValue("editIdOfFied"),
            "dto['fieldid']" : Base.getValue("controlFiled"),
            "dto['fieldname']" : Base.getValue("controlFiledname")
        }, false, false, function(data) {
            Base.msgTopTip("更新成功");
            var treeObj = $.fn.zTree.getZTreeObj("pageFieldGatherTree");
            var editNode =  treeObj.getNodeByParam("id", Base.getValue("editIdOfFied"), null);
            editNode.fieldid = Base.getValue("controlFiled");
            editNode.fieldname = Base.getValue("controlFiledname");
            treeObj.updateNode(editNode);
        });
    }
    function fnUpdateMenu(){
        /*var pmenuObj = Base.getValue('pmenuid');
        if(pmenuObj == 0){
            var treeObj = $.fn.zTree.getZTreeObj("menuTree");
            var node = treeObj.getNodeByParam(treeObj.setting.data.simpleData.idKey, Base.getValue('menuid'));
            node.menuname = Base.getValue("menuname");
            treeObj.updateNode(node);
// 			Base.refleshTree('menuTree',Base.getValue('menuid'));
        }else{
            Base.refleshTree('menuTree',pmenuObj);
        }*/
        var newdata = Base.getJson("menuMgAction!webGetAsyncAllMenu.do");
        Base.recreateTree("menuTree",null,newdata);
        //展开到指定node
        var verfyNode = Base.getObj("menuTree").getNodesByParam("menuid",Base.getValue("nowEditMenuId"),null)[0];
        //层层扩展
        expandNodeExit(verfyNode);
        if(expandNodes){
            for(var i = expandNodes.length-1;i>=0;i--){
                Base.getObj("menuTree").expandNode(expandNodes[i],true);
            }
        }
        Base.msgTopTip('更新成功');
        if(Base.getValue('isFiledsControl')=='0'){
            fnResetFiledCollection();
        }
        recreateSelectTree(newdata);
    }
    function recreateSelectTree(newdata){
        var options = {};
        options.url = "#";
        options.treeId = "selecttreepmenuname";
        options.targetDESC = "selectmenuname";
        options.targetId = "selectmenuid";
        options.selectTreeBeforeClick = fnClickPMenu;
        options.nameKey = 'menuname';
        options.idKey = 'menuid';
        options.parentKey = 'pmenuid';
        options.nodesData = newdata;
        Ta.core.TaUIManager.unregister("selecttreepmenuname");
        Ta.core.TaUIManager.unregister("selectmenuname");
        Ta.core.TaUIManager.register("selecttreepmenuname",new SelectTree("dropdownTreeBackground_selecttreepmenuname",options));

    }
    function treeEffective(treeId, treeNode){
        if(treeNode.effective == "0" && treeNode.consolemodule=="0"){
            return {'text-decoration':'line-through'};
        }
        else if(treeNode.effective == "1" && treeNode.consolemodule=="1"){
            return {'color':'#0fcbaa'};
        }
        else if(treeNode.effective == "0" && treeNode.consolemodule=="1"){
            return {'text-decoration':'line-through','color':'#0fcbaa'};
        }
    }
    //修改menuidpath,menunamepath
    function fnClickPMenu(treeId,treeNode){
        var parentMenuIdPath = treeNode.menuidpath;
        var parentMenuNamePath = treeNode.menunamepath;
        var parentMenuId = treeNode.menuid;
        Base.setValue("menuidpath",parentMenuIdPath+"/"+Base.getValue("menuid"));
        Base.setValue("menunamepath",parentMenuNamePath+"/"+Base.getValue("menuname"));
        Base.setValue("pmenuid",parentMenuId);

        //不能 选本身
        if(parentMenuId==Base.getValue("menuid")){
            Base.msgTopTip("上级功能不能是本身");
            return false;
        }
        if(1==Base.getValue("menuid")){
            Base.msgTopTip("不能修改顶级节点的父亲节点");
            return false;
        }
        return true;
    }
</script>
<%@ include file="/ta/incfooter.jsp"%>
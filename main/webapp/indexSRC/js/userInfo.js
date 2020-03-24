/**
 * Created by zhouhy
 */
var UserInfo = {
    parameters:{},
    init:function(param){
        $.extend(this.parameters,param);
        this.setLoginUserName();
        //初始化签到状态
        if(this.parameters.signstate=="1"){
            $("#sign").html("已签到");
            $("#signType").addClass("sign");
        }else{
            $("#sign").html("签到");
            $("#signType").addClass("unsign");
        }
        //国际化根据配置是否显示
        if(!this.parameters.isOpenI18n){
            $("#languageBox").addClass("hidden");
        }

        //通知信息初始化
        // this.initDwr(this.parameters.orgId,this.parameters.userId,basePath);
        initStyle("webSocket",this.parameters.orgId,this.parameters.userId,basePath,baseHost);

        //换肤
        this.changeSkin();
    },
    //隐藏用户信息菜单
    hideUserInfo:function(){
        $("#userMenu,#notice,#pwdBox,#positionBox").hide();
        $(".menuContainer").removeClass("menuActive");
        $(".user-info-control").removeClass("active")
    },
    //显示用户信息菜单
    showUserInfo:function(){
        $("#userMenu").show().addClass("userShadow");
        $(".user-info-control").addClass("active");
    },
    showNoticeInfo:function(){
        showUserInfo();
        $("#noticeMenu").click();
    },
    //退出
    userLogOut:function(){
        top.location = this.parameters.location;
    },
    //用户姓名
    setLoginUserName:function(){
        $(".userInfo .userName").html(this.parameters.loginUserName);
        $(".userInfo .userPosition").html(this.parameters.loginUserName);
    },
    //签退
    signRecordOut:function(){
        $.ajax({
            "url": basePath+"signrecord/signRecordAction!signstate.do?sign=out",
            "data": {},
            "success": function (obj) {
                var msg = obj.fieldData.signmsg;
                if (msg == "out_success") {
                    $("#signType").removeClass("sign");
                    $("#signType").addClass("unsign");
                    $("#sign").html("签到");
                    alert("签退成功");
                } else if (msg == "out_fail") {
                    alert("签退失败");
                }
            },
            "type": "POST",
            "dataType": "json"
        });
    },
    //签到
    signRecordIn:function () {
        $.ajax({
            "url": basePath+"signrecord/signRecordAction!signstate.do?sign=in",
            "data": {},
            "success": function (obj) {
                var msg = obj.fieldData.signmsg;
                if (msg == "in_success") {
                    $("#signType").removeClass("unsign");
                    $("#signType").addClass("sign");
                    $("#sign").html("已签到");
                    alert("签到成功");
                } else if (msg == "in_fail") {
                    alert("签到失败");
                }
            },
            "type": "POST",
            "dataType": "json"
        });
    },
    //切换皮肤
    //切换皮肤
    changeSkin:function(){
        $(".skin").click(function(e){
            var skinName = $(e.target).attr("data-skin-name");
            applySkin(skinName);
        });
    }
}

//退出
function logout(){
    UserInfo.userLogOut();
}

//点击签到事件
function fnUserSign(){
    if($("#signType").hasClass("unsign")){
        UserInfo.signRecordIn();
    }
    if($("#signType").hasClass("sign")){
        UserInfo.signRecordOut();
    }
}

//显示密码修改界面
function fnShowPwd(ele){
    var $ele = $(ele);
    if($ele.hasClass("menuActive")){
        $ele.removeClass("menuActive");
        $("#userMenu").addClass("userShadow");
        $("#pwdBox").hide();
    }else{
        $(".menuContainer").removeClass("menuActive")
        $ele.addClass("menuActive");
        $("#userMenu").removeClass("userShadow");
        $("#pwdBox").show();
        $("#notice,#positionBox").hide();
    }
}
//密码框取消按钮
function fnClosePass(){
    $(".menuContainer").removeClass("menuActive");
    $("#userMenu").addClass("userShadow");
    $("#oldPass").val("");
    $("#newPass").val("");
    $("#rpassword").val("");
    $("#pwdBox").hide();
}
//start密码长度验证
//onkeypress
function fnCheckOldPass(o) {
    return checkLengthOnkeypress(o, 50, "");
}
//onkeypress
function fnCheckNewPass(o) {
    return checkLengthOnkeypress(o, 50, "");
}
//控制输入字符长度
var timer;//定时器
var delay = 800;//延迟时间
function checkLengthOnkeypress(target, l, classname) {
    //控制长度
    if (!checkPwdLengthOnkeypress(target, l)) {
        toNotice();
        timer = setTimeout(function () {
            clearNotice();
        }, delay);
        return false;
    } else {
            return true;
    }
}
function clearNotice() {
    $("#validTip").css("visibility","hidden");
    timer = null;
}
function toNotice() {
    if (timer) {
        clearTimeout(timer);
    } else {
        $("#validTip").css("visibility","visible");
    }
}
//end 修改密码长度控制 xp
//保存密码
function fnSavePass() {
    var oldPass = $("#oldPass").val();
    var newPass = $("#newPass").val();
    var rpassword = $("#rpassword").val();
    if (oldPass == "") {
        alert("原密码不能为空！");
        return;
    }
    if (newPass == "") {
        alert("新密码不能为空！");
        return;
    }
    //密码复杂度检查
    var complexityMsg = checkPwdComplexity(newPass);
    if (complexityMsg && complexityMsg.length > 0) {
        alert(complexityMsg);
        return;
    }
    if (rpassword == "") {
        alert("密码确认不能为空！");
        return;
    }
    if (newPass != rpassword) {
        alert("两次输入的密码不一致，请检查！");
        return;
    }

    if(UserInfo.parameters.passwordRSA) {
        oldPass = encryptPwd(oldPass);
        newPass = encryptPwd(newPass);
    }

    var d = "dto['oldPass']=" + oldPass + "&dto['newPass']=" + newPass
        + "&indexChangePass=1";
    $.ajax({
        "url": basePath+"indexAction!changePassword.do",
        "data": d,
        "success": function (data) {
            if (data.resultMessage) {
                alert(data.resultMessage.message);
                if (data.resultMessage.messageType != "error") {
                    fnClosePass();
                }
            } else {
                fnClosePass();
            }
        },
        "type": "POST",
        "dataType": "json"
    });
}
function encryptPwd(pwd) {
    return encryptedString(key, encodeURI(pwd));
}

//显示岗位切换页面
function fnShowChangePosition(ele) {
    var $ele = $(ele);
    if($ele.hasClass("menuActive")){
        $ele.removeClass("menuActive");
        $("#userMenu").addClass("userShadow");
        $("#positionBox").hide();
    }else{
        $(".menuContainer").removeClass("menuActive")
        $ele.addClass("menuActive");
        $("#userMenu").removeClass("userShadow");
        initPositionList();
        $("#positionBox").show();
        $("#pwdBox,#notice").hide();
    }
}
//初始化岗位列表
function initPositionList() {
    var html = "";
    var positions = UserInfo.parameters.positions;
    $(".positionCount").html("目前岗位数:" + positions.length + "个");
    for(var i=0;i<positions.length;i++){
        var p = positions[i];
        var id = 'p_'+p.positionid;
        var _id = p.positiontype;
        var _name = p.positionname;
        if(UserInfo.parameters.nowPosId==p.positionid){
            html += "<div id='"+id+"' _id='"+_id+"' _name='"+_name+"' class='postDiv'><a class='postNow'>当前</a>" + p.positionname + "</div>";
        }else{
            html += "<div id='"+id+"' _id='"+_id+"' _name='"+_name+"' class='postDiv' onclick='fnSelectPosition(this)'><span class='postRadio'></span>" + p.positionname + "</div>";
        }
    }
    html += "<div style='clear:both;'></div>";
    $("#positionList").html(html)
}
//选择岗位
function fnSelectPosition(o) {
    var $o = $(o);
    if(!$o.hasClass("active")){
        
        $(".postDiv").removeClass("active");
        $(".postDiv span").removeClass("faceIcon icon-correct2 postClick");
        $(".postDiv span").addClass("postRadio");


        $o.children("span").eq(0).removeClass("postRadio");
        $o.children("span").eq(0).addClass("faceIcon icon-correct2 postClick");
        $o.addClass("active");
    }else{
        $o.children("span").eq(0).addClass("postRadio");
        $o.children("span").eq(0).removeClass("faceIcon icon-correct2 postClick");
        $o.removeClass("active");
    }
}
//取消选择岗位
function fnClosePost() {
    $(".menuContainer").removeClass("menuActive");
    $("#userMenu").addClass("userShadow");
    $("#positionBox").hide();
}
//保存岗位选择
function fnSavePost(){

    var $ele = $(".postDiv.active");

    if($ele.length>0) {
        var positionid = $ele.attr("id").substring(2);
        $.ajax({
            "data": "__positionId=" + positionid,
            "url": "commonAction.do",
            "success": function (data) {
                $(".userInfo .userName").html($ele.attr("_name"));
                $(".userInfo .userPosition").html($ele.attr("_name"));
                UserInfo.parameters.nowPosId=positionid;
                alert("切换成功");
                fnClosePost();
            },
            "type": "POST",
            "dataType": "json"
        });
    } else {
        alert("请选择要切换的岗位。");
    }
}
//国际化切换
function changeLocale(div) {
    $.ajax({
        url: 'i18n/changeLocale.do?langType=' + div.id,
        type: 'POST',
        success: function (data) {
            location.reload();
        }
    });
}
//常用菜单管理
function fnToMangeCommMenu(div){
    indexTab.addTab("02","常用菜单管理","commonMenuAction!toAddCommonMenus.do",true);
}
//展示用户帮助信息
function fnShowUserGuide(){
    UserInfo.hideUserInfo();
    toShowPageGuide();
}

/**
 *通知
 */
//显示通知界面
function fnShowNotice(ele){
    var $ele = $(ele);
    if($ele.hasClass("menuActive")){
        $ele.removeClass("menuActive");
        $("#userMenu").addClass("userShadow");
        $("#notice").hide();
    }else{
        $(".menuContainer").removeClass("menuActive")
        $ele.addClass("menuActive");
        $("#userMenu").removeClass("userShadow");
        $("#notice").show();
        $("#pwdBox,#positionBox").hide();
    }
}
//显示通知未读条数
function mgPoint(){
    var newMg = 0;
    for(var i=0;i<mgRecord.length;i++){
        if(mgRecord[i].state != '1'){
            newMg++;
        }
    }
    if(newMg){
        $("#noticeHead").html("通知("+"<span>"+newMg+"</span>"+"条未读)"+"<a onclick='setAllMgRead()'>全部标记已读</a>");
        $(".mgTip").remove();
        if(newMg>99){
            $("#mgTitle").after("<div class='mgTip'>99+</div>");
        }else{
            $("#mgTitle").after("<div class='mgTip'>"+newMg+"</div>");
        }
    }else{
        $(".mgTip").remove();
        $("#noticeHead").html("通知");
    }
}
//显示通知内容
function showMgContent(ele,index){
    //$(ele).parent().hasClass("unread")
    var $mgDiv = $(ele).parent().parent();
    var mgRid = mgRecord[index].mgid;
    var html = "<div class='mgContent'>"+mgRecord[index].content+"</div>";
    if($mgDiv.find(".mgContent").length<=0){
        $mgDiv.append(html);
    }else{
        $mgDiv.find(".mgContent").show();
    }
    // $mgDiv.find(".mgContent").stop(true, true).slideDown();

    if($(ele).parent().hasClass("unread")) {
        $.ajax({
            "url": basePath + "message/messageSend!setMg2sign.do",
            "data": {"dto['mgid']": mgRid},
            "type": "POST",
            "dataType": "json"
        });
        mgRecord[index].state = 1;
        mghead_r = 0;
    }
    $(ele).parent().removeClass("unread").addClass("read");
    mgPoint();
    $(ele).context.outerHTML='<span onclick="hideMgContent(this,'+index+')">收起<a class="faceIcon icon-arrow_up"></a></span>';
}
//收起通知内容
function hideMgContent(ele,index){
    $mgDiv = $(ele).parent().parent();
    // $mgDiv.find(".mgContent").stop(true, true).slideUp();
    $mgDiv.find(".mgContent").hide();
    $(ele).context.outerHTML='<span onclick="showMgContent(this,'+index+')">展开<a class="faceIcon icon-arrow_down"></a></span>';
}
//通知全部标记已读
function setAllMgRead() {
    $("li.unread").each(function(num,ele){
        var index = $(ele).parent().index();
        $.ajax({
            "url": basePath + "message/messageSend!setMg2sign.do",
            "data": {"dto['mgid']": mgRecord[index].mgid},
            "type": "POST",
            "dataType": "json",
            "async": false
        });
        mgRecord[index].state = 1;

        $(ele).removeClass("unread").addClass("read");

    });
    mghead_r = 0;
    mgPoint();
}
/*//这个函数是提供给后台推送的时候  调用的
function messageDwr(msg){
    console.log(msg);
    mgRecord.push(msg);
}*/
/**
 * 通知初始化
 */
var messageServerPath="http://localhost:8080";
var messageServerPath_ws="localhost:8080";
function initStyle(type,orgid,userid,basePath,baseHost){
    messageServerPath = basePath;
    messageServerPath_ws = baseHost+basePath;
    now_userid = userid;

    if(type=="dwr"){
        initDwr(orgid,userid,basePath);
    }else if(type=="webSocket"){
        initWebSocket(userid);
    }else{
        alert("未选择通知使用类型");
    }
    fngetMessage(basePath);
}
var mgRecord = [];  //保存通知
var mghead_r = 0;   // 是否重新获取通知
var mgRid; //记录当前打开的通知 id
var now_userid;

function mgPoint_box(msg){
    Notice.initNoticeWindow(msg.title,msg.content,"消息通知. . . from:"+msg.name,5000)
}
/* 首页通知 通用function  end */
/* dwr */
function initDwr(id,userid,basePath){
    // dwr.engine.setOverridePath(messageServerPath+"/dwr");
    dwr.engine.setActiveReverseAjax(true);
    dwr.engine.setNotifyServerOnPageUnload(true);
    dwr.engine.setErrorHandler(function(message, ex){
        dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
    });
    var intervalsArray = [1,3,10];
    dwr.engine.setRetryIntervals(intervalsArray);
    dwr.engine.setMaxRetries(100);
    DwrService.onPageLoad(id,userid);

}
/**
 * 页面加载查询通知 dwr
 * @param systemid
 * @param userid
 */
function fngetMessage(basePath){
    $.ajax({
        "url" : basePath+"message/messageSend!getMg.do",
        "data":"",
        "success":function(data){
            if(data){
                mgRecord = data.fieldData.datalist;
                mghead_r = 0;
                mgPoint();
                if(!mghead_r){
                    if(mgRecord.length){
                        var html ="";
                        for(var i=0;i<mgRecord.length;i++){
                            if(mgRecord[i].state == 1){
                                html+="<div><li class='read'>"+mgRecord[i].title+"<span onclick='showMgContent(this,"+i+")'>展开<a class='faceIcon icon-arrow_down'></a></span></li>"+
                                    "<div class='mgDate'>"+mgRecord[i].mgdate+"<span>from:"+mgRecord[i].name+"</span></div></div>";
                            }else{
                                html+="<div><li class='unread'>"+mgRecord[i].title+"<span onclick='showMgContent(this,"+i+")'>展开<a class='faceIcon icon-arrow_down'></a></span></li>"+
                                    "<div class='mgDate'>"+mgRecord[i].mgdate+"<span>from:"+mgRecord[i].name+"</span></div></div>";
                            }
                        }
                        $("#noticeList").html(html);
                    }else{
                        $("#noticeList").html("<h1>暂无通知</h1>");
                    }
                    mghead_r=1;
                }
            }
        },
        "type":"POST",
        "dataType":"json"
    });
}
/**
 * 附件下载
 * @param fileid
 */
function mgAtmDownload(fileid){
    var url = "<%=basePath%>message/messageSend!mgAtmDownload.do" +"?_download_fileName="+name+"&_download_file="+mgRid;
    location.href = url;

}
/**
 * 服务器消息推送 dwr
 * @param msg
 */
function messageDwr(msg){
    console.log(msg);
    mgRecord.push(msg);
    mghead_r = 0;
    mgPoint_box(msg);
    mgPoint();

    //新通知放到页面上
    var html="<div><li class='unread'>"+msg.title+"<span onclick='showMgContent(this,"+(mgRecord.length-1)+")'>展开<a class='faceIcon icon-arrow_down'></a></span></li>"+
        "<div class='mgDate'>"+msg.mgdateShow+"<span>from:"+msg.name+"</span></div></div>";
    if(mgRecord.length==1){
        $("#noticeList").html(html);
    }else{
        $("#noticeList").html(html+$("#noticeList").html());
    }
}
/*dwrEnd*/
/* webSokcet*/
function initWebSocket(userid){
    var websocket;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://"+messageServerPath_ws+"/webSocketServer");
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://"+messageServerPath_ws+"/webSocketServer");
    } else {
        // websocket = new SockJS("http://"+messageServerPath_ws+"/sockjs/webSocketServer");
    }
    if(typeof websocket !== "undefined"){
        websocket.onopen = function(event) {
            console.log("webSocket open");
            websocket.send(userid);
        };
        websocket.onmessage = function(event) {
            messageDwr(JSON.parse(event.data));
        };
        websocket.onerror = function(event) {
            console.log("webSocket连接断开");
        };
        websocket.onclose = function(event) {
            console.log("webSocket连接关闭");
        }
    }
}
/*webSokcet end*/



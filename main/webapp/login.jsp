﻿<%@page import="com.yinhai.core.common.api.config.impl.SysConfig"%>
<%@ page import="com.yinhai.core.common.api.base.IConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/ta/inc-path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=10" />
<link href="${pageContext.servletContext.contextPath }/loginSRC/css/master.css" rel="stylesheet"
    type="text/css" />
    <%--登陆页面样式覆盖--%>
    <link href="${pageContext.servletContext.contextPath }/projectCover/login_cover/login_cover.css" rel="stylesheet"/>
<title>登录</title>
<script src="loginSRC/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="loginSRC/js/Barrett.js" type="text/javascript"></script>
<script src="loginSRC/js/BigInt.js" type="text/javascript"></script>
<script src="loginSRC/js/jQuery.md5.js" type="text/javascript"></script>
<script src="loginSRC/js/RSA.js" type="text/javascript"></script>
<script src="<%=facePath%>lib/clientEnvironment/clientEnvironment.js" type="text/javascript"></script>
<script src="loginSRC/js/passwordCheck.js" type="text/javascript"></script>
<script src="loginSRC/js/SlideCheckCode.js" type="text/javascript"></script>

    <%
    String postId=String.valueOf(Math.random());
    request.getSession(false).setAttribute("_POSTID",postId);           
    response.addHeader("Set-Cookie", "POSTID="+postId+";Path="+request.getContextPath()+";HttpOnly");
    
    //错误码解析
    String errorcode = (String)request.getParameter("errorcode");
    String errorcodemsg="";
    if("001".equals(errorcode)){
        errorcodemsg="登录超时，请重新登录";
    }else if("002".equals(errorcode)){
        errorcodemsg="当前账户已在其他地方登录";
    }

    //验证码配置
    boolean useCheckCode = SysConfig.getSysConfigToBoolean("useCheckCode", false);
    String checkCodeType = SysConfig.getSysConfig("checkCodeType",IConstants.CHECKCODE_TYPE_NUMBER);
    %>
    <script>
        var developMode = <%=SysConfig.getSysConfig("developMode")%>;
        //获取配置的是否启用验证码
        var useCheckCode = <%=useCheckCode%>;
        //获取配置的验证码模式
        var checkCodeType = "<%=checkCodeType%>";
        var isNumberCheckCode = <%=IConstants.CHECKCODE_TYPE_NUMBER.equals(checkCodeType)%>;
        var isSlideCheckCode = <%=IConstants.CHECKCODE_TYPE_SLIDE.equals(checkCodeType)%>;
    </script>
</head>
<body onselectstart="return false;">
    <div class="top-title">
        <div class="logo"></div>
        <span>通用综合查询统计平台</span>
    </div>
    <img id="bgimgimg" src="${pageContext.servletContext.contextPath }/loginSRC/img/bg2.png">
    <div class="container">
        <!-- top start -->
        <!-- 
        <div class="top" id="top">
            <span class="time">今天是:${date}</span> <a
                onclick="fnSetHome(this.window.location.pathname)" class="home"
                href="javascript:void(0)">设为首页</a> <a
                onclick="fnAddFavorite(window.location,document.title)"
                class="collect" href="javascript:void(0)">加入收藏</a>
        </div>
         -->
        <!-- top end -->
        <!-- banner start -->
        <!-- 
        <div class="banner" id="banner">
            <img class="banner-img" src="loginSRC/images/dl_top.jpg">
        </div>
         -->
        <!-- banner end -->
        <!-- main start -->
        <div class="main" id="main">
            <div class="loginFormPL">
                <%--<img class="logoimg" src="${pageContext.servletContext.contextPath }/loginSRC/img/logo2.png"/>--%>
                <div class="loginform">
                    <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="logintitle">用户登录</td>
                        </tr>
                        <tr>
                            <td id="validate" class="validate" >${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message} </td>
                        </tr>
                        <tr>
                            <td><input type="text" id="j_username"
                                onkeyup="usernameKeyup(this)" placeholder='用户名' onkeydown="revertValidate(this)"
                                name="j_username" class="username" border="0"  /></td>
                        </tr>
                        <tr>
                            <td class="tdText"><span class="fontCn"></span><span
                                class="fontccc"></span></td> 
                        </tr>
                        <tr>
                            <td><input type="password" id="j_password" autocomplate="off"
                                onkeyup="passwordKeyup(this)" placeholder='密码' onkeydown="revertValidate(this)"
                                name="j_password" class="password" border="0"/></td>
                        </tr>
                        <tr>
                            <td class="tdText"><span class="fontCn"></span><span
                                class="fontccc"></span></td>
                        </tr>
                        <%if(useCheckCode){%>
                        <%if(IConstants.CHECKCODE_TYPE_NUMBER.equals(checkCodeType)){%>
                        <tr style="display: none;" id="checkCodeDiv">
                            <td><input type="text" id="checkCode"
                                       onkeyup="checkCodeKeyup(this)" placeholder='验证码' onkeydown="revertValidate(this)"
                                       name="checkCode" class="code" border="0" style="float:left;"  value="11"/>
                                <a href="#"><img id="codeimg"
                                                 onclick="javascript:refeshCode();" src="CaptchaImg"
                                                 title="点击获取验证码" style="float:left;width:95px;height:34px;margin-left:12px;border:1px solid #dcdcdc;" /></a></td>
                        </tr>
                        <%}%>
                        <%if(IConstants.CHECKCODE_TYPE_SLIDE.equals(checkCodeType)){%>
                        <tr style="display: none" id="checkCodeDiv">
                            <td>
                                <div class="codeDragValidate-layout-div">
                                    <label>验证码</label>
                                    <div class="codeDragBar-drag-div">
                                        <div class="codeDrag-win-div">
                                            <div class="codeDrag-win-div-body">
                                                <div class="codeDrag-bg-img-div">
                                                    <img class="codeDrag-code-img" src="#" alt="背景图"/>
                                                    <img class="codeDrag-darg-img" src="#" alt="拖动图"/>
                                                </div>
                                                <div class="codeDrag-code-refresh" ></div>
                                            </div>
                                        </div>
                                        <div class="dragBar">
                                            <span></span>
                                        </div>
                                        <div class="dragBar-inDrag-bg"></div>
                                        <div class="dragBar-base-bg">
                                            <span>向右滑动登陆</span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <%}%>
                        <%}%>
                        <tr>
                            <td></td>
                    </tr>
                    <tr id="登陆">
                        <td><input type="button" id="submit" 
                            class="submit" border="0" value="登录" onclick="login()"/></td>
                    </tr>
                    <tr>
                        <td style="text-align:right;">
                            <a href="#" onclick="fnOpenModifyPsd()" id="changePass" >修改密码</a>
                        </td>
                    </tr>
                </table>
            </div>
            </div>
        </div>
        <!-- main end -->
        <!-- footer start -->
        <!-- 
        <div class="footer" id="footer">
            技术支持：四川银海软件股份有限责任公司<br /> Surport:SiChuan YinHai Software Limited By
            Share Ltd
        
        </div>
         -->
        <!-- footer end -->
    </div>

    <!-- 遮罩层start -->
    <div class="mask" id="mask"></div>
    <!-- 遮罩层end -->

    <!-- 密码修改框 start-->
    <div class="psdmodify" id="psdmodify">
        <div class="title">
            密码修改<a href="javascript:;" onclick="fnCloseModifyPsd()" class="close"
                title="关闭"></a>
        </div>
        <form id="psdmodifyform">
            <table cellpadding="0" cellspacing="0" width="0">
                <tr>
                    <td class="lable">用户名</td>
                    <td><input id="loginId" name="dto['loginId']" required="true"
                        type="text" title="用户名"  onkeyup="loginIdKeyup(this)" /></td>
                </tr>
                <tr>
                    <td class="lable">密码</td>
                    <td><input name="dto['oldPass']" type="password" id="oldPass"
                        required="true" title="密码" onkeyup="oldPassKeyup(this)" autocomplate="off" /></td>
                </tr>
                <tr>
                    <td class="lable">新密码</td>
                    <td><input type="password" id="newPass" name="dto['newPass']"
                        required="true" title="新密码" onkeyup="newPassKeyup(this)" autocomplate="off" /></td>
                </tr>
                <tr>
                    <td class="lable">确认密码</td>
                    <td><input type="password" id="rpassword"
                        onkeyup="rpasswordKeyup(this)" name="dto['rpassword']"
                        required="true" title="确认密码" autocomplate="off" /></td>
                </tr>
                <%if(useCheckCode){%>
                <%if(IConstants.CHECKCODE_TYPE_NUMBER.equals(checkCodeType)){%>
                <tr>
                    <td class="lable">验证码</td>
                    <td><input id="checkCodePass" name="dto['checkCodePass']"
                               onkeyup="checkCodePassKeyup(this)" type="text" class="captcha"
                               title="验证码" /><img id="codeimgPass"  class="numberCodeImg"
                                                  onclick="javascript:refeshCodePass();"
                                                  src="" title="看不清,换一张" /></td>
                </tr>
                <%}%>
                <%if(IConstants.CHECKCODE_TYPE_SLIDE.equals(checkCodeType)){%>
                <tr id="modifyCheckCodeDiv">
                    <td class="lable">
                        验证码
                    </td>
                    <td colspan="2">
                        <div class="codeDragValidate-layout-div" style="width: 262px">
                            <div class="codeDragBar-drag-div">
                                <div class="codeDrag-win-div">
                                    <div class="codeDrag-win-div-body">
                                        <div class="codeDrag-bg-img-div">
                                            <img class="codeDrag-code-img" src="#" alt="背景图"/>
                                            <img class="codeDrag-darg-img" src="#" alt="拖动图"/>
                                        </div>
                                        <div class="codeDrag-code-refresh" ></div>
                                    </div>
                                </div>
                                <div class="dragBar">
                                    <span></span>
                                </div>
                                <div class="dragBar-inDrag-bg"></div>
                                <div class="dragBar-base-bg">
                                    <span>向右滑动确认修改</span>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <%}%>
                <%}%>

                <tr>
                    <td class="lable">&nbsp;</td>
                    <td><input type="button" class="submit" border="0"
                        value="确认修改" id="modifyPsd" onclick="fnSavePass()" /></td>
                </tr>
                <tr>
                    <td colspan="2" id="errornotice"></td>
                </tr>
            </table>
        </form>
    </div>
    <!--        <script type="text/javascript" src="http://118.112.188.108:8808/s/7fe82ab90965234e6bfb4a4f20a764ae-T/zh_CN-ropdih/64016/15/1.4.25/_/download/batch/com.atlassian.jira.collector.plugin.jira-issue-collector-plugin:issuecollector/com.atlassian.jira.collector.plugin.jira-issue-collector-plugin:issuecollector.js?locale=zh-CN&collectorId=671b2d72"></script> -->
</body>

<script type="text/javascript">
    if (top != this)top.location.href = "ta/login.jsp";
    window.onload = function(){//等待页面内DOM、图片资源加载完毕后触发执行
        var errorcodemsg = "<%=errorcodemsg%>";
        var validate=document.getElementById("validate");
        if(errorcodemsg){
            validate.innerHTML = errorcodemsg;
        }
        var m=document.getElementById("validate")
        
        if(validate.innerHTML != undefined && validate.innerHTML != null && $.trim(validate.innerHTML) != ""){
            validate.className = "validateError";
        }
        document.getElementById("j_username").focus();
        //refeshCode();
        bodyRSA();
        //页面加载完成之后
        if(useCheckCode){
            if(isSlideCheckCode){
                window.ModifySlideCheckCode = new SlideCheckCode("modifyCheckCodeDiv",{
                    successCallBack : function(){
                        fnSavePass();
                    }
                });
                window.LoginSlideCheckCode = new SlideCheckCode("checkCodeDiv",{
                    initImg:true,
                    successCallBack : function () {
                        login();
                    }
                });
            }
            fnPasswordValidationErrorNumber();
        }
    }
    function fnPasswordValidationErrorNumber(){
        //是否显示验证码输入框
        var passwordValidationErrorNumberSession = '${passwordValidationErrorNumber}';

        if (passwordValidationErrorNumberSession == '') {
            passwordValidationErrorNumberSession = 0;
        }
        var passwordValidationErrorNumberConfig = "<%=SysConfig.getSysConfigToInteger("passwordValidationErrorNumber",0)%>";
        if (passwordValidationErrorNumberSession == passwordValidationErrorNumberConfig) {
            window.loginCheckCodeShow = true;
            document.getElementById("checkCodeDiv").style.display = "block";
        }else{
            window.loginCheckCodeShow = false;
        }
    }

    var key ;
    function bodyRSA(){
      setMaxDigits(130);
      key = new RSAKeyPair("10001","","906C793510FB049452764740B21B97A51DAEA794AB6E43836269D5E6317D49226C12362BA22DAB5EC3BC79553A8A098B01F3C4D81A87B3EE5BD2F4F1431CC495EE2FE54688B212145BB32D56EEEEE1430CE26234331B291CFC53C9B84FAFFDF0B44371A032880C3D567F588D2CD5FCE28D9CDD2923CB547DAD219A6A1B8B5D3D"); 
    }
    
    function encryptPwd(pwd){
       return   encryptedString(key,encodeURI(pwd));
    }
    
    //账号keyup事件
    function usernameKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                document.getElementById("j_password").focus();
            }
        }   
    }
    //密码keyup事件
    function passwordKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                if(useCheckCode && isNumberCheckCode && loginCheckCodeShow) document.getElementById("checkCode").focus();
                else login();
            }
        }   
    }
    //验证码keyup事件
    function checkCodeKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                login();
            }
        }   
    }   
    //修改密码页，用户名keyup事件
    function loginIdKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                document.getElementById("oldPass").focus();
            }
        }   
    }
    function oldPassKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                document.getElementById("newPass").focus();
            }
        }   
    }
    function newPassKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                document.getElementById("rpassword").focus();
            }
        }
    }
    function rpasswordKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                if(useCheckCode && isNumberCheckCode)document.getElementById("checkCodePass").focus();
                else fnSavePass();
            }
        }
    }
    function checkCodePassKeyup(obj){
        var str=obj.value;
        if(str!=null||str!=""){
            if(event.keyCode==13){
                fnSavePass();
            }
        }
    }
    function login() {
        var username = document.getElementById("j_username").value;
        var pass = document.getElementById("j_password").value;
        var checkcode;
        if (username == "" || username == undefined) {
            toNotice("validate","validateError","请输入用户名");
            j_username.style.border = "1px solid #ef7d1b";
            document.getElementById("j_username").focus();
            return;
        }else{
            clearNotice("validate");
        }
        if (pass == "" || pass == undefined) {
            toNotice("validate","validateError","请输入密码");
            j_password.style.border = "1px solid #ef7d1b";
            document.getElementById("j_password").focus();
            return;
        }else{
            clearNotice("validate");
        }

        if(useCheckCode && isNumberCheckCode){
            checkcode = document.getElementById("checkCode").value;
            if (checkcode == "" || checkcode == undefined) {
                toNotice("validate","validateError","请输入验证码");
                checkCode.style.border = "1px solid #ef7d1b";
                document.getElementById("checkcode").focus();
                return;
            }else{
                clearNotice("validate");
            }
        }

        
        <%if ("true".equals(SysConfig.getSysConfig("passwordRSA", "false"))) {%>
            pass = encryptPwd(pass);
        <%}%>
        submit.disabled = "disabled";
        submit.value = "登录中...";
        submit.className = submit.className + " disable";
        var xmlhttp;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        } else {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }


        xmlhttp.open("POST", "j_spring_security_check" ,true);
        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlhttp.setRequestHeader("x-requested-with", "XMLHttpRequest");
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                try {
                    submit.disabled = "disabled";
                    window.location.href = "indexAction.do";
                } catch (error) {
                    toNotice("validate","validateError","发生异常，请刷新后重试，或联系管理员");
                } finally {
                    enableSubmitButton();
                    refeshCode();
                }

            } else if (xmlhttp.readyState == 4) {
                enableSubmitButton();
                refeshCode();
            } else if(xmlhttp.status == 500){ //非 security 异常的处理
                toNotice("validate","validateError","系统正在维护，请稍后重试");
                refeshCode();
            }
        }
        xmlhttp.send("username=" + username +
                "&password=" + pass +
                "&checkCode=" + checkcode +
                "&clientsystem="  + ClientEnvironment.SystemInfo.System +
                "&clientscreensize=" + ClientEnvironment.SystemInfo.ScreenSize +
                "&clientbrowser=" + ClientEnvironment.BrowserInfo.Browser+ClientEnvironment.BrowserInfo.verinNum);
    }
    function hasClass(obj, cls) {  
        return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));  
    }  
    function addClass(obj, cls) {  
     if (!this.hasClass(obj, cls)) obj.className += " " + cls;  
    }  
    function removeClass(obj, cls) {  
        if (hasClass(obj, cls)) {  
            var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');  
        obj.className = obj.className.replace(reg, ' ');  
        }
     }
    
    
    
    //控制输入字符长度
    var timer;//定时器
    var delay = 800;//延迟时间
    function checkLengthOnkeypress(target,l,notice,classname){
        
        //控制长度
        if(!checkPwdLengthOnkeypress(target,l)){
            toNotice(notice,classname,"输入已达长度限制，不能继续输入！");
            timer = setTimeout(function(){
                clearNotice(notice);
            },delay);
            return false;
        }else{
            return true;
        }
    }
    
    function clearNotice(notice){
        var errornotice= document.getElementById(notice);
        
        if(notice == "errornotice"){
            //修改密码消息反馈
            removeClass(errornotice,"errornotice");
            removeClass(errornotice,"warnnotice");
        }else{
            //登录消息反馈
            removeClass(errornotice,"validateError");
            removeClass(errornotice,"validateWarn");
        }
        errornotice.innerHTML="";
        timer = null;
    }
    function toNotice(notice,classname,msg){
        if(timer)clearTimeout(timer);
        var errornotice=document.getElementById(notice);
        clearNotice(notice);
        
        addClass(errornotice,classname);
        errornotice.innerHTML=msg;
    }
    
    //=====================登录输入长度控制=================
        
    $("#j_username").keypress(function(e){
        var target = e.target || e.srcElement;
        var notice = "validate";
        var classname = "validateWarn";
        return checkLengthOnkeypress(target,20,notice,classname);
    });
    $("#j_password").keypress(function(e){
        var target = e.target || e.srcElement;
        var notice = "validate";
        var classname = "validateWarn";
        return checkLengthOnkeypress(target,50,notice,classname);
    });
    $("#checkCode").keypress(function(e){
        if (e.which == 13)return;
        var target = e.target || e.srcElement;
        var notice = "validate";
        var classname = "validateWarn";
        <%
        String captchaCheckCode = (String) SysConfig.getSysConfig("numberCheckCodeLevel","1");
        %>
        var code = <%=captchaCheckCode%>;
        
        return checkLengthOnkeypress(target,parseInt(code/2 + 3),notice,classname);
        
    });
    
    //=====================修改密码输入长度控制=================
    $("#loginId").keypress(function(e){
        var target = e.target || e.srcElement;
        var notice = "errornotice";
        var classname = "warnnotice";
        return checkLengthOnkeypress(target,20,notice,classname);
    });
    $("#oldPass,#newPass,#rpassword").keypress(function(e){
        var target = e.target || e.srcElement;
        var notice = "errornotice";
        var classname = "warnnotice";
        return checkLengthOnkeypress(target,50,notice,classname);
    });
    $("#checkCodePass").keypress(function(e){
        if (e.which == 13)return;
        var target = e.target || e.srcElement;
        var notice = "errornotice";
        var classname = "warnnotice";
        <%
        String captcha = (String) SysConfig.getSysConfig("numberCheckCodeLevel","1");
        %>
        var code = <%=captcha%>;

        return checkLengthOnkeypress(target,parseInt(code/2 + 3),notice,classname);
        
    });
    
    function fnSavePass() {
        var d = "";
        var loginId=document.getElementById("loginId").value;
        var oldPass=document.getElementById("oldPass").value;
        var newPass=document.getElementById("newPass").value;
        var rpassword=document.getElementById("rpassword").value;
        var modifyPsd=document.getElementById("modifyPsd");
        var checkCodePass="";

        var notice = "errornotice";
        var errorClass = "errornotice";
        //var errornotice=document.getElementById("errornotice");
        if(loginId == "" || loginId == null || loginId == undefined){
            toNotice(notice,errorClass,"用户名不能为空");
            document.getElementById("loginId").focus();         
            return;
        }else{
            clearNotice(notice);
        }
        if(oldPass == "" || oldPass == null || oldPass == undefined){
            toNotice(notice,errorClass,"密码不能为空");
            document.getElementById("oldPass").focus();
            return;
        }else{
            clearNotice(notice);
        }
        if(newPass == "" || newPass == null || newPass == undefined){
            toNotice(notice,errorClass,"新密码不能为空");
            document.getElementById("newPass").focus();
            return;
        }else if(newPass == oldPass){
            toNotice(notice,errorClass,"新密码不能与老密码一致");
            document.getElementById("newPass").focus();
            return;
        }else{
            clearNotice(notice);
        }
        
        //密码复杂度检查
        var complexityMsg = checkPwdComplexity(newPass);
        if(complexityMsg && complexityMsg.length > 0){
            toNotice(notice,errorClass,complexityMsg);
            document.getElementById("newPass").focus();
            return;
        }else{
            clearNotice(notice);
        }
        
        if(rpassword == "" || rpassword == null || rpassword == undefined){
            toNotice(notice,errorClass,"确认密码不能为空");
            document.getElementById("rpassword").focus();           
            return;
        }else if(rpassword!= newPass){
            toNotice(notice,errorClass,"密码必须一致");
            document.getElementById("rpassword").focus();   
            return;
        }else{
            clearNotice(notice);
        }
        if(useCheckCode && isNumberCheckCode){
            var checkCodePass=document.getElementById("checkCodePass").value;
            if(checkCodePass == "" || checkCodePass == null || checkCodePass == undefined){
                toNotice(notice,errorClass,"验证码不能为空");
                document.getElementById("checkCodePass").focus();
                return;
            }else{
                clearNotice(notice);
            }
        }

        modifyPsd.disabled = "disabled";
        modifyPsd.value = "修改中...";
        modifyPsd.className = modifyPsd.className + " disable";
        <%if ("true".equals(SysConfig.getSysConfig("passwordRSA", "false"))) {%>
        var _oldPass=encryptPwd(document.getElementById("oldPass").value);
        var _newPass=encryptPwd(document.getElementById("newPass").value);          
        <%} else {%>
        var _oldPass=document.getElementById("oldPass").value;
        var _newPass=document.getElementById("newPass").value;  
        <%}%>
        var changeLoginId=document.getElementById("loginId").value;
        d += "dto['loginId']="+changeLoginId+"&dto['oldPass']="+_oldPass+"&dto['newPass']="+_newPass+"&dto['checkCodePass']="+checkCodePass;
        var xhr = new XMLHttpRequest();
        xhr.open("post", "indexAction!changePassword.do", true);
        xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xhr.setRequestHeader("x-requested-with", "XMLHttpRequest");
        xhr.onreadystatechange=function(){
            if (xhr.readyState == 4 && xhr.status==200) {
                    var data = eval("(" + xhr.responseText + ")");
                    modifyPsd.disabled = false;
                    modifyPsd.value = "确认修改";
                    modifyPsd.className = "submit";
                    if(data.resultMessage){
                        if(data.resultMessage.messageType !="error"){
                            alert(data.resultMessage.message);
                            fnCloseModifyPsd();
                            document.getElementById("j_username").value=changeLoginId;
                            document.getElementById("j_password").value="";
                        }else{
                            toNotice(notice,errorClass,data.resultMessage.message);
                        }
                    }else{
                        fnCloseModifyPsd();
                    }
                refeshCodePass();
            }

        };
        xhr.send(d);
    }
    
//  function checkRequired($o,val){
//      if(val == "" || val == null || val == undefined){
//          $("#errornotice").addClass("errornotice").html($o.attr("title")+"不能为空");
//          $o.focus();
//      }
//  }
    function checkRequired($o,val){
        if(val==""||val==null||val==undefined){
            var error=document.getElementById("errornotice");
            addClass(error, "errornotice");
            error.innerHTML($o.getAttribute("title")+"不能为空");
            $o.focus();
        }
    }

    function fnOpenModifyPsd() {
        var tp = windowHeight() - 342 >= 0 ? (windowHeight() - 342) / 2 : 0;
        var lt = windowWidth() - 402 >= 0 ? (windowWidth() - 402) / 2 : 0;
        //$('#mask').show();
        mask.style.display = "block";
        psdmodify.style.top = tp + "px";
        psdmodify.style.left = lt + "px";
        psdmodify.style.display = "block";
        //$("#psdmodify").css({top:tp,left:lt}).fadeIn();
//      $("#loginId").focus();
        document.getElementById("loginId").focus();
        refeshCodePass();//aolei add
    }

    function enableSubmitButton() {
        document.getElementById("submit").disabled = false;
        document.getElementById("submit").value = "登录";
        document.getElementById("submit").className = document.getElementById("submit").className.replace(new RegExp("(\\s|^)disable(\\s|$)"), "");
    }

    function refeshCode() {
        if(useCheckCode) {
            if(isSlideCheckCode){
                LoginSlideCheckCode.refreshSlideCheckCode();
            }
            if(isNumberCheckCode){
                document.getElementById("codeimg").src = "CaptchaImg?j="+ Math.random();
            }
        }
    }

    function revertValidate(input) {
        input.style.border = "1px solid #ccc";
    }
    //收藏
    function fnAddFavorite(sURL, sTitle) {
        sURL = encodeURI(sURL);
        try {
            window.external.addFavorite(sURL, sTitle);
        } catch (e) {
            try {
                window.sidebar.addPanel(sTitle, sURL, "");
            } catch (e) {
                alert("您的浏览器不支持自动加入收藏功能，请使用Ctrl+D进行添加，或手动在浏览器里进行设置！");
            }
        }
    }
    //设为首页
    
    function fnSetHome(obj,url) {
        if (document.all) {
            try {
                document.body.style.behavior = 'url(#default#homepage)';
                document.body.setHomePage(url);
            } catch (e) {
                if (window.netscape) {
                    try {
                        netscape.security.PrivilegeManager
                                .enablePrivilege("UniversalXPConnect");
                    } catch (e) {
                        alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
                    }
                } else {
                    alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
                }
            }
        } else {
            alert("您的浏览器不支持自动设置页面为首页功能，请您手动在浏览器里设置该页面为首页！");
        }
    }
    //浏览器视口的宽度
    function windowWidth() {
        var de = document.documentElement;
        return self.innerWidth || (de && de.clientWidth)
                || document.body.clientWidth
    }
    //关闭修改密码窗口
    function fnCloseModifyPsd() {
        mask.style.display = "none";
        psdmodify.style.display = "none";
        document.getElementById("psdmodifyform").reset();
        refeshCode();//刷新验证码
    }
    //浏览器视口的高度
    function windowHeight() {
        var de = document.documentElement;
        return self.innerHeight || (de && de.clientHeight)
                || document.body.clientHeight;
    }
    //修改密码，验证码 modify by xp
    function refeshCodePass(){
        if(useCheckCode){
            if(isSlideCheckCode){
                ModifySlideCheckCode.refreshSlideCheckCode();
            }
            if(isNumberCheckCode){
                document.getElementById("codeimgPass").src ="CaptchaImg?j=" + Math.random();
            }
        }
    }
</script>

</html>
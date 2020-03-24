<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" pageEncoding="UTF-8" %>
<%@page import="com.alibaba.fastjson.JSON" %>
<%@page import="com.alibaba.fastjson.serializer.PropertyFilter" %>
<%@page import="com.alibaba.fastjson.serializer.SerializerFeature" %>
<%@page import="com.yinhai.modules.org.api.util.IOrgConstants" %>
<%@page import="com.yinhai.modules.org.api.vo.position.PositionVo" %>
<%@page import="com.yinhai.modules.org.api.vo.user.UserVo" %>
<%@page import="com.yinhai.modules.security.api.util.SecurityUtil" %>
<%@page import="com.yinhai.modules.security.api.vo.UserAccountInfo" %>
<%@page import="org.springframework.web.servlet.support.RequestContext" %>
<%@page import="java.util.List" %>
<%
    String curPageUrl = request.getRequestURI();
    curPageUrl = curPageUrl.substring(curPageUrl.lastIndexOf("/") + 1);
%>
<%@ include file="/ta/inc-path.jsp" %>

<%
    RequestContext rc = new RequestContext(request);
%>
<script type="text/javascript">
    var baseHost = "<%=request.getHeader("Host")%>";
    var basePath = "<%=basePath%>";
    var facePath = "<%=facePath%>";
    var developMode = <%=SysConfig.getSysConfig("developMode")%>;
</script>
<%
    UserAccountInfo userAccountInfo = SecurityUtil.getUserAccountInfo(request);
    UserVo user = userAccountInfo.getUser();

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List menuList = (List) request.getAttribute("menuList");

    //创建PropertyFilter对象，并重载apply方法
    PropertyFilter propertyFilter = new PropertyFilter() {
        boolean flag = true;

        public boolean apply(Object obj, String name, Object value) {
            if (name.equalsIgnoreCase("parent") || name.equalsIgnoreCase("root")) {
                flag = false;
            } else {
                flag = true;
            }
            return flag;
        }

    };
    String menuJson = JSON.toJSONString(menuList, propertyFilter, SerializerFeature.WriteSlashAsSpecial);
    String curRoleName = userAccountInfo.getNowPosition() == null ? "无岗位" : userAccountInfo.getNowPosition().getPositionname();
    List<PositionVo> positions = SecurityUtil.getCurrentUserPositions(session);
    String perPos = IOrgConstants.POSITION_TYPE_PERSON;
    PositionVo mainPosition = userAccountInfo.getNowPosition();
    long nowPosId = mainPosition.getPositionid();
    PositionVo p;
    String signstate = (String) session.getAttribute("signstate");
%>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="X-UA-Compatible" content="IE=8; IE=9; IE=10"/>
<html>
<head>
    <title>通用综合查询统计平台</title>
    <link rel="stylesheet" href="<%=facePath%>fonts.css">
    <%@ include file="/ta/inc-skin-index.jsp" %>
    <script src="<%=facePath%>support/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=facePath%>support/i18n/i18n.js" type="text/javascript"></script>

    <!-- plugin start -->

    <%-- 【强制依赖 不可剔除】 --%>
    <%--跨域支持 无样式--%>
    <script src="<%=facePath%>support/crossDomain/crossDomain.js"></script>

    <%-- 【以下为非强制依赖，可剔除，注意相应的样式代码】 --%>
    <%--帮助--%>
    <script src="<%=facePath%>support/helpTip/helpTip.js"></script>
    <%--最上层弹出弹出框--%>
    <script src="<%=facePath%>support/indexWindow/indexWindow.js"></script>
    <%--文件预览组件--%>
    <script src="<%=facePath%>support/filepreview/filepreview.js"></script>

    <!-- plugin end -->

    <%--首页样式覆盖--%>
    <link rel="stylesheet" href="<%=basePath%>projectCover/index_cover/index_cover.css">

    <script type="text/javascript">
        window.baseGlobvar = {};
        window.baseGlobvar.contextPath = '<%=request.getContextPath()%>';
        window.baseGlobvar.basePath = '<%=basePath%>';
        window.baseGlobvar.curPageUrl = '<%=curPageUrl%>';
        //加载页面配置文件
//        Base.I18n.loadFaceConfig();
        //lins jquery 1.8- 的兼容方案
        jQuery.browser = {};
        jQuery.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
        jQuery.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
        jQuery.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
        jQuery.browser.msie11 = /rv:11.0/.test(navigator.userAgent.toLowerCase());
        jQuery.browser.msie = /msie/.test(navigator.userAgent.toLowerCase()) || jQuery.browser.msie11;
    </script>




</head>
<body>
<%--顶部部分start--%>
<div class="top-part">
    <%--logo部分start--%>
    <div class="logoImg"></div>
    <span class="logo">通用综合查询统计平台</span>

    <%--logo部分end--%>
    <%--顶部横向菜单start   --%>
    <div class="horizontal-menu-con">
        <div class="horizon-menu" id="horizon-menu">
            <div class="item-more icon-dotWithFrame" id="item-more"></div>
        </div>
    </div>
    <%--顶部横向菜单end   --%>
    <%--帮助start--%>
    <div class="helptip icon-help" onclick="toShowPageGuide()">帮助</div>
    <%--帮助end--%>
    <%--用户信息切入框 start  --%>
    <div class="user-info-control icon-person" onclick="showUserInfo()">
        <%=mainPosition.getPositionname()%>
    </div>
    <%--用户信息切入框 end  --%>
</div>
<%--顶部部分end--%>

<%--右侧用户详细信包括密码修改皮肤切换等 start--%>
<%@ include file="../../indexSRC/part/userInfo.html" %>
<%--右侧用户详细信包括密码修改皮肤切换等 end--%>


<div class="center-part">

    <div class="left-part" id="left-part">
        <%@ include file="../../indexSRC/part/leftPart.html" %>
    </div>

    <div class="drag-bar" id="drag-bar"></div>
    <div class="right-part" id="right-part">
        <div class="index-tabs">
            <%--里面装tabs标签功能的--%>
            <div class="index-tab-gzt active" id="tab_01">工作台</div>
            <ul id="index_tab_ul" class="index-tab-parent">
                <li class="index-tab-more">
                    <span class="faceIcon icon-setting"></span>
                    <div class="index-tab-more-pos display">
                        <div class="index-tab-gzt">工作台</div>
                        <ul  id="index_tab_more"></ul>
                        <div class="index-tab-more-close">关闭所有</div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="index-iframe" id="mainFrameBox">
            <%--里面装嵌入的iframe的--%>
            <%--<iframe name="tab_b_01" frameborder=no src="<%=basePath %>sysapp/portalAction.do" style="width: 100%; height: 100%" id="tab_b_01"></iframe>--%>
        </div>
    </div>
</div>
</body>


<%--<script type='text/javascript' src='<%=basePath%>dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>dwr/util.js'></script>
<script type='text/javascript' src='<%=basePath%>dwr/interface/DwrService.js'></script>--%>
<script src="<%=basePath %>loginSRC/js/passwordCheck.js"></script>
<script src="<%=basePath %>loginSRC/js/RSA.js" type="text/javascript"></script>
<script src="<%=basePath %>loginSRC/js/BigInt.js" type="text/javascript"></script>
<script src="<%=basePath %>loginSRC/js/Barrett.js" type="text/javascript"></script>

<script src="<%=basePath %>indexSRC/js/index.js"></script>
<script src="<%=basePath %>indexSRC/js/indexTab.js"></script>
<script src="<%=basePath %>indexSRC/js/menuCommon.js"></script>
<script src="<%=basePath %>indexSRC/js/menuNextSlide.js"></script>
<script src="<%=basePath %>indexSRC/js/menuLevelTwo.js"></script>
<script src="<%=basePath %>indexSRC/js/menuLevelThreeDropdown.js"></script>
<script src="<%=basePath %>indexSRC/js/menuLevelTwo.js"></script>
<script src="<%=basePath %>indexSRC/js/menuLevelOne.js"></script>
<script src="<%=basePath %>indexSRC/js/userInfo.js"></script>
<script src="<%=basePath %>indexSRC/js/notice.js"></script>

<%--首页功能js覆盖--%>
<%--<script src="<%=basePath %>projectCover/index_cover/index_cover.js"></script>--%>

<script>
    $(document).ready(function () {
        bodyRSA();
        var menuList = eval('<%=menuJson%>');
        //初始化菜单
        indexCommon.init(menuList);
        //初始化用户信息
        UserInfoInit();
    });

    function bodyRSA() {
        setMaxDigits(130);
        key = new RSAKeyPair("10001", "", "906C793510FB049452764740B21B97A51DAEA794AB6E43836269D5E6317D49226C12362BA22DAB5EC3BC79553A8A098B01F3C4D81A87B3EE5BD2F4F1431CC495EE2FE54688B212145BB32D56EEEEE1430CE26234331B291CFC53C9B84FAFFDF0B44371A032880C3D567F588D2CD5FCE28D9CDD2923CB547DAD219A6A1B8B5D3D");
    }

    function UserInfoInit(){
        var session = '<%=request.getSession(false)%>';
        var location = "<%=request.getContextPath()%>/logout";
        var loginUserName = "<%=mainPosition.getPositionname()%>";
        var passwordRSA = <%=SysConfig.getSysConfig("passwordRSA", "false")%>;
        var signstate = "<%=signstate%>";
        var orgId = "<%=userAccountInfo.getNowPosition().getOrgid()%>";
        var userId = '<%=user.getUserid()%>';
        var positions = <%=JSON.toJSONString(positions)%>;
        var nowPosId = "<%=nowPosId%>";
        var isOpenI18n = <%=SysConfig.getSysConfig("isOpenI18n")%>;
        var param = {};
        param.session = session;
        param.location = location;
        param.loginUserName = loginUserName;
        param.passwordRSA = passwordRSA;
        param.signstate = signstate;
        param.orgId = orgId;
        param.userId = userId;
        param.positions = positions;
        param.nowPosId = nowPosId;
        param.isOpenI18n = isOpenI18n;
        UserInfo.init(param);
    }

    function showUserInfo() {
        if($(".user-info-control").hasClass("active")){
            UserInfo.hideUserInfo();
        }else{
            UserInfo.showUserInfo();
        }
    }

    function getWebConfig(key) {
        return Base.globvar[key];
    }

</script>
</html>


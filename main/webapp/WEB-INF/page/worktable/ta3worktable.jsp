<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.util.*"%>
<head>
    <meta charset="UTF-8">
    <title>工作台</title>
    <%@include file="/ta/inc-path.jsp"%>
    <%@ include file="/ta/inc-skin-worktable.jsp" %>
    <link rel="stylesheet" href="<%=basePath%>indexSRC/fonts/faceIcon/faceIcon.css">
    <script src="<%=facePath%>support/jquery/jquery-1.11.0.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>indexSRC/worktable/jquery.json.js" type="text/javascript"></script>
</head>
<body>
<div id="drop_factory">
    <ul class="auto_con">
        <li class="add_new_con" id="add_con">
            <span class="faceIcon icon-selectText"></span>
        </li>
    </ul>
</div>
<!--弹出层-->
<div id="mask_con">
    <div class="mask_content">
        <div class="mask_content_head">
            添加面板
            <ul class="btn-box">
                <%--<li class="btn-min faceIcon icon-minimize"></li>--%>
                <%--<li class="btn-max faceIcon icon-maximization"></li>--%>
                <li class="btn-close faceIcon icon-close" onclick="Mask_con.closeMask()"></li>
            </ul>
        </div>
        <div class="mask_content_con">
            <ul id="panel_ul"></ul>
        </div>
        <div class="mask_content_bottom"></div>
    </div>
</div>
<script>
    $(document).ready(function() {

    });
    var pageFlag = "index";
    var portal = null;
    var basePath = "<%=basePath%>";

</script>
<script src="<%=basePath%>indexSRC/worktable/worktable.js"></script>
</body>
</html>
<%@include file="/ta/incfooterTopEvent.jsp"%>
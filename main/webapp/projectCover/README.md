# 前端样式覆盖指南

项目样式覆盖以及js功能覆盖一律写在
`ta3-peoject-ta3/src/main/webapp/projectCover/`下面.

`component_cover`文档下是框架组件css样式以及框架js功能覆盖文档.

`index_cover`文档下是index首页`index.jsp`css样式以及index首页`index.jsp` js功能覆盖文档.

`login_cover`文档下是登陆页`login.jsp`css样式以及登陆页`login.jsp` js功能覆盖文档.

### 1. 框架组件css样式以及组件js功能覆盖

在`component_cover`中的`component_cover.css`样式文档中添加修改样式.


在`component_cover`中的`component_cover.js`js文档中添加js组件方法覆盖.

然后在`appinc.jsp` 中解注释

	 <%--引入项目组件样式覆盖文件--%>
	 <link rel="stylesheet" type="text/css" href="${basePath}projectCover/component_cover/component_cover.css" />
	 <%--引入项目js功能覆盖文件--%>
	 <script src="${basePath}projectCover/component_cover/component_cover.js" type="text/javascript"></script>


### 2. index首页css样式以及首页js功能覆盖

在`index_cover`中的`index_cover.css`样式文档中添加修改样式.


在`index_cover`中的`index_cover.js`js文档中添加js组件方法覆盖.

然后在`index.jsp` 中解注释

    <%--引入项目主页覆盖文件--%>
    <link rel="stylesheet" type="text/css" href="${basePath}projectCover/index_cover/index_cover.css" />
    <%--引入项目主页js功能覆盖文件--%>
    <script src="${basePath}projectCover/index_cover/index_cover.js" type="text/javascript"></script>

### 3. login登陆页css样式覆盖

在`login_cover`中的`login_cover.css`样式文档中添加修改样式.

然后在`login.jsp` 中解注释

    <%--登陆页面样式覆盖--%>
    <link href="${pageContext.servletContext.contextPath }/projectCover/login_cover/login_cover.css" rel="stylesheet"  />







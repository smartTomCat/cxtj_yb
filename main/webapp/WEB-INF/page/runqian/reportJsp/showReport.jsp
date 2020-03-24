<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/runqianReport4.tld" prefix="report" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.runqian.report4.usermodel.Context"%>
<%@taglib prefix="ta" tagdir="/WEB-INF/tags/tatags"%>
<html>
<head>
	<%@ include file="/ta/inc.jsp"%>
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>

<table id="rpt" align="center"><tr><td>
	<table id="param_tbl" width="100%" height="100%"><tr><td>
	</td>
	<td><a href="javascript:_submit( form1 )"><img src="../images/query.jpg" border=no style="vertical-align:middle"></a></td>
	</tr>	
		<report:html name="report1" reportFileName="a.raq"
			funcBarLocation="top"
			needPageMark="yes"
			generateParamForm="no"
			appletJarName="runqian/runqianReport4Applet.jar,runqian/dmGraphApplet.jar"
			needSelectPrinter="no"
			needSaveAsExcel="yes"
			needPrint="yes"
		/>
</table>

</body>
</html>

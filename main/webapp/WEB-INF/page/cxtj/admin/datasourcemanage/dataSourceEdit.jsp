<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib prefix="ta" tagdir="/WEB-INF/tags/tatags" %>
<html xmlns="http://www.w3.org/1999/xhtml" style="height:100%">
<head>
    <title>数据源编辑</title>
    <%@ include file="/ta/inc.jsp" %>
</head>
<body class="" style="padding: 0px; margin: 0px">
<ta:pageloading/>
<ta:form id="form2" fit="true" heightDiff="60">
    <ta:text id="yzb670" key="数据源Id" display="false"/>
    <ta:fieldset id="baseInfo" cols="2" key="基本参数">
        <ta:selectInput id="yzb671" key="数据源类型" required="true" value="0" collection="YZB671"  onSelect="fnSelect" labelWidth="160" />
        <ta:text id="yzb672" key="数据源名称" placeholder="只允许输入字母、数字和下划线" labelWidth="160"
                 required="true" validType="[{type:'maxLength',param:[20],msg:'最大长度为20'}]" onChange="fnCheckName(this)"/>
        <ta:text id="yzb673" key="数据源描述"
                 required="true" validType="[{type:'maxLength',param:[50],msg:'最大长度为50'}]" labelWidth="160" />
        <ta:selectInput id="yzb674" key="数据库类型" required="true" onChange="fnDbTypeChange" labelWidth="160" collection="YZB674" />
        <ta:box id="jdbcBaseInfo" cols="2" span="2">
            <ta:selectInput id="yzb676" readOnly="true" span="2" key="数据库驱动"
                            required="true" labelWidth="160" value="0" collection="YZB676" data="[{'id':'1','name':'oracle.jdbc.OracleDriver'},{'id':'2','name':'com.mysql.jdbc.Driver'},{'id':'3','name':'org.postgresql.Driver'},{'id':'4','name':'com.gbase.jdbc.Driver'}]"/>
            <ta:text id="yzb677" key="连接URL" span="2" required="true" labelWidth="160"
                     validType="[{type:'maxLength',param:[50],msg:'最大长度为50'}]"  onChange="setUnable()"/>

            <ta:text id="yzb678" key="用户名" required="true" labelWidth="160"
                     validType="[{type:'maxLength',param:[20],msg:'最大长度为20'}]" onChange="setUnable()"/>
            <ta:text id="yzb679" type="password" required="true" key="密码" labelWidth="160"
                     validType="[{type:'maxLength',param:[20],msg:'最大长度为20'}]" onChange="setUnable()"/>
        </ta:box>

        <ta:text id="yzb675" key="JNDI数据源名称" span="2" validType="[{type:'maxLength',param:[15],msg:'最大长度为15'}]" labelWidth="160"
                 display="false"></ta:text>
    </ta:fieldset>
    <ta:fieldset id="jdbcOtherInfo" cols="2" key="其他参数">
        <ta:number id="yzb681" labelWidth="160" key="初始连接数" required="true" min="1" value="1" alignLeft="true"
                   placeholder="最小连接数"/>
        <ta:number id="yzb682" labelWidth="160" key="最大连接数" required="true" min="1" max="500" value="20" alignLeft="true"
                   placeholder="最大连接数"/>
        <ta:number id="yzb683" labelWidth="160" key="最大闲置秒数" required="true" min="1"  value="60" alignLeft="true"
                   placeholder="最大闲置时间，单位秒"/>
        <ta:number id="yzb684" labelWidth="160" key="最大获取连接秒数" required="true" min="1" value="5" alignLeft="true"
                   placeholder="最大获取连接时间,单位秒"/>
        <ta:number id="yzb685" labelWidth="160" required="true" min="1" value="60" alignLeft="true"
                   placeholder="连接失败重新获取等待时间，单位秒" key="连接失败重新获取秒数"/>
        <ta:number id="yzb686" labelWidth="160" required="true" min="60" value="600" alignLeft="true"
                   placeholder="超过连接最大存活时间且没有正在使用的连接将自动销毁，单位秒"
                   key="连接最大存活秒数"/>
        <ta:number id="yzb687" labelWidth="160" required="true" min="1" alignLeft="true"
                   value="60"
                   placeholder="连接回收执行间隔时间，单位秒"
                   key="连接回收执行间隔秒数"/>
        <ta:number id="yzb688" labelWidth="160" required="true" min="1" alignLeft="true"
                   value="2000"
                   placeholder="最大获取数据时间，单位秒"
                   key="最大获取数据秒数"/>
    </ta:fieldset>
</ta:form>

<ta:buttonGroup align="center" cssStyle="margin-top:15px">
    <ta:button id="connent" key="测试连接" isok="true"  space="true" onClick="testConnect()"/>
    <ta:button id="save" key="保存" isok="true" onClick="fnSave()" space="true" disabled="true"/>
    <ta:button id="close" key="关闭" onClick="parent.Base.closeWindow('popWin')"/>
</ta:buttonGroup>

</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
        $("body").taLayout();
    });

    function setUnable() {
        Base.setDisabled("save");
    }

    function fnSelect(key, value) {
        //jdbc
        if (key == "jdbc") {
            Base.showObj("jdbcBaseInfo,jdbcOtherInfo");
            Base.hideObj("yzb675");
            Base.setRequired("driverClassName,connectUrl,dbUsername,dbPassword," +
                "initialSize,minIdle,maxActive,maxWait,timeBetweenEvictionRunsMillis," +
                "minEvictableIdleTimeMillis,poolPreparedStatements,maxPoolPreparedStatementPerConnectionSize");
            Base.setDisRequired("jndiName");
            Base.setEnable("connent");
            //jndi
        } else {
            Base.showObj("yzb675");
            Base.hideObj("jdbcBaseInfo,jdbcOtherInfo");
            Base.setRequired("jndiName");
            Base.setDisRequired("connectUrl,dbUsername,dbPassword," +
                "initialSize,minIdle,maxActive,maxWait,timeBetweenEvictionRunsMillis," +
                "minEvictableIdleTimeMillis,poolPreparedStatements,maxPoolPreparedStatementPerConnectionSize");
//            Base.setDisabled("connent");
//            Base.alert("开发测试中...","warning");
        }
    }

    function fnDbTypeChange(value, key) {
        //设置驱动
        Base.setValue("yzb676",key);
        //设置连接示例地址
        if("1"==Base.getValue("yzb671")){
            if("1"==key){
                Base.setValue("yzb677","jdbc:oracle:thin:@127.0.0.1:1521:orcl");
            }else if("2"==key){
                Base.setValue("yzb677","jdbc:mysql://127.0.0.1:3306/database");
            }else if("3"==key){
                Base.setValue("yzb677","jdbc:postgresql://127.0.0.1:5432/dbName");
            }else{
                Base.setValue("yzb677","");
            }
        }
    }

    function testConnect() {
        Base.submit("form2", "dataSourceManageController!testConnect.do", null, null, true, function () {

        });
    }

    function fnSave() {
        var yzb670 = Base.getValue("yzb670");
        if(yzb670){
            //修改
            Base.submit("form2", "dataSourceManageController!updateDataSource.do", {"dto.yzb670":yzb670}, null, true, function () {
            });
        }else{
            //新增
            Base.submit("form2", "dataSourceManageController!saveDataSource.do", null, null, true, function () {

            });
        }
    }

    /*检查数据源名称是否存在*/
    function fnCheckName(obj) {
        /*检查系统标志格式和是否存在*/
        Base.setEnable("save");
        var name = obj.value.trim();
        if (!name || name == "") return;
        var reg = /^[A-Za-z0-9_]+$/;
        if (!reg.test(name)) {
            Base.alert("数据源名称格式不正确，只允许输入字母、数字和下划线！");
            Base.setDisabled("save");
            return;
        }
        Base.submit("", "dataSourceManageController!checkNameExist.do", {"dto['yzb672']": obj.value});
    }
</script>
<%@ include file="/ta/incfooter.jsp" %>
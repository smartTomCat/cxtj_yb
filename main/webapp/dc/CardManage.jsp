<%@ page language="java" pageEncoding="UTF-8"%>
<div class="DIV-OCX" style="DISPLAY:none">
	<OBJECT ID="SCCARDOcx" CLASSID="CLSID:D9532F10-603B-4BF7-87AE-F4130EF43553"></OBJECT>
</div>
<script type="text/javascript">

/**
 * 持卡类型
 */
var type = 3;

/**
 * 读卡业务类型
 */
var BussinessType_01 = "01";

/**
 * 密码重置业务类型
 */
var BussinessType_02 = "02";

/**
 * 写卡业务类型
 */
var BussinessType_04 = "04";

/**
 * 消费交易业务类型
 */
var BussinessType_07 = "07";

/**
 * 外部认证
 */
var serviceFlag_244 = "244";

/**
 * 安全认证
 */
var serviceFlag_245 = "245";

/**
 * 内部认证
 */
var serviceFlag_246 = "246";

/**
 * 读取卡基本信息
 */
function readCardBas() {
	var returnvar,basedate,basedates,result;	
	/* 读取基本信息：iReadCardBa */
    returnvar = SCCARDOcx.iReadCardBas(type);
    if (returnvar != 0) { // 不成功
    	/* 判断是否是需加密的错误情况 */
    	if (!isErrorJM(returnvar)) {
    		Base.alert(getErrorMsg(returnvar), "warn");
    		return result;
    	}
    	
    	/* 第一步： 获取认证数据*/
    	returnvar = SCCARDOcx.iReadCardBas_HSM_Step1(type);
    	if ("0" != returnvar) {
            Base.alert(getErrorMsg(returnvar), "warn");
            return result;
        }
    	basedate = SCCARDOcx.pOutInfo;
    	var sfbs = basedate.split("|")[2]; // 算法标识
    	/* 第二步：加密认证 */
        var param = {};
        param["dto['keyword']"] = basedate; 
        param["dto['aab301']"] = basedate.split("|")[0];
        param["dto['zdbm']"] = basedate.split("|")[8]; // 终端编码
        param["dto['BussinessType']"] = BussinessType_01; // 读卡业务类型
        param["dto['serviceFlag']"] = serviceFlag_246; // 内部认证
        var json = Base.getJson("cardManageAction!toWebService.do", param);       
        var code = json.fieldData.code;
        if ("1" != code) {
            var message = json.fieldData.message;
            Base.alert(message, "warn");
            return result;
        }
        var inst = json.fieldData.inst;
        if ("03" != sfbs) { // 国密算法
            inst = inst + "|";
        }
        /* 第三步：读卡 */
        returnvar = SCCARDOcx. iReadCardBas_HSM_Step2(inst);
        if ("0" != returnvar) {
            Base.alert(getErrorMsg(returnvar), "warn");
            return result;
        }
    }
    basedate = SCCARDOcx.pOutInfo;
    basedates = basedate.split("|");
    result = basedates;
	return result;
}

/**
 * 通用读卡
 * cardInfo: json格式
 * cardInfo.aab301; // 行政区划
 * cardInfo.aac002; // 社会保障号码
 * cardInfo.aaz500; // 卡号
 * cardInfo.aaz501; // 卡识别码
 * cardInfo.aac003  // 姓名
 * cardInfo.aaz507; // 卡复位信息
 * cardInfo.zdbm;   // 终端编码
 * fileAddr:文件地址(String)  格式："3F00EF06|08|09|4E|0A|0B|0C|0D|$"
 */
function readCard(cardInfo, fileAddr) {
	var returnvar,basedate,basedates,result;
	/* 参数校验 */
	if (fileAddr == "" || fileAddr == null || fileAddr == undefined ) {
		Base.alert("获取文件地址失败!", "warn");
		return result;
	}	
	if (cardInfo == "" || cardInfo == null || cardInfo == undefined) {
		Base.alert("获取卡基本信息失败!", "warn");
		return result;
	}
	/* 通用读卡 */
	var cardInfoBas = cardInfo.aaz501+"|"+cardInfo.aaz500+"|";
	returnvar = SCCARDOcx.iReadCard(type, 2, cardInfoBas, fileAddr);	
	if (returnvar != 0) { // 不成功
		/* 判断是否是需加密的错误情况 */
	    if (!isErrorJM(returnvar)) {
	    	Base.alert(getErrorMsg(returnvar), "warn");
	    	return result;
	    }
		/* 第一步：基于加密机的通用读卡 */
	    returnvar = SCCARDOcx.iReadCard_HSM_Step1(type, cardInfoBas, fileAddr);
		if (returnvar != 0) {	    		
	    	Base.alert(getErrorMsg(returnvar), "warn");
	        return result;           
	    }
	    basedate = SCCARDOcx.pOutInfo;
	   	basedates = basedate.split("|");	  	
	   	/* 第二步：外部认证 */
	   	var param = {};
    	param["dto['BussinessType']"] = BussinessType_01;	// 读卡业务类型
        param["dto['serviceFlag']"] = serviceFlag_244;	// 外部部认证
        param["dto['aab301']"] = cardInfo.aab301; 	// 行政区划
        param["dto['aac002']"] = cardInfo.aac002; 	// 社会保障号码
        param["dto['aaz500']"] = cardInfo.aaz500; 	// 卡号
        param["dto['aaz501']"] = cardInfo.aaz501; 	// 卡识别码
        param["dto['aac003']"] = cardInfo.aac003;  	// 姓名
        param["dto['aaz507']"] = cardInfo.aaz507; 	// 卡复位信息
        param["dto['zdbm']"] = cardInfo.zdbm; 	// 终端编码  
        param["dto['sfbs']"] = basedates[0];    // 算法标识
        param["dto['mydz']"] = basedates[1];    // 密钥地址
        param["dto['sjs1']"] = basedates[2];    // 随机数：分散因子
        param["dto['sjs2']"] = basedates[3];    // 随机数：鉴别所需原始信息
        json = Base.getJson("cardManageAction!toWebService.do",param);	
        code = json.fieldData.code;
        if ("1" != code) {
        	var message = json.fieldData.message;
        	Base.alert(message, "warn");
        	return result;
        }
        inst = json.fieldData.inst;
        /* 第三步：基于加密机的通用读卡  */
        returnvar = SCCARDOcx.iReadCard_HSM_Step2(inst);
    	if ("0" != returnvar) {
            Base.alert(getErrorMsg(returnvar), "warn");
            return result;
        }
	}
	basedate = SCCARDOcx.pOutInfo;
    result = basedate;
	return result;
}

/**
 * 通用写卡
 * cardInfo: json格式
 * cardInfo.aab301; // 行政区划
 * cardInfo.aac002; // 社会保障号码
 * cardInfo.aaz500; // 卡号
 * cardInfo.aaz501; // 卡识别码
 * cardInfo.aac003  // 姓名
 * cardInfo.aaz507; // 卡复位信息
 * cardInfo.zdbm;   // 终端编码
 * fileAddr:文件地址(String)   格式："3F00EF06|08|09|4E|0A|0B|0C|0D|$"
 * writeData: 写卡数据(String) 格式："3F00EF06|08|09|4E|0A|0B|0C|0D|$"
 */
function writeCard(cardInfo, fileAddr, writeData) {
	var returnvar,basedate,basedates;
	/* 参数校验 */
	if (fileAddr == "" || fileAddr == null || fileAddr == undefined ) {
		Base.alert("获取文件地址失败!", "warn");
		return false;
	}
	if (writeData == "" || writeData == null || writeData == undefined) {
		Base.alert("获取写卡数据失败!", "warn");
		return false;
	}
	if (cardInfo == "" || cardInfo == null || cardInfo == undefined) {
		Base.alert("获取卡基本信息失败!", "warn");
		return false;
	}	
	
	var cardInfoBas = cardInfo.aaz501+"|"+cardInfo.aaz500+"|";
    /* 通用写卡 */
    returnvar = SCCARDOcx.iWriteCard(type, cardInfoBas, fileAddr, writeData); 
    if (returnvar != 0) {
    	/* 判断是否是需加密的错误情况 */
    	if (!isErrorJM(returnvar)) {
    		Base.alert(getErrorMsg(returnvar), "warn");
    		return false;
    	}
    	/* 第一步：基于加密机的通用写卡 */
    	returnvar = SCCARDOcx.iWriteCard_HSM_Step1(type, cardInfoBas, fileAddr);
    	if ("0" != returnvar) {
            Base.alert(getErrorMsg(returnvar), "warn");
            return false;
        }
    	basedate = SCCARDOcx.pOutInfo;
    	basedates = basedate.split("|");
    	/* 第二步：外部认证 */
    	var param = {};
    	param["dto['BussinessType']"] = BussinessType_04;	// 写卡业务类型
        param["dto['serviceFlag']"] = serviceFlag_244;	// 外部部认证
        param["dto['aab301']"] = cardInfo.aab301; 	// 行政区划
        param["dto['aac002']"] = cardInfo.aac002; 	// 社会保障号码
        param["dto['aaz500']"] = cardInfo.aaz500; 	// 卡号
        param["dto['aaz501']"] = cardInfo.aaz501; 	// 卡识别码
        param["dto['aac003']"] = cardInfo.aac003;  	// 姓名
        param["dto['aaz507']"] = cardInfo.aaz507; 	// 卡复位信息
        param["dto['zdbm']"] = cardInfo.zdbm; 	// 终端编码  
        param["dto['sfbs']"] = basedates[0];    // 算法标识
        param["dto['mydz']"] = basedates[1];    // 密钥地址
        param["dto['sjs1']"] = basedates[2];    // 随机数：分散因子
        param["dto['sjs2']"] = basedates[3];    // 随机数：鉴别所需原始信息
        var json = Base.getJson("cardManageAction!toWebService.do",param);	
        var code = json.fieldData.code;
        if ("1" != code) {
        	var message = json.fieldData.message;
        	Base.alert(message, "warn");
        	return false;
        }
        inst = json.fieldData.inst; 
      	/* 第三步：基于加密机的通用写卡  */   	
       	returnvar = SCCARDOcx.iWriteCard_HSM_Step2(inst, writeData);
    	if ("0" != returnvar) {
            Base.alert(getErrorMsg(returnvar), "warn");
            return false;
        }
	}
    return true;
}

/**
 * 密码校验 
 */
function verifyPIN() {
	var returnvar = SCCARDOcx.iVerifyPIN(type);
	if (returnvar != 0) {
		 Base.alert(getErrorMsg(returnvar), "warn");
		 return false;
	 }
	 return true;
}

/**
 * 修改密码
 */
function changePIN() {
	 var returnvar = SCCARDOcx.iChangePIN(type);
	 if (returnvar != 0) {
		 Base.alert(getErrorMsg(returnvar), "warn");
		 return false;
	 }
	 return true;
}

/**
 * 密码重置
 * cardInfo:json格式
 * cardInfo.aab301; // 行政区划
 * cardInfo.aac002; // 社会保障号码
 * cardInfo.aaz500; // 卡号
 * cardInfo.aaz501; // 卡识别码
 * cardInfo.aac003  // 姓名
 * cardInfo.aaz507; // 卡复位信息
 * cardInfo.zdbm;   // 终端编码
 */
function reloadPIN(cardInfo) {
	var returnvar,basedate,basedates,json,code,inst;	
	if (cardInfo == "" || cardInfo == null || cardInfo == undefined) {
		Base.alert("请先读取卡信息", "warn");
		return false;
	}
	returnvar = SCCARDOcx.iReloadPIN(type, cardInfo.aaz501+"|"+cardInfo.aaz500+"|");
	if (returnvar != 0) { // 不成功
		/* 判断是否是需加密的错误情况 */
    	if (!isErrorJM(returnvar)) {
    		Base.alert(getErrorMsg(returnvar), "warn");
    		return false;
    	}
    	
		/* 第一步：获取外部认证数据源 */
		returnvar = SCCARDOcx.iReloadPIN_HSM_Step1(type, cardInfo.aaz501+"|"+cardInfo.aaz500+"|");
    	if (returnvar != 0) {
            Base.alert(getErrorMsg(returnvar), "warn");
            return false;           
        }
    	basedate = SCCARDOcx.pOutInfo;
    	basedates = basedate.split("|");
    	
    	/* 第二步：外部认证 */
    	var param = {};
    	param["dto['BussinessType']"] = BussinessType_02;     // 密码重置业务类型
        param["dto['serviceFlag']"] = serviceFlag_244;      // 外部部认证
        param["dto['aab301']"] = cardInfo.aab301; // 行政区划
        param["dto['aac002']"] = cardInfo.aac002; // 社会保障号码
        param["dto['aaz500']"] = cardInfo.aaz500; // 卡号
        param["dto['aaz501']"] = cardInfo.aaz501; // 卡识别码
        param["dto['aac003']"] = cardInfo.aac003  // 姓名
        param["dto['aaz507']"] = cardInfo.aaz507; // 卡复位信息  
        param["dto['zdbm']"] = cardInfo.zdbm; 	  // 终端编码 
        param["dto['sfbs']"] = basedates[0];      // 算法标识
        param["dto['mydz']"] = basedates[1];      // 密钥地址
        param["dto['sjs1']"] = basedates[2];      // 随机数：分散因子
        param["dto['sjs2']"] = basedates[3];      // 随机数：鉴别所需原始信息
       	json = Base.getJson("cardManageAction!toWebService.do",param);	
        code = json.fieldData.code;
        if ("1" != code) {
        	var message = json.fieldData.message;
        	Base.alert(message, "warn");
        	return false;
        }
        inst = json.fieldData.inst;
        /* 第三步：获取安全认证数据源 */
        returnvar = SCCARDOcx.iReloadPIN_HSM_Step2(inst);
    	if (returnvar != 0) {
	        var message= getErrorMsg(returnvar);
	        Base.alert(message, "warn");
	        return false;
        }
        basedate = SCCARDOcx.pOutInfo;
        basedates = basedate.split("|");
        
        /* 第四步：安全认证 */
        param["dto['sfbs']"] = basedates[0]; // 算法标识
        param["dto['mydz']"] = basedates[1]; // 密钥地址
        param["dto['sjs1']"] = basedates[2]; // 随机数：过程因子
        param["dto['sjs2']"] = basedates[3]+"|"+basedates[4]; // 随机数：APDU命令明文数据
        param["dto['serviceFlag']"] = serviceFlag_245;    // 安全认证
        if (basedates[4].length > 8) {
        	Base.alert("密码位数暂时不支持超过8位!", "warn");
        	return false;
        }
        if ("03" == basedates[0]) { // 国密算法
        	 param["dto['sjs2']"] = basedates[4]; // 只传密码
        }
        json = Base.getJson("cardManageAction!toWebService.do",param);	
        code = json.fieldData.code;
        if("1" != code){
        	var message = json.fieldData.message;
        	Base.alert(message, "warn");
        	return false;
        }
        inst = json.fieldData.inst;     
        if (basedates[0] == "03") { // 国密算法
        	inst = basedates[3]+json.fieldData.inst;
        }
        
        /* 第五步：密码重置 */
        returnvar = SCCARDOcx.iReloadPIN_HSM_Step3(inst);
    	if (returnvar != 0) {
	        var message = getErrorMsg(returnvar);
	        Base.alert(message, "warn");
	        return false;
        }   	
	}
	return true;
}

/**
 * 消费交易
 * cardInfo:json格式
 * cardInfo.aab301; // 行政区划
 * cardInfo.aac002; // 社会保障号码
 * cardInfo.aaz500; // 卡号
 * cardInfo.aaz501; // 卡识别码
 * cardInfo.aac003  // 姓名
 * cardInfo.aaz507; // 卡复位信息
 * cardInfo.zdbm;   // 终端编码
 * payInfo: 消费信息     //"0.1|0.2|20151118185001|"
 */
function doDebit(cardInfo, payInfo) {
	var returnvar,basedate,basedates,result;	
	if (cardInfo == "" || cardInfo == null || cardInfo == undefined) {
		Base.alert("获取卡基本信息失败", "warn");
		return result;
	}
	if (payInfo == "" || payInfo == null || payInfo == undefined) {
		Base.alert("获取消费信息失败", "warn");
		return result;
	}
	returnvar = SCCARDOcx.iDoDebit(type, cardInfo.aaz501+"|"+cardInfo.aaz500+"|", payInfo);
	if (returnvar != 0) { // 不成功
		/* 判断是否是需加密的错误情况 */
    	if (!isErrorJM(returnvar)) {
    		Base.alert(getErrorMsg(returnvar), "warn");
    		return result;
    	}
		/*第一步：获取安全认证数据源*/
		returnvar = SCCARDOcx.iDoDebit_HSM_Step1(type, cardInfo.aaz501+"|"+cardInfo.aaz500+"|", payInfo);
    	if (returnvar != 0) {   		
            Base.alert(getErrorMsg(returnvar), "warn");
            return result;           
        }
    	basedate = SCCARDOcx.pOutInfo;
    	basedates = basedate.split("|");
    	/* 第二步：安全报文认证 */
    	var param = {};
    	param["dto['BussinessType']"] = BussinessType_07;     // 医疗消费业务类型
        param["dto['serviceFlag']"] = serviceFlag_245;      // 安全报文认证
        param["dto['aab301']"] = cardInfo.aab301; // 行政区划
        param["dto['aac002']"] = cardInfo.aac002; // 社会保障号码
        param["dto['aaz500']"] = cardInfo.aaz500; // 卡号
        param["dto['aaz501']"] = cardInfo.aaz501; // 卡识别码
        param["dto['aac003']"] = cardInfo.aac003  // 姓名
        param["dto['aaz507']"] = cardInfo.aaz507; // 卡复位信息
        param["dto['zdbm']"] = cardInfo.zdbm; 	// 终端编码  
        param["dto['sfbs']"] = basedates[0];      // 算法标识
        param["dto['mydz']"] = basedates[1];      // 密钥地址
        param["dto['sjs1']"] = basedates[2]+basedates[3]+basedates[3];      // 随机数：分散因子
        param["dto['sjs2']"] = "00000000"+basedates[4]+basedates[5]+basedates[6]+basedates[7];      // 随机数：鉴别所需原始信息
       	json = Base.getJson("cardManageAction!toWebService.do",param);	
        code = json.fieldData.code;
        if ("1" != code) {
        	var message = json.fieldData.message;
        	Base.alert(message, "warn");
        	return result;
        }
        inst = json.fieldData.inst;
        inst = "0000"+basedates[3]+"|"+basedates[7]+"|"+inst+"|";
        /* 第三步：基于加密机的消费交易*/
        returnvar = SCCARDOcx.iDoDebit_HSM_Step2(inst);
        if (returnvar != 0) {
            Base.alert(getErrorMsg(returnvar), "warn");
            return result;           
        }
	}
	basedate = SCCARDOcx.pOutInfo;
	basedates = basedate.split("|");
	result = basedates;
	return result;
}

/**
 * 读取消费交易
 */
function readDebitRecord() {
	var returnvar,basedate,basedates,result;
	returnvar = SCCARDOcx.iReadDebitRecord(type);
	if (returnvar != 0) {
        Base.alert(getErrorMsg(returnvar), "warn");
        return result;           
    }
	basedate = SCCARDOcx.pOutInfo;
	basedates = basedate.split("|"); 
	result = basedates;
	return result;
}

/**
 * 获取密码随机数
 */
function getSJ(pass) {
	var sj = "";  // 国密随机数： 密码位数+密码
	var mmLength = pass.length; // 密码长度
	if (mmLength%2 > 0) {
	 	sj = "0" + (parseInt(mmLength/2)+1) + pass + "F";
	} else {
		sj = "0" + parseInt(mmLength/2) + pass;
	}
	var sjLength = sj.length; // 随机数长度
	if (sjLength < 16) {
		if ((16 - sjLength) == 2) {
			sj = sj + "80";
		} else {
			var bw = ""; // 补位数 
    		for (var i = 0; i < (14 - sjLength); i++) {
    			bw = bw + "0";
			}
    		sj = sj + "80" + bw;
		}       		
	}
	return sj;
}

/**
 * 是否是需加密的错误
 * flag:错误代码
 */
function isErrorJM(flag){
	if (flag == "" || flag == null || flag == undefined) {
		return false;
	}
	if (flag == "-2201" || flag == "无PSAM卡") {
		return true;
	}
	if (flag == "-2202" || flag == "算法不支持") {
		return true;
	}
	if (flag == "-2203" || flag == "无RKSSSE密钥") {
		return true;
	}
	if (flag == "-27272" || flag == "未找到密钥") {
		return true;
	}
	if (flag == "-24" || flag == "密钥级别不够") {
		return true;
	}
	return false;
}

/**
 * 根据错误代码获取错误描述
 * flag:错误代码
 */
function getErrorMsg(flag){
    var errmsg="";
   	switch(flag){
    	case -1:
    		errmsg = flag+":卡类型不正确";
       		break;
        case -2:
        	errmsg = flag+":无卡";
        	break;
        case -3:
        	errmsg = flag+":有卡未上电";
        	break;
        case -4:
        	errmsg = flag+":卡无应答";
        	break;
        case -5:
        	errmsg = flag+":加载动态库错误";
        	break;
        case -11:
        	errmsg = flag+":读卡器连接错误";
        	break;
        case -12:
        	errmsg = flag+":未建立连接";
        	break;
        case -13:
        	errmsg = flag+":(动态库)不支持该命令";
        	break;
        case -14:
        	errmsg = flag+":(发给动态库的)命令参数错误";
        	break;
        case -15:
        	errmsg = flag+":信息校验出错";
        	break;
        case -20:
        	errmsg = flag+":卡识别码格式错误";
        	break;
        case -21:
        	errmsg = flag+":内部认证失败（用户卡不合法）";
        	break;
        case -22:
        	errmsg = flag+":传入数据与卡内不符";
        	break;
        case -23:
        	errmsg = flag+":传入数据不合法";
        	break;
        case -24:
        	errmsg = flag+":PSAM卡密钥级别不够";
        	break;
        case -31:
        	errmsg = flag+":用户取消密码输入";
        	break;
        case -32:
        	errmsg = flag+":密码输入操作超时";
        	break;
        case -33:
        	errmsg = flag+":输入密码长度错误";
        	break;
        case -34:
        	errmsg = flag+":两次输入密码不一致";
        	break;
        case -35:
        	errmsg = flag+":初始密码不能交易";
        	break;
        case -36:
        	errmsg = flag+":不能改为初始密码";
        	break;
        case -41:
        	errmsg = flag+":运算数据含非法字符";
        	break;
        case -42:
        	errmsg = flag+":运算数据长度错误";
        	break;
        case -51:
        	errmsg = flag+":PIN校验失败";
        	break;
        case -52:
        	errmsg = flag+":PIN锁定";
        	break;
        case -2201:
        	errmsg = flag+":无PSAM卡";
        	break;
        case -2202:
        	errmsg = flag+":PSAM卡算法不支持（即PSAM卡内没有SSF33算法或SM4算法）";
        	break;
        case -2203:
        	errmsg = flag+":PSAM卡内没有RKssse密钥（3.0卡读个人基本信息需要RKssse密钥外部认证）";
        	break;
        case -2204:
        	errmsg = flag+":不需要加密机认证";
        	break;
        case -25536:
        	errmsg = flag+":外部认证失败";
        	break;
        case -25537:
        	errmsg = flag+":外部认证失败";
        	break;
        case -25538:
        	errmsg = flag+":外部认证失败";
        	break;
        case -26368:
        	errmsg = flag+":Lc/Le不正确";
        	break;
        case -26881:
        	errmsg = flag+":命令不接受（无效状态）";
        	break;
        case -27009:
        	errmsg = flag+":命令与文件结构不相符，当前文件非所需文件";
        	break;
        case -27010:
        	errmsg = flag+":不满足安全条件";
        	break;
        case -27011:
        	errmsg = flag+":密钥锁定（算法锁定）鉴别方法锁定";
        	break;
        case -27012:
        	errmsg = flag+"：引用数据无效、随机数无效";
        	break;
        case -27013:
        	errmsg = flag+":不满足使用条件、应用被锁定、应用未选择、余额上溢";
        	break;
        case -27016:
        	errmsg = flag+":安全报文数据项不正确、MAC不正确";
        	break;
        case -27264:
        	errmsg = flag+":数据域参数不正确";
        	break;
        case -27265:
        	errmsg = flag+":不支持该功能、卡中无MF、卡被锁定、应用锁定";
        	break;
        case -27266:
        	errmsg = flag+"：未找到文件、文件标识相重、SFI不正确";
        	break;
        case -27267:
        	errmsg = flag+":未找到记录";
        	break;
        case -27272:
        	errmsg = flag+":未找到引用数据、未找到密钥";
        	break;
        case -37634:
        	errmsg = flag+":MAC无效";
        	break;
        case -37635:
        	errmsg = flag+"：应用已被永久锁定、卡片锁定";
        	break;
        case -37891:
        	errmsg = flag+":PSAM卡不支持消费交易";
        	break;
        case -37894:
        	errmsg = flag+":所需MAC(或/和TAC)不可用";
        	break;
        default:
        	errmsg = flag+":未知错误";
        	break;
        }
    return errmsg;
}
</script>
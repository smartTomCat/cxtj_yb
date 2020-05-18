/**
 * 读卡器公用js
 */

var url = "ws://127.0.0.1:12342";
var websocket;
var connected = false;

/**
 * 初始化webSocket连接
 * @param callback
 * @param value
 * @constructor
 */
function ConnectServer(callback, value) {

    if ('WebSocket' in window) {
        websocket = new WebSocket(url);
    } else if (window.WebSocket) {
        websocket = new WebSocket(url);
    } else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket(url);
    } else {
        alert("浏览器版本太低！请使用Chrome、Firefox、IE10+浏览器！");
    }

    websocket.onopen = function () {
        connected = true;
        callback(value);
    }
    websocket.onclose = function (e) {
        connected = false;
    }
    websocket.onmessage = function (e) {
        onMessage(e);
    }
    websocket.onerror = function (e) {
        alert("未连接websocket服务器，请确保已运行服务端！!!!")
    };

}

/**
 * 接收服务器消息
 * @param e
 */
function onMessage(e) {
    var jsonObj = eval('(' + e.data + ')');
    //通用回调处理函数 jsonObj 遵循文档返回数据格式要求
    cardProduceMessage(jsonObj);
}

/**
 * 向服务器发送信息的共享方法
 * @param jsonStr
 */
function sendMessage(jsonStr) {
    connected ? websocket.send(jsonStr) : alert("未连接websocket服务器，请确保已运行服务端！")
}


/******************* 以下为读卡器函数 *************************/

/**
 * 读基本信息
 * @param iType 卡类型
 * 1-接触式操作卡；2-非接触式操作卡；3-自动寻卡，接触式操作卡优先；4-自动寻卡，非接触式操作卡优先。
 * @constructor
 */
function iReadCardBas(iType) {
    var data = JSON.stringify({'function': 'iReadCardBas', 'iType': iType});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的读基本信息（步骤一）
 * @param iType 卡类型
 * 1-接触式操作卡；2-非接触式操作卡；3-自动寻卡，接触式操作卡优先；4-自动寻卡，非接触式操作卡优先。
 * @constructor
 */
function iReadCardBas_HSM_Step1(iType) {
    var data = JSON.stringify({'function': 'iReadCardBas_HSM_Step1', 'iType': iType});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的读基本信息（步骤二）
 * @param pKey 加密机返回的结果数据
 * @constructor
 */
function ReadCard_HSM_Step2(pKey) {
    var data = JSON.stringify({'function': 'ReadCard_HSM_Step2', 'pKey': pKey});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 通用读卡
 * @param iType 卡类型：
 * @param iAuthType 认证方式：
 * @param pCardInfo 卡基本信息：
 * @param pFileAddr 文件名及数据项：
 * @constructor
 */
function iReadCard(iType, iAuthType, pCardInfo, pFileAddr) {
    var data = JSON.stringify({'function': 'iReadCard', 'iType': iType, 'iAuthType': iAuthType, 'pCardInfo': pCardInfo, 'pFileAddr': pFileAddr});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的通用读卡（步骤一）
 * @param iType 卡类型：
 * @param pCardInfo 卡基本信息：
 * @param pFileAddr 文件名及数据项：
 * @constructor
 */
function iReadCard_HSM_Step1(iType, pCardInfo, pFileAddr) {
    var data = JSON.stringify({'function': 'iReadCard', 'iType': iType,  'pCardInfo': pCardInfo, 'pFileAddr': pFileAddr});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}


/**
 * 基于加密机的通用读卡（步骤二）
 * @param pKey 加密机返回的结果数据
 * @constructor
 */
function ReadCard_HSM_Step2(pKey) {
    var data = JSON.stringify({'function': 'ReadCard_HSM_Step2', 'pKey': pKey});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * PIN校验
 * @param iType 卡类型
 * @constructor
 */
function iVerifyPIN(iType) {
    var data = JSON.stringify({'function': 'iVerifyPIN', 'iType': iType});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * PIN重置
 * @param iType 卡类型
 * @param pCardInfo 卡基本信息
 * @constructor
 */
function iReloadPIN(iType, pCardInfo) {
    var data = JSON.stringify({'function': 'iReloadPIN', 'iType': iType, 'pCardInfo': pCardInfo});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的PIN重置（步骤一）
 * @param iType 卡类型：
 * @param pCardInfo 卡基本信息：
 * @constructor
 */
function iReloadPIN_HSM_Step1(iType, pCardInfo) {
    var data = JSON.stringify({'function': 'iReloadPIN_HSM_Step1', 'iType': iType,  'pCardInfo': pCardInfo});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的PIN重置（步骤二）
 * @param pKey 加密机返回的结果数据
 * @constructor
 */
function iReloadPIN_HSM_Step2(pKey) {
    var data = JSON.stringify({'function': 'iReloadPIN_HSM_Step2', 'pKey': pKey});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的PIN重置（步骤三）
 * @param pKey 安全报文数据
 * @constructor
 */
function iReloadPIN_HSM_Step3(pKey) {
    var data = JSON.stringify({'function': 'iReloadPIN_HSM_Step3', 'pKey': pKey});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * PIN解锁
 * @param iType 卡类型
 * @param pCardInfo 卡基本信息
 * @constructor
 */
function iUnblockPIN(iType, pCardInfo) {
    var data = JSON.stringify({'function': 'iUnblockPIN', 'iType': iType, 'pCardInfo': pCardInfo});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的PIN解锁（步骤一）
 * @param iType 卡类型
 * @param pCardInfo 卡基本信息
 * @constructor
 */
function iUnblockPIN_HSM_Step1(iType, pCardInfo) {
    var data = JSON.stringify({'function': 'iUnblockPIN_HSM_Step1', 'iType': iType, 'pCardInfo': pCardInfo});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的PIN解锁（步骤二）
 * @param pkey 加密机返回的结果数据
 * @constructor
 */
function iUnblockPIN_HSM_Step2(pkey) {
    var data = JSON.stringify({'function': 'iUnblockPIN_HSM_Step2', 'pkey': pkey});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 基于加密机的PIN解锁（步骤三）
 * @param pkey 安全报文数据
 * @constructor
 */
function iUnblockPIN_HSM_Step3(pkey) {
    var data = JSON.stringify({'function': 'iUnblockPIN_HSM_Step3', 'pkey': pkey});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 读取银行卡号
 * @constructor
 */
function iReadICCardNum() {
    var data = JSON.stringify({'function': 'iReadICCardNum'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 读取身份证号
 * @constructor
 */
function iReadIdentityCard() {
    var data = JSON.stringify({'function': 'iReadIdentityCard'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * PIN 修改
 * @param iType 卡类型
 * @constructor
 */
function iChangePIN(iType) {
    var data = JSON.stringify({'function': 'iChangePIN', 'iType': iType});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}




/**
 * 读基本信息 消息处理封装版
 * 将此函数放在 produceCardMessage 中
 * @param jsonobj  服务器返回的消息
 * @param callback 拿到卡基本信息后要做的处理函数
 */
function readCardBasProMsg(jsonobj,callback) {
    //通用读卡成功
    var b1 = jsonObj.functionName == "iReadCardBas" && jsonObj.success == "1";
    if (b1) {
        //做业务操作  jsonObj.pOutInfo为卡基本信息
        callback(jsonObj.pOutInfo);
        return;
    }

    var b2 = jsonObj.functionName == "iReadCardBas" && jsonObj.success != "1"
    //不满足 加密机制通道读取的前提条件 直接返回错误信息
    if (b2 && !isErrorJM(jsonObj.success)) {
        Base.alert(getErrorMsg(jsonObj.success), "warn");
        return;
    }

    //满足加密机制通道读取的前提条件
    if(b2 && isErrorJM(jsonObj.success)) {
        //加密机制读取基本信息 step1 向ws服务器发送指令
        iReadCardBas_HSM_Step1(3);
        return;
    }

    //step1 信息处理
    if ("iReadCardBas_HSM_Step1" == jsonObj.functionName) {
        if (jsonObj.success != "1") {
            //失败 弹出错误信息 不再继续发指令
            Base.alert(getErrorMsg(jsonObj.success), "warn");
            return;
        }

        //step1 成功返回
        var basedate = jsonObj.pOutInfo;
        var sfbs = basedate.split("|")[2]; // 算法标识
        var param = {};
        param["dto['keyword']"] = basedate;
        param["dto['aab301']"] = basedate.split("|")[0];
        param["dto['zdbm']"] = basedate.split("|")[8]; // 终端编码
        param["dto['BussinessType']"] = BussinessType_01; // 读卡业务类型
        param["dto['serviceFlag']"] = serviceFlag_246; // 内部认证
        //调用持卡库的卡内部认证服务接口
        var json = Base.getJson("cardManageAction!toWebService.do", param);
        var code = json.fieldData.code;
        if ("1" != code) {
            var message = json.fieldData.message;
            Base.alert(message, "warn");
            return;
        }
        var inst = json.fieldData.inst;
        if ("03" != sfbs) { // 国密算法
            inst = inst + "|";
        }
        //加密机制读取基本信息 step2 发送指令
        iReadCardBas_HSM_Step2(inst);
        return;
    }
    //step2 信息处理
    if ("iReadCardBas_HSM_Step2" == jsonObj.functionName) {
        if (jsonObj.success != "1") {
            //失败 弹出错误信息 不再继续发指令
            Base.alert(getErrorMsg(jsonObj.success), "warn");
            return;
        }
        //成功返回 做业务操作  jsonObj.pOutInfo为卡基本信息
        callback(jsonObj.pOutInfo);
    }
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

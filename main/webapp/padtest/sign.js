/**
 * 手写签名板公用js
 */

var url = "ws://127.0.0.1:12343";
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
    singProduceMessage(jsonObj);
}

/**
 * 向服务器发送信息的共享方法
 * @param jsonStr
 */
function sendMessage(jsonStr) {
    connected ? websocket.send(jsonStr) : alert("未连接websocket服务器，请确保已运行服务端！")
}


/******************* 以下为签名平板函数 *************************/

/**
 * 启动签字设备
 * @constructor
 */
function ZCStartDeviceAB() {
    var data = "{'function': 'ZCStartDeviceAB'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 退出签名模式
 * @constructor
 */
function ZCEndSignAB() {
    var data = "{'function':'ZCEndSignAB'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 初始化设备并进入签名模式
 * @param x 在签字板中的x
 * @param y 在签字板中的y
 * @param width 签字窗体宽度
 * @param height 签字窗体高度
 * @constructor
 */
function ZCBeginSignAB(x,y,width,height) {
    var data = JSON.stringify({'function':'ZCBeginSignAB','x':x,'y':y,'width':width,'height':height});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 停止设备
 * @constructor
 */
function ZCStopDeviceAB() {
    var data = "{'function':'ZCStopDeviceAB'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 将当前笔迹保存到Base64串中,并自动裁剪掉白边
 * @constructor
 */
function ZCOutputImageBase64AB() {
    var data = "{'function':'ZCOutputImageBase64AB'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 签名版屏幕网页推送
 * @param url 推送地址
 * @constructor
 */
function ZCShowHtmlAB(url) {
    var data = JSON.stringify({'function':'ZCShowHtmlAB','url': url});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 获取签名版序列号
 * @constructor
 */
function ZCGetSerialNumAB() {
    var data = "{'function':'ZCGetSerialNumAB'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 设置笔模式
 * @param mode 笔模式
 * @constructor
 */
function ZCSetModeAB(mode) {
    var data = JSON.stringify({'function':'ZCSetModeAB','mode': mode});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

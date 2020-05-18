/**
 * 双目摄像头公用js
 */

var url = "ws://127.0.0.1:12345";
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
    eyescameraProduceMessage(jsonObj);
}

/**
 * 向服务器发送信息的共享方法
 * @param jsonStr
 */
function sendMessage(jsonStr) {
    connected ? websocket.send(jsonStr) : alert("未连接websocket服务器，请确保已运行服务端！")
}


/******************* 以下为双目摄像头函数 *************************/

/**
 * 打开双目人脸摄像头
 * @constructor
 */
function OpenDualFaceDevice() {
    var data = JSON.stringify({'function': 'OpenDualFaceDevice'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 关闭双目人脸摄像头
 * @constructor
 */
function CloseDualFaceDevice() {
    var data = JSON.stringify({'function': 'CloseDualFaceDevice'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 获取活体检测结果
 * @constructor
 */
function GetFaceLivingBodyStatus() {
    var data = JSON.stringify({'function': 'GetFaceLivingBodyStatus'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 获取检测到的人脸图片
 * @constructor
 */
function GetFaceImageFromCam() {
    var data = JSON.stringify({'function': 'GetFaceImageFromCam'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}



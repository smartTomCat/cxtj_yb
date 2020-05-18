/**
 * 扫描仪公用js
 */

var url = "ws://127.0.0.1:12344";
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
    scanProduceMessage(jsonObj);
}

/**
 * 向服务器发送信息的共享方法
 * @param jsonStr
 */
function sendMessage(jsonStr) {
    connected ? websocket.send(jsonStr) : alert("未连接websocket服务器，请确保已运行服务端！")
}


/******************* 以下为扫描仪函数 *************************/

/**
 * 打开文档扫描仪
 * @constructor
 */
function iHsOpen() {
    var data = "{'function': 'iHsOpen'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 设置扫描参数
 * @param parID 参数ID
 * @param value 参数ID对应的值
     1 SCAN_MODE   0:单面扫描、1:双面扫描
     2 IMAGE_TYPE    0:黑白 1：灰度 2：彩色
     3 IMAGE_FORMAT  图像格式 0:BMP 1:JPEG 2:TIFF
     4 RESOLUTION   0:100 1:150 2:200 3:240 4:3005:400 6:600
     5 DESKEW   1:自动裁剪纠偏 0:不裁剪
     6 BARCODE    0:无 1:条码 2:QR码 3:PDF417码
     7 IMAG_EFILEPATH 设置图像文件的保存路
     8 NAME_PREFIX 文件名前缀，不设置时默认为空
     9 AUTO_GO_BLANK 1：自动去空白页 0：不自动去空白页
     10 BLANK_RANGE空白也内容上线，百分比，内容为0到100的整数，超出按照边界值算
     11 AUTO_ROTATE识别文字方向自动旋转图片  0：不自动旋转  1：自动旋转
 * @constructor
 */
function iHsSetParameter(ParID,Value) {
    var data = JSON.stringify({'function': 'iHsSetParameter', 'ParID': ParID, 'Value':Value});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 打开扫描仪设置面板
 * @constructor
 */
function iHsSettingsPanel() {
    var data = JSON.stringify({'function': 'iHsSettingsPanel'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 启动扫描仪(单张返回)
 * @constructor
 */
function iHsStartScan() {
    var data = "{'function':'iHsStartScan'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 启动扫描仪(批量返回)
 * @constructor
 */
function iHsStartTempScan() {
    var data = "{'function':'iHsStartTempScan'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 获取扫描仪状态
 * @constructor
 */
function iHsGetStatus() {
    var data = "{'function':'iHsGetStatus'}";
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 条码识别
 * @param barcodeType 条码类型:1:一维条码 2:QR码 3:PDF417码
 * @param path 待识别图片路径
 * @constructor
 */
function iHsReadBarcode(barcodeType, path) {
    var data = JSON.stringify({'function': 'iHsReadBarcode', 'barcodeType': barcodeType, 'path': path});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 合成PDF文件
 * @param path 需要转换的图片路径
 * @param PDFFile 合成后的PDF文件所在位置
 * @constructor
 */
function iHsMakePDF(path, PDFFile) {
    var data = JSON.stringify({'function': 'iHsMakePDF', 'path': path, 'PDFFile': PDFFile});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

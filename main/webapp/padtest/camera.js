/**
 * 高拍仪公用js
 */

var url = "ws://127.0.0.1:12341";
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
    var jsonObj = JSON.parse(e.data.replace(/[\r\n]/g,""));
    //通用回调处理函数 jsonObj 遵循文档返回数据格式要求
    CameraProduceMessage(jsonObj);
}

/**
 * 向服务器发送信息的共享方法
 * @param jsonStr
 */
function sendMessage(jsonStr) {
    connected ? websocket.send(jsonStr) : alert("未连接websocket服务器，请确保已运行服务端！")
}


/******************* 以下为高拍仪函数 *************************/

/**
 * 初始化
 * @constructor
 */
function iCamInit() {
    var data = JSON.stringify({'function': 'iCamInit'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 反初始化
 * @constructor
 */
function iCamDeinit() {
    var data = JSON.stringify({'function':'iCamDeinit'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 打开摄像头
 * @param iCamNo 摄像头编号：
 1:主摄像头，拍摄桌面资料的摄像头
 2:人像摄像头，一般意义上的副摄像头，可以拍摄参保对象
 3:VR摄像头，可以进行人脸识别对比的摄像头
 9:非设备提供的摄像头，用户电脑自带的摄像头，不是由当前外接设备提供的摄像头
 * @constructor
 */
function iCamOpen(iCamNo) {
    var data = JSON.stringify({'function': 'iCamOpen', 'iCamNo': iCamNo});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 获取摄像头返回
 * @constructor
 */
function iCamGetCameraImage() {
    var data = JSON.stringify({'function':'iCamGetCameraImage'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 关闭摄像头
 * @constructor
 */
function iCamClose() {
    window.clearInterval(timOut);
    var data = JSON.stringify({'function':'iCamClose'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 获取摄像头分辨率
 * @constructor
 */
function iCamGetResolutionList() {
    var data = JSON.stringify({'function':'iCamGetResolutionList'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 设置摄像头分辨率
 * @param resolutionNo 分辨率在降序列表中的序号
 * @constructor
 */
function iCamSetResolution(resolutionNo) {
    var data = JSON.stringify({'function': 'iCamSetResolution', 'resolutionNo': resolutionNo});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 获取当前打开摄像头使用的分辨率
 * @param resolutionNo 分辨率在降序列表中的序号
 * @constructor
 */
function iCamGetCurrentResolution(resolutionNo) {
    var data = JSON.stringify({'function': 'iCamGetCurrentResolution'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 设置色彩模式
 * @param colorCode 颜色编号  1:黑白 2:彩色 3:灰度
 * @constructor
 */
function iCamSetColor(colorCode) {
    var data = JSON.stringify({'function': 'iCamSetColor', 'colorCode': colorCode});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 设置剪裁模式
 * @param cutType 剪裁模式
 0:不裁切，拍摄视频区所有内容
 1:自动裁切，自动根据内容进行裁切并纠偏
 2:手动裁切，根据手动选定的区域进行拍摄
 * @constructor
 */
function iCamSetCutType(cutType) {
    var data = JSON.stringify({'function': 'iCamSetCutType', 'cutType': cutType});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}


/**
 * 拍摄成base64字符串
 * @param imgFormat 图片格式
 * @constructor
 */
function iCamPhotoBase64(imgFormat) {
    var data = JSON.stringify({'function': 'iCamPhotoBase64', 'imgFormat': imgFormat});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}


/**
 * 将文件转成base64字符串
 * @param filePath 文件路径
 * @constructor
 */
function iCamFileToBase64(filePath) {
    var data = JSON.stringify({'function': 'iCamFileToBase64', 'filePath': filePath});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}


/**
 * 拍摄图片到指定路径
 * @param imgPath 图片全路径，包含后缀名
 * @constructor
 */
function iCamPhoto(imgPath) {
    var data = JSON.stringify({'function': 'iCamPhoto', 'imgPath': imgPath});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 开始定时抓拍
 * @param time 间隔时间，单位毫秒
 * @param path 抓拍图像存放文件夹地址
 * @constructor
 */
function iCamStartTimerCap(time, path) {
    var data = JSON.stringify({'function': 'iCamStartTimerCap', 'time': time, 'path': path});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 结束定时抓拍
 * @constructor
 */
function iCamStopTimerCap() {
    var data = JSON.stringify({'function': 'iCamStopTimerCap'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 开始自动抓拍
 * @param path 抓拍图像存放文件夹地址
 * @constructor
 */
function iCamStartAutoCap(path) {
    var data = JSON.stringify({'function': 'iCamStartAutoCap', 'path': path});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 关闭自动抓拍
 * @constructor
 */
function iCamStopAutoCap() {
    var data = JSON.stringify({'function': 'iCamStopAutoCap'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 向左旋转
 * 把视频区域图片向左旋转90°
 * @constructor
 */
function iCamSetRotateLeft() {
    var data = JSON.stringify({'function': 'iCamSetRotateLeft'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 向右旋转
 * 把视频区域图片向右旋转90°
 * @constructor
 */
function iCamSetRotateRight() {
    var data = JSON.stringify({'function': 'iCamSetRotateRight'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 放大
 * 视频区域图像放大
 * @constructor
 */
function iCamZoomIn() {
    var data = JSON.stringify({'function': 'iCamZoomIn'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 缩小
 * 视频区域图像缩小
 * @constructor
 */
function iCamZoomOut() {
    var data = JSON.stringify({'function': 'iCamZoomOut'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 识别条码
 * @param imgPath 图片全路径，包含后缀名
 * @constructor
 */
function iCamReadBarCode(imgPath) {
    var data = JSON.stringify({'function': 'iCamReadBarCode', 'imgPath': imgPath});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 识别二维码
 * @param imgPath 图片全路径，包含后缀名
 * @constructor
 */
function iCamReadQRCode(imgPath) {
    var data = JSON.stringify({'function': 'iCamReadQRCode', 'imgPath': imgPath});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 图片设置
 * 高拍仪控件弹出窗口进行其他图片拍摄参数设置，用户可以选择设置只在当前页面生效还是永久生效
 * @constructor
 */
function iCamImageSet() {
    var data = JSON.stringify({'function': 'iCamImageSet'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 开始视频拍摄 （视频输出到地址指定路径）
 * @param vidoPath 视频地址，到文件夹
 * @param vidoName 视频名称，不包含后缀名
 * @param vidoFormat 视频格式 如果不要求视频格式，可以传递空字符串，由设备自行使用缺省格式
 * @constructor
 */
function iCamVidoStart(vidoPath, vidoName, vidoFormat) {
    var data = JSON.stringify({'function': 'iCamVidoStart', 'vidoPath': vidoPath, 'vidoName': vidoName, 'vidoFormat': vidoFormat});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 结束视频拍摄
 * 结束视频拍摄，结束后返回视频绝对路径
 * @constructor
 */
function iCamVidoStop() {
    var data = JSON.stringify({'function': 'iCamVidoStop'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}

/**
 * 视频设置
 * 高拍仪控件弹出窗口进行视频参数设置，用户可以选择设置只在当前页面生效还是永久生效
 * @constructor
 */
function iCamVidoSet() {
    var data = JSON.stringify({'function': 'iCamVidoSet'});
    connected ? sendMessage(data) : ConnectServer(sendMessage, data)
}
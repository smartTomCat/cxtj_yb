var url = "ws://127.0.0.1:8342";
var websocket;
var connected=false;

//初始化WebSocket连接
function ConnectServer(callback,value) {

    if ('WebSocket' in window) {
        websocket = new WebSocket(url);
    }
    else if (window.WebSocket) {
        websocket = new WebSocket(url);
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket(url);
    }
    else {
        alert("浏览器版本太低！请使用Chrome、Firefox、IE10+浏览器！");
    }
    
    websocket.onopen = function () {
        connected = true;
        callback(value);
    }
    websocket.onclose = function (e) {
        connected = false;
        onMessage({ data: JSON.stringify({ FuncName: 'Connect', result: 0 }) });
    }
    websocket.onmessage = function (e) {
        onMessage(e);
    }
    websocket.onerror = function (e) {
        //if (websocket.readyState != WebSocket.OPEN||websocket.readyState == WebSocket.CLOSED) {
        //    onMessage({ data: JSON.stringify({ FuncName: 'Connect', result: 0 }) });
        //}
    };

}

//接收服务器发送的信息
function onMessage(event) {
    if (typeof (event.data) == "string") {
        var str = event.data;
        var jsonOBJ = JSON.parse(str);
        var name = jsonOBJ.FuncName;
        var re = jsonOBJ.result;
        if (name == "Connect"&&re==1) {
            connected = true;
            
        }
        produceMessage(name, re);
    }
}

//向服务器发送信息的共享方法
function sendMessage(data) {
    if (connected) {
        var jsData = JSON.stringify(data);
        websocket.send(jsData);
    }
    else {
        alert("未连接websocket服务器，请确保已运行服务端！");
    }
    
}


/*==============以下是扫描仪功能======================================================*/
//打开扫描仪--四川社保接口规范
function hsOpen() {
    if (!connected) {
        ConnectServer(shsOpen, null);
    }
    else {
        shsOpen();
    }
}
function shsOpen() {
    var data = { "Func": "hsOpen", "arg": 0 };
    sendMessage(data);
}
//设置参数--四川社保接口规范
function hsSetParameter(ParID, Value) {
    var data = { "Func": "hsSetParameter", "arg": {"ParID":ParID,"Value":Value} };
    if (!connected) {
        ConnectServer(sSetParameter, data);
    }
    else {
        sSetParameter(data);
    }
}
function sSetParameter(data) {
    sendMessage(data);
}
//开始扫描--四川社保接口规范
function hsStartScan() {
    if (!connected) {
        ConnectServer(shsStartScan, null);
    }
    else {
        shsStartScan();
    }
}
function shsStartScan() {
    var data = { "Func": "hsStartScan", "arg": 0 };
    sendMessage(data);
}
//条码识别--四川社保接口规范
function hsReadBarcode(barcodeType, path) {
    var data = { "Func": "hsReadBarcode", "arg": { "barcodeType": barcodeType, "path": path } };
    if (!connected) {
        ConnectServer(shsReadBarcode, data);
    }
    else {
        shsReadBarcode(data);
    }
}
function shsReadBarcode(data) {
    sendMessage(data);
}
//图片转pdf--四川社保接口规范
function hsMakePDF(imageList, pdfFile) {
    var data = { "Func": "hsMakePDF", "arg": { "picPath": imageList, "pdfFile": pdfFile } };
    if (!connected) {
        ConnectServer(shsMakePDF, data);
    }
    else {
        shsMakePDF(data);
    }
}
function shsMakePDF(data) {
    sendMessage(data);
}
//设置保存路径
function SetSavePath(savePath) {
    if (!connected) {
        ConnectServer(ssPath, savePath);
    }
    else {
        ssPath(savePath);
    }
}
function ssPath(savePath) {
    var data = { "Func": "SetSavePath", "arg": savePath };
    sendMessage(data);
}
//设置文件前缀
function SetFilePre(pre) {
    if (!connected) {
        ConnectServer(sFilePre, pre);
    }
    else {
        sFilePre(pre);
    }
}
function sFilePre(pre) {
    var data = { "Func": "SetFilePre", "arg": pre };
    sendMessage(data);
}
//设置Twain源名称
function SetDevice(twainName) {
    if (!connected) {
        ConnectServer(sDevice, twainName);
    }
    else {
        sDevice(twainName);
    }
}
function sDevice(twainName) {
    var data = { "Func": "SetDevice", "arg": twainName };
    sendMessage(data);
}
//获取所有twain源名称
function GetDeviceList() {
    if (!connected) {
        ConnectServer(gDeviceList, null);
    }
    else {
        gDeviceList();
    }
}
function gDeviceList() {
    var data = { "Func": "GetDeviceList", "arg": 0 };
    sendMessage(data);
}
//设置色彩模式
function SetColorType(colorType) {
    if (!connected) {
        ConnectServer(sColorType, colorType);
    }
    else {
        sColorType(colorType);
    }
}
function sColorType(colorType) {
    var data = { "Func": "SetColorType", "arg": colorType };
    sendMessage(data);
}
//设置分辨率
function SetDPI(dpi) {
    if (!connected) {
        ConnectServer(sDPI, dpi);
    }
    else {
        sDPI(dpi);
    }
}
function sDPI(dpi) {
    var data = { "Func": "SetDPI", "arg": dpi };
    sendMessage(data);
}
//设置是否弹出twainui
function ShowTwainUI(showUI) {
    if (!connected) {
        ConnectServer(sTwainUI, showUI);
    }
    else {
        sTwainUI(showUI);
    }
}
function sTwainUI(showUI) {
    var data = { "Func": "ShowTwainUI", "arg": showUI };
    sendMessage(data);
}
//设置文件类型
function SetFileType(fileType) {
    if (!connected) {
        ConnectServer(sFileType, fileType);
    }
    else {
        sFileType(fileType);
    }
}
function sFileType(fileType) {
    var data = { "Func": "SetFileType", "arg": fileType };
    sendMessage(data);
}
//设置单双面
function SetDuplex(duplex) {
    if (!connected) {
        ConnectServer(sDuplex, duplex);
    }
    else {
        sDuplex(duplex);
    }
}
function sDuplex(duplex) {
    var data = { "Func": "SetDuplex", "arg": duplex };
    sendMessage(data);
}
//设置是否传输每一张图像的base64
function SetTransPerPageBase64(trans) {
    if (!connected) {
        ConnectServer(sTransPerPageBase64, trans);
    }
    else {
        sTransPerPageBase64(trans);
    }
}
function sTransPerPageBase64(trans) {
    var data = { "Func": "SetTransPerPageBase64", "arg": trans };
    sendMessage(data);
}
//设置JPG图片压缩率
function SetJPGQuality(quality) {
    if (!connected) {
        ConnectServer(sJPGQuality, quality);
    }
    else {
        sJPGQuality(quality);
    }
}
function sJPGQuality(quality) {
    var data = { "Func": "SetJPGQuality", "arg": quality };
    sendMessage(data);
}
//获取扫描仪序列号
function GetScannerSN() {
    if (!connected) {
        ConnectServer(gScannerSN, null);
    }
    else {
        gScannerSN();
    }
}
function gScannerSN() {
    var data = { "Func": "GetScannerSN", "arg": 0 };
    sendMessage(data);
}
//开始扫描
function StartScan(pageNum) {
    if (!connected) {
        ConnectServer(sScan, pageNum);
    }
    else {
        sScan(pageNum);
    }
}
function sScan(pageNum) {
    var data = { "Func": "StartScan", "arg": pageNum };
    sendMessage(data);
}
//获取本次扫描的文件列表
function GetFilesList() {
    if (!connected) {
        ConnectServer(gFilesList, null);
    }
    else {
        gFilesList();
    }
}
function gFilesList() {
    var data = { "Func": "GetFilesList", "arg": 0 };
    sendMessage(data);
}
//图片转BASE64
function ImageToBase64(filePath) {
    if (!connected) {
        ConnectServer(imgToBase64, filePath);
    }
    else {
        imgToBase64(filePath);
    }
}
function imgToBase64(filePath) {
    var data = { "Func": "ImageToBase64", "arg": filePath };
    sendMessage(data);
}
//文件转BASE64
function FileToBase64(filePath) {
    if (!connected) {
        ConnectServer(fToBase64, filePath);
    }
    else {
        fToBase64(filePath);
    }
}
function fToBase64(filePath) {
    var data = { "Func": "FileToBase64", "arg": filePath };
    sendMessage(data);
}
//图片http上传
function httpUpload(serverAddress, filePath, del) {
    var data = { "Func": "httpUpload", "arg": { "serverAddress": serverAddress, "filePath": filePath, "del": del } };
    if (!connected) {
        ConnectServer(hUpload, data);
    }
    else {
        hUpload(data);
    }
}
function hUpload(data) {
    sendMessage(data);
}
//图片ftp上传
function ftpUpload(ftpServerIP, ftpRemotePath, ftpUserID,ftpPassword,filename,del) {
    var data = { "Func": "ftpUpload", "arg": { "ftpServerIP": ftpServerIP, "ftpRemotePath": ftpRemotePath, "ftpUserID": ftpUserID, "ftpPassword": ftpPassword, "filename": filename, "del": del } };
    if (!connected) {
        ConnectServer(fUpload, data);
    }
    else {
        fUpload(data);
    }
}
function fUpload(data) {
    sendMessage(data);
}
//图片转pdf
function ImagesToPDF(imageList, pdfFile) {
    var data = { "Func": "ImagesToPDF", "arg": { "images": imageList, "pdfFile": pdfFile } };
    if (!connected) {
        ConnectServer(imgToPDF, data);
    }
    else {
        imgToPDF(data);
    }
}
function imgToPDF(data) {
    sendMessage(data);
}
//获取指定文件夹下的文件列表
function GetFolderFileListJson(folderPath) {
    if (!connected) {
        ConnectServer(gFolderFileListJson, folderPath);
    }
    else {
        gFolderFileListJson(folderPath);
    }
}
function gFolderFileListJson(folderPath) {
    var data = { "Func": "GetFolderFileListJson", "arg": folderPath };
    sendMessage(data);
}
//删除指定文件夹下的所有文件
function DeleteImagesInFolder(folderPath) {
    if (!connected) {
        ConnectServer(dImagesInFolder, folderPath);
    }
    else {
        dImagesInFolder(folderPath);
    }
}
function dImagesInFolder(folderPath) {
    var data = { "Func": "DeleteImagesInFolder", "arg": folderPath };
    sendMessage(data);
}
//删除指定文件
function DeleteImage(filePath) {
    if (!connected) {
        ConnectServer(dImage, filePath);
    }
    else {
        dImage(filePath);
    }
}
function dImage(filePath) {
    var data = { "Func": "DeleteImage", "arg": filePath };
    sendMessage(data);
}
/*==============以下是读卡\指纹功能======================================================*/
//读身份证
function iReadIdentityCardAB() {
    if (!connected) {
        ConnectServer(siReadIdentityCardAB, null);
    }
    else {
        siReadIdentityCardAB();
    }
}
function siReadIdentityCardAB() {
    var data = { "Func": "iReadIdentityCardAB", "arg": 0 };
    sendMessage(data);
}
//读身份证
function ReadIDCard() {
    if (!connected) {
        ConnectServer(rIDCard, null);
    }
    else {
        rIDCard();
    }
}
function rIDCard() {
    var data = { "Func": "ReadIDCard", "arg": 0 };
    sendMessage(data);
}
//读二代社保卡DC
function Read2thSSCardDC() {
    if (!connected) {
        ConnectServer(r2thSSCardDC, null);
    }
    else {
        r2thSSCardDC();
    }
}
function r2thSSCardDC() {
    var data = { "Func": "Read2thSSCardDC", "arg": 0 };
    sendMessage(data);
}
//读二代社保卡
function Read2thSSCard() {
    if (!connected) {
        ConnectServer(r2thSSCard, null);
    }
    else {
        r2thSSCard();
    }
}
function r2thSSCard() {
    var data = { "Func": "Read2thSSCard", "arg": 0 };
    sendMessage(data);
}
//读三代社保卡（PSAM）
function Read3thSSCardPSAM(type) {
    if (!connected) {
        ConnectServer(r3thSSCardPSAM, type);
    }
    else {
        r3thSSCardPSAM(type);
    }
}
function r3thSSCardPSAM(type) {
    var data = { "Func": "Read3thSSCardPSAM", "arg": type };
    sendMessage(data);
}
//读三代社保卡步骤1（加密机）
function Read3thSSCardHSM1(type) {
    if (!connected) {
        ConnectServer(r3thSSCardHSM1, type);
    }
    else {
        r3thSSCardHSM1(type);
    }
}
function r3thSSCardHSM1(type) {
    var data = { "Func": "Read3thSSCardHSM1", "arg": type };
    sendMessage(data);
}
//读三代社保卡步骤2（加密机）
function Read3thSSCardHSM2(key) {
    if (!connected) {
        ConnectServer(r3thSSCardHSM2, key);
    }
    else {
        r3thSSCardHSM2(key);
    }
}
function r3thSSCardHSM2(key) {
    var data = { "Func": "Read3thSSCardHSM2", "arg": key };
    sendMessage(data);
}
//采集指纹-文件方式
function CapFingerFile(fingerPic) {
    if (!connected) {
        ConnectServer(cFingerFile, fingerPic);
    }
    else {
        cFingerFile(fingerPic);
    }
}
function cFingerFile(fingerPic) {
    var data = { "Func": "CapFingerFile", "arg": fingerPic };
    sendMessage(data);
}
//采集指纹-base64方式
function CapFingerBASE64() {
    if (!connected) {
        ConnectServer(cFingerBASE64, null);
    }
    else {
        cFingerBASE64();
    }
}
function cFingerBASE64() {
    var data = { "Func": "CapFingerBASE64", "arg": 0 };
    sendMessage(data);
}
//比对指纹-文件方式
function MatchFingerByFile(fingerPic1, fingerPic2) {
    var data = { "Func": "MatchFingerByFile", "arg": { "fingerPic1": fingerPic1, "fingerPic2": fingerPic2 } };
    if (!connected) {
        ConnectServer(mFingerByFile, data);
    }
    else {
        mFingerByFile(data);
    }
}
function mFingerByFile(data) {
    sendMessage(data);
}
//与身份证比对指纹-文件方式
function MatchCardFingerByFile(fingerPic1) {
    if (!connected) {
        ConnectServer(mCardFingerByFile, fingerPic1);
    }
    else {
        mCardFingerByFile(fingerPic1);
    }
}
function mCardFingerByFile(fingerPic1) {
    var data = { "Func": "MatchCardFingerByFile", "arg": { "fingerPic1": fingerPic1 } };
    sendMessage(data);
}
//比对指纹-base64方式
function MatchFingerByBASE64(fingerPic1, fingerPic2) {
    var strOneLength = Math.ceil(fingerPic1.length / 470), strTwoLength = Math.ceil(fingerPic2.length / 470);
    try {
        for (var i = 0; i < strOneLength; i++) {
            var data = { "Func": "FingerBase64One", "arg": { "stringOne": fingerPic1.substr(i * 470, 470) } };
            if (!connected) {
                ConnectServer(sendFingerBase64, data);
            }
            else {
                sendFingerBase64(data);
            }
        }
        for (var i = 0; i < strTwoLength; i++) {
            var data = { "Func": "FingerBase64Two", "arg": { "stringTwo": fingerPic2.substr(i * 470, 470) } };
            if (!connected) {
                ConnectServer(sendFingerBase64, data);
            }
            else {
                sendFingerBase64(data);
            }
        }
        var data = { "Func": "MatchFingerByBASE64", "arg": 0 };
        if (!connected) {
            ConnectServer(sendFingerBase64, data);
        }
        else {
            sendFingerBase64(data);
        }
    } catch (e) {

    }
    
}
function sendFingerBase64(data)
{
    sendMessage(data);
}
/*==============以下是手写屏功能======================================================*/
//开始签字
function StartSign() {
    if (!connected) {
        ConnectServer(sSign, null);
    }
    else {
        sSign();
    }
}
function sSign() {
    var data = { "Func": "StartSign", "arg": 0 };
    sendMessage(data);
}
//开启评价器
function StartEvalute() {
    if (!connected) {
        ConnectServer(sEvalute, null);
    }
    else {
        sEvalute();
    }
}
function sEvalute() {
    var data = { "Func": "StartEvalute", "arg": 0 };
    sendMessage(data);
}
//展示工牌
function StartStaff(headPic, staffName, staffNum, staffStar) {
    var data = { "Func": "StartStaff", "arg": { "headPic'": headPic, "staffName": staffName, "staffNum": staffNum, "staffStar": +staffStar } };
    if (!connected) {
        ConnectServer(sStaff, data);
    }
    else {
        sStaff(data);
    }
}
function sStaff(data) {
    
    sendMessage(data);
}
//关闭工牌
function CloseStaff() {
    if (!connected) {
        ConnectServer(cStaff, null);
    }
    else {
        cStaff();
    }
}
function cStaff() {
    var data = { "Func": "CloseStaff", "arg": 0 };
    sendMessage(data);
}
//打开PDF
function OpenPDF(filePath, withSign) {
    var data = { "Func": "OpenPDF", "arg": { "filePath": filePath, "withSign":+withSign } };
    if (!connected) {
        ConnectServer(oPDF, data);
    }
    else {
        oPDF(data);
    }
}
function oPDF(data) {
    sendMessage(data);
}
//关闭PDF窗口
function ClosePDF() {
    if (!connected) {
        ConnectServer(cPDF, null);
    }
    else {
        cPDF();
    }
}
function cPDF() {
    var data = { "Func": "ClosePDF", "arg": 0 };
    sendMessage(data);
}
//打开网页
function OpenWebPage(url) {
    if (!connected) {
        ConnectServer(oWebPage, url);
    }
    else {
        oWebPage();
    }
}
function oWebPage(url) {
    var data = { "Func": "OpenWebPage", "arg": url };
    sendMessage(data);
}
//关闭网页
function CloseWebPage() {
    if (!connected) {
        ConnectServer(cWebPage, null);
    }
    else {
        cWebPage();
    }
}
function cWebPage() {          
    var data = { "Func": "CloseWebPage", "arg": 0 };
    sendMessage(data);
}
/*以下ZC开头函数是社保定制接口*/
function ZCStartDeviceAB() {
    if (!connected) {
        ConnectServer(StartDeviceAB,null);
    }
    else {
        StartDeviceAB();
    }
}
function StartDeviceAB(){
    var data = { "Func": "ZCStartDeviceAB", "arg": 0 };
    sendMessage(data);
}
//
function ZCStopDeviceAB() {
    if (!connected) {
        ConnectServer(StopDeviceAB, null);
    }
    else {
        StopDeviceAB();
    }
}
function StopDeviceAB() {
    var data = { "Func": "ZCStopDeviceAB", "arg": 0 };
    sendMessage(data);
}
//
function getScreenType() {
    if (!connected) {
        ConnectServer(getType,null);
    }
    else {
        getType();
    }
}
function getType() {
    var data = { "Func": "getScreenType", "arg": 0 };
    sendMessage(data);
}
//
function setScreenType(mode) {
    if (!connected) {
        ConnectServer(setType, mode);
    }
    else {
        setType(mode);
    }
}
function setType(mode) {
    var data = { "Func": "setScreenType", "arg": mode };
    sendMessage(data);
}
//
function ZCBeginSignAB() {
    if (!connected) {
        ConnectServer(BeginSignAB, null);
    }
    else {
        BeginSignAB();
    }
}
function BeginSignAB() {
    var data = { "Func": "ZCBeginSignAB", "arg": 0 };
    sendMessage(data);
}
//
function ZCEndSignAB() {
    if (!connected) {
        ConnectServer(EndSignAB, null);
    }
    else {
        EndSignAB();
    }
}
function EndSignAB() {
    var data = { "Func": "ZCEndSignAB", "arg": 0 };
    sendMessage(data);
}
//
function ZCShowHtmlAB(url) {
    if (!connected) {
        ConnectServer(ShowHtmlAB, url);
    }
    else {
        ShowHtmlAB(url);
    }
}
function ShowHtmlAB(url) {
    var data = { "Func": "ZCShowHtmlAB", "arg": url };
    sendMessage(data);
}
//
function ZCShowHtmlAB2(url) {
    var data = { "Func": "ZCShowHtmlAB2", "arg": url };
    sendMessage(data);
}
function ZCCloseHtmlAB() {
    if (!connected) {
        ConnectServer(CloseHtmlAB, null);
    }
    else {
        CloseHtmlAB();
    }
}
function CloseHtmlAB() {
    var data = { "Func": "ZCCloseHtmlAB", "arg": 0 };
    sendMessage(data);
}
/*==============以下是高拍仪功能======================================================*/
//打开视频窗口
function StartVideo(camIndex) {
    if (!connected) {
        ConnectServer(stVideo, camIndex);
    }
    else {
       stVideo(camIndex);
    }
}
function stVideo(camIndex)
{
    var data = { "Func": "StartVideo", "arg": camIndex };
    sendMessage(data);
}
//打开主头视频窗口
function StartRunMain() {
    if (!connected) {
        ConnectServer(stRunMain, null);
    }
    else {
        stRunMain();
    }
}
function stRunMain() {
    var data = { "Func": "StartRunMain", "arg": 0 };
    sendMessage(data);
}
//打开副头视频窗口
function StartRunSub() {
    if (!connected) {
        ConnectServer(stRunSub, null);
    }
    else {
        stRunSub();
    }
}
function stRunSub() {
    var data = { "Func": "StartRunSub", "arg": 0 };
    sendMessage(data);
}
//按指定PID\VID打开设备
function StartRunByID(vid, pid) {
    var data = { "Func": "StartRunByID", "arg": { "VID":vid,"PID":pid}};
    if (!connected) {
        ConnectServer(stRunByID, data);
    }
    else {
        stRunByID(data);
    }
}
function stRunByID(data) {
    sendMessage(data);
}
//左旋转
function RotateLeft() {
    if (!connected) {
        ConnectServer(rLeft, null);
    }
    else {
        rLeft();
    }
}
function rLeft() {
    var data = { "Func": "RotateLeft", "arg": 0 };
    sendMessage(data);
}
//右旋转
function RotateRight() {
    if (!connected) {
        ConnectServer(rRight, null);
    }
    else {
        rRight();
    }
}
function rRight() {
    var data = { "Func": "RotateRight", "arg": 0 };
    sendMessage(data);
}
//关闭视频窗口
function StopVideo() {
    var data = { "Func": "StopVideo", "arg": 0 };
    sendMessage(data);
}
//获取视频设备列表
function GetVideoDevices() {
    if (!connected) {
        ConnectServer(gvDevices, null);
    }
    else {
        gvDevices();
    }
}
function gvDevices() {
    var data = { "Func": "GetVideoDevices", "arg": 0 };
    sendMessage(data);
}
//切换视频设备
function ChangeVideoDevice(camIndex) {
    if (!connected) {
        ConnectServer(cvDevice, camIndex);
    }
    else {
        cvDevice(camIndex);
    }
}
function cvDevice(camIndex) {
    var data = { "Func": "ChangeVideoDevice", "arg": camIndex };
    sendMessage(data);
}
//切换视频分辨率
function ChangeVideoResolution(resIndex) {
    if (!connected) {
        ConnectServer(cvResolution, resIndex);
    }
    else {
        cvResolution(resIndex);
    }
}
function cvResolution(resIndex) {
    var data = { "Func": "ChangeVideoResolution", "arg": resIndex };
    sendMessage(data);
}
//切换裁剪方式
function ChangeCutType(type) {
    if (!connected) {
        ConnectServer(cCutType, type);
    }
    else {
        cCutType(type);
    }
}
function cCutType(type) {
    var data = { "Func": "ChangeCutType", "arg": type };
    sendMessage(data);
}
//获取高拍仪序列号
function GetVideoDeviceSN() {
    if (!connected) {
        ConnectServer(gvDeviceSN, null);
    }
    else {
        gvDeviceSN();
    }
}
function gvDeviceSN() {
    var data = { "Func": "GetVideoDeviceSN", "arg": 0 };
    sendMessage(data);
}
//文件方式拍照
function CaptureFile(fileName) {
    if (!connected) {
        ConnectServer(cpFile, fileName);
    }
    else {
        cpFile(fileName);
    }
}
function cpFile(fileName) {
    var data = { "Func": "CaptureFile", "arg": fileName };
    sendMessage(data);
}
//base64方式拍照
function CaptureBase64(format) {
    if (!connected) {
        ConnectServer(cpBase64, format);
    }
    else {
        cpBase64(format);
    }
}
function cpBase64(format) {
    var data = { "Func": "CaptureBase64", "arg": format };
    sendMessage(data);
}
//采集发票二维码
function CaptureTaxQR() {
    if (!connected) {
        ConnectServer(cpTaxQR, null);
    }
    else {
        cpTaxQR();
    }
}
function cpTaxQR() {
    var data = { "Func": "CaptureTaxQR", "arg": 0 };
    sendMessage(data);
}

var timOut = 0;
var ws = "";
var m_isConnectWS = false;
var m_splitTag = "$*$";
var m_lastMessage = "";
var m_imageDataH = 0;
var m_imageDataW = 0;
var m_imageDataS = 0;
var m_stopWait = false; //同步等待
var m_closed = false; //是否被关闭了


//开启webSocket
function StartWebSocket() {
    var url = "ws://localhost:12341/";

    if ('WebSocket' in window) {
        ws = new WebSocket(url);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(url);
    }

    ws.onopen = function () {
        ws.binaryType = "arraybuffer";
        m_isConnectWS = true;
        m_closed = false;
        alert("Start your performance！")
    };

    var g_strmsg = "";
    var g_funname;
    var g_iRet;
    ws.onmessage = function (evt) {
        if (typeof (evt.data) == "string") {

            var str = evt.data;

            if (str.length <= 0) {

                return;
            }
            if (str.indexOf("longdata") >= 0) {

                var strs = new Array();
                strs = str.split(m_splitTag); //分割出参
                g_funname = strs[1];
                g_strmsg = g_strmsg + strs[2];
                g_iRet = strs[3];

            } else if (str.indexOf("over") >= 0) {
                var longstr = g_funname + m_splitTag + g_strmsg + m_splitTag + g_iRet;
                //alert(longstr);
                handleEveryMessage(longstr);
                g_strmsg = "";
                console.log(longstr);
            } else {
                handleEveryMessage(str);
                console.log(str);
            }

        }
    };

    ws.onclose = function () {
        m_isConnectWS = false;
        clearInterval(timOut);
        alert("连接失败");
    };

}

function sendWsMessage(msg) {
    //alert("send:"+msg);
    ws.send(msg);
}


function load() {
    StartWebSocket();
}

var ishowup;
var hidden, visibilityChange;
if (typeof document.hidden !== "undefined") {
    hidden = "hidden";
    visibilityChange = "visibilitychange";
} else if (typeof document.msHidden !== "undefined") {
    hidden = "msHidden";
    visibilityChange = "msvisibilitychange";
} else if (typeof document.webkitHidden !== "undefined") {
    hidden = "webkitHidden";
    visibilityChange = "webkitvisibilitychange";
}

function handleVisibilityChange() {
    if (document[hidden]) {
        //document.title = '离开';
        ishowup = 1;
        //document.title = '离开';
    } else {
        //document.title = '未离开';
        ishowup = 0;
    }
}    // 判断浏览器的支持情况

if (typeof document.addEventListener === "undefined" || typeof document[hidden] === "undefined") {
    alert("浏览器不支持");
} else {    // 监听visibilityChange事件
    document.addEventListener(visibilityChange, handleVisibilityChange, false);
}
window.onblur = function () {
    ishowup = 1;
};
window.onclick = function () {
    ishowup = 0;
    //alert("点击");
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


function ICamInitAB() {

    sendWsMessage("{\"functionName\":\"iCamInit\"}");
}

function iCamDeinitAB() {
    sendWsMessage("{\"functionName\":\"iCamDeinit\"}")
}

function iCamOpenAB(iCamNo) {
    window.clearInterval(timOut);
    sendWsMessage("{\"functionName\":\"iCamOpen\",\"iCamNo\":\"" + iCamNo + "\"}");
    //每200毫秒调用一次GetBase64
    timOut = window.setInterval(function () {
        ws.send("{\"functionName\":\"iCamGetCameraImage\"}");
    }, 200);

}

function iCamCloseAB() {
    window.clearInterval(timOut);
    sendWsMessage("{\"functionName\":\"iCamClose\"}");
}

function iCamGetResolutionListAB() {
    sendWsMessage("{\"functionName\":\"iCamGetResolutionList\"}");
}

function iCamSetResolutionAB(resolutionNo) {
    sendWsMessage("{\"functionName\":\"iCamSetResolution\",\"resolutionNo\":\"" + resolutionNo + "\"}");
}

function iCamGetCurrentResolutionAB() {
    sendWsMessage("{\"functionName\":\"iCamGetCurrentResolution\"}");
}

function iCamSetColorAB(colorCode) {
    sendWsMessage("{\"functionName\":\"iCamSetColor\",\"colorCode\":\"" + colorCode + "\"}");
}

function iCamSetCutTypeAB(cutType) {
    sendWsMessage("{\"functionName\":\"iCamSetCutType\",\"cutType\":\"" + cutType + "\"}");
}

function iCamPhotoBase64AB(imgFormat) {
    sendWsMessage("{\"functionName\":\"iCamPhotoBase64\",\"imgFormat\":\"" + imgFormat + "\"}");
}

function iCamFileToBase64AB(filePath) {
    sendWsMessage("{\"functionName\":\"iCamFileToBase64\",\"filePath\":\"" + filePath + "\"}");
}

function iCamPhotoAB(imgPath) {
    sendWsMessage("{\"functionName\":\"iCamPhoto\",\"imgPath\":\"" + imgPath + "\"}");
}

function iCamStartTimerCapAB(time, path) {
    sendWsMessage("{\"functionName\":\"iCamStartTimerCap\",\"time\":\"" + time + "\",,\"path\":\"" + path + "\"}");
}

function iCamStopTimerCapAB() {
    sendWsMessage("{\"functionName\":\"iCamStopTimerCap\"}");
}

function iCamStartAutoCapAB(path) {
    sendWsMessage("{\"functionName\":\"iCamStartAutoCap\",\"path\":\"" + path + "\"}");
}

function iCamStopAutoCapAB() {
    sendWsMessage("{\"functionName\":\"iCamStopAutoCap\"}");
}

function iCamSetRotateLeftAB() {
    sendWsMessage("{\"functionName\":\"iCamSetRotateLeft\"}");
}

function iCamSetRotateRightAB() {
    sendWsMessage("{\"functionName\":\"iCamSetRotateRight\"}");
}

function iCamZoomInAB() {
    sendWsMessage("{\"functionName\":\"iCamZoomIn\"}");
}

function iCamZoomOutAB() {
    sendWsMessage("{\"functionName\":\"iCamZoomOut\"}");
}

function iCamReadBarCodeAB(imgPath) {
    sendWsMessage("{\"functionName\":\"iCamReadBarCode\",\"imgPath\":\"" + imgPath + "\"}");
}

function iCamReadQRCodeAB(imgPath) {
    sendWsMessage("{\"functionName\":\"iCamReadQRCode\",\"imgPath\":\"" + imgPath + "\"}");
}

function iCamImageSetAB() {
    sendWsMessage("{\"functionName\":\"iCamImageSet\"}");
}

function iCamVidoStartAB(vidoPath, vidoName, vidoFormat) {
    sendWsMessage("{\"functionName\":\"iCamVidoStart\",\"vidoPath\":\"" + vidoPath + "\",\"vidoName\":\"" + vidoName + "\",\"vidoFormat\":\"" + vidoFormat + "\"}");
}

function iCamVidoStopAB() {
    sendWsMessage("{\"functionName\":\"iCamVidoStop\"}");
}

function iCamVidoSetAB() {
    sendWsMessage("{\"functionName\":\"iCamVidoSet\"}");
}


///////////////////////////////////////////////////////////////////////////
function browseFolder(path) {
    try {
        var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
        var Shell = new ActiveXObject("Shell.Application");
        var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
        //var Folder = Shell.BrowseForFolder(0, Message, 0); //起始目录为：桌面
        if (Folder != null) {
            Folder = Folder.items(); // 返回 FolderItems 对象
            Folder = Folder.item(); // 返回 Folderitem 对象
            Folder = Folder.Path; // 返回路径
            if (Folder.charAt(Folder.length - 1) != "\\") {
                Folder = Folder + "\\";
            }
            document.getElementById(path).value = Folder;
            return Folder;
        }
    } catch (e) {
        alert(e.message);
    }
}

//////////////////////////////////////////

function handleEveryMessage(str) {
    if (str.indexOf("iCamGetCameraImage") >= 0) {
        var strs = new Array();
        strs = str.split(m_splitTag); //分割出参
        setImageWithBase64(strs[1]);
        //var jsonObj= eval('(' + str + ')');
        //setImageWithBase64(jsonObj.imgBase64Str);
    } else {
        showmessage(str);
    }
}


function Uint8ToString(u8a) {
    var CHUNK_SZ = 0x8000;
    var c = [];
    for (var i = 0; i < u8a.length; i += CHUNK_SZ) {
        c.push(String.fromCharCode.apply(null, u8a.subarray(i, i + CHUNK_SZ)));
    }
    return c.join("");
}

function setImageWithBase64(str) {
//alert(str);
    var myimg = document.getElementById("myCanvas");
    myimg.src = "data:image/png;base64," + str;

}


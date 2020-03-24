/**
 * 消息提示
 * Created by xiep
 */
var Notice = (function () {
    var $window,$windowContext,$windowClose;

    function initNoticeWindow(title,context,author,seconds) {
        $(".message-notice-win").hide();
        $window = $("<div class='message-notice-win'></div>");
        var $windowHead = $("<div class='message-notice-win-head'><span class='notice-head-icon faceIcon icon-tishi'></span><span class='notice-head-span'>"+(author||"消息通知")+"</span></div>");
        $windowClose = $("<span class='notice-head-close faceIcon icon-close'></span>");

        $windowContext = $("<div class='message-notice-win-context'></div>");
        var $windowContextTitle = $("<div class='message-title'></div>");
        var $windowContextContext = $("<div class='message-context'></div>");

        $windowContextTitle.text(title);
        $windowContextContext.html(context);

        $windowHead.append($windowClose);
        $windowContext.append($windowContextTitle);
        $windowContext.append($windowContextContext);
        $window.append($windowHead);
        $window.append($windowContext);
        $("body").append($window);

        initEvent();
        initAnimation($window,seconds);
    }
    
    function initEvent() {

        $windowClose.on("click",function (e) {
            $window.slideUp(500,function () {
                $window.remove();
            });
        })
        $windowContext.on("click",function () {
            UserInfo.showNoticeInfo();
            $window.remove();
        })
    }
    
    function initAnimation($window,seconds) {
        $window.slideDown(500);
        setTimeout(function () {
            $window.slideUp(500,function () {
                $window.remove();
            });

        },seconds||5000)
    }

    return {
        initNoticeWindow : initNoticeWindow
    }
})()
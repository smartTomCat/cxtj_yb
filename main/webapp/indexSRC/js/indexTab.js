/*
 * index-tab功能
 * author: Selina
 * */
var indexTab = (function () {

    var resizeFlag = false;
    /*
     * _addTab 添加tab
     * @param id：tab_id, text：标题, url：路径, reload：是否重置
     * */
    function _addTab(id, text, url, reload){
        var tab = _getTab(id);
        //若已经存在，直接激活
        if (tab) return _actTab(id, reload);

        $(".index-tab-li").removeClass("active");
        var tArr = [];
        tArr.push("<li class=\"index-tab-li active\"><p>"+text+"</p><span class=\"faceIcon icon-close\" onclick=\"indexTab.closeTab('" + id + "')\"></span></li>");
        var html = $(tArr.join(""));
        html.attr("id", "tab_" + id).attr("_id", id).insertBefore(".index-tab-more");
        _adjustTabWidth();

        //添加iframe
        var tabBody = $("<iframe class='mainFrame' frameborder=\"0\"/>").attr("id", "tab_b_" + id).attr("name", "tab_b_" + id);
        if (reload){
            $("#tab_b_" + id).attr('src', $("#tab_b_" + id).attr('src'));
        }
        tabBody.css({
            width: "100%",
            height: $("#mainFrameBox").height()
        });
        if (url) {
            if(url.indexOf("?") > 0){
                tabBody.attr("src", url+"&___businessId="+id);
            }else{
                tabBody.attr("src", url+"?___businessId="+id);
            }
        }
        $("#mainFrameBox").append(tabBody);
        _tabShow(id);
    }

    /*
     * _actTab 激活tab
     * @param id：tab_id reload：是否重置
     * */
    function _actTab(id, reload){
        if($("#tab_" + id).parent("ul").hasClass("index-tab-parent")){
            //tab菜单里的
            $("#tab_" + id).addClass("active").siblings(".index-tab-li").removeClass("active");
        }else{
            //更多菜单里
            $($("#tab_" + id).prop("outerHTML")).insertBefore(".index-tab-more");
            $("#index_tab_more #tab_"+ id +"").remove();
            $("#tab_" + id).addClass("active").siblings().removeClass("active");
            _adjustTabWidth();
        }
        _tabShow(id);
        if(reload){
            $("#tab_b_" + id).attr('src', $("#tab_b_" + id).attr('src'));
        }
    }

    /*
     * _tabShow 显示Frame
     * @param id：tab_id
     * */
    function _tabShow(id){
        $("#tab_01").removeClass("active");
        _closeMore();
        $("#mainFrameBox iframe").hide();
        $("#tab_b_" + id).show();
        if(id == "01" && resizeFlag){
            $("#tab_b_01").attr('src', $("#tab_b_01").attr('src'));
            resizeFlag = false;
        }
        window.currentBuinessId = id;
    }

    /*
     * _getTab 获取tab
     * @param id：tab_id
     * */
    function _getTab(id) {
        return document.getElementById("tab_" + id);
    }

    /*
     * _closeTab 关闭tab标签
     * @param id：tab_id
     * */
    function _closeTab(id) {
        if (!id)
            return;
        var $tab = $("#tab_"+ id);
        $("#tab_b_" + id).remove();
        if($tab.hasClass("active")){
            if($tab.next().hasClass("index-tab-more")){
                if($tab.prev().length){
                    _tabShow($tab.prev().attr("_id"));
                    $tab.prev().addClass("active");
                }else {
                    _tabShow("01");
                    $("#tab_01").addClass("active");
                }
            }else {
                _tabShow($tab.next().attr("_id"));
                $tab.next().addClass("active");
            }
        }else {
            if($tab.parent("#index_tab_more").length){
                _closeMore();
            }
        }
        $tab.remove();
        _adjustTabWidth();
    }

    function _closeAllTab(){
        $(".index-tab-li").remove();
        $("#mainFrameBox .mainFrame").remove();
        _tabShow("01");
        $("#tab_01").addClass("active");
    }

    /*
     * _tabClickEvent 点击事件
     * */
    function _tabClickEvent() {
        //点击tab
        $(document).on("click", ".index-tab-li", function(e){
            var $e = $(e.target);
            if(!$e.hasClass("icon-close")){
                _actTab($(this).attr("_id"));
            }
            if($(".index-tab-more").hasClass("active")){
                _closeMore();
            }
        });
        //点击工作台
        $(document).on("click", ".index-tab-gzt", function(){
            _tabShow("01");
            $(".index-tab-li").removeClass("active");
            $("#tab_01").addClass("active");
        });
        //更多菜单
        $(document).on("click", ".index-tab-more", function(e){
            var $e = $(e.target), $this = $(this);
            //更多菜单弹出框距离右边的位置
            var wd = document.documentElement.clientWidth;
            var left = $this.offset().left;
            var differ = wd - left;
            if(differ < 220){
                $(".index-tab-more-pos").css("margin-left", (differ-220-5)+"px");
            }else {
                $(".index-tab-more-pos").css("margin-left", "0px");
            }
            if($this.hasClass("active")){
                _closeMore();
            }else {
                $this.addClass("active").find(".index-tab-more-pos").removeClass("display");
            }
            //关闭全部
            if($e.hasClass("index-tab-more-close")){
                _closeAllTab();
            }else if($e.hasClass("index-tab-li") || $e.parent().hasClass("index-tab-li")){
                _closeMore();
            }else if($e.hasClass("index-tab-gzt")){
                _closeMore();
            }
        });
    }

    /*
     * _adjustTabWidth 增加、删除tab，改变窗口大小的时候改变tab显示个数
     * @param type：窗口resize时触发_adjustTabWidth()
     * @param leftControl：点击左侧菜单收起展开
     * */
    function _adjustTabWidth(type,leftControl) {
        var list_right =  $(".index-tabs>.index-tab-gzt").width() + $(".index-tab-more").width();//工作台和更多按钮宽度
        //200:tab最大宽度为200px。更多菜单里有tab时，将更多菜单里的最新一个tab放在主面板tab中
        //场景 1：屏幕增大时 2：减少tab时
        while (($(".index-tabs").width() - list_right - $("#index_tab_ul").width() > 200) && ($("#index_tab_more").find("li").length > 0)){
            var $tabLast = $("#index_tab_more").find("li:last");
            $($tabLast.prop("outerHTML")).prependTo("#index_tab_ul");
            $tabLast.remove();
            if($tabLast.hasClass("active")){
                _tabShow($tabLast.attr("_id"));
            }
        }
        //主面板tab减少，且有tab时，放到更多菜单里
        //场景 1：屏幕缩小时 2：添加tab时
        while (($(".index-tabs").width() - list_right - $("#index_tab_ul").width() < 30) && ($("#index_tab_ul").children(".index-tab-li").length > 0)){
            var $indexTabLi = $("#index_tab_ul>.index-tab-li").eq(0);
            if($indexTabLi.hasClass("active")){
                if($("#index_tab_ul>.index-tab-li").eq(1).length){
                    var $indexTabLiNext = $("#index_tab_ul>.index-tab-li").eq(1);
                    $("#index_tab_more").append($indexTabLiNext.prop("outerHTML"));
                    $indexTabLiNext.remove();
                }else {
                    $("#index_tab_more").append($indexTabLi.prop("outerHTML"));
                    $indexTabLi.remove();
                    _tabShow("01");
                    $("#tab_01").addClass("active");
                }
            }else {
                $("#index_tab_more").append($indexTabLi.prop("outerHTML"));
                $indexTabLi.remove();
            }
        }
        //resize
        if(type){
            if(leftControl){
                resizeFlag = true;
            }
            if($("#tab_01").hasClass("active")){
                _tabShow("01");
                $("#tab_01").addClass("active");
            }else{
                _tabShow($(".index-tab-li.active").attr("_id"));
            }
            $(".index-tab-parent").removeClass("display");
        }
    }

    /*
     * _closeMore 关闭工作台弹出框
     * @param
     * */
    function _closeMore() {
        $(".index-tab-more").removeClass("active").find(".index-tab-more-pos").addClass("display");
    }

    /*
     * _resizeFrameHeight Frame适应屏幕宽高
     * */
    function _resizeFrameHeight() {
        var h = parseInt($(window).height(),10), toph = $(".top-part").height()+$(".index-tabs").height();
        $("#mainFrameBox").css("height",h-toph);
        $("#mainFrameBox iframe").css("height",h-toph);
    }

    /*
     * _indexTabResize worktable/indexTab resize
     * */
    function _indexTabResize() {
        _adjustTabWidth(true,true);
    }

    return {
        init: function () {
            _tabClickEvent();
            var resizeEventTimer= null;
            $(window).resize(function(){
                $("#mainFrameBox iframe").hide();
                $(".index-tab-parent").addClass("display");
                if (resizeEventTimer){
                    clearTimeout(resizeEventTimer);
                }
                resizeEventTimer = setTimeout(function(){
                    _adjustTabWidth(true);
                    _resizeFrameHeight();
                }, 200);
            });
            var h = parseInt($(window).height(),10), toph = $(".top-part").height()+$(".index-tabs").height();
            $("#mainFrameBox").css("height",h-toph);
            $("#mainFrameBox").append("<iframe name=\"tab_b_01\" frameborder=no src=\""+basePath+"sysapp/portalAction.do\" style=\"width: 100%; height: "+$("#mainFrameBox").height()+"px\" id=\"tab_b_01\"></iframe>");
        },
        addTab: _addTab,
        closeTab: _closeTab,
        closeAllTab:_closeAllTab,
        closeMore: _closeMore,
        indexTabResize: _indexTabResize
    };
})();
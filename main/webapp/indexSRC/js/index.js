/* 首页的各种初始化以及公共方法等
 * 1.一级菜单显示方式(水平/下拉)
 * 2.三级菜单的出现方式(手风琴/侧拉)
 * 2.初始化左侧菜单的宽度
 * */
var indexCommon = (function () {
    /*初始化首页样式*/
    function _initStyle() {
        //初始化左侧菜单的宽度
        var w = parseInt(getWebConfig("leftmenuWidth"));
        $(".left-part").width(w);
        $(".drag-bar").css("left", w + "px");
        $(".right-part").css("padding-left", w + "px");
    }

    /*初始化菜单显示*/
    function _initMenu(menuList) {
        var menuLeve1OneStyle = getWebConfig("menuLeve1OneStyle"),
            menuLevelThreeStyle = getWebConfig("menuLevelThreeStyle");
        menuLeve1OneStyle == "horizon" ? menuLevelOneHorizon.init(menuList) : menuLevelOneDropdown.init(menuList);
    }

    /*初始化左侧菜单的控制控制收起来和展开以及拖动*/
    function _initLeftPartContol() {
        //左侧菜单打开和关闭控制
        $("#left-part-control").bind("click", function (e) {
            var $self = $(this);
            $("#mainFrameBox iframe").hide();
            $(".index-tab-parent").addClass("display");
            if ($self.hasClass("active")) {
                $self.removeClass("active");
                $(".left-part").stop().animate({
                    "width": "0px"
                });
                $(".drag-bar").stop().animate({
                    "left": "0px"
                });
                $(".right-part").stop().animate({
                    "padding-left": "0px"
                },function () {
                    indexTab.indexTabResize();
                });
                $("#menu-one-dropdown-control").css("display", "none")

            } else {
                var w = parseInt(getWebConfig("leftmenuWidth"));
                $self.addClass("active");
                $(".left-part").stop().animate({
                    "width": w + "px"
                });
                $(".drag-bar").stop().animate({
                    "left": w + "px"
                });
                $(".right-part").stop().animate({
                    "padding-left": w + "px"
                },function () {
                    indexTab.indexTabResize();
                });
                $("#menu-one-dropdown-control").css("display", "inline-block")

            }
        });
        //左侧菜单拖动分割调宽度控制初始化
        var resbar = new _resizeObject("#drag-bar");
    }
    function _resizeObject(el) {
        var self = this;
        self.mouse_top = 0;
        self.mouse_left = 0;
        self.el = $(el);//拖动对象
        self.lf = parseInt(self.el.css("left"));//拖动对象的left
        self.tp = parseInt(self.el.css("top"));//拖动对象的top
        self.el.bind("mousedown", function (e) {
            self.mouse_left = e.clientX, self.mouse_top = e.clientY;
            $("#right-part").css({"display": "none"});
            $(document).bind("mousemove", mouseMoveDrag).bind("mouseup", mouseUpDrag);
        })
        //拖动改变位置
        function mouseMoveDrag(e) {
            var target = self.el;
            var dx = e.clientX - self.mouse_left;
            var dy = e.clientY - self.mouse_top;
            var l = parseInt(target.css("left"));
            var t = parseInt(target.css("top"));
            var movex = l + dx;
            var movey = t + dy;
            // if (movex < 27) {
            //     movex = 27;
            // }
            if (movex > 350) {
                movex = 350;
            }
            if (movex > 90) {
                $("#menu-one-dropdown-control").show();

            }
            if (movex < 90) {
                $("#menu-one-dropdown-control").hide();
                $("#left-part-control").removeClass("active");

            }
            self.mouse_left = e.clientX;
            self.mouse_top = e.clientY;
            self.lf = movex;
            self.tp = movey;
            /* target.css({'left': movex+"px", 'top': movey+"px"}); */
            target.css({'left': movex + "px"});
            $(".left-part").width(movex);
            $(".right-part").css({"padding-left": movex});
        };
        function mouseUpDrag(e) {
            $(".right-part").css({"display": "block"});
            $(document).unbind("mousemove", mouseMoveDrag).unbind("mouseup", mouseUpDrag);
        }
    }

    //body 点击控制弹出框隐藏
    $(document.body).bind("mousedown", function (e) {
        var event = e || window.event;
        var target = event.target || event.srcElement;
        _popBoxControl(target);
    });

    function _popBoxControl(target){
        if(target){
            //如果点击的不是水平菜单的更多,那么水平菜单的更多菜单隐藏
            if ($(".more-menu-con").length!=0 && $(".more-menu-con")[0] != target && !$.contains($(".more-menu-con")[0], target)) {
                menuLevelOneHorizon.hideMoreMenu();
            }
            //如果点击的不是用户信息那么关闭用户信息
            if($("#userMenu") && $("#userMenu")[0]!==target &&　!$.contains($("#userMenu")[0], target) && $(".user-info-control")[0]!=target){
                UserInfo.hideUserInfo();
            }
            //如果点击的不是右侧slide菜单那么关闭右侧slide菜单
            if($("#left-part_n").length!=0 && $("#left-part_n")[0]!==target &&　!$.contains($("#left-part_n")[0], target)){
                menuNextSlide.closeAllMenuSlideNext();
            }
            //如果点击不是tab更多菜单那么关闭tab的更多
            if($(".index-tab-more").length!=0 && $(".index-tab-more")[0]!==target &&　!$.contains($(".index-tab-more")[0], target)){
                indexTab.closeMore();
            }

        }else {
            menuLevelOneHorizon.hideMoreMenu();
            UserInfo.hideUserInfo();
            menuNextSlide.closeAllMenuSlideNext();
            indexTab.closeMore();
        }


    }
    function _initIndexTab() {
        indexTab.init();
    }


    function _initGuide(){
        window.currentBuinessId = "01";//初始值：工作台
        function showPageGuide() {
            var data = [{
                id: $(".user-info-control"),
                message: "这里可以进行用户管理，具体内容包括 修改密码、切换岗位、更换皮肤、退出系统！"
            },{
                id: $("#menu-common-control"),
                message: "这里可查看常用菜单！"
            },{
                id: $(".helptip"),
                message: "这里可以重新进行引导"
            }];
            new TaHelpTip($("body")).helpTip({
                replay: false,
                show: true,
                cookname: window.currentBuinessId,
                data: data
            });
        }
        //打开当前页面(tabActive)的帮助提示 TODO xiep 改为 crossDomain
        window.toShowPageGuide = function() {
            if (window.frames["tab_b_" + window.currentBuinessId].window.$(".hint-tips").length > 0) {
                return;
            }
            new TaHelpTip().clearCookieHintArray(window.currentBuinessId);
            if (window.currentBuinessId != "01") {
                var fun = window.frames["tab_b_" + window.currentBuinessId].window.fnPageGuide;
                if (fun && typeof(fun) == "function") {
                    window.frames["tab_b_" + window.currentBuinessId].window.fnPageGuide(window.currentBuinessId);
                } else {
                    alert("该页面没有引导");
                }
            } else {
                showPageGuide();
            }
        }
    }
    return {
        init: function (menuList) {
            _initStyle();
            _initMenu(menuList);
            _initLeftPartContol();
            _initIndexTab();
            _initGuide();
        },
        popBoxControl:_popBoxControl

    }

})();


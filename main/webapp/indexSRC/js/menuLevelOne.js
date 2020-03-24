/*
 * menuLevelOne 一级菜单
 * 分为两种
 * menuLevelOneHorizon 放在头顶部的水平显示模式
 * menuLevelOneDropdown 放在左侧下拉显示的模式
 * author:cy
 * */
var menuLevelOneHorizon = (function () {
    var menuList;
    //获取元素 宽度(包括小数)
    function _getElWidth(el) {
        var elm = document.getElementById(el);
        var wd, rect = elm.getBoundingClientRect();
        if (rect.width) {
            wd = rect.width;
        } else {
            wd = rect.right - rect.left;
        }
        return wd;
    }

    /*
     * _setMoreMenu初始水平菜单
     * @param  menuList 菜单数据
     * @author cy
     * */
    function _setMoreMenu() {
        //modify by cy
        $(".more-menu-con").remove();//清空
        var menu_con = $("<div class='more-menu-con' ></div>");
        $("#item-more").append(menu_con);
        var morelist = "<div class='more-menucon'>";
        for (var i = 0; i < menuList.length; i++) {
            if ($("#" + menuList[i].menuId).length <= 0) {
                var icon = menuList[i]["img"] ? menuList[i]["img"].split("?")[0] : "icon-001";
                // var color = menuList[i]["img"] ? (menuList[i]["img"].split("?")[1] ? menuList[i]["img"].split("?")[1] : "") : "";
                morelist = morelist + "<div  class='item-menu " + icon + "' id='" + menuList[i].menuId + "' data-i='" + i + "'>" +
                    "<span>" + menuList[i].menuName + "</span></div>";
            }
        }
        morelist = morelist + "</div>";
        menu_con.html(morelist).focus();
        // //初始化选中项
        // var active_item = $("#horizon-menu").attr("data-active");
        // active_item ? $("#" + active_item).addClass("active") : $("#" + menuList[0].menuId).addClass("active");

    }

    /*
     *_createMenuLevelOneHorizon创建水平菜单
     * @param  menuList 菜单数据
     * @author cy
     * */
    function _createMenuLevelOneHorizon() {
        var $menu = $("#horizon-menu");
        $(".item-menu").remove();//清空节点
        _hideMoreMenu();//清空
        var $item_more = $("#item-more");
        $item_more.html("更多导航");
        var more_width = 120;//更多选项的预设宽度
        var items_width = 0;
        //装载 menu list
        for (var i = 0; i < menuList.length; i++) {
            var icon = menuList[i]["img"] ? (menuList[i]["img"].split("?")[0]=="" ? "icon-001":menuList[i]["img"].split("?")[0]) : "icon-001";
            // var color = menuList[i]["img"] ? (menuList[i]["img"].split("?")[1] ? menuList[i]["img"].split("?")[1] : "") : "";
            var item = "<div  class='item-menu " + icon + "' id='" + menuList[i].menuId + "'  data-i='" + i + "'>" +
                "<span>" + menuList[i].menuName + "</span></div>";
            $item_more.before(item)
            var list_width = $menu.width();//总宽度
            var item_width = _getElWidth(menuList[i].menuId);//item宽度
            items_width = items_width + item_width;//items宽度
            if (items_width + more_width > list_width) {//当加入元素宽度大于list的那么移除节点并加入更多菜单的选项卡
                $("#" + menuList[i].menuId).remove();
                $item_more.css("display", "inline-block");
                break;
            } else {
                $item_more.css("display", "none");
            }
        }
        if (menuList.length > 0) {
            //初始化选中项
            var active_item = $menu.attr("data-active");
            active_item ? $("#" + active_item).addClass("active") : $("#" + menuList[0].menuId).addClass("active");
            $(".menu-one-dropdown-control").html( menuList[0].menuName||"")
        }
    }

    function _bindMenuItemEvent() {
        $("#horizon-menu").on("click",".item-menu", function (e) {
            var $target=$(this);
            //调整样式
            $(".item-menu.active,.item-more.active").removeClass("active");
            $target.addClass("active");
            $target.attr("data-active", this.id);
            var menuObj = menuList[this.getAttribute("data-i")];
            //设置左侧菜单头的文字
            $(".menu-one-dropdown-control").html(menuObj.menuName||"")
            //打开url对应的tab页
            if (menuObj && menuObj.url) {
                indexTab.addTab(menuObj.menuId, menuObj.menuName, menuObj.url, false);
            }
            _hideMoreMenu();
            //打开对应的初始化二级菜单
            var level2List = (menuObj && menuObj.childNode) ? menuObj.childNode : [];
            menuLevelTwo.init(level2List);

        });
        $("#horizon-menu").on("click",".item-more",function (e) {
            $(".item-menu.active").removeClass("active");
            var event=e || window.event;
            var target=e.target || e.srcElement;
            target.id=="item-more" && _setMoreMenu();


        })

    }

    function _hideMoreMenu() {
       $(".more-menu-con").remove();
    }
    return {
        init: function (menulist) {
            menuList = menulist;
            _createMenuLevelOneHorizon();
            _bindMenuItemEvent();
            if(menuList){
                menuList[0]?( menuList[0].childNode? menuLevelTwo.init(menuList[0].childNode):menuLevelTwo.init([])):menuLevelTwo.init([]);
            }
            $(window).resize(function () {
                _createMenuLevelOneHorizon();
            });

        },
        showMoreMenu: _setMoreMenu,
        hideMoreMenu: _hideMoreMenu

    };
})();

var menuLevelOneDropdown = (function () {
    var menuList;
    var $menucon = $("#menu-one-dropdown");
    var $control = $(".menu-one-dropdown-control");

    function _createMenuLevelOneDropdown() {

       //初始化内容
        var str = [];
        for (var i = 0; i < menuList.length; i++) {
            var icon = menuList[i]["img"] ? (menuList[i]["img"].split("?")[0]=="" ? "icon-001":menuList[i]["img"].split("?")[0]) : "icon-001";
            str.push("<div  class='item-menu " + icon + "' id='"+ menuList[i].menuId +"' data-i='" + i + "'>" +
                "<span>" + menuList[i].menuName + "</span></div>");
        }
        $menucon.append(str.join(""));
        _setControl();
        _bindMenuItemEvent();
        //给控制条bind事件
        $control.bind("click", function () {
            $control.hasClass("active") ? _hideMoreMenu() : _showMoreMenu();
        });

    }
    function _setControl() {
        //设置显示选中的内容,以及选中的样式
        if(menuList && menuList.length){
            var active_item = $menucon.attr("data-active"),$active_item= $("#" + active_item);
            $(".item-menu").removeClass("active");
           if(active_item){
               $active_item.addClass("active");
               $control.html($active_item.text())
           }else {
               //如果是初始化的时候那么默认选中第一个menu,并初始化对应的二级菜单
               $("#" + menuList[0].menuId).addClass("active");
               $menucon.attr("data-active",menuList[0].menuId);
               $control.html(menuList[0].menuName);
               _nextMenuInit(0);
           }
        }
    }
    function _bindMenuItemEvent() {
        $menucon.bind("click",function (e) {
            var event = e || window.event;
            var target = event.target || event.srcElement;
            target.tagName.toUpperCase() == "SPAN" && (target = target.parentNode);
            if (!$(target).hasClass("item-menu"))return;
            //添加样式
            $menucon.attr("data-active",target.id);
            _setControl();
            _nextMenuInit(target.getAttribute("data-i"));
            _hideMoreMenu();
        });
    }
    function _nextMenuInit(index) {
        var menuObj = menuList[index];
        //打开url对应的tab页
        if (menuObj && menuObj.url) {
            indexTab.addTab(menuObj.menuId, menuObj.menuName, menuObj.url, false);
        }
        //打开对应的二级菜单
        var level2List = (menuObj && menuObj.childNode) ? menuObj.childNode : [];
        menuLevelTwo.init(level2List);
    }


    function _showMoreMenu() {
        $control.addClass("active");
        $menucon.stop().slideDown();
    }

    function _hideMoreMenu() {
        $control.removeClass("active");
        $menucon.stop().slideUp();
    }

    return {
        init: function (menulist) {
            menuList = menulist;
            _createMenuLevelOneDropdown();
            if(menuList){
                menuList[0]?( menuList[0].childNode? menuLevelTwo.init(menuList[0].childNode):menuLevelTwo.init([])):menuLevelTwo.init([]);
            }

        }
    }

})();
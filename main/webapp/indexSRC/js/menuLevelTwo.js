/**
 * 左侧二级菜单menuLevelTwo;
 * @author cy
 */
var menuLevelTwo = (function () {
    var menuList;
    var $menucon = $("#menu-two");

    function _createMenuLevelTwo() {
        _menuTwoShow();
        $menucon.empty();
        var str = [], item = [];
        for (var i = 0; i < menuList.length; i++) {
            item = [];
            //判断是否有子节点
            var haschild = (menuList[i].childNode && menuList[i].childNode.length != 0) ? true : false;
            //判断是否是常用菜单
            var isCommonMenu = (menuList[i].isCommonMenu=='yes')? true : false;
            //判断是否显示二级菜单图标
            var icon = getWebConfig("menuLeve1TwoIfIcon") == "true" ? (menuList[i]["img"] ? " menuIcon " + menuList[i]["img"].split("?")[0] : " menuIcon  icon-001") : "menu-two-dot";
            item.push("<div class='menu-two-item-con' onclick='menuLevelTwo.menuTwoGotoNext(this)' data-i='" + i + "' id='" + menuList[i].menuId + "' haschild='"+haschild+"'>");

            item.push("<div class='menu-two-item' ><span class='menu-two-icon " + icon + "'></span>" + menuList[i].menuName + "</div>");
            //判断是否有子节点,如果有那么显示next图标 否则显示收藏图标
            if (haschild) {
                item.push("<span class='menu-next-control faceIcon icon-arrow_right'></span>");
            } else if(!isCommonMenu) {
                item.push("<span class='menu-item-store faceIcon icon-favorites' data-id='" + menuList[i].menuId + "'  onclick='menuCommon.addMenuCommon(this)'></span>");
            }
            item.push("</div>");
            str.push(item.join(""));
        }
        $menucon.append(str.join(""));
    }


    function _menuTwoGotoNext(el) {
        var $el = $(el), menuObj = menuList[$el.attr("data-i")];
        if (menuObj && menuObj.url) {
            //打开页面
            indexTab.addTab(menuObj.menuId, menuObj.menuName, menuObj.url, false);
        }
        if ($el.hasClass("active")) {//如果自身是展开状态那么点击关闭
            $el.removeClass("active");
            $el.find(">.menu-next-control").removeClass("icon-arrow_down").addClass("icon-arrow_right");
            //如果有子节点 关闭对应的三级菜单节点
            if (menuObj && menuObj.childNode && menuObj.childNode.length != 0) {
                getWebConfig("menuLevelThreeStyle") == "dropdown" ? menuLevelThreeDropdown.hideMenuLevelThreeDropdown(el.id) : menuNextSlide.hideMenuSlideNext("left-part");
            }
        } else {//如果自身不是展开状态那么点击展开
            if(getWebConfig("menuLevelThreeDropdownIfShowAll")&& getWebConfig("menuLevelThreeStyle") == "dropdown"){
                //如果是三级下拉菜单全部展示,并且三级菜单是下拉方式,那么点击之后其他的菜单不自动收回,并且没有下级菜单的元素去掉激活状态
                $(".menu-two-item-con.active[haschild=false]").removeClass("active");
                $el.addClass("active");
            }else {
                //去掉其他节点的展开状态---start---
                var $flag = $(".menu-two-item-con.active").find(">.menu-next-control");
                if ($flag.length > 0) {
                    if (getWebConfig("menuLevelThreeStyle") == "dropdown") {
                       menuLevelThreeDropdown.hideMenuLevelThreeDropdown($(".menu-two-item-con.active").attr("id"));
                       $flag.removeClass("icon-arrow_down").addClass("icon-arrow_right");
                    } else {
                        menuNextSlide.hideMenuSlideNext("left-part");
                    }
                }
                $(".menu-two-item-con.active").removeClass("active");
                //去掉其他节点的展开状态---end---
                $el.addClass("active");
            }


            //如果有下级菜单那么打开下级菜单并且关闭其他三级菜单.
            if (menuObj && menuObj.childNode && menuObj.childNode.length != 0) {
                if (getWebConfig("menuLevelThreeStyle") == "dropdown") {
                    $el.find(">.menu-next-control").removeClass("icon-arrow_right").addClass("icon-arrow_down");
                    menuLevelThreeDropdown.showMenuLevelThreeDropdown(el.id, menuObj.childNode)
                } else {
                    menuNextSlide.showMenuSlideNext("left-part", menuObj.childNode);
                }
            }
        }
    }
    function _menuTwoShow() {
        $menucon.css("display", "block");
    };
    function _menuTwoHide() {
        $menucon.css("display", "none");
    }
    function _menuTwoShowAll() {
        $(".menu-two-item-con[haschild=true]").click();
    }

    return {
        init: function (menulist) {
            if (menulist) {
                menuList = menulist;
            }
            _createMenuLevelTwo();
            //如果是需要全部展开二级菜单(必须在三级菜单式下拉情况下)
            if(getWebConfig("menuLevelThreeDropdownIfShowAll")&& getWebConfig("menuLevelThreeStyle") == "dropdown"){
                _menuTwoShowAll();
            }
        },
        menuTwoShow: _menuTwoShow,
        menuTwoHide: _menuTwoHide,
        menuTwoGotoNext: _menuTwoGotoNext

    }
})();



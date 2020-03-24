/**
 * menuLevelThreeDropdown 下拉出三级菜单
 * 下拉菜单传入的容器是 直接的上级二级菜单作为容器
 * @author cy
 */
var menuLevelThreeDropdown = (function () {

    function _showMenuLevelThreeDropdown(con, menuList) {
        var $con;
        if (con) {
            $con = $("#" + con);
        } else {
            return;
        }
        if ($con.find(".menu-three-dropdown-con").length < 1) {
            _creatMenuLevlThreeDropdown($con, menuList)
        }
        $con.find(".menu-three-dropdown-con").stop().slideDown();

    }

    function _creatMenuLevlThreeDropdown($con, menuList) {
        var $menucon = $("<div class='menu-three-dropdown-con'></div>"), item = [];
        $menucon.data(menuList);
        $con.append($menucon);
        for (var i = 0; i < menuList.length; i++) {
            //判断是否有子节点
            var haschild = (menuList[i].childNode && menuList[i].childNode.length != 0) ? true : false;
            //判断是否是常用菜单
            var isCommonMenu = (menuList[i].isCommonMenu=='yes')? true : false;
            //判断是否显示菜单图标
            var icon = getWebConfig("menuLevelThreeDropdownIfIcon") == "true" ? (menuList[i]["img"] ? " menuIcon " + menuList[i]["img"].split("?")[0] : " menuIcon  icon-001") : "";
            item.push("<div class='menu-three-item " + icon + "' id='" + menuList[i].menuId + "'data-i='" + i + "' >");
            item.push(menuList[i].menuName);
            //判断是否有子节点,如果有那么显示next图标 否则显示收藏图标
            if (haschild) {
                item.push("<span class='menu-next-control faceIcon icon-arrow_right'></span>");
            } else if(!isCommonMenu) {
                item.push("<span class='menu-item-store faceIcon icon-favorites' data-id='" + menuList[i].menuId + "'  onclick='menuCommon.addMenuCommon(this)'></span>");
            }
            item.push("</div>");
        }
        $menucon.append(item.join(""));

        $menucon.bind("click", function (e) {
            var event = e || window.event;
            var target = event.target || event.srcElement;
            $(target).hasClass("menu-next-control") && (target = target.parentNode);
            var $target = $(target), menuList = $menucon.data(), menuObj = menuList[$target.attr("data-i")];
           //如果点击的是自身那么下一级也关闭
            if($target.hasClass("active")){
                $target.removeClass("active");
                menuNextSlide.hideMenuSlideNext("left-part");
                return false;
            }
            //设置选中状态
            $(".menu-three-item").removeClass("active");
            $target.addClass("active");
            if (menuObj && menuObj.url) {
                //打开页面
                indexTab.addTab(menuObj.menuId, menuObj.menuName, menuObj.url, false);
            }
            //如果有子节点 打开子节点否则关闭下一级菜单
            if (menuObj && menuObj.childNode && menuObj.childNode.length != 0) {
                menuNextSlide.showMenuSlideNext("left-part", menuObj.childNode);
            }else {
                menuNextSlide.hideMenuSlideNext("left-part");
            }
            return false;
        })

    }


    function _hideMenuLevelThreeDropdown(con) {
        var $con;
        if (con) {
            $con = $("#" + con);
        } else {
            return;
        }
        $con.find(".menu-three-dropdown-con >.active").removeClass("active");
        $con.find(".menu-three-dropdown-con").stop().slideUp();
        menuNextSlide.hideMenuSlideNext("left-part");
    }

    return {
        showMenuLevelThreeDropdown: _showMenuLevelThreeDropdown,
        hideMenuLevelThreeDropdown: _hideMenuLevelThreeDropdown
    }

})();
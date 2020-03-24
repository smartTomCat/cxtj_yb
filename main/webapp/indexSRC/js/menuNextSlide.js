/**
 * menuNextSlide 侧拉出下一级菜单,包括侧拉的三级菜单 以及3+级菜单
 * 传入的容器是上级容器(大的容器框)
 * 如果是 二级菜单或者是 下拉式的三级菜单传过来的容器就是 left-part这个大容器
 * @author cy
 */

var menuNextSlide = (function () {
    function _showMenuSlideNext(con, menulist) {
        var $con, menuList = menulist, $menucon;
        if (con) {
            $con = $("#" + con)
        } else return;
        if (document.getElementById(con + "_n")) {
            $menucon = $("#" + con + "_n");
            $menucon.empty();
        } else {
            $menucon = $("<div id='" + con + "_n' class='menu-next-slide-con'></div>");
            $con.append($menucon);
        }
        $menucon.data(menuList);
        var items = [];
        items.push("<div>");
        for (var i = 0; i < menuList.length; i++) {
            //判断是否有子节点
            var haschild = (menuList[i].childNode && menuList[i].childNode.length != 0) ? true : false;
            //判断是否是常用菜单
            var isCommonMenu = (menuList[i].isCommonMenu=='yes')? true : false;
            items.push("<div class='menu-next-item ' id='" + menuList[i].menuId + "'data-i='" + i + "' >");
            items.push(menuList[i].menuName);
            if (haschild) {
                items.push("<span class='menu-next-control faceIcon icon-arrow_right'></span>");
            } else if(!isCommonMenu) {
                items.push("<span class='menu-item-store faceIcon icon-favorites' data-id='" + menuList[i].menuId + "'  onclick='menuCommon.addMenuCommon(this)'></span>");
            }
            items.push("</div>");

        }
        items.push("</div>");
        $menucon.append(items.join(""));
        $menucon.unbind("click").bind("click", function (e) {
            var event = e || window.event;
            var target = event.target || event.srcElement;
            if ($(target).hasClass("menu-next-control")) {
                target = target.parentNode;
            }
            $(target).addClass("active").siblings().removeClass("active");
            var menuList = $(this).data(), menuObj = menuList[$(target).attr("data-i")];
            if (menuObj && menuObj.url) {
                //打开页面并关闭本级菜单
                indexTab.addTab(menuObj.menuId, menuObj.menuName, menuObj.url, false);
                menuNextSlide.closeAllMenuSlideNext($con.attr("id"));
            }
            //如果有子节点 打开子节点,否则关闭子容器
            if (menuObj && menuObj.childNode && menuObj.childNode.length != 0) {
                menuNextSlide.showMenuSlideNext($menucon.attr("id"), menuObj.childNode);
            }else{

                menuNextSlide.hideMenuSlideNext($menucon.attr("id"));
            }
            return false;
        })
    }
   //传入的是他的上级容器,
    function _hideMenuSlideNext(con) {
        var $con;
        if (con) {
            $con = $("#" + con);
            $con.find(".menu-next-slide-con").remove();
        } else {
            return;
        }

    }
    function _closeAllMenuSlideNext() {
      $(".menu-next-slide-con").remove();
       getWebConfig("menuLevelThreeStyle")=="sliding"?$(".menu-two-item-con.active").removeClass("active"):$(".menu-three-item.active").removeClass("active");

    }

    return {
        showMenuSlideNext: _showMenuSlideNext,
        hideMenuSlideNext: _hideMenuSlideNext,
        closeAllMenuSlideNext:_closeAllMenuSlideNext
    }

})();
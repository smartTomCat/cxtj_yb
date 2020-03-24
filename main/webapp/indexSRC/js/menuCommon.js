/**
 *常用菜单menuCommon
 * @author cy
 */

var menuCommon = (function () {
    var $menucon = $("#menu-common-con");
    var $menucontrol = $("#menu-common-control");

    //给常用菜单控制条bind点击事件
    $menucontrol.unbind("click").bind("click", function () {
        if ($menucontrol.hasClass("active")) {
            $menucontrol.removeClass("active");
            _menuCommonHide();
        } else {
            $menucontrol.addClass("active");
            _menuCommonShow();
        }

    });
    //给常用菜单bind点击事件
    $menucon.bind("click",function (e) {
        var event = e || window.event;
        var target = event.target || event.srcElement;
        var $target=$(target);
       if( target.tagName.toUpperCase() == "SPAN"){
           _deleteMenuCommon($target.attr("data-menuid"));
       }else if($target.hasClass("menu-common-item")){
           var menuList=$(this).data(),menuObj=menuList[$target.attr("data-i")];
           if(menuObj && menuObj.url){
               //打开对应的iframe页面
               indexTab.addTab(menuObj.menuid, menuObj.menuname, menuObj.url, false);
           }
       }
        return false;
    });

    function _menuCommonShow() {
        $menucon.stop().slideDown("fast");
        _getMenuCommonList();
    }

    function _menuCommonHide() {
        $menucon.stop().slideUp();
    }

    function _getMenuCommonList() {
        $.ajax({
            "url": basePath + "commonMenuAction.do",
            "type": "POST",
            "dataType": "json",
            "success": function (data) {
                _loadMenuCommon(data);
            }
        });
    }

    function _loadMenuCommon(menuList) {
        //装载常用菜单数据到面板上
        $menucon.empty();
        var items = [];
        $menucon.data(menuList);
        for (var i = 0; i < menuList.length; i++) {
            var icon = menuList[i]["img"] ? " " + menuList[i]["img"].split("?")[0] : "  icon-text2";
            items.push("<div class='menu-common-item " + icon + "'  data-i='"+i+"'>");
            items.push(menuList[i].menuname);
            items.push("<span class='menu-common-delete faceIcon icon-delete' data-menuid='"+menuList[i].menuid+"'></span>");
            items.push("</div>");
        }
        $menucon.append(items.join(""));
    }

    function _addMenuCommon(target) {
        // console.log("addmenu");
        window.event ? window.event.cancelBubble = true : e.stopPropagation();
        var menuid = target.getAttribute("data-id");
        $.ajax({
            "data": "menuid=" + menuid,
            "url": "commonMenuAction!saveCommonMenu.do",
            "success": function (data) {
                _addMenuCommonSuccess(data);
            },
            "type": "POST",
            "dataType": "json"
        });
    }

    function _deleteMenuCommon(menuid) {
        $.ajax({
            "data": "menuid=" + menuid,
            "url": "commonMenuAction!deleteCommonMenu.do",
            "success": function (data) {
                _deleteMenuCommonSuccess(data);
            },
            "type": "POST",
            "dataType": "json"
        });
    }

    function _addMenuCommonSuccess() {
        _getMenuCommonList();
        Base.alert("添加成功");
    }

    function _deleteMenuCommonSuccess() {
        _getMenuCommonList();
        Base.alert("删除成功");
    }

    return {
        addMenuCommon: _addMenuCommon,
        deleteMenuCommon: _deleteMenuCommon,
        menuCommonShow: _menuCommonShow,
        menuCommonHide: _menuCommonHide,
        getMenuCommonList:_getMenuCommonList
    }
})();

/**
 * Created by Administrator on 2016/6/30.
 */
var container = new Container();//容器
var defaultInfo = {defaultPanelLabel: [
    {"id": "001", "name": "代办", "src": basePath+"indexSRC/worktable/console/daiban.html"},
    {"id": "002", "name": "系统", "src": basePath+"indexSRC/worktable/console/system.html"},
    {"id": "003", "name": "通知", "src": basePath+"indexSRC/worktable/console/tongzhi.html"},
    {"id": "004", "name": "协同管理", "src": basePath+"indexSRC/worktable/console/xietong.html"},
    {"id": "005", "name": "工作量统计", "src": basePath+"indexSRC/worktable/console/yewuliang.html"},
    {"id": "006", "name": "仪表盘", "src": basePath+"indexSRC/worktable/console/yibiaopan.html"}
],
    panelInfo: [
        {"id": "001", "name": "代办", "src": basePath+"indexSRC/worktable/console/daiban.html", "sX": 5, "sY": 4},
        {"id": "002", "name": "系统", "src": basePath+"indexSRC/worktable/console/system.html", "sX": 5, "sY": 4},
        {"id": "003", "name": "通知", "src": basePath+"indexSRC/worktable/console/tongzhi.html", "sX": 5, "sY": 4},
        {"id": "004", "name": "协同管理", "src": basePath+"indexSRC/worktable/console/xietong.html", "sX": 5, "sY": 4},
        {"id": "005", "name": "工作量统计", "src": basePath+"indexSRC/worktable/console/yewuliang.html", "sX": 5, "sY": 4},
        {"id": "006", "name": "仪表盘", "src": basePath+"indexSRC/worktable/console/yibiaopan.html", "sX": 5, "sY": 4}]
};
var elInfo = {};//元素信息
//首次打开初始化位置
function initTable(){
    //判断是不是首次登陆 first=true 如果是那么初始化给出初始化位置elInfo,否则后台读取elInfo
    $.getJSON(basePath + "sysapp/portalAction!getPortalInfo.do?t="+Math.random()+"&pageFlag=" + pageFlag, function(data){
        if (data) {
            data = eval("("+data+")");
            if (data.defaultItems||data.location) {

                //数据匹配处理,如果确定模块管理那套库表不使用，可以替换后台的domain，这一步就不需要了
                if(data.defaultItems){
                    var defaultItems = [];
                    for(var i in data.defaultItems){
                        defaultItems[i] = {
                            'id': data.defaultItems[i].moduleid,
                            'name' : data.defaultItems[i].modulename,
                            'src' : data.defaultItems[i].moduleurl,
                        };
                    }
                }
                elInfo = {
                    defaultPanelLabel:defaultItems||[],
                    panelInfo:data.location||[]
                }
            }else{
                elInfo = defaultInfo;//元素信息
                //现在默认是首次登陆
                var len=container.sizeX.length, pllen=elInfo.panelInfo.length, x=1,y=1;

                elInfo.panelInfo[0].cl=1;
                elInfo.panelInfo[0].rw=1;
                for(var i=1;i<pllen;i++){
                    if(elInfo.panelInfo[i-1].cl+5+5-1>len){
                        elInfo.panelInfo[i].cl=1;
                        elInfo.panelInfo[i].rw=y+4;
                        x=1;
                        y=y+4;
                    }
                    else {
                        elInfo.panelInfo[i].cl=x+5;
                        elInfo.panelInfo[i].rw=y;
                        x=x+5;
                    }
                }
            }
        }

        initCreatePanel();
        container.adjustAllBoxPosition();
    });
}

initTable();

var dpanel = new defaultPanelDeal();//默认panel标签处理;
var Mask_con = {
    openMask: function () {
        dpanel.initdefaultPanel();//初始化标签
        document.getElementById("mask_con").style.display = "block";
        document.body.style.overflow="hidden";
    },
    closeMask: function () {
        dpanel.closedefaultPanel();//关闭标签
        document.getElementById("mask_con").style.display = "none";
        document.body.style.overflow="auto";
    }
}//弹出框

//外部容器(布局格子)
function Container() {
    this.el = $("#drop_factory");//盒子元素
    var that = this;
    //布局盒子的格子的宽度,高度,以及位置//设定只有16格
    var wd = parseInt(this.el.css("width"));
    (wd<1005)&&(wd=1005);//限制宽度
    var gz=wd*0.0625,jx=wd*0.00390625;
    this.sizeX = [gz];
    this.sizeY = [gz];
    this.Col = [jx];
    this.Row = [jx];
    this.gz=gz;
    this.jx=jx;
    this.fs=16;
    this.WD = wd;
    this.conlist = [];//被布局元素

    //在宽度范围内创建格子;
    this.initSizeX = function () {
        for (var i = 2; i < this.fs; i++) {
            this.sizeX.push(this.gz*i+this.jx*(i-1));
        }
    }
    this.initSizeY=function(){
        for (var i = 2; i <= 50; i++) {
            this.sizeY.push(this.gz*i+this.jx*(i-1));
        }
    }

    //在宽度范围内创建列
    this.initCol = function () {
        for (var i = 2; i <this.fs; i++) {
            this.Col.push(this.jx*i + gz*(i-1));
        }

    }
    this.initRow=function(){
        for (var i = 2; i <= 50; i++) {
            this.Row.push(this.jx*i + gz*(i-1));
        }

    }
    //布局函数
    this.adjustAllBoxPosition = function () {
        var clist = this.conlist;
        for (var i = 0; i < clist.length; i++) {
            for (var j = i; j < clist.length; j++) {
                var temp;
                if (clist[i].row > clist[j].row) {
                    temp = clist[i];
                    clist[i] = clist[j];
                    clist[j] = temp;
                }
                if (clist[i].row == clist[j].row && clist[j].el.attr("lock") == "locked") {
                    temp = clist[i];
                    clist[i] = clist[j];
                    clist[j] = temp;
                }
            }
        }
        var len = clist.length;
        for (var i = 0; i < len; i++) {
            var armlist = [];
            for (var j = 0; j < i; j++) {//找出当前元素的前面的元素
                if ((clist[j].col <= clist[i].col && clist[i].col < clist[j].col + clist[j].sizeX)
                    || (clist[j].col < clist[i].col + clist[i].sizeX && clist[i].col + clist[i].sizeX < clist[j].col + clist[j].sizeX)
                    || (clist[j].col >= clist[i].col && (clist[j].col + clist[j].sizeX <= clist[i].col + clist[i].sizeX))) {
                    //如果 他们出现在当前row的范围里面那么记录下来
                    armlist.push(clist[j])
                }
            }
            //var row = clist[i].row;注释无效代码
            var row;
            if (armlist.length <= 0) {//如果上面木有盒子挡住那么移动到最上面
                row = 1;

            } else {//如果上面有挡住那么直接定位到挡住盒子的下面
                var max = armlist[0].row + armlist[0].sizeY;
                for (var m = 0; m < armlist.length; m++) {
                    if (armlist[m].row + armlist[m].sizeY > max) {
                        max = armlist[m].row + armlist[m].sizeY;
                    }
                }
                row = max;
            }

            //box移动过程中，拖拽box不参与位置变动
            if (!clist[i].el.attr("lock")) {
                //如果两次变动结果一直，不打断当前位移动作
                if (clist[i].row != row) {
                    clist[i].el.stop();
                    clist[i].row = row;
                    clist[i].el.attr("data-row", row);
                    clist[i].adjustBoxPosition();
                }

            } else {
                //拖拽box预选位置变动
                clist[i].row = row;
                clist[i].el.attr("data-row", row);
                var li = "<li class='placeholder' style='width:" + container.sizeX[clist[i].sizeX - 1] + "px;height:" + container.sizeY[clist[i].sizeY - 1] + "px;left:" + container.Col[clist[i].col - 1] + "px;top:" + container.Row[clist[i].row - 1] + "px'></li>";
                $(".placeholder").remove();
                $(".auto_con").append(li);
            }
            //释放box时，拖拽box也要加入布局中
            if (clist[i].el.attr("rlock")) {
                clist[i].row = row;
                clist[i].el.attr("data-row", row);
                clist[i].adjustBoxPosition();
                //刷新位置
                refreshPanelInfo();
            }
        }


    }
    this.init = function () {
        this.initSizeX();
        this.initSizeY();
        this.initCol();
        this.initRow();
        this.el.css({"width": this.WD - 20});//初始化盒子的宽
    }
    this.init();
}
//添加框
$("#add_con").bind("click", function () {
    Mask_con.openMask();
});
/*
 * 面板对象
 * @param
 * id:标识
 * name:面板名称(面板头上的文字)
 * src:面板显示的源
 * sX:宽度多少格
 * sY:高度多少格
 * cl:在多少列
 * rw:在多少行
 * */
function DragCon(id, name, src, sX, sY, cl, rw) {
    //el被拖动的元素,drag_bar移动控制条,resize_bar,改变大小控制条

    sX = parseInt(sX), sY = parseInt(sY), cl = parseInt(cl) , rw = parseInt(rw);

    //创建元素
    this.creatDragcon = function () {
        //modify by zzb 菜单ID页面留存相关处理
        if(src.indexOf("___businessId") > 0){
            src.replace(/(___businessId=)([\w\W\s\S]*)/,"$1"+id);
        }else{
            if(src.indexOf("?") > 0){
                src+="&___businessId="+id;
            }else{
                src +="?___businessId="+id;
            }
        }


        var el = $(" <li class='con' data-sizeX='" + sX + "' data-sizeY='" + sY + "' data-col='" + cl + "' data-row='" + rw + "'> " +
            "<div class='drag_bar'> " +
            "<span class='con_head_name'>" + name + "</span> " +
            "<span class='con_head_delete faceIcon icon-delete'></span> " +
            "<span class='con_head_refresh faceIcon icon-refresh'></span> " +
            "</div> " +
            "<div class='con_content'> " +
            "<iframe   frameborder='0' scrolling='yes' src='" + basePath + src + "'></iframe> " +
            "</div> " +
            "<span class='resize_bar'><span></span></span> " +
            "</li>");
        var sizeX_w = container.sizeX[sX - 1];
        var sizeY_h = container.sizeY[sY - 1];
        var col_l = container.Col[cl - 1];
        var row_t = container.Row[rw - 1];
        $(el).css({
            width: sizeX_w + "px",
            height: sizeY_h + "px",
            left: col_l + "px",
            top: row_t + "px"
        });
        $(".add_new_con").before(el);
        return el;
    }
    //初始化参数
    var that = this;
    this.id = id;
    this.el = this.creatDragcon();
    this.drag_bar = this.el.find(".drag_bar");
    this.resize_bar = this.el.find(".resize_bar");
    this.sizeX = sX;//所占宽度是多少
    this.sizeY = sY;//所占高度是多少
    this.col = cl;//所在多少列
    this.row = rw;//所在多少行
    this.name = name;
    //控制初始化的时候创建的panel超出container
    if (this.col + this.sizeX > container.sizeX.length) {
        this.col = container.sizeX.length - this.sizeX + 1;
        this.sizeX = Math.min(this.sizeX, container.sizeX.length);
    }

    this.refresh = this.el.find(".con_head_refresh");//刷新
    this.dt = this.el.find(".con_head_delete");//删除
    this.con_content = this.el.find("iframe");//嵌入的内容
    this.src = src;

    //初始记录鼠标的位置也用来记录鼠标上一次的位置
    this.mouse_left = 0;
    this.mouse_top = 0;

    //refresh  click事件
    this.refresh.click(function () {
        that.con_content.attr('src', basePath + src);
    });
    //删除元素
    this.deleteSelf= function () {
        //去掉当前节点
        that.el.remove();
        //后台数据删除
        delelteItem(that.id);
    }
    //delete click事件
    this.dt.click(function () {
        that.deleteSelf();
    });

    //获取主题色shadow
    function _getRgba() {
        var $c = $(".auto_con .con").css("border-top-color");
        var $rgba = $c.slice(0,3) + "a" + $c.slice(3,$c.length-1)+" ,0.3)";
        var boxshadow = "0px 0px 4px 4px "+$rgba+"";
        return boxshadow;
    }

    //给drag_bar bind mousedown事件
    this.drag_bar.bind("mousedown", function (e) {
        //阻止删除按钮触发拖动事件
        var event=e || window.event;
        if(event.target.tagName.toUpperCase()=="SPAN"){
            return;
        }
        that.mouse_left = e.clientX, that.mouse_top = e.clientY;
        //隐藏内容
        that.con_content.css("display", "none");
        //添加拖动标志
        that.el.css({"z-index": 998, "box-shadow": _getRgba()});
        that.el.attr("lock", "locked");
        //鼠标按下之后给document添加onmousemove和onmouseup事件
        $(document).bind("mousemove", mouseMoveDrag).bind("mouseup", mouseUpDrag);
    });
    //拖动改变位置
    function mouseMoveDrag(e) {
        var target = that.el;
        var dx = e.clientX - that.mouse_left;
        var dy = e.clientY - that.mouse_top;
        var l = parseInt(target.css("left"));
        var t = parseInt(target.css("top"));
        if (dx + l < 0 || dx + l + target.width() + 2 >= container.WD) {//判断左右是否超出范围
            dx = 0;
        }
        if (dy + t < 0) {//判断上面是否超出范围
            dy = 0;
        }
        var movex = l + dx;
        var movey = t + dy;
        that.mouse_left = e.clientX;
        that.mouse_top = e.clientY;
        target.css({'left': movex, 'top': movey});
        calculateBoxPosition(movex, movey);
        container.adjustAllBoxPosition();

    };
    function mouseUpDrag(e) {
        $(document).unbind("mousemove", mouseMoveDrag).unbind("mouseup", mouseUpDrag);
        //显示内容
        that.con_content.css("display", "block");
        //解锁标志
        that.el.attr("rlock", "rlocked");
        container.adjustAllBoxPosition();
        that.el.css({"z-index": 990, "box-shadow": "none"}).removeAttr("lock").removeAttr("rlock");
        //清除预选位置box
        $(".placeholder").remove();

    };
    //计算盒子应该在哪个位置
    function calculateBoxPosition(l, t) {
        that.col = (parseInt((l - container.jx) / (container.jx+container.gz))) + 1;
        ( ((l - container.jx) % (container.jx+container.gz)) > container.gz/2) && (that.col = that.col + 1);
        that.row = (parseInt((t - container.jx) / (container.jx+container.gz))) + 1;
        ( ((t - container.jx) % (container.jx+container.gz)) > container.gz/2) && (that.row = that.row + 1);
        that.el.attr({"data-row": that.row, "data-col": that.col});
    }

    //调整盒子位置
    this.adjustBoxPosition = function () {
        var col_l = container.Col[this.col - 1];
        var row_t = container.Row[this.row - 1];
        this.el.animate({
            left: col_l + "px",
            top: row_t + "px"
        }, "fast");
    }
    this.resize_bar.bind("mousedown", function (e) {
        that.mouse_left = e.clientX, that.mouse_top = e.clientY;
        //被改变元素标志出来
        //隐藏内容
        that.con_content.css("display", "none");
        that.el.css({"z-index": 998, "box-shadow": _getRgba()});
        that.el.attr("lock", "locked");
        $(document).bind("mousemove", mouseMoveResize).bind("mouseup", mouseUpResize);
        e.preventDefault();
    });
    //拖动改变大小
    function mouseMoveResize(e) {
        var target = that.el;
        var dx = e.clientX - that.mouse_left;
        var dy = e.clientY - that.mouse_top;
        var l = parseInt(target.css("left"));
        var t = parseInt(target.css("top"));
        var w = parseInt(target.css("width"));
        var h = parseInt(target.css("height"));
        if (dx + l + target.width() + 2 >= container.WD) {//判断左右是否超出范围
            dx = 0;
        }
        that.mouse_left = e.clientX;
        that.mouse_top = e.clientY;
        target.css({'width': w + dx, 'height': h + dy});
        //计算现在的宽高是在哪个sizex,sizey里面
        calculateBoxSize(w, h);
        //调整其他盒子的位置
        container.adjustAllBoxPosition();

    }

    function mouseUpResize(e) {
        $(document).unbind("mousemove", mouseMoveResize).unbind("mouseup", mouseUpResize);
        that.adjustBoxSize();
        //解锁标志
        that.el.attr("rlock", "rlocked");
        container.adjustAllBoxPosition();
        //显示内容
        that.con_content.css("display", "block");
        that.el.css({"z-index": 990, "box-shadow": "none"}).removeAttr("lock").removeAttr("rlock");
        //清除预选位置box
        $(".placeholder").remove();

    }

    //计算盒子的宽高是在哪个sizex,sizey里面
    function calculateBoxSize(w, h) {
        that.sizeX = (parseInt((w - container.gz) / (container.jx+container.gz))) + 1;
        ( ((w - container.gz) % (container.jx+container.gz)) >=container.gz/2) && (that.sizeX = that.sizeX + 1);

        that.sizeY = (parseInt((h - container.gz) / (container.jx+container.gz))) + 1;
        ( ((h - container.jx) % (container.jx+container.gz)) >= container.gz/2) && (that.sizeY = that.sizeY + 1);

        that.el.attr({"data-sizeX": that.sizeX, "data-sizeY": that.sizeY});

    }

    //调整盒子的宽度
    this.adjustBoxSize = function () {
        var sizeX_w = container.sizeX[that.sizeX - 1];
        var sizeY_h = container.sizeY[that.sizeY - 1];
        that.el.animate({
            width: sizeX_w + "px",
            height: sizeY_h + "px"
        }, 100);
    }
}

//后台传入的要显示的panel

//初始创建要显示的panel
function initCreatePanel() {
    for (var i = 0; i < elInfo.panelInfo.length; i++) {
        var dr = new DragCon(elInfo.panelInfo[i].id, elInfo.panelInfo[i].name, elInfo.panelInfo[i].src, elInfo.panelInfo[i].sX, elInfo.panelInfo[i].sY, elInfo.panelInfo[i].cl, elInfo.panelInfo[i].rw);
        container.conlist.push(dr);
    }
}


//元素的删除 传入元素的标识
function delelteItem(id) {
    var i, j, k, dtnum;
    //container.listcon容器内元素删除
    for (i = 0; i < container.conlist.length; i++) {
        if (container.conlist[i].id == id) {
            container.conlist.splice(i, 1);
            break;
        }
    }
    //位置更新
    container.adjustAllBoxPosition();
    //数据更新
    refreshPanelInfo();
}

//更新记录模板的elInfo.panelInfo对象的数据//并传入后台更新
function refreshPanelInfo() {
    elInfo.panelInfo.splice(0, elInfo.panelInfo.length);
    for (var i = 0; i < container.conlist.length; i++) {
        elInfo.panelInfo.push({
            id: container.conlist[i].id,
            "name": container.conlist[i].name,
            "src": container.conlist[i].src,
            "sX": container.conlist[i].sizeX,
            "sY": container.conlist[i].sizeY,
            "cl": container.conlist[i].col,
            "rw": container.conlist[i].row
        })
    }
    //ajax更新 data=elInfo.panelInfo
    savePanelInfo(elInfo.panelInfo);
}


//默认模板的处理//初始化显示
function defaultPanelDeal() {
    $(document).on("click", ".panel-li", function (event) {
        var e = event || window.event;
        var tg = e.target || e.srcElement;
        var $this = $(this);
        if (tg.nodeName.toUpperCase() == "LI" || tg.parentNode.nodeName.toUpperCase() == "LI") {
            var id = $this.attr("data-id"), name = $this.attr("data-name"), src = $this.attr("data-src");
            if ($this.hasClass("active")) {//如果是active状态那么就删除否则添加
                $this.removeClass("active");
                for (var i = 0; i < container.conlist.length; i++) {
                    if (container.conlist[i].id == id) {
                        container.conlist[i].deleteSelf();
                    }
                }
            }
            else {
                var eli=container.conlist[container.conlist.length-1];
                var r = eli?(eli.row+eli.sizeY):1;
                $this.addClass("active");
                var dr = new DragCon(id, name, src, 5, 4, 1,r);
                container.conlist.push(dr);
                container.adjustAllBoxPosition();
                refreshPanelInfo();
            }
        }
    })

    //初始化模板显示
    this.initdefaultPanel = function () {
        var len = elInfo.defaultPanelLabel.length, i, str = "", j;
        for (i = 0; i < len; i++) {
            var active = "";
            for (j = 0; j < elInfo.panelInfo.length; j++) {
                if (elInfo.defaultPanelLabel[i].id == elInfo.panelInfo[j].id) {
                    active = "active";
                    break;
                }
            }
            str += "<li class=\"panel-li " + active + "\" data-id='" + elInfo.defaultPanelLabel[i].id + "' data-src='" + elInfo.defaultPanelLabel[i].src + "' data-name='" + elInfo.defaultPanelLabel[i].name + "'>";
            str += "<div class=\"one-tab\" title="+elInfo.defaultPanelLabel[i].name+">" + elInfo.defaultPanelLabel[i].name + "<\/div><\/li>";
        }
        $("#panel_ul").html(str);

    }
    //关闭模板时清空默认模板显示
    this.closedefaultPanel = function () {
        $("#panel_ul").html("");
    }

}

//确认创建按钮 ensure_create
/*
 * 面板对象
 * @param
 * id:标识
 * name:面板名称(面板头上的文字)
 * src:面板显示的源
 * sX:宽度多少格
 * sY:高度多少格
 * cl:在多少列
 * rw:在多少行
 * */
$("#ensure_create").bind("click", function () {
    var name = document.getElementById("m_name").value,
        src = document.getElementById("m_src").value,
        id = new Date().getTime();

    var eli=container.conlist[container.conlist.length-1];
    var r = eli?(eli.row+eli.sizeY):1;

    var dr = new DragCon(id, name, basePath+src, 5, 4, 1, r);
    container.conlist.push(dr);
    container.adjustAllBoxPosition();
    //添加数据后台交互
    refreshPanelInfo();
});

//传入位置信息
function savePanelInfo(g_save_data){
    //记录地址时去掉basepath
    if(g_save_data){
        for(var i in g_save_data){
            g_save_data[i].src = g_save_data[i].src.replace(basePath,"")
        }
    }


    $.post(basePath + "sysapp/portalAction!savePortalInfo.do", {
        "dto['location']": $.toJSON(g_save_data),
        "dto['pageFlag']": pageFlag
    }, function(){

    });
}
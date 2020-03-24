grid.pager.js代码微调

1、147代码   //ta3Cloud.js 行数22603
var exeQueryFn;//人事人才扩展  
                 
2、26行代码 //22731
function setExeQueryFn(fn){//人事人才扩展
              exeQueryFn = fn;
        } 
                                         
3、141号代码   //22737
function load(url, param) {
        	if(exeQueryFn){//人事人才扩展
        	   exeQueryFn.call(this);
        	}else{
	        	if (typeof options.validateForm == "function") {
	        		if (!options.validateForm()) {
	        			return;
	        		}
	        	}
	        	var suburl = url ? url : options.url;
	        	var submitString = options.submitIds != undefined? options.submitIds +  "," + grid.getGridId(): grid.getGridId();
	        	if (options.successCallBack == undefined) {
	        		Base.submit(submitString, suburl, param);
	        	} else {
	        		Base.submit(submitString, suburl, param, null, null, options.successCallBack);
	        	}
        	}
        }  

4、763号代码  //23025
$.extend(this, {
            "setStatus" : setStatus,
            "setPagerUrl" : setPagerUrl,
            "clearDirty" : clearDirty,
            "setExeQueryFn":setExeQueryFn//人事人才扩展
        });
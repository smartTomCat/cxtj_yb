//密码复杂度检查
function checkPwdComplexity(pword){
	if(typeof pword == 'undefined' || pword == null || pword == "")return "密码不能为空";

	if(typeof developMode == 'undefined' || !developMode){
		if(pword.length < 6){
			return "密码的长度需要大于等于6";
		}
		if(!/\d/.test(pword) || !/[a-zA-Z_]/.test(pword)){
			return "密码必须同时包含数字与字母";
		}
	}
}
//密码长度输入时校验，适用于keypress方法
function checkPwdLengthOnkeypress(target,l){
	var value = target.value; 
	
	var selectValue = "";
    if (window.getSelection) {
        if (target.selectionStart != undefined && target.selectionEnd != undefined) {
        	selectValue = target.value.substring(target.selectionStart, target.selectionEnd);
        } 
    } else {
    	selectValue = document.selection.createRange().text;
    }
    
    if(selectValue != ""){
    	value = value.replace(selectValue,"");
    }
    
    //控制长度
	if(value.length >= l){
		return false;
	}else{
		return true;
	}
}

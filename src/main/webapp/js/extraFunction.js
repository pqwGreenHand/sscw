Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

function valueToDate(value, formatStr) {
    if(!formatStr){
        formatStr = 'yyyy-MM-dd hh:mm:ss';
    }
    if(value==null || value==0) {
        return "";
    }else{
        var d=new Date(value);
        return d.format(formatStr);
    }
}

function formatTF(value){
	if('true'==value || 1==value)
	{
		return '是';
	}else
	{
		return '否';
	}
}

function formatTFColor(value){
	if('true'==value || 1==value)
	{
		return '<font size="14" color="green"><B>是</B></font>';
	}else
	{
		return '<font size="14" color="red"><B>否</B></font>';
	}
}

function selectCheck(checkName,bool)
{
	//$(checkName).attr('checked',bool);
	if(bool){
		$(checkName).prop('checked',true);
	}else{
		$(checkName).prop('checked',false);
	}
}

//显示Dialog设置标题并mask背景
function showDialog(name,title)
{
	$(name).dialog({
		modal:true,
		title:title
	});
    $(name).dialog('open');
}


function cleanParams(queryParams)
{
	for(var p in queryParams)
	{
		queryParams[p]='';
	}
}

/*
 * 执行后台回调方法
 * */
function backFun(text)
{
	eval(text);
}

/*
 * 执行后台回调方法
 * */
function showButton(name)
{
	$('#'+name).removeClass("btnHide");
	$('#'+name).addClass("btnShow");
}

function hideButton(name)
{
	$('#'+name).addClass("btnHide");
}


function doCall()
{
	//$.messager.alert('Message', 'servletPath='+servletPath); 
	//$.messager.alert('Message', 'contextPath='+contextPath); 
	var data={pagePath:servletPath};
	var buttonPath=contextPath+"/framework/main/getButton.do";
	//$.messager.alert('Message', 'data='+data); 
	//$.messager.alert('Message', 'buttonPath='+buttonPath); 
	$.post(buttonPath,data,function(text,textStatus){
		//$.messager.alert('Message', 'text='+text);
		//$.messager.alert('Message', 'textStatus='+textStatus);
		if('success'==textStatus)
		{
			backFun(text);
		}
	});
}


function getExplorerInfo() {
	 var explorer = window.navigator.userAgent.toLowerCase() ;
	 //IE
	 if (explorer.indexOf("msie") >= 0) {
	    var ver=explorer.match(/msie ([\d.]+)/)[1];
	    return {type:"IE",version:ver};
	 }
	 //firefox
	 else if (explorer.indexOf("firefox") >= 0) {
	    var ver=explorer.match(/firefox\/([\d.]+)/)[1];
	    return {type:"Firefox",version:ver};
	 }
	 //Chrome
	 else if(explorer.indexOf("chrome") >= 0){
	    var ver=explorer.match(/chrome\/([\d.]+)/)[1];
	    return {type:"Chrome",version:ver};
	 }
	 //Opera
	 else if(explorer.indexOf("opera") >= 0){
		var ver=explorer.match(/opera.([\d.]+)/)[1];
		return {type:"Opera",version:ver};
	 }
	 //Safari
	 else if(explorer.indexOf("Safari") >= 0){
		var ver=explorer.match(/version\/([\d.]+)/)[1];
		return {type:"Safari",version:ver};
	 }else{
		return {type:"Unknown",version:"Unknown"};
	 }
	 // alert("explore: "+getExplorerInfo().type+" version: "+getExplorerInfo().version);
}


function checkForm(form_)
{
	var flag=$(form_).form('enableValidation').form('validate');
	if (!flag)
	{
		$.messager.alert('检查警告', '请检查有问题的数据！','info',function(){
			$(form_).form('enableValidation').form('validate');
		}); 
		return false;
	}else{
		return true;
	}

}

$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
      validator: function (value, param) {
        return /^[\u0391-\uFFE5]+$/.test(value);
      },
      message: '请输入汉字'
    },
    english : {// 验证英语
          validator : function(value) {
              return /^[A-Za-z]+$/i.test(value);
          },
          message : '请输入英文'
      },
      ip : {// 验证IP地址
          validator : function(value) {
              return /\d+\.\d+\.\d+\.\d+/.test(value);
          },
          message : 'IP地址格式不正确'
      },
    ZIP: {
      validator: function (value, param) {
        return /^[0-9]\d{5}$/.test(value);
      },
      message: '邮政编码不存在'
    },
    QQ: {
      validator: function (value, param) {
        return /^[1-9]\d{4,10}$/.test(value);
      },
      message: 'QQ号码不正确'
    },
    mobile: {
      validator: function (value, param) {
        return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
      },
      message: '手机号码不正确'
    },
    tel:{
      validator:function(value,param){
        return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value);
      },
      message:'电话号码不正确'
    },
    mobileAndTel: {
      validator: function (value, param) {
        return /(^([0\+]\d{2,3})\d{3,4}\-\d{3,8}$)|(^([0\+]\d{2,3})\d{3,4}\d{3,8}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/.test(value);
      },
      message: '请正确输入电话号码'
    },
    number: {
      validator: function (value, param) {
        return /^[0-9]+.?[0-9]*$/.test(value);
      },
      message: '请输入数字'
    },
    money:{
      validator: function (value, param) {
       	return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
       },
       message:'请输入正确的金额'

    },
    mone:{
      validator: function (value, param) {
       	return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
       },
       message:'请输入整数或小数'

    },
    integer:{
      validator:function(value,param){
        return /^[+]?[1-9]\d*$/.test(value);
      },
      message: '请输入最小为1的整数'
    },
    integ:{
      validator:function(value,param){
        return /^[+]?[0-9]\d*$/.test(value);
      },
      message: '请输入整数'
    },
    minrange:{
        validator:function(value,param){
          if(/^[1-9]\d*$/.test(value)){
            return value >= param[0]
          }else{
            return false;
          }
        },
        message:'输入的数字必须大于{0}'
      },
    range:{
      validator:function(value,param){
        if(/^[1-9]\d*$/.test(value)){
          return value >= param[0] && value <= param[1]
        }else{
          return false;
        }
      },
      message:'输入的数字在{0}到{1}之间'
    },
    minLength:{
      validator:function(value,param){
        return value.length >=param[0]
      },
      message:'至少输入{0}个字'
    },
    maxLength:{
      validator:function(value,param){
        return value.length<=param[0]
      },
      message:'最多{0}个字'
    },
    //select即选择框的验证
    selectValid:{
      validator:function(value,param){
        if(value == param[0]){
          return false;
        }else{
          return true ;
        }
      },
      message:'请选择'
    },
    idCode:{
      validator:function(value,param){
        return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
      },
      message: '请输入正确的身份证号'
    },
    loginName: {
      validator: function (value, param) {
        return /^[\u0391-\uFFE5\w]+$/.test(value);
      },
      message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    equalTo: {
      validator: function (value, param) {
        return value == $(param[0]).val();
      },
      message: '两次输入的字符不一至'
    },
    englishOrNum : {// 只能输入英文和数字
          validator : function(value) {
              return /^[a-zA-Z0-9_ ]{1,}$/.test(value);
          },
          message : '请输入英文、数字、下划线或者空格'
      },
     xiaoshu:{ 
        validator : function(value){ 
        return /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
        }, 
        message : '最多保留两位小数！'    
    	},
    ddPrice:{
    validator:function(value,param){
      if(/^[1-9]\d*$/.test(value)){
        return value >= param[0] && value <= param[1];
      }else{
        return false;
      }
    },
    message:'请输入1到100之间正整数'
  },
  jretailUpperLimit:{
    validator:function(value,param){
      if(/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)){
        return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
      }else{
        return false;
      }
    },
    message:'请输入0到100之间的最多俩位小数的数字'
  },
  rateCheck:{
    validator:function(value,param){
      if(/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)){
        return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
      }else{
        return false;
      }
    },
    message:'请输入0到1000之间的最多俩位小数的数字'
  }
  });

function hiddendiv(id)
{
	$(id).hide();
}


function showdiv(id)
{
	$(id).show();
}


function comboxclean(id)
{
	$(id).combo("setValue","").combo("setText","");  
}
//公用code combobox
function codeCombobox(id,type){
	$('#'+id).combobox({
	    url:contextPath+"/combobox/listCodeByType.do?type="+type,
	    valueField:'codeKey',
	    textField:'codeValue'
	    	,
        filter: function(q, row){
        	console.info(q);
            var opts = $(this).combobox('options');
            //return row[opts.textField].indexOf(q) == 0;
            return row[opts.codeValue].indexOf(q)>-1;//将从头位置匹配改为任意匹配
        }

	});
}

function codeCombo(id,type,selectedId){
    $('#'+id).combobox({ 
    	url:contextPath+"/zhfz/common/code/listCodeByType.do?type="+type,
        valueField:'codeKey',    
        textField:'codeValue',
		editable: false,
        filter: function(q, row){ 
            var opts = $(this).combobox('options'); 
            //return row[opts.textField].indexOf(q) == 0; 
            return row.codeValue.indexOf(q)>-1;//将从头位置匹配改为任意匹配 
        },
        onLoadSuccess : function() {
        	if(selectedId){
        		//延迟一下，对于某些浏览器能set成功
        		setTimeout(function(){
        			$('#'+id).combobox('setValue', selectedId);
				},10);
        	}
        	
        }   
    });
}

function messagershow(){
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'加载中...'
	});	
}
/*
 * 身份证最后一位计算,仅页面使用
 * 例:calc('31010419990911001')
 * */
function calc(str)
{

	var coeff = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1];
	var suffix = ['1','0','x','9','8','7','6','5','4','3','2'];
	var sum = 0;
	for(var i=0;i<17;i++)
		sum += coeff[i] * parseInt(str.charCodeAt(i)-48);
	sum %= 11;
	str = str.substr(0,17) + suffix[sum];
	return str;
	//console.info(str);
}
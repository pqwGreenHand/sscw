function getBrowser(){
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    var ver;
    (s = ua.match(/edge\/([\d.]+)/)) ? Sys.edge = s[1] :
        (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
            (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
                (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
                    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                            (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    if (Sys.edge) return 1;
    if (Sys.ie) return 0;
    if (Sys.firefox) return 1;
    if (Sys.chrome){ ver = Sys.chrome;ver.toLowerCase();var arr = ver.split('.');if(parseInt(arr[0])>43){return 1;}else{return 0;}}
    if (Sys.opera) return 1;
    if (Sys.safari) return 1;
    return 1;
}

function ShowPage(root,path)
{
    var pre = "WebOffice://|Officectrl|";//智能窗打开的固定参数
    var v=getBrowser();
    if(v==1){//当浏览器返回值为1时定义为使用智能窗方式打开网址
        strUrl = pre + root + path;
        window.open(strUrl,'_self');
    }
    else
    { //当浏览器返回值1以外的数据时采用传统方式打开网址
        strUrl = root + path;
        window.open(strUrl,'_blank');
    }
}

function readDoc(id)
{
    var strPath='read.jsp?id=' + id;
    var strRoot = decodeURI(window.location.href).substring(0,window.location.href.lastIndexOf('/')+1);
    alert(strRoot);
    ShowPage(strRoot,strPath);
}

$(function(){
	$('#certificategrid').datagrid({
		iconCls:'icon-datagrid',
		region: 'center',  
		width:'100%',
		height:'100%',
		fitColumns:true,
		nowrap: false,
		striped: true,
		collapsible:false,
		loadMsg: '加载中.....',
		method: 'get',
		url: 'list.do',
		queryParams : {
			trefresh:new Date().getTime()
		},
		sortName: 'sortNo',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'name',title:'告知书名称',width:80},
		    {field:'content',title:'word内容',width:300},
			{field:'sortNo',title:'访问热度',width:60},
			{field:'createdTime',title:'创建时间',width:80,formatter:function (value,row,index) {
					return formatter(new Date(value));
                }},
			{field:'id',title:'操作',width:150,
		    	formatter:function(value,row,index){
		    		var u='',d='',l='',e='';
					// if(isGridButton("rightsTemplateLook")){
					// 	l ='<span class="spanRow"><a href="#" class="gridlook" onclick="rightsTemplateLook('+value+')">预览</a></span>';
					// }
                    if(isGridButton("rightsTemplateDownload")){
                        u ='<span class="spanRow"><a href="load.do?id='+value+'" target="_blank" class="gridedit">下载</a></span>';
                    }
                    if(isGridButton("rightsTemplateEdit")){
                        e ='<span class="spanRow"><a href="#" class="gridedit" onclick="rightsTemplateEdit('+index+')">编辑</a></span>';
                    }
					if(isGridButton("rightsTemplateRemove")){
						d ='<span class="spanRow"><a href="#" class="griddel" onclick="rightsTemplateRemove('+value+')">删除</a></span>';
					}
		    		return l+e+u+d;
		    	}}
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#certificateToolbar'
	});
	var p = $('#certificategrid').datagrid('getPager');

	$(p).pagination({
		onBeforeRefresh:function(){
			//alert('before refresh');
			$('#certificategrid').datagrid('reload',{
				name : $('#q_name').val(),
				trefresh:new Date().getTime()});
			return false;
		}
	});
	$('#fudong').remove();
});

function saveEnterprise(){
	console.log('rightsTemplateAdd:submit')
	if(!checkForm('#certificate_form')){
		return;
	}
    var jsonForm= new FormData();;
    jsonForm.append('sortNo',$("#sort-no").val());
    jsonForm.append('id',$("#id").val());
    var file = $("#word-file")[0].files[0];
    if(file)
        jsonForm.append('wordFile',file);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	    });
	jQuery.ajax({
		type : 'POST',
		url : url,
		data : jsonForm,
		dataType : 'json',
        cache:false,
        traditional: true,
        contentType: false,
        processData: false,
		success : function(data){
			$.messager.progress('close');
            $.messager.show({
                title:'提示',
                msg:data.content,
                timeout:3000
            });
			$('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
			$('#certificate_dialog').dialog('close');
		},
		error : function(data){
			$.messager.progress('close');
			//exception in java
			$.messager.alert('错误', '未知错误!');
		}
	});  
}

function rightsTemplateAdd(){
	showDialog('#certificate_dialog','新增告知书');
	$('#certificate_form').form('clear');  
	url = 'add.do';
}
function rightsTemplateEdit(index){
	showDialog('#certificate_dialog','编辑告知书');
    var row = $('#certificategrid').datagrid('getRows')[index];
    $('#id').val(row.id);
    $('#sort-no').val(row.sortNo);
    $('#word-name').val(row.name);
	url = 'edit.do';
}

function rightsTemplateLook(value){
    readDoc(value);
}

function rightsTemplateRemove(value){
	$.messager.confirm('删除确认','是否确定删除此数据？',function(r){  
        if (r){
		$.messager.progress({
	   	 title:'请等待',
	   	 msg:'删除数据中...'
	    });
		jQuery.ajax({
			type : 'POST',
			url : 'remove.do',
			data : {id:value},
			dataType : 'json',
			success : function(data){
                $.messager.show({
                    title:'提示',
                    msg:data.content,
                    timeout:3000
                });
				$('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
				$.messager.progress('close');
			},
			error : function(data){
				//exception in java
				$.messager.progress('close');
				$.messager.alert('错误', '未知错误!');
			}
		});
        }});
	}

function doSearch() {
	$('#certificategrid').datagrid('load', {
        name : $('#q_name').val(),
		trefresh:new Date().getTime()
	});
}

function doClear() {
	$('#q_name').prop('value', '');
}

function doSelectWord() {
	$('#word-file').click();
}

function doWordChange(_this) {
	$('#word-name').val($(_this).val());
}
var url;
var type = {
    '肤色':'skin',
    '性别':'sex',
    '体型':'shape',
    '脸型':'faceStyle',
    '眼皮':'eyeStyle',
    '发长':'hairLength',
    '发色':'hairColor',
    '衣着':'clothing',
    '包扎':'bindUp',
}
var code = {
    }
function init(){
    $('#save-serial').combogrid({
        url:'../combobox/listSerial.do',
        panelWidth:223,
        idField:'id',
        textField:'value',
        columns:[[
            {field:'value',title:'涉案人员',width:100},
            {field:'groupName',title:'案件编号',width:120},
        ]],
        onSelect: function (record) {
            var data = $('#save-serial').combogrid('grid').datagrid('getSelected');
            $('#save-police-ask,#save-police-record').combobox('reload','../combobox/listSerialPolice.do?caseId=' + (parseParam(data.id)['caseId'] || 0));
        }
    });
    $('#query-person-name').combobox({
        url:'../combobox/listSerial.do',
        valueField:'id',
        textField:'value',
    });
    jQuery.ajax({
        type: 'GET',
        url: 'selectFlagAll.do',
        dataType: 'json',
        async:false,
        success: function (data) {
            var t;
            $.each(data,function (index,obj) {
                t = type[obj.type];
                if(typeof code[t] == 'undefined')
                    code[t] = [];
                code[t].push(obj);
            });
            console.log(code);
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}
init();

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
		sortName: 'id',
		sortOrder: 'asc',
		remoteSort: false,
		idField:'id',
		singleSelect:true,
		frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
		]],
		columns:[[
            {field:'name',title:'姓名',width:80},
		    {field:'sex',title:'性别',width:80,formatter:function (value) { return {'0':'未知性别','1':'男','2':'女','9':'未说明性别'}[value]; }},
			{field:'age',title:'年龄',width:80},
			{field:'certificateNo',title:'证件号码',width:120},
			{field:'photoName',title:'图片',width:120,formatter:function (value) {
			    return '<img class="jzw-photo" onmouseover="onBigImage(event,this)" onmouseout="mvBigImage()" src="'+ fileUtils.originalUrl('br',0) + '/BRDownloadPhoto/' + value +'" />'
                }},
		]],
		pagination:true,
		pageList: [10,20,30,40,50,100],
		rownumbers:true,
		toolbar:'#certificateToolbar',
        onClickRow : function (index,row) {
            console.log(row);
            $('#search').form('clear').form('load',row);
        }
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
	if(!checkForm('#save-form')){
		return;
	}
    var jsonForm = {};
	jsonForm = $('#save-form').serializeObject(jsonForm);
    jsonForm = parseParam(jsonForm.serial,jsonForm);
	$.messager.progress({
	   	 title:'请等待',
	   	 msg:'添加/修改数据中...'
	});
    jQuery.ajax({
        type: 'POST',
        url: url,
        data: jsonForm,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#certificategrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
            $('#certificate-dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function selectImgs() {
    $('#logoFile').target('click')
}


function setImgs(_this) {
	var obj = $(_this);
	var f = obj.val();
    if(f == null || f ==undefined || f == ''){
        return false;
    }else if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f))
    {
        alert("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
        $(obj).val('');
        return false;
    }else{
        var files = _this.files;
        var formData = new FormData();
        for(var i = 0; i < files.length; i++){
        	formData.append('files',files[i],files[i].name);
        }
        $.messager.progress({
            title: '请等待',
            msg: '添加数据中...'
        });
        jQuery.ajax({
            type: 'POST',
            url: 'imgAdd.do',
            data: formData,
            contentType: false, // 注意这里应设为false
            processData: false,
            cache: false,
            dataType: 'json',
            success: function (data) {
                $.messager.show({
                    title: '提示',
                    msg: data.content,
                    timeout: 3000
                });
                $('#certificategrid').datagrid('reload', {trefresh: new Date().getTime()});// reload the data
                $.messager.progress('close');
            },
            error: function (data) {
                //exception in java
                $.messager.progress('close');
                $.messager.alert('错误', '未知错误!');
            }
        });

	}
}

function add(){
	showDialog('#certificate_dialog','新增医疗救助');
	url = 'add.do'
	$('#save-form').form('clear');
}


function doSearch() {
    var param = {trefresh:new Date().getTime()};
    param = $('#search').serializeObject(param);
	$('#certificategrid').datagrid('load', param);
}

function doClear() {
	$('#search').form('clear');
}

function doSave() {
    $.messager.progress({
        title: '请等待',
        msg: '修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: 'edit.do',
        data: $('#search').serializeObject(),
        dataType: 'json',
        success: function (data) {
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#certificategrid').datagrid('reload', {trefresh: new Date().getTime()});// reload the data
            $.messager.progress('close');
        },
        error: function (data) {
            //exception in java
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!');
        }
    });
}

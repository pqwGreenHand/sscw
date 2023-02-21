var _answerParam = {trefresh: '', questionId: 0};

/**
 * left
 */
$(function () {
    $('#left-certificategrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载中.....',
        method: 'get',
        url: 'question/list.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortName: 'sortNo',
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'typeName', title: '笔录问题类型', width: 80},
            {field: 'content', title: '内容', width: 300},
            {field: 'sortNo', title: '访问热度', width: 60},
            {field: 'isMajor', title: '是否重要', width: 80,formatter:function (value, row, index) {
                    return ['是','否'][value];
                }},
            {
                field: 'id', title: '操作', width: 150,
                formatter: function (value, row, index) {
                    var e = '';
                    var d = '';
                    if (isGridButton("questionEdit")) {
                        e = '<span class="spanRow"><a href="#" class="gridedit" onclick="doEdit(0,' + index + ')">编辑</a></span>';
                    }
                    if (isGridButton("questionRemove")) {
                        d = '<span class="spanRow"><a href="#" class="griddel" onclick="doRemove(' + value + ',\'question/remove.do\',\'#left-certificategrid\',true)">删除</a></span>';
                    }
                    return e + d;
                }
            }
        ]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#left-certificateToolbar',
        onClickRow: function (rowIndex, rowData) {
            $('#right-search .reset').click();
            _answerParam.questionId = rowData.id;
            $('#answer-search-btn').click();
        }
    });
    var p = $('#left-certificategrid').datagrid('getPager');

    $(p).pagination({
        onBeforeRefresh: function () {
            //alert('before refresh');
            $('#left-certificategrid').datagrid('reload', {
                name: $('#q_name').val(),
                trefresh: new Date().getTime()
            });
            return false;
        }
    });
    $('#fudong').remove();
});

/**
 * right
 */
$(function () {
    $('#right-certificategrid').datagrid({
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '加载中.....',
        method: 'get',
        url: 'answer/list.do',
        queryParams: {
            questionId: 0,
            trefresh: new Date().getTime()
        },
        sortName: 'sortNo',
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'content', title: '内容', width: 300},
            {field: 'sortNo', title: '访问热度', width: 60},
            {
                field: 'id', title: '操作', width: 150,
                formatter: function (value, row, index) {
                    var e = '';
                    var d = '';
                    if (isGridButton("answerEdit")) {
                        e = '<span class="spanRow"><a href="#" class="gridedit" onclick="doEdit(1,' + index + ')">编辑</a></span>';
                    }
                    if (isGridButton("answerRemove")) {
                        d = '<span class="spanRow"><a href="#" class="griddel" onclick="doRemove(' + value + ',\'answer/remove.do\',\'#right-certificategrid\')">删除</a></span>';
                    }
                    return e + d;
                }
            }
        ]],
        pagination: true,
        pageList: [10, 20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#right-certificateToolbar'
    });
    var p = $('#right-certificategrid').datagrid('getPager');

    $(p).pagination({
        onBeforeRefresh: function () {
            //alert('before refresh');
            $('#right-certificategrid').datagrid('reload', {trefresh: new Date().getTime()});
            return false;
        }
    });
    // $('#fudong').remove();
});

function saveEnterprise(_this) {
    console.log('save:submit')
    var jsonForm, formClazz, panelClazz, param;
    _this = $(_this);
    if (_this.data('type')) { //答案
        formClazz = '#right-certificate-form';
        panelClazz = '#right-certificategrid';
        _answerParam.trefresh = new Date().getTime();
        jsonForm = $(formClazz).serializeObject(_answerParam);
        param = _answerParam
    } else {		//问题
        formClazz = '#left-certificate-form';
        panelClazz = '#left-certificategrid';
        param = {trefresh: new Date().getTime()};
        jsonForm = $(formClazz).serializeObject(param);
    }
    if (!checkForm(formClazz)) {
        return;
    }
    console.log(jsonForm);
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        url: _this.data('url'),
        contentType: 'application/json',
        data: JSON.stringify(jsonForm),
        dataType: 'json',
        cache: false,
        traditional: true,
        processData: false,
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $(panelClazz).datagrid('reload', param);// reload the data
            $('#certificate-dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            //exception in java
            $.messager.alert('错误', '未知错误!');
        }
    });
}

function doAdd(title, url, form, type) {
    if (type && _answerParam.questionId == 0) {
        $.messager.show({
            title: '提示',
            msg: '请先选择问题',
            timeout: 3000
        });
        return null;
    }

    showDialog('#certificate-dialog', title);
    $('#certificate-dialog form').hide();
    $(form).show().form('clear');
    $('#save-btn').data('type', type).data('url', url);
}

function doRemove(value, url, clazz,type) {
    $.messager.confirm('删除确认', '是否确定删除此数据？', function (r) {
        if (r) {
            _answerParam.trefresh = new Date().getTime();
            $.messager.progress({
                title: '请等待',
                msg: '删除数据中...'
            });
            jQuery.ajax({
                type: 'POST',
                url: url,
                data: {id: value},
                dataType: 'json',
                success: function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.content,
                        timeout: 3000
                    });
                    if(type){
                        $('#right-certificategrid').datagrid('reload', _answerParam);
                    }
                    $(clazz).datagrid('reload', _answerParam);// reload the data
                    $.messager.progress('close');
                },
                error: function (data) {
                    //exception in java
                    $.messager.progress('close');
                    $.messager.alert('错误', '未知错误!');
                }
            });
        }
    });
}


function doEdit(type,index){
    var title,form,panel,url;
    if(type){
        title = '修改答案';
        form = '#right-certificate-form';
        panel = '#right-certificategrid';
        url = 'answer/edit.do'
    }else{
        title = '修改问题';
        form = '#left-certificate-form';
        panel = '#left-certificategrid';
        url = 'question/edit.do'
    }
    showDialog('#certificate-dialog', title);
    $('#certificate-dialog form').hide();
    $('#save-btn').data('type', type).data('url', url);
    var row = $(panel).datagrid('getRows')[index];
    $(form).show().form('clear').form('load',row);
}

function doSearch(panel, form) {
    _answerParam.trefresh = new Date().getTime();
    $(panel).datagrid('load', $(form).serializeObject(_answerParam));
}

function doClear(form) {
    $(form).form('clear');
}

$(function () {
    $('#crimeDefineGrid').datagrid({
        //title:'通用配置',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: '数据加载中...',
        method: 'get',
        url: 'list.do',
        sortName: 'id',
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[
            {field: 'ck', checkbox: true},
            {title: 'ID', field: 'id', width: 20, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'codeClass', title: '案件类型简称', width: 50},
            {field: 'codeClassDesc', title: '案件类型', width: 50},
            {field: 'code', title: '案件性质编号', width: 60},
            {field: 'codeDesc', title: '案件性质', width: 80},
            {field: 'spellCode', title: '案件性质简称', width: 50},
            {field: 'sortNo', title: '访问热度', width: 40},
            {
                field: 'operation', title: '操作', width: 80,
                formatter: function (value, row, index) {
                    var e = '';
                    var d = '';
                    if (isGridButton("crimeDefineEdit")) {
                        e = '<span class="spanRow"><a href="#" class="gridedit" onclick="crimeDefineEdit(' + index + ')">修改</a></span>';
                    }
                    if (isGridButton("crimeDefineRemove")) {
                        d = '<span class="spanRow"><a href="#" class="griddel" onclick="crimeDefineRemove(' + row.id + ')">删除</a></span>';
                    }
                    return e + d;
                }
            }
        ]],
        pagination: true,
        pageList: [20, 30, 40, 50, 100],
        pageSize:20,
        rownumbers: true,
        //行选择方法，进行条件触发
        onSelect: function (rowIndex, rowData) {
            subCrimeTypeGridLoad(rowData);
        },
        toolbar: '#crimeDefineToolbar'


    });
    var p = $('#crimeDefineGrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
            //alert('before refresh');
        }
    });


    var p1 = $('#subCrimeTypeGrid').datagrid('getPager');
    $(p1).pagination({
        onBeforeRefresh: function () {

        }
    });
    loadCrimeType();
    $('#fudong').remove();
});

function loadCrimeType() {
    $('#crimeType_cmb').combobox({
        url: '../crimeType/comboCrimeType.do',
        valueField: 'id',
        textField: 'value',
        onLoadSuccess: function (data) {

        },
        onChange: function (newValue, oldValue) {

        },
        filter: function (q, row) {
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;
        }
    });
}

function subCrimeTypeGridLoad(rowData) {
    $('#subCrimeTypeGrid').datagrid('load',
        {
            crimeTypeId: rowData.id,
            name: $('#s_subcrimetype_name').textbox('getValue'),
            trefresh: new Date().getTime()
        });
}

function crimeDefineAdd() {
    var erow = $('#crimeDefineGrid').datagrid('getSelected');
    if (erow) {
        showDialog('#sub_crime_type_dialog', '新增犯罪子类型');
        url = '../crimedefine/add.do';
        $('#subCrimeType_form').form('clear');
        $('#crimeType_cmb').combobox('setValue', erow.id);
        $('#subCrimetypeName').focus();
    } else {
        $.messager.alert('提示', '请先选择一个犯罪类型!');
    }
}

function subCrimeTypeEdit(index) {
    var row = $('#subCrimeTypeGrid').datagrid('getRows')[index];
    showDialog('#sub_crime_type_dialog', '编辑犯罪子类型');
    $('#subCrimeType_form').form('clear');
    $('#subCrimeType_form').form('load', row);
    url = '../crimedefine/update.do';
    $('#subCrimetypeName').focus();
}

function saveSubCrimeType() {
    if (!checkForm('#subCrimeType_form')) {
        return;
    }
    var orgForm = $('#subCrimeType_form');
    var organizationinfo = JSON.stringify(orgForm.serializeObject());
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: organizationinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#subCrimeTypeGrid').datagrid('reload');// reload the data
            $('#sub_crime_type_dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!' + data.content);
        }
    });
}

function saveCrimeDefine() {
    if (!checkForm('#crime_define_form')) {
        return;
    }

    var entForm = $('#crime_define_form');
    var enterpriseinfo = JSON.stringify(entForm.serializeObject());
    $.messager.progress({
        title: '请等待',
        msg: '添加/修改数据中...'
    });
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: enterpriseinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.progress('close');
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#crimeDefineGrid').datagrid('reload');// reload the data
            $('#crime_define_dialog').dialog('close');
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '未知错误!' + data.content);
        }
    });
}


function subCrimeTypeRemove(value) {
    $.messager.confirm('提示', '确定删除此犯罪子类型？', function (r) {
        if (r) {
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: '../crimedefine/remove.do',
                data: '{"id":' + value + '}',
                dataType: 'json',
                success: function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.content,
                        timeout: 3000
                    });
                    $('#subCrimeTypeGrid').datagrid('reload');// reload the data
                },
                error: function (data) {
                    $.messager.alert('错误', '未知错误!' + data.content);
                }
            });

        }
    });
}


function crimeDefineAdd() {
    showDialog('#crime_define_dialog', '新增犯罪类型');
    $('#crime_define_form').form('clear');
    url = 'add.do';
}

function crimeDefineEdit(index) {
    var row = $('#crimeDefineGrid').datagrid('getRows')[index];
    showDialog('#crime_define_dialog', '编辑犯罪类型');
    $('#crime_define_form').form('clear');
    $('#crime_define_form').form('load', row);
    url = 'update.do?id=' + row.id;
}

function crimeDefineRemove(value) {
    $.messager.confirm('提示', '您确定删除此犯罪类型？', function (r) {
        if (r) {
            $.messager.progress({
                title: '请等待',
                msg: '删除数据中...'
            });
            jQuery.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: 'remove.do',
                data: '{"id":' + value + '}',
                dataType: 'json',
                success: function (data) {
                    $.messager.show({
                        title: '提示',
                        msg: data.content,
                        timeout: 3000
                    });

                    $('#crimeDefineGrid').datagrid('reload');// reload the data
                    $.messager.progress('close');
                },
                error: function (data) {
                    $.messager.alert('错误', '未知错误!' + data.content);
                }
            });
        }
    });
}


function docrimeDefineSearch() {
    $('#crimeDefineGrid').datagrid('load', {
        codeClassDesc: $('#s_codeClassDesc_cmb').combobox('getValue'),
        codeDesc: $('#s_codeDesc_name').textbox('getValue'),
        trefresh: new Date().getTime()
    });
    $('#crimeDefineGrid').datagrid('clearChecked');
}

function docrimeDefineClear() {
    $('#s_codeClassDesc_cmb').combobox('setValue', '');
    $('#s_codeDesc_name').textbox('setValue', '');
}

function dosubCrimeTypeSearch() {
    var row = $('#crimeDefineGrid').datagrid('getSelected');
    var crimeTypeId;
    if (row) {
        crimeTypeId = row.id;
    }
    $('#subCrimeTypeGrid').datagrid('load', {
        crimeTypeId: crimeTypeId,
        name: $('#s_subcrimetype_name').textbox('getValue'),
        trefresh: new Date().getTime()
    });
}

function dosubCrimeTypeClear() {
    $('#s_subcrimetype_name').textbox('setValue');
}



$(function () {
    //角色表格查询
    $('#role_datagrid').datagrid({
        //title:'角色',
        iconCls: 'icon-datagrid',
        region: 'center',
        width: '100%',
        height: '100%',
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: false,
        loadMsg: 'Loading...',
        method: 'get',
        url: 'getRoles.do',
        queryParams: {
            trefresh: new Date().getTime()
        },
        sortName: 'id',
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'id',
        singleSelect: true,
        frozenColumns: [[
            {field: 'ck', checkbox: true},
            {title: 'ID', field: 'id', width: 80, sortable: true, hidden: true}
        ]],
        columns: [[
            {field: 'name', title: '角色名', width: 100},
            {field: 'DataAuth', title: '数据权限', width: 250,formatter: function (value, row, index) {
                var dataAuth = "";
                    if (row.bacsDataAuth == 0) {
                        dataAuth += '<span  >办案场所数据权限：本人</span><br/>';
                    } else if (row.bacsDataAuth == 1) {
                        dataAuth += '<span  >办案场所数据权限：本办案场所</span><br/>';
                    } else if (row.bacsDataAuth == 2) {
                        dataAuth += '<span  >办案场所数据权限：本办案场所及下级办案场所</span><br/>';
                    } else if (row.bacsDataAuth == 3) {
                        dataAuth += '<span  >办案场所数据权限：上级办案场所及其下级办案场所</span><br/>';
                    } else if (row.bacsDataAuth == 5) {
                        dataAuth += '<span  >办案场所数据权限：本部门</span><br/>';
                    } else if (row.bacsDataAuth == 6) {
                        dataAuth += '<span  >办案场所数据权限：本部门及下级部门</span><br/>';
                    } else if (row.bacsDataAuth == 7) {
                        dataAuth += '<span  >办案场所数据权限：上级部门及其下级部门</span><br/>';
                    } else if (row.bacsDataAuth == 4){//4
                        dataAuth +=  '<span  >办案场所数据权限：全部</span><br/>';
                    }else if(row.jzglDataAuth == -1){
                        dataAuth +=  '<span  >办案场所数据权限：无权限</span><br/>';
                    }

                    if(row.sacwDataAuth == 0){
                        dataAuth += '<span  >涉案财物数据权限：本人</span><br/>';
                    }else if(row.sacwDataAuth == 1){
                        dataAuth += '<span  >涉案财物数据权限：本涉案财物场所</span><br/>';
                    }else if(row.sacwDataAuth == 2){
                        dataAuth += '<span  >涉案财物数据权限：本涉案财物场所及下级涉案财物场所</span><br/>';
                    }else if(row.sacwDataAuth == 3){
                        dataAuth += '<span  >涉案财物数据权限：上级涉案财物场所及其下级涉案财物场所</span><br/>';
                    }else if(row.sacwDataAuth ==5){
                        dataAuth += '<span  >涉案财物数据权限：本部门</span><br/>';
                    }else if(row.sacwDataAuth == 6){
                        dataAuth += '<span  >涉案财物数据权限：本部门及下级部门</span><br/>';
                    }else if(row.sacwDataAuth == 7){
                        dataAuth += '<span  >涉案财物数据权限：上级部门及其下级部门</span><br/>';
                    }else if(row.jzglDataAuth == 4){
                        dataAuth +=  '<span  >案财物数据权限：全部</span><br/>';
                    }else if(row.jzglDataAuth == -1){
                        dataAuth +=  '<span  >案财物数据权限：无权限</span><br/>';
                    }

                    if(row.jzglDataAuth == 0){
                        dataAuth += '<span  >卷管中心数据权限：本人</span><br/>';
                    }else if(row.jzglDataAuth == 1){
                        dataAuth += '<span  >卷管中心数据权限：本卷管中心</span><br/>';
                    }else if(row.jzglDataAuth == 2){
                        dataAuth += '<span  >卷管中心数据权限：本卷管中心及下级卷管中心</span><br/>';
                    }else if(row.jzglDataAuth == 3){
                        dataAuth += '<span  >卷管中心数据权限：上级卷管中心及其下级卷管中心</span><br/>';
                    }else if(row.jzglDataAuth ==5){
                        dataAuth += '<span  >卷管中心数据权限：本部门</span><br/>';
                    }else if(row.jzglDataAuth == 6){
                        dataAuth += '<span  >卷管中心数据权限：本部门及下级部门</span><br/>';
                    }else if(row.jzglDataAuth == 7){
                        dataAuth += '<span  >卷管中心数据权限：上级部门及其下级部门</span><br/>';
                    }else if(row.jzglDataAuth == 4){
                        dataAuth +=  '<span  >卷管中心数据权限：全部</span><br/>';
                    }else if(row.jzglDataAuth == -1){
                        dataAuth +=  '<span  >卷管中心数据权限：无权限</span><br/>';
                    }

                    if(row.jxkpDataAuth == 0){
                        dataAuth += '<span  >绩效考评数据权限：本人</span><br/>';
                    }else if(row.jxkpDataAuth == 1){
                        dataAuth += '<span  ></span><br/>';
                    }else if(row.jxkpDataAuth == 2){
                        dataAuth += '<span  ></span><br/>';
                    }else if(row.jxkpDataAuth == 3){
                        dataAuth += '<span  ></span><br/>';
                    }else if(row.jxkpDataAuth ==5){
                        dataAuth += '<span  >绩效考评数据权限：本部门</span><br/>';
                    }else if(row.jxkpDataAuth == 6){
                        dataAuth += '<span  >绩效考评数据权限：本部门及下级部门</span><br/>';
                    }else if(row.jxkpDataAuth == 7){
                        dataAuth += '<span  >绩效考评数据权限：上级部门及其下级部门</span><br/>';
                    }else if(row.jxkpDataAuth == 4){
                        dataAuth +=  '<span  >绩效考评数据权限：全部</span><br/>';
                    }else if(row.jxkpDataAuth == -1){
                        dataAuth +=  '<span  >绩效考评数据权限：无权限</span><br/>';
                    }

                    if(row.slaDataAuth == 0){
                        dataAuth += '<span  >受立案数据权限：本人</span><br/>';
                    }else if(row.slaDataAuth == 1){
                        dataAuth += '<span  >受立案数据权限：本办案场所</span><br/>';
                    }else if(row.slaDataAuth == 2){
                        dataAuth += '<span  >受立案数据权限：本办案场所及下级办案场所</span><br/>';
                    }else if(row.slaDataAuth == 3){
                        dataAuth += '<span  >受立案数据权限：上级办案场所及其下级办案场所</span><br/>';
                    }else if(row.slaDataAuth ==5){
                        dataAuth += '<span  >受立案数据权限：本部门</span><br/>';
                    }else if(row.slaDataAuth == 6){
                        dataAuth += '<span  >受立案数据权限：本部门及下级部门</span><br/>';
                    }else if(row.slaDataAuth == 7){
                        dataAuth += '<span  >受立案数据权限：上级部门及其下级部门</span><br/>';
                    }else if(row.slaDataAuth == 4){
                        dataAuth +=  '<span  >受立案数据权限：全部</span><br/>';
                    }else if(row.slaDataAuth == -1){
                        dataAuth +=  '<span  >受立案数据权限：无权限</span><br/>';
                    }

                    if(row.zhglDataAuth == 0){
                        dataAuth += '<span  >综合管理数据权限：本人</span><br/>';
                    }else if(row.zhglDataAuth == 1){
                        dataAuth += '<span  >综合管理数据权限：本办案场所</span><br/>';
                    }else if(row.zhglDataAuth == 2){
                        dataAuth += '<span  >综合管理数据权限：本办案场所及下级办案场所</span><br/>';
                    }else if(row.zhglDataAuth == 3){
                        dataAuth += '<span  >综合管理数据权限：上级办案场所及其下级办案场所</span><br/>';
                    }else if(row.zhglDataAuth ==5){
                        dataAuth += '<span  >综合管理数据权限：本部门</span><br/>';
                    }else if(row.zhglDataAuth == 6){
                        dataAuth += '<span  >综合管理数据权限：本部门及下级部门</span><br/>';
                    }else if(row.zhglDataAuth == 7){
                        dataAuth += '<span  >综合管理数据权限：上级部门及其下级部门</span><br/>';
                    }else if(row.zhglDataAuth == 4){
                        dataAuth +=  '<span  >综合管理数据权限：全部</span><br/>';
                    }else if(row.zhglDataAuth == -1){
                        dataAuth +=  '<span  >综合管理数据权限：无权限</span><br/>';
                    }

                    return dataAuth;
                }
            },
            {field: 'Configure', title: '配置权限', width: 250,formatter: function (value, row, index) {
                    var dataAuth = "";
                    if (row.bacsConfigure == 0) {
                        dataAuth += '<span  >办案场所配置权限：本人</span><br/>';
                    } else if (row.bacsConfigure == 1) {
                        dataAuth += '<span  >办案场所配置权限：本办案场所</span><br/>';
                    } else if (row.bacsConfigure == 2) {
                        dataAuth += '<span  >办案场所配置权限：本办案场所及下级办案场所</span><br/>';
                    } else if (row.bacsConfigure == 3) {
                        dataAuth += '<span  >办案场所配置权限：上级办案场所及其下级办案场所</span><br/>';
                    } else if (row.bacsConfigure == 5) {
                        dataAuth += '<span  >办案场所配置权限：本部门</span><br/>';
                    } else if (row.bacsConfigure == 6) {
                        dataAuth += '<span  >办案场所配置权限：本部门及下级部门</span><br/>';
                    } else if (row.bacsConfigure == 7) {
                        dataAuth += '<span  >办案场所配置权限：上级部门及其下级部门</span><br/>';
                    } else if (row.bacsConfigure == 4){//4
                        dataAuth +=  '<span  >办案场所配置权限：全部</span><br/>';
                    }else if(row.bacsConfigure == -1){
                        dataAuth +=  '<span  >办案场所配置权限：无权限</span><br/>';
                    }

                    if(row.sacwConfigure == 0){
                        dataAuth += '<span  >涉案财物配置权限：本人</span><br/>';
                    }else if(row.sacwConfigure == 1){
                        dataAuth += '<span  >涉案财物配置权限：本涉案财物场所</span><br/>';
                    }else if(row.sacwConfigure == 2){
                        dataAuth += '<span  >涉案财物配置权限：本涉案财物场所及下级涉案财物场所</span><br/>';
                    }else if(row.sacwConfigure == 3){
                        dataAuth += '<span  >涉案财物配置权限：上级涉案财物场所及其下级涉案财物场所</span><br/>';
                    }else if(row.sacwConfigure ==5){
                        dataAuth += '<span  >涉案财物配置权限：本部门</span><br/>';
                    }else if(row.sacwConfigure == 6){
                        dataAuth += '<span  >涉案财物配置权限：本部门及下级部门</span><br/>';
                    }else if(row.sacwConfigure == 7){
                        dataAuth += '<span  >涉案财物配置权限：上级部门及其下级部门</span><br/>';
                    }else if(row.sacwConfigure == 4){
                        dataAuth +=  '<span  >涉案财物配置权限：全部</span><br/>';
                    }else if(row.sacwConfigure == -1){
                        dataAuth +=  '<span  >涉案财物配置权限：无权限</span><br/>';
                    }

                    if(row.jzglConfigure == 0){
                        dataAuth += '<span  >卷管中心配置权限：本人</span><br/>';
                    }else if(row.jzglConfigure == 1){
                        dataAuth += '<span  >卷管中心配置权限：本卷管中心</span><br/>';
                    }else if(row.jzglConfigure == 2){
                        dataAuth += '<span  >卷管中心配置权限：本卷管中心及下级卷管中心</span><br/>';
                    }else if(row.jzglConfigure == 3){
                        dataAuth += '<span  >卷管中心配置权限：上级卷管中心及其下级卷管中心</span><br/>';
                    }else if(row.jzglConfigure ==5){
                        dataAuth += '<span  >卷管中心配置权限：本部门</span><br/>';
                    }else if(row.jzglConfigure == 6){
                        dataAuth += '<span  >卷管中心配置权限：本部门及下级部门</span><br/>';
                    }else if(row.jzglConfigure == 7){
                        dataAuth += '<span  >卷管中心配置权限：上级部门及其下级部门</span><br/>';
                    }else if(row.jzglConfigure == 4){
                        dataAuth +=  '<span  >卷管中心配置权限：全部</span><br/>';
                    }else if(row.jzglConfigure == -1){
                        dataAuth +=  '<span  >卷管中心配置权限：无权限</span><br/>';
                    }

                    if(row.jxkpConfigure == 0){
                        dataAuth += '<span  >绩效考评配置权限：本人</span><br/>';
                    }else if(row.jxkpConfigure == 1){
                        dataAuth += '<span  ></span><br/>';
                    }else if(row.jxkpConfigure == 2){
                        dataAuth += '<span  ></span><br/>';
                    }else if(row.jxkpConfigure == 3){
                        dataAuth += '<span  ></span><br/>';
                    }else if(row.jxkpConfigure ==5){
                        dataAuth += '<span  >绩效考评配置权限：本部门</span><br/>';
                    }else if(row.jxkpConfigure == 6){
                        dataAuth += '<span  >绩效考评配置权限：本部门及下级部门</span><br/>';
                    }else if(row.jxkpConfigure == 7){
                        dataAuth += '<span  >绩效考评配置权限：上级部门及其下级部门</span><br/>';
                    }else if(row.jxkpConfigure == 4){
                        dataAuth +=  '<span  >绩效考评配置权限：全部</span><br/>';
                    }else if(row.jxkpConfigure == -1){
                        dataAuth +=  '<span  >绩效考评配置权限：无权限</span><br/>';
                    }

                    if(row.slaConfigure == 0){
                        dataAuth += '<span  >受立案配置权限：本人</span><br/>';
                    }else if(row.slaConfigure == 1){
                        dataAuth += '<span  >受立案配置权限：本办案场所</span><br/>';
                    }else if(row.slaConfigure == 2){
                        dataAuth += '<span  >受立案配置权限：本办案场所及下级办案场所</span><br/>';
                    }else if(row.slaConfigure == 3){
                        dataAuth += '<span  >受立案配置权限：上级办案场所及其下级办案场所</span><br/>';
                    }else if(row.slaConfigure ==5){
                        dataAuth += '<span  >受立案配置权限：本部门</span><br/>';
                    }else if(row.slaConfigure == 6){
                        dataAuth += '<span  >受立案配置权限：本部门及下级部门</span><br/>';
                    }else if(row.slaConfigure == 7){
                        dataAuth += '<span  >受立案配置权限：上级部门及其下级部门</span><br/>';
                    }else if(row.slaConfigure == 4){
                        dataAuth +=  '<span  >受立案配置权限：全部</span><br/>';
                    }else if(row.slaConfigure == -1){
                        dataAuth +=  '<span  >受立案配置权限：无权限</span><br/>';
                    }

                    if(row.xtConfigure == 0){
                        dataAuth += '<span  >综合管理数据权限：本人</span><br/>';
                    }else if(row.xtConfigure == 1){
                        dataAuth += '<span  >综合管理数据权限：本办案场所</span><br/>';
                    }else if(row.xtConfigure == 2){
                        dataAuth += '<span  >综合管理数据权限：本办案场所及下级办案场所</span><br/>';
                    }else if(row.xtConfigure == 3){
                        dataAuth += '<span  >综合管理数据权限：上级办案场所及其下级办案场所</span><br/>';
                    }else if(row.xtConfigure ==5){
                        dataAuth += '<span  >综合管理数据权限：本部门</span><br/>';
                    }else if(row.xtConfigure == 6){
                        dataAuth += '<span  >综合管理数据权限：本部门及下级部门</span><br/>';
                    }else if(row.xtConfigure == 7){
                        dataAuth += '<span  >综合管理数据权限：上级部门及其下级部门</span><br/>';
                    }else if(row.xtConfigure == 4){
                        dataAuth +=  '<span  >综合管理数据权限：全部</span><br/>';
                    }else if(row.xtConfigure == -1){
                        dataAuth +=  '<span  >综合管理数据权限：无权限</span><br/>';
                    }

                    return dataAuth;
                }
            },
            {
                field: '操作', title: '操作', width: 110,
                formatter: function (value, row, index) {
                    var e = '';
                    var d = '';
                    if (isGridButton("editRole")) {
                        e = '<span class="spanRow"><a href="javascript:void(0)" class="gridedit" onclick="editRole('
                            + index + ')">修改</a></span> ';
                    }
                    if (isGridButton("deleteRole")) {
                        d = '<span class="spanRow"><a href="javascript:void(0)" class="griddel" onclick="deleteRole('
                            + index + ')")">删除</a></span>';
                    }
                    return e + d;
                }
            }
        ]],
        pagination: true,
        pageSize: 20,
        pageList: [20, 30, 40, 50, 100],
        rownumbers: true,
        toolbar: '#toolbar',
        //行选择方法，进行条件触发
        onSelect: function (rowIndex, rowData) {
            rolegridData(rowData);
        }
    });
    var p = $('#role_datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh: function () {
            $('#role_datagrid').datagrid('load', {
                name_t: $('#s_name').textbox('getValue'),
                trefresh: new Date().getTime()
            });
            return false;
        }
    });
    //加载权限数据
    $('#authority-tree').tree({
        url: "../authority/getAuthorityTree.do",
        checkbox: true,
        cascadeCheck: false,
        onClick: function (record) {
            var state = record.checked ? "uncheck" : "check";
            $('#authority-tree').tree(state, record.target);
        }
    });
    //加载功能权限
    loadOtherRole();
    $('#fudong').remove();
});
//角色的权限赋值
function rolegridData(row) {
    //所有选择的清空
    var nodes = $('#authority-tree').tree('getChecked');
    $.each(nodes, function (index, value) {
        $('#authority-tree').tree('uncheck', value.target);
    });
    jQuery.ajax({
        async: false,
        contentType: 'application/json',
        url: '../authority/getRoleAuthority.do',
        data: {id: row.id, trefresh: new Date().getTime()},
        dataType: 'json',
        success: function (data) {
            if (data) {
                $.each(data, function (index, value) {
                    var currObj = $('#authority-tree').tree('find', value);
                    if (currObj) {
                        var currTarget = currObj.target;
                        $('#authority-tree').tree("check", currTarget);
                    }
                });
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '查询该角色权限失败（getRoleAuthority）!');
        }
    });
}
//全选
function allSelect(flag) {
    var nodes = $('#authority-tree').tree('getChecked', (flag ? "unchecked" : "checked"));
    $.each(nodes, function (index, value) {
        $('#authority-tree').tree((flag ? "check" : "uncheck"), value.target);
    });
}
//保存角色权限
function saveRoleAuthority() {
    var dataGridSelected = $('#role_datagrid').datagrid("getSelected");
    if (dataGridSelected) {
        $.messager.progress({
            title: '请等待',
            msg: '正在保存...'
        });
        var authorityIds = "";
        var nodes = $('#authority-tree').tree('getChecked');
        $.each(nodes, function (index, value) {
            authorityIds += value.id + ",";
        });
        var roleId = dataGridSelected.id;
        jQuery.ajax({
            method: "POST",
            url: '../authority/savaRoleAuthority.do',
            data: {roleId: roleId, authorityIds: authorityIds},
            dataType: 'json',
            success: function (data) {
                $.messager.progress('close');
                $.messager.show({
                    title: '信息',
                    msg: '保存成功，权限刷新！',
                    timeout: 3000
                });
                refrshAuthority();
            },
            error: function (data) {
                $.messager.progress('close');
                $.messager.show({
                    title: '信息',
                    msg: '保存失败！',
                    timeout: 3000
                });
            }
        });
    } else {
        alert("请选择对应的角色..");
    }
}
//刷新权限
function refrshAuthority() {
    $.ajax({
        //async: false,
        type: 'POST',
        url: '../authority/refrshAuthority.do',
        dataType: 'json',
        success: function (data) {

        },
        error: function () {
            $.messager.alert('警告', '刷新权限失败（refrshAuthority）!');
        }
    })
}
//增加角色
function addRole() {
    showDialog('#role_dialog', '新增用户');
    $('#role_form').form('clear');
    url = 'add.do';
}
//修改角色信息
function editRole(index) {
    var row = $('#role_datagrid').datagrid('getRows')[index];
    if (row) {
        showDialog('#role_dialog', '编辑角色信息');
        $('#role_form').form('clear');
        $('#role_form').form('load', row);
        url = 'edit.do?id=' + row.id;
    } else {
        $.messager.alert('提示', '请选择一行!');
    }
}
//删除角色
function deleteRole(index) {
    var row = $('#role_datagrid').datagrid('getRows')[index];
    if (row) {
        $.messager.confirm('提示', '是否删除数据?', function (r) {
            if (r) {
                $.messager.progress({
                    title: '请等待',
                    msg: '删除数据中...'
                });
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: 'delete.do',
                    data: '{"id":' + row.id + '}',
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示',
                            msg: data.content,
                            timeout: 3000
                        });
                        $('#role_datagrid').datagrid('load', {
                            name_t: $('#s_name').textbox('getValue'),
                            trefresh: new Date().getTime()
                        });
                        $.messager.progress('close');
                    },
                    error: function (data) {
                        $.messager.progress('close');
                        $.messager.alert('错误', '角色删除失败(delete)!');
                    }
                });

            }
        });
    } else {
        $.messager.alert('提示', '请选择一行!');
    }
}
//查询
function doSearch() {
    $('#role_datagrid').datagrid('load', {
        name_t: $('#s_name').textbox('getValue'),
        trefresh: new Date().getTime()
    });
}
//清除
function doClear() {
    $('#s_name').textbox('setValue', ''),
        $('#s_start_date').datebox('setValue', '');
    $('#s_end_date').datebox('setValue', '');
}
保存
function saveData() {
    if (!checkForm('#role_form')) {
        return;
    }
    var elemUserinfo = $('#role_form');
    var userinfo = elemUserinfo.serializeObject();
//	userinfo["dataAuth"]=;
    var jsonuserinfo = JSON.stringify(userinfo);
//	alert("jsonuserinfo="+jsonuserinfo);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: jsonuserinfo,
        dataType: 'json',
        success: function (data) {
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
//			$('#role_datagrid').datagrid('reload',{trefresh:new Date().getTime()});// reload the data
            $('#role_datagrid').datagrid('load', {
                name_t: $('#s_name').textbox('getValue'),
                trefresh: new Date().getTime()
            });
            $('#role_dialog').dialog('close');
        },
        error: function (data) {
            $('#role_dialog').dialog('close');
            $.messager.alert('错误', '保存失败!' + data.content());
        }
    });
}






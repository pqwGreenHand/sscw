$(function () {
    $('#role_datagrid').datagrid({
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
        url: '../role/getRoles.do',
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
            {field: 'name', title: '角色名', width: 150},
            {field: 'dataAuth', title: '办案中心数据权限', width: 150,
                formatter: function (value, row, index) {
                    if (row.dataAuth == 0) {
                        return '<span  >本人</span>';
                    } else if (row.dataAuth == 1) {
                        return '<span  >本办案场所</span>';
                    } else if (row.dataAuth == 2) {
                        return '<span  >本办案场所及下级办案场所</span>';
                    } else if (row.dataAuth == 3) {
                        return '<span  >上级办案场所及其下级办案场所</span>';
                    } else if (row.dataAuth == 5) {
                        return '<span  >本部门</span>';
                    } else if (row.dataAuth == 6) {
                        return '<span  >本部门及下级部门</span>';
                    } else if (row.dataAuth == 7) {
                        return '<span  >上级部门及其下级部门</span>';
                    } else {//4
                        return '<span  >全部</span>';
                    }
                }
            },
            {field: 'invDataAuth', title: '涉案财物数据权限', width: 150,
                formatter: function (value, row, index) {
                    if (row.invDataAuth == 0) {
                        return '<span  >本人</span>';
                    } else if (row.invDataAuth == 1) {
                        return '<span  >本涉案财物场所</span>';
                    } else if (row.invDataAuth == 2) {
                        return '<span  >本涉案财物场所及下级涉案财物场所</span>';
                    } else if (row.invDataAuth == 3) {
                        return '<span  >上级涉案财物场所及其下级涉案财物场所</span>';
                    } else if (row.invDataAuth == 5) {
                        return '<span  >本部门</span>';
                    } else if (row.invDataAuth == 6) {
                        return '<span  >本部门及下级部门</span>';
                    } else if (row.invDataAuth == 7) {
                        return '<span  >上级部门及其下级部门</span>';
                    } else {//4
                        return '<span  >全部</span>';
                    }
                }
            },
            {field: 'jzDataAuth', title: '卷管中心数据权限', width: 150,
                formatter: function (value, row, index) {
                    if (row.jzDataAuth == 0) {
                        return '<span  >本人</span>';
                    } else if (row.jzDataAuth == 1) {
                        return '<span  >本卷管中心</span>';
                    } else if (row.jzDataAuth == 2) {
                        return '<span  >本卷管中心及下级卷管中心</span>';
                    } else if (row.jzDataAuth == 3) {
                        return '<span  >上级卷管中心及其下级卷管中心</span>';
                    } else if (row.jzDataAuth == 5) {
                        return '<span  >本部门</span>';
                    } else if (row.jzDataAuth == 6) {
                        return '<span  >本部门及下级部门</span>';
                    } else if (row.jzDataAuth == 7) {
                        return '<span  >上级部门及其下级部门</span>';
                    } else {//4
                        return '<span  >全部</span>';
                    }
                }
            },
            {field: 'kpDataAuth', title: '考评数据权限', width: 150,
                formatter: function (value, row, index) {
                    if(row.kpDataAuth==0){
                        return '<span  >本办案场所</span>';
                    }else if(row.kpDataAuth==6) {
                        return '<span  >本法制员</span>';
                    }
                    else if(row.kpDataAuth==4) {
                        return '<span  >全部考评数据</span>';
                    }
                }
            },
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
        url: "../authority/getCsAuthorityTree.do",
        checkbox: true,
        cascadeCheck: false,
        onClick: function (record) {
            var state = record.checked ? "uncheck" : "check";
            $('#authority-tree').tree(state, record.target);
        }
    });
    //加载功能权限
  //  loadOtherRole();
    $('#fudong').remove();
})

// 查询
function doSearch() {
    $('#role_datagrid').datagrid({
        queryParams: {
            name: $('#s_name').val(),
            trefresh: new Date().getTime()
        }
    })
}

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
        url: '../authority/getRoleAuthorityCs.do',
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

// 全选
function allSelect(flag) {
    var nodes = $('#authority-tree').tree('getChecked', (flag ? "unchecked" : "checked"));
    $.each(nodes, function (index, value) {
        $('#authority-tree').tree((flag ? "check" : "uncheck"), value.target);
    });
}

// 保存角色权限
function saveRoleAuthorityCs() {
    var dataGridSelected = $('#role_datagrid').datagrid('getSelected');
    if (dataGridSelected){
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
            url: '../authority/savaRoleAuthorityCs.do',
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

    }else {
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
$(function () {
    $('#parentId').combo({disabled: true});
    $('#authorityTreegrid').treegrid({
        //	title:"权限管理",
        iconCls: 'icon-datagrid',
        width: '100%',
        height: 600,
        url: 'allTree.do',
        state: 'closed',
        idField: 'id',
        treeField: 'title',
        frozenColumns: [[
            {title: "标题", field: 'title', width: 300}
        ]],
        columns: [[
            //{title:"英文标题",field:'englishTitle',width:150},
            {title: "描述", field: 'description', width: 200},
            {title: "权限ID", field: 'id', width: 200},
            {title: "URL", field: 'url', width: 450},
            {
                title: '类型', field: 'type', width: 200,
                formatter: function (value, row, index) {
                    if (row.type == 1) {
                        return "菜单目录";
                    } else if (row.type == 0) {
                        return "系统目录";
                    } else if (row.type == 2) {
                        return "菜单URL";
                    } else if (row.type == 3) {
                        return "按钮";
                    } else if (row.type == 4) {
                        return "表格按钮";
                    } else if (row.type == 5) {
                        return "隐藏URL";
                    } else if (row.type == 6) {
                        return "导航";
                    } else if (row.type == 7) {
                        return "外部链接";
                    }
                }
            },
            {title: "配置参数", field: 'jsMethod', width: 150}
        ]],
        onLoadSuccess: function () {
            $('#authorityTreegrid').treegrid('collapseAll');
        },
        toolbar: '#toolbar'
    });

    $('#fudong').remove();
});

//打开全部目录
function openAllAuthority() {
    $('#authorityTreegrid').treegrid('expandAll');
}

//关闭全部目录
function closeAllAuthority() {
    $('#authorityTreegrid').treegrid('collapseAll');
}

//增加新权限
function addAuthority() {
    showDialog('#data_dialog', '新增权限');
    $('#data_form').form('clear');
    document.getElementById("hidden1").style.display = "none";
    document.getElementById("hidden2").style.display = "";
    document.getElementById("hidden3").style.display = "";
    url = 'addAuthority.do';
    initForm();
    $('#parentId').combo({disabled: true});
    //----默认系统目录状态
    //$('#parentId').combo({disabled:true});
    document.getElementById("hidden1").style.display = "none";
    document.getElementById("hidden2").style.display = "";
    document.getElementById("hidden3").style.display = "none";
    $('#parentId').combo({disabled: true});
    //必填项附颜色
    document.getElementById('font_authorityId').setAttribute('color', 'red');
    $('#authorityId').validatebox({
        required: true
    });
    document.getElementById('font_title').setAttribute('color', 'red');
    $('#title').validatebox({
        required: true
    });
    document.getElementById('font_type').setAttribute('color', 'red');
    $('#type').validatebox({
        required: true
    });
    document.getElementById('font_parentId').setAttribute('color', '');
    document.getElementById('font_sortNumber').setAttribute('color', '');
    document.getElementById('font_url').setAttribute('color', 'red');
    $('#url').validatebox({
        required: true
    });
    document.getElementById('font_icon').setAttribute('color', '');
    document.getElementById('font_description').setAttribute('color', '');
    document.getElementById('font_jsMethod').setAttribute('color', '');
    document.getElementById('font_son').setAttribute('color', '');
    document.getElementById('font_jsMethod1').setAttribute('color', '');

}

function editAuthority() {
    var row = $('#authorityTreegrid').treegrid('getSelected');
    if (row) {
        showDialog('#data_dialog', '修改权限');
        clearStatus();
        var type = row.type;
        $('#parentId').combotree({
            url: 'rootTree.do?type=' + type,
            panelWidth: 300,
            valueField: 'id',
            textField: 'text',
            onLoadSuccess: function (node, data) {
                //alert(JSON.stringify(data));
            }
        });
//		initForm();
        $('#type').combobox({
            data: typeData,
            valueField: 'id',
            textField: 'text',
            onSelect: function (rowIndex, rowData) {
                changeType(rowIndex, rowData);
            }
        });
        $('#data_form').form('clear');
        $('#type').combobox('select', row.type);
        changeType1(row.type);
//	    $('#data_form').form('load',row);
        $('#id').val(row.id);
        $('#authorityId').val(row.id);
        $('#rootId').val(row.rootId);
        $('#title').val(row.title);
        $('#englishTitle').val(row.englishTitle);
        $('#type').combobox('setValue', row.type);
        $('#parentId').combotree('setValue', row.parentId);
//	    alert("-----sortNumber-----"+row.sortNumber);
        $('#sortNumber').val(row.sortNumber);
        $('#url').val(row.url);
        $('#icon').val(row.icon);
        $('#bigIcon').val(row.bigIcon);
        $('#smallIcon').val(row.smallIcon);
        $('#description').val(row.description);
        $('#jsMethod1').val(row.jsMethod);
        $('#jsMethod').val(row.jsMethod);
        url = 'editAuthority.do?id=' + row.id;
    } else {
        $.messager.alert('提示', '请选择一行!');
    }
}

function removeAuthority() {
    var row = $('#authorityTreegrid').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示', '确定删除?', function (r) {
            if (r) {
                jQuery.ajax({
                    type: 'POST',
                    contentType: 'application/json',
                    url: 'removeAuthority.do',
                    data: '{"id":' + row.id + '}',
                    dataType: 'json',
                    success: function (data) {

                        $.messager.show({
                            title: '提示',
                            msg: data.content,
                            timeout: 3000
                        });
                        $('#authorityTreegrid').datagrid('reload', {trefresh: new Date().getTime()});// reload the data
                        location.reload();
                    },
                    error: function (data) {
                        //exception in java
                        $.messager.alert('错误', '删除失败（removeAuthority）!');
                    }
                });

            }
        });
    } else {
        $.messager.alert('提示', '请选择一行!');
    }
}

//下拉框
function initForm() {
    $('#type').combobox({
        data: typeData,
        valueField: 'id',
        textField: 'text',
        onSelect: function (rowIndex, rowData) {
            changeType(rowIndex, rowData);
        }
    });
    $('#parentId').combotree({
        url: 'rootTree.do?type=0',
        valueField: 'id',
        textField: 'text',
        panelWidth: 300,
        onLoadSuccess: function (node, data) {
        }
    });
}

//权限新增保存
function saveData() {
    var elemUserinfo = $('#data_form');
    var info = elemUserinfo.serializeObject();
    var jsonInfo = JSON.stringify(info);
    jQuery.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: url,
        data: jsonInfo,
        dataType: 'json',
        success: function (data) {
            $.messager.show({
                title: '提示',
                msg: data.content,
                timeout: 3000
            });
            $('#authorityTreegrid').treegrid('reload', {trefresh: new Date().getTime()});// reload the data
            $('#parentId').combotree('reload', 'rootTree.do');
            $('#data_dialog').dialog('close');
        },
        error: function (data) {
            //exception in java
            $.messager.alert('错误', '权限新增失败（addAuthority）!');
        }
    });
}

function changeType1(data) {
    var value = data;
    //系统目录
    if (value == 0) {
        $('#parentId').combo({disabled: true});
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "";
        document.getElementById("hidden3").style.display = "none";
        $('#parentId').combo({disabled: true});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', '');
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', '');
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //菜单目录
    if (value == 1) {
        document.getElementById("hidden1").style.display = " ";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = " ";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //菜单url
    if (value == 2) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "none";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', '');
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //按钮
    if (value == 3) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', 'red');
        $('#jsMethod1').validatebox({
            required: true
        });
    }
    //表格按钮
    if (value == 4) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', 'red');
        $('#jsMethod1').validatebox({
            required: true
        });
    }
    //隐藏url
    if (value == 5) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "none";
        //	$('#parentId').combo({disabled:true});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', '');
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //导航
    if (value == 6) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "";
        //	$('#parentId').combo({disabled:true});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', '');
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', '');
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
}

function changeType(rowIndex, rowData) {
    clearStatus();
    var value = $('#type').combobox('getValue');
    //系统目录
    if (value == 0) {
        $('#parentId').combo({disabled: true});
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "";
        document.getElementById("hidden3").style.display = "none";
        $('#parentId').combo({disabled: true});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', '');
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', '');
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //菜单目录
    if (value == 1) {
        document.getElementById("hidden1").style.display = " ";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = " ";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //菜单url
    if (value == 2 || value == 7) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "none";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', '');
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //按钮
    if (value == 3) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', 'red');
        $('#jsMethod1').validatebox({
            required: true
        });
    }
    //表格按钮
    if (value == 4) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "";
        $('#parentId').combo({disabled: false});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', 'red');
        $('#jsMethod1').validatebox({
            required: true
        });
    }
    //隐藏url
    if (value == 5) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "none";
        //	$('#parentId').combo({disabled:true});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', 'red');
        $('#parentId').validatebox({
            required: true
        });
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', 'red');
        $('#url').validatebox({
            required: true
        });
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', '');
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
    //导航
    if (value == 6) {
        document.getElementById("hidden1").style.display = "none";
        document.getElementById("hidden2").style.display = "none";
        document.getElementById("hidden3").style.display = "";
        //	$('#parentId').combo({disabled:true});
        //必填项附颜色
        document.getElementById('font_authorityId').setAttribute('color', 'red');
        $('#authorityId').validatebox({
            required: true
        });
        document.getElementById('font_title').setAttribute('color', 'red');
        $('#title').validatebox({
            required: true
        });
        document.getElementById('font_type').setAttribute('color', 'red');
        $('#type').validatebox({
            required: true
        });
        document.getElementById('font_parentId').setAttribute('color', '');
        document.getElementById('font_sortNumber').setAttribute('color', '');
        document.getElementById('font_url').setAttribute('color', '');
        document.getElementById('font_icon').setAttribute('color', '');
        document.getElementById('font_description').setAttribute('color', '');
        document.getElementById('font_jsMethod').setAttribute('color', 'red');
        $('#jsMethod').validatebox({
            required: true
        });
        document.getElementById('font_son').setAttribute('color', '');
        document.getElementById('font_jsMethod1').setAttribute('color', '');
    }
}

function refreshAuthority() {
    location.reload();
}

function clearStatus() {
    document.getElementById('font_authorityId').setAttribute('color', '');
    $('#authorityId').validatebox({
        required: false
    });
    document.getElementById('font_title').setAttribute('color', '');
    $('#title').validatebox({
        required: false
    });
    document.getElementById('font_type').setAttribute('color', '');
    $('#type').validatebox({
        required: false
    });
    document.getElementById('font_parentId').setAttribute('color', '');
    $('#parentId').validatebox({
        required: false
    });
    document.getElementById('font_parentId').setAttribute('color', '');
    $('#parentId').validatebox({
        required: false
    });
    document.getElementById('font_url').setAttribute('color', '');
    $('#url').validatebox({
        required: false
    });
    document.getElementById('font_icon').setAttribute('color', '');
    $('#icon').validatebox({
        required: false
    });
    document.getElementById('font_description').setAttribute('color', '');
    $('#description').validatebox({
        required: false
    });
    document.getElementById('font_jsMethod').setAttribute('color', '');
    $('#jsMethod').validatebox({
        required: false
    });
    document.getElementById('font_son').setAttribute('color', '');
    $('#son').validatebox({
        required: false
    });
    document.getElementById('font_jsMethod1').setAttribute('color', '');
    $('#jsMethod1').validatebox({
        required: false
    });

}
$(function() {
            $('#commonUserTable').bootstrapTable('destroy').bootstrapTable({    //'destroy' 是必须要加的==作用是加载服务器//    //数据，初始化表格的内容Destroy the bootstrap table.
                url:'../../../evaluation/evaluation/selectUserCommon.do',
                dataType: 'json',
                data_locale: "zh-US",
                pagination: true,
                pageList: [5,10,15],
                pageNumber: 1,
                cache:false,
                pageSize: 5,//每页显示的数量
                sidePagination: "server",
                queryParamsType:'',
                paginationPreText: '‹',
                paginationNextText: '›',
                queryParams: queryParams,
                search: false,
                singleSelect:true,
               /* onClickRow : onClick,*/
                showColumns: false,
                idField: "id",//指定主键列
                //这里也可以将TABLE样式中的<tr>标签里的内容挪到这里面：
                onDblClickRow: function (row) {
                	
                	parent.dblclick_tr_duty(row);
                	
                },
                columns: [ {
                    field: 'id',
                    title: '编号',
                    hidden:'true',
                    width:'10px',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'loginName',
                    title: '民警警号',
                    width:'100px',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'realName',
                    title: '民警姓名',
                    width:'100px',
                    align: 'center',
                    valign: 'middle'
                }, {
                    field: 'email',
                    title: '民警单位',
                    width:'150px',
                    align: 'center',
                    valign: 'middle'
                }, ]
            })
    /*单位选择下拉框Start*/
    $.ajax({
        url: '../../../evaluation/evaluation/selectAllInterrogateArea.do',
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        traditional: true,
        success: function (data) {
            var optionstring = "<option hassubinfo=\"true\" value=\"\" > 请选择民警单位 </option>";
            for (var i = 0; i < data.length; i++) {
                optionstring += "<option hassubinfo=\"true\" value=\"" + data[i].id + "\" >" + data[i].name + "</option>";
            }
            $("#mjdw").html(optionstring);
            $("#mjdw").trigger("liszt:updated");
            $("#mjdw").val(sessionStorage.dw);
            
            
            dwchange();
        }
    });
    $("#commonUserTable").on("click-row.bs.table",function(e,row,ele){
        
        sessionStorage.docname = row.realName,
            
        
        $(".danger").removeClass("danger");
        $(ele).addClass("danger");
    });
});
//获取查询参数
function queryParams(params){
    return{
        //每页多少条数据
        pageSize: params.pageSize,
        //请求第几页
        pageNumber:params.pageNumber,
       /* Name:$('#search_name').val(),
        Tel:$('#search_tel').val()*/

    }
}

//查询
$("#selectUserButton").click(function(){
   /* var data =  queryParams2();
    data = JSON.stringify(data);*/
    $('#commonUserTable').bootstrapTable('destroy').bootstrapTable({    //'destroy' 是必须要加的==作用是加载服务器//    //数据，初始化表格的内容Destroy the bootstrap table.
        url:'../../../evaluation/evaluation/selectUserCommonParam.do',
        dataType: 'json',
        data_locale: "zh-US",
        pagination: true,
        pageList: [5,10,15],
        pageNumber: 1,
        cache:false,
        pageSize: 5,//每页显示的数量
        paginationPreText: '‹',
        paginationNextText: '›',
        sidePagination: "server",
        queryParamsType:'',
        queryParams: function (params) {
            var data =  queryParams2(params);
            return data;
        },
        search: false,
        showColumns: false,
        onDblClickRow: function (row) {
        	
        	parent.dblclick_tr_duty(row);
        	
        },
        columns: [
            {
            field: 'id',
            title: '编号',
            hidden:'true',
            width:'10px',
                align: 'center',
                valign: 'middle'
        }, {
            field: 'loginName',
            title: '民警警号',
            width:'100px',
                align: 'center',
                valign: 'middle'
        }, {
            field: 'realName',
            title: '民警姓名',
            width:'100px',
                align: 'center',
                valign: 'middle'
        }, {
            field: 'email',
            title: '民警单位',
            width:'150px',
                align: 'center',
                valign: 'middle'
        }, ]
    })


})


function queryParams2(params){
    var mjjh = $("#mjjh").val();
    var mjxm = $("#mjxm").val();
    var mjdw = $("#mjdw").val();
    return{
        //每页多少条数据
        pageSize: params.pageSize,
        //请求第几页
        pageNumber:params.pageNumber,
        /* Name:$('#search_name').val(),
         Tel:$('#search_tel').val()*/
        mjjh:mjjh,
        mjxm: mjxm,
        mjdw: mjdw,
    }
}

function cancelFzcxCommon() {
    $('select option:selected').removeAttr('selected');
    $("#mjjh").val("");
    $("#mjxm").val("");
    dwchange();
}



function dwchange(){
    $('#commonUserTable').bootstrapTable('destroy').bootstrapTable({    //'destroy' 是必须要加的==作用是加载服务器//    //数据，初始化表格的内容Destroy the bootstrap table.
        url:'../../../evaluation/evaluation/selectUserCommonParam.do',
        dataType: 'json',
        data_locale: "zh-US",
        pagination: true,
        pageList: [5,10,15],
        pageNumber: 1,
        cache:false,
        pageSize: 5,//每页显示的数量
        paginationPreText: '‹',
        paginationNextText: '›',
        sidePagination: "server",
        queryParamsType:'',
        queryParams: function (params) {
            var data =  queryParams2(params);
            return data;
        },
        search: false,
        showColumns: false,
        onDblClickRow: function (row) {
        	
        	parent.dblclick_tr_duty(row);
        	
        },
        columns: [
            {
            field: 'id',
            title: '编号',
            hidden:'true',
            width:'10px',
                align: 'center',
                valign: 'middle'
        }, {
            field: 'loginName',
            title: '民警警号',
            width:'100px',
                align: 'center',
                valign: 'middle'
        }, {
            field: 'realName',
            title: '民警姓名',
            width:'100px',
                align: 'center',
                valign: 'middle'
        }, {
            field: 'email',
            title: '民警单位',
            width:'150px',
                align: 'center',
                valign: 'middle'
        }, ]
    })


}





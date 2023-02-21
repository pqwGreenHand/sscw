$(function(){
    loadSerialPerson("");
    loadPersonnel("");
     $('#recognizegrid').datagrid({
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
         url: '../recognizeRecord/list.do',
         queryParams : {
             trefresh:new Date().getTime()
         },
         sortName: 'id',
         sortOrder: 'desc',
         remoteSort: false,
         idField:'id',
         singleSelect:true,
         frozenColumns:[[
             {field:'ck',checkbox:true},
             {title:'ID',field:'id',width:80,sortable:true,hidden:true}
         ]],
         columns:[[  // areaName
             {field:'areaName',title:'所属办案场所',width:120},
             {field:'areaId',title:'所属办案场所ID',width:20,hidden:true},
             {field:'startTime',title:'开始时间',width:100,
                 formatter:function(value,rec){
                     if(value!=null&&value!=''){
                         return valueToDate(value);
                     }else{
                         return "";
                     }
                 }
             },
             {field:'endTime',title:'结束时间',width:100,
                 formatter:function(value,rec){
                     if(value!=null&&value!=''){
                         return valueToDate(value);
                     }else{
                         return "";
                     }
                 }
             },
             {field:'policeman',title:'办案民警人员及其单位',width:120,
                 formatter:function(value,rec){
                     if(value ==null || value==''){
                         return '无';
                     }else{
                         return formatterColunmVal(value,6);
                     }
                 }
             },
             {field:'recognize',title:'检查或者辨认对象',width:100,
                 formatter:function(value,rec){
                     if(value ==null || value==''){
                         return '无';
                     }else{
                         return formatterColunmVal(value,6);
                     }
                 }
             },
             {field:'victim',title:'事主',width:60,
                 formatter:function(value,rec){
                     if(value ==null || value==''){
                         return '无';
                     }else{
                         return formatterColunmVal(value,6);
                     }
                 }
             },
             {field:'witniss',title:'见证人',width:60,
                 formatter:function(value,rec){
                     if(value ==null || value==''){
                         return '无';
                     }else{
                         return formatterColunmVal(value,6);
                     }
                 }
             },
             {field:'target',title:'辨认目的',width:100,
                 formatter:function(value,rec){
                     if(value ==null || value==''){
                         return '无';
                     }else{
                         return formatterColunmVal(value,6);
                     }
                 }
             },
             {field:'result',title:'辨认结果',width:150},
             {field:'id',title:'操作',width:140,
                 formatter:function(value,row,index){
                     var e='';
                     var d='';
                     var f='';
                     if(isGridButton("recognizeEdit") && row.status != 1){
                         e ='<span class="spanRow"><a href="#" class="gridedit" name="nobutton" onclick="recognizeEdit('+index+')">修改</a></span>';
                     }

                     if(row.endTime==null||row.endTime==''){
                         if(isGridButton("startRecognize")){
                             f ='<span class="spanRow"><a href="#" class="gridlook" name="nobutton" onclick="startRecognize('+value+')">辨认</a></span>';
                         }
                     }

                     return e+d+f;
                 }}
         ]],
         pagination:true,
         pageList: [10,20,30,40,50,100],
         rownumbers:true,
         toolbar:'#recognizeToolbar'
     });
    $('#fudong').remove();
    var p = $('#recognizegrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh:function(){

        }
    });
    loadArea();
    loadAreaDialog()

    // 笔录类型下拉框
    $('#recognizeType').combobox({
        url:'../../common/code/listCodeByType.do',
        queryParams : {
            type:'brlx',
            trefresh:new Date().getTime()
        },
        valueField:'codeKey',
        textField:'codeValue',
        onLoadSuccess: function(data){

        },
        onChange:function(newValue,oldValue){
        }
    });

        // 辨认对象下拉框
        $('#recognizeName').combobox({
            url:'../../common/code/listCodeByType.do',
            queryParams:{
                type:'brdx',
                trefresh:new Date().getTime()
            },
            valueField:'codeKey',
            textField:'codeValue',
            onLoadSuccess: function(data){

            },
            onChange:function(newValue,oldValue){
            }
        });

        // 辨认事由和目的下拉框
        $('#target').combobox({
            url:'../../common/code/listCodeByType.do',
            queryParams:{
                type:'brmd',
                trefresh:new Date().getTime()
            },
            valueField:'codeKey',
            textField:'codeValue',
            onLoadSuccess: function(data){

            },

            onChange:function(newValue,oldValue){
            }
        })


});


     // 被辨认人下拉框展示
     function loadSerialPerson(areaId) {
         $('#serialId').combogrid({
             method:'get',
             url:'../serial/getIdentifyPersonList.do?areaId='+areaId,
             mode:'remote',
             idField:'personId',
             textField:'personName',
             columns: [[
                 {field:'personName',title:'姓名',width:50},
                 {field:'certificateNo',title:'证件号码',width:180}
             ]],
             onLoadSuccess: function(data){

             },
             onChange:function(newValue,oldValue){

             },

         })
     }


    // 办案场所下拉框展示
    function loadArea() {
        $('#area_cmb').combobox({
            url:'../combobox/getAllInterrogateAreaName.do',
            valueField:'id',
            textField:'value',
            onLoadSuccess: function(data){

            },
            onChange:function(newValue,oldValue){

            }
        });
    }

    // 查询
    function doSearch() {
        $('#recognizegrid').datagrid('load',{
            areaId:$('#area_cmb').combobox('getValue'),
            policeman:$('#policeman').textbox('getValue'),
            trefresh:new Date().getTime()
        })
    }

    // 清除查询条件
    function doClear() {
        $('#policeman').textbox('setValue',"");
        $('#area_cmb').combobox('setValue',"");
    }

    // 管辖的办案地址下拉框展示
    function loadAreaDialog(){
        $('#area_cmb_id').combobox({
            url:'../combobox/getAllInterrogateAreaName.do',
            valueField:'id',
            textField:'value',
            onLoadSuccess: function(data){

            },
            onChange:function(newValue,oldValue){
                if(newValue != ""){
                   // loadSerialPerson(newValue);
                   // loadPersonnel(newValue);
                }
            }
        })
    }

    // 民警查询
    function  policeInfo() {
        showDialog('#searchPoliceman','民警信息查询');
        $('#policeInfo').datagrid({
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
            url: '../../common/user/getUsers.do',
            queryParams : {
                trefresh:new Date().getTime()
            },
            sortName: 'id',
            sortOrder: 'desc',
            remoteSort: false,
            idField:'id',
            onDblClickRow :function(rowIndex,rowData){
                var allPoliceInfo =  $('#policeman1').textbox('getValue')
                var policeName = rowData.realName;
                var dw = rowData.organizationName;
                if(allPoliceInfo!="" && allPoliceInfo!=null){
                    var s  = policeName+"  "+dw;
                    var i = allPoliceInfo.indexOf(s)
                    if (i == -1){
                        $('#policeman1').textbox('setValue',allPoliceInfo+"\r\n"+s);
                    }else{
                        $.messager.alert('提示','您已添加');
                    }
                }else{
                    $('#policeman1').textbox('setValue',policeName+"  "+dw);
                }
                $('#policeInfo').datagrid('clearSelections');
                $('#searchPoliceman').dialog('close');
            },
            frozenColumns:[[
                {field:'ck',checkbox:true},
                {title:'ID',field:'id',width:50,sortable:true,hidden:true}
            ]],
            columns:[[
                {field:'realName',title:'民警姓名',width:50},
                {field:'certificateNo',title:'民警警号',width:50},
                {field:'organizationName',title:'单位',width:100}
            ]],
            pagination:true,
            pageList: [10,20,30,40,50,100],
            rownumbers:true,
            toolbar:'#toolbar_policeInfo',
            onSelect : function(rowIndex, rowData) {
            },
        });
        var p = $('#policeInfo').datagrid('getPager');
        $(p).pagination({
            onBeforeRefresh:function(){
                $('#policeInfo').datagrid('load', {
                    real_name : $('#policeInfo_name').val(),
                    certificate_no : $('#policeInfo_no').val(),
                    organization_name:$('#policeInfo_organization').val(),
                    trefresh:new Date().getTime()
                });
                return false;
            }
        });
    }

    // 民警模糊查询
    function searchPoliceInfo() {
         $('#policeInfo').datagrid('load',{
             real_name:$('#policeInfo_name').val(),
             certificate_no : $('#policeInfo_no').val(),
             organization_name:$('#policeInfo_organization').val(),
             trefresh:new Date().getTime()
         })
    }

    // 民警模糊清除
    function doClearPoliceInfo() {
        $('#policeInfo_name').val("");
        $('#policeInfo_no').val("");
        $('#policeInfo_organization').val("");
    }

    // 民警选择保存
    function savePoliceInfo() {
        var allPoliceInfo = $('#policeInfo').datagrid('getSelections');
        var policemanStr = '';
        if (allPoliceInfo != null && allPoliceInfo != ''){
            var s  =  $('#policeman1').textbox('getValue');
            for (var i = 0;i< allPoliceInfo.length ; i++){
                 var policeName = allPoliceInfo[i].realName;
                 var organizationName = allPoliceInfo[i].organizationName;
                 var line = policeName+'  ' + organizationName;
                if (s.replace(" ", "").indexOf(line) > -1){
                    continue;
                }
                policemanStr += line + '\r\n';
            }
            var policemanStr = policemanStr.substring(0,policemanStr.lastIndexOf('\r\n'));
            $('#policeman1').textbox('setValue',policemanStr +'\r\n'+s);
            $('#policeInfo').datagrid('clearSelections');
            $('#searchPoliceman').dialog('close');
        }else {
            $.messager.alert('提示','请勾选民警信息!');
        }

    }
      var f = '';
     // 人员查询信息  1.当事人  2,见证人
     function personInfo(index) {
        if(index == 1){
            showDialog('#searchPerson','当事人信息查询');
             f = 1;
        };
        if(index == 2){
            showDialog('#searchPerson','见证人信息查询');
            f = 2;
        };

     }


     // 人员查询
    function loadPersonnel(areaId) {
        $('#personInfo').datagrid({
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
            url: '../serial/getPersonnelList.do?areaId='+areaId,
            queryParams : {
                trefresh:new Date().getTime()
            },
            sortName: 'id',
            sortOrder: 'desc',
            remoteSort: false,
            idField:'id',
            onDblClickRow :function(rowIndex,rowData){
                var tqy =  $('#victim').textbox('getValue');
                var qy = $('#witniss').textbox('getValue');
                var policeName = rowData.personName;
                var dw = rowData.certificateNo;
                if(f == 1){
                    if(tqy!="" && tqy!=null ){
                        //$('#victim').textbox('setValue',tqy+"\r\n"+policeName+"  "+dw+" ");
                        var s  = policeName+"  "+dw;
                        var i = tqy.indexOf(s)
                        if (i == -1){
                            $('#victim').textbox('setValue',tqy+"\r\n"+s);
                        }else{
                            $.messager.alert('提示','您已添加');
                        }
                    }else {
                        $('#victim').textbox('setValue',policeName+"  "+dw+" ");
                    }
                }
                if (f == 2){
                    if(qy != "" && qy != null){
                      //  $('#witniss').textbox('setValue',qy+"\r\n"+policeName+"  "+dw+" ");
                        var s  = policeName+"  "+dw;
                        var i = tqy.indexOf(s)
                        if (i == -1){
                            $('#witniss').textbox('setValue',qy+"\r\n"+s);
                        }else{
                            $.messager.alert('提示','您已添加');
                        }
                    }else {
                        $('#witniss').textbox('setValue',policeName+"  "+dw+ " ");

                    }
                }

                $('#personInfo').datagrid('clearSelections');
                $('#searchPerson').dialog('close');

            },
            frozenColumns:[[
                {field:'ck',checkbox:true},
                {title:'ID',field:'id',width:50,sortable:true,hidden:true}
            ]],
            columns:[[
                {field:'personName',title:'姓名',width:50},
                {field:'certificateNo',title:'证件号码',width:180}
            ]],
            pagination:true,
            pageList: [10,20,30,40,50,100],
            rownumbers:true,
            toolbar:'#toolbar_personInfo',
            onSelect : function(rowIndex, rowData) {
            },
        });
        var p = $('#personInfo').datagrid('getPager');
        $(p).pagination({
            onBeforeRefresh:function(){
                $('#personInfo').datagrid('load', {
                    real_name : $('#policeInfo_name').val(),
                    certificate_no : $('#policeInfo_no').val(),
                    organization_name:$('#policeInfo_organization').val(),
                    trefresh:new Date().getTime()
                });
                return false;
            }
        });
    }

    // 人员查询选择保存
    function savePersonInfo(){
         var personStr = '';
         if (f == 1){
             var allPersonInfo = $('#personInfo').datagrid('getSelections');
             if (allPersonInfo !='' && allPersonInfo !=null){
                 var v  =  $('#victim').textbox('getValue');
                 for (var i= 0;i < allPersonInfo.length;i++){
                     var personName = allPersonInfo[i].personName;
                     var certificateNo = allPersonInfo[i].certificateNo;
                     var line = personName+'  ' + certificateNo;
                     if (v.indexOf(line) > -1){
                         continue;
                     }
                     personStr += line+'\r\n';
                 }
               //  var p = personStr.substring(0,personStr.lastIndexOf('\r\n'));
                 $('#victim').textbox('setValue',personStr+v);
                 $('#personInfo').datagrid('clearSelections');
                 $('#searchPerson').dialog('close');
             }else{
                 $.messager.alert('提示','请勾选当事人信息!');
             }
         }

         if (f == 2){
             var allPersonInfo = $('#personInfo').datagrid('getSelections');
             if (allPersonInfo !='' && allPersonInfo !=null){
                 var w = $('#witniss').textbox('getValue');
                 for (var i= 0;i < allPersonInfo.length;i++){
                     var personName = allPersonInfo[i].personName;
                     var certificateNo = allPersonInfo[i].certificateNo;
                     var line = personName + '  '+ certificateNo
                     if(w.indexOf(line) > -1){
                         continue;
                     }
                     personStr += line +'\r\n';

                 }

                 $('#witniss').textbox('setValue',personStr+w);
                 $('#personInfo').datagrid('clearSelections');
                 $('#searchPerson').dialog('close');
             }else{
                 $.messager.alert('提示','请勾选见证人信息!');
             }
         }
    }


    // 人员模糊查询
    function searchPersonInfo(){
        $('#personInfo').datagrid('load',{
            personName:$('#personInfo_name').val(),
            certificateNo: $('#personInfo_no').val(),
            trefresh:new Date().getTime()
        })
    }

    // 人员模糊查询清除
    function doClearPersonInfo(){
        $('#personInfo_name').val("");
        $('#personInfo_no').val("")
    }

    // 辨认笔录新增
    function recognizeAdd(){
        showDialog('#recognize_dialog','新增辨认笔录信息');
        $('#recognize_form').form('clear');
        url = '../recognizeRecord/add.do';
    }
    // 表单保存
    function saveEnterprise(){
        if(!checkForm('#recognize_form')){
            return;
        }
        var  info = $('#recognize_form').serializeObject();
        info["address"] = $('#area_cmb_id').combobox("getText");
        info["areaId"] = $("#serialId").combogrid("grid").datagrid('getSelected').areaId;
        info["recognize"] = $('#recognizeName').combobox("getText");
        info["caseId"] = $("#serialId").combogrid("grid").datagrid('getSelected').caseId;
        info["personId"] =$("#serialId").combogrid("grid").datagrid('getSelected').personId;
        info["target"] = $("#target").combobox("getText");
        info["recognizeType"] = $("#recognizeType").combobox("getValue");
        var  results = JSON.stringify(info);
        $.messager.progress({
            title:'请等待',
            msg:'添加/修改数据中...'
        });

        jQuery.ajax({
            type:'POST',
            contentType : 'application/json',
            url : url,
            data : results,
            dataType : 'json',
            success : function(data){
                $.messager.progress('close');
                $.messager.show({
                    title:'提示',
                    msg:data.content,
                    timeout:3000
                });
                $('#recognizegrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
                $('#recognize_dialog').dialog('close');
                if(url.indexOf('add.do') > -1){
                    startRecognize(data.callbackData.id);
                }
            },
            error : function(data){
                $.messager.progress('close');
                //exception in java
                $.messager.alert('错误', '未知错误!');
            }
        })
    }


    // 辨认笔录修改
    function recognizeEdit(index) {
        var row = $('#recognizegrid').datagrid('getRows')[index];
        $("#id").val(row.id);
        url = '../recognizeRecord/update.do';
        showDialog('#recognize_dialog','编辑辨认笔录信息');
        $('#recognize_form').form('clear');
        $('#recognize_form').form('load',row);
    }


    // 开始辨认
    function startRecognize(value){
        console.log(value);
        $.messager.progress({
            title:'请等待',
            msg:'辨认笔录界面加载中...'
        });


        jQuery.ajax({
            type : 'POST',
            url : contextPath + '/zhfz/common/cshelper/getCsUrl.do',
            data:{id:value,type:1},
            dataType : 'json',
            success : function(data){
                $.messager.progress('close');
                if(data.error){
                    $.messager.alert('提示',data.content);
                }else{
                    try{
                        // var wsh=new ActiveXObject("WScript.Shell");
                        // var result=wsh.Run(data.callbackData);
                        window.location = data.callbackData
                        //window.location="zhixinrdrecord:// 1 4 http://127.0.0.1:8081 127.0.0.1;21;interrogate;ns204$$"
                    }catch(e){
                        $.messager.alert("提示", "未正确安装客户端程序！"+e);
                    }
                    $('#remotecontrolGrid').datagrid('reload');// reload the data
                }


            },
            error : function(data){
                $.messager.progress('close');
                $.messager.alert('错误', '请确认客户端程序是否安装！');
            }
        });
    }
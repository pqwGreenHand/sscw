var url;
$(function(){
    $('#infocollection_datagrid').datagrid({
        iconCls:'icon-datagrid',
        region: 'west',
        width:'100%',
        height:'100%',
        fitColumns:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: 'Loading...',
        method: 'get',
        url: 'getAllInfocollection.do',
        sortName: 'id',
        queryParams : {
            trefresh:new Date().getTime()
        },
        sortOrder: 'desc',
        remoteSort: false,
        idField:'id',
        singleSelect:true,
        frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'personName',title:'嫌疑人',width:100,
                formatter : function(value, row, index){
                    return value;
                }
            },
            {field:'serialNo',title:'专属编号',width:190,hidden:true},
            {field:'certificateNo',title:'证件号码',width:190},
            {field:'registerTime',title:'记录时间',width:190,
                formatter:function(value,rec){
                    return valueToDate(value);
                }
            },
            {field:'realName',title:'采集民警',width:150},
            {
                field:'caseType',
                title:'案件类型',
                width:80,
                formatter : function(value){
                    var str = "";
                    if(value==0)
                        str = "刑事";
                    if(value==1)
                        str = "行政";
                    return str;
                }
            },
            {field:'caseNature',title:'案件性质',width:150},
            {field:'areaName',title:'办案场所',width:190,hidden:true},
            {field:'操作',title:'操作',width:280,
                formatter : function(value, row, index) {
                    var e='';
                    var bcp ='';
                    if(isGridButton("showdialog3")){
                        e ='<span class="spanRow2"><a href="#" class="gridedit" onclick="showdialog3('+index+')">继续采集</a></span>';
                    }
                    /*bcp ='<span class="spanRow2"><a href="#" class="gridedit" onclick="barcodeprint('+index+')">条码打印</a></span>';*/
                    return e + bcp;
                }
            }
        ]],
        pagination:true,
        pageList: [10,20,30,40,50,100],
        rownumbers:true,
        toolbar:'#toolbar',
        //行选择方法，进行条件触发
        onSelect: function(rowIndex, rowData){
            infocollectiondetaialgridLoad(rowData);
        },
    });

    var p = $('#infocollection_datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh:function(){
            $('#infocollection_datagrid').datagrid('reload',{trefresh:new Date().getTime()} );
            return false;

        }
    });
    $('#infocollection_detaial_datagrid').datagrid({
        iconCls:'icon-datagrid',
        region: 'east',
        width:'100%',
        height:'100%',
        fitColumns:true,
        rownumbers:true,
        nowrap: false,
        striped: true,
        collapsible:false,
        loadMsg: 'Loading...',
        method: 'get',
        sortName: 'id',
        remoteSort: false,
        idField:'id',
        queryParams:"{'enpId':'-99','trefresh':'"+new Date().getTime()+"'}",
        url: 'selectDetaialByPrimaryKey.do',
        singleSelect:true,
        frozenColumns:[[
            {title:'ID',field:'id',width:80,sortable:true,hidden:true}
        ]],
        columns:[[
            {field:'collKey',title:'采集项名称',width:50},
            {field:'collValue',title:'已采集',width:40,
                formatter:function(value,row,index){
                    if(row.collValue==null){
                        return '<span style="color: #f6ff66">否</span>';
                    }else{
                        return '<span style="color: #4fff9b" >是</span>';
                    }
                }
            },
            {field:'collectTime',title:'采集时间',width:110,
                formatter:function(value,row){
                    if(row.collValue==null){
                        return "";
                    }else{
                        return valueToDate(value);
                    }
                }
            },

        ]],
        //当数据加载成功时触发
        onLoadSuccess:function(){
        } ,
        toolbar:'#toolbar1'
    });
    //新增是的下拉框显示
    $('#serialNo1').combogrid({
        panelWidth:360,
        mode: 'remote',
        url: '../../common/combogrid/getSuspectSerialNo.do?type=infocollection',
        idField: 'id',
        textField: 'name',
        columns: [[
            {field:'serialNo',title:'专属编号',width:135},
            {field:'name',title:'姓名',width:60},
            {field:'certificateNo',title:'证件号码',width:150}

        ]],
        onChange:function(newvalue,oldvalue){
            selectSerailNo(newvalue);
        }
    });

    //办案中心下拉框
    	$('#area').combobox({
    		url : '../combobox/comboArea.do',
    		valueField : 'id',
    		textField : 'name',
    		onLoadSuccess : function(data) {
    			for(var i = 0;i<data.length;i++){
    				if(ssareaid==data[i].id){
    					$('#area').combobox('setValue', data[i].name);
    				}
    			}
    		}
    	});
    //TODO hideButton('btnadd');

    var p = $('#infocollection_datagrid').datagrid('getPager');
    $(p).pagination({
        onBeforeRefresh:function(){
            $('#infocollection_detaial_datagrid').datagrid('reload',{trefresh:new Date().getTime()} );
            return false;
        }
    });

    $('#fudong').remove();

    // 扫描手环回车事件
    $('#cuffNumber').bind('keyup', function(event) {
        if (event.keyCode == "13") {
            var c =  $("#cuffNumber").val();
            if(c == null || c==""){
                $.messager.alert('提醒',"请输入手环编号");
                return;
            }
            checkRingNo($('#cuffNumber').val());
        }
    });

    function checkRingNo(icNo) {
        var cuff = {};
        cuff["icNo"] = icNo;
        cuff["type"] = 0;
        var jsonrtinfo = JSON.stringify(cuff);
        jQuery.ajax({
            type:'POST',
            contentType : 'application/json',
            url:contextPath+'/zhfz/bacs/cuff/getCuffNoByIcNoAndType.do',
            data:jsonrtinfo,
            dataType:'json',
            success:function (data) {
                if(data.error){
                    $.messager.alert(data.title,data.content);
                    $("#cuffNumber").val("");
                    return;
                }
                if(data.callbackData.status == 0 || data.callbackData.status == 3){
                    $.messager.alert(data.title,data.content);
                    $("#cuffNumber").val("");
                    return;
                }
                // 根据icNo将查出来 显示到输入框
                $("#cuffNumber").val(data.callbackData.cuffNo);
                readRingNo(data.callbackData.cuffNo);
            },
            error:function (data) {
                $.messager.alert('错误', '未知错误!');
                $('#cuffNumber').val('');
            }
        })
    }
    
    // 手环扫描
    function  readRingNo(number) {
        $('#serialNo1').combogrid({
            panelWidth:360,
            mode: 'remote',
            url: '../../common/combogrid/getSuspectSerialNo.do?type=infocollection',
            idField: 'id',
            textField: 'name',
            columns: [[
                {field:'serialNo',title:'专属编号',width:135},
                {field:'name',title:'姓名',width:60},
                {field:'certificateNo',title:'证件号码',width:150}

            ]],
            onChange:function(newvalue,oldvalue){
                selectSerailNo(newvalue);
            }
        });

        if(number){
            var cuff = {};
            cuff["cuffNo"]=number;
            cuff["type"]=0;
            var jsonrtinfo = JSON.stringify(cuff);
            jQuery.ajax({
                type : 'POST',
                contentType : 'application/json',
                url : contextPath+'/zhfz/bacs/serial/getSerialByCuffNo.do',
                data : jsonrtinfo,
                dataType : 'json',
                success : function(data){
                    var id=data.callbackData?data.callbackData.id:null;
                    if(id!=null&&id!=""){
                        $('#cuffNumber').val( data.callbackData.cuffNo);
                        $('#serialNo1').combogrid('setValue',id);
                    }else{
                        $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                        $('#cuffNumber').val('');
                    }
                },
                error : function(data){
                    $.messager.alert('错误', '未知错误!');
                    $('#cuffNumber').val('');
                }
            });
        }else{
            alert("读卡失败！");
            $('#cuffNumber').val('');
        }
    }


});


//点击一行显示右边信息
function infocollectiondetaialgridLoad(rowData)
{
    $('#infocollection_detaial_datagrid').datagrid('load',{id:rowData.id,
        trefresh:new Date().getTime()
    });
}
function selectSerailNo(id){
    if(id==""||id==null){

    }else{
        jQuery.ajax({
            type : 'post',
            contentType : 'application/json',
            url : 'exitCollectionById.do?id='+id,
            data : '{"id":'+id+'}',
            dataType : 'json',
            success : function(data){

                jQuery.ajax({
                    type : 'post',
                    url : 'listAllInfoCollectionDetail.do',
                    data : 'infoCollectionId='+data,
                    dataType : 'json',
                    success : function(data){
                        var html = '';
                        for(var i=0;i<data.rows.length;i++){
                            if((i+1)%4==1){
                                html += '<tr>';
                            }
                            html +=
                                '<td width="120px" height="50px">'+
                                '<input type="checkbox"';
                            if(data.rows[i].collValue==null){
                                html+=' id="" name="caiji1" value="'+data.rows[i].collKey+'" style="align:left;font-size:30px;" />'+'<span style="font-size:14px;" >'+data.rows[i].collKey+'</span>'+
                                    '</td>';
                            }else{
                                html+=' onclick="return    false;" checked="checked" id="" name="caiji1" value="'+data.rows[i].collKey+'" style="align:left;font-size:30px;" />'+'<span style="font-size:14px;" >'+data.rows[i].collKey+'</span>'+
                                    '</td>';
                            }
                            if((i+1)%4==0){
                                html += '</tr>';
                            }
                        }
                        html+='</tr>';

                        $('#caiji_detaial_datagrid_new').html(html);
                    }
                })
            },
            error : function(data){
                $.messager.progress('close');
                $.messager.alert('错误', '未知错误!');
            }
        });
    }

}

function editinfocollection(index){
    $('#infocollection_form').form('clear');
    var row = $('#infocollection_datagrid').datagrid('getRows')[index];
    if (row){
        showDialog('#infocollection_dialog','修改采集信息');
        $('#infocollection_form').form('load',row);
        url = 'updateByPrimaryKeySelective.do';

    } else{
        $.messager.alert('提示', '请选择一行!');
    }
}

function deleteinfocollection(index){
    var row = $('#infocollection_datagrid').datagrid('getRows')[index];
    $.messager.confirm('删除确认','是否确定删除此数据？',function(r){
        if (r){
            $.messager.progress({
                title:'请等待',
                msg:'删除数据中...'
            });
            jQuery.ajax({
                type : 'POST',
                contentType : 'application/json',
                url : 'deleteInfocollection.do',
                data : '{"id":'+row.id+'}',
                dataType : 'json',
                success : function(data){
                    $.messager.alert(data.title, data.content);
                    $('#infocollection_datagrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
                    $.messager.progress('close');
                },
                error : function(data){
                    $.messager.progress('close');
                    $.messager.alert('错误', '未知错误!');
                }
            });
        }
    });

}

//信息采集页搜索
function doSearch() {
    $('#infocollection_datagrid').datagrid('load', {
        personName : $('#personName').val(),
        certificateNo : $('#certificateNo').val(),
        serialNo : $('#serialNo').val(),
        areaId : $('#area').combobox('getValue'),
        trefresh:new Date().getTime()

    });
}
//信息采集页搜索清空
function doClear() {
    $('#personName').prop('value','');
    $('#certificateNo').prop('value','');
    $('#serialNo').prop('value','');
    $('#area').combobox('setValue','');
}


//查询该专属编号是否已存在
function showdialog3(index){
    $('#caiji_detaial_datagrid1').html('');
    $('#infocollection_form2').form('clear');
    var row = $('#infocollection_datagrid').datagrid('getRows')[index];
    $('#serialNo2').prop('value',row.serialNo);
    var serialNo=  $('#serialNo2').val();
    showDialog('#infocollection_dialog3','信息采集');
    jQuery.ajax({
        type : 'POST',
        /*contentType : 'application/json',*/
        url : 'listAllInfoCollectionDetailById.do?id='+row.id,
        //data : '{"id":'+row.id+'}',
        dataType : 'json',
        success : function(data){
            var html = '';
            for(var i=0;i<data.rows.length;i++){
                if((i+1)%4==1){
                    html += '<tr>';
                }
                html +=
                    '<td width="120px" height="50px">'+
                    '<input type="checkbox"';
                if(data.rows[i].collValue==null){
                    html+=' id="" name="caiji" value="'+data.rows[i].collKey+'" style="align:left" />'+'<span style="font-size:14px;" >'+data.rows[i].collKey+'</span>'+
                        '</td>';
                }else{
                    html+=' onclick="return    false;" checked="checked" id="" name="caiji" value="'+data.rows[i].collKey+'" style="align:left" />'+'<span style="font-size:14px;" >'+data.rows[i].collKey+'</span>'+
                        '</td>';
                }
                if((i+1)%4==0){
                    html += '</tr>';
                }
            }
            html+='</tr>';
            $('#caiji_detaial_datagrid1').html(html);

        },
        error : function(data){
            return;
        }
    });

}
function addinfocollection(){
    $('#serialNo1').prop('value','');
    $('#caiji_detaial_datagrid_new').html('');
    $('#infocollection_form1').form('clear');
    showDialog('#infocollection_dialog1','采集信息');
    document.getElementById("serialNo1").focus();
}
function saveData(){
    var serialNo = $('#serialNo1').combogrid('grid');	// 获取数据表格对象
    var row = serialNo.datagrid('getSelected');
    if($('#serialNo1').val()==''){
        return;
    }
    var infoCollectionId=null;
    jQuery.ajax({
        type : 'POST',
        url : 'exitCollectionBySerialNo.do?serialId='+row.id,
        dataType : 'json',
        async: false ,
        success : function(data){
            infoCollectionId=data;
            //复选框
            var n=0;
            var obj = document.getElementsByName("caiji1");
            var ids=null;
            for(var i=0;i<obj.length;i++){
                if(obj[i].checked==true){
                    if(ids==null){
                        ids=obj[i].value;
                    }else{
                        ids=ids+","+obj[i].value;
                    }
                }
            }
            if(ids==null){
                $('#infocollection_dialog1').dialog('close');
                return;
            }

            jQuery.ajax({
                type : 'POST',
                url : 'updateDeatils.do?ids='+encodeURI(ids,'utf-8')+'&infoCollectionId='+data,
                dataType : 'json',
                async: false ,
                success : function(data){
                    datasuccess=data;
                    $('#infocollection_dialog1').dialog('close');
                    $('#infocollection_dialog3').dialog('close');
                    $('#infocollection_datagrid').datagrid('reload',{trefresh:new Date().getTime()});
                    $('#infocollection_detaial_datagrid').datagrid('load',{id:infoCollectionId});
                },

                error : function(data){
                    $('#infocollection_dialog1').dialog('close');
                    $.messager.alert('错误', '未知错误'+data);
                }
            });
        },
        error : function(data){
            $.messager.progress('close');
            $('#infocollection_dialog1').dialog('close');
            $.messager.alert('错误', '新增所需信息不全（需有：嫌疑人、办案场所、专属编号、嫌疑人涉及案件）!');
        }
    });

}
function saveData1(){
    var infoCollectionId=null;
    var serialNo=null;
    var row = $('#infocollection_datagrid').datagrid('getSelected');
    if(row!=null){
        infoCollectionId=row.id;
    }else{
        serialNo=  $('#serialNo1').val();
    }
    var n=0;
    var obj = document.getElementsByName("caiji");
    var ids=null;
    for(var i=0;i<obj.length;i++){
        if(obj[i].checked==true){
            if(ids==null){
                ids=obj[i].value;
            }else{
                ids=ids+","+obj[i].value;
            }
        }
    }
    if(ids==null){
        $('#infocollection_dialog1').dialog('close');
        $('#infocollection_dialog2').dialog('close');
        return;
    }
    var data=null;
    if(infoCollectionId==null&&serialNo!=null){
        data='&serialNo='+serialNo;
    }
    if(serialNo==null&&infoCollectionId!=null){
        data='&infoCollectionId='+infoCollectionId;
    }
    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : 'updateDeatils.do?ids='+encodeURI(ids,'utf-8')+data,
        dataType : 'json',
        success : function(data){
            $.messager.alert(data.title, data.content);
            $('#infocollection_detaial_datagrid').datagrid('reload',{trefresh:new Date().getTime()});
            $('#infocollection_dialog1').dialog('close');
            $('#infocollection_dialog2').dialog('close');
            $('#infocollection_dialog3').dialog('close');
            $('#infocollection_detaial_datagrid').datagrid('load',{id:infoCollectionId});
        },
        error : function(data){
            $('#infocollection_dialog1').dialog('close');
            $.messager.alert('错误', '未知错误!'+data);
        }
    });
    $('#infocollection_datagrid').datagrid('reload',{trefresh:new Date().getTime()} );// reload the data
}






//手环扫描
var strls = "";
var errorno = "";


var BLOCK0_EN = 0x01;//读第一块的(16个字节)
var BLOCK1_EN = 0x02;//读第二块的(16个字节)
var BLOCK2_EN = 0x04;//读第四块的(16个字节)
var NEEDSERIAL = 0x08;//仅读指定序列号的卡
var EXTERNKEY = 0x10;//用明码认证密码,产品开发完成后，建议把密码放到设备的只写区，然后用该区的密码后台认证，这样谁都不知道密码是多少，需要这方面支持请联系
var NEEDHALT = 0x20;//读/写完卡后立即休眠该卡，相当于这张卡不在感应区。要相重新操作该卡必要拿开卡再放上去

var myctrlword = 0;
var myareano = 0;
var authmode = 0;


var mypiccserial = "";

var mypicckey = "";
var piccdata0_2 = "";

var mypicckey_old = "";//旧密码
var mypicckey_new = "";//新密码
function readRingNo(){
    var number2="";
    //指定控制字
    myctrlword=BLOCK0_EN + BLOCK1_EN + BLOCK2_EN + EXTERNKEY;
    //指定区号
    myareano = 1; //指定为第8区
    //批定密码模式
    authmode = 1; //大于0表示用A密码认证，推荐用A密码认证

    //指定序列号，未知卡序列号时可指定为8个0
    mypiccserial="00000000";

    //指定密码，以下密码为厂家出厂密码
    mypicckey = "ffffffffffff";

    strls=IcCardReader.piccreadex(myctrlword, mypiccserial,myareano,authmode,mypicckey);
    errorno = strls.substr(0,4);


    switch(errorno){
        case "ER08":
            alert("寻不到卡08");
            break;
        case "ER09":
            alert("寻不到卡09");
            break;
        case "ER10":
            alert("寻不到卡10");
            break;

        case "ER11":
            CardIDShower.value = "密码认证错误\r\n";
            CardIDShower.value = CardIDShower.value + strls + "\r\n";
            CardIDShower.value = CardIDShower.value + "其中错误号为：" + errorno + "\r\n";
            CardIDShower.value = CardIDShower.value + "卡十六进制序列号为：" + strls.substr(5,8) + "\r\n";
            alert("密码认证错误11");
            break;
        case "ER12":
            CardIDShower.value = "密码认证错误" + "\r\n";
            CardIDShower.value = CardIDShower.value + strls + "\r\n";
            CardIDShower.value = CardIDShower.value + "其中错误号为：" + errorno + "\r\n";
            CardIDShower.value = CardIDShower.value + "卡十六进制序列号为：" + strls.substr(5,8) + "\r\n";
            alert("密码认证错误12");
            break;
        case "ER13":
            CardIDShower.value = "读卡错误" + "\r\n";
            CardIDShower.value = CardIDShower.value + strls + "\r\n";
            CardIDShower.value = CardIDShower.value + "其中错误号为：" + errorno + "\r\n";
            CardIDShower.value = CardIDShower.value + "卡十六进制序列号为：" + strls.substr(5,8) + "\r\n";
            alert("读卡错误12");
            break;

        case "ER14":
            CardIDShower.value = "写卡错误" + "\r\n";
            CardIDShower.value = CardIDShower.value + strls + "\r\n";
            CardIDShower.value = CardIDShower.value + "其中错误号为：" + errorno + "\r\n";
            CardIDShower.value = CardIDShower.value + "卡十六进制序列号为：" + strls.substr(5,8) + "\r\n";
            alert("写卡错误14");
            break;

        case "ER21":
            alert("没找到动态库21");
            break;

        case "ER22":
            alert("动态库或驱动程序异常22");
            break;

        case "ER23":
            alert("读卡器未插上或动态库或驱动程序异常23");
            break;
        case "ER24":
            alert("操作超时，一般是动态库没有反应24");
            break;
        case "ER25":
            alert("发送字数不够25");
            break;
        case "ER26":
            alert("发送的CRC错26");
            break;
        case "ER27":
            alert("接收的字数不够27");
            break;
        case "ER28":
            alert("接收的CRC错28");
            break;
        case "ER29":
            alert("函数输入参数格式错误,请仔细查看29"	);
            break;
        default :
            IcCardReader.pcdbeep(100);//100表示响100毫秒
            //读卡成功,其中ER00表示完全成功,ER01表示完全没读到卡数据，ER02表示仅读该卡的第一块成功,，ER02表示仅读该卡的第一二块成功，这是刷卡太快原因
            //CardIDShower.value = "读卡成功" + "\r\n";
            //CardIDShower.value = CardIDShower.value + strls + "\r\n";
            //CardIDShower.value = CardIDShower.value + "其中错误号为：" + errorno + "\r\n";
            //CardIDShower.value = CardIDShower.value + "卡十六进制序列号为：" + strls.substr(5,8) + "\r\n";
            //CardIDShower.value = CardIDShower.value + "该区第一块十六进制数据为：" + strls.substr(14,32) + "\r\n";
            var number1=strls.substr(14,32);
            number2=number1.substr((number1.length-4),number1.length);
            //alert("读卡成功parseInt= "+parseInt(number2,10));
            //CardIDShower.value = CardIDShower.value + "该区第二块十六进制数据为：" + strls.substr(46,32) + "\r\n";
            //CardIDShower.value = CardIDShower.value + "该区第三块十六进制数据为：" + strls.substr(78,32) + "\r\n";
            break;
    }
    if(number2!=""){
        //alert("读卡成功！number2= "+number2);
        var cuff = $('#cuffInfo').serializeObject();
        cuff["cuffNo"]=number2;
        var jsonrtinfo = JSON.stringify(cuff);
        //alert("读卡成功！jsonrtinfo= "+jsonrtinfo);
        jQuery.ajax({
            type : 'POST',
            contentType : 'application/json',
            url : '../entrance/getSerialByCuffNo.do',
            data : jsonrtinfo,
            dataType : 'json',
            success : function(data){
                //$.messager.alert(data.title, data.content);
                var id=data.callbackData.id;
                if(id!=null&&id!=""){
                    $('#cuffNumber').val( data.callbackData.cuffNo);
                    $('#serialNo1').combogrid('setValue',id);
                }else{
                    $.messager.alert('提醒', '该手环未绑定嫌疑人专属编号!');
                }
            },
            error : function(data){
                $.messager.alert('错误', '未知错误!');
            }
        });
    }else{
        alert("读卡失败！");
    }
}


function barcodeprint(index){
    var row = $('#infocollection_datagrid').datagrid('getRows')[index];
    //办案中心名称
    var an =row.areaName;
    //嫌疑人姓名
    var pn =row.personName;
    //案由
    var cn =row.caseName;
    //条码值
    var co =row.serialNo;
    //打印机名称
    var printerName ="ZDesigner GT800 (EPL)";
    //端口
    var printerPort ="USB001)";

    jQuery.ajax({
        type : 'POST',
        contentType : 'application/json',
        url : '../commonconfig/getbarcodeprinterconfig.do',
        dataType : 'json',
        success : function(data){
            if(data.callbackData!=null){
                printerName =data.callbackData.barCodePrinterName;
                printerPort =data.callbackData.barCodePrinterPort;
                print_epl.Open_Port(printerPort);
                print_epl.Begin_Job("2", "12", "False", "B");
                print_epl.Print_Winfont(200, 15, "办案中心名称:"+an, "宋体", 20, 12, "True", "False", "False", "False", "False");
                print_epl.Print_Winfont(200, 45, "嫌疑人姓名:"+pn, "宋体", 20, 12, "True", "False", "False", "False", "False");
                print_epl.Print_BarCode(200, 80, co, "1", "60", "2", "6", "B", "0");
                var caseInfo=cn;
                if(caseInfo.length>50){
                    caseInfo=caseInfo.substr(0,50)
                }
                if(caseInfo.length>0 && caseInfo.length<=25){
                    print_epl.Print_Winfont(200, 170, caseInfo, "宋体", 20, 8, "True", "False", "False", "False", "False");
                }else if(caseInfo.length>25 && caseInfo.length<=50){
                    caseInfo1 = caseInfo.substr(0,25);
                    caseInfo2 = caseInfo.substr(25);
                    print_epl.Print_Winfont(200, 170, caseInfo1, "宋体", 20, 8, "True", "False", "False", "False", "False");
                    print_epl.Print_Winfont(200, 200, caseInfo2, "宋体", 20, 8, "True", "False", "False", "False", "False");
                }

                print_epl.End_Job();
                print_epl.Close_Port();
                print_epl.Printing_USBPORT(printerName);
            }
        },
        error : function(data){
            $.messager.alert(data.title, data.content);
        }
    });
}

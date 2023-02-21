<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!--关联警综案件信息弹出框 -->
<div id="link_jzAjxx_dialog" class="m-popup m-info-popup" style="z-index: 25">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="width: 1000px;height: 550px;">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>警综案件信息</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#link_jzAjxx_dialog').hide();"></a>
            </div>
            <div id="linkjzAjxxDiv"  class="bd" style="margin-top: 50px;width: 98%;height: 80%" align="center">
                <table id="link_jzAjxx_datagrid" style="height: 60%"></table>
                <input type="hidden" id="jzAjxxId" name="jzAjxxId"/>
                <!-- toolbar -->
                <div id="jzAjxxToolbar" style="padding:5px;height:auto">
                    <div>
                        案件名称: <input name="case_name" id="caseName1" class="easyui-textbox" style="width:150px;height: 30px"/>
                        <%--主办单位: <select style="width:150px;height: 30px" editable="true" id="org_cmb1"
                                      name="org_cmb" class="easyui-combobox">
                                  </select>--%>
                        案件编号: <input name="ajbh" id="s_ajbh" class="easyui-textbox" style="width:150px;height: 30px"/>

                        案件类别： <select style="width:150px;height: 30px" editable="true" id="s_ajlb"
                                      name="ajlb" class="easyui-combobox">
                        <option value="1">行政</option>
                        <option value="2">刑事</option>
                    </select>
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-search" onclick="doSearchLinkCase()">查询</a>
                        <a href="#" class="easyui-linkbutton" name="noButton" iconCls="icon-clear" onclick="doClearLinkCase()">清除</a>
                    </div>
                </div>

                <table id="link_jzAjxx_rY_datagrid" style="height: 40%"></table>


                <%--<div class="next" style="margin-top: 50px">
                    <button class="m-btn-1 blue" onclick="linkJzAjxxSave()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">保存</button>
                    <button class="m-btn-1 blue" onclick="javaScript:$('#link_jzAjxx_dialog').hide();" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">关闭</button>
                </div>--%>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>

<script type="text/javascript">
    var jzAjlyCode = 0;
    function loadLinkJzAjxxList() {
        jzAjlyCode = 0;
        $('#link_jzAjxx_datagrid').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            width: '100%',
            height: '80%',
            fitColumns: true,
            striped : true,
            nowrap: false,
            collapsible: false,
            loadMsg: '加载中.....',
            method: 'get',
            url: '../../common/case/listJzAjxx.do',
            queryParams: {
                trefresh: new Date().getTime(),
                ajlb:1
            },
            sortName: 'id',
            sortOrder: 'id',
            remoteSort: false,
            idField: 'id',
            singleSelect: true,
            frozenColumns: [[
                {title: 'ID', field: 'id', width: 20, sortable: true, hidden: true},
                {title: 'ajbh', field: 'ajbh', width: 20, sortable: true, hidden: true},
                {title: 'bmId', field: 'bmId', width: 20, sortable: true, hidden: true}
            ]],
            columns: [[
                {field: 'ajmc', title: '案件名称', width: 35},
                {field: 'ajbh', title: '案件编号', width: 35},
                {field: 'badw', title: '主办单位', width: 15},
                {field: 'afsj', title: '案发时间', width: 12,
                    formatter : function(value, rec) {
                        return valueToDate(value);
                    }
                },
                {field: 'ajlb', title: '案件类型',width: 10,
                    formatter:function(value,rec){
                        if(value==2){
                            return '<font color="#DAA520">刑事</font>';
                        }else if(value==1){
                            return '<font color="#00EEEE">行政</font>';
                        }else {
                            return '<font color="yellow">警情</font>';
                        }
                    }},
                /*{field: 'caseNature', title: '案件性质', width: 13},*/
                {field: 'zbmjName', title: '主办民警', width: 10},
                /*{field: 'xbmjName', title: '协办民警', width: 20}*/
            ]],
            pagination: true,
            onLoadSuccess: function(data){

            },
            pageList: [10, 20, 30, 40, 50, 100],
            rownumbers: true,
            toolbar: '#jzAjxxToolbar',
            onClickRow:function(index,row){
                listRy(row.ajbh);
            }
        });
        var caseP = $('#link_jzAjxx_datagrid').datagrid('getPager');
        $(caseP).pagination({
            onBeforeRefresh: function () {
                $('#link_jzAjxx_datagrid').datagrid('load', {
                    trefresh: new Date().getTime()
                });
                return false;
            }
        });
        /*$("#org_cmb1").combobox({
            url : '../../common/combobox/listAllOrganizationNameCombobox.do',
            valueField : 'id',
            textField : 'value',
        });*/


    }


    // 案件关联人员信息
    function listRy(ajbh) {
        $('#link_jzAjxx_rY_datagrid').datagrid({
            iconCls: 'icon-datagrid',
            region: 'center',
            width: '100%',
            height: '80%',
            fitColumns: true,
            striped : true,
            nowrap: false,
            collapsible: false,
            loadMsg: '加载中.....',
            method: 'get',
            url: '../../common/case/listRy.do?ajbh='+ajbh,
            queryParams: {
                trefresh: new Date().getTime()
            },
            sortName: 'id',
            sortOrder: 'id',
            remoteSort: false,
            idField: 'id',
            singleSelect: true,
            frozenColumns: [[
                {title: 'ID', field: 'id', width: 20, sortable: true, hidden: true},
                {title: 'ajbh', field: 'ajbh', width: 20, sortable: true, hidden: true},
                {title: 'bmId', field: 'bmId', width: 20, sortable: true, hidden: true}
            ]],
            columns: [[
                {field: 'xm', title: '姓名', width: 10},
                {field: 'xb', title: '性别', width: 10,
                    formatter : function(value, rec) {
                        if(value == 2){
                            return "女";
                        } else {
                            return "男";
                        }
                    }
                },
                {field: 'zjhm', title: '证件号码', width: 35},
                {field: 'rybh', title: '人员编号', width: 35},
                {field: 'ajbh', title: '案件编号',width: 35},

            ]],
            pagination: true,
            onLoadSuccess: function(data){

            },
            pageList: [10, 20, 30, 40, 50, 100],
            rownumbers: true,

            onDblClickRow:function(row,index){
                $('#link_jzAjxx_dialog').hide();
                $('#link_case_ajmc').val(index.rybh);
               // showDialog('#jzDialog', '警综信息');
               // $('#jzRybh').val(index.rybh);
               // console.log(index);
                $('#jz_ajbh').val(ajbh);
                $('#jzRybh').val(index.rybh);
            }
        });
        var caseP = $('#link_jzAjxx_rY_datagrid').datagrid('getPager');
        $(caseP).pagination({
            onBeforeRefresh: function () {
                $('#link_jzAjxx_rY_datagrid').datagrid('load', {
                    trefresh: new Date().getTime()
                });
                return false;
            }
        });

    }


    //打开关联警综案件弹框
    function linkJzAjxx(){
        $("#caseName1").textbox('setValue','');
        $("#s_ajbh").textbox('setValue','');
        $("#link_jzAjxx_dialog").show();
        loadLinkJzAjxxList();
        if($('#link_jzAjxx_datagrid')) {
            $('#link_jzAjxx_datagrid').datagrid({
                onLoadSuccess: function (data) {
                    if($("#jzAjxxId")){
                        var jzAjxxId = $("#jzAjxxId").val();
                        if (jzAjxxId != null && jzAjxxId != '' && data != null && data.rows != null && data.rows.length != 0) {
                            for (var i=0;i<data.rows.length;i++){
                                if(jzAjxxId == data.rows[i].id){
                                    $('#link_jzAjxx_datagrid').datagrid("selectRow", i);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    //关联案件保存
    function linkJzAjxxSave(){
        var erow = $('#link_jzAjxx_datagrid').datagrid('getSelected');
        if(erow==null) {
            $.messager.alert('提示', '请先选择一行!');
            return;
        } else{
            $("#zbmjCertificateNo").val(erow.zbmj);
            $('#zbmjName').val(erow.zbmjName);
            initAjlx('AJLX',erow.ajlb);
            if(erow.ajlb==2){
                initAb('刑事案件');
            }else if(erow.ajlb==1){
                initAb('行政案件');
            }
            $('#zbdwId').combobox('setValue',erow.bmId);
            $("#afsj").datetimebox('setValue', formatter2(erow.afsj));
            $("#jqh").val(erow.jqbh);
            $("#involvedAddress").val(erow.afdd);
            $("#zyaq").val(erow.jyaq);
            $('#jzAjxx_ajmc').val(erow.ajmc);
            $('#jzAjxx_ajbh').val(erow.ajbh);
            $('#jzAjxx_ajzt').val(erow.ajzt);
            jzAjlyCode = 101;
            $("#link_jzAjxx_dialog").hide();
        }
    }

    //查询警综案件
    //var ajlb = 1
    function doSearchLinkCase(){
        ajlb  = $("#s_ajlb").combobox('getValue');

        //alert(ajlb);
        $("#link_jzAjxx_datagrid").datagrid("reload",{
            trefresh:new Date().getTime(),
            ajmc: $("#caseName1").textbox("getValue"),
            //bmId:$("#org_cmb1").combobox("getValue"),
            ajbh:$('#s_ajbh').val(),
            ajlb:ajlb
        });
    }
    //清除警综查询条件
    function doClearLinkCase(){
        $("#caseName1").textbox("setValue",'');
        $("#org_cmb1").combobox("setValue","");
        $("#s_ajbh").textbox("setValue",'');
    }

    function formatter2(date,ss) {
        ss = ss || 'yyyy-MM-dd hh:mm:ss';
        if(date == null)
            return '--/--';
        if(typeof date === 'number')
            date = new Date(date);
        else if(date.getTime() == 0)
            return '--/--';
        return date.formatDate(ss);
    }

    function initAb(natureValue) {
        $('#ab').combobox({
            url : '../../bacs/combobox/listcrimetypebynature.do?nature=' +encodeURI(natureValue,"UTF-8"),
            valueField : 'id',
            textField : 'value'
        });
    }

    function initAjlx(type,selectedId){
        $('#ajlx').combobox({
            url:contextPath+"/zhfz/common/code/listCodeByType.do?type="+type,
            valueField:'codeKey',
            textField:'codeValue',
            onLoadSuccess : function() {
                if(selectedId){
                    $('#ajlx').combobox('setValue', selectedId);
                }
            }
        });
    }
</script>
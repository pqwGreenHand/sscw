<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<%@ include file="../../../common/common-head.jsp"%>
</head>
<body style="background-color: #082040">
	<table>
        <tr>
            <td>
                <div id="logobox" class="easyui-draggable" data-options="handle:'#title'"
                     style="margin-left:120px;margin-top:30px;width:725px;height:650px;background:url(../../security/image/demo4.jpg) no-repeat 0px 0px;;position:relative">
                <div id="datasTests" name="contents"  contentEditable="true" onclick="delData(0)" style="display:inline;margin:80px 0px 0px 60px;border:1px solid #8B0000;width:120px;height:50px;position:absolute">伤情描述</div>
                </div>
                <div style="float:left">
                <form id="person_font_form" method="post" class="form-style">
                    <div id="personform">
                        <input name="content" type="hidden"  id="content"/>
                        <input name="textTop" type="hidden"  id="textTop"/>
                        <input name="textLeft" type="hidden"  id="textLeft"/>
                        <input name="logoboxWidth" type="hidden"  id="logoboxWidth"/>
                        <input name="logoboxHeight" type="hidden"  id="logoboxHeight"/>
                        <input name="uuid" type="hidden"  id="uuid"/>
                        <input name="areaId" type="hidden"  id="areaId"/>
                        <input name="count" type="hidden"  id="count"/>
                    </div>
                </form>
            </div></td>
            <td>
                <div style="margin-right:250px;margin-bottom:20px;">
                    <button name="noButton" onclick="addData()"  class="easyui-linkbutton" iconCls="icon-ok" style="width:80px;height:40px">添加</button>
                    <button name="noButton" onclick="setData()"  class="easyui-linkbutton" iconCls="icon-save" style="width:80px;height:40px">保存</button>
                    <button name="noButton" onclick="removeData()"  class="easyui-linkbutton" iconCls="icon-remove" style="width:80px;height:40px">删除</button>
                    <input name="deldata" type="hidden"  id="deldata"/>
                    <input name="starts" type="hidden" value="0"  id="starts"/>
                </div></td>
        </tr>
    </table>
    <script type="text/javascript">
        var afterUrl =  window.location.search.substring(1);
        var afterUrl2 =  window.location.search.substring(2);
        var afterUrl3 =  window.location.search.substring(3);
        var afterEqualUrl = afterUrl.substring(afterUrl.indexOf('uuid=')+5,afterUrl2.indexOf('areaId='));
        var afterEqualUrl2 = afterUrl2.substring(afterUrl2.indexOf('areaId=')+7,afterUrl3.indexOf('count='));
        var afterEqualUrl3 = afterUrl3.substring(afterUrl3.indexOf('count=')+6);
        console.log(afterEqualUrl)
        console.log(afterEqualUrl2)
        console.log(afterEqualUrl3)
        $(document).ready(function() {
            var divTitle=$('#datasTests');
            $('#datasTests').draggable();
            divTitle.draggable().click(function (){
                $(this).draggable({ disabled: false });
                $(this).css('backgroundColor', 'transparent');
            }).dblclick(function (){
                $(this).draggable({ disabled: true });
                $(this).css('backgroundColor', '#FFFF6F');
            });
        });

        var ids=0;
        var idarr=[];
        function addData(){
            ids++;
            idarr.push(ids);
            var te = '<div id="datasTests'+ids+'" name="contents"  onclick="delData('+ids+')" onfocus="editData('+ids+')" contentEditable="true" style="margin:1'+ids+'0px 0px 0px 60px;border:1px solid #8B0000;width:120px;height:50px;position:absolute"></div>';
            $("#logobox").append(te);
            var divTitle=$('#datasTests'+ids);
            $('#datasTests'+ids).draggable();
            divTitle.draggable().click(function (){
                $(this).draggable({ disabled: false });
                $(this).css('backgroundColor', 'transparent');
            }).dblclick(function ()
            {
                $(this).draggable({ disabled: true });
                $(this).css('backgroundColor', '#FFFF6F');
            });
        }

        function parentX(elem) {
            /**
             * 如果offsetParent是元素的父亲,那么提前提出
             * 否则,我们需要找到元素和元素的父亲相对于整个页面位置，并计算他们之间的差
             * */
            return elem.parentNode == elem.offsetParent ? elem.offsetLeft : pageX(elem) - pageX(elem.parentNode);
        }
        function parentY(elem) {
            /**
             * 如果offsetParent是元素的父亲,那么提前提出
             * 否则,我们需要找到元素和元素的父亲相对于整个页面位置，并计算他们之间的差
             * */
            return elem.parentNode == elem.offsetParent ? elem.offsetTop : pageY(elem) - pageY(elem.parentNode);
        }

        function setData() {
            var as = $("#datasTests");
            var ai = $("#starts").val();
            if(ai!=1){
                $("#content").val(as.text());
                $("#textTop").val((as.offset().top-20));
                $("#textLeft").val((as.offset().left-250));
                var content = $("#content").val();
                if (content.length==0){
                    alert("伤情描述不能为空!");
                    return false;
                }
            }
            for(var i=0;i<idarr.length;i++){
                var id = i+1;
                var teh = '<input name="content'+id+'" type="hidden"  id="content'+id+'"/><input name="textTop'+id+'" type="hidden"  id="textTop'+id+'"/><input name="textLeft'+id+'" type="hidden"  id="textLeft'+id+'"/>'
                $("#personform").append(teh);
                $("#content"+id).val($("#datasTests"+idarr[i]).text());
                $("#textTop"+id).val($("#datasTests"+idarr[i]).offset().top-20);
                $("#textLeft"+id).val($("#datasTests"+idarr[i]).offset().left-250);
                var contents = $("#content"+id).val();
                if (contents.length==0){
                    alert("伤情描述不能为空!");
                    return false;
                }
            }
            $("#uuid").val(afterEqualUrl)
            $("#areaId").val(afterEqualUrl2)
            $("#count").val(afterEqualUrl3)
            $("#logoboxWidth").val($("#logobox").width());
            $("#logoboxHeight").val($("#logobox").height());
            var orgForm = $('#person_font_form');
            var organizationinfo = JSON.stringify(orgForm.serializeObject());
            $.messager.progress({
                title:'请等待',
                msg:'添加/修改数据中...'
            });
            jQuery.ajax({
                type : 'POST',
                contentType : 'application/json',
                url : "../insertPersonData.do?ids="+ids+"&starts="+ai,
                data : organizationinfo,
                dataType : 'json',
                success : function(_data){
                    $.messager.progress('close');
                    var d = _data.callbackData;
                    var dd =d.split("###");
                    var obj = new Object();
                    obj.texts=dd[0];
                    obj.img = dd[1];
                    window.returnValue = obj;
                    window.close();
                    window.opener.document.getElementById("bodyTag").value = '1';
                    window.opener.testfun();
                },
                error : function(_data){
                    $.messager.progress('close');
                    $.messager.alert('错误', '错误信息!'+_data);
                }
            });
        }
        function delData(ids){
            $("#deldata").val(ids);
        }
        function removeData(){
            var ids = $("#deldata").val();
            if(ids==0){
                $("#datasTests").remove();
                $("#starts").val(1);
            }else{
                $("#datasTests"+ids).remove();

                for(var i=0; i< idarr.length;i++){
                    if(idarr[i]==ids){
                        idarr.splice(i,1);//从start的位置开始向后删除delCount个元素
                    }
                }
            }
        }
    </script>
</body>
</html>
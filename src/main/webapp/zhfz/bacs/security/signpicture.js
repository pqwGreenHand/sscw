// 预约信息添加start
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
            $("#textTop").val((as.offset().top-47));
            $("#textLeft").val((as.offset().left-610));
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
            $("#textTop"+id).val($("#datasTests"+idarr[i]).offset().top-47);
            $("#textLeft"+id).val($("#datasTests"+idarr[i]).offset().left-610);
            var contents = $("#content"+id).val();
            if (contents.length==0){
                alert("伤情描述不能为空!");
                return false;
            }
        }
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
            url : "insertPersonData.do?ids="+ids+"&starts="+ai,
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
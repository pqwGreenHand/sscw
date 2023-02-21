$(function(){
	select("xzdw");
	select("tjdw");
})


function select(dw){
	$.ajax({
        url: '../../../evaluation/evaluation/selectAllInterrogateArea.do',
        type: "get",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        traditional: true,
        success: function (data) {
            var optionstring = "<option hassubinfo=\"true\" value=\"\" > 请选择单位 </option>";
            for (var i = 0; i < data.length; i++) {
                optionstring += "<option hassubinfo=\"true\" value=\"" + data[i].id + "\" >" + data[i].value + "</option>";
            }
            $("#"+dw).html(optionstring);
        }
    });
}

function ry(ry){
	sessionStorage.ry = ry;
	if(ry==1){
		sessionStorage.dw = $("#tjdw").val();
	}else{
		sessionStorage.dw = $("#xzdw").val();
	}
	parent.zeroModal.show({
        unique:'ry',
        title: '人员选择',
        iframe: true,
        url: 'docinput/ry.jsp',
        width: '1000px',
        height: '80%',
        forbidBodyScroll:true,
        buttons: [{
            className: 'btn btn-success btn-sm',
            name: '确定',
            fn: function(opt) {
            	if(ry==1){
            		if($("#tjr").val()!=""||$("#tjr").val()!=null){
            			$("#tjr").val($("#tjr").val()+"、"+sessionStorage.docname);
            		}else{
            			$("#tjr").val(sessionStorage.docname);
            		}
            	}
            	if(ry==2){
            		if($("xzr").val()!=""||$("#xzr").val()!=null){
            			$("#xzr").val($("#xzr").val()+"、"+sessionStorage.docname);
            		}else{
            			$("#xzr").val(sessionStorage.docname);
            		}
            	}
            	if(ry==3){
            		if($("#pzr").val()!=""||$("#pzr").val()!=null){
            			$("#pzr").val($("#pzr").val()+"、"+sessionStorage.docname);
            		}else{
            			$("#pzr").val(sessionStorage.docname);
            		}
            	}
            	if(ry==4){
            		if($("#tfr").val()!=""||$("#tfr").val()!=null){
            			$("#tfr").val($("#tfr").val()+"、"+sessionStorage.docname);
            		}else{
            			$("#tfr").val(sessionStorage.docname);
            		}
            	}
            }
        }, {
            className: 'btn btn-danger btn-sm',
            name: '取消',
            fn: function(opt) {
            	
            }
        }]
    });
}


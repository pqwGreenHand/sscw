function shuaxin(id) {

    loadinfo();
}
//取物
function showout(id, key) {
    if (isGridButton("eshowout")) {
        $.messager.confirm('取物确认',"储物柜编号：" + key + "，是否确认取物？",function(r){
            if (r){
                window.location.href = "exhibitout.jsp?s_lockerId=" + id + "&ssareaid=" + ssareaid;
            }
        })
    } else {
        $.messager.alert('提示', '权限不足，请更换账号或联系管理员!');
    }
}
//存物
function showin(id, key) {
    if (isGridButton("eshowin")) {
            $.messager.confirm('存物确认',"储物柜编号：" + key + "，是否确认存物？",function(r){
                if (r) {
                    window.location.href = "exhibitin.jsp?s_lockerId=" + id + "&ssareaid=" + ssareaid;
                }
        });
    } else {
        $.messager.alert('提示', '权限不足，请更换账号或联系管理员!');
    }
}


function goBelongManage(ssareaid) {
    window.location.href = 'exhibit.jsp?ssareaid=' + ssareaid;
}

function goBelongin(ssareaid) {
    window.location.href = 'exhibitin.jsp?ssareaid=' + ssareaid;
}

function goBelongout() {
    window.location.href = 'exhibitout.jsp';
}
//加载柜子
function loadinfo() {
    if(ssareaid==1){
        $("#box").hide();
        $("#box_area1").show();
    }else{
        $("#box").show();
        $("#box_area1").hide();
    }
    jQuery.ajax({
        async: false,
        type: 'POST',
        contentType: 'application/json',
        url: 'listboxinfo.do?areaid=' + ssareaid + "&timeSign=" + getTimeSign(),
        dataType: 'json',
        success: function (data) {
            for (var key in data) {
                var list = new Array();
                list = data[key];
                if(ssareaid!=1){
                    drawBox(list,key);
                }
                attrType(list);
            }
        },
        error: function (data) {
            $.messager.progress('close');
            $.messager.alert('错误', '柜子显示失败（listboxinfo）!');
        }
    });
}
//画柜子
function drawBox(list,key) {
    var str = "<div class=\"box-title\">" + "<span>第" + key + "组</span>" + "</div>";
    $("#box").append(str);
    var string = "";
    for (var i = 0; i < list.length; i++) {
        string += "";
        if (i == 0) {
            if(list[i].type==2){
                string += "<div class=\"list\">\n" +
                    "              <div class=\"row big\">\n" +
                    "                <div class=\"item blank\" id='box"+list[i].id+"'onclick='showin("+list[i].id+", "+list[i].showNo+")'>\n" +
                    "                  <div class=\"item-in\" id='item"+list[i].id+"' >\n" +
                    "                    <i>"+list[i].showNo+"</i>\n" +
                    "                  </div>\n" +
                    "                </div>";
            }else{
                string += "<div class=\"list\">\n" +
                    "              <div class=\"row\">\n" +
                    "                <div class=\"item blank\" id='box"+list[i].id+"' onclick='showin("+list[i].id+", "+list[i].showNo+")'>\n" +
                    "                  <div class=\"item-in\" id='item"+list[i].id+"'>\n" +
                    "                    <i>"+list[i].showNo+"</i>\n" +
                    "                  </div>\n" +
                    "                </div>";
            }
        }else if (list[i].row > list[i-1].row) {
            if(list[i].type==2){
                string += " </div>\n" +
                    "              <div class=\"row big\">\n" +
                    "                <div class=\"item blank\" id='box"+list[i].id+"' onclick='showin("+list[i].id+","+list[i].showNo+")'>\n" +
                    "                  <div class=\"item-in\" id='item"+list[i].id+"'>\n" +
                    "                    <i>"+list[i].showNo+"</i>\n" +
                    "                  </div>\n" +
                    "                </div>";
            }else{
                string += " </div>\n" +
                    "              <div class=\"row\">\n" +
                    "                <div class=\"item blank\" id='box"+list[i].id+"' onclick='showin("+list[i].id+", "+list[i].showNo+")'>\n" +
                    "                  <div class=\"item-in\" id='item"+list[i].id+"'>\n" +
                    "                    <i>"+list[i].showNo+"</i>\n" +
                    "                  </div>\n" +
                    "                </div>";
            }
        }else if(i==list.length-1){
            string += "<div class=\"item blank\" id='box"+list[i].id+"' onclick='showin("+list[i].id+", "+list[i].showNo+")'>\n" +
                "                  <div class=\"item-in\" id='item"+list[i].id+"'>\n" +
                "                    <i>"+list[i].showNo+"</i>\n" +
                "                  </div>\n" +
                "                </div></div></div>";
        } else{
            string += "<div class=\"item blank\" id='box"+list[i].id+"' onclick='showin("+list[i].id+", "+list[i].showNo+")'>\n" +
                "                  <div class=\"item-in\" id='item"+list[i].id+"'>\n" +
                "                    <i>"+list[i].showNo+"</i>\n" +
                "                  </div>\n" +
                "                </div>";
        }
    }
    $("#box").append(string);
}
function attrType(list){
    for (var i = 0; i < list.length; i++) {
        if(list[i].isGet==0){
            $("#box"+(list[i].id+"")).removeClass("item blank");
            $("#box"+(list[i].id+"")).removeAttr("onclick");
            $("#box"+(list[i].id+"")).attr("onclick","showout("+list[i].id+","+list[i].showNo+");");
            $("#box"+(list[i].id+"")).addClass("item used");
            $("#item"+(list[i].id+"")).append("<div>\n" +
                "                      <i>"+list[i].count+"</i>\n" +
                "                      <span>件</span>\n" +
                "                    </div>\n" +
                "                    <span>"+list[i].personName+"</span>");
        }
    }
}
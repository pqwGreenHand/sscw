<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta content="always" name="referrer">
    <title>平谷分局涉案人员随身财物智能化管理系统</title>
    <%@ include file="common.jsp" %>
    <style>
        #mainTabs .tabs-panels > .panel > .panel-body {
            overflow: hidden;
        }

        #mainTabs > .tabs-header {
            padding-top: 0px;
        }
        .datagrid-body{
            height: 60px !important;
        }
        /*.panel-body .accordion-body{*/
            /*height:fit-content !important;*/
        /*}*/
        .logo {
            display: block;
            width: 1200px;
            height: 98px;
            z-index: 4;
            margin: auto;
            position: relative;
            text-align: center;
            margin-bottom: 30px;
            margin-top: -10px;
        }
        .logo span {
            color: #fff;
            font-size: 44px;
            color: #fff;
            display: inline-block;
            line-height: 98px;
            padding-left: 110px;
            position: relative;
        }
        .logo span:before {
            content: '';
            display: inline-block;
            width: 90px;
            height: 98px;
            background-image: url(static/images/login-5.png);
            position: absolute;
            left: 0;
            /* top: 0; */
            bottom: 0;
            margin: auto;
        }
    </style>
</head>

<body>
<div id="master-layout">
    <div data-options="region:'north',border:false,bodyCls:'theme-header-layout'">

        <div class="theme-navigate">
            <div class="left">
                <div class="logo">
                    <span style="font-size: 35px;font-weight:bold;">平谷分局涉案人员随身财物管理智能化系统</span>
                </div>
              <%--  <a href="#" class="easyui-linkbutton left-control-switch"><i class="fa fa-bars fa-lg"></i></a>
               --%> <%--<a href="#" class="easyui-menubutton theme-navigate-user-button"
                   data-options="menu:'.theme-navigate-user-panel'">${user.username}&lt;%&ndash;匿名&ndash;%&gt;</a>--%>
                <%--<a href="#" class="easyui-linkbutton">新建</a>
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm1',hasDownArrow:false">文件</a>
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm2',hasDownArrow:false">编辑</a>
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm3',hasDownArrow:false">消息<span
                        class="badge color-default">15</span></a>--%>

                <%--<select id="cc1" class="easyui-combobox theme-navigate-combobox" name="dept" style="width:120px;">
                    <option>选择样式</option>
                    <option>Insdep</option>
                    <option>Bootstrap</option>
                    <option>Gray</option>
                    <option>Metro</option>
                    <option>Material</option>
                </select>--%>

            </div>
            <div class="right">
                <%--<select id="cc2" class="easyui-combobox theme-navigate-combobox" name="dept" style="width:180px;">
                    <option>Choose a language</option>
                    <option>Chinese</option>
                    <option>English</option>
                    <option>Korean</option>
                    <option>Japanese</option>
                    <option>Arabic</option>
                </select>--%>
                <%-- <input class="easyui-searchbox theme-navigate-search"
                        data-options="prompt:'输入搜索的关键词..',menu:'#mm',searcher:doSearch" style="width:300px"/>--%>
                <a href="#" class="easyui-menubutton theme-navigate-more-button"
                   data-options="menu:'#more',hasDownArrow:false"></a>
                <div id="more" class="theme-navigate-more-panel">
                    <div onclick="modifyPassword()">修改密码</div>
                    <div onclick="logout()">注销</div>
                </div>
                <%-- <div id="mm" class="theme-navigate-menu-panel">
                     <div data-options="name:'all'">全部内容</div>
                     <div data-options="name:'sports'">标题</div>
                     <div data-options="name:'sports'">作者</div>
                     <div data-options="name:'sports'">内容</div>
                 </div>--%>
            </div>
        </div>
    </div>

    <!--开始左侧菜单-->
    <div data-options="region:'west',border:false,bodyCls:'theme-left-layout'" style="width:200px;">

        <!--正常菜单-->
        <div class="theme-left-normal">
            <!--theme-left-switch 如果不需要缩进按钮，删除该对象即可-->
            <div class="left-control-switch theme-left-switch"><i class="fa fa-chevron-left fa-lg"></i></div>

            <!--start class="easyui-layout"-->
            <div class="easyui-layout" data-options="border:false,fit:true">
                <!--start region:'north'-->
                <div data-options="region:'north',border:false" style="height:100px;">
                    <!--start theme-left-user-panel-->
                    <div class="theme-left-user-panel">
                        <dl>
                            <dt>
                                <img src="${ctx}/newpage/static/plugin/easyui/themes/insdep/images/portrait86x86.png"
                                     width="43"
                                     height="43"
                                     onerror="javascript:this.src='${ctx}/newpage/static/plugin/easyui/themes/insdep/images/portrait86x86.png'">
                            </dt>
                            <dd>
                                <b class="badge-prompt">
                                    </b>
                                <p>消息：<i class="badge color-important">10</i></p>
                                <%--<p>安全等级：<i class="text-success" id="testHeight">11</i></p>--%>
                            </dd>

                        </dl>
                    </div>
                    <!--end theme-left-user-panel-->
                </div>
                <!--end region:'north'-->

                <!--start region:'center'-->
                <div data-options="region:'center',border:false">

                    <!--start easyui-accordion-->

                    <div class="easyui-accordion" data-options="border:true,fit:false" id="menu-container">
                        <%--   <div title="案件管理11">
                                <ul class="easyui-datalist"
                                    data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                    <li value="Test.html">案件登记</li>
                                    <li value="Test.html">案件登记1</li>
                                    <li value="Test.html">案件登记1</li>
                                    <li value="Test.html">案件登记1</li>
                                </ul>
                            </div>--%>
                       <div title="案件管理">
                            <ul class="easyui-datalist"
                                data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                <li value="${ctx}/newpage/ajxx/ajxx.jsp">案件管理</li>
                            </ul>
                        </div>
                            <div title="人员管理">
                                <ul class="easyui-datalist"
                                    data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                    <li value="${ctx}/newpage/person/person.jsp">人员管理</li>
                                </ul>
                            </div>
                        <div title="物品暂存">
                            <ul class="easyui-datalist"
                                data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                <li value="${ctx}/newpage/sswp/index.jsp">随身物品暂存</li>
                            </ul>
                        </div>
                        <div title="移交接收">
                            <ul class="easyui-datalist"
                                data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                <li value="${ctx}/newpage/wpyj/wpyj.jsp">移交接收</li>
                            </ul>
                        </div>

                        <div title="文书管理">
                            <ul class="easyui-datalist"
                                data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                <li value="${ctx}/newpage/wsgl/wsgl.jsp">文书管理</li>
                            </ul>
                        </div>
                        <div title="电子档案">
                            <ul class="easyui-datalist"
                                data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                <li value="${ctx}/newpage/dzda/dzda.jsp">电子档案</li>
                            </ul>
                        </div>
                            <div title="物品明细">
                                <ul class="easyui-datalist"
                                    data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                    <li value="${ctx}/newpage/dashboard.jsp">物品明细</li>
                                </ul>
                            </div>
                            <div title="民警管理">
                                <ul class="easyui-datalist"
                                    data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                    <li value="${ctx}/newpage/dzda/dzda.jsp">民警管理</li>
                                </ul>
                            </div>
                        <%--
                            <div title="控制台">
                                <ul class="easyui-datalist"
                                    data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                    <li value="${ctx}/zhfz/bacs/dzRecord/archives.jsp">控制台</li>
                                </ul>
                            </div>--%>

                        <%--      <c:forEach var="${menu}" items="${childAuth}>">
                                   <c:if test="${menu.parentTitle==null}">
                                       <div title="${menu.parentTitle}">
                                           <ul class="easyui-datalist"
                                               data-options="border:false,fit:true, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                               <c:forEach var="${childMenu}" items="${childAuth.lowerMenu}">
                                                       <li value="${ctx}/${childMenu.url}">${childMenu.title}</li>
                                               </c:forEach>
                                           </ul>
                                       </div>
                                   </c:if>
                               </c:forEach>--%>


                        <%-- <c:forEach var="menu" items="${menus}">
                             <c:if test="${menu._parentId==null}">
                                 <div title="${menu.name}">
                                     <ul class="easyui-datalist"
                                         data-options="border:false,fit:true, onClickRow:function(index,row){ztab.open(row.text,row.value)}">
                                         <c:forEach var="childMenu" items="${menus}">
                                             <c:if test='${childMenu._parentId==menu.id}'>
                                                 <li value="${ctx}/${childMenu.url}">${childMenu.name}</li>
                                             </c:if>
                                         </c:forEach>
                                     </ul>
                                 </div>
                             </c:if>
                         </c:forEach>--%>
                    </div>


                    <!--end easyui-accordion-->
                </div>
                <!--end region:'center'-->
            </div>
            <!--end class="easyui-layout"-->

        </div>
        <!--最小化菜单-->
        <div class="theme-left-minimal">
            <ul class="easyui-datalist" data-options="border:false,fit:true">

                <c:forEach var="menu" items="${menus}">
                    <c:if test="${menu._parentId==null}">
                        <li><%--<i class="fa fa-home fa-2x"></i>--%>
                            <a class="left-control-switch">
                                <i class="fa fa-cube fa-2x" aria-hidden="true"></i>
                                <p>${menu.name}</p>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
                <li>
                    <a class="left-control-switch">
                        <i class="fa fa-chevron-right fa-2x"></i>
                        <p>打开</p>
                    </a>
                </li>
            </ul>
        </div>

    </div>
    <!--结束左侧菜单-->

    <%--<div data-options="region:'center',border:false,href:'${ctx}/admin/login'" id="control" style="padding:20px; background:#fff;">
        <iframe class="page-iframe" src="workbench.html" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
    </div>--%>

    <div data-options="region: 'center',border:false" style="padding:0px;margin: 0px; background:#fff;">
        <div id="mainTabs_tools" class="tabs-tool">
            <table>
                <tr>
                    <td><a id="mainTabs_jumpHome"
                           class="easyui-linkbutton easyui-tooltip" title="跳转至主页选项卡"
                           data-options="plain: true, iconCls: 'icon-back'" onclick="javascript:ztab.home()"></a></td>
                    <td>
                        <div class="datagrid-btn-separator"></div>
                    </td>
                    <td><a id="mainTabs_refTab"
                           class="easyui-linkbutton easyui-tooltip" title="刷新当前选中的选项卡"
                           data-options="plain: true, iconCls: 'icon-reload'"
                           onclick="javascript:ztab.refreshCurrentTab()"></a></td>
                    <td>
                        <div class="datagrid-btn-separator"></div>
                    </td>
                    <td><a id="mainTabs_closeTab"
                           class="easyui-linkbutton easyui-tooltip" title="关闭所有选项卡"
                           data-options="plain: true, iconCls: 'icon-cancel'" onclick="javascript:ztab.closeAll()"></a>
                    </td>
                </tr>
            </table>
        </div>

        <div id="mainTabs"
             class="easyui-tabs <%--theme-tab-blue-block theme-tab-unradius theme-tab-line-bold theme-tab-unbackdrop--%>"
             data-options="tabPosition:'top',plain:true,narrow:true,fit: true, border: false, tools: '#mainTabs_tools'"
             style="margin: 0px; padding: 0px;">
        </div>
    </div>
</div>
<div id="dlg"></div>

<div id="updatePw" style="display: none">
    <form id="keyForm" >
        <input type="hidden" id="id" name="id" />
            <table style="margin: 0 auto; padding: 10px;width: 90%">
                <tr>
                    <td>旧&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码：:</td>
                    <td><input  id="oldPwd" name="oldPwd" type="password"  class="easyui-textbox" required="true" style="margin: -2px; width: 170px; height: 28px" ><font color="red">*</font>
                    </td>
                </tr>
                <tr>
                    <td>新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码</td>
                    <td><input id="newPwd" name="newPwd" type="password" class="easyui-textbox" required="true" style="margin: -2px; width: 170px; height: 28px" ><font color="red">*</font>
                    </td>
                </tr>
                <tr>
                    <td>确认新密码</td>
                    <td><input id="confirmPwd" name="confirmPwd" type="password" class="easyui-textbox" required="true" style="margin: -2px; width: 170px; height: 28px" ><font color="red">*</font>
                    </td>
                </tr>
            </table>
    </form>

  <%--  <div id="changeps-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" name="noButton" onclick="updatePwdSave()">保存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" name="noButton" onclick="closeUpdatePw();">关闭</a>
    </div>--%>
</div>

<script>
    $(function () {
        // initMenuFun(2);
        initUserName();
        $(".panel-title").before('<i style="background-image: url(static/images/folding-menu-4.png);padding-right: 1;width: 25px;height: 25px;display: inline-block;position: absolute;bottom: 0;top: 0;margin: auto;"></i>');
        /*布局部分*/
        $('#master-layout').layout({
            fit: true/*布局框架全屏*/
        });
        var left_control_status = true;
        var left_control_panel = $("#master-layout").layout("panel", 'west');

        $(".left-control-switch").on("click", function () {
            if (left_control_status) {
                left_control_panel.panel('resize', {width: 100});
                left_control_status = false;
                $(".theme-left-normal").hide();
                $(".theme-left-minimal").show();
            } else {
                left_control_panel.panel('resize', {width: 200});
                left_control_status = true;
                $(".theme-left-normal").show();
                $(".theme-left-minimal").hide();
            }
            $("#master-layout").layout('resize', {width: '100%'})
        });

        $(".theme-navigate-user-modify").on("click", function () {
            $('.theme-navigate-user-panel').menu('hide');
//            $.insdep.window({id: "personal-set-window", href: "user.html", title: "修改资料"});
            modifyUserInfo();
        });
        //$.insdep.control("list.html");

        /* var cc1 = $('#cc1').combo('panel');
         cc1.panel({cls: "theme-navigate-combobox-panel"});*/
        /*var cc2 = $('#cc2').combo('panel');
        cc2.panel({cls: "theme-navigate-combobox-panel"});*/


        /*$("#open-layout").on("click",function(){
         var option = {
         "region":"west",
         "split":true,
         "title":"title",
         "width":180
         };
         $('#master-layout').layout('add', option);

         });*/
        //主页
        <%--ztab.openWithOptions({title: '主页', url: '${ctx}/login.jsp'});--%>
        ztab.openWithOptions({title: '主页', url: '${ctx}/newpage/dashboard.jsp'});
    });

    function initUserName() {
        $.get("${ctx}/common/getSessionInfo.do", function (data) {
            var sessionObj = eval('(' + data + ')');
            if(sessionObj.user==null){
                $.messager.alert('错误', '网络已断开，请重新登录', 'warning', function () {
                    location.href = '${ctx}/newpage/login.jsp';
                });
            }else{
                $(".badge-prompt").text(sessionObj.user.realName);
            }
            /*$("#userName").html(sessionObj.user.realName);
            $("#loginName").val(sessionObj.user.loginName);
            $("#realName").val(sessionObj.user.realName);
            $("#jobTitle").val(sessionObj.user.jobTitle);
            $("#organizationName").val(sessionObj.user.organizationName);
            $("#certificateName").val(sessionObj.user.certificateName);
            $("#certificateNo").val(sessionObj.user.certificateNo);
            $("#mobile").val(sessionObj.user.mobile);
            $("#email").val(sessionObj.user.email);

            if (sessionObj.user.sex == 1) {
                $("input[name='sex'][value='1']").attr("checked", true);
            } else {
                $("input[name='sex'][value='2']").attr("checked", true);
            }
            $("#description").val(sessionObj.user.description);*/
        });
    }
    function initMenuFun(pid) {
        <%--window.open('${ctx}/common/listMenuByPidMvw.do?pid=' + pid)--%>
        $.ajax({
            url: '${ctx}/common/listMenuByPid.do?pid=' + pid,
            dataType: 'json',
            async: false,
            success: function (data) {
                var authInfo = data['authInfo'];
                var childAuths = data['childAuth'];
                console.log(data)
                $('#testHeight').html("111")
                var menuUi = ''
                $.each(childAuths, function (i, childAuthInfo) {
                    menuUi += ' <div title=' + childAuthInfo.parentTitle + '>' +
                        ' <ul class="easyui-datalist" data-options="border:false, onClickRow:function(index,row){ztab.open(row.text,row.value)}">';
                    var childs = childAuthInfo.lowerMenu;
                    if (childs && childs.length > 0) {
                        $.each(childs, function (j, temp) {
                            menuUi += ' <li value=' + temp.url + '>' + temp.title + '</li>'
                        })
                    }
                    menuUi += '</ul></div>';
                });
                $("#menu-container").html(menuUi);
            },
            error: function () {
                $.messager.alert('错误', '网络连接已断开，请重新登录', 'warning', function () {
                    location.href = '${ctx}/newpage/login.jsp';
                });
            }
        });
    }


    function doSearch(value, name) {
        alert('You input: ' + value + '(' + name + ')');
    }

    /**
     * 注销、退出
     */
    function logout() {
        parent.$.messager.confirm('提示', '您确定要退出系统吗？', function (data) {
            if (data) {
                window.location.href = '${ctx}/logout.do';
            }
        });
    }

    /**
     * 修改，密码
     * @param id
     */
    function modifyPassword() {
        var dialog = $("#updatePw").dialog({
            title: '修改密码',
            width: 480,
            height: 260,
            maximizable: false,
            // href: 'person/personadd.jsp',
            modal: true,
            buttons: [{
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    var isValid = $("#updatePw").form('validate');
                    if (isValid) {
                        var entForm = $('#keyForm');
                        var obj = entForm.serializeObject();
                        if (!obj.oldPwd) {
                            alert("请输入旧密码！");
                            return;
                        }
                        if (obj.newPwd.length < 6) {
                            alert("请输入6位及6位以上的新密码！");
                            return;
                        }
                        if (obj.newPwd != obj.confirmPwd) {
                            alert("输入的两次密码不一致！");
                            return;
                        }
                        var enterpriseinfo = JSON.stringify(obj);

                        jQuery.ajax({
                            type: 'POST',
                            contentType: 'application/json',
                            url: '/zhfz/zhfz/common/user/changePsw.do',
                            data: enterpriseinfo,
                            dataType: 'json',
                            success: function (data) {
                                if (data.msg == '密码修改成功！') {
                                    dialog.dialog('close');
                                    $.messager.show({
                                        title: '提示',
                                        msg: data.msg,
                                        timeout: 3000
                                    });
                                    parent.$.messager.confirm('提示', '您确定要退出系统重新登录吗？', function (data) {
                                        if (data) {
                                            window.location.href = '${ctx}/logout.do';
                                        }
                                    });
                                } else {
                                    U.msg(data.msg);
                                    $.messager.progress('close');
                                }
                            },
                            error: function (data) {
                                $.messager.progress('close');
                                U.msg('系统错误!' + data.Msg);
                            }
                        });
                    }
                }
            }, {
                text: '取消', iconCls: 'icon-cancel',
                handler: function () {
                    dialog.dialog('close');
                }
            }]
        });
    }
</script>
</body>
</html>

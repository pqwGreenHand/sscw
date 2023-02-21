<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="FivePringTaizhangDialog" class="m-popup m-info-popup">
    <div class="popup-bg"></div>
    <div class="popup-content m-box" style="height: 300px;width:660px">
        <div class="popup-inner" style="width: 100%;height: 100%">
            <div class="title">
                <div><i style="background-image: url(static/popup-2.png);"></i><span>选择打印台账类型</span></div>
                <a name="noButton" class="close" onclick="javaScript:$('#FivePringTaizhangDialog').hide();"></a>
            </div>
            <div id="FivePringTaizhangDiv1"  class="bd" style="margin-top: 50px;width: 98%;height: 90%;overflow:hidden" align="center">
                <input type="hidden" id="FivePringTaizhangSerialId" >
                <div class="page locker-home" style="height:250px;width: 660px">
                    <div class="main" style="height:250px;width: 660px">
                        <div class="box" style="text-align:left;width:660px">
                        <input type="hidden" id="fivePringTaizhangSerialId" name="fivePringTaizhangSerialId">
	                        <div>
								<table style=" font-size: 20px;color: white;width: 500px;" >
									<tr>
										<td>入区台账</td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-edit" onclick="fiveSign('1')">签字</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveSignPrint('1')">签字版打印</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveOrigPrint('1')">原始版打印</a></td>
									</tr>

									<tr>
										<td>人身安检台账</td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-edit" onclick="fiveSign('2')">签字</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveSignPrint('2')">签字版打印</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveOrigPrint('2')">原始版打印</a></td>
									</tr>

									<tr>
										<td>随身物品台账</td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-edit" onclick="fiveSign('3')">签字</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveSignPrint('3')">签字版打印</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveOrigPrint('3')">原始版打印</a></td>
									</tr>

									<tr>
										<td>涉案物品台账</td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-edit" onclick="fiveSign('4')">签字</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveSignPrint('4')">签字版打印</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveOrigPrint('4')">原始版打印</a></td>
									</tr>

									<tr>
										<td>出区台账</td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-edit" onclick="fiveSign('5')">签字</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveSignPrint('5')">签字版打印</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveOrigPrint('5')">原始版打印</a></td>
									</tr>
                                    <tr>
										<td>五合一台账</td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-edit" onclick="fiveSign('6')">签字</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveSignPrint('6')">签字版打印</a></td>
										<td><a href="#" name="noButton" class="easyui-linkbutton" iconCls="icon-print" onclick="fiveOrigPrint('6')">原始版打印</a></td>
									</tr>
								</table>

							</div>        
                               
                               
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="edges">
            </i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
        </div>
    </div>
</div>
 
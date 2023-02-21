<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<tr>
    <td style="line-height: 180px;width: 240px;border: gainsboro solid 1px">
        <!-- 入区照片框 -->
        <div id="in_photo" style="padding-left: 0px;">
            <img alt="" id="oldphoto" width="240" height="180" style="padding-left:0px;" onerror='this.src="image/noPhoto.jpg"'>
        </div>
    </td>
    <td>
        <!--照片框 -->
        <div id="canvas_view" style="border: gray solid 1px">
            <canvas id="canvas" width="220" height="170" ></canvas>
        </div>
    </td>
    <td width="100px">
        <a id="inSerialPhotoTips" class="item i1 m-box" name="noButton" onclick="photoScan()">
            <span>&nbsp;&nbsp;拍照</span>
            <div class="edges">
                <i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
            </div>
        </a>
    </td>
    <td >
        <!-- 拍照框 -->
        <div id="webcam_view" style="width: 220px;height: 170px;">
            <div id="webcam" ></div>
        </div>
    </td>
</tr>
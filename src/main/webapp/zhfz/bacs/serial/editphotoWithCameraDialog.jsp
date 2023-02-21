<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="editphotoDialog" class="m-popup m-info-popup">
	<div class="popup-bg"></div>
	<div class="popup-content m-box" style="height: 500px;width: 1000px">
		<div class="popup-inner">
			<div class="title">
				<div><i style="background-image: url(static/popup-2.png);"></i><span>修改照片</span></div>
				<a name="noButton" class="close" onclick="javaScript:$('#editphotoDialog').hide();"></a>
			</div>
			<div id="editphotoDiv1"  class="bd" style="margin-top: 50px">
				<form id="editphoto" enctype="multipart/form-data" action="ashx/login.ashx" method="post">
					<input type="hidden" id="editphotoSerialId">
					<table class="main_form1" style="color: white;border-collapse: separate;border-spacing: 10px;margin-left: 50px">
						<tr>
							<td>入区照片</td>
							<td>新拍照片</td>
							<td></td>
							<td>摄像头</td>
						</tr>
						<tr style="text-align: center">
							<td style="line-height: 180px;width: 240px;border: gainsboro solid 1px">
								<!-- 入区照片框 -->
								<div id="in_photo" style="padding-left: 0px;">
									<img alt="" id="oldphoto" width="240" height="180" style="padding-left:0px;" onerror='this.src="image/noPhoto.jpg"'>
								</div>
							</td>
							<td style="line-height: 180px;width: 240px;border: gainsboro solid 1px">
								<!-- 拍照照片框 -->
								<div id="canvas_view">
									<canvas id="editCanvas" width="220" height="170" ></canvas>
								</div>
							</td>
							<td>
								<a id="eidtPhotoTips" class="item i1 m-box" name="noButton" onclick="editPhotoScan()">
									<span>&nbsp;&nbsp;拍照</span>
									<div class="edges">
										<i class="i1"></i><i class="i2"></i><i class="i3"></i><i class="i4"></i>
									</div>
								</a>
							</td>
							<td style="line-height: 180px;width: 240px;border: gainsboro solid 1px">
								<!-- 摄像头框 -->
								<div id="webcam_view">
									<div id="editWebcam" ></div>
								</div>
							</td>

						</tr>
					</table>
				</form>
				<div class="next" style="margin-top: 100px;">
					<button class="m-btn-1 blue" onclick="uploadPhoto()" style="width:120px;height: 40px;font-size: 15px;cursor: pointer">提交</button>
				</div>
			</div>
			<div class="edges">
				</i><i class="i1"></i><i class="i2"></i></i><i class="i3"></i><i class="i4"></i>
			</div>
		</div>
	</div>
</div>
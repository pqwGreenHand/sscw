function SWFLoad(swfu, settings) {
    swfu.SWFObj = new SWFUpload({
        upload_url: settings.upload_url,
        post_params: settings.post_params,
        file_size_limit: settings.file_size_limit,
        file_types: settings.file_types,
        file_types_description: settings.file_types_description,
        file_upload_limit: settings.file_upload_limit,

        use_query_string : true,//要传递参数用到的配置   
		file_post_name : "filedata",//上传文件的名称
        file_queue_error_handler: fileQueueError,
        file_dialog_complete_handler: fileDialogComplete,
        upload_start_handler : settings.upload_start_handler,
        upload_progress_handler: uploadProgress,
        upload_error_handler: uploadError,
        upload_success_handler: settings.upload_success_handler,
        upload_complete_handler: uploadComplete,
        file_dialog_start_handler: fileDialogStart,
        file_queued_handler:fileQueued,

        button_cursor: SWFUpload.CURSOR.HAND,
        //button_action: settings.button_action,
        //button_disabled: settings.button_disabled,
        button_image_url: settings.button_image_url,
        //button_text: settings.button_text,
        button_placeholder_id: settings.button_placeholder_id,
        button_width: settings.button_width,
        button_height: settings.button_height,
        //button_text_top_padding: settings.button_text_top_padding,
        //button_text_left_padding: settings.button_text_left_padding,

        flash_url: settings.flash_url,

        custom_settings: settings.custom_settings,
        debug: false
    });
}
/*初始化结束*/

//替代window.onload   解决不能同时使用多个window.onload的错误
function addLoadEvent(func) {
    var oldonload = window.onload;
    if (typeof window.onload != "function") {
        window.onload = func;
    } else {
        window.onload = function() {
            func();
            oldonload();
        }
    }
}
 
/* This is an example of how to cancel all the files queued up.  It's made somewhat generic.  Just pass your SWFUpload
object in to this method and it loops through cancelling the uploads. */
function cancelQueue(instance) {
	document.getElementById(instance.customSettings.cancelButtonId).disabled = true;
	instance.stopUpload();
	var stats;
	
	do {
		stats = instance.getStats();
		instance.cancelUpload();
	} while (stats.files_queued !== 0);
	
}

/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */
function fileDialogStart() {
	/* I don't need to do anything here */
}
function fileQueued(file) {
	try {
		// You might include code here that prevents the form from being submitted while the upload is in
		// progress.  Then you'll want to put code in the Queue Complete handler to "unblock" the form
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		//progress.setStatus("Pending...");
		progress.setStatus("等待...");
		progress.toggleCancel(true, this);

	} catch (ex) {
		this.debug(ex);
	}

}

function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			//alert("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")));
			alert("添加的文件过多.\n" + (message === "0" ? "You have reached the upload limit." : "你可以最多 " + (message > 1 ? "选择 " + message + " 个文件." : "1个文件.")));
			return;
		}

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			//progress.setStatus("File is too big.");
			progress.setStatus("文件太大.");
			this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			//progress.setStatus("Cannot upload Zero Byte files.");
			progress.setStatus("不能上传大小为0B的文件.");
			this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			//progress.setStatus("Invalid File Type.");
			progress.setStatus("文件类型无效.");
			this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			//alert("You have selected too many files.  " +  (message > 1 ? "You may only add " +  message + " more files" : "You cannot add any more files."));
			alert("上传文件选的过多。");
			break;
		default:
			if (file !== null) {
				progress.setStatus("未知错误.");
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}


function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (this.getStats().files_queued > 0) {
			document.getElementById(this.customSettings.cancelButtonId).disabled = false;
		}
		
		/* I want auto start and I can do that here */
		this.startUpload();
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and return true to indicate that the upload should start */
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		
		//progress.setStatus("Uploading...");
		progress.setStatus("上传中..."); 
		progress.toggleCancel(true, this);
		/*this.setPostParams({ 
			  'fileName':encodeURIComponent(file.name) 
			  });*/ 
		
		var post_params = this.settings.post_params; 
		post_params.fileName = encodeURI(file.name);
		// Ext.apply(post_params,{  
		  //       'fileName':encodeURI(file.name)  
		//  }); 
		this.setPostParams(post_params); 
		  
		
	}
	catch (ex) {
	}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {

	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setProgress(percent);
		//progress.setStatus("Uploading...");
		progress.setStatus("上传中...");
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(fileObj, serverData,base,swfObj) {
/*	try {
		var progress = new FileProgress(fileObj, this.customSettings.progressTarget);
		progress.setComplete();
		progress.setStatus("Complete.");
		progress.toggleCancel(false); 

	} catch (ex) {
		this.debug(ex);
	}*/
	 try {
			//alert(server_data);
			var progress = new FileProgress(fileObj,swfObj.customSettings.progressTarget);

			progress.setComplete();
			var fileSize = fileObj.size;
			var i = 0;
			while (fileSize > 1024 && i < 2) {
				fileSize = fileSize / 1024;
				i++;
			}

			var sizeDW;
			if (i == 0)
				sizeDW = "B";
			else if (i == 1)
			    sizeDW = "K";
			else if (i == 2)
				sizeDW = "M";
			
			progress.setStatus(toDecimal2(fileSize) + sizeDW + "  上传完成");
			//document.getElementById("size").value = toDecimal2(fileSize) + sizeDW;
			progress.toggleCancel(false);
			
            
			var filelist = document	.getElementById("progressContainer" + fileObj.id);
			filelist.style.paddingLeft = "50px";
			filelist.style.background = "#F0F5FF url('"+base+"/resource/image/file-icon/"
								+ fileObj.type.replace('.', '')
								+ ".png') no-repeat 5px 5px";
			//alert(filelist.style.background);
            
			//删除附件按钮
			var deleteObject = document.createElement("a");
			deleteObject.className = "myProgressBt";
			deleteObject.href = "javascript:void(0);";
			deleteObject.appendChild(document.createTextNode("删除"));
			deleteObject.onclick = function() {
			     progress.setDelete();
			     if(typeof delFile === "function"){ 
			     	delFile(idObj.value,fileObj,swfObj);
			        //this.parentNode.parentNode.remove();
			     }
			     //document.getElementById("size").value ="";
			     
			}
			filelist.appendChild(deleteObject);
			//action 返回的附件ID
			var idObj = document.createElement("input");
			idObj.setAttribute("type", "hidden");
			idObj.setAttribute("name", swfObj.customSettings.file_post_name);
			var data = jQuery.parseJSON(serverData);
			idObj.setAttribute("value", data.message);
			idObj.setAttribute("id", "arrId" + fileObj.id);
			filelist.appendChild(idObj);
			
			//文件名称
			var fileName = document.createElement("input");
			fileName.setAttribute("type","hidden");
			fileName.setAttribute("name","fileNames");
			fileName.setAttribute("value",fileObj.name);
			fileName.setAttribute("id","fileName" + + fileObj.id);
			filelist.appendChild(fileName);
			
			if(swfObj.customSettings.fileSiteName){
			//fileSite
			var fileSiteObj = document.createElement("input");
			fileSiteObj.setAttribute("type", "hidden");
			fileSiteObj.setAttribute("name", swfObj.customSettings.fileSiteName);
			fileSiteObj.setAttribute("value", toDecimal2(fileSize) + sizeDW); 
			filelist.appendChild(fileSiteObj);
			}

			if(swfObj.customSettings.fileTypeName){
			//fileType
			var fileTypeObj = document.createElement("input");
			fileTypeObj.setAttribute("type", "hidden");
			fileTypeObj.setAttribute("name", swfObj.customSettings.fileTypeName);
			fileTypeObj.setAttribute("value", fileObj.type); 
			filelist.appendChild(fileTypeObj);
			}
			
			var descObj = document.createElement("p");
			descObj.style.clear = "both";
			var textObj = document.createTextNode("描述：");
			descObj.appendChild(textObj);
			
			//返回的附件原文件描述
			var nameObj = document.createElement("input");
			nameObj.setAttribute("type", "input");
			nameObj.setAttribute("name", "fileDesc");
			nameObj.setAttribute("value", fileObj.name);
			nameObj.setAttribute("id", "fileDesc" + fileObj.id);
			descObj.appendChild(nameObj);
			filelist.appendChild(descObj);
			
			var clearObj = document.createElement("div");
			clearObj.className = "clear";
			filelist.appendChild(clearObj);

			//alert(fileObj.value);
		} catch (ex) {
			swfObj.debug(ex);
		}
	 
}
 
function uploadComplete(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued === 0) {
			document.getElementById(this.customSettings.cancelButtonId).disabled = true;
		} else {	
			this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}

}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);
		
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("Upload Error: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
			//progress.setStatus("Configuration Error");
			progress.setStatus("配置错误.");
			this.debug("Error Code: No backend file, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			//progress.setStatus("Upload Failed.");
			progress.setStatus("上传失败.");
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			//progress.setStatus("Server (IO) Error");
			progress.setStatus("服务器 (IO) 错误.");
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			//progress.setStatus("Security Error");
			progress.setStatus("安全错误.");
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			//progress.setStatus("Upload limit exceeded.");
			progress.setStatus("上传的文件过多.");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
			//progress.setStatus("File not found.");
			progress.setStatus("找不到文件.");
			this.debug("Error Code: The file was not found, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("Failed Validation.  Upload skipped.");
			this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}
			//progress.setStatus("Cancelled");
			progress.setStatus("取消上传");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			//progress.setStatus("Stopped");
			progress.setStatus("已停止");
			break;
		default:
			progress.setStatus("Unhandled Error: " + error_code);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}
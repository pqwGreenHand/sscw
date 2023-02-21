function getSwf(holder, settings) {
    settings.custom_settings.queue = new Array();
    settings.custom_settings.progress = new Progress();
    var swf = new SWFUpload({
        upload_url: settings.upload_url,
        post_params: settings.post_params,
        use_query_string : true,//要传递参数用到的配置   
		file_post_name : "filedata",//"file",//上传文件的名称
        file_size_limit: "500 MB",
        file_types: settings.file_types,
        file_types_description: settings.file_types_description,
        file_upload_limit: 0, 
        file_queue_error_handler: single_fileQueueError,
        upload_progress_handler: single_uploadProgress,
        upload_error_handler: single_uploadError,
        upload_success_handler: single_uploadSuccess,
        //upload_complete_handler: single_uploadComplete,
        file_queued_handler: single_fileQueued,
        fileDialogComplete:single_fileDialogComplete,
        upload_start_handler:single_uploadStart,

        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
        button_cursor: SWFUpload.CURSOR.HAND,
        button_disabled: settings.button_disabled,
        button_image_url: settings.button_image_url,
        button_text:"浏览...",
        button_placeholder_id: holder,
        button_width: 58,
        button_height: 24,
        button_text_top_padding: 2,
        button_text_left_padding: 10,

        flash_url: settings.flash_url,

        custom_settings: settings.custom_settings,
        debug: false
    });
    return swf;
}

function single_addLoadEvent(func) {
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

function single_fileDialogComplete(numFilesSelected, numFilesQueued, numFilesInQueue) {
    if (numFilesQueued > 0 && numFilesInQueue) { 
        clearQueue(this);
    }
}

function single_fileQueued(file) {
    this.customSettings.queue.push(file.id);
    if (typeof this.customSettings.singleFileQueued == "function") {
        this.customSettings.singleFileQueued(file);
    }
}

function single_uploadStart() {
    this.customSettings.progress.prepare();
}

//该事件由flash定时触发，提供三个参数分别访问上传文件对象、已上传的字节数，总共的字节数。
//因此可以在这个事件中来定时更新页面中的UI元素，以达到及时显示上传进度的效果。
//正在上传
function single_uploadProgress(file, bytesLoaded, bytes) {
    try {
        var percent = Math.ceil((bytesLoaded / bytes) * 100);
        this.customSettings.progress.setProgress(percent);
    } catch (ex) {
        this.debug(ex);
    }
}

function clearQueue(swf) {
    /*var queue = swf.customSettings.queue;
    while (queue.length > 0) {
        var id = queue.pop();
        swf.cancelUpload();
    }*/ 
}

//当文件上传的处理已经完成（这里的完成只是指向目标处理程序发送了Files信息，只管发，不管是否成功接收），
//并且服务端返回了200的HTTP状态时，触发此事件
//注意：此事件的原版在UC_SWFUpload.ascx中，此事件在此处是经修改过的，以使同一页面能存在多个上传控件
function single_uploadSuccess(file, serverData) {
    try {
        //更新serverData
        document.getElementById(this.customSettings.serverDataId).value = serverData;
        //重新设置异步发送的数据
        this.setPostParams({ 
        	savePath: this.settings.post_params.savePath, 
            small: this.settings.post_params.small,
            width: this.settings.post_params.width,
            height: this.settings.post_params.height, 
            data: serverData,  
        });
        if (typeof this.customSettings.singleUploadComplete == "function") {
            var data = serverData;//JSON.parse(serverData);
            if (data && data.length > 0)
                this.customSettings.singleUploadComplete(data);
        }
        this.customSettings.progress.setComplete();
    } catch (ex) {
        this.debug(ex);
    }
}

//全部文件上传完毕
function single_uploadComplete(file) {
    try {  
        if (this.getStats().files_queued === 0) {
        	this.customSettings.progress.setComplete();
            //启用提交按钮
            if (this.customSettings.submitBtnId) {
                var submit_button = document.getElementById(this.customSettings.submitBtnId);
                if (submit_button) {
                    submit_button.disabled = false;
                }
            }
        } else {
            this.startUpload();
        }
    } catch (ex) {
        this.debug(ex);
    }
}

function single_uploadError(file, errorCode, message) {
    try {
        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
                try {
                    this.cancelUpload(file.id);
                } catch (ex1) {
                    this.debug(ex1);
                }
                break;
            case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
                try {
                    this.stopUpload(file.id)
                } catch (ex2) {
                    this.debug(ex2);
                }
            default:
                alert(message);
                break;
        }
    } catch (ex3) {
        this.debug(ex3);
    }
}

function single_fileQueueError(file, errorCode, message) {
    try {
        var errorMsg = "";
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                if (message > 0) {
                    errorMsg = "您只能上传" + message + "个文件!";
                } else {
                    errorMsg = "您不可继续上传文件!";
                }
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                errorMsg = "您不可上传0字节的文件";
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                errorMsg = "文件不可大于" + this.settings.file_size_limit;
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
            default:
                alert(message);
                break;
        }
        if (errorMsg !== "") {
            alert(errorMsg);
            return;
        }
    } catch (ex) {
        this.debug(ex);
    }

}

var isIE = (document.all) ? true : false;
var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);

function addEventHandler(tatget, eventName, handler) {
    if (tatget.addEventListener) {
        tatget.addEventListener(eventName, handler, false);
    } else if (tatget.attachEvent) {
        tatget.attachEvent("on" + eventName, handler);
    } else {
        tatget["on" + eventName] = handler;
    }
}

function setOpacity(target, opacity) {
    if (typeof target.style.opacity == "undefined") {
        target.style.filter = (opacity == 100) ? "" : "alpha(opacity=" + opacity + ")";
    } else {
        target.style.opacity = (opacity == 100) ? "" : "0." + opacity.toString();
    }
}

function Progress() {
    this.casing = document.getElementById("swf_progress_box");
    if (!this.casing) {
        this.casing = document.createElement("div");
        this.casing.setAttribute("id", "swf_progress_box");
        this.casing.className = "progress_box";
        var progressbar_container = document.createElement("div");
        progressbar_container.className = "progressbar_container";
        var progressbar = document.createElement("div");
        progressbar.className = "progressbar";
        progressbar_container.appendChild(progressbar);
        this.casing.appendChild(progressbar_container);
        var text = document.createElement("div");
        text.className = "progress_text";
        this.casing.appendChild(text);
        document.body.appendChild(this.casing);
    }
    this.progress = this.casing.childNodes[0].childNodes[0];
    this.text = this.casing.childNodes[1];
}

Progress.prototype.setProgress = function(percentage) {
    this.progress.style.width = percentage + "%";
    this.text.innerHTML = "已经上传 " + percentage + "%";
}

Progress.prototype.prepare = function() {
    this.casing.style.position = isIE6 ? "absolute" : "fixed";

    if (isIE6) {
        var box = this.casing;
        box.style.marginTop = document.documentElement.scrollTop - box.offsetHeight / 2 + "px";
        box.style.marginLeft = document.documentElement.scrollLeft - box.offsetWidth / 2 + "px";
        window.onscroll = function() {
            box.style.marginTop = document.documentElement.scrollTop - box.offsetHeight / 2 + "px";
            box.style.marginLeft = document.documentElement.scrollLeft - box.offsetWidth / 2 + "px";
        }        
    }
    this.casing.style.visibility = "visible";
    this.mask().style.display = "block";
    this.setProgress(0);
}

Progress.prototype.setComplete = function() {
    this.casing.style.visibility = "hidden";
    this.mask().style.display = "none";
}

Progress.prototype.mask = function() {
    var swf_mask = document.getElementById("swf_mask");
    if (!swf_mask) {
        swf_mask = document.createElement("iframe");
        swf_mask.className = 'swf_mask';
        swf_mask.setAttribute("id", "swf_mask");
        setOpacity(swf_mask, 50);
        addEventHandler(swf_mask, "click", function(e) { });
        addEventHandler(swf_mask, "mousedown", function(e) { });
        document.body.appendChild(swf_mask);
        var doc = (document.compatMode != "CSS1Compat") ? document.body : document.documentElement;
        swf_mask.style.width = Math.max(doc.scrollWidth, doc.clientWidth) + 'px';
        swf_mask.style.height = Math.max(doc.scrollHeight, doc.clientHeight) + 'px';
    }
    return swf_mask;
}
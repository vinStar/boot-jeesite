var dialog;
var uploader;
/**
 * plupload插件。
 * 使用方法：
 * initUpload(id,inputId,rootPath,uploadId,curUser,curDate);
 * id：按钮id
 * rootPath:工程路径。
 */
function initUpload(id,inputId,rootPath,uploadId,curUser,curDate){
	
	$("#"+id).on("click",function(){
		//业务Id
		var bussId = $("#businessKey").val();
		//密级元素对象
		var secretObj = document.getElementsByName('m:bpm_businessed:secret_level')[0];
		//密级
		var secretCode;
		var content = "<div><select style='width:150px' id='attachSecret'>";
		if(bussId){
			$.ajax({

	            type: "get",

	            url: __ctx+"/common/doc/getParams.ht",

	            data: {

	                id: bussId,

	                tableName: "BPM_BUSINESSED",

	                column: "SECRET_LEVEL",

	                idName: "BUSINESS_ID"

	            },
	            
	            cache:false,

	            async: false,

	            success: function (result) {

	            	secretCode = result.SECRET_LEVEL;

	            }

	        });
		}else{
			if(secretObj){
				secretCode = secretObj.value;
			}else{
				return;
			}
		}
		
		$.ajax({  
			 type : "get",  
			  url : __ctx+"/platform/system/dicData/getList.ht",  
			  data : {dicId:"mjSelect"},  
			  async : false,  
			  cache:false,
			  success : function(result){ 
				var len = result.length;
				for(var i=0;i<len;i++){
					if(!secretCode){
						content += "<option value='000'>普通</option>";
						break;
					}else{
						if(secretCode>result[i].code||secretCode==result[i].code){
							content += "<option value='"+result[i].code+"'>"+result[i].name+"</option>";
						}		
					}		
				}
			 }  
		});
			
		content += "</select></br><span style='color:red;font-size:14px;line-height:18px;'>*注：附件密级不能超过表单密级</span></div>";
		content += "<div style='margin-top:10px'><input type='button' id='ensure' class='inp-send input-btn attach_btn' style=' width:150px;'  value='选择文件'></div>";
		
		dialog = $.ligerDialog.open({
			height:180,
			width: 200,
			title : '选择附件密级',
			content: content
		});
			
		$("#ensure").bind("click",function(){
		});
		
		createUpload("ensure",inputId,rootPath,uploadId,curUser,curDate);
		//createUpload(id,inputId,rootPath,uploadId,curUser,curDate);
		
	});
	
	
	$(".del_btn").on("click",function(){
		var attachIds = $("input[name='attach_checkbox']:checked");
		var attachId = "";
		var attachPath = "";
		var newFileName = "";
		for(var i=0;i<attachIds.length;i++){
			attachId += $(attachIds[i]).val() + ","
			attachPath += $(attachIds[i]).parent().siblings().find("input[name='attachPath']").val() + ",";
			newFileName += $(attachIds[i]).parent().siblings().find("input[name='newFileName']").val() + ",";
		}

		if(attachIds.length==0){
			alert("请先选择附件!");
			return;
		}

		$.ajax({
			type: "post",
			cache:false,
			async:false, 
			url: rootPath+"/common/attach/deleteBtachFile.ht",
			data:{"attachId":attachId,"attachPath":attachPath,"newFileName":newFileName},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//通常情况下textStatus和errorThrown只有其中一个包含信息
				$.dialog({
					content : textStatus,
					title: "消息提示",
					lock : false,
					width: 200,
					time : 2000
				});
			},
			success: function(data){
				if(data.flag){
					for(var i=0;i<attachIds.length;i++){
						var trId = $(attachIds[i]).parents("tr").attr("id");
						$("#"+trId).remove();
					}
				}
			}
		});
	});
	
}


function createUpload(id,inputId,rootPath,uploadId,curUser,curDate){
	uploader = new plupload.Uploader({ //实例化一个plupload上传对象	
		runtimes: 'gears,html5,html4,silverlight,flash',
		browse_button :id,
		url : rootPath+'/common/attach/saveFile.ht',//上传文件路径
		//url : rootPath+'/upload.jsp',
		flash_swf_url : rootPath+'/main_oa/main_css/uploadjs/Moxie.swf',
		max_file_size : '100mb',//100b, 10kb, 10mb, 1gb
        //chunk_size : '100mb',//分块大小，小于这个大小的不分块
		silverlight_xap_url : rootPath+'/main_oa/main_css/uploadjs/Moxie.xap'
	});

	uploader.init(); //初始化

	var fileObj = "";

	//绑定文件添加进队列事件
	uploader.bind('FilesAdded',function(uploader,files){
		var attachSecret = $("#attachSecret option:selected").val();
		var attachSecretName = $("#attachSecret option:selected").text();
		var warnNum = 0;
		var warnType = "";
		for(var i in files){
			var fileName = files[i].name;
			var fileType = files[i].type;
			var re;
			$.ajax({  
				type : "get",  
				url : __ctx+"/platform/system/dicData/getList.ht",  
				data : {dicId:"fileType"},  
				async : false,  
				success : function(result){
					var len = result.length;
					var valid = "";
					for(var m=0;m<len;m++){
						if(m==len-1){
							warnType += result[m].code;
							valid += "(" + result[m].code + ")";
						}else{
							warnType += result[m].code + ",";
							valid += "(" + result[m].code + ")|";
						}	
					}
					re = new RegExp(valid,"ig");
				}  
			});
			
			var typeArr = transType(fileType);
			if(typeArr.length==0){
				fileType = getExtName(fileName);
				if(attachSecret>001){
					if(fileType!='pdf'){
						uploader.removeFile(files[i]);
						warnNum++;
					}
				}else{
					if(!re.test(fileType)){
						uploader.removeFile(files[i]);
						warnNum++;
					}
				}
				
			}else{
				var validNum = 0;
				for(var n in typeArr){	
					var ext = getExtName(fileName);
					fileType = typeArr[n];
					if(attachSecret>001){
						
						if(ext==fileType){
							if(fileType!='pdf'){
								validNum++;
							}
						}else{
							if(ext!='pdf'){
								validNum++;
							}
						}	
						
						if(validNum==typeArr.length){
							uploader.removeFile(files[i]);
							warnNum++;
						}
					}else{
						if(ext==fileType){
							if(!re.test(fileType)){
								validNum++;
							}
						}else{
							if(!re.test(ext)){
								validNum++;
							}
						}
						
						
						if(validNum==typeArr.length){
							uploader.removeFile(files[i]);
							warnNum++;
						}
					}
					
				}
			}				
			
		}
		
		//如果是密件
		if(attachSecret>001){
			if(warnNum>0){
				$.ligerDialog.warn("密件附件只支持pdf格式", '提示');
				return;
			}
		}else{
			if(warnNum>0){
				$.ligerDialog.warn("只支持"+warnType+"等格式", '提示');
				return;
			}
		}
		
		
		uploader.settings.url = rootPath+'/common/attach/saveFile.ht?attachSecret='+attachSecret+'&attachSecretName='+encodeURI(encodeURI(attachSecretName));
		uploader.start();
		
	});



	//绑定文件上传进度事件
	uploader.bind('UploadProgress',function(uploader,file){
		$('#file-'+file.id+' .percent').text(file.percent + '%');
		$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
	});

    uploader.bind('UploadComplete',function(uploader,file){
		
	});

	uploader.bind("FileUploaded",function(up, file, info){
		var str = (info.response).replace(/(^\s*)|(\s*$)/g,'');
		var data = $.parseJSON(str);
		if(data.status){
			var file_name = file.name; //文件名
			var file_type = file.type;
			var file_size = file.size;
			var attachSecret = data.attachSecret;
			var attachSecretName = data.attachSecretName;
			if(file_type == "" || file_type.indexOf("application/")>=0){file_type = getExtName(file_name);}
			//构造html来更新UI
			var size = Math.round(file_size/1024)>0?(Math.round(file_size/1024)+"/KB"):(file_size+"/B")
			var htm = "<tr id='file-" + data.attachId +"' align='center'><td style='width:25px;'><input type='hidden' name='attachSecret' value='"+attachSecret+"'><input name='attach_checkbox' value='"+data.attachId+"' type='checkbox'/></td><td class='file-name file-info' style='word-wrap: break-word;'><a href='javascript:;' class='download_btn'>"
						+"【"+attachSecretName+"】"+file_name+" 【"+curUser+"，"+getNowDate()+"】</a><input type='hidden' name='fileName' value='"+file_name+"' /></td><td class='file_type hidden'><input type='hidden' name='attachIds' value='"+data.attachId+"' /><input type='hidden' name='newFileName' value='' /><input type='hidden' value='' name='attachPath' />"
						+file_type+"</td><td class='file_size hidden'>"
						+size+"</td><td align='left' class='hidden'><div class='progress' ><a class='percent'></a></div></td><td class='hidden'><input type='button' class='input-btn delete_btn' value='删除' /><input type='button' class='input-btn view_btn' value='预览' /><input type='button' class='input-btn download_btn' value='下载' /></td></tr>";
			//var html = '<li id="file-' + files[i].id +'"><p class="file-name">' + file_name + '</p><p class="progress"></p></li>';
			$(htm).appendTo('#file-list');
			closeEmerg();

			$(".download_btn").on("click", function(event){
				  var attachId = $(this).parent().siblings().find("input[name='attach_checkbox']").val();
				  var fileName = $(this).siblings("input[name='fileName']").val();
				  var attachPath = $(this).parent().siblings().find("input[name='attachPath']").val();
				  var newFileName = $(this).parent().siblings().find("input[name='newFileName']").val();
				  fileName = encodeURI(encodeURI(fileName));
				  if(attachId!=""){
					  window.location.href = rootPath+"/common/attach/download.ht?attachId="+attachId+"&isDownload=true";
				  }else{

					$.dialog({
						content : '请先上传文件！',
						title: "消息提示",
						lock : false,
						width: 200,
						time : 2000
					});
					closeEmerg();
					return;
				  }
			});

			$(".delete_btn").on("click",function(){
				var attachId = $(this).parent().siblings().find("input[name='attachIds']").val();
				var attachPath = $(this).parent().siblings().find("input[name='attachPath']").val();
				var newFileName = $(this).parent().siblings().find("input[name='newFileName']").val();
				var trId = $(this).parents("tr").attr("id");
				if(attachId!=""){
					$.ajax({
						type: "post",
						cache:false,
						async:false, 
						url: rootPath+"/common/attach/deleteFile.ht",
						data:{"attachId":attachId,"attachPath":attachPath,"newFileName":newFileName},
						dataType: "json",
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							//通常情况下textStatus和errorThrown只有其中一个包含信息
							$.dialog({
								content : textStatus,
								title: "消息提示",
								lock : false,
								width: 200,
								time : 2000
							});
							closeEmerg();
						},
						success: function(data){
							if(data.flag){
								$("#"+trId).remove();		
							}
						}
					});
					closeEmerg();
				}else{
					$.dialog({
						content : '请先上传文件！',
						title: "消息提示",
						lock : false,
						width: 200,
						time : 2000
					});
					closeEmerg();
					return;
				}
			
			});

			$(".view_btn").on("click",function(){
				var attachId = $(this).parent().siblings().find("input[name='attachIds']").val();
				var attachPath = $(this).parent().siblings().find("input[name='attachPath']").val();
				var newFileName = $(this).parent().siblings().find("input[name='newFileName']").val(); 
				var url = rootPath+"/common/doc/getAttach.ht";
				$.ajax({
					url:url,
					data:{
						attachId:attachId
					},
					success:function(data){
						if(data.status!=1){
							alert("预览出错，请联系管理员！");
						}else{
							var attach = data.attach;
							var path = rootPath+"/common/doc/file_"+attachId+".ht";
							if(/(doc)|(docx)|(xls)|(xlsx)|(ppt)|(pptx)/ig.test(attach.attach_type)){
								var h=screen.availHeight-35;
								var w=screen.availWidth-5;
								
								var vars="top=0,left=0,height="+h+",width="+w+",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";
								var showUrl = rootPath+"/common/doc/viewAttach.ht?attachId=" + attachId + "&ext="+attach.attach_type;
								// /bpmx/platform/system/sysFile/file_10000000500000.ht
								if(/(doc)|(docx)/ig.test(attach.attach_type)){//word才支持在线编辑
									showUrl+="&rights=w";
								}else{
									showUrl+="&rights=r";
								}
								
								window.open(showUrl,"myWindow",vars);
							}else{
								window.open(path,'_blank');	
							}
						}
					}
					
					
				});
				
			});
		}

    });

	//上传按钮
	$('#'+uploadId).click(function(){
		uploader.start(); //开始上传
	});


	function getNowDate() {
		var date = new Date();
		var seperator1 = "-";
		var seperator2 = ":";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
				+ " " + date.getHours() + seperator2 + date.getMinutes();
		return currentdate;
	}


	
	
	/**常用MIME类型
	 * 转化文件类型
	 * @param fileType(文件类型)
	 */
	function transType(fileType){
		var typeArr = [];
		switch(fileType) {	
			case "image/jpeg":
				typeArr.push("jpe");
				typeArr.push("jpg");
				typeArr.push("jpeg");
				break;
			case "image/png":
				typeArr.push("png");
				break;
			case "image/gif":
				typeArr.push("gif");
				break;
			case "application/msword":				
				typeArr.push("dot");
				typeArr.push("doc");
				break;
			case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
				typeArr.push("docx");
				break;
			case "application/vnd.ms-excel":
				typeArr.push("xla");
				typeArr.push("xlc");
				typeArr.push("xls");
				typeArr.push("xlm");
				typeArr.push("xlt");
				typeArr.push("xlw");
				break;
			case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
				typeArr.push("xlsx");
				break;
			case "application/vnd.ms-powerpoint":
				typeArr.push("ppt");
				break;
			case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
				typeArr.push("pptx");
				break;
			case "application/rtf":
				typeArr.push("rtf");
				break;
			case "text/html":
				typeArr.push("htm");
				typeArr.push("html");
				break;
			case "application/x-shockwave-flash":
				typeArr.push("swf");
				break;
			case "image/bmp":
				typeArr.push("bmp");
				break;
			case "video/mp4":
				typeArr.push("mp4");
				break;
			case "video/x-ms-wmv":
				typeArr.push("wmv");
				break;
			case "video/x-ms-wm":
				typeArr.push("wm");
				break;
			case "video/vnd.rn-realvideo":
				typeArr.push("rv");
				break;
			case "audio/mp3":
				typeArr.push("mp3");
				break;
			case "audio/x-ms-wma":
				typeArr.push("wma");
				break;
			case "audio/wav":
				typeArr.push("wav");
				break;
			case "application/x-cedx":
				typeArr.push("ceb");
				typeArr.push("cebx");
				break;
			case "application/pdf":
				typeArr.push("pdf");
				break;
			case "text/plain":
				typeArr.push("txt");
				break;
			case "text/css":
				typeArr.push("css");
				break;
			case "text/javascript":
				typeArr.push("js");
				break;
			case "image/tiff":
				typeArr.push("tif");
				typeArr.push("tiff");
				break;	
			case "application/x-zip-compressed":
				typeArr.push("zip");
				break;
			case "text/xml":
				typeArr.push("xml");
				break;
			case "image/bmp":
				typeArr.push("bmp");
				break;
			case "message/rfc822":
				typeArr.push(".mht");
				typeArr.push(".mhtml");
				break;
			case "application/x-compress":
				typeArr.push(".z");
				break;
			case "video/x-msvideo":
				typeArr.push(".avi");
				break;

		}
		return typeArr;
	}
	
	/**
	 * 获取文件后缀
	 * @param fileName(文件名称)
	 * @returns
	 */
	function getExtName(fileName){
		var ext = "";
		if(fileName.lastIndexOf('.')>0){
			ext = fileName.substring(fileName.lastIndexOf('.')+1);
		}
		return ext;
	}
}

function closeEmerg(){
	//dialog.close();
}

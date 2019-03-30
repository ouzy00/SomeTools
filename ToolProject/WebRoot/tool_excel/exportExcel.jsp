<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Excel相关工具</title>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.11.0.min.js"></script>
  </head>
  
  <body>
    Excel相关工具 <br><br><br>
   	<a href="javascript:void(0);" onclick="document.getElementById('downloadForm').submit();" id="agree" class="determine">使用a标签下载Excel模板</a>
	<form id="downloadForm" method="post" action="DownloadExcelServlet"></form>
	<br>
	<form id="uploadForm" method="post" action="UploadExcelServlet" enctype="multipart/form-data">
		<input type="file" name="theFile" id="theFile" style="height: 24px;width: 230px;line-height: 24px;"/>
		<button onclick="fnUpload()">上传Excel文件</button>
	</form>
  </body>
</html>
<script type="text/javascript">
	//上传至后台并保存
	function fnUpload() {
		var file = $("#theFile").val();
		var filename=file.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
		var filetype=file.replace(/.+\./,"");   //正则表达式获取后缀
		if ("" == filename) {
			alert("请选择上传文件！", "warn");
			return;
		}
		if ('xls' != filetype && 'xlsx' != filetype) {
			alert("上传文件须为excel格式文件！", "warn");
			return;
		}
		document.getElementById('uploadForm').submit();
		
	}
</script>

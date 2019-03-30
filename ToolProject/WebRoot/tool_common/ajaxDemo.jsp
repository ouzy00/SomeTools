<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>一个简单的ajax示例</title>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.11.0.min.js"></script>
  </head>
  
  <body>
    <button type="button" onclick="fnRequest()">ajax方式Post请求servlet</button>
  </body>
</html>
<script type="text/javascript">
	function fnRequest(){
		$.ajax({
            url:"AjaxServlet",	//要请求的服务器url 
            data:null,			//入参，写法{method:"ajaxTest",val:value}
            async:true,			//是否为异步请求
            cache:false,		//是否缓存结果
            type:"POST",		//请求方式为POST
            dataType:"json",   	//服务器返回的数据是什么类型 
            success:function(result){  //这个方法会在服务器执行成功是被调用 ，参数result就是服务器返回的值(现在是json类型) 
                
            }
        });
	}
</script>


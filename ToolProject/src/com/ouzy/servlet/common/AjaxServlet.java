package com.ouzy.servlet.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
 
	/**
	 * Constructor of the object.
	 */
	public AjaxServlet() {
		super(); 
	}

	/**
	 * servlet声明周期
	 * 仅执行一次，在服务器端停止且卸载Servlet时执行该方法，有点类似于C++的delete方法。
	 * 一个Servlet在运行service()方法时可能会产生其他的线程，
	 * 因此需要确认在调用destroy()方法时，这些线程已经终止或完成。
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("doPost请求成功");
	}

	/**
	 * servlet声明周期
	 * 在Servlet的生命周期中，仅执行一次init()方法，它是在服务器装入Servlet时执行的，
	 * 可以配置服务器，以在启动服务器或客户机首次访问Servlet时装入Servlet。
	 * 无论有多少客户机访问Servlet，都不会重复执行init()；
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}

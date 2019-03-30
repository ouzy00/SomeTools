package com.ouzy.servlet.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel相关工具
 * @author OUZY
 *
 */
@WebServlet("/DownloadExcelServlet")
public class DownloadExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * dopost方法，此处用于excel模板下载功能
	 * @author OUZY
	 * @param request
	 * @param response
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OutputStream os = response.getOutputStream();
		InputStream is = null;
		XSSFWorkbook xssfworkbook = null;
		try {
			response.reset();// 防止IE缓存
			response.setHeader("Pragma", "public");//response.setHeader("pragma", "no-cache");
			//发送一个报头，告诉浏览器当前页面不进行缓存，每次访问的时间必须从服务器上读取最新的数据
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setDateHeader("Expires", 0);
			response.setContentType("request/vnd.ms-excel");
			String filename = null;
			String path = request.getSession().getServletContext().getRealPath("tool_excel/名单模板.xlsx");
			filename = "名单模板.xlsx";
			response.addHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes("GBK"), "ISO8859-1"));
			//获取文件路径，如果是war包的方式，getRealPath方式会返回null，这种情况下通过getResourceAsStream取得文件流
			if(path == null){
	        	 is = (FileInputStream) request.getSession().getServletContext().getResourceAsStream(path);
	        }else{
	        	File uploadFile = new File(path);
				is = new FileInputStream(uploadFile);
	        }
			// 通过模板得到一个可写的Workbook：如果xls则用HSSFWorkbook
			xssfworkbook = new XSSFWorkbook(is);
			xssfworkbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
				if (is != null) {
					is.close();
				}
				if (xssfworkbook != null) {
					xssfworkbook.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
	}

}

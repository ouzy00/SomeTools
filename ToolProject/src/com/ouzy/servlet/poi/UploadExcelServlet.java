package com.ouzy.servlet.poi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import com.ouzy.util.poi.ExcelUtil;

@WebServlet("/UploadExcelServlet")
public class UploadExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		RequestContext requestContext = new ServletRequestContext(request);
		if (FileUpload.isMultipartContent(requestContext)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(50*1024*1024); // 文件大小
			List items = null;
			InputStream inputStream = null;
			try {
				items = upload.parseRequest(request);
				Iterator it = items.iterator();
				int rowStart = 2;// 默认起始行
				String filename = "CELL_A,CELL_B,CELL_C,CELL_D,CELL_E,CELL_F,CELL_G,CELL_H,CELL_I,CELL_J,CELL_K,CELL_L,CELL_M,CELL_N,CELL_O,CELL_P,CELL_Q,CELL_R";
				while (it.hasNext()) {
					FileItem fileItem = (FileItem) it.next();
					// 后缀判断
					if (fileItem.getName() == null || (!fileItem.getName().endsWith(".xls") && !fileItem.getName().endsWith(".xlsx"))) {
						continue;
					}
					inputStream = fileItem.getInputStream();
					List<Map<String, String>> list1 = ExcelUtil.getExcelInputStream2ObjectListWithParam(inputStream, filename, rowStart,0, 0, 0);
					System.out.println("list:"+list1.size());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

package com.ouzy.util.poi;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.ouzy.util.common.ValidateUtil;

/**
 * 解析excel工具类
 * @author OUZY
 *
 */
public class ExcelUtil {

	/**
	 * 根据流类型来构建xls或者xlsx对象
	 * @param inputStream
	 * @return
	 * @author OUZY
	 */
	public static Workbook chooseWorkbook(InputStream inputStream) {
		BufferedInputStream buf = new BufferedInputStream(inputStream);
		try {
			if (POIFSFileSystem.hasPOIFSHeader(buf)){
				return new HSSFWorkbook(buf); //.xls
			} else if(POIXMLDocument.hasOOXMLHeader(buf)){
				return new XSSFWorkbook(OPCPackage.open(buf)); //.xlsx
			} else {
				System.out.println("非法的Excel表格文件");
			}
		} catch (IOException e) {
			System.out.println("非法的Excel表格文件");
		} catch (InvalidFormatException e) {
			System.out.println("非法的Excel表格文件");
		} finally{
			
		}
		return null;
	}
	
	/**
	 * 关闭打开的文件
	 * @param workbook
	 * @author OUZY
	 */
	public static void closeWorkbook(Workbook workbook){
		if (workbook == null) return;
		try {
			if (workbook instanceof XSSFWorkbook) {
				((XSSFWorkbook)workbook).getPackage().close();
			} else {
			//	workbook.close();
			}
		} catch (IOException e) {
		}
	}
	
	
	/**
	 * 通过输入Excel文件字节流(以Excel文件形式)转化为对象列表
	 * @param is
	 * 				输入Excel文件流
	 * @param fieldNames
	 * 				字段名列表，以,分隔
	 * @param rowStart
	 * 				从表格第几行开始解析
	 * @param rowEnd
	 * 				从表格第几行结束解析
	 * @param cellStart
	 * 				从表格第几列开始解析
	 * @param cellEnd
	 * 				从表格第几列结束解析
	 * @param pch
	 * 				批次号
	 * @return
	 * @throws Exception
	 * @author OUZY
	 * 表格样式：
	 *  ———— ———— ———— ———— ———— ———— ———— ———— ———— 
	 * |标题1|标题2|标题3|标题4|标题5|标题6|标题7|标题8|标题9|
	 *  ———— ———— ———— ———— ———— ———— ———— ———— ———— 
	 * |A1	|B1	 |C1  |D1  |E1  |F1  |G1  |H1  |I1  |
	 *  ———— ———— ———— ———— ———— ———— ———— ———— ———— 
	 * |A2	|B2	 |C2  |D2  |E2  |F2  |G2  |H2  |I2  |
	 *  ———— ———— ———— ———— ———— ———— ———— ———— ———— 
	 *  ...
	 *  ...
	 *  ...
	 *     _____________
	 *    |sheet1|sheet2|
	 * ￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣ 
	 */
	public static List<Map<String,String>> getExcelInputStream2ObjectListWithParam(InputStream is, String fieldNames, 
			int rowStart, int rowEnd, int cellStart, int cellEnd)
	throws Exception {
		List<Map<String,String>> retList = new ArrayList<Map<String,String>>(); //声明定义返回结果list
		String[] fields = fieldNames.split(","); //取表格每一列字段名称
		Workbook workbook = chooseWorkbook(is);	//创建excel对象
		Sheet sheet0 = workbook.getSheetAt(0); //创建excel底下的sheet对象
		int rowNumber = rowStart;	//从表格第几行开始解析
		int rows = (sheet0.getLastRowNum()-sheet0.getFirstRowNum()) - rowEnd; //获取需要解析表格数据的全部记录行数
		Map<String,String> map = null; //创建map对象存储表格每一格数据
		for (;rowNumber < rows + 1; rowNumber ++){ //开始解析表格第rowNumber行
			map = new HashMap<String,String>(); //每解析完一行清除map对象，用于下一行数据存储
			if(ValidateUtil.isEmpty(sheet0.getRow(rowNumber))){//遇到空行就跳过
				continue;
			}
			for (int i = cellStart; i < fields.length + cellStart; i ++){ //开始解析表格第rowNumber行第i列
				Cell cell = sheet0.getRow(rowNumber).getCell(i); //rowNumber行，i列
				if (!ValidateUtil.isEmpty(cell)) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA: // 公式类型
						try {
							map.put(fields[i],String.valueOf(cell.getNumericCellValue())); // 一般公式类型的单元格最终结果应该是数字
						} catch (IllegalStateException e) {
							map.put(fields[i], String.valueOf(cell.getRichStringCellValue()));
						}
						break;
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字类型
						map.put(fields[i],String.valueOf(cell.getNumericCellValue()));
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串类型
						map.put(fields[i],String.valueOf(cell.getRichStringCellValue()));
						break;
					}
				} else {
					map.put(fields[i], null); // 无数据情况
				}
			}
			//解析完一行存储一行list
			if (map.containsValue(null)) { //判断map中是否有为null的值
				for (int i = 0; i < fields.length; i++) {
					if (!ValidateUtil.isEmpty(map.get(fields[i]))) { //如果map中有值不为null的情况，则证明excel此行不为空，如果map全部值都为null，则证明excel此行实际为空行
						retList.add(map); //每解析完一行存储一条list
						break;
					}
				}
			}else { //如果map中全部值都不为null，则证明excel此行绝逼不为空行
				retList.add(map); //每解析完一行存储一条list
			}
		}
		closeWorkbook(workbook); //关闭打开文件
		return retList;
	}
}

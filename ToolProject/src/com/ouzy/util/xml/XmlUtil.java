package com.ouzy.util.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.ouzy.util.common.ValidateUtil;

public class XmlUtil {
	
	public static void main(String[] args) {
//		解析以下xml串：
//		<?xml version="\&quot;1.0\&quot;" encoding="\&quot;utf-8\&quot;?"?>
//		<result>
//		    <code>1</code>
//		    <message/>
//		    <output>666</output>
//		</result>
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result><code>1</code><message/><output>666</output></result>";
		xmlMap(xmlString);
		
//		解析以下xml串：
//		<?xml version="\&quot;1.0\&quot;" encoding="\&quot;utf-8\&quot;?"?>
//		<result>
//		    <code>1</code>
//		    <message/>
//		    <output>
//				<key>key</key>
//		    </output>
//		</result>
		String xmlString2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result><code>1</code><message/><output><key>key</key></output></result>";
		xml2Map(xmlString2);
	}
	
	
	/**
	 * 方法说明:将传入的xml转换为Map对象（针对根节点下不止一层子节点的情况）
	 * 
	 * @param xml
	 *            传入xml字符串
	 * @return Map
	 * @author OUZY
	 * @date 2019-4-03
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> xml2Map(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		SAXBuilder sb = new SAXBuilder(); //新建一个SAX的JDOM解析器
		Reader reader = new StringReader(xml); //新建字符读取流
		try {
			Document document = sb.build(reader); //新建Document对象，相当于可操作的xml文档对象
			Element rootElement = document.getRootElement(); //获取根对象
			List list = (List) rootElement.getChildren(); //获取第一层所有子节点
			if (rootElement.getChildren().size() != 0) { 
				Element el = null;
				for (Iterator iter = list.iterator(); iter.hasNext();) { //遍历第一层所有子节点数据
					el = (Element) iter.next();
					if (!ValidateUtil.isEmpty(el.getChildren())) { //判断如果第二层字节点不为空
						List list2 = el.getChildren(); //以下解析第二层子节点
						Element el2 = null;
						for (Iterator iter2 = list2.iterator(); iter2.hasNext();) {
							el2 = (Element) iter2.next();
							map.put(el2.getName(), el2.getText());
						}
					}
					map.put(el.getName(), el.getText());
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	

	/**
	 * 方法说明:将传入的xml转换为Map对象（针对根节点下只有一层子节点的情况）
	 * 
	 * @param xml
	 *            传入xml字符串
	 * @return Map
	 * @author OUZY
	 * @date 2019-4-03
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> xmlMap(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		SAXBuilder sb = new SAXBuilder(); //新建一个SAX的JDOM解析器
		Reader reader = new StringReader(xml); //新建字符读取流
		try {
			Document document = sb.build(reader); //新建Document对象，相当于可操作的xml文档对象
			Element rootElement = document.getRootElement(); //获取根对象
			List list = (List) rootElement.getChildren(); //获取第一层所有子节点
			if (rootElement.getChildren().size() != 0) { 
				Element el = null;
				for (Iterator iter = list.iterator(); iter.hasNext();) {//遍历第一层所有子节点数据
					el = (Element) iter.next();
					map.put(el.getName(), el.getText());
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}

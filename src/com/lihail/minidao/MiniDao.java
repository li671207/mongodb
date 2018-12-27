package com.lihail.minidao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MiniDao {
	
	/**
	 * 新增数据库
	 * @param databaseName
	 */
	public void createDatabase(String xmlPath, String databaseName) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlPath);
			Element root = document.getRootElement();
			List<Element> databaseElements = root.elements();
			if (!isElementExists(databaseName, databaseElements)) {
				root.addElement("data").addAttribute("name", databaseName);
			}
			writeDocument(xmlPath, document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Document写入xml
	 * @param xmlPath
	 * @param document
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void writeDocument(String xmlPath, Document document) throws FileNotFoundException, IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(new File(xmlPath)));
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("UTF-8");
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.write(document);
		xmlWriter.flush();
		xmlWriter.close();
	}

	/**
	 * 根据name属性，查看元素是否已存在
	 * @param name
	 * @param elements
	 */
	private boolean isElementExists(String name, List<Element> elements) {
		for (Element element : elements) {
			if (element.attributeValue("name").equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 新增集合
	 * @param xmlPath
	 * @param databaseName
	 * @param collectionName
	 */
	public void createCollection(String xmlPath, String databaseName, String collectionName) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlPath);
			Element root = document.getRootElement();
			List<Element> databaseElements = root.elements();
			for (Element databaseElement : databaseElements) {
				if (databaseElement.attributeValue("name").equalsIgnoreCase(databaseName)) {
					List<Element> collectionElements = databaseElement.elements();
					if (!isElementExists(collectionName, collectionElements)) {
						databaseElement.addElement("collection").addAttribute("name", collectionName);
					}
					break;
				}
			}
			writeDocument(xmlPath, document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 插入数据
	 * @param xmlPath
	 * @param databaseName
	 * @param collectionName
	 * @param dataMap
	 */
	public void insertData(String xmlPath, String databaseName, String collectionName, Map<String, Object> dataMap) {
		
	}
	
	public void removeData(String xmlPath, String databaseName, String collectionName, String id) {
		
	}
	
	public void updateData(String databaseName, String collectionName, Map<String, Object> dataMap) {
		
	}
	
	public String findById(String databaseName, String collectionName, String id) {
		return "";
	}
	
	public List<String> findAll(String databaseName, String collectionName) {
		return null;
	}
}

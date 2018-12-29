package com.lihail.mongodb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONObject;

public class MongodbDao {
	
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
				root.addElement("database").addAttribute("name", databaseName);
			}
			writeDocument(xmlPath, document);
			System.out.println("******创建数据库成功******");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			System.out.println("******新增集合成功******");
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
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlPath);
			Element root = document.getRootElement();
			List<Element> databaseElements = root.elements();
			for (Element databaseElement : databaseElements) {
				if (databaseElement.attributeValue("name").equalsIgnoreCase(databaseName)) {
					List<Element> collectionElements = databaseElement.elements();
					for (Element collectionElement : collectionElements) {
						if (collectionElement.attributeValue("name").equalsIgnoreCase(collectionName)) {
							collectionElement.addElement("data").addText(new JSONObject(dataMap).toJSONString());
							break;
						}
					}
				}
			}
			writeDocument(xmlPath, document);
			System.out.println("******新增记录成功******");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id删除记录
	 * @param xmlPath
	 * @param databaseName
	 * @param collectionName
	 * @param id
	 */
	public void removeData(String xmlPath, String databaseName, String collectionName, String id) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlPath);
			Element root = document.getRootElement();
			List<Element> databaseElements = root.elements();
			for (Element databaseElement : databaseElements) {
				if (databaseElement.attributeValue("name").equalsIgnoreCase(databaseName)) {
					List<Element> collectionElements = databaseElement.elements();
					for (Element collectionElement : collectionElements) {
						if (collectionElement.attributeValue("name").equalsIgnoreCase(collectionName)) {
							List<Element> dataElements = collectionElement.elements("data");
							for (Element dataElement : dataElements) {
								if (dataElement.getTextTrim().indexOf(id) >= 0) {
									collectionElement.remove(dataElement);
									break;
								}
							}
						}
					}
				}
			}
			writeDocument(xmlPath, document);
			System.out.println("******删除记录成功******");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id修改记录
	 * @param xmlPath
	 * @param databaseName
	 * @param collectionName
	 * @param dataMap
	 */
	public void updateData(String xmlPath, String databaseName, String collectionName, Map<String, Object> dataMap) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlPath);
			Element root = document.getRootElement();
			List<Element> databaseElements = root.elements();
			for (Element databaseElement : databaseElements) {
				if (databaseElement.attributeValue("name").equalsIgnoreCase(databaseName)) {
					List<Element> collectionElements = databaseElement.elements();
					for (Element collectionElement : collectionElements) {
						if (collectionElement.attributeValue("name").equalsIgnoreCase(collectionName)) {
							List<Element> dataElements = collectionElement.elements("data");
							for (Element dataElement : dataElements) {
								if (dataElement.getTextTrim().indexOf((String)dataMap.get("_id")) >= 0) {
									dataElement.setText(new JSONObject(dataMap).toJSONString());
									break;
								}
							}
						}
					}
				}
			}
			writeDocument(xmlPath, document);
			System.out.println("******修改记录成功******");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id查找记录
	 * @param xmlPath
	 * @param databaseName
	 * @param collectionName
	 * @param id
	 * @return String
	 */
	public String findById(String xmlPath, String databaseName, String collectionName, String id) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlPath);
			Element root = document.getRootElement();
			List<Element> databaseElements = root.elements();
			for (Element databaseElement : databaseElements) {
				if (databaseElement.attributeValue("name").equalsIgnoreCase(databaseName)) {
					List<Element> collectionElements = databaseElement.elements();
					for (Element collectionElement : collectionElements) {
						if (collectionElement.attributeValue("name").equalsIgnoreCase(collectionName)) {
							List<Element> dataElements = collectionElement.elements("data");
							for (Element dataElement : dataElements) {
								if (dataElement.getTextTrim().indexOf(id) >= 0) {
									return dataElement.getTextTrim();
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 查询集合记录
	 * @param xmlPath
	 * @param databaseName
	 * @param collectionName
	 * @return
	 */
	public List<String> findAll(String xmlPath, String databaseName, String collectionName) {
		List<String> result = new ArrayList<>();
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(xmlPath);
			Element root = document.getRootElement();
			List<Element> databaseElements = root.elements();
			for (Element databaseElement : databaseElements) {
				if (databaseElement.attributeValue("name").equalsIgnoreCase(databaseName)) {
					List<Element> collectionElements = databaseElement.elements();
					for (Element collectionElement : collectionElements) {
						if (collectionElement.attributeValue("name").equalsIgnoreCase(collectionName)) {
							List<Element> dataElements = collectionElement.elements("data");
							for (Element dataElement : dataElements) {
								result.add(dataElement.getTextTrim());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
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
}

package com.lihail.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.lihail.minidao.MiniDao;

public class MiniDaoTest {

	public static void main(String[] args) {
		MiniDao miniDao = new MiniDao();
		String xmlPath = "minidao.xml";
		String databaseName = "miniDb";
		String collectionName = "miniCollection";
		Map<String, Object> dataMap = new HashMap<String, Object>(){{
			put("_id", UUID.randomUUID());
			put("name", "minidao");
			put("gender", "M");
			put("age", 33);
		}};
		Map<String, Object> updateDataMap = new HashMap<String, Object>(){{
			put("_id", "3696e3c8-fc56-4e24-a812-96eb720b6896");
			put("name", "updateminidao");
			put("gender", "F");
			put("age", 123);
		}};
		String id = "3696e3c8-fc56-4e24-a812-96eb720b6896";
//		miniDao.createDatabase(xmlPath, databaseName);//新增数据库
//		miniDao.createCollection(xmlPath, databaseName, collectionName);//新增集合
//		miniDao.insertData(xmlPath, databaseName, collectionName, dataMap);//插入记录
//		miniDao.updateData(xmlPath, databaseName, collectionName, updateDataMap);//修改记录
//		System.out.println(miniDao.findById(xmlPath, databaseName, collectionName, id));//根据id查询记录
//		System.out.println(miniDao.findAll(xmlPath, databaseName, collectionName));;//查询集合
		miniDao.removeData(xmlPath, databaseName, collectionName, id);//删除记录
	}
}

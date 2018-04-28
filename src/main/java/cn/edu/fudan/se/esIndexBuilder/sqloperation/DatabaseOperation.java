/**
 * 
 */
package cn.edu.fudan.se.esIndexBuilder.sqloperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fancying
 *
 */
public class DatabaseOperation {

	private Connection conn = null;
	private Statement sqlStatement = null;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.141.221.73:3306/codehub?user=root&password=root&useSSL=false";

	public DatabaseOperation() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url);
			sqlStatement = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
			sqlStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * repository query
	 * 
	 * 
	 * public List<Map<String, Object>> queryRepo(int start, int once) { // TODO
	 * Auto-generated method stub String query =
	 * "select id,repos_name, local_addr, stars,  owner_name, primaryLanguage, updated_at, license_name, description, languages FROM repository_java limit"
	 * + start + "," + once; System.out.println(query); List<Map<String,Object>>
	 * resultList = new ArrayList<Map <String,Object>>(); try { ResultSet result =
	 * sqlStatement.executeQuery(query); List<String> columns = getColumn(result);
	 * while(result.next()){ Map<String,Object> oneResult = new
	 * HashMap<String,Object>(); for(int i=0;i<columns.size();i++) {
	 * if(columns.get(i).contains("_at")) { oneResult.put(columns.get(i),
	 * result.getDate(columns.get(i))); }else { oneResult.put(columns.get(i),
	 * result.getObject(columns.get(i))); } } resultList.add(oneResult); }
	 * }catch(SQLException e) { e.printStackTrace(); } return resultList; }
	 */

	/*
	 * issue query need add repository name
	 * 
	 * public List<Map<String, Object>> queryIssue(int start,int once){ String query
	 * =
	 * "select i.id, title,repos_name, reporter, issue_index, i.created_at, closed_at,  body, state FROM issue as i join repository_java as r where i.repository_id = r.id limit"
	 * + start + "," + once; System.out.println(query); List<Map<String,Object>>
	 * resultList = new ArrayList<Map <String,Object>>(); try { ResultSet result =
	 * sqlStatement.executeQuery(query); List<String> columns = getColumn(result);
	 * while(result.next()){ Map<String,Object> oneResult = new
	 * HashMap<String,Object>(); for(int i=0;i<columns.size();i++) {
	 * if(columns.get(i).contains("_at")) { oneResult.put(columns.get(i),
	 * result.getDate(columns.get(i))); }else { oneResult.put(columns.get(i),
	 * result.getObject(columns.get(i))); } } resultList.add(oneResult); }
	 * }catch(SQLException e) { e.printStackTrace(); } return resultList; }
	 * 
	 * 
	 * @org.junit.Test public void Test() {
	 * 
	 * System.out.println("aaa");
	 * 
	 * new DatabaseOperation().queryAll("commits", 0, 10);
	 * 
	 * System.out.println("ccc"); }
	 */
	// repository_java issue commits
	public List<Map<String, Object>> queryAll(String tableName, int start, int once) {
		String query = "";

		if (tableName == "repository_java") {
			query = "select id,repos_name, local_addr, stars,  owner_name, primaryLanguage, updated_at, license_name, description, languages FROM repository_java limit "
					+ start + "," + once;
		} else if (tableName == "issue") {
			query = "select i.id, i.repository_id,title,repos_name, reporter, issue_index, i.created_at, closed_at,  body, state FROM issue as i join repository_java as r where i.repository_id = r.id limit "
					+ start + "," + once;
		} else {
			query = "SELECT * FROM commits limit " + start + "," + once;
			;
		}

		System.out.println(query);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			ResultSet result = sqlStatement.executeQuery(query);
			List<String> columns = getColumn(result);
			while (result.next()) {
				Map<String, Object> oneResult = new HashMap<String, Object>();
				for (int i = 0; i < columns.size(); i++) {
					if (columns.get(i).contains("_at") || columns.get(i).contains("_date")) {
						oneResult.put(columns.get(i), result.getDate(columns.get(i)));
					} else {
						oneResult.put(columns.get(i), result.getObject(columns.get(i)));
					}
				}
				resultList.add(oneResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	//

	/**
	 * @param result
	 * @return
	 */
	private List<String> getColumn(ResultSet rs) {
		List<String> list = new ArrayList<String>();
		ResultSetMetaData data;
		try {
			data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				String columnName = data.getColumnName(i);
				list.add(columnName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param tableName
	 * @param start
	 * @param once
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryAllbyId(String tableName, int start, int once, String id) {
		String query = "select * from" + tableName + "where " + id + " >= " + start + " limit " + once;
		System.out.println(query);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			ResultSet result = sqlStatement.executeQuery(query);
			List<String> columns = getColumn(result);
			while (result.next()) {
				Map<String, Object> oneResult = new HashMap<String, Object>();
				for (int i = 1; i <= columns.size(); i++) {
					if (columns.get(i).contains("_date") || columns.get(i).contains("_at")) {// 判断日期
						oneResult.put(columns.get(i), result.getDate(columns.get(i)));
					} else {
						oneResult.put(columns.get(i), result.getObject(columns.get(i)));
					}
				}
				list.add(oneResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}

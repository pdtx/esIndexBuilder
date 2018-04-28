/**
 * 
 */
package cn.edu.fudan.se.esIndexBuilder.Dao;

import java.util.List;
import java.util.Map;

import cn.edu.fudan.se.esIndexBuilder.esoperation.EsQuery;

/**
 * @author fancying
 *
 */
public class EsDao {
	private EsQuery esQuery;
	
	private String index = "codehub";
	
	public List<Map<String, Object>> queryRepo(String val){
		return esQuery.queryDocuments(index, "repository", val);
	}
	
	public List<Map<String, Object>> queryCommit(String val) {
		return esQuery.queryDocuments(index,"repository", val);
	}
	
	public List<Map<String, Object>> queryIssue(String val){
		return esQuery.queryDocuments(index, "issue", val);
	}
	
	public Map<String, Long> getResultNumber(String val){
		return esQuery.getResultNumber(val);
	}
	
	public List<Map<String, Object>> queryCode(String val){
		
		return null;
	}
}

/**
 * 
 */
package cn.edu.fudan.se.esIndexBuilder;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fdse.analyze.Analyzer;

//import com.fdse.analyze.Analyzer;

import cn.edu.fudan.se.esIndexBuilder.esoperation.EsIndex;
import cn.edu.fudan.se.esIndexBuilder.sqloperation.DatabaseOperation;

/**
 * @author fancying
 *
 */
public class ReadAndIndex {

	private DatabaseOperation database = null;
	private EsIndex es = null;
	private final int once = 10000;

	// private Analyzer analyzerCode = null;

	public ReadAndIndex() {
		database = new DatabaseOperation();
		es = new EsIndex();
		// analyzerCode = new Analyzer();
	}

	/**
	 * @param string
	 */
	public void indexTable(String tableName) {
		// TODO Auto-generated method stub
		int num = 0;
		List<Map<String, Object>> list = database.queryAll(tableName, 0, once);

		while ((list != null) && (list.size() != 0)) {
			es.addDocuments(list, "database", tableName);
			num += once;
			list = database.queryAll(tableName, num, once);
			System.out.println(tableName + "\t" + num + "------");
		}
	}
	
	public void createCodeIndex(int start) {
		int once = 1000;
		Analyzer analyzer = new Analyzer();
		List<Map<String, Object>> list = analyzer.getFileInfoFromRepository(start, start+once);
		
		while((list != null) && (list.size() != 0)) {
			es.addDocuments(list, "code", "java");
			start += once;
			list =  analyzer.getFileInfoFromRepository(start, start+once);
			System.out.println("code java" + "\t" + start + "------");
		}
	}
	
	@Test
	public void test() {

		System.out.println("begin");
		new Analyzer().getFileInfoFromRepository(10001, 10010);
		System.out.println("end");
	}

	/*
	 * public void indexTable(String tableName,int start) { int num = start;
	 * List<Map<String,Object>> list = database.queryAll(tableName, start, once);
	 * while((list != null)&& (list.size() != 0)) { es.addDocuments(list,
	 * "database", tableName); num += once; list = database.queryAll(tableName, num,
	 * once); System.out.println(tableName + "\t" + num +"------"); } }
	 * 
	 * public void indexTableById(String tableName,int start, String id) { int num =
	 * start; List<Map<String,Object>> list =
	 * database.queryAllbyId(tableName,start,once,id); while((list !=
	 * null)&&(list.size() != 0)) { es.addDocuments(list, "database",tableName); num
	 * += once; list = database.queryAllbyId(tableName, start, once, id);
	 * System.out.println(tableName + "\t" + num + "------"); }
	 * 
	 * 
	 * }
	 */
	public void close() {
		database.close();
	}
}

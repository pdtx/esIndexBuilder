/**
 * 
 */
package cn.edu.fudan.se.esIndexBuilder.esoperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;



/**
 * @author fancying
 *
 */
public class EsQuery {
	 TransportClient client = null;
	 
	 String type[] = new String[] {
		"repository","issues","commits"	 
	 };
	
	public EsQuery() {
		// TODO Auto-generated constructor stub
		client = ClientFactory.getTransportClient();
	}
	
	//query repository sort by name desc
	public  List<Map<String, Object>> queryDocuments(String index ,String type,String val) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		SearchResponse response = null;
		
		if (type == "repository") {
			response = client.prepareSearch(index)
					.setTypes(type)
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.queryStringQuery(val))
					.setSize(100)
					.addSort("name", SortOrder.DESC)
					.get();
		}else {
			response = client.prepareSearch(index)
					.setTypes(type)
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setQuery(QueryBuilders.queryStringQuery(val))
					.setSize(100)
					.get();
		}
		
		SearchHits hits = response.getHits();
		for(SearchHit hit: hits) {
			list.add(hit.getSourceAsMap());
		}
		return list;
	}
	
	
	public Map<String, Long> getResultNumber(String val) {
		Map<String, Long> rsMap = new HashMap<String, Long>();
		/*index include database code
		 * type include repository issue commit java 
		 * */
		for(String tp: type) {
			rsMap.put(tp, getNumber("database",tp,val));
		}
		rsMap.put("java",getNumber("code","java",val));
		
		return rsMap;
	}
	
	public Long getNumber(String index,String type,String val) {
		
		Long rs = null ;
		
		SearchResponse response = client.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.queryStringQuery(val))
				.setSize(0)		//do not return result 
				.get();
		
		rs = response.getHits().totalHits;
		
		return rs;
	}
	
	// which fields highlight
	public List<Map<String, Object>> queryDocumentsHighlight(String index,String type,String [] fields,String val){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<span >");
		highlightBuilder.postTags("</span>");
		for(String f : fields) {
			highlightBuilder.field(f);
		}
		
		SearchResponse response = client.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.multiMatchQuery(val, fields))
				.setSize(100)
				.highlighter(highlightBuilder)
				.get();
		
		for(SearchHit hit : response.getHits()) {
			Map<String, Object> oneResult = hit.getSourceAsMap();
			Map<String, HighlightField> highlightResult = hit.getHighlightFields();
			for(String f: fields) {
				HighlightField contentField = highlightResult.get(f);
				if(contentField != null) {
					Text[] texts = contentField.fragments();
					String temp = "";
					for(Text text : texts) {
						temp = temp + text;
					}
					oneResult.replace(f, temp);
				}
			}
			list.add(oneResult);
		}
		return list;
	}
	
	@Test
	public void test() {
		EsQuery esQuery = new EsQuery();
		List<Map<String, Object>> list =   esQuery.queryDocuments("repository", "java","CraftBukkit");
		if (list != null) {
			System.out.println("success");
		}else {
			System.out.println("failed");
		}
	}

}
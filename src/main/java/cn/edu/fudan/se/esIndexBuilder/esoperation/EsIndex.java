/**
 * 
 */
package cn.edu.fudan.se.esIndexBuilder.esoperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * @author fancying
 *
 */
public class EsIndex {
	TransportClient client = null;
	
	public EsIndex() {
		client = ClientFactory.getTransportClient();
	}
	
	
	
	public  Map<String, Object> getDocument(String index,String type,String id) {
		GetResponse response =client.prepareGet(index,type,id).get();
		Map<String, Object> map = response.getSource();
		return map;
	}
	
	/**
	 * @param list
	 * @param index : database and  code
	 * @param tableName
	 */
	public void addDocuments(List<Map<String, Object>> list, String index, String tableName) {
		// TODO Auto-generated method stub
		try {
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			for(Map<String, Object>map : list) {
				XContentBuilder xb = XContentFactory.jsonBuilder().startObject();
				for(String key : map.keySet()) {
					xb.field(key,map.get(key));
				}
				xb.endObject();
				bulkRequest.add(client.prepareIndex(index, tableName).setSource(xb));
			}
			BulkResponse bulkResponse = bulkRequest.get();
			if(bulkResponse.hasFailures()) {
				System.err.println(bulkResponse.buildFailureMessage());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	public List<Map<String, Object>> queryDocuments(String index,String type,String val){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		SearchResponse response = client.prepareSearch(index)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.queryStringQuery(val))
				.setFrom(0)
				.setSize(60)
				.setExplain(true)
				.get();
		SearchHits hits = response.getHits();
		for(SearchHit hit : hits) {
			list.add(hit.getSourceAsMap());
		}
		
		return list;
	}
	*/
	
	/*
	public  long countResult(String val) {
		long nbHits = 0;
		SearchRequestBuilder srBuilder = client
				.prepareSearch().setQuery(QueryBuilders.queryStringQuery(val)).setSize(1);
		MultiSearchResponse sr = client.prepareMultiSearch().add(srBuilder).get();
		
		for(MultiSearchResponse.Item item : sr.getResponses()) {
			SearchResponse response = item.getResponse();
			nbHits += response.getHits().getTotalHits();
		}
		
		return nbHits;
	}
	*/
}

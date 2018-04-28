/**
 * 
 */
package cn.edu.fudan.se.esIndexBuilder.esoperation;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

/**
 * @author fancying
 *
 */
public class ClientFactory {
	private static TransportClient client;

	static {

		// settings.builder().put("client.transport.sniff",true).build;
		if (client == null) {
			Settings settings = Settings.builder().put("cluster.name", "fdselasticSearch").build();
			// settings.builder().put("client.transport.sniff",true).build();
			client = new PreBuiltTransportClient(settings);

			TransportAddress transportAddress;
			try {
				transportAddress = new TransportAddress(InetAddress.getByName("10.141.221.86"), 9300);
				client.addTransportAddress(transportAddress);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static TransportClient getTransportClient() {
		return client;
	}

	@Test
	public void test() {

		System.out.println("twice");

		TransportClient c = new ClientFactory().getTransportClient();
		if (c == null)
			System.out.println("NULL");
		else
			System.out.println("not NULL");

	}

}

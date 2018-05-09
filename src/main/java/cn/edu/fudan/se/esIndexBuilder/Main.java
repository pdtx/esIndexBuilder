/**
 * 
 */
package cn.edu.fudan.se.esIndexBuilder;

/**
 * @author fancying
 *
 */
public class Main {
	public static void main(String[] args) {
		ReadAndIndex i = new ReadAndIndex();
		/*/
		//ElasticSearch6.x只允许在一个index里对一个type进行mapping
		//database table repository_java
		System.out.println("------repository_java------");
		i.indexTable("repository_java");
		
		
		// database table issue
		System.out.println("------issue------");
		i.indexTable("issue");
		
		*/
		//database table  commits
		System.out.println("--");
		i.indexTable("commits");
		
		
		System.out.println("end");
		
	}

}

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
		
		//database table repository_java
		System.out.println("------repository_java------");
		i.indexTable("repository_java");
		
		// database table issue
		System.out.println("------issue------");
		i.indexTable("issue");
		
		//database table  commits
		System.out.println("--");
		i.indexTable("commits");
		
		//
		System.out.println("");
		
	}

}

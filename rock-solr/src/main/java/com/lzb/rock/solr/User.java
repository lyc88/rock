package com.lzb.rock.solr;
//package com.jiahong.solr;
//
//import org.apache.solr.client.solrj.beans.Field;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// 
////jackSon注解：忽略未匹配到的字段
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class User {
// 
//	public User(String id, String name) {
//		super();
//		this.id = id;
//		this.name = name;
//	}
// 
//	public User() {
//		super();
//	}
// 
//	// solr查询若直接将数据转为对象，需要指定Field，该值需要和managed-schema配置Field的name一致
//	@Field("id")
//	private String id;
// 
//	@Field("name")
//	private String name;
// 
//	public String getId() {
//		return id;
//	}
// 
//	public void setId(String id) {
//		this.id = id;
//	}
// 
//	public String getName() {
//		return name;
//	}
// 
//	public void setName(String name) {
//		this.name = name;
//	}
// 
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", name=" + name + "]";
//	}
// 
//}

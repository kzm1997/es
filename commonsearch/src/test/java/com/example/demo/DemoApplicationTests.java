package com.example.demo;

import com.example.demo.entity.search.PostMainInfoSearch;
import com.example.demo.mapper.PostMainInfoSearchMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.mapper.ParseContext;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {
	
	@Autowired
	private ElasticsearchRestTemplate elasticsearchTemplate;

	@Autowired
	private PostMainInfoSearchMapper postMainInfoSearchMapper;


	@Test
	void contextLoads() {
		System.out.println(elasticsearchTemplate.getClass());
	}

	/**
	 * 插入数据
	 */
	@Test
	public void insertDoc(){
		PostMainInfoSearch postMainInfoSearch=new PostMainInfoSearch();
		postMainInfoSearch.setId(1).setAccId(1).setAccName("作者名称").setContent("测测试试").setCreateTime(LocalDateTime.now())
				.setUpdateTime(LocalDateTime.now()).setTitle("标题1").setTag("tag");

		IndexQuery indexQuery=new IndexQueryBuilder().withId(String.valueOf(postMainInfoSearch.getId()))
				.withObject(postMainInfoSearch).build();
		
		elasticsearchTemplate.index(indexQuery,IndexCoordinates.of("post_main_info"));


	}

	/**
	 * 创建索引
	 */
	@Test
	public void testCreateIndex(){
		elasticsearchTemplate.indexOps(IndexCoordinates.of("post_main_info")).create();
		
	   
		
		
	}

	/**
	 * 创建索引
	 */
	@Test
	public void testCreate(){
		elasticsearchTemplate.createIndex(PostMainInfoSearch.class);
	}
	
	@Test
	public void getDoc(){
		PostMainInfoSearch postMainInfoSearch = elasticsearchTemplate.get("3", PostMainInfoSearch.class, IndexCoordinates.of("post_main_info"));

		System.out.println(postMainInfoSearch);
	}
	
	@Test
	public void batchInsert(){
		List<IndexQuery>list=new ArrayList<>();


		
		list.add(new IndexQueryBuilder().withObject(new PostMainInfoSearch().setId(2).setAccId(1).setAccName("寇政民").setContent("ElasticsearchTemplate提供了创建索引的方法，但是不建议使用 " +
				"ElasticsearchTemplate 对索引进行管理（创建索引，更新映射，删除索引）").setCreateTime(LocalDateTime.now())
				.setUpdateTime(LocalDateTime.now()).setTitle("创建索引").setTag("索引")).build());

		list.add( new IndexQueryBuilder().withObject(new PostMainInfoSearch().setId(3).setAccId(2).setAccName("寇政民").setContent("索引就像是数据库或者数据库中的表，" +
				"我们平时是不会是通过java代码频繁的去创建修改删除数据库或者表的相关信息，我们只会针对数据做CRUD的操作）").setCreateTime(LocalDateTime.now())
				.setUpdateTime(LocalDateTime.now()).setTitle("创建索引方式2").setTag("索引")).build());
		

	  elasticsearchTemplate.bulkIndex(list,IndexCoordinates.of("post_main_info"));
	  
	}
	
	@Test
	public void upateItemDoc(){
		Map<String,Object> sourceMap=new HashMap<>();
		
		sourceMap.put("title","新的创建索引方式");

		IndexRequest indexRequest=new IndexRequest();
		indexRequest.source(sourceMap);

		//elasticsearchTemplate.update(updateQuery,IndexCoordinates.of("post_main_info"));

		Document document = Document.create();
		document.put("title","测试更新标题");
		UpdateQuery updateQuery= UpdateQuery.builder("3").withDocument(document).build();
		elasticsearchTemplate.update(updateQuery,IndexCoordinates.of("post_main_info"));
	}

	@Test
	public void  deleteDoc(){
		elasticsearchTemplate.delete("3",IndexCoordinates.of("post_main_info"));
	}


	@Test
	public void batchUpdate(){

		BulkOptions bulkOptions= BulkOptions.builder().build(); //分片相关(数据刷新策略)

		//elasticsearchTemplate.bulkUpdate();
	}



	
	

}

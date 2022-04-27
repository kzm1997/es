package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.search.PostMainInfoSearch;
import com.sun.org.apache.regexp.internal.RE;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class HighLevelRestClientTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    /**
     * 插入
     */
    @Test
    public void testIndex() throws IOException {

        IndexRequest request = new IndexRequest("rest_client");
        request.id("1");
        PostMainInfoSearch postMainInfoSearch = new PostMainInfoSearch();
        postMainInfoSearch.setId(1).setAccId(1).setAccName("作者名称").setContent("测测试试").setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now()).setTitle("标题1").setTag("tag");

        request.source(JSON.toJSONString(postMainInfoSearch), XContentType.JSON);
        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }


    /**
     * 获取
     */
    @Test
    public void getIndex() throws IOException {
        GetRequest getRequest = new GetRequest("rest_client", "1");

        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(JSON.toJSONString(response.getSource()));
    }


    @Test
    public void existsTest() throws IOException {
        GetRequest getRequest = new GetRequest("rest_client", "1");

        getRequest.fetchSourceContext(new FetchSourceContext(false)); //不查询_souce字段
        getRequest.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    /**
     * 测试更新数据(局部更新,不会覆盖null)
     *
     * @throws IOException
     */
    @Test
    public void updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest("rest_client", "1");
        PostMainInfoSearch postMainInfoSearch = new PostMainInfoSearch();
        postMainInfoSearch.setId(1).setTitle("测试更改标题");

        request.doc(JSON.toJSONString(postMainInfoSearch), XContentType.JSON);

        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);

        System.out.println(response.getGetResult());

    }


    @Test
    public void bulkTest() throws IOException {
        BulkRequest request = new BulkRequest();

        PostMainInfoSearch postMainInfoSearch = new PostMainInfoSearch();
        postMainInfoSearch.setId(4).setAccId(1).setAccName("作者名称").setContent("测测试试").setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now()).setTitle("标题1").setTag("tag");

        request.add(new IndexRequest("rest_client").id("4").source(JSON.toJSONString(postMainInfoSearch), XContentType.JSON));
        Map<String, Object> map = new HashMap<>();
        map.put("title", "测试bulk");
        request.add(new UpdateRequest("rest_client", "4").doc(map));
        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }


    @Test
    public void getList() throws IOException {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item("rest_client", "1"));
        request.add(new MultiGetRequest.Item("rest_client", "4"));

        MultiGetResponse responses = restHighLevelClient.mget(request, RequestOptions.DEFAULT);

        MultiGetItemResponse[] responseArray = responses.getResponses();

        for (MultiGetItemResponse multiGetItemResponse : responseArray) {
            System.out.println(multiGetItemResponse.getFailure());

            GetResponse response = multiGetItemResponse.getResponse();
            if (response.isExists()) {
                System.out.println(response.getSourceAsString());
            }
        }

    }

    @Test
    public void updateByQuery() throws IOException {
        UpdateByQueryRequest request = new UpdateByQueryRequest("rest_client");

        request.setQuery(new TermQueryBuilder("title", "bulk"));

        request.setScript(new Script(ScriptType.INLINE, "painless",
                "ctx._source.accName='111'", Collections.emptyMap()));

        restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
    }


    @Test
    public void baseSearch() throws IOException {
        SearchRequest searchRequest=new SearchRequest("rest_client");

        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("accName","111"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);

        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        HighlightBuilder.Field title=new HighlightBuilder.Field("accName");
        title.preTags("<em>");
        title.postTags("<em>");

        highlightBuilder.field(title);
        searchSourceBuilder.highlighter(highlightBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //返回命中信息
        SearchHits hits = response.getHits();

        SearchHit[] hitsArray = hits.getHits();

        for (SearchHit hit : hitsArray) {
            System.out.println(hit.getSourceAsString());
        }

    }






}

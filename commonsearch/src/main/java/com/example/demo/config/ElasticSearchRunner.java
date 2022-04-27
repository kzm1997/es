package com.example.demo.config;

import cn.hutool.core.bean.BeanUtil;
import com.example.demo.entity.PostCommentInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ElasticSearchRunner implements ApplicationRunner {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String indexName="";
        GetIndexRequest getIndexRequest=new GetIndexRequest(indexName);

        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        //不存在则创建index和setting mapping
        if (!exists){
            CreateIndexRequest createIndexRequest=new CreateIndexRequest(indexName);
            Settings settings=Settings.builder()
                    .put("index_number_of_shards",1)
                    .put("index.number_of_replicas",1)
                    .build();
        }

    }
/*
    private Map<String,Object> createIndexMapping(){
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> fieldMap=new HashMap<>();
        PostCommentInfo postCommentInfo=new PostCommentInfo();
        Map<String, Object> beanMap = BeanUtil.beanToMap(postCommentInfo, false, false);
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            String key=entry.getKey();
            Map<String,Object> map=new HashMap<>();
            if ("id".equals(key)){

            }
        }

    }*/
}

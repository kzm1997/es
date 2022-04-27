package com.example.demo.config;

import cn.hutool.core.util.StrUtil;
import org.apache.http.HttpHost;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.List;

@Configuration
public class EsConfig {


    @Value("${spring.elasticsearch.rest.uris}")
    private List<String> uris;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        HttpHost[] hosts = createHosts();
        RestClientBuilder restClientBuilder = RestClient.builder(hosts);
        RestHighLevelClient restHighLevelClient=new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;

    }

    private HttpHost[] createHosts() {
        HttpHost[] httpHosts = new HttpHost[uris.size()];
        for (int i = 0; i < uris.size(); i++) {
            String hostStr = uris.get(i);
            String[] host = hostStr.split(":");
            httpHosts[i] = new HttpHost(StrUtil.trim(host[0]), Integer.valueOf(StrUtil.trim(host[1])));
        }
        return httpHosts;
    }
    
   
}

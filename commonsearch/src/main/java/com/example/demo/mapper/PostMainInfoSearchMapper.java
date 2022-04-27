package com.example.demo.mapper;

import com.example.demo.entity.search.PostMainInfoSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMainInfoSearchMapper  extends  ElasticsearchRepository<PostMainInfoSearch,Integer> {


}

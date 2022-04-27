package com.example.demo.entity.search;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "post_main_info")
@Data
@Accessors(chain = true)
public class PostMainInfoSearch {

    @Id
    private int id;

    @Field(type = FieldType.Keyword,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String tag;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;

    @Field(type =FieldType.Date,format = DateFormat.date_optional_time)
    private LocalDateTime createTime;

    @Field(type = FieldType.Date,format = DateFormat.date_optional_time)
    private LocalDateTime updateTime;
    
    @Field(type = FieldType.Integer)
    private Integer accId; 
    
    @Field(type = FieldType.Keyword)
    private String accName;
    
    
    
    

}

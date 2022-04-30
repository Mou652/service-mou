package com.example.mongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 文章信息
 *
 * @author yinjihuan
 */
@Data
@Document(collection = "article_info")
public class Article {

    private String name;
}

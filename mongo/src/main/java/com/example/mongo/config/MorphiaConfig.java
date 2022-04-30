package com.example.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import lombok.Data;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MorphiaConfig {

    private MongoClient mongoClient;

    /**
     * 设置连接最大空闲时间(到达时间,连接关闭)
     * @return mongo client属性
     */
    @Bean
    public MongoClientOptions mongoClientOptions() {
        return MongoClientOptions.builder()
                .maxConnectionIdleTime(6000 * 5)
                .maxConnectionLifeTime(0)
                .build();
    }

    @Autowired
    public MorphiaConfig(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Bean
    public Datastore datastore(@Autowired MongoClient mongoClient) {
        Morphia res = new Morphia();
        // 确定Mongo实体类的存放包名
        res.mapPackage("com.test.log.entity");
        Datastore datastore = res.createDatastore(mongoClient, "ops");
        //  为实体类创建索引
        datastore.ensureIndexes();
        return datastore;
    }
}

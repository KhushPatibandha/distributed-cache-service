package com.khush.cache.server.ZooKeeperConfig;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZKConfig {
    @Bean
    public ZooKeeper zooKeeper() throws IOException {
        return new ZooKeeper("zookeeper-service:2181", 15000, null);
    }
     
}

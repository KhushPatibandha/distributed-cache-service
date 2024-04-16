package com.khush.cache.client;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ApplicationTests {

	@MockBean
    private ZooKeeper zooKeeper;

}

package com.khush.cache.server;

import static org.mockito.Mockito.doNothing;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.khush.cache.server.ZooKeeperConfig.ZKDaemon;

@SpringBootTest
class ApplicationTests {

	@MockBean
    private ZooKeeper zooKeeper;

    @MockBean
    private ZKDaemon config;

    @Test
    public void contextLoads() throws KeeperException, InterruptedException, IOException {
        doNothing().when(config).run();
    }

}

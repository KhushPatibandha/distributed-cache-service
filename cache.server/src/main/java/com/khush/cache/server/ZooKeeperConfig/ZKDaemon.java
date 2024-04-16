package com.khush.cache.server.ZooKeeperConfig;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ZKDaemon implements CommandLineRunner {
    private static String MemberNode = "/members";
    private static final Logger LOG = LoggerFactory.getLogger(ZKDaemon.class);

    @Autowired
    private final ZooKeeper zooKeeper;

    public ZKDaemon(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
    
    @Override
    public void run(String... args) throws KeeperException, InterruptedException, IOException {
        String id = System.getenv("POD_IP");
        LOG.info("POD's ID = " + id);
        String createResponse = zooKeeper.create(MemberNode + "/" + id, id.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, null);
        LOG.info(createResponse);
    }

}

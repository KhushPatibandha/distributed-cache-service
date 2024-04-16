package com.khush.cache.client.ZooKeeperConfig;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.khush.cache.client.Services.ConsistentHashing;

import jakarta.annotation.PostConstruct;

@Component
public class Monitor {
    private static String MemberNode = "/members";
    private static final Logger LOG = LoggerFactory.getLogger(Monitor.class);
    private static ZooKeeper zooKeeper;

    @Autowired
    private ConsistentHashing consistentHashing;

    @PostConstruct
    public void init() throws IOException, KeeperException, InterruptedException {
        zooKeeper = new ZooKeeper("zookeeper-service:2181", 15000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                LOG.info("---------------------------------------");
                LOG.info("Got the event for node = " + event.getPath());
                LOG.info("The event type = ", event.getType());
                LOG.info("---------------------------------------");
                try {
                    startWatch();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (KeeperException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // CREATE
        if(zooKeeper.exists(MemberNode, false) == null) {
            zooKeeper.create(MemberNode, "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, null);
        }

        startWatch();
    }

    private void startWatch() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(MemberNode, true, null);
        if(zooKeeper != null) {
            System.err.println("List of children = ");
            children.forEach(child -> System.out.println(child + " "));
            System.out.println();
           
            consistentHashing.clearAll();
            for(String child : children) {
                consistentHashing.addServer(child);
            }
        }
    }

    public List<String> getChildren() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(MemberNode, true, null);
        if(zooKeeper != null) {
            return children;
        }
        return null;
    }

}

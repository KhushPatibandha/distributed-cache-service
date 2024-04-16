package com.khush.cache.client.Services;

import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

@Service
public class ConsistentHashing {
    private final SortedMap<Integer, String> circle = new TreeMap<>();

    public void addServer(String ip) {
        int hash = ip.hashCode();
        circle.put(hash, ip);
    }

    public void removeServer(String ip) {
        int hash = ip.hashCode();
        circle.remove(hash);
    }

    public String getServer(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = key.hashCode();
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public void clearAll() {
        circle.clear();
    }

}

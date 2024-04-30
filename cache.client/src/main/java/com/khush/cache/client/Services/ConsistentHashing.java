package com.khush.cache.client.Services;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

@Service
public class ConsistentHashing {
    private final SortedMap<Integer, String> circle;
    private final HashMap<String, String> randomNameToIp;
    private final Random random;
    private final int numberOfReplicas = 3;

    public ConsistentHashing() {
        this.circle = new TreeMap<>();
        randomNameToIp = new HashMap<>();
        random = new Random();
    }

    private String randomNameGeneratorFromServerIp(String serverIp, int replicaIndex) {
        String randomName = serverIp + replicaIndex + random.nextInt(10000);
        randomNameToIp.put(randomName, serverIp);
        return randomName;
    }

    private int computeHash(String key) {
        return Hashing.murmur3_128().hashString(key, StandardCharsets.UTF_8).asInt();
    }

    public void addServer(String ip) {
        for (int i = 0; i < numberOfReplicas; i++) {
            String randomName = randomNameGeneratorFromServerIp(ip, i);
            int hash = computeHash(randomName);
            circle.put(hash, randomName);
        }
    }

    public void removeServer(String ip) {
        for (int i = 0; i < numberOfReplicas; i++) {
            String randomName = ip + i + random.nextInt(10000);
            int hash = computeHash(randomName);
            circle.remove(hash);
            randomNameToIp.remove(randomName);
        }
    }

    public String getServer(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = computeHash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return randomNameToIp.get(circle.get(hash));
    }

    public void clearAll() {
        circle.clear();
    }
}

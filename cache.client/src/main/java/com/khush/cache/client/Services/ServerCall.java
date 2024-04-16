package com.khush.cache.client.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khush.cache.client.ZooKeeperConfig.Monitor;

@Service
public class ServerCall {

    @Autowired
    private ConsistentHashing consistentHashing;

    @Autowired
    private Monitor monitor;

    public String getValue(String key) throws IOException, KeeperException, InterruptedException {
        System.out.println(monitor.getChildren());
        String serverIp = consistentHashing.getServer(key);
        
        if (serverIp == null || serverIp.isEmpty()) {
            throw new IOException("Server IP is null or empty");
        }
        
        String urlStr = "http://" + serverIp + ":8080/api/get/" + key;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        
        while((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        
        System.out.println("Response: " + response.toString());
        return response.toString();
    }

    public String putKeyValue(String key, String value) throws IOException, KeeperException, InterruptedException {
        System.out.println(monitor.getChildren());
        String serverIp = consistentHashing.getServer(key);

        if (serverIp == null || serverIp.isEmpty()) {
            throw new IOException("Server IP is null or empty");
        }

        String urlStr = "http://" + serverIp + ":8080/api/post?key=" + URLEncoder.encode(key, "UTF-8") + "&value=" + URLEncoder.encode(value, "UTF-8");
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
       
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        System.out.println("Response: " + response.toString());
        return response.toString();
    }


}

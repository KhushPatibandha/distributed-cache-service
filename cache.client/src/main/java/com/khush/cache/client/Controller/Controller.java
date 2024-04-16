package com.khush.cache.client.Controller;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khush.cache.client.Services.ServerCall;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private ServerCall serverCall;

    @GetMapping("/get/{key}")
    public ResponseEntity<String> getValue(@PathVariable("key") String key) throws IOException, KeeperException, InterruptedException {
        String response = serverCall.getValue(key);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/post")
    public ResponseEntity<String> postKeyValue(@RequestParam String key, @RequestParam String value) throws IOException, KeeperException, InterruptedException {
        String response = serverCall.putKeyValue(key, value);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

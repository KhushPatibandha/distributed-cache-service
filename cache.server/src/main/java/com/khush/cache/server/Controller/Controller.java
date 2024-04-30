package com.khush.cache.server.Controller;

import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khush.cache.server.Services.Cache;

@RestController
@RequestMapping("/api")
public class Controller {
    private Cache cache = new Cache(100);

    @GetMapping("/get/{key}")
    public ResponseEntity<String> get(@PathVariable("key") String key) {
        String value = cache.get(key);
        if(value == null) {
            System.out.println("requested key: " + key + " dosent exist in this server");
            return new ResponseEntity<>(value, HttpStatus.NOT_FOUND);
        }
        System.out.println("key: " + key + " returned");
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestParam String key, @RequestParam String value) {
        cache.put(key, value);
        System.out.println("key: " + key + " added to this server");
        return new ResponseEntity<>("Key-Value pair added successfully", HttpStatus.OK);
    }
    
    @GetMapping("/print")
    public ResponseEntity<ArrayList<String>> print() {
        ArrayList<String> list = cache.printList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}

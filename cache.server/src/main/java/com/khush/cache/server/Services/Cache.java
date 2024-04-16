package com.khush.cache.server.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cache {
    private final Map<String, Node> map;
    private final int capacity;
    
    private Node head = null;
    private Node tail = null;

    public Cache(int capacity) {
        map = new HashMap<String, Node>();
        this.capacity = capacity;
    }

    public String get(String key) {
        if(!map.containsKey(key)) {
            return null;
        }

        Node node = map.get(key);
        
        deleteFromList(node);
        setListHead(node);
        
        return node.getValue();
    }

    public void put(String key, String value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.setValue(value);
        
            deleteFromList(node);
            setListHead(node);
        } else {
            if(map.size() >= capacity) {
                map.remove(tail.getKey());
                deleteFromList(tail);
            }

            Node node = new Node(key, value);

            map.put(key, node);
            setListHead(node);
        }
    }

    public ArrayList<String> printList() {
        Node node = head;
        ArrayList<String> list = new ArrayList<>();
        while(node != null) {
            list.add(node.getKey() + " : " + node.getValue());
            System.out.println(node.getKey() + " : " + node.getValue());
            node = node.getNext();
        }
        return list;
    }

    private void deleteFromList(Node node) {
        if(node.getPrev() != null && node.getNext() != null) {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        } else if(node.getPrev() == null && node.getNext() != null) {
            head = node.getNext();
            head.setPrev(null);
        } else if(node.getPrev() != null && node.getNext() == null) {
            tail = node.getPrev();
            tail.setNext(null);
        } else {
            head = null;
            tail = null;
        }
    }

    private void setListHead(Node node) {
        if(head == null) {
            head = node;
            tail = node;
        } else {
            head.setPrev(node);
            node.setNext(head);
            node.setPrev(null);
            head = node;
        }
    }
    
}

package main.java.com.lld;

import java.util.*;

//Least recently used - prefer Doubly LL approach
public class LRUCache {

    public static void main(String[] args) {
        System.out.println("LRU using LinkedHashmap");
        LRUCache lruCache = new LRUCache(2);
        lruCache.set(1,10);
        lruCache.set(2,20);
        System.out.println(lruCache.get(1));
        lruCache.set(3,30);
        System.out.println(lruCache.get(1)); //ONE GLITCH IN OUTPUT, due to which I prefer the Doubly LL approach
        lruCache.set(4,40);
        System.out.println(lruCache.get(2));
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));

        System.out.println("LRU using Doubly LL and hashmap");
        LRUCacheUsingDoublyLL lruDD = new LRUCache(2).new LRUCacheUsingDoublyLL(2);
        lruDD.set(1,10);
        lruDD.set(2,20);
        System.out.println(lruDD.get(1));
        lruDD.set(3,30);
        System.out.println(lruDD.get(1));
        lruDD.set(4,40);
        System.out.println(lruDD.get(2));
        System.out.println(lruDD.get(3));
        System.out.println(lruDD.get(4));

        //The hash map makes the time of get() to be O(1)

    }
    int capacity;
    LinkedHashMap<Integer, Integer> linkedHashMap;
    LRUCache(int capacity){
        this.capacity = capacity;
        linkedHashMap = new LinkedHashMap(){
                 protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size()>capacity;
                }
        };
    }

    void set(int key, int value){
        linkedHashMap.put(key, value);
    }
    int get(int key){
        return linkedHashMap.getOrDefault(key,-1);
    }

    class Node{
        int key;
        int value;
        Node pre;
        Node next;

        Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    //The LRU cache is a hash map of keys and double linked nodes. .
    // The list of double linked nodes make the nodes adding/removal operations O(1).
    class LRUCacheUsingDoublyLL{
        int capacity;
        Node head;
        Node tail;
        private HashMap<Integer, Node> map;

        LRUCacheUsingDoublyLL(int capacity){
            map = new HashMap<>();
            head = new Node(0,0);
            tail = new Node(0,0);
            head.next = tail;
            tail.pre = head;
            this.capacity = capacity;
        }

        int get(int key){
            if(map.containsKey(key)){
                Node node = map.get(key);
                int res = node.value;
                delete(node);
                addToHead(node);
                return res;
            }
            return -1;
        }

        void set(int key, int value){
            if(map.get(key)!=null){
                Node node = map.get(key);
                node.value = value;
                delete(node);
                addToHead(node);
            }else{
                Node node = new Node(key, value);
                if(map.size()==capacity){
                    Node lruNode = tail.pre;
                    map.remove(lruNode.key);
                    delete(lruNode);
                    addToHead(node);
                }else{
                    addToHead(node);
                }
                map.put(key, node);
            }
        }

        void addToHead(Node node){
            node.next = head.next;
            node.next.pre=node;
            node.pre=head;
            head.next=node;
        }

        void delete(Node node){
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
    }
}
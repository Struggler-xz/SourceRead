package com.hx.xz;

import java.util.Map;
import java.util.Objects;

/**
 * create by xz on 2017/9/14 14:08
 */
public class SimpleHashMap {

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    final float loadFactor;

    transient int size;

    transient Node[] table;

    public SimpleHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
        table = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String put(String key, String value) {
        return putVal(hash(key), key, value);
    }

    private final String putVal(int hash, String key, String value) {
        Node[] tab; Node p; int n,i;
        if((tab = table) != null &&(n = tab.length)!=0){
            //索引所在的节点在table中不存在，就插入
            if ((p = tab[i = (n - 1) & hash]) == null)
                tab[i] = new Node(hash, key, value, null);
            else{
                //这里做链表的插入
                Node e; String k;
                if (p.hash == hash &&
                        ((k = p.key) == key || (key != null && key.equals(k))))
                    e = p;
                else{
                    for (int binCount = 0; ; ++binCount) {
                        if ((e = p.next) == null) {
                            //next为null,插入
                            p.next = new Node(hash, key, value, null);
                            break;
                        }
                        if (e.hash == hash &&
                                ((k = e.key) == key || (key != null && key.equals(k))))
                            break;
                        p = e;
                    }
                }
            }
        }
        ++size;
        return null;
    }

    public String get(Object key) {
        Node e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    final Node getNode(int hash, Object key) {
        Node[] tab; Node first, e; int n; String k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static class Node{
        int hash;
        String key;
        String value;
        Node next;

        public Node(int hash, String key, String value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key)^Objects.hashCode(value);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Node) {
                Node e = (Node) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return key+":"+value;
        }

        public final String getKey()        { return key; }
        public final String getValue()      { return value; }
        public final String setValue(String newValue) {
            String oldValue = value;
            value = newValue;
            return oldValue;
        }

    }
}

package com.example.uzair.blaze_downloader.cache;

import com.example.uzair.blaze_downloader.ImageModel;

import java.util.HashMap;

public class LruCache {
    private HashMap<String, Entry> hashMap;
    private Entry start, end;
    private int LRU_CACHE_SIZE = 4;
    private Builder builder = new Builder();

    public LruCache() {
        hashMap = new HashMap<>();
    }

    public Builder builder() {
        return builder;
    }

    public class Builder {
        public Builder setLruCache(final int lruCacheSize) {
            LRU_CACHE_SIZE = lruCacheSize;
            return this;
        }

        public LruCache build() {
            return LruCache.this;
        }
    }

    public ImageModel getEntry(String key) {
        if (hashMap.containsKey(key)) // Key Already Exist, just update
        {
            Entry entry = hashMap.get(key);
            if (entry != null) {
                removeNode(entry);
                addAtTop(entry);
                return entry.value;
            }
        }
        return null;
    }

    public void putEntry(String key, ImageModel value) {
        if (hashMap.containsKey(key)) // Key Already Exist, just update the value and move it to top
        {
            Entry entry = hashMap.get(key);
            if (entry != null) {
                entry.value = value;
                removeNode(entry);
                addAtTop(entry);
            }
        } else {
            Entry newNode = new Entry();
            newNode.left = null;
            newNode.right = null;
            newNode.value = value;
            newNode.key = key;
            if (hashMap.size() > LRU_CACHE_SIZE) // We have reached maximum size so need to make room for new element.
            {
                hashMap.remove(end.key);
                removeNode(end);
                addAtTop(newNode);

            } else {
                addAtTop(newNode);
            }

            hashMap.put(key, newNode);
        }
    }

    private void addAtTop(Entry node) {
        node.right = start;
        node.left = null;
        if (start != null)
            start.left = node;
        start = node;
        if (end == null)
            end = start;
    }

    private void removeNode(Entry node) {

        if (node.left != null) {
            node.left.right = node.right;
        } else {
            start = node.right;
        }

        if (node.right != null) {
            node.right.left = node.left;
        } else {
            end = node.left;
        }
    }
}

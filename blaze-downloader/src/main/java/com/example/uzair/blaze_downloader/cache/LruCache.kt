package com.example.uzair.blaze_downloader.cache

import android.util.Log
import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blaze_downloader.ImageModel
import java.util.*

class LruCache(private val blazeDownloader: BlazeDownloader) {
    private val hashMap: HashMap<String, Entry> = HashMap()
    private var start: Entry? = null
    private var end: Entry? = null

    fun getEntry(key: String): ImageModel? {
        if (hashMap.containsKey(key))
        //Key Already Exist, just update the value and move it to top
        {
            val entry = hashMap[key]
            if (entry != null) {
                removeNode(entry)
                addAtTop(entry)
                return entry.value
            }
        }
        return null
    }

    fun putEntry(key: String, value: ImageModel) {
        if (hashMap.containsKey(key))
        //Key Already Exist, just update the value and move it to top
        {
            val entry = hashMap[key]
            if (entry != null) {
                entry.value = value
                removeNode(entry)
                addAtTop(entry)
            }
        } else {
            val newNode = Entry()
            newNode.left = null
            newNode.right = null
            newNode.value = value
            newNode.key = key
            if (hashMap.size > blazeDownloader.lruCacheSize)
            // We have reached maximum size so need to make room for new element.
            {
                hashMap.remove(end!!.key)
                removeNode(end!!)
                addAtTop(newNode)
                Log.i(TAG, "DEBUG : Removed from cache")
            } else {
                addAtTop(newNode)
            }

            hashMap[key] = newNode
        }
    }

    private fun addAtTop(node: Entry) {
        node.right = start
        node.left = null
        if (start != null)
            start!!.left = node
        start = node
        if (end == null)
            end = start
    }

    private fun removeNode(node: Entry) {

        if (node.left != null) {
            node.left!!.right = node.right
        } else {
            start = node.right
        }

        if (node.right != null) {
            node.right!!.left = node.left
        } else {
            end = node.left
        }
    }

    companion object {
        private val TAG = LruCache::class.java.simpleName
    }
}

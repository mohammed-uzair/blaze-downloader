package com.example.uzair.blaze_downloader.cache

import com.example.uzair.blaze_downloader.BlazeDownloader
import com.example.uzair.blaze_downloader.DataModel
import java.util.*

/**
 * This is the main algorithm for LRU(Least Recently Used) eviction logic.
 * I have used hash map and doubly linked list for this.
 *
 * Every item that is accessed is moved to the top of the list(Recently used item).
 * Thus always the least recently used item remains at the last of the node.
 * Thus its easy for removal of the least used item from the last node.
 */
class LruCache(private val blazeDownloader: BlazeDownloader) {
    //A hash map containing the url as key and entry as the reference to doubly linked list
    private val hashMap: HashMap<String, Entry> = HashMap()

    //Assign the start node as null
    private var start: Entry? = null

    //Assign the end node as null
    private var end: Entry? = null

    /**
     * Get the model from the cache, returns null if not in the cache
     */
    fun getEntry(key: String): DataModel? {
        if (hashMap.containsKey(key)) {
            //Key already exist, just update the value and move it to top
            val entry = hashMap[key]
            if (entry != null) {
                removeNode(entry)
                addAtTop(entry)
                return entry.value
            }
        }
        return null
    }

    /**
     * Add a new item to the LRU cache
     */
    fun putEntry(key: String, value: DataModel) {
        if (hashMap.containsKey(key)) {
            //Key already exist, just update the value and move it to top
            val entry = hashMap[key]
            if (entry != null) {
                entry.value = value
                removeNode(entry)
                addAtTop(entry)
            }
        }

        //Add a new entry into the cache
        else {
            val newNode = Entry()
            newNode.left = null
            newNode.right = null
            newNode.value = value
            newNode.key = key
            if (hashMap.size > blazeDownloader.lruCacheSize) {
                //We have reached maximum size so need to make room for new element.
                hashMap.remove(end!!.key)
                removeNode(end!!)
                addAtTop(newNode)
            } else {
                addAtTop(newNode)
            }

            hashMap[key] = newNode
        }
    }

    /**
     * Move/add an element to the top of the map and at start of the list
     */
    private fun addAtTop(node: Entry) {
        node.right = start
        node.left = null
        if (start != null)
            start!!.left = node
        start = node
        if (end == null)
            end = start
    }

    /**
     * Remove an item from the cache
     */
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
}

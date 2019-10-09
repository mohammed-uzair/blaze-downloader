package com.example.uzair.blaze_downloader

import com.example.uzair.blaze_downloader.cache.LruCache
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LruCacheTest {
    private lateinit var blazeDownloader: BlazeDownloader
    lateinit var lruCache: LruCache

    @Before
    fun setUp() {
        blazeDownloader = BlazeDownloader.instance
        lruCache = LruCache(blazeDownloader)
    }

    @Test
    fun testLruAdditionOfData() {
        lruCache.putEntry("key", DataModel("someurl", null))
        val dataModel = lruCache.getEntry("someurl")

        assertNotNull(dataModel)
    }

    @Test
    fun testLruRemovalOfData() {
        //Fill cache up till the neck, and check if its removing old entries
        lruCache.putEntry("key0", DataModel("someurl0", null))
        lruCache.putEntry("key1", DataModel("someurl1", null))
        lruCache.putEntry("key2", DataModel("someurl2", null))
        lruCache.putEntry("key3", DataModel("someurl3", null))
        lruCache.putEntry("key4", DataModel("someurl4", null))
        lruCache.putEntry("key5", DataModel("someurl5", null))
        lruCache.putEntry("key6", DataModel("someurl6", null))
        lruCache.putEntry("key7", DataModel("someurl7", null))
        lruCache.putEntry("key8", DataModel("someurl8", null))
        lruCache.putEntry("key9", DataModel("someurl9", null))

        //Add one more item
        lruCache.putEntry("key10", DataModel("someurl10", null))

        //Assert if the first item is removed, as that is for now the least used item
        val data = lruCache.getEntry("key1")

        assertNull(data)
    }
}
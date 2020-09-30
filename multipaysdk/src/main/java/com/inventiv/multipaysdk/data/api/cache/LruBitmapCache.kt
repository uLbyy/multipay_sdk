package com.inventiv.multipaysdk.data.api.cache

import android.content.Context
import android.graphics.Bitmap
import androidx.collection.LruCache
import com.android.volley.toolbox.ImageLoader

internal class LruBitmapCache private constructor(maxSize: Int) :
    LruCache<String?, Bitmap?>(maxSize),
    ImageLoader.ImageCache {
    constructor(ctx: Context) : this(getCacheSize(ctx))

    override fun sizeOf(key: String, value: Bitmap): Int {
        return value.rowBytes * value.height
    }

    override fun getBitmap(url: String): Bitmap? {
        return get(url)
    }

    override fun putBitmap(url: String, bitmap: Bitmap) {
        put(url, bitmap)
    }

    companion object {
        private fun getCacheSize(context: Context): Int {
            val displayMetrics = context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels
            val screenHeight = displayMetrics.heightPixels
            // 4 bytes per pixel
            val screenBytes = screenWidth * screenHeight * 4
            return screenBytes * 3
        }
    }
}
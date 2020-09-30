package com.inventiv.multipaysdk.data.api

import android.content.Context
import android.util.Log
import com.android.volley.NoConnectionError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.ImageLoader
import com.inventiv.multipaysdk.data.api.cache.LruBitmapCache
import com.inventiv.multipaysdk.data.api.callback.NetworkCallback
import com.inventiv.multipaysdk.data.api.error.ApiError
import com.inventiv.multipaysdk.data.api.error.VolleyCancelError
import com.inventiv.multipaysdk.data.api.error.VolleyParseError
import com.inventiv.multipaysdk.data.model.request.BaseRequest
import com.inventiv.multipaysdk.data.model.response.BaseResponse

import java.io.File

internal class VolleyManager(private val context: Context) {

    companion object {
        private const val REQUEST_TAG = "MultiPaySdkRequestTag"
        private const val THREAD_POOL_SIZE = 1
    }

    private val requestQueue: RequestQueue
    private var mLruBitmapCache: LruBitmapCache? = null
    private var imageLoader: ImageLoader? = null

    init {
        val cacheDir = File(context.cacheDir, "volley")
        val network = BasicNetwork(HurlStack())
        requestQueue = RequestQueue(DiskBasedCache(cacheDir), network, THREAD_POOL_SIZE)
        requestQueue.start()
    }

    fun getVolleyImageLoader(): ImageLoader {
        if (imageLoader == null) {
            imageLoader = ImageLoader(
                requestQueue,
                getVolleyImageCache()
            )
        }
        return requireNotNull(imageLoader)
    }

    private fun getVolleyImageCache(): LruBitmapCache? {
        if (mLruBitmapCache == null) {
            mLruBitmapCache = LruBitmapCache(context)
        }
        return mLruBitmapCache
    }

    fun <T : BaseResponse> sendRequest(
        baseUrl: String,
        baseRequest: BaseRequest,
        requestPath: String,
        headers: MutableMap<String, String>,
        responseModel: Class<T>,
        callback: NetworkCallback<T>
    ) {
        val gsonRequest = GsonRequest(
            "$baseUrl${requestPath}",
            baseRequest,
            responseModel,
            headers,
            Response.Listener { response ->
                Log.d("VolleyNetworkAdapter", "sendRequest Response: $response")
                callback.onSuccess(response)
            },
            Response.ErrorListener { volleyError ->
                val apiError: ApiError = when {
                    volleyError == null -> {
                        ApiError.generalInstance()
                    }
                    volleyError is VolleyCancelError -> {
                        ApiError.cancelInstance()
                    }
                    volleyError is NoConnectionError -> {
                        ApiError.noConnectionInstance()
                    }
                    volleyError is VolleyParseError -> {
                        ApiError.invalidResponseInstance()
                    }
                    volleyError.networkResponse != null -> {
                        ApiError.serverErrorInstance(
                            volleyError.message ?: "",
                            volleyError.networkResponse.statusCode,
                            volleyError.networkResponse.data
                        )
                    }
                    else -> {
                        if (volleyError.message.isNullOrEmpty()) {
                            ApiError.generalInstance()
                        } else {
                            ApiError.generalInstance(volleyError.message!!)
                        }
                    }
                }
                Log.d("VolleyNetworkAdapter", "sendRequest Error: $apiError")
                callback.onError(apiError)
            }
        )

        gsonRequest.headers.putAll(headers)
        gsonRequest.tag = REQUEST_TAG
        gsonRequest.setShouldCache(false)

        Log.d("VolleyNetworkAdapter", "sendRequest Start: $gsonRequest")
        requestQueue.add(gsonRequest)
    }

    fun cancelAllRequests() {
        requestQueue.cancelAll { request -> request.tag === REQUEST_TAG }
    }
}

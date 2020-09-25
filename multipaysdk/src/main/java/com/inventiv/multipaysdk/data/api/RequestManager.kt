package com.inventiv.multipaysdk.data.api

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.inventiv.multipaysdk.data.model.request.BaseRequest
import com.inventiv.multipaysdk.data.model.response.BaseResponse
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class RequestManager {

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    open class GsonRequest<I : BaseRequest, O>(
        url: String,
        private val requestClass: I,
        private val clazz: Class<O>,
        private val headers: MutableMap<String, String>?,
        private val listener: Response.Listener<O>,
        errorListener: Response.ErrorListener
    ) : JsonRequest<O>(Method.POST, url, Gson().toJson(requestClass), listener, errorListener) {

        private val gson = Gson()

        override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

        override fun deliverResponse(response: O) = listener.onResponse(response)

        override fun parseNetworkResponse(response: NetworkResponse?): Response<O> {
            return try {
                val json = String(
                    response?.data ?: ByteArray(0),
                    Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
                )
                val baseResponse = gson.fromJson(json, BaseResponse::class.java);
                val model = gson.fromJson(baseResponse.result, clazz)
                Response.success(
                    model,
                    HttpHeaderParser.parseCacheHeaders(response)
                )
            } catch (e: UnsupportedEncodingException) {
                Response.error(ParseError(e))
            } catch (e: JsonSyntaxException) {
                Response.error(ParseError(e))
            }
        }
    }
}
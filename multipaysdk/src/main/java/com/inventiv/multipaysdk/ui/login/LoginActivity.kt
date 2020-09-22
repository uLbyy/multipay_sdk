package com.inventiv.multipaysdk.ui.login

import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.inventiv.multipaysdk.R
import com.inventiv.multipaysdk.base.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val queue = Volley.newRequestQueue(this)
        val url = "https://www.google.com"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                Toast.makeText(this, "FAIL", Toast.LENGTH_LONG).show()
            })

        queue.add(stringRequest)
    }
}
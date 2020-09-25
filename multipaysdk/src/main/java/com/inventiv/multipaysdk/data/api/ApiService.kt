package com.inventiv.multipaysdk.data.api

import com.inventiv.multipaysdk.data.model.request.LoginGsm
import com.inventiv.multipaysdk.data.model.response.BaseResponse

interface ApiService {

    fun loginGsm(loginGsm: LoginGsm?): BaseResponse?
}
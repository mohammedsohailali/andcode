package com.weather.info.api

import com.android.realapp.util.logException
import com.google.gson.Gson
import com.weather.info.model.ErrorModel
import com.weather.info.util.Constant
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
class ApiResponse<T> {

    val code: Int
    val body: T?
    val errorMessage: String?
    val isSuccessful: Boolean
        get() = code in 200..299

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = if (error is UnknownHostException) {
            Constant.CHECK_INTERNET
        } else {
            error.message
        }
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null

        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    val model =
                        Gson().fromJson(response.errorBody()!!.string(), ErrorModel::class.java)
                    message = model.message
                  //  message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    logException(ignored, "error while parsing response")
                }
            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }
    }
}
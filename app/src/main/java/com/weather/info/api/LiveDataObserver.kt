package com.weather.info.api

import androidx.lifecycle.Observer
import com.weather.info.util.Constant
import com.weather.info.api.Status

/**
 * Handles API Response and Parse Accordingly
 */
abstract class LiveDataObserver<T> : Observer<Resource<T>> {
    override fun onChanged(t: Resource<T>?) {
        when (t?.status) {
            Status.ERROR -> {
                if (t.code == 401 || t.code == 403) {
                    notAuthorized()

                } else {
                    val message = if (t.message == Constant.CHECK_INTERNET) {
                        t.message
                    } else {
                        //DefaultResponseParser(t.message).getMessage()
                        onError(t.message)
                    }
                    onError(t.message)
                }
            }
            Status.LOADING -> {
                onLoading()
            }
            Status.SUCCESS -> {
                onSuccess(t.data, t.message)
            }
        }
    }

    abstract fun onError(message: String?)
    abstract fun onSuccess(data: T?, message: String?)
    abstract fun onLoading()
    abstract fun notAuthorized()
}
package com.weather.info.api

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType> @MainThread internal constructor(
        private val appExecutors: AppExecutors
) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.setValue(Resource.loading(null))
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            if (response?.isSuccessful == true) {
                appExecutors.diskIO().execute {
                    processResponse(response)?.let {
                        appExecutors.mainThread().execute {
                            result.addSource(convertToLiveData(it)) { newData ->
                                setValue(Resource.success(newData, "Success"))
                            }
                        }
                    }
                }
            } else {
                onFetchFailed()
                setValue(Resource.error(response.errorMessage, null, response.code))
            }
        }
    }

    private fun onFetchFailed() {}
    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: ApiResponse<ResultType>?): ResultType? {
        return response?.body
    }

    @MainThread
    private fun convertToLiveData(response: ResultType?): LiveData<ResultType> {
        val mutableLiveData = MutableLiveData<ResultType>()
        mutableLiveData.value = response
        return mutableLiveData
    }

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<ResultType>>
}

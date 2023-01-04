package com.weather.info.model

import com.google.gson.annotations.SerializedName
import com.weather.info.model.BaseModel


data class ErrorModel(
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("timestamp")
    val timestamp: String
) : BaseModel()
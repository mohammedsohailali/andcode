package com.weather.info.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseModel : Serializable {

    @SerializedName("Status")
    lateinit var status: Status

    data class Status(
        @SerializedName("Message")
        val message: String,
        @SerializedName("StatusId")
        val statusId: Int,
        @SerializedName("StatusName")
        val statusName: String
    )
}
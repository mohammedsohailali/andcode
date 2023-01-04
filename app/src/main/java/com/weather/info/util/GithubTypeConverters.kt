package com.weather.info.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class GithubTypeConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): ArrayList<Any?>? {
        if (data == null) {
            return ArrayList()
        }
        val listType: Type =
            object : TypeToken<ArrayList<Any?>?>() {}.type
        return gson.fromJson<ArrayList<Any?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: ArrayList<Any?>?): String? {
        return gson.toJson(someObjects)
    }
}
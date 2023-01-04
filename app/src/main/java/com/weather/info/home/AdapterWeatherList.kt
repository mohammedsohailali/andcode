package com.weather.info.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.realapp.util.kelvinToCelsius
import com.weather.info.R
import com.weather.info.model.ResWeatherInfo
import com.weather.info.model.WeatherInfoModel
import kotlinx.android.synthetic.main.item_weather_list.view.*

class AdapterWeatherList(
    val resWeatherInfo: ResWeatherInfo,
    val listenerWeatherList: ListenerWeatherList
) :
    RecyclerView.Adapter<AdapterWeatherList.WeatherViewHolder>() {

    class WeatherViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather_list, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resWeatherInfo.list.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.view.tvWeatherType.text = resWeatherInfo.list[position].weather[0].main
        holder.view.tvTemp.text =
            "Temp: ${resWeatherInfo.list[position].main.temp.kelvinToCelsius()}°"

        holder.itemView.setOnClickListener { listenerWeatherList.onWeatherItemClicked(resWeatherInfo.list[position]) }
    }
}

interface ListenerWeatherList {
    fun onWeatherItemClicked(weatherInfoModel: WeatherInfoModel)
}
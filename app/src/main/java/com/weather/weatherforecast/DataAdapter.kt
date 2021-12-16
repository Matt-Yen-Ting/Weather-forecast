package com.weather.weatherforecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.weatherforecast.databinding.ImageItemBinding
import com.weather.weatherforecast.databinding.WeatherDataItemBinding
import com.weather.weatherforecast.entities.RequestDataItem

class DataAdapter(private val onClick: (data: PassData) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: List<RequestDataItem.Record.Location.WeatherElement.WeatherInfo> =
        emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            WEATHER_VIEW_TYPE -> WeatherDataViewHolder(
                WeatherDataItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ImageViewHolder(
                ImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            WEATHER_VIEW_TYPE -> (holder as WeatherDataViewHolder).bind(dataList[position])
            IMAGE_VIEW_TYPE -> (holder as ImageViewHolder)
        }
    }

    private inner class WeatherDataViewHolder(private val binding: WeatherDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RequestDataItem.Record.Location.WeatherElement.WeatherInfo) = with(binding) {
            tvStartTime.text = item.startTime
            tvEndTime.text = item.endTime
            val temperature = buildString {
                append(item.parameter.parameterName)
                append(item.parameter.parameterUnit)
            }

            tvTemperature.text = temperature
            binding.root.setOnClickListener {
                onClick.invoke(PassData(item.startTime, item.endTime, temperature))
            }
        }
    }

    private inner class ImageViewHolder(binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<RequestDataItem.Record.Location.WeatherElement.WeatherInfo>) {
        this.dataList = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    companion object {
        const val WEATHER_VIEW_TYPE = 0
        const val IMAGE_VIEW_TYPE = 1
    }
}
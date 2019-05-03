package com.pixelart.geocodingweatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity

class LocationsAdapter(private val listener: OnItemClickedListener):
    ListAdapter<LocationEntity, LocationsAdapter.ViewHolder>(DIFF_CALLBACK) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_adapter_layout, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = getItem(position)
        holder.apply {
            setContent(location)
            itemView.setOnClickListener { listener.onItemClicked(position) }
        }
    }
    
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val locationName: TextView = view.findViewById(R.id.tvLocationName)
        
        fun setContent(location: LocationEntity){
            locationName.text = location.locationName
        }
    }
    
    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<LocationEntity> =
            object : DiffUtil.ItemCallback<LocationEntity>(){
                override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
                    return oldItem == newItem
                }
    
                override fun areContentsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }
    
    interface OnItemClickedListener{
        fun onItemClicked(position: Int)
    }
}
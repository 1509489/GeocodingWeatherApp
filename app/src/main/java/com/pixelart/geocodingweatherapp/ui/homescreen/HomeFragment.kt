package com.pixelart.geocodingweatherapp.ui.homescreen


import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelart.geocodingweatherapp.AppController

import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.adapter.LocationsAdapter
import com.pixelart.geocodingweatherapp.common.RxBus
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import com.pixelart.geocodingweatherapp.di.fragment.FragmentModule
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import javax.inject.Inject

class HomeFragment : Fragment(), LocationsAdapter.OnItemClickedListener {
    
    @Inject lateinit var viewModel: LocationViewModel

    private lateinit var adapter: LocationsAdapter
    private lateinit var recyclerViewLayoutAnim: LayoutAnimationController
    private lateinit var rootView: View
    private lateinit var locations: ArrayList<LocationEntity>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val fragmentComponent = (activity?.application as AppController)
            .applicationComponent
            .newFragmentComponent(FragmentModule(this))
        fragmentComponent.injectHomeScreen(this)

        adapter = LocationsAdapter(this)
        recyclerViewLayoutAnim = AnimationUtils.loadLayoutAnimation(activity?.applicationContext, R.anim.layout_anim_fall_down)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.getLocations().observe(this, Observer {
            adapter.submitList(it)
            locations = it as ArrayList<LocationEntity>
        })

        rootView.rvLocations.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.context)
            addItemDecoration(DividerItemDecoration(this@HomeFragment.context, LinearLayoutManager.VERTICAL))
            adapter = this@HomeFragment.adapter
            layoutAnimation = recyclerViewLayoutAnim
        }
        
        btnAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddLocation()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onItemClicked(position: Int) {
        RxBus.INSTANCE.post(locations[position])

        val itemView = rootView.rvLocations.findViewHolderForAdapterPosition(position)?.itemView

        val action = HomeFragmentDirections.actionHomeToForecast()
        itemView?.let { Navigation.findNavController(it).navigate(action) }
    }
}

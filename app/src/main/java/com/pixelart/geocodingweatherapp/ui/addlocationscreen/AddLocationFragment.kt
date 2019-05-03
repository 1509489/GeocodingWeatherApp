package com.pixelart.geocodingweatherapp.ui.addlocationscreen


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.pixelart.geocodingweatherapp.AppController

import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import com.pixelart.geocodingweatherapp.di.fragment.FragmentModule
import com.pixelart.geocodingweatherapp.ui.homescreen.LocationViewModel
import kotlinx.android.synthetic.main.fragment_add_location.view.*
import javax.inject.Inject

class AddLocationFragment : Fragment() {

    @Inject lateinit var viewModel: LocationViewModel

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val fragmentComponent = (activity?.application as AppController)
            .applicationComponent
            .newFragmentComponent(FragmentModule(this))
        fragmentComponent.injectAddLocationScreen(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_location, container, false)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.mItem_done){

            val location = rootView.etLocation.text.toString()

            viewModel.addLocation(LocationEntity(
                locationName = location,
                longitude = 25.88,
                latitude = 24.66,
                countryLongName = "United Kingdom",
                countryShortName = "UK"
            ))

            Log.d("TEST", "City: $location")
            activity?.supportFragmentManager?.popBackStack()
            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }
}

package com.pixelart.geocodingweatherapp.ui.addlocationscreen


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pixelart.geocodingweatherapp.AppController

import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import com.pixelart.geocodingweatherapp.data.repository.LocationRepositoryImpl
import com.pixelart.geocodingweatherapp.di.fragment.FragmentModule
import com.pixelart.geocodingweatherapp.ui.homescreen.LocationViewModel
import kotlinx.android.synthetic.main.fragment_add_location.view.*
import javax.inject.Inject
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import com.pixelart.geocodingweatherapp.ui.MainActivity


class AddLocationFragment : Fragment() {

    @Inject lateinit var viewModel: LocationViewModel

    private lateinit var rootView: View
    private lateinit var locations: ArrayList<LocationEntity>
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity = MainActivity()
        val fragmentComponent = (activity?.application as AppController)
            .applicationComponent
            .newFragmentComponent(FragmentModule(this))
        fragmentComponent.injectAddLocationScreen(this)

        locations = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_location, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var lastTextEdit = 0L
        val delay = 1000L
        val handler = Handler()

        val inputFinish = Runnable{
            if (System.currentTimeMillis() > lastTextEdit + delay - 500) {

                if(rootView.etLocation.text.toString().isNotBlank()){
                    val location = rootView.etLocation.text.toString()

                    mainActivity.countingIdlingResource.increment()
                    viewModel.getLocationNetwork(location).observe(this, Observer { geoResponse ->

                        if (geoResponse.status.contains("OK", true)){
                            val country  = geoResponse.results[0].addressComponents.filter { addressComponent ->
                                addressComponent.types[0].contains("country", true)
                            }

                            locations.add(
                                LocationEntity(
                                    locationName = geoResponse.results[0].formattedAddress,
                                    longitude = geoResponse.results[0].geometry.location.lng,
                                    latitude = geoResponse.results[0].geometry.location.lat,
                                    countryLongName = country[0].longName,
                                    countryShortName = country[0].shortName
                            ))
                            rootView.tvCountry.text = country[0].longName
                        }
                    })
                    mainActivity.countingIdlingResource.decrement()
                }else{
                    rootView.tvCountry.text = ""
                }
            }
        }

        rootView.etLocation.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s?.length!! > 0){
                    lastTextEdit = System.currentTimeMillis()
                    handler.postDelayed(inputFinish, delay)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handler.removeCallbacks(inputFinish)
                locations.clear()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.mItem_done){

            if (locations.size > 0){
                viewModel.addLocation(locations[0])
            }


            viewModel.getStatus().observe(this, Observer {
                    when(it!!){
                        LocationRepositoryImpl.Status.SUCCESS ->{
                            locations.clear()
                            activity?.supportFragmentManager?.popBackStack()
                        }
                        LocationRepositoryImpl.Status.FAILURE ->{

                        }
                    }
                })

            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }
}

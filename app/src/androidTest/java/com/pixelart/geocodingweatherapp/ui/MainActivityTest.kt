package com.pixelart.geocodingweatherapp.ui

import android.content.Intent
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.pixelart.geocodingweatherapp.R
import com.pixelart.geocodingweatherapp.TestUtil
import com.pixelart.geocodingweatherapp.common.GEO_BASE_URL
import com.pixelart.geocodingweatherapp.common.WEATHER_BASE_URL
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickBack
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.closeKeyboard
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import com.schibsted.spain.barista.rule.cleardata.ClearPreferencesRule
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule var clearPreferencesRule = ClearPreferencesRule()
    private val GEO_FILE_NAME = "location_response.json"
    private val WEATHER_FILE_NAME = "weather_response.json"

    private val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)
    private lateinit var geoMockWebServer: MockWebServer
    private lateinit var weatherMockWebServer: MockWebServer

    @Before
    fun setUp(){

        geoMockWebServer = MockWebServer()
        weatherMockWebServer = MockWebServer()
        try {
            geoMockWebServer.start()
            geoMockWebServer.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(TestUtil.getStringFromFile(InstrumentationRegistry.getInstrumentation().context, GEO_FILE_NAME)))

            weatherMockWebServer.start()
            weatherMockWebServer.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(TestUtil.getStringFromFile(InstrumentationRegistry.getInstrumentation().context, WEATHER_FILE_NAME)))

        }catch (e: IOException){
            e.printStackTrace()
        }

        GEO_BASE_URL = geoMockWebServer.url("/").toString()
        WEATHER_BASE_URL = weatherMockWebServer.url("/").toString()
        activityTestRule.launchActivity(Intent())
    }

    @Test
    fun testViewInteractions(){
        val countingIdlingResource: CountingIdlingResource = activityTestRule.activity.countingIdlingResource
        IdlingRegistry.getInstance().register(countingIdlingResource)

        clickOn(R.id.btnAdd)
        writeTo(R.id.etLocation, "london")
        closeKeyboard()
        sleep(2000)
        assertDisplayed(R.id.tvCountry, "United Kingdom")
        clickOn(R.id.mItem_done)

        clickListItem(R.id.rvLocations, 0)
        sleep(2000)
        clickBack()

        openDrawer()
        clickOn("Settings")
        assertDisplayed(R.id.switchFahrenheit, "Display Fahrenheit")
        clickOn(R.id.switchFahrenheit)
        assertDisplayed(R.id.switchFahrenheit, "Display Celsius")
        clickBack()
    }
}
package com.pixelart.geocodingweatherapp.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pixelart.geocodingweatherapp.TestUtil
import com.pixelart.geocodingweatherapp.data.database.LocationDatabase
import com.pixelart.geocodingweatherapp.data.entities.LocationEntity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalRoomDBTest {
    private lateinit var database: LocationDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, LocationDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDatabase(){
        database.close()
    }

    @Test
    fun testDBInsertAndRetrieve(){
        val locationList = ArrayList<LocationEntity>()
        for (i in 0 until 10)
            locationList.add(
                LocationEntity(i, "Location $i", -21.55, 51.54,
                "Some Country $i", "SC$i")
            )

        locationList.forEach { database.getLocationDao().insert(it) }

        val locations:List<LocationEntity> = TestUtil.getValue(database.getLocationDao().getAllLocations())
        assert(locations.isNotEmpty())
        Assert.assertEquals("Location 5", locations[5].locationName)
    }
}
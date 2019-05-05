@file:Suppress("UNCHECKED_CAST")

package com.pixelart.geocodingweatherapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.annotation.Nullable

object TestUtil {

    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T{
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)

        val observer = object : Observer<T>{
            override fun onChanged(@Nullable value: T) {
                data[0] = value
                latch.countDown()
                liveData.removeObserver(this)
            }
        }

        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return data[0] as T
    }

    @Throws(IOException::class)
    internal fun getStringFromFile(context: Context, filepath: String): String {
        val inputStream = context.resources.assets
            .open(filepath)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        bufferedReader.readLines().forEach {
            stringBuilder.append(it)
        }

        bufferedReader.close()
        inputStream.close()

        return stringBuilder.toString()
    }
}
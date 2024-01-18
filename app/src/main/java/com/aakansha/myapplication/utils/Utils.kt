package com.aakansha.myapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import java.lang.Exception
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utils {
    var uid = 0
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
        return false
    }

    fun convertDateTimeStringToDateString(datetimeString: String): String {
        if(datetimeString.isNullOrEmpty()) return "1 Jan 1976"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val localDateTime = LocalDateTime.parse(datetimeString, formatter)
        val dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
        return localDateTime.format(dateFormatter)
    }



    fun iso8601ToUnixTimestamp(iso8601Timestamp: String): Long {
        if(iso8601Timestamp.isNullOrEmpty()) return 0L
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val instant = Instant.from(formatter.parse(iso8601Timestamp))
            return instant.epochSecond
        }
        catch (e:Exception){
            return 0L
        }


    }


    val darkGradientBrudshes = arrayListOf(

        // Deep Ocean: Blue to Purple
        Brush.verticalGradient(
            colors = listOf(Color(0xFF34495E), Color(0xFF4A148C)),
            tileMode = TileMode.Clamp
        ),

        // Midnight Sky: Navy to Black
        Brush.verticalGradient(
            colors = listOf(Color(0xFF2C3E50), Color(0xFF000000)),
            tileMode = TileMode.Clamp
        ),

        // Dark Forest: Green to Black
        Brush.verticalGradient(
            colors = listOf(Color(0xFF22543D), Color(0xFF000000)),
            tileMode = TileMode.Clamp
        ),

        // Dusky Moon: Dark Gray to Black
        Brush.verticalGradient(
            colors = listOf(Color(0xFF484848), Color(0xFF000000)),
            tileMode = TileMode.Clamp
        ),

        // Blood Moon: Deep Red to Black
        Brush.verticalGradient(
            colors = listOf(Color(0xFF990000), Color(0xFF000000)),
            tileMode = TileMode.Clamp
        ),

        // Cosmic Dusk: Purple to Dark Blue
        Brush.verticalGradient(
            colors = listOf(Color(0xFF4A148C), Color(0xFF34495E)),
            tileMode = TileMode.Clamp
        ),

        // Nebula Dream: Dark Purple to Violet
        Brush.verticalGradient(
            colors = listOf(Color(0xFF4A148C), Color(0xFF5E5CA7)),
            tileMode = TileMode.Clamp
        ),

        // Twilight Fog: Dark Gray to Purple
        Brush.verticalGradient(
            colors = listOf(Color(0xFF454545), Color(0xFF4A148C)),
            tileMode = TileMode.Clamp
        ),

        // Galaxy Glow: Blue to Purple to Pink
        Brush.verticalGradient(
            colors = listOf(Color(0xFF34495E), Color(0xFF4A148C), Color(0xFFC51162)),
            tileMode = TileMode.Clamp
        ),

        // Aurora Night: Blue to Green to Purple
        Brush.verticalGradient(
            colors = listOf(Color(0xFF3498DB), Color(0xFF2ECC71), Color(0xFF9B59B6)),
            tileMode = TileMode.Clamp
        )
    )
}
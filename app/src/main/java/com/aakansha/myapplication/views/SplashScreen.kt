package com.aakansha.myapplication.views

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aakansha.myapplication.MainActivity
import com.aakansha.myapplication.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SplashScreen(){
    val context = LocalContext.current
    Handler(Looper.getMainLooper()).postDelayed(
        {
        context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        }, 4000)
    Column(
        modifier = Modifier
//            .background(Color.Black)
            .paint(
                painter = painterResource(id = R.drawable.space_background),
                contentScale = ContentScale.FillBounds
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    )
    {

        Image(painter = painterResource(id = R.drawable.starwarslogo), contentDescription ="Star War Logo" )


// remember lottie composition, which
// accepts the lottie composition result
        val composition by rememberLottieComposition(

            LottieCompositionSpec
                // here `code` is the file name of lottie file
                // use it accordingly
                .RawRes(R.raw.splash_lottie_json)
        )

        // to control the animation
        val progress by animateLottieCompositionAsState(
            // pass the composition created above
            composition,

            // Iterates Forever
            iterations = LottieConstants.IterateForever,

            // pass isPlaying we created above,
            // changing isPlaying will recompose
            // Lottie and pause/play
            isPlaying = true,

            // pass speed we created above,
            // changing speed will increase Lottie
//            speed = speed,

            // this makes animation to restart
            // when paused and play
            // pass false to continue the animation
            // at which it was paused
            restartOnPlay = false

        )
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(400.dp)
        )

    }
}
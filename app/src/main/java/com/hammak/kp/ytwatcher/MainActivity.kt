package com.hammak.kp.ytwatcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment

private const val PLAYLIST_ID = "PLZfhqd1-Hl3BdvYUJaB2eOJ1JoFchUeMv"
private const val API_KEY = "AIzaSyBKEc-HhscdHeUZ658_jhEzYiSEEgkRpQM"

class MainActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        @Suppress("DEPRECATION")
        val fragment = fragmentManager.findFragmentById(R.id.yt_player_fragment) as YouTubePlayerFragment
        fragment.initialize(API_KEY, this)

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (p1 != null) {
            p1.cueVideo("Awpj7bnyWYk")
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        TODO("Not yet implemented")
    }
}

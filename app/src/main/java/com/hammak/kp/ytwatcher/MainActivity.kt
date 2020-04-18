package com.hammak.kp.ytwatcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubePlayerFragment

private const val PLAYLIST_ID = "PLZfhqd1-Hl3BdvYUJaB2eOJ1JoFchUeMv"
private const val API_KEY = "AIzaSyBKEc-HhscdHeUZ658_jhEzYiSEEgkRpQM"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        @Suppress("DEPRECATION")
        val fragment =
            fragmentManager.findFragmentById(R.id.yt_player_fragment) as YouTubePlayerFragment
        fragment.initialize(API_KEY,
            YouTubePlayerOnInitializedListenerHandler()
        )
    }
}

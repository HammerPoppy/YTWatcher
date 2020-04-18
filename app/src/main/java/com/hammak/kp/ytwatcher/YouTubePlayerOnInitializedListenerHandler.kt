package com.hammak.kp.ytwatcher

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class YouTubePlayerOnInitializedListenerHandler : YouTubePlayer.OnInitializedListener {

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
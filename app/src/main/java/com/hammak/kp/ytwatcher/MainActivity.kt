package com.hammak.kp.ytwatcher

import Root
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hammak.kp.ytwatcher.videolistfragment.VideoData
import com.hammak.kp.ytwatcher.videolistfragment.VideoListFragment
import com.hammak.kp.ytwatcher.videolistfragment.VideoListFragment.OnListFragmentInteractionListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

//private const val PLAYLIST_ID = "PLZfhqd1-Hl3BdvYUJaB2eOJ1JoFchUeMv" // SG
//private const val PLAYLIST_ID = "PLb48Ubr_9WeXp7jzHVI4B9w00xX-6M1Ex" // Karamyshev
private const val PLAYLIST_ID = "PL8Zztx2W2NQkYZPbVa3e7ZX-T7SFYQP_r" // Absurd
private const val API_KEY = 

class MainActivity : AppCompatActivity(), OnListFragmentInteractionListener {

    private val apiServe by lazy {
        ApiService.create()
    }

    private var disposable: Disposable? = null
    lateinit var youTubePlayer: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData("")
        initYouTubePlayer()
    }

    private fun initYouTubePlayer() {
        lifecycle.addObserver(ytplayer)
        ytplayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@MainActivity.youTubePlayer = youTubePlayer
            }
        })
        ytplayer.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            @SuppressLint("SourceLockedOrientationActivity")
            override fun onYouTubePlayerEnterFullScreen() {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }

            @SuppressLint("SourceLockedOrientationActivity")
            override fun onYouTubePlayerExitFullScreen() {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                window.clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }

        })
    }

    private fun startListFragment() {
        loadingTextView.visibility = View.INVISIBLE
        val f = VideoListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.list, f as Fragment)
            .show(f)
            .commit()
    }

    private fun loadData(nextPageToken: String) {
        disposable =
            apiServe.getPlaylistItemsInfo(
                "snippet, id",
                50,
                nextPageToken,
                PLAYLIST_ID,
                API_KEY
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> onLoadDataResult(result) },
                    { error -> onLoadDataError(error.message) }
                )
    }

    private fun onLoadDataResult(result: Root) {
        result.items.forEach {
            VideoData.addItem(
                thumbnailURL = it.snippet.thumbnails.medium.url,
                title = it.snippet.title,
                position = it.snippet.position,
                id = it.snippet.resourceId.videoId
            )
        }

        if (result.nextPageToken.isEmpty()) {
            startListFragment()
        } else
            loadData(result.nextPageToken)
    }

    @SuppressLint("SetTextI18n")
    private fun onLoadDataError(message: String?) {

        loadingTextView.text = "Fetching API data error 1.\nError message: \"$message\"."
        loadingTextView.textSize = 10f
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onListFragmentInteraction(item: VideoData.Video) {
        ytplayer.visibility = View.VISIBLE
        youTubePlayer.loadOrCueVideo(lifecycle, item.id, 0F)
    }
}

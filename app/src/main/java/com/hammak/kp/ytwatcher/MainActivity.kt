package com.hammak.kp.ytwatcher

import Root
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubePlayerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

private const val PLAYLIST_ID = "PLZfhqd1-Hl3BdvYUJaB2eOJ1JoFchUeMv"
private const val API_KEY = "AIzaSyBKEc-HhscdHeUZ658_jhEzYiSEEgkRpQM"

class MainActivity : AppCompatActivity() {

    val apiServe by lazy {
        ApiService.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ytPlayerFragment = YouTubePlayerFragment()
//        fragmentManager.beginTransaction()
//            .add(R.id.root, ytPlayerFragment)
//            .show(ytPlayerFragment)
//            .commit()

        loadData("")

//        ytPlayerFragment.initialize(
//            API_KEY,
//            YouTubePlayerOnInitializedListenerHandler()
//        )
    }

    private fun loadData(nextPageToken: String) {
        disposable =
            apiServe.getPlaylistItemsInfo(
                "snippet",
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
                position = it.snippet.position
            )
        }

        if (result.nextPageToken.isNullOrEmpty())
//            launchFragment()
        else
            loadData(result.nextPageToken)
    }

    private fun onLoadDataError(message: String?) {

        loadingTextView.text = "Fetching API data error 1.\nError message: \"$message\"."
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}

package com.hammak.kp.ytwatcher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.youtube.player.YouTubePlayerFragment

private const val ARG_PARAM1 = "playlist_id"
private const val ARG_PARAM2 = "video_position"

/**
 * A simple [Fragment] subclass.
 * Use the [MyYouTubePlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyYouTubePlayerFragment : YouTubePlayerFragment() {
    private var playlistId: String? = null
    private var videoPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            playlistId = it.getString(ARG_PARAM1)
            videoPosition = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            setText(R.string.hello_blank_fragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param playlistId Playlist ID.
         * @param videoPosition Video position in playlist.
         * @return A new instance of fragment MyYouTubePlayerFragment.
         */
        @JvmStatic
        fun newInstance(playlistId: String, videoPosition: String) =
            MyYouTubePlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, playlistId)
                    putString(ARG_PARAM2, videoPosition)
                }
            }
    }
}

package com.hammak.kp.ytwatcher.videolistfragment

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hammak.kp.ytwatcher.R


import com.hammak.kp.ytwatcher.videolistfragment.VideoListFragment.OnListFragmentInteractionListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_video_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [VideoData] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class VideoRecyclerViewAdapter(
    private val values: List<VideoData.Video>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as VideoData.Video
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            listener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_video_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        Picasso.get().load(item.thumbnailURL).into(holder.thumbnail)
        holder.videoTitle.text = item.title

        with(holder.view) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.thumbnail
        val videoTitle: TextView = view.videoTitle
    }
}

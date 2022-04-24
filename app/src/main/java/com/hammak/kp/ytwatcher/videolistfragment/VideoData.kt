package com.hammak.kp.ytwatcher.videolistfragment

object VideoData {

    val ITEMS: MutableList<Video> = ArrayList()

    fun addItem(thumbnailURL: String, title: String, position: Int, id: String) {
        ITEMS.add(
            position,
            Video(
                thumbnailURL,
                title,
                id
            )
        )
    }

    data class Video(val thumbnailURL: String, val title: String, val id: String)
}
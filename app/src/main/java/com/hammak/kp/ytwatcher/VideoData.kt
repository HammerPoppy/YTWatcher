package com.hammak.kp.ytwatcher

object VideoData {

    val ITEMS: MutableList<Video> = ArrayList()

    fun addItem(thumbnailURL: String, title: String, position: Int) {
        ITEMS.add(
            position,
            Video(
                thumbnailURL,
                title
            )
        )
    }

    data class Video(val thumbnailURL: String, val title: String)
}
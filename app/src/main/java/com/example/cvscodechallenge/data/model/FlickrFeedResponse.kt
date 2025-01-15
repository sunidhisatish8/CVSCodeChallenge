package com.example.cvscodechallenge.data.model

import com.google.gson.annotations.SerializedName

data class FlickrFeedResponse(
    val description: String,
    val generator: String,
    @SerializedName("items")
    val photoItems: List<PhotoItem>,
    val link: String,
    val modified: String,
    val title: String
)
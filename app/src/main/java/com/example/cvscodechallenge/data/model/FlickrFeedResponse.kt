package com.example.cvscodechallenge.data.model

data class FlickrFeedResponse(
    val description: String,
    val generator: String,
    val items: List<Item>,
    val link: String,
    val modified: String,
    val title: String
)
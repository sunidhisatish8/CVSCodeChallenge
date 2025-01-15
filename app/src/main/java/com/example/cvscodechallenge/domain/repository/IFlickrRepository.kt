package com.example.cvscodechallenge.domain.repository

import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.utils.ResponseState

interface IFlickrRepository {
    suspend fun getFlickrPhotoFeed(
        tagName: String
    ): ResponseState<FlickrFeedResponse>
}
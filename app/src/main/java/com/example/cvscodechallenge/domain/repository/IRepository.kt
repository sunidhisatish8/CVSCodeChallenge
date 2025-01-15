package com.example.cvscodechallenge.domain.repository

import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.utils.ResponseState

interface IRepository {
    suspend fun getFlickrPhotoFeed(
        tagName: String
    ): ResponseState<FlickrFeedResponse>
}
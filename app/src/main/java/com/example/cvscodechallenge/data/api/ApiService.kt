package com.example.cvscodechallenge.data.api

import com.example.cvscodechallenge.data.model.Constants
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.END_POINT)
    suspend fun getFlickrPhotoFeed(
        @Query("format") formatType: String = "json",
        @Query("nojsoncallback") jsonFormat: Int = 1,
        @Query("tags") tagName: String
    ) : Response<FlickrFeedResponse>
}
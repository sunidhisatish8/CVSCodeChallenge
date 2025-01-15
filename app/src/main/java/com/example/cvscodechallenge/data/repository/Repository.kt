package com.example.cvscodechallenge.data.repository

import com.example.cvscodechallenge.data.api.ApiService
import com.example.cvscodechallenge.data.interceptor.NetworkInterceptor
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.utils.ResponseState
import com.example.cvscodechallenge.domain.repository.IRepository
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) : IRepository {
    override suspend fun getFlickrPhotoFeed(
        tagName: String
    ): ResponseState<FlickrFeedResponse> {
        return try {
            val response = apiService.getFlickrPhotoFeed(tagName = tagName)
            if (response.isSuccessful) {
                response.body()?.let {
                    ResponseState.Success(it)
                } ?: ResponseState.Failed("Empty response body")
            } else {
                ResponseState.Failed("Failed with error code - ${response.code()} and error message - ${response.message()}")
            }
        } catch (networkException: NetworkInterceptor.NoInternetException) {
            ResponseState.Failed("Failed with the message - ${networkException.message}")
        } catch (exception: Exception) {
            ResponseState.Failed("Failed with the message - ${exception.message}")
        }
    }
}
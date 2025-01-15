package com.example.cvscodechallenge.data.repository

import com.example.cvscodechallenge.data.api.FlickrApiService
import com.example.cvscodechallenge.data.interceptor.NetworkInterceptor
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.utils.ResponseState
import com.example.cvscodechallenge.domain.repository.IFlickrRepository
import javax.inject.Inject

/**
 * A repository that fetches the Flickr photo feed from the API.
 * It implements the `IFlickrRepository` interface and `ApiService` to retrieve photo feed data.
 *
 * @param flickrApiService The API service used to make network requests.
 */
class FlickrRepository @Inject constructor(private val flickrApiService: FlickrApiService) : IFlickrRepository {
    override suspend fun getFlickrPhotoFeed(
        tagName: String
    ): ResponseState<FlickrFeedResponse> {
        return try {
            val response = flickrApiService.getFlickrPhotoFeed(tagName = tagName)
            if (response.isSuccessful) {
                response.body()?.let {
                    ResponseState.Success(it)
                } ?: ResponseState.Failed("Empty response body")
            } else {
                ResponseState.Failed("Failed with error code - ${response.code()} and error message - ${response.message()}")
            }
        } catch (networkException: NetworkInterceptor.NoInternetException) {
            ResponseState.Failed(networkException.message)
        } catch (exception: Exception) {
            ResponseState.Failed("Failed with the message - ${exception.message}")
        }
    }
}
package com.example.cvscodechallenge.domain.useCase

import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.utils.ResponseState
import com.example.cvscodechallenge.domain.repository.IRepository
import javax.inject.Inject

class GetFlickPhotoFeedUseCase @Inject constructor(private val repository: IRepository) {
    suspend operator fun invoke(
        tagName: String
    ): ResponseState<FlickrFeedResponse> {
        return repository.getFlickrPhotoFeed(tagName)
    }
}
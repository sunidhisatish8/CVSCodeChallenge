package com.example.cvscodechallenge.domain.useCase

import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.utils.ResponseState
import com.example.cvscodechallenge.domain.repository.IFlickrRepository
import javax.inject.Inject

/**
 * A use case class responsible for fetching the Flickr photo feed.
 * It interacts with the repository layer to fetch the photo feed data.
 *
 * @param flickrRepository The repository that handles fetching the Flickr photo feed data.
 */
class GetFlickPhotoFeedUseCase @Inject constructor(private val flickrRepository: IFlickrRepository) {
    suspend operator fun invoke(
        tagName: String
    ): ResponseState<FlickrFeedResponse> {
        return flickrRepository.getFlickrPhotoFeed(tagName)
    }
}
package com.example.cvscodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.data.repository.FlickrRepository
import com.example.cvscodechallenge.domain.useCase.GetFlickPhotoFeedUseCase
import com.example.cvscodechallenge.utils.ResponseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetFlickrPhotoFeedUseCaseTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var getFlickrPhotoFeedUseCase: GetFlickPhotoFeedUseCase
    private lateinit var flickrRepository: FlickrRepository

    @Before
    fun setup() {
        flickrRepository = mockk()
        getFlickrPhotoFeedUseCase = GetFlickPhotoFeedUseCase(flickrRepository)
    }

    @Test
    fun `verify if the flickr photo feed use case returns the photo details successfully`() =
        runTest {
            val mockTagName = "porcupine"
            val mockResponse = FlickrFeedResponse(
                title = "Mock Title",
                description = "Mock Description",
                link = "Mock Link",
                photoItems = emptyList(),
                modified = "Mock modified",
                generator = "Mock generator"
            )

            coEvery { flickrRepository.getFlickrPhotoFeed(tagName = mockTagName) } returns ResponseState.Success(
                mockResponse
            )

            val actualResponse = getFlickrPhotoFeedUseCase.invoke(mockTagName)
            val successResponse = actualResponse as ResponseState.Success
            assertEquals(successResponse.data, mockResponse)

            coVerify { flickrRepository.getFlickrPhotoFeed(tagName = mockTagName) }
        }

    @Test
    fun `verify if the flickr photo feed use case is handling the network error correctly`() = runTest {
        val mockTagName = "porcupine"
        val mockError = Exception("Network error")
        coEvery { flickrRepository.getFlickrPhotoFeed(tagName = mockTagName) } returns ResponseState.Failed(
            mockError.message
        )

        val actualResponse = getFlickrPhotoFeedUseCase.invoke(mockTagName)
        assert(actualResponse is ResponseState.Failed)
        val errorResponse = actualResponse as ResponseState.Failed
        assertEquals(errorResponse.message, actualResponse.message)

        coVerify { flickrRepository.getFlickrPhotoFeed(tagName = mockTagName) }
    }
}
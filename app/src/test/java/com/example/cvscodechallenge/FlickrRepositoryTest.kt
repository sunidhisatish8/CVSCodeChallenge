package com.example.cvscodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cvscodechallenge.data.api.FlickrApiService
import com.example.cvscodechallenge.data.interceptor.NetworkInterceptor
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.data.repository.FlickrRepository
import com.example.cvscodechallenge.utils.ResponseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class FlickrRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var flickrRepository: FlickrRepository
    private lateinit var flickrApiService: FlickrApiService

    @Before
    fun setup() {
        flickrApiService = mockk()
        flickrRepository = FlickrRepository(flickrApiService)
    }

    @Test
    fun `verify repository returns the photo details successfully`() = runTest {
        val mockTagName = "porcupine"
        val mockResponse = FlickrFeedResponse(
            title = "Mock Title",
            description = "Mock Description",
            link = "Mock Link",
            photoItems = emptyList(),
            modified = "Mock modified",
            generator = "Mock generator"
        )

        coEvery { flickrApiService.getFlickrPhotoFeed(tagName = mockTagName) } returns Response.success(
            mockResponse
        )

        val actualResponse = flickrRepository.getFlickrPhotoFeed(mockTagName)
        val successResponse = actualResponse as ResponseState.Success
        assertEquals(successResponse.data, mockResponse)

        coVerify { flickrApiService.getFlickrPhotoFeed(tagName = mockTagName) }
    }

    @Test
    fun `verify if the repository is handling the network error correctly`() = runTest {
        val mockTagName = "porcupine"
        coEvery { flickrApiService.getFlickrPhotoFeed(tagName = mockTagName) } throws NetworkInterceptor.NoInternetException("No internet connection")

        val actualResponse = flickrRepository.getFlickrPhotoFeed(mockTagName)
        assert(actualResponse is ResponseState.Failed)
        assertEquals("No internet connection", (actualResponse as ResponseState.Failed).message)

        coVerify { flickrApiService.getFlickrPhotoFeed(tagName = mockTagName) }
    }
}
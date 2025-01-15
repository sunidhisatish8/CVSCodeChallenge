package com.example.cvscodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.domain.useCase.GetFlickPhotoFeedUseCase
import com.example.cvscodechallenge.presentation.viewModel.FlickrPhotoViewModel
import com.example.cvscodechallenge.utils.ResponseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlickrViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var flickrPhotoViewModel: FlickrPhotoViewModel
    private lateinit var getFlickPhotoFeedUseCase: GetFlickPhotoFeedUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getFlickPhotoFeedUseCase = mockk()
        flickrPhotoViewModel = FlickrPhotoViewModel(getFlickPhotoFeedUseCase)
    }

    @Test
    fun `verify if photo details are displayed successfully`() = runTest {
        val mockTagName = "porcupine"
        val mockResponse = FlickrFeedResponse(
            title = "Mock Title",
            description = "Mock Description",
            link = "Mock Link",
            photoItems = emptyList(),
            modified = "Mock modified",
            generator = "Mock generator"
        )

        coEvery { getFlickPhotoFeedUseCase.invoke(mockTagName) } returns ResponseState.Success(
            mockResponse
        )

        flickrPhotoViewModel.fetchFlickrPhotoDetails(mockTagName)
        advanceUntilIdle()

        val actualResponse = flickrPhotoViewModel.photoDetails.first()
        val successResponse = actualResponse as ResponseState.Success
        assertEquals(successResponse.data, mockResponse)

        coVerify { getFlickPhotoFeedUseCase.invoke(mockTagName) }
    }

    @Test
    fun `verify if the network error is handled correctly`() = runTest {
        val mockTagName = "porcupine"
        val mockError = Exception("Network error")

        coEvery { getFlickPhotoFeedUseCase.invoke(mockTagName) } returns ResponseState.Failed(
            mockError.message
        )

        flickrPhotoViewModel.fetchFlickrPhotoDetails(mockTagName)
        advanceUntilIdle()

        val actualResponse = flickrPhotoViewModel.photoDetails.first()
        val errorResponse = actualResponse as ResponseState.Failed
        assertEquals(errorResponse.message, mockError.message)

        coVerify { getFlickPhotoFeedUseCase.invoke(mockTagName) }
    }

    @Test
    fun `verify if loading state is shown when tag name is empty`() = runTest {
        val mockTagName = ""

        flickrPhotoViewModel.fetchFlickrPhotoDetails(mockTagName)
        advanceUntilIdle()

        val actualResponse = flickrPhotoViewModel.photoDetails.first()
        assertTrue(actualResponse is ResponseState.Loading)

        coVerify(exactly = 0) { getFlickPhotoFeedUseCase.invoke(mockTagName) }
    }
}
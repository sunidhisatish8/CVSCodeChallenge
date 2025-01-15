package com.example.cvscodechallenge.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.domain.useCase.GetFlickPhotoFeedUseCase
import com.example.cvscodechallenge.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing the state of the Flickr photo feed screen.
 * It handles the logic of fetching the photo feed details based on the tag name input.
 *
 * @param getFlickPhotoFeedUseCase The use case that fetches the Flickr photo feed data.
 */
@HiltViewModel
class FlickrPhotoViewModel @Inject constructor(
    private val getFlickPhotoFeedUseCase: GetFlickPhotoFeedUseCase
) : ViewModel() {
    private var _photoDetails =
        MutableStateFlow<ResponseState<FlickrFeedResponse>>(ResponseState.Loading())
    val photoDetails: StateFlow<ResponseState<FlickrFeedResponse>> = _photoDetails

    private var _tagName = MutableStateFlow("")
    val tagName: StateFlow<String> = _tagName

    fun fetchFlickrPhotoDetails(newTagName: String) {
        viewModelScope.launch {
            _tagName.value = newTagName
            _tagName
                .debounce(500)
                .distinctUntilChanged()
                .filter { it.isNotEmpty() }
                .collect { newTagName ->
                    _photoDetails.value = ResponseState.Loading()
                    try {
                        _photoDetails.value = getFlickPhotoFeedUseCase.invoke(newTagName)
                    } catch (exception : Exception){
                        _photoDetails.value = ResponseState.Failed(exception.message)
                    }
                }
        }
    }
}
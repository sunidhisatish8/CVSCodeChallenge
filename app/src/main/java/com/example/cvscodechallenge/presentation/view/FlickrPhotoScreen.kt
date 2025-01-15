package com.example.cvscodechallenge.presentation.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cvscodechallenge.data.model.FlickrFeedResponse
import com.example.cvscodechallenge.data.model.Item
import com.example.cvscodechallenge.presentation.viewModel.FlickrPhotoViewModel
import com.example.cvscodechallenge.utils.ResponseState
import com.example.cvscodechallenge.utils.formatDate

@Composable
fun FlickrPhotoScreen() {
    val flickrPhotoViewModel: FlickrPhotoViewModel = hiltViewModel()
    val photoDetails by flickrPhotoViewModel.photoDetails.collectAsState()
    val tagName by flickrPhotoViewModel.tagName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchQueryChange(
            tagName,
            onSearchQuery = { flickrPhotoViewModel.fetchFlickrPhotoDetails(it) }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        FetchPhotoDetails(photoDetails)
    }
}

@Composable
fun SearchQueryChange(inputValue: String, onSearchQuery: (String) -> Unit) {
    TextField(
        value = inputValue,
        onValueChange = onSearchQuery,
        label = { Text(text = "Search by tag name") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun FetchPhotoDetails(photoDetails: ResponseState<FlickrFeedResponse>) {
    val context = LocalContext.current
    when (photoDetails) {
        is ResponseState.Loading -> {
            CircularProgressIndicator()
        }

        is ResponseState.Success -> {
            FetchSearchResult(photoDetails.data)
        }

        is ResponseState.Failed -> {
            Toast.makeText(context, photoDetails.message, Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FetchSearchResult(flickrFeedResponse: FlickrFeedResponse) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedPhotos by remember { mutableStateOf<Item?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn {
            items(flickrFeedResponse.items) { photoItem ->
                GlideImage(
                    model = photoItem.media.image,
                    contentDescription = "Flickr images",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(bottom = 16.dp)
                        .clickable {
                            selectedPhotos = photoItem
                            showBottomSheet = true
                        }
                )
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = rememberModalBottomSheetState(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    model = selectedPhotos?.media?.image,
                    contentDescription = "Flickr images",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp),
                )
            }
            Text(
                text = "Title: ${selectedPhotos?.title}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Description: ${selectedPhotos?.description}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Author: ${selectedPhotos?.author}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Published Date: ${selectedPhotos?.published?.formatDate()}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}
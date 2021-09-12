package com.example.imagesearchapp.ui.main

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor( val state : SavedStateHandle,
                                            val unsplashRepository: UnsplashRepository ) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap {
        unsplashRepository.searchResults(it).cachedIn(viewModelScope)
    }

    fun searchPhotos(query : String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "java"
    }
}
package com.example.imagesearchapp.ui.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imagesearchapp.ui.main.api.UnsplashApi
import com.example.imagesearchapp.ui.main.paging.UnsplashPagingSource
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi : UnsplashApi) {

    fun searchResults(query : String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
    ).liveData
}
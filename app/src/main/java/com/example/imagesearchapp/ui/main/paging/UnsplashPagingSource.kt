package com.example.imagesearchapp.ui.main.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearchapp.ui.main.api.UnsplashApi
import com.example.imagesearchapp.ui.main.data.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_POSITION_INDEX = 1

class UnsplashPagingSource(
    private val unsplashApi : UnsplashApi,
    private val query : String
) : PagingSource<Int, UnsplashPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_POSITION_INDEX


        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if(position == UNSPLASH_STARTING_POSITION_INDEX) null else position - 1,
                nextKey = if(photos.isEmpty()) null else position + 1
            )
        } catch (ioe : IOException) {
            LoadResult.Error(ioe)
        } catch (httpException : HttpException) {
            LoadResult.Error(httpException)
        }
    }

}
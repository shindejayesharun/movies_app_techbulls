package com.shindejayesharun.moviesapptechbulls.ui.component.movie

import androidx.paging.PagingSource
import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import com.shindejayesharun.moviesapptechbulls.data.remote.service.MoviesService

class MoviePagingSource(
    val movieApiService: MoviesService,
) : PagingSource<Int, Movies.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies.Movie> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val response = movieApiService.fetchMovies("iron",nextPage)

            return LoadResult.Page(
                data = response.body()!!.Search,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
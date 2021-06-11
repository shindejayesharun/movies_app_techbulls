package com.shindejayesharun.moviesapptechbulls.data.remote

import com.shindejayesharun.moviesapptechbulls.data.Resource
import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies

/**
 * Created by shindejayesharun
 */

internal interface RemoteDataSource {
    suspend fun requestMovies(movieName:String, page:Int): Resource<Movies>
}

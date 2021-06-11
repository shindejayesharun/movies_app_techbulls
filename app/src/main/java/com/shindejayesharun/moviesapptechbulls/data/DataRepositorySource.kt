package com.shindejayesharun.moviesapptechbulls.data

import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import kotlinx.coroutines.flow.Flow

/**
 * Created by shindejayesharun
 */

interface DataRepositorySource {
    suspend fun requestMovies(movieName: String,page:Int): Flow<Resource<Movies>>

}

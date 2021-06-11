package com.shindejayesharun.moviesapptechbulls.data.remote.service

import com.shindejayesharun.moviesapptechbulls.API_KEY
import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by shindejayesharun
 */

interface MoviesService {
    @GET("?apikey=$API_KEY")
    suspend fun fetchMovies(@Query("s") movieName: String?, @Query("page") pageNo: Int?): Response<Movies>
}

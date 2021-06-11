package com.shindejayesharun.moviesapptechbulls.data.remote

import com.shindejayesharun.moviesapptechbulls.data.Resource
import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import com.shindejayesharun.moviesapptechbulls.data.error.NETWORK_ERROR
import com.shindejayesharun.moviesapptechbulls.data.error.NO_INTERNET_CONNECTION
import com.shindejayesharun.moviesapptechbulls.data.remote.service.MoviesService
import com.shindejayesharun.moviesapptechbulls.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


/**
 * Created by shindejayesharun
 */

class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) : RemoteDataSource {

    override suspend fun requestMovies(movieName:String, page:Int): Resource<Movies> {
        val moviesService = serviceGenerator.createService(MoviesService::class.java)
        return when (val response = processCall({ moviesService.fetchMovies(movieName,1) })) {
            is Movies -> {
                Resource.Success(data = response as Movies)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}

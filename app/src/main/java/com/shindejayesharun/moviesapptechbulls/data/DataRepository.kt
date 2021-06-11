package com.shindejayesharun.moviesapptechbulls.data


import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import com.shindejayesharun.moviesapptechbulls.data.local.LocalData
import com.shindejayesharun.moviesapptechbulls.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by shindejayesharun
 */

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {



    override suspend fun requestMovies(movieName:String, page:Int): Flow<Resource<Movies>> {
        return flow {
            emit(remoteRepository.requestMovies(movieName,page))
        }.flowOn(ioDispatcher)
    }


}

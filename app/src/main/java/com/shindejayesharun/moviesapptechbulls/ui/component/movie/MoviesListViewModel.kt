package com.shindejayesharun.moviesapptechbulls.ui.component.movie

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shindejayesharun.moviesapptechbulls.data.DataRepositorySource
import com.shindejayesharun.moviesapptechbulls.data.Resource
import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import com.shindejayesharun.moviesapptechbulls.ui.base.BaseViewModel
import com.shindejayesharun.moviesapptechbulls.utils.SingleEvent
import com.shindejayesharun.moviesapptechbulls.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by shindejayesharun
 */
@HiltViewModel
class MoviesListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val moviesLiveDataPrivate = MutableLiveData<Resource<Movies>>()
    val moviesLiveData: LiveData<Resource<Movies>> get() = moviesLiveDataPrivate


    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getMovies(movieName: String,page:Int) {
        viewModelScope.launch {
            moviesLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestMovies(movieName,page).collect {
                    moviesLiveDataPrivate.value = it
                }
            }
        }
    }


    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

    fun onSearchClick(movieName: String) {
        getMovies(movieName,1)
    }
}

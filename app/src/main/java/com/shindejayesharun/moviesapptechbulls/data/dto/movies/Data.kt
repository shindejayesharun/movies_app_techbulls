package com.kanchanpal.newsfeed.api

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.shindejayesharun.moviesapptechbulls.data.Resource

/**
 * Data class that is necessary for a UI to show a listing and interact w/ the rest of the system
 */
data class Data<T : Any>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
    // represents the network request status to show to the user
    val networkState: LiveData<Resource<T>>)

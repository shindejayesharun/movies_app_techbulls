package com.shindejayesharun.moviesapptechbulls.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {
    private var visibleThreshold = 2
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    // it can LinearLayoutManager as well
    fun EndlessScrollListener(layoutManager: GridLayoutManager) {
        mLayoutManager = layoutManager
        visibleThreshold = visibleThreshold * layoutManager.spanCount
    }



    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)

}
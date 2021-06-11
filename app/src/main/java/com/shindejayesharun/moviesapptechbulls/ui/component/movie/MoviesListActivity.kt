package com.shindejayesharun.moviesapptechbulls.ui.component.movie

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.shindejayesharun.moviesapptechbulls.R
import com.shindejayesharun.moviesapptechbulls.data.Resource
import com.shindejayesharun.moviesapptechbulls.data.dto.movies.Movies
import com.shindejayesharun.moviesapptechbulls.data.error.SEARCH_ERROR
import com.shindejayesharun.moviesapptechbulls.databinding.HomeActivityBinding
import com.shindejayesharun.moviesapptechbulls.ui.base.BaseActivity
import com.shindejayesharun.moviesapptechbulls.utils.*
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by shindejayesharun
 */
@AndroidEntryPoint
class MoviesListActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    private val moviesListViewModel: MoviesListViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter
    private var scrollListener: EndlessRecyclerViewScrollListener? = null


    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.recipe)
        val layoutManager = LinearLayoutManager(this)
        binding.rvMoviesList.layoutManager = layoutManager
        moviesListViewModel.getMovies("batman",1)
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                moviesListViewModel.getMovies("batman",page)
            }
        }
        binding.rvMoviesList.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_name)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> moviesListViewModel.getMovies("batman",1)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSearch(query: String) {
        if (query.isNotEmpty()) {
            binding.pbLoading.visibility = VISIBLE
            moviesListViewModel.onSearchClick(query)
        }
    }


    private fun bindListData(movies: Movies) {
        if (!(movies.Search.isNullOrEmpty())) {
            moviesAdapter = MoviesAdapter(moviesListViewModel, movies.Search)
            binding.rvMoviesList.adapter = moviesAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<Movies.Movie>) {
        /*navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(RECIPE_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }*/
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        moviesListViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvMoviesList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvMoviesList.toGone()
    }




    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.toGone()
    }

    private fun handleRecipesList(status: Resource<Movies>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(movies = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { moviesListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(moviesListViewModel.moviesLiveData, ::handleRecipesList)
        observeSnackBarMessages(moviesListViewModel.showSnackBar)
        observeToast(moviesListViewModel.showToast)

    }
}

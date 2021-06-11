package com.shindejayesharun.moviesapptechbulls.ui.base

import androidx.lifecycle.ViewModel
import com.shindejayesharun.moviesapptechbulls.usecase.errors.ErrorManager
import javax.inject.Inject


/**
 * Created by shindejayesharun
 */


abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    @Inject
    lateinit var errorManager: ErrorManager
}

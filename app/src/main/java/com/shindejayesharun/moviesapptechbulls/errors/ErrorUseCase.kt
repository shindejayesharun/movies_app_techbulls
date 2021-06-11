package com.shindejayesharun.moviesapptechbulls.usecase.errors

import com.shindejayesharun.moviesapptechbulls.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}

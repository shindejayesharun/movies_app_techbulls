package com.shindejayesharun.moviesapptechbulls.usecase.errors

import com.shindejayesharun.moviesapptechbulls.data.error.Error
import com.shindejayesharun.moviesapptechbulls.data.error.mapper.ErrorMapper
import javax.inject.Inject

/**
 * Created by shindejayesharun
 */

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}

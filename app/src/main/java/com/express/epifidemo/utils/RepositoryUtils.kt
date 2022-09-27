package com.express.epifidemo.utils

import android.util.Log
import com.express.epifidemo.data.Result
import retrofit2.HttpException
import java.net.UnknownHostException

inline fun <T> repositoryTryCatch(action: () -> Result<T>): Result<T> {
    return try {
        action()
    } catch (e: HttpException) {
        Log.d("HttpException ", e.message())
        Result.Failure(e.message())
    } catch (e: UnknownHostException) {
        Result.Failure("Please check your internet connection")
    } catch (e: Exception) {
        Result.Failure(e.localizedMessage ?: "Some error occurred")
    }
}
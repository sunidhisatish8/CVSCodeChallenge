package com.example.cvscodechallenge.data.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(@ApplicationContext private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable(context)) {
            throw NoInternetException("No internet connection is available")
        }
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    class NoInternetException(message: String) : IOException(message)
}
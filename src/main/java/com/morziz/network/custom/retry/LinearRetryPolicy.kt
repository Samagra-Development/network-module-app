package com.morziz.network.custom.retry

import android.util.Log
import com.morziz.network.custom.RetryPolicy
import retrofit2.Call
import retrofit2.Callback

class LinearRetryPolicy(private val retryMaxCount: Int) : RetryPolicy {
    var retryCount = 0
    override fun <T> retry(proxy: Call<T>, callback: Callback<T>): Boolean {
        if (retryCount++ < retryMaxCount) {
            val nextProxy = proxy.clone()
            nextProxy.enqueue(callback)
            Log.e("RetryLog", "retrying ${proxy.request().url} for $retryCount time")
            return true
        }
        retryCount = 0
        return false
    }
}

package com.morziz.network.network

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.morziz.network.config.ClientType
import com.morziz.network.config.ConfigManager
import com.morziz.network.config.NetworkConfig
import com.morziz.network.graphql.ApiClient
import okhttp3.Interceptor

class Network {
    companion object {
        internal var context: Context? = null
        private lateinit var moduleDependency: ModuleDependency

        @JvmOverloads
        fun init(dependency: ModuleDependency) {
            moduleDependency = dependency
            context = dependency.getAppContext()
        }

        fun getBaseUrl(type: String): String {
            return moduleDependency.getBaseUrl(type)
        }

        fun getHeaders(): HashMap<String, String>? {
            return moduleDependency.getHeaders()
        }

        fun getStringFromRes(resId: Int) = context?.getString(resId)

        fun reValidateUser(code: Int) {
            moduleDependency.reValidateUer(code)
        }

        fun getGoogleKey(): String? {
            return moduleDependency.getGoogleKeys()
        }

        fun getExtraInterceptors(): List<Interceptor>? {
            return moduleDependency.getExtraInterceptors()
        }

        fun addNetworkConfig(config: NetworkConfig) {
            ConfigManager.getInstance().addConfig(config)
        }

        fun <S> getClient(clientType: ClientType, clazz: Class<S>, identity: String): S? {
            if (clientType == ClientType.GRAPHQL && clazz == ApolloClient::class.java) {
                val apolloClient =
                    ConfigManager.getInstance().getApolloClient(identity) ?: return null
                return apolloClient as S
            }
            return null
        }
    }
}

interface ModuleDependency {
    fun getBaseUrl(type: String): String
    fun getHeaders(): HashMap<String, String>?
    fun reValidateUer(code: Int)
    fun getGoogleKeys(): String?
    fun getAppContext(): Context
    fun getExtraInterceptors(): List<Interceptor>?
}
package jotaemepereira.com.githubtrending.networking

import jotaemepereira.com.githubtrending.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubRESTApi() {
    companion object {
        fun create(): GithubApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.API_ENDPOINT)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            return retrofit.create(GithubApi::class.java)
        }
    }
}
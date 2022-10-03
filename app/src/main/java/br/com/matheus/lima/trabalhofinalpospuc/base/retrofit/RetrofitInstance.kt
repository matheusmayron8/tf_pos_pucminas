package br.com.matheus.lima.trabalhofinalpospuc.base.retrofit

import br.com.matheus.lima.trabalhofinalpospuc.BuildConfig
import br.com.matheus.lima.trabalhofinalpospuc.data.service.ApiService
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

class RetrofitInstance {
    companion object {
        fun createReportsApiService(): ApiService {
            val okHttpClientBuilder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                okHttpClientBuilder.addInterceptor(OkHttpProfilerInterceptor())
            }

            val retrofit = Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl("https://api-pos-puc-tf.herokuapp.com")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}

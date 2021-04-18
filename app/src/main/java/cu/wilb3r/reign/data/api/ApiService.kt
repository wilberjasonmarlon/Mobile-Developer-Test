package cu.wilb3r.reign.data.api

import cu.wilb3r.reign.data.api.models.HitsResponse
import cu.wilb3r.reign.utils.Constants
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET(Constants.SEARCH_BY_DATE)
    suspend fun getHits(
        @QueryMap filter: Map<String, String>
    ): Response<HitsResponse>

    companion object {
        operator fun invoke(
            httpLoggingInterceptor: HttpLoggingInterceptor
        ): ApiService {
            val okHttpclient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .client(okHttpclient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_URL)
                .build()
                .create(ApiService::class.java)
        }
    }
}
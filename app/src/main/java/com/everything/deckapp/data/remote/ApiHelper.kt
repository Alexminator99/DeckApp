package com.everything.deckapp.data.remote

import com.everything.deckapp.data.models.Card
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {

    private val okHttpClient = OkHttpClient().newBuilder().addInterceptor(getInterceptor()).build()
    lateinit var apiService: ApiService

    init {
        makeService()
    }

    private fun makeService() {
        val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.jsonbin.io/b/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getJsonFromURL(key: String, secretKey: String): Result<Card> {
        return safeApiCall(call = { apiService.getJson(key, secretKey) })
    }

    private fun getInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                Result.Success(myResp.body()!!)
            } else {

                /*
                handle standard error codes
                if (myResp.code() == 403){
                    Log.i("responseCode","Authentication failed")
                }
                .
                .
                .
                 */

                Result.Error(
                    Pair(
                        myResp.code(),
                        myResp.errorBody()?.string() ?: "No hay conexión a internet."
                    )
                )
            }

        } catch (e: Exception) {
            Result.Error(Pair(0, e.message ?: "No hay conexión a internet."))
        }
    }
}
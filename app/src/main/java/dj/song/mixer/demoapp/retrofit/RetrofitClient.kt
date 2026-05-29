package dj.song.mixer.demoapp.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Logs request/response lines, headers, and bodies
    }

    // 2. Add it to the OkHttpClient
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: ApiInterface by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client)
            .build().create(
            ApiInterface::class.java
        )
    }
}
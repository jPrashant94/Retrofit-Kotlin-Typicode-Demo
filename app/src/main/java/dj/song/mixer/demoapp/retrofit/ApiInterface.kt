package dj.song.mixer.demoapp.retrofit

import dj.song.mixer.demoapp.model.PostDTO
import dj.song.mixer.demoapp.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("users")
    suspend fun getAllUsers(): List<UserDTO>

    @GET("users/{id}/posts")
    suspend fun getUserPost(@Path("id") id: Int): List<PostDTO>

    @GET("users/{id}/posts")
    suspend fun getUserPostPaged(
        @Path("id") id: Int,
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ) : List<PostDTO>
}
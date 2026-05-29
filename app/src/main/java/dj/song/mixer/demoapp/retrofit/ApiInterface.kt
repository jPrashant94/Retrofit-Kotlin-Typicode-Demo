package dj.song.mixer.demoapp.retrofit

import dj.song.mixer.demoapp.model.PostDTO
import dj.song.mixer.demoapp.model.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("users")
    suspend fun getAllUsers(): List<UserDTO>

    @GET("users/{id}/posts")
    suspend fun getUserPost(@Path("id") id: Int): List<PostDTO>

}
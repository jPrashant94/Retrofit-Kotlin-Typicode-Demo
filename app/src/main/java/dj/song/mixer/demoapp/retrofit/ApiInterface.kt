package dj.song.mixer.demoapp.retrofit

import dj.song.mixer.demoapp.model.UserDTO
import retrofit2.http.GET

interface ApiInterface {

    @GET("users")
    suspend fun getAllUsers() : List<UserDTO>

}
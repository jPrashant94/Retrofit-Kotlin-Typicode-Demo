package dj.song.mixer.demoapp.repository

import dj.song.mixer.demoapp.model.DomainUser
import dj.song.mixer.demoapp.model.UserDTO
import dj.song.mixer.demoapp.model.toDomainUser
import dj.song.mixer.demoapp.retrofit.ApiInterface
import dj.song.mixer.demoapp.retrofit.RetrofitClient

class UsersListRepository(private val api : ApiInterface) {
    // to get only required field
    suspend fun getAllDomainUsers(): List<DomainUser> {
        return api.getAllUsers().map { dTO ->
            dTO.toDomainUser()
        }
    }

    // get all fields
    suspend fun getAllUsers(): List<UserDTO> {
        return api.getAllUsers()
    }
}
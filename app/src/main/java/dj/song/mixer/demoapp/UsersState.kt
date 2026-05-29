package dj.song.mixer.demoapp

import dj.song.mixer.demoapp.model.DomainUser
import dj.song.mixer.demoapp.model.UserDTO

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val userDTOS : List<DomainUser>) : UsersState()
    data class Error(val message: String) : UsersState()
}
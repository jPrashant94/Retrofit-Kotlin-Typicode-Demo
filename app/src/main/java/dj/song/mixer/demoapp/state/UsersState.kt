package dj.song.mixer.demoapp.state

import dj.song.mixer.demoapp.model.DomainUser

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val userDTOS : List<DomainUser>) : UsersState()
    data class Error(val message: String) : UsersState()
}
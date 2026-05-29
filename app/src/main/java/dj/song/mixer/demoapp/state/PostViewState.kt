package dj.song.mixer.demoapp.state

import dj.song.mixer.demoapp.model.DomainUser
import dj.song.mixer.demoapp.model.PostDTO

sealed class PostViewState {
    object Loading : PostViewState()
    data class Success(val userDTOS : List<PostDTO>) : PostViewState()
    data class Error(val message: String) : PostViewState()
}
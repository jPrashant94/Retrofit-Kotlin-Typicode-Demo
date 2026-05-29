package dj.song.mixer.demoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dj.song.mixer.demoapp.state.UsersState
import dj.song.mixer.demoapp.repository.UsersListRepository
import dj.song.mixer.demoapp.state.PostViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: UsersListRepository, val id: Int) : ViewModel() {

    private val _uiState = MutableStateFlow<PostViewState>(PostViewState.Loading)
    val uiState: StateFlow<PostViewState> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = PostViewState.Loading
            try {
                val users = repository.getUserPost(id)
                _uiState.value = PostViewState.Success(users)
            } catch (e: Exception) {
                _uiState.value = PostViewState.Error(e.message ?: "Loading Error")
            }
        }
    }


}
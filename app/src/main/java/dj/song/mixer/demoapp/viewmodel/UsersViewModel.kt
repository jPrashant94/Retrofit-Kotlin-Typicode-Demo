package dj.song.mixer.demoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dj.song.mixer.demoapp.UsersState
import dj.song.mixer.demoapp.repository.UsersListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UsersListRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersState>(UsersState.Loading)
    val uiState: StateFlow<UsersState> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _uiState.value = UsersState.Loading
            try {
                val users = repository.getAllDomainUsers()
                _uiState.value = UsersState.Success(users)
            } catch (e: Exception) {
                _uiState.value = UsersState.Error(e.message ?: "Loading Error")
            }
        }
    }

}
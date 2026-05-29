package dj.song.mixer.demoapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dj.song.mixer.demoapp.repository.UsersListRepository
import dj.song.mixer.demoapp.viewmodel.UsersViewModel

class UsersViewModelFactory(private val usersListRepository: UsersListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(usersListRepository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
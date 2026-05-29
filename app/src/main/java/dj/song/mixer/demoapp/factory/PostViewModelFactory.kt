package dj.song.mixer.demoapp.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dj.song.mixer.demoapp.repository.UsersListRepository
import dj.song.mixer.demoapp.viewmodel.PostViewModel
import dj.song.mixer.demoapp.viewmodel.UsersViewModel

class PostViewModelFactory(private val usersListRepository: UsersListRepository, private val id:Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PostViewModel::class.java)){
            return PostViewModel(usersListRepository,id) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
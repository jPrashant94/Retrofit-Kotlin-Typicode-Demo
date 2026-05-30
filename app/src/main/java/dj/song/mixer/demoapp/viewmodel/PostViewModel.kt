package dj.song.mixer.demoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dj.song.mixer.demoapp.model.PostDTO
import dj.song.mixer.demoapp.repository.PostsPagingSource
import dj.song.mixer.demoapp.state.UsersState
import dj.song.mixer.demoapp.repository.UsersListRepository
import dj.song.mixer.demoapp.state.PostItemSpinnerState
import dj.song.mixer.demoapp.state.PostViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val repository: UsersListRepository, val id: Int) : ViewModel() {

    private val _uiState = MutableStateFlow<PostViewState>(PostViewState.Loading)
    val uiState: StateFlow<PostViewState> = _uiState


    private var currentPage =1
    private val pageLimit = 5
    private var isLastPage = false
    private var isCurrentlyLoading = false

    private val allLoadedPosts = mutableListOf<PostDTO>()

    val postsFlow: Flow<PagingData<PostDTO>> = Pager(
        config = PagingConfig(
            pageSize = 10, // How many items to load at once
            prefetchDistance = 2, // Fetch next page when 2 items from the bottom
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            PostsPagingSource(repository, id)
        }
    ).flow.cachedIn(viewModelScope)

    init {
       // loadNextPage()
      //  fetchUsers()
    }

    fun loadNextPage() {
        if (isCurrentlyLoading || isLastPage) return
        isCurrentlyLoading = true

        viewModelScope.launch {
            try {
                val newPosts = repository.getUserPostPaged(id, currentPage, pageLimit)

                if (newPosts.isEmpty()) {
                    isLastPage = true
                } else {
                    currentPage++
                    allLoadedPosts.addAll(newPosts)

                    // Always emit a completely new list reference for DiffUtil to recognize changes
//                    _uiState.value = PostViewState.Success(allLoadedPosts.toList())
                }

                val uiList = mutableListOf<PostItemSpinnerState>()
                uiList.addAll(allLoadedPosts.map { PostItemSpinnerState.PostItem(it) })

// If there is still more data on the server, add the spinner at the very bottom!
                if (!isLastPage) {
                    uiList.add(PostItemSpinnerState.LoadingItem)
                }

// Push the new UI list to the StateFlow
                _uiState.value = PostViewState.Success(uiList)
            } catch (e: Exception) {
                // If it's the first page, emit an Error layout state
                if (currentPage == 1) {
                    _uiState.value = PostViewState.Error(e.message ?: "Failed to load posts")
                } else {
                    // Log or handle pagination background error gracefully here
                }
            } finally {
                isCurrentlyLoading = false
            }
        }
    }

    // fetch all post
    private fun fetchUsers() {
//        viewModelScope.launch {
//            _uiState.value = PostViewState.Loading
//            try {
//                val users = repository.getUserPost(id)
//                _uiState.value = PostViewState.Success(users)
//            } catch (e: Exception) {
//                _uiState.value = PostViewState.Error(e.message ?: "Loading Error")
//            }
//        }
    }


}
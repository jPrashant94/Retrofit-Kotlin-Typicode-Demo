package dj.song.mixer.demoapp.state

import dj.song.mixer.demoapp.model.PostDTO

sealed class PostItemSpinnerState {

    data class PostItem(val post : PostDTO ) : PostItemSpinnerState()
    object LoadingItem: PostItemSpinnerState()
}
package dj.song.mixer.demoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dj.song.mixer.demoapp.factory.PostViewModelFactory
import dj.song.mixer.demoapp.factory.UsersViewModelFactory
import dj.song.mixer.demoapp.repository.UsersListRepository
import dj.song.mixer.demoapp.retrofit.RetrofitClient
import dj.song.mixer.demoapp.state.PostViewState
import dj.song.mixer.demoapp.state.UsersState
import dj.song.mixer.demoapp.viewmodel.PostViewModel
import dj.song.mixer.demoapp.viewmodel.UsersViewModel
import kotlinx.coroutines.launch

class PostViewActivity : AppCompatActivity() {

    lateinit var postViewModel: PostViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getIntExtra("USER_ID",-1)
        val api = RetrofitClient.api
        val repository = UsersListRepository(api)
        val factory = PostViewModelFactory(repository,id)

        postViewModel = ViewModelProvider(this@PostViewActivity,factory).get(PostViewModel::class.java)
        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                postViewModel.uiState.collect { state->
                    handleUIState(state)
                }
            }
        }
    }

    private fun handleUIState(state: PostViewState){
        when(state){
            is PostViewState.Loading -> {
                Log.d("HTTP==","Loading")
            }
            is PostViewState.Success -> {
                state.userDTOS.toString()
                Log.d("HTTP==","Success = ${state.userDTOS.toString()}")
            }
            is PostViewState.Error -> {
                Log.d("HTTP==","Error")
            }
        }
    }
}
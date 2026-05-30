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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dj.song.mixer.demoapp.adapter.PostAdapter
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
    lateinit var recPost: RecyclerView
    lateinit var adapterPost: PostAdapter

    private var isNetworkLoading = false
    var id = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        recPost = findViewById<RecyclerView>(R.id.recPosts)
        val layoutManager = LinearLayoutManager(this)
        recPost.layoutManager = layoutManager
        adapterPost = PostAdapter(onPostClicked = { post ->

        })
        recPost.adapter = adapterPost
        recPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Only check if scrolling down
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!isNetworkLoading) {
                        // Trigger fetching when the user reaches the end of the current threshold
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            postViewModel.loadNextPage()
                        }
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        id = intent.getIntExtra("USER_ID", -1)
        val api = RetrofitClient.api
        val repository = UsersListRepository(api)
        val factory = PostViewModelFactory(repository, id)
        postViewModel =
            ViewModelProvider(this@PostViewActivity, factory).get(PostViewModel::class.java)
        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postViewModel.uiState.collect { state ->
                    handleUIState(state)
                }
            }
        }
    }

    private fun handleUIState(state: PostViewState) {
        when (state) {
            is PostViewState.Loading -> {
                Log.d("HTTP==", "Loading")
                isNetworkLoading = true
            }

            is PostViewState.Success -> {
                isNetworkLoading = false
                adapterPost.submitList(state.userDTOS)
                Log.d("HTTP==", "Success = ${state.userDTOS.toString()}")
            }

            is PostViewState.Error -> {
                isNetworkLoading = false
                Log.d("HTTP==", "Error")
            }
        }
    }
}
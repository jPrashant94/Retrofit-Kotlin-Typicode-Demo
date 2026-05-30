package dj.song.mixer.demoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import dj.song.mixer.demoapp.adapter.PostAdapter
import dj.song.mixer.demoapp.adapter.UserAdapter
import dj.song.mixer.demoapp.factory.UsersViewModelFactory
import dj.song.mixer.demoapp.repository.UsersListRepository
import dj.song.mixer.demoapp.retrofit.RetrofitClient
import dj.song.mixer.demoapp.state.UsersState
import dj.song.mixer.demoapp.viewmodel.UsersViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var usersViewModel: UsersViewModel

    lateinit var recUser: RecyclerView
    lateinit var adapterUser: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recUser = findViewById<RecyclerView>(R.id.recUsers)



        adapterUser = UserAdapter { user ->
            val intent = Intent(this@MainActivity, PostViewActivity::class.java)
            intent.putExtra("USER_ID", user.id)
            startActivity(intent)
        }
        recUser.adapter = adapterUser

        val api = RetrofitClient.api
        val repository = UsersListRepository(api)
        val factory = UsersViewModelFactory(repository)

        usersViewModel =
            ViewModelProvider(this@MainActivity, factory).get(UsersViewModel::class.java)
        getData()

    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                usersViewModel.uiState.collect { state ->
                    handleUIState(state)
                }
            }
        }
    }

    private fun handleUIState(state: UsersState) {
        when (state) {
            is UsersState.Loading -> {
                Log.d("HTTP==", "Loading")
            }

            is UsersState.Success -> {
                state.userDTOS.toString()
                Log.d("HTTP==", "Success = ${state.userDTOS.toString()}")

                adapterUser.submitList(state.userDTOS)
            }

            is UsersState.Error -> {
                Log.d("HTTP==", "Error")
            }
        }
    }
}
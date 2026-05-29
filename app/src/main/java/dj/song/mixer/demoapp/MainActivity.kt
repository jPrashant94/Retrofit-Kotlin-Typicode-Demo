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
import dj.song.mixer.demoapp.viewmodel.UsersViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var usersViewModel: UsersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usersViewModel = ViewModelProvider(this@MainActivity).get(UsersViewModel::class.java)
        getData()
    }

    private fun getData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                usersViewModel.uiState.collect { state->
                    handleUIState(state)
                }
            }
        }
    }

    private fun handleUIState(state: UsersState){
        when(state){
            is UsersState.Loading -> {
                Log.d("HTTP==","Loading")
            }
            is UsersState.Success -> {
                state.userDTOS.toString()
                Log.d("HTTP==","Success = ${state.userDTOS.toString()}")
            }
            is UsersState.Error -> {
                Log.d("HTTP==","Error")
            }
        }
    }
}
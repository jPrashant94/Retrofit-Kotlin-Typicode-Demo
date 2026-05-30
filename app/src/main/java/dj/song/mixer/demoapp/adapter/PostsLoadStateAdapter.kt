package dj.song.mixer.demoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import dj.song.mixer.demoapp.R

class PostsLoadStateAdapter(
    private val onRetryClicked: () -> Unit
) : LoadStateAdapter<PostsLoadStateAdapter.LoadStateViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PostsLoadStateAdapter.LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_paged_footer, parent, false)
        return LoadStateViewHolder(view, onRetryClicked)
    }

    override fun onBindViewHolder(
        holder: PostsLoadStateAdapter.LoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
        holder.retryButton.setOnClickListener {
            onRetryClicked()
        }
    }

    class LoadStateViewHolder(
        view: View,
        private val onRetryClicked: () -> Unit
    ) : RecyclerView.ViewHolder(view) {

        val progressBar = view.findViewById<CircularProgressIndicator>(R.id.cirlce)
        val retryButton = view.findViewById<Button>(R.id.btnRetry)
        val errorMsg = view.findViewById<TextView>(R.id.txtError)

        fun bind(loadState: LoadState) {
            // If the state is Loading, show the spinner
            progressBar.isVisible = loadState is LoadState.Loading

            // If the state is Error, show the button and error text
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error

            // If it's an error, extract the message from the Throwable exception
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage ?: "An error occurred"
            }
        }
    }
}
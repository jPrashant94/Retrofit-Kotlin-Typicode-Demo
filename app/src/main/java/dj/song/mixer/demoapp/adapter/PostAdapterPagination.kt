package dj.song.mixer.demoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dj.song.mixer.demoapp.R
import dj.song.mixer.demoapp.model.PostDTO
import dj.song.mixer.demoapp.state.PostItemSpinnerState

class PostAdapterPagination(private val onPostClicked: (PostDTO) -> Unit) :
    ListAdapter<PostItemSpinnerState, RecyclerView.ViewHolder>(PostPaginationDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_POST = 1
        private const val VIEW_TYPE_LOADING = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PostItemSpinnerState.PostItem -> VIEW_TYPE_POST
            is PostItemSpinnerState.LoadingItem -> VIEW_TYPE_LOADING
        }
    }


    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.txtTitle)
        val bodyText: TextView = view.findViewById(R.id.txtBody)

        fun bind(post: PostDTO) {
            titleText.text = post.title
            bodyText.text = post.body
        }

    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_POST -> {
                val view = inflater.inflate(R.layout.adapter_post, parent, false)
                PostViewHolder(view)
            }

            VIEW_TYPE_LOADING -> {
                val view = inflater.inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid ViewType")
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        if (holder is PostViewHolder && post is PostItemSpinnerState.PostItem) {
            holder.bind(post.post)
            holder.itemView.setOnClickListener {
                onPostClicked(post.post)
            }

        }
    }


}

class PostPaginationDiffCallback : DiffUtil.ItemCallback<PostItemSpinnerState>() {
    override fun areItemsTheSame(
        oldItem: PostItemSpinnerState, newItem: PostItemSpinnerState
    ): Boolean {
        return when {
            oldItem is PostItemSpinnerState.PostItem && newItem is PostItemSpinnerState.PostItem -> oldItem.post.id == newItem.post.id
            oldItem is PostItemSpinnerState.LoadingItem && newItem is PostItemSpinnerState.LoadingItem -> true
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: PostItemSpinnerState, newItem: PostItemSpinnerState
    ): Boolean {
        return oldItem == newItem
    }

}
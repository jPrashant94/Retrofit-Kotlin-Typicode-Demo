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

class PostAdapter(private val onPostClicked : (PostDTO) -> Unit) : ListAdapter<PostDTO, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.txtTitle)
        val bodyText: TextView = view.findViewById(R.id.txtBody)

        fun bind(post: PostDTO) {
            titleText.text = post.title
            bodyText.text = post.body
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostAdapter.PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        // getItem(position) is built into ListAdapter!
        val post = getItem(position)
        holder.bind(post)

        holder.itemView.setOnClickListener {
            onPostClicked(post)
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<PostDTO>() {
    override fun areItemsTheSame(
        p0: PostDTO, p1: PostDTO
    ): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(
        p0: PostDTO, p1: PostDTO
    ): Boolean {
        return p0 == p1
    }

}
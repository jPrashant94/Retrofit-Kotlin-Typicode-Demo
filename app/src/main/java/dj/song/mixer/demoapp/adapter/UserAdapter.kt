package dj.song.mixer.demoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dj.song.mixer.demoapp.R
import dj.song.mixer.demoapp.model.DomainUser

class UserAdapter(private val onUserClicked: (DomainUser) -> Unit) :
    ListAdapter<DomainUser, UserAdapter.UserViewHolder>(UserDiffCallBack()) {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.txtTitle)
        val bodyText: TextView = view.findViewById(R.id.txtBody)

        fun bind(post: DomainUser) {
            titleText.text = post.name
            bodyText.text = post.email
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)

        holder.itemView.setOnClickListener {
            onUserClicked(post)
        }
    }
}

class UserDiffCallBack : DiffUtil.ItemCallback<DomainUser>() {
    override fun areItemsTheSame(
        p0: DomainUser,
        p1: DomainUser
    ): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(
        p0: DomainUser,
        p1: DomainUser
    ): Boolean {
        return p0 == p1
    }

}
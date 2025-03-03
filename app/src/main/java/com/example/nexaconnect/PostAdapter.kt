package com.example.nexaconnect

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nexaconnect.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class PostAdapter(options: FirestoreRecyclerOptions<Post>, private val listener: IPostAdapter) : FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(        //FirestoreRecyclerAdapter will take care of everything
    options
) {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val postText: TextView = itemView.findViewById(R.id.postTitle)
        val userText: TextView = itemView.findViewById(R.id.userName)
        val createdAt: TextView = itemView.findViewById(R.id.createdAt)
        val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewholder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
        viewholder.likeButton.setOnClickListener{
            listener.onLikeClicked(snapshots.getSnapshot(viewholder.absoluteAdapterPosition).id)
        }
        return viewholder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {

        try {
            Log.d("PostAdapter", "Binding view holder for position $position")

            holder.postText.text = model.text
            holder.userText.text = model.createdBy.displayName
            Glide.with(holder.userImage.context).load(model.createdBy.imageUrl).circleCrop()        //circleCrop() is used to round the profile photo
                .into(holder.userImage)
            holder.likeCount.text = model.likedBy.size.toString()
            holder.createdAt.text = Utils.getTimeAgo(model.createdAt)

            val auth = Firebase.auth
            val currentUserId = auth.currentUser!!.uid
            val isLiked = model.likedBy.contains(currentUserId)

            if (isLiked) {
                holder.likeButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.likeButton.context,
                        R.drawable.baseline_favorite_liked
                    )
                )
            } else {
                holder.likeButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.likeButton.context,
                        R.drawable.baseline_favorite_border_24
                    )
                )
            }
        }   catch (e: Exception) {
            Log.e("PostAdapter", "Error binding view holder for position $position", e)
        }
    }


}

interface IPostAdapter {
    fun onLikeClicked(postId: String)
}
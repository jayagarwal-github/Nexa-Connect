package com.example.nexaconnect.daos

import com.example.nexaconnect.models.Post
import com.example.nexaconnect.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    private val db = FirebaseFirestore.getInstance()
    val postCollections = db.collection("posts")
    private val auth = Firebase.auth

    @OptIn(DelicateCoroutinesApi::class)
    fun addPost(text : String){
        val currentUserId = auth.currentUser!!.uid          //!! resemble that if auth.currentUser is null then our app got 'crash!' (It basically prevent the app from the illegal activity or operation)
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User:: class.java)!!

            val currentTime = System.currentTimeMillis()
            val post = Post(text, user , currentTime)

            postCollections.document().set(post)
        }

    }

    private fun getPostById(postId : String): Task<DocumentSnapshot> {
        return postCollections.document(postId).get()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateLikes(postId:String){
        GlobalScope.launch {
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            val isLiked = post.likedBy.contains(currentUserId)

            if(isLiked){
                post.likedBy.remove(currentUserId)
            }
            else{
                post.likedBy.add(currentUserId)
            }
            postCollections.document(postId).set(post)
        }
    }
}
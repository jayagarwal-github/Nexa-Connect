package com.example.nexaconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nexaconnect.daos.PostDao
import com.example.nexaconnect.databinding.ActivityMainBinding
import com.example.nexaconnect.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), IPostAdapter {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PostAdapter
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            val createPostActivity = Intent(this, CreatePostActivity::class.java)
            startActivity(createPostActivity)
        }

        setUpRecyclerView()
        Log.d("MainActivity", "onCreate")
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postCollections = postDao.postCollections
        val query = postCollections.orderBy("createdAt", Query.Direction.ASCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
        Log.d("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
        Log.d("MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
        Log.d("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
        Log.d("MainActivity", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        adapter.startListening()
        Log.d("MainActivity", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}
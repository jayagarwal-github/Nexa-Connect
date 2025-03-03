package com.example.nexaconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.nexaconnect.daos.PostDao
import com.example.nexaconnect.databinding.ActivityCreatePostBinding
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class CreatePostActivity : AppCompatActivity() {
    private lateinit var postDao: PostDao
    private lateinit var binding: ActivityCreatePostBinding
    private val TAG = "CreatePostActivity"
    private var isPostInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize PostDao
        postDao = PostDao()

        binding.post.setOnClickListener {
            if (!isPostInProgress) {
                val input = binding.editpost.text.toString().trim()
                if (input.isNotEmpty() && isValidInput(input)) {
                    createPost(input)
                } else {
                    Toast.makeText(this, "Please enter a valid post (minimum 5 characters)", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Log.d(TAG, "onCreate: Activity created")
    }

    private fun createPost(input: String) {
        isPostInProgress = true
        binding.post.isEnabled = false // Disable the post button while posting

        lifecycleScope.launch {
            try {
                postDao.addPost(input)
                runOnUiThread {
                    Toast.makeText(this@CreatePostActivity, "Post created successfully", Toast.LENGTH_SHORT).show()
                    isPostInProgress = false
                    val mainActivity = Intent(this@CreatePostActivity, MainActivity::class.java)
                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(mainActivity)
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@CreatePostActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    isPostInProgress = false
                    binding.post.isEnabled = true // Re-enable the post button on error
                }
            }
        }
    }

    private fun isValidInput(input: String): Boolean {
        return input.length >= 5 // Example: minimum 5 characters
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (isPostInProgress) {
            Toast.makeText(this, "Please wait while post is being created...", Toast.LENGTH_SHORT).show()
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        } else {
            if (binding.editpost.text.toString().isNotEmpty()) {
                // Show confirmation dialog if there's unsaved content
                AlertDialog.Builder(this)
                    .setTitle("Discard Post?")
                    .setMessage("You have unsaved changes. Are you sure you want to discard this post?")
                    .setPositiveButton("Discard") { _, _ ->
                        val mainActivity = Intent(this, MainActivity::class.java)
                        startActivity(mainActivity)
                        finish()
                    }
                    .setNegativeButton("Keep Editing", null)
                    .show()
                 }
            else {
                val mainActivity = Intent(this, MainActivity::class.java)
                startActivity(mainActivity)
                super.onBackPressed()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity is becoming visible")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity is in the foreground and ready for user interaction")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity is partially visible, may be in transition")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity is no longer visible")
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel any ongoing coroutines when the activity is destroyed
        lifecycleScope.coroutineContext.cancelChildren()
        Log.d(TAG, "onDestroy: Activity is being destroyed")
    }
}
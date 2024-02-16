package com.example.nexaconnect.daos

import com.example.nexaconnect.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Document

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    @OptIn(DelicateCoroutinesApi::class)
    fun addUsers(user : User?){
        user?.let{                                                      // If user not null then enter in if statement
            GlobalScope.launch(Dispatchers.IO){         // global scope use to declare the Coroutines which helps to run process on back tread
                usersCollection.document(user.uid).set(it)
            }
        }
    }
    fun getUserById(uId: String): Task<DocumentSnapshot>{           //get() Fun return Task
        return usersCollection.document(uId).get()                  //All the firebase calls are the asynchronous Calls means the data you will not get the data immediately it will take the some delay to take the data to you
    }
}
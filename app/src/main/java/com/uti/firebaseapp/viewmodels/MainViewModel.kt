package com.uti.firebaseapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.uti.firebaseapp.models.Mahasiswa
import com.uti.firebaseapp.sealed.State

class MainViewModel {

    val response: MutableState<State> = mutableStateOf(State.Empty)

    init {
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val tempList = mutableListOf<Mahasiswa>()
        response.value = State.Loading
        FirebaseDatabase.getInstance().getReference("mahasiswa")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children) {
                        val mhs_item = DataSnap.getValue(Mahasiswa::class.java)
                        if (mhs_item != null)
                            tempList.add(mhs_item)
                    }
                    response.value = State.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value = State.Failure(error.message)
                }

            })
    }

}
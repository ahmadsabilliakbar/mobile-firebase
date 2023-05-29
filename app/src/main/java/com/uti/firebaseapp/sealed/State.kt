package com.uti.firebaseapp.sealed

import com.uti.firebaseapp.models.Mahasiswa

sealed class State{
    class Success(val data: MutableList<Mahasiswa>) : State()
    class Failure(val message: String) : State()
    object Loading : State()
    object Empty : State()
}

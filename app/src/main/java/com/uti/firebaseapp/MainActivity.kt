package com.uti.firebaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uti.firebaseapp.models.Mahasiswa
import com.uti.firebaseapp.sealed.State
import com.uti.firebaseapp.ui.theme.FirebaseAppTheme
import com.uti.firebaseapp.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

//    private val viewModel: MainViewModel by viewModels()
//    private val viewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAppTheme {
                Column {
                    TopAppBar(
                        title = {
                            Text(text = "Data Mahasiswa", color = Color.White, fontSize = 18.sp)
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Red)

                    )

                    SetData(MainViewModel())

                }
            }
        }
    }

    @Composable
    fun SetData(viewModel: MainViewModel) {
        when (val result = viewModel.response.value) {
            is State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is State.Success -> {
                ShowLazyList(result.data)
            }
            is State.Failure -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = result.message,
//                        fontSize = MaterialTheme.typography.h5.fontSize,
                    )
                }
            }
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error Fetching data",
//                        fontSize = MaterialTheme.typography.h5.fontSize,
                    )
                }
            }
        }
    }

    @Composable
    fun ShowLazyList(mhs: MutableList<Mahasiswa>) {
        LazyColumn {
            items(mhs) { mahasiswa ->
                CardItem(mahasiswa)
            }
        }
    }

    @Composable
    fun CardItem(mhs: Mahasiswa) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = mhs.npm!!)
                    Text(text = mhs.nama!!)
                    Text(text = mhs.jurusan!!)
                }

            }

        }
    }

}
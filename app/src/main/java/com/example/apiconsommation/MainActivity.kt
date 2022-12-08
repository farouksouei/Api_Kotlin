package com.example.apiconsommation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apiconsommation.ui.theme.APICONSOMMATIONTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    lateinit var album: Deferred<Album>
    lateinit var album1: Album
    lateinit var albumList: Deferred<List<Album>>
    lateinit var albumList1: List<Album>

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APICONSOMMATIONTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // launching a new coroutine
                    var albums: Album? = null
                    val albumApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
                    runBlocking {
                        album = async {
                            albumApi.getALbums().body()!![0]
                        }
                        album1 = album.await()
                        albumList = async {
                            albumApi.getALbums().body()!!
                        }
                        albumList1 = albumList.await()
                        Log.d("Album", album1.title)
                    }
                    Log.d("Album", album1.title)
                    Scaffold(
                        content = {
                            LazyColumn(content = {
                                items(albumList1.size) {
                                    Text(text = "The Api Fetch", style = MaterialTheme.typography.h3)
                                    Text(text = albumList1[it].id.toString())
                                    Text(text = albumList1[it].userId.toString())
                                    Text(text = albumList1[it].title)
                                }
                            })                         //looping through the list and displaying each album
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun DisplayAlbum(Title:String,album: Album?) {
    Column() {
        Text(text = Title, style = MaterialTheme.typography.h3)
        Text(text = album?.userId.toString())
        Text(text = album?.id.toString())
        if (album != null) {
            Text(text = album.title)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    APICONSOMMATIONTheme {
        Greeting("Android")
    }
}
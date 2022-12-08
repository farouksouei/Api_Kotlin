package com.example.apiconsommation
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {
    @GET("/albums/1")
    suspend fun getALbum() : Response<Album>
    @GET("/albums")
    suspend fun getALbums() : Response<List<Album>>
}
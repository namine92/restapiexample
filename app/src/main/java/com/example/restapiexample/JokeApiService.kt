package com.example.restapiexample

import com.example.restapiexample.model.Joke
import retrofit2.Call
import retrofit2.http.GET


interface JokeApiService {

    @GET("jokes/programming/random")
    fun getJoke(): Call<List<Joke>>

}
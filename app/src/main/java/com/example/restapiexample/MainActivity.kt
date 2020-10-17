package com.example.restapiexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.restapiexample.model.Joke
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var jokeService = createJokeApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        joke_button.setOnClickListener {
            getJokeFromTheAPI()
        }

    }


    private fun getJokeFromTheAPI() {
        val joke = jokeService.getJoke()


        joke.enqueue(object : Callback<List<Joke>> {
            override fun onResponse(call: Call<List<Joke>>, response: Response<List<Joke>>) {
                if (response.isSuccessful) {
                    val j = response.body()?.get(0)
                    joke_text.text = "${j?.setup} ${j?.punchline}"
                }

            }

            override fun onFailure(call: Call<List<Joke>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }


    companion object {
        fun createJokeApiService(): JokeApiService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://official-joke-api.appspot.com/")
                .build()

            return retrofit.create(JokeApiService::class.java)
        }
    }
}
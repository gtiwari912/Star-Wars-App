package com.aakansha.myapplication.repo

import com.aakansha.myapplication.repo.model.CharactersApiResponseModel
import com.aakansha.myapplication.repo.model.FilmApiResponseModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://swapi.dev/api/people/?page=2&format=json
//    https://swapi.dev/api/films/?format=json

const val BASE_URL = "https://swapi.dev/"
interface StarWarApiInterface{

    @GET("api/people/")
    fun getCharacters(@Query("page") page: Int, @Query("format") format: String = "json"): Call<CharactersApiResponseModel>


    @GET("api/films/")
    fun getStarWarsFilms(@Query("format") format: String = "json"): Call<FilmApiResponseModel>

}
object StarWarApi {
    val starWarApiInstance: StarWarApiInterface
    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        starWarApiInstance = retrofit.create(StarWarApiInterface::class.java)
    }
}
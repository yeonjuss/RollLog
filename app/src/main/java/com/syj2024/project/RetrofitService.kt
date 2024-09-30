package com.syj2024.project

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @GET("/v2/local/search/keyword.json")

   fun SearchPlace(
    @Header("Authorization") key: String,
    @Query("query") query:String) : Call<ResultSearchKeyWord>


}
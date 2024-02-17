package com.example.assignments.api

import com.example.assignments.models.NResponse
import com.example.assignments.util.Constant.Companion.API_KEY

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NAPI {
    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        countryCode:String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey : String = API_KEY
    ): Response<NResponse>

}
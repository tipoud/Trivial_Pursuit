package com.sandbox.data.api

import com.sandbox.data.response.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface CategoryApi {

    @GET("https://opentdb.com/api_category.php")
    suspend fun getCategoryResponse(): CategoryResponse

}

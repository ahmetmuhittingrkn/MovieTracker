package com.example.filmtakip.retrofit

class ApiUtils {

    companion object{
        val BASE_URL="https://api.themoviedb.org/3/"

        fun getApiService() : ApiService {
            return RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
        }
    }
}
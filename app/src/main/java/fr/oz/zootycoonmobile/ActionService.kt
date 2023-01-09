package fr.oz.zootycoonmobile

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET


interface ActionService {
    @GET("actions")

    fun getActionAll(): Call<List<JsonObject>>
}
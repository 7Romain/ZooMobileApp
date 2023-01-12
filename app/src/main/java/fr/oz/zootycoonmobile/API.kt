package fr.oz.zootycoonmobile

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ActionService {
    @GET("actions")
    fun getActionAll(): Call<List<JsonObject>>

    @FormUrlEncoded
    @POST("actions/creer")
    suspend fun postAction(
        @Field("id_personnel") id_personnel: Int,
        @Field("id_enclos") id_enclos: String,
        @Field("id_espece") id_espece: String,
        @Field("id_animal") id_animal: String,
        @Field("date_prevue") date_prevue: String,
        @Field("observations") observations: String

    ): Response<ActionModel>

}
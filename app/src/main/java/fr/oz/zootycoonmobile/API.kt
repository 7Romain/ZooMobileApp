package fr.oz.zootycoonmobile

import fr.oz.zootycoonmobile.model.ActionModelList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface API {
    @GET("actions")
    fun getActionAll(): Call<List<ActionModelList>>

    @GET("actions/animaux/{id}")
    suspend fun getActionsAnimal(@Path("id") id: String): List<ActionModelList>

    @GET("actions/enclos/{id}")
    suspend fun getActionsEnclos(@Path("id") id: String): List<ActionModelList>

    @GET("actions/especes/{id}")
    suspend fun getActionsEspeces(@Path("id") id: String): List<ActionModelList>

    @GET("actions/zones/{id}")
    suspend fun getActionsZones(@Path("id") id: String): List<ActionModelList>


    @FormUrlEncoded
    @POST("actions/creer")
    suspend fun postAction(
        @Field("idPersonnel") idPersonnel: Int,
        @Field("idEnclos") idEnclos: String,
        @Field("idEspece") idEspece: String,
        @Field("idAnimal") idAnimal: String,
        @Field("datePrevue") datePrevue: String,
        @Field("observations") observations: String

    ): Response<ActionModel>

}
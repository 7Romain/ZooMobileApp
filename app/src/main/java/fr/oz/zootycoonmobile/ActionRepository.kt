package fr.oz.zootycoonmobile

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActionRepository {


    fun initJson(): List<JsonObject> {
        lateinit var actionList: List<JsonObject>
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.12:9003/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val actionService = retrofit.create(ActionService::class.java)

        val result: Call<List<JsonObject>> = actionService.getActionAll()

        result.enqueue(object : Callback<List<JsonObject>> {
            override fun onResponse(
                call: Call<List<JsonObject>>,
                response: Response<List<JsonObject>>
            ) {
                if (response.isSuccessful) {
                    actionList = response.body()!!
//                    holder.actionObservations?.text = response.body()
                }
            }

            override fun onFailure(call: Call<List<JsonObject>>, t: Throwable) {
                println("erreur lors de l'acces à la BDD")

//                Toast.makeText(MainActivity.this, "Erreur", Toast.LENGTH_SHORT).show()
            }

        })
        return actionList
    }


}



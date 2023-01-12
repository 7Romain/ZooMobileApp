package fr.oz.zootycoonmobile.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import fr.oz.zootycoonmobile.API
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R
import fr.oz.zootycoonmobile.Utils.Constants.Companion.BASE_URL
import fr.oz.zootycoonmobile.adapter.ActionAdapter
import fr.oz.zootycoonmobile.model.ActionModelList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment
    (private val context: MainActivity, filtrerPar: String, destination: String) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        val retrofit = Retrofit.Builder()

            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val aPI = retrofit.create(API::class.java)

        val result: Call<List<ActionModelList>> = aPI.getActionAll()

        result.enqueue(object : Callback<List<ActionModelList>> {
            override fun onResponse(
                call: Call<List<ActionModelList>>,
                response: Response<List<ActionModelList>>
            ) {
                if (response.isSuccessful) {
                    val actionList = response.body()!!
                    var actionListFiltre: List<JsonObject>
                    horizontalRecyclerView.adapter = ActionAdapter(context, actionList)
//                    when(destination){
//                        "all" -> actionListFiltre = actionList
//                        "zone" ->actionListFiltre = actionList.filter{ it as JsonObject.enclos.zone.id == filtre}
//
//                        "enclos"->
//                            "animal"->

                }
            }

            override fun onFailure(call: Call<List<ActionModelList>>, t: Throwable) {
                println("erreur lors de l'acces à la BDD")
            }
        })

//      horizontalRecyclerView.adapter = ActionAdapter(context,actionList)


        return view
    }
}
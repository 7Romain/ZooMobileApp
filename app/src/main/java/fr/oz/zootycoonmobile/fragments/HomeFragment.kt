package fr.oz.zootycoonmobile.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import fr.oz.zootycoonmobile.ActionService
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R
import fr.oz.zootycoonmobile.adapter.ActionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment
    (private val context: MainActivity) : Fragment() {
    //    val actionRepository: ActionRepository = ActionRepository()
    lateinit var actionAdapter: ActionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        val retrofit = Retrofit.Builder()
            //Mettre ici l'adresse du server PostGres
            .baseUrl("http://192.168." + "43.12" + ":9003/api/")
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
                    val actionList = response.body()!!
                    horizontalRecyclerView.adapter = ActionAdapter(context, actionList)

                }
            }

            override fun onFailure(call: Call<List<JsonObject>>, t: Throwable) {
                println("erreur lors de l'acces à la BDD")
            }
        })

//      horizontalRecyclerView.adapter = ActionAdapter(context,actionList)


        return view
    }
}
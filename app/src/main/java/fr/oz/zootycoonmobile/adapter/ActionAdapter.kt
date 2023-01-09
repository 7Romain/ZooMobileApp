package fr.oz.zootycoonmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R
import java.util.*
import kotlin.properties.Delegates

class ActionAdapter(
    private val context: MainActivity,
    private val actionList: List<JsonObject>
//    private val layoutId: Int
) : RecyclerView.Adapter<ActionAdapter.ViewHolder>() {
    var nombre by Delegates.notNull<Int>()

    //    lateinit var actionList: List<JsonObject>
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        val actionList: List<JsonObject> = initJson()
        val actionObservations: TextView? = view.findViewById(R.id.obsText)
        val actionDate: TextView? = view.findViewById(R.id.dateText)
        val actionEspece: TextView? = view.findViewById(R.id.especeText)
        val actionAnimal: TextView? = view.findViewById(R.id.animalText)
        val actionEnclos: TextView? = view.findViewById(R.id.enclosText)
        val actionZone: TextView? = view.findViewById(R.id.zoneText)
        val actionAuteur: TextView? = view.findViewById(R.id.auteurText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val actionList: List<JsonObject> = initJson()
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_action, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun miseEnPage(nombre: Int): String =
            if (nombre <= 9) "0" + nombre.toString() else nombre.toString()

        fun capitalize(str: String): String {
            return str.trim().split("\\s+".toRegex())
                .map { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } }
                .joinToString(" ")
        }

        fun removeQuote(string: String): String = string.replace("\"".toRegex(), "")
        fun proprerisation(str: String): String = capitalize(removeQuote(str))

        val currentAction = actionList[holder.getAdapterPosition()]
        val personne = currentAction.get("personnel")?.asJsonObject
        val nom = personne?.get("nom")
        val prenom = personne?.get("prenom")
        val auteur = "$prenom $nom"
        val enclos = currentAction.get("enclos")?.asJsonObject
        val nomEnclos = enclos?.get("nom")
        val nomZone = enclos?.get("zone")?.asJsonObject?.get("nom").toString()
        val espece = currentAction.get("idEspece").toString()
        val animal = currentAction.get("idAnimal").toString()
        val obs = currentAction.get("observations").toString()
        val date = currentAction.get("datePrevue")?.asJsonArray
        val annee = date?.get(0)
        val mois = date?.get(1)?.asInt?.let { miseEnPage(it) }
        val jour = date?.get(2)?.let { miseEnPage(it.asInt) }
        val heure = date?.get(3)?.let { miseEnPage(it.asInt) }
        val min = date?.get(4)?.let { miseEnPage(it.asInt) }


//        Glide.with(context).load(Uri.parse(currentAction.image)).into(holder.actionImage)
        holder.actionAnimal?.text = capitalize(removeQuote(animal))
        holder.actionEspece?.text = capitalize(removeQuote(espece))
        holder.actionEnclos?.text = removeQuote(nomEnclos.toString())
        holder.actionZone?.text = removeQuote(nomZone)
        holder.actionAuteur?.text = capitalize(removeQuote(auteur))
        holder.actionDate?.text = "$jour/$mois/$annee  $heure:$min"
        holder.actionObservations?.text = removeQuote(obs)

//                    holder.actionObservations?.text = response.body()
//                }
//            }
//
//            override fun onFailure(call: Call<List<JsonObject>>, t: Throwable) {
//                println("erreur lors de l'acces à la BDD")
//                Toast.makeText(MainActivity.this, "Erreur", Toast.LENGTH_SHORT).show()
//            }
//        })


    }


    override fun getItemCount(): Int {

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://192.168.43.12:9003/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val actionService = retrofit.create(ActionService::class.java)
//
//        val result: Call<List<JsonObject>> = actionService.getActionAll()
//
//        result.enqueue(object : Callback<List<JsonObject>> {
//            override fun onResponse(
//                call: Call<List<JsonObject>>,
//                response: Response<List<JsonObject>>
//            ) {
//                if (response.isSuccessful) {
//                    val actionList = response.body()!!
//                    nombre = actionList.size
//
//                }
//            }
//
//            override fun onFailure(call: Call<List<JsonObject>>, t: Throwable) {
//                println("erreur lors de l'acces à la BDD")
//                nombre = 0
//
////                Toast.makeText(MainActivity.this, "Erreur", Toast.LENGTH_SHORT).show()
//            }
//
//
//        })
//        return nombre
        return actionList.size
    }
}

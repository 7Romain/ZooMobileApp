package fr.oz.zootycoonmobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R
import fr.oz.zootycoonmobile.model.ActionModelList
import java.util.*
import kotlin.properties.Delegates

class ActionAdapter(
    private val context: MainActivity,
    private val actionList: List<ActionModelList>
) : RecyclerView.Adapter<ActionAdapter.ViewHolder>() {
    var nombre by Delegates.notNull<Int>()


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val actionObservations: TextView? = view.findViewById(R.id.obsText)
        val actionDate: TextView? = view.findViewById(R.id.dateText)
        val actionEspece: TextView? = view.findViewById(R.id.especeText)
        val actionAnimal: TextView? = view.findViewById(R.id.animalText)
        val actionEnclos: TextView? = view.findViewById(R.id.enclosText)
        val actionZone: TextView? = view.findViewById(R.id.zoneText)
        val actionAuteur: TextView? = view.findViewById(R.id.auteurText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_action, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var auteur = " "
        var nomEnclos = " "
        var nomZone = " "
        var espece = " "
        var animal = " "
        var obs = " "
        var annee = " "
        var mois = " "
        var jour = " "
        var heure = " "
        var min = " "
        var moment = " "
        fun miseEnPage(nombre: Int): String =
            if (nombre <= 9) "0" + nombre.toString() else nombre.toString()

        fun capitalize(str: String): String {
            return str.trim().split("\\s+".toRegex())
                .map { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } }
                .joinToString(" ")
        }

        fun removeQuote(string: String): String {
            if (string.isNotBlank())
                return string.replace("\"".toRegex(), "") else return " "
        }

        fun proprerisation(str: String): String = capitalize(removeQuote(str))

        val currentAction = actionList[holder.getAdapterPosition()]
        if (currentAction.enclos == null) {

            holder.actionEnclos?.text = ""
            holder.actionZone?.text = ""

        }

        if (currentAction.personnel != null) {

            val nom = currentAction.personnel.nom
            val prenom = currentAction.personnel.prenom
            auteur = "$prenom $nom"
        }


        if (currentAction.enclos != null) {

            nomEnclos = currentAction.enclos.nom
            nomZone = currentAction.enclos.zone.nom
        }
        if (!currentAction.idEspece.isNullOrBlank())
            espece = currentAction.idEspece

        if (!currentAction.idAnimal.isNullOrBlank()) animal =
            currentAction.idAnimal


        if (!currentAction.observations.isNullOrBlank()) obs =
            currentAction.observations

        if (currentAction.datePrevue.size > 0) {
            annee = currentAction.datePrevue.get(0).toString()

            mois = currentAction.datePrevue.get(1).toString()
            jour = currentAction.datePrevue.get(2).toString()
            heure = currentAction.datePrevue.get(3).toString()
            min = currentAction.datePrevue.get(4).toString()
            moment = "$jour/$mois/$annee  $heure:$min"

        }

        holder.actionAnimal?.text = capitalize(removeQuote(animal))
        holder.actionEspece?.text =
            if (espece.isBlank()) " " else capitalize(removeQuote(espece))
        holder.actionEnclos?.text = removeQuote(nomEnclos)
        holder.actionZone?.text = removeQuote(nomZone)
        holder.actionAuteur?.text = capitalize(removeQuote(auteur))
        holder.actionDate?.text = moment
        holder.actionObservations?.text = removeQuote(obs)


    }


    override fun getItemCount(): Int {
        return actionList.size
    }
}

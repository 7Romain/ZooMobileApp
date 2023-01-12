package fr.oz.zootycoonmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R

class ChoixActionsListFragment(private val context: MainActivity) : Fragment() {

    fun transactionFrag(fragment: Fragment) {
        val supportFragmentManager = parentFragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_choix_actions_list, container, false)
        val parZone = view?.findViewById<CardView>(R.id.parZone)
        val parEnclos = view?.findViewById<CardView>(R.id.parEnclos)
        val parEspece = view?.findViewById<CardView>(R.id.parEspece)
        val parAnimal = view?.findViewById<CardView>(R.id.parAnimal)


        parEnclos?.setOnClickListener {
            transactionFrag(
                ScanActionsList(
                    context,
                    "enclos",

                    )
            )
        }

        parEspece?.setOnClickListener {
            transactionFrag(
                ScanActionsList(
                    context,
                    "espece",

                    )
            )
        }
        parAnimal?.setOnClickListener {
            transactionFrag(
                ScanActionsList(
                    context,
                    "animal",

                    )
            )
        }
        parZone?.setOnClickListener {
            transactionFrag(
                ScanActionsList(
                    context,
                    "zone",

                    )
            )
        }
        return view
    }
}
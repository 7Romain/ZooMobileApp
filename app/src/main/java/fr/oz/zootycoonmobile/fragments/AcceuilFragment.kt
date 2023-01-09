package fr.oz.zootycoonmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R

class AcceuilFragment(private val context: MainActivity) : Fragment() {
    lateinit var actions: CardView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val supportFragmentManager = parentFragmentManager

        val view = inflater.inflate(R.layout.acceuil, container, false)
        val enclos = view.findViewById<CardView>(R.id.cardEnclos)
        val especes = view.findViewById<CardView>(R.id.cardEspeces)
        val animaux = view.findViewById<CardView>(R.id.cardAnimaux)
        val evenements = view.findViewById<CardView>(R.id.cardEvenements)
        val actions = view.findViewById<CardView>(R.id.cardAction)

        actions.setOnClickListener {
//            val intentToActionsActivity: Intent = Intent(context, ActionsActivity::class.java)
//            startActivity(intentToActionsActivity)
            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, LobbyActionsFragment(context))
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return view
    }

//    fun onClick(view: View) {
//        lateinit var i: Intent
//        when (view.getId()) {
//            actions.id -> {
//
//
//            }
//
//        }
//
//    }


}
package fr.oz.zootycoonmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R


class LobbyActionsFragment(private val context: MainActivity) : Fragment() {
    lateinit var actionsList: CardView
    lateinit var actionsCreate: CardView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val supportFragmentManager = parentFragmentManager
        val view = inflater.inflate(R.layout.fragment_lobby_actions, container, false)
        val actionsList = view?.findViewById<CardView>(R.id.parAnimal)
        val actionsCreate = view?.findViewById<CardView>(R.id.parZone)
        actionsList?.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, ChoixActionsListFragment(context))
            transaction.addToBackStack(null)
            transaction.commit()
        }
        actionsCreate?.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, CreerActionFragment(context))
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view


    }


}
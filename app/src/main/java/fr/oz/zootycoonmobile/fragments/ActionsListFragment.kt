package fr.oz.zootycoonmobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import fr.oz.zootycoonmobile.ActionRepository
import fr.oz.zootycoonmobile.MainActivity
import fr.oz.zootycoonmobile.R
import fr.oz.zootycoonmobile.adapter.ActionAdapter
import fr.oz.zootycoonmobile.viewModel.MainViewModel
import fr.oz.zootycoonmobile.viewModelFactory.MainViewModelFactory


class ActionsListFragment(
    private val context: MainActivity,
    filtrerPar: String,
    destinatio: String
) : Fragment() {
    private lateinit var viewModel: MainViewModel
    val destination = destinatio
    val filtre = filtrerPar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_actions_list, container, false)
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        val repository = ActionRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(context, viewModelFactory).get(MainViewModel::class.java)
        when (filtre) {
            "animal" -> viewModel.getActionsAnimaux(destination)
            "enclos" -> viewModel.getActionsEnclos(destination)
            "zone" -> viewModel.getActionsZones(destination)
            "espece" -> viewModel.getActionsEspeces(destination)
        }
        viewModel.myResponse.observe(context, { reponse ->
            horizontalRecyclerView.adapter = ActionAdapter(context, reponse)
        })
        return view
    }
}
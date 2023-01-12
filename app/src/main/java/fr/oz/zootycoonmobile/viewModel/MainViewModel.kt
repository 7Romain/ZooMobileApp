package fr.oz.zootycoonmobile.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.oz.zootycoonmobile.ActionModel
import fr.oz.zootycoonmobile.ActionRepository
import fr.oz.zootycoonmobile.model.ActionModelList
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repositoryAction: ActionRepository) : ViewModel() {

    //    val myResponse: MutableLiveData<ActionModel> = MutableLiveData()
    val myResponse: MutableLiveData<List<ActionModelList>> = MutableLiveData()

    val myPushResponse: MutableLiveData<Response<ActionModel>> = MutableLiveData()

    fun getActionsAnimaux(id: String) {
        viewModelScope.launch {
            val response = repositoryAction.getActionsAnimal(id)
            myResponse.value = response
        }
    }

    fun getActionsEnclos(id: String) {
        viewModelScope.launch {
            val response = repositoryAction.getActionsEnclos(id)
            myResponse.value = response
        }
    }

    fun getActionsEspeces(id: String) {
        viewModelScope.launch {
            val response = repositoryAction.getActionsEspeces(id)
            myResponse.value = response
        }
    }

    fun getActionsZones(id: String) {
        viewModelScope.launch {
            val response = repositoryAction.getActionsZones(id)
            myResponse.value = response
        }
    }


    fun postAction(
        idPersonnel: Int,
        idEnclos: String,
        idEspece: String,
        idAnimal: String,
        datePrevue: String,
        observations: String
    ) {
        viewModelScope.launch {
            val response = repositoryAction.postAction(
                idPersonnel,
                idEnclos,
                idEspece,
                idAnimal,
                datePrevue,
                observations
            )
            myPushResponse.value = response
        }
    }
}
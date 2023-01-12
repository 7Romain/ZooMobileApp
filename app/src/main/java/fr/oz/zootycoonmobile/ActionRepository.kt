package fr.oz.zootycoonmobile

import fr.oz.zootycoonmobile.model.ActionModelList
import retrofit2.Response

class ActionRepository {

    suspend fun getActionsAnimal(id: String): List<ActionModelList> {
        return RetrofitInstance.api.getActionsAnimal(id)
    }

    suspend fun getActionsEnclos(id: String): List<ActionModelList> {
        return RetrofitInstance.api.getActionsEnclos(id)
    }

    suspend fun getActionsEspeces(id: String): List<ActionModelList> {
        return RetrofitInstance.api.getActionsEspeces(id)
    }

    suspend fun getActionsZones(id: String): List<ActionModelList> {
        return RetrofitInstance.api.getActionsZones(id)
    }

    suspend fun postAction(
        idPersonnel: Int,
        idEnclos: String,
        idEspece: String,
        idAnimal: String,
        datePrevue: String,
        observations: String


    ): Response<ActionModel> {
        return RetrofitInstance.api.postAction(
            idPersonnel,
            idEnclos,
            idEspece,
            idAnimal,
            datePrevue,
            observations
        )
    }

}



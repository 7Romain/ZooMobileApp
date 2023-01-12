package fr.oz.zootycoonmobile.model

data class ActionModelList(
    val id: Int,
    val personnel: PersonnelModel,
    val enclos: EnclosModel,
    val idEspece: String,
    val idAnimal: String,
    val datePrevue: List<Int>,
    val observations: String,
)

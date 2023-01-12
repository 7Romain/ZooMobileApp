package fr.oz.zootycoonmobile.model

data class PersonnelModel(
    val id: Int,
    val nom: String,
    val prenom: String,
    val secu: String,
    val naissance: List<Int>,
    val profession: String,
    val username: String,
)
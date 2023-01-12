package fr.oz.zootycoonmobile.model

data class EnclosModel(
    val id: String,
    val nom: String,
    val zone: ZoneModel,
    val superficie: Int,
    val coordonnees: String,

    )
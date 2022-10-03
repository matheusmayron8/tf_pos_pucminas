package br.com.matheus.lima.trabalhofinalpospuc.domain.model


data class NewReport(
    val cpf: String,
    val title: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    var photos: List<String>,
    val userId: String
)

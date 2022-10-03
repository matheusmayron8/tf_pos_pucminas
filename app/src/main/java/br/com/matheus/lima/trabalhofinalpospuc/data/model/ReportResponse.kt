package br.com.matheus.lima.trabalhofinalpospuc.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportResponse(
    val id: String,
    @Json(name = "user_id")
    val userId: String,
    val cpf: String?,
    val title: String,
    val description: String,
    val status: String,
    val latitude: String,
    val longitude: String,
    val photos: List<String>?
)

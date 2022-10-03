package br.com.matheus.lima.trabalhofinalpospuc.domain.model

import android.os.Parcelable
import br.com.matheus.lima.trabalhofinalpospuc.constants.ReportStatusEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyReport(
    val id: String,
    val status: ReportStatusEnum,
    val title: String,
    val description: String,
    val cpf: String,
    val latitude: Double,
    val longitude: Double,
    val photos: List<String>
) : Parcelable

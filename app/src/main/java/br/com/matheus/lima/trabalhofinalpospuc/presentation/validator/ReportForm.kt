package br.com.matheus.lima.trabalhofinalpospuc.presentation.validator

import androidx.lifecycle.MutableLiveData
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.NewReport
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ReportForm {
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val cpf = MutableLiveData<String>()
    val location = MutableLiveData<String>()
    val latitude = MutableLiveData<Double>()
    val longitude = MutableLiveData<Double>()
    val photos = MutableLiveData<Queue<String>>(LinkedList())

    fun validateForm(): Boolean {
        return !title.value.isNullOrBlank() &&
            !description.value.isNullOrEmpty() &&
            !cpf.value.isNullOrEmpty() &&
            !location.value.isNullOrEmpty()
    }

    fun toNewReport(): NewReport? {
        if (!validateForm()) {
            return null
        }

        return NewReport(
            cpf = cpf.value ?: "",
            title = title.value ?: "",
            description = description.value ?: "",
            latitude = latitude.value.toString(),
            longitude = longitude.value.toString(),
            photos = photos.value?.toList() ?: listOf(),
            userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        )
    }
}

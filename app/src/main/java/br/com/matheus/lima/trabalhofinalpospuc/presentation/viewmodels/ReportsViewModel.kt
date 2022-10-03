package br.com.matheus.lima.trabalhofinalpospuc.presentation.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.matheus.lima.trabalhofinalpospuc.base.BaseUseCase
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport
import br.com.matheus.lima.trabalhofinalpospuc.domain.usecase.GetAllReportsUseCase
import br.com.matheus.lima.trabalhofinalpospuc.domain.usecase.GetReportsByUserId
import br.com.matheus.lima.trabalhofinalpospuc.domain.usecase.PostNewReportUseCase
import br.com.matheus.lima.trabalhofinalpospuc.presentation.validator.ReportForm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import java.io.ByteArrayOutputStream

class ReportsViewModel(
    private val mGetAllReportsUseCase: GetAllReportsUseCase,
    private val mGetReportsByUserId: GetReportsByUserId,
    private val mPostNewReportUseCase: PostNewReportUseCase
) : ViewModel() {

    private val mListOfReports = MutableLiveData<List<MyReport>>()
    val listOfReports: LiveData<List<MyReport>>
        get() = mListOfReports

    private val mGenericError = MutableLiveData<String>()
    val genericError: LiveData<String>
        get() = mGenericError

    private val mSuccessSentReport = MutableLiveData<Boolean>()
    val successSentReport: LiveData<Boolean>
        get() = mSuccessSentReport

    val reportForm = ReportForm()

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = mLoading

    fun getAllReports() {
        mGetAllReportsUseCase.produce(BaseUseCase.None)
            .map { list ->
                list.let {
                    mListOfReports.value = list
                }
            }.catch { err ->
                err.message?.let { errorMessage ->
                    mGenericError.value = errorMessage
                }
            }.launchIn(viewModelScope)
    }

    fun getReportsByUserId(userId: String) {
        mLoading.value = true
        mGetReportsByUserId.produce(GetReportsByUserId.Params(userId))
            .map { list ->
                list.let {
                    mListOfReports.value = list
                }
            }.catch { err ->
                err.message?.let { errorMessage ->
                    mGenericError.value = errorMessage
                }
            }.onCompletion {
                mLoading.value = false
            }
            .launchIn(viewModelScope)
    }

    fun uploadFile(bitmap: Bitmap): String? {
        val storage = FirebaseStorage.getInstance()
        val bucket = "gs://pucposprojects.appspot.com"
        val storageRef = storage.getReferenceFromUrl(bucket)

        FirebaseAuth.getInstance().currentUser?.let { user ->
            val userRef = storageRef.child("${user.uid}/${System.currentTimeMillis()}.jpg")
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = userRef.putBytes(data)
            uploadTask.addOnSuccessListener {
            }.addOnFailureListener {
                Log.d("TAG", it.message, it)
            }
            return "$bucket${userRef.path}"
        } ?: return null
    }

    fun addPhotoOnQueue(photoPath: String) {
        val aux = reportForm.photos.value
        if (aux?.size == 3) {
            aux.remove()
        }
        aux?.add(photoPath)
        reportForm.photos.value = aux
    }

    fun postNewReport() {
        val downloadUrlsList = arrayListOf<String>()
        reportForm.photos.value?.forEach { photoPath ->
            val bitmap = BitmapFactory.decodeFile(photoPath)
            uploadFile(bitmap)?.let {
                downloadUrlsList.add(it)
            }
        }
        val newReport = reportForm.toNewReport()
        newReport?.photos = downloadUrlsList
        newReport?.let {
            mPostNewReportUseCase.produce(PostNewReportUseCase.Params(it))
                .map {
                    mSuccessSentReport.value = true
                }.catch { err ->
                    Log.d("TAG", err.message, err)
                    mGenericError.value = "Erro ao enviar solicitação. Por favor, tente novamente."
                }.launchIn(viewModelScope)
        }
    }
}

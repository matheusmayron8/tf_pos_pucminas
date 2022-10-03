package br.com.matheus.lima.trabalhofinalpospuc.domain.repository

import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.NewReport
import retrofit2.Response

interface ReportsRepository {

    suspend fun getAllReports(): List<MyReport>

    suspend fun getReportsByUserId(userId: String): List<MyReport>

    suspend fun postNewReport(newReport: NewReport): Response<Unit>
}

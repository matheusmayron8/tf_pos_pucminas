package br.com.matheus.lima.trabalhofinalpospuc.data.service

import br.com.matheus.lima.trabalhofinalpospuc.data.model.NewReportRequest
import br.com.matheus.lima.trabalhofinalpospuc.data.model.ReportResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/reports")
    suspend fun getAllReports(): List<ReportResponse>

    @GET("/reports/{user_id}")
    suspend fun getReportsByUserId(@Path("user_id") userId: String): List<ReportResponse>

    @POST("/reports")
    suspend fun postNewReport(@Body newReport: NewReportRequest): Response<Unit>
}

package br.com.matheus.lima.trabalhofinalpospuc.data.repository

import br.com.matheus.lima.trabalhofinalpospuc.data.mapper.NewReportMapper
import br.com.matheus.lima.trabalhofinalpospuc.data.mapper.ReportResponseMapper
import br.com.matheus.lima.trabalhofinalpospuc.data.service.ApiService
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.NewReport
import br.com.matheus.lima.trabalhofinalpospuc.domain.repository.ReportsRepository
import retrofit2.Response

class ReportsRepositoryImpl(
    private val mReportsService: ApiService
) : ReportsRepository {

    override suspend fun getAllReports(): List<MyReport> {
        return ReportResponseMapper.transformToList(
            mReportsService.getAllReports()
        )
    }

    override suspend fun getReportsByUserId(userId: String): List<MyReport> {
        return ReportResponseMapper.transformToList(
            mReportsService.getReportsByUserId(userId)
        )
    }

    override suspend fun postNewReport(newReport: NewReport): Response<Unit> {
        return mReportsService.postNewReport(
            NewReportMapper.transformTo(newReport)
        )
    }
}

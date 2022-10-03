package br.com.matheus.lima.trabalhofinalpospuc.domain.usecase

import br.com.matheus.lima.trabalhofinalpospuc.base.BaseUseCase
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.NewReport
import br.com.matheus.lima.trabalhofinalpospuc.domain.repository.ReportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class PostNewReportUseCase(
    private val mReportsRepository: ReportsRepository
) : BaseUseCase<Response<Unit>, PostNewReportUseCase.Params>() {

    data class Params(
        val newReport: NewReport
    )

    override fun doWork(params: Params): Flow<Response<Unit>?> = flow {
        emit(
            mReportsRepository.postNewReport(params.newReport)
        )
    }
}
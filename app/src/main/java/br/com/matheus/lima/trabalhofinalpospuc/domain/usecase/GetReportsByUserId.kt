package br.com.matheus.lima.trabalhofinalpospuc.domain.usecase

import br.com.matheus.lima.trabalhofinalpospuc.base.BaseUseCase
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport
import br.com.matheus.lima.trabalhofinalpospuc.domain.repository.ReportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetReportsByUserId(
    private val mReportsRepository: ReportsRepository
) : BaseUseCase<List<MyReport>, GetReportsByUserId.Params>() {

    data class Params(
        val userId: String
    )

    override fun doWork(params: Params): Flow<List<MyReport>?> = flow {
        emit(mReportsRepository.getReportsByUserId(params.userId))
    }
}
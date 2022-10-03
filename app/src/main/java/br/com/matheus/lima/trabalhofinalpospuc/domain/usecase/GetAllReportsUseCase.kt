package br.com.matheus.lima.trabalhofinalpospuc.domain.usecase

import br.com.matheus.lima.trabalhofinalpospuc.base.BaseUseCase
import br.com.matheus.lima.trabalhofinalpospuc.domain.model.MyReport
import br.com.matheus.lima.trabalhofinalpospuc.domain.repository.ReportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllReportsUseCase(
    private val mReportsRepository: ReportsRepository
) : BaseUseCase<List<MyReport>, BaseUseCase.None>() {
    override fun doWork(params: None): Flow<List<MyReport>?> = flow {
        emit(mReportsRepository.getAllReports())
    }
}

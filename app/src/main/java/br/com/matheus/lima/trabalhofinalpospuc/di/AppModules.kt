package br.com.matheus.lima.trabalhofinalpospuc.di

import br.com.matheus.lima.trabalhofinalpospuc.base.retrofit.RetrofitInstance
import br.com.matheus.lima.trabalhofinalpospuc.data.repository.ReportsRepositoryImpl
import br.com.matheus.lima.trabalhofinalpospuc.domain.repository.ReportsRepository
import br.com.matheus.lima.trabalhofinalpospuc.domain.usecase.GetAllReportsUseCase
import br.com.matheus.lima.trabalhofinalpospuc.domain.usecase.GetReportsByUserId
import br.com.matheus.lima.trabalhofinalpospuc.domain.usecase.PostNewReportUseCase
import br.com.matheus.lima.trabalhofinalpospuc.presentation.viewmodels.ReportsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { RetrofitInstance.createReportsApiService() }
}

val repositoryModule = module {
    single<ReportsRepository> { ReportsRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { GetAllReportsUseCase(get()) }
    factory { GetReportsByUserId(get()) }
    factory { PostNewReportUseCase(get()) }
}

val viewModelModule = module {
    viewModel { ReportsViewModel(get(), get(), get()) }
}

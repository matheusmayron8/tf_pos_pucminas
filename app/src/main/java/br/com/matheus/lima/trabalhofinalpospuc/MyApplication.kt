package br.com.matheus.lima.trabalhofinalpospuc

import android.app.Application
import br.com.matheus.lima.trabalhofinalpospuc.di.networkModule
import br.com.matheus.lima.trabalhofinalpospuc.di.repositoryModule
import br.com.matheus.lima.trabalhofinalpospuc.di.useCaseModule
import br.com.matheus.lima.trabalhofinalpospuc.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}

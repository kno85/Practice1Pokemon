package com.aca.acoders

import android.app.Application
import com.aca.acoders.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokemonApplication)
            modules(appModule)
        }
    }
}

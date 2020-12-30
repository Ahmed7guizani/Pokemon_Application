package com.example.pokemon_application.injection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApplication : Application(){
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@PokemonApplication)
            modules(presentationModule, domainModule, dataModule)
        }
    }
}
package com.example.pokemon_application.presentation.register

sealed class RegisterStatus

data class RegisterError(val email:String) : RegisterStatus()
object RegisterSuccess : RegisterStatus()
package com.example.pokemon_application.presentation.main

sealed class LoginStatus

data class LoginSuccess(val email:String, val password: String) : LoginStatus()
object LoginError : LoginStatus()
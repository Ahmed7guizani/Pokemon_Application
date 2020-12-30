package com.example.pokemon_application.domain.usecase

import com.example.pokemon_application.data.repository.UserRepository
import com.example.pokemon_application.domain.entity.User

class CreateAccountUseCase (
    private val userRepository : UserRepository

){
    suspend fun invoke(user: User) {
        userRepository.createAccount(user)
    }
}
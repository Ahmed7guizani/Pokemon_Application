package com.example.pokemon_application.domain.usecase

import com.example.pokemon_application.data.repository.UserRepository
import com.example.pokemon_application.domain.entity.User

class GetAccountUseCase (
    private val userRepository : UserRepository
    ){
        suspend fun invoke(email: String, password: String): User? {
            return userRepository.getAccount(email, password)
        }
}
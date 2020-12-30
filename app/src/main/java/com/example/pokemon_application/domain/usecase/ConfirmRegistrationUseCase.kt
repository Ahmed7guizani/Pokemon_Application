package com.example.pokemon_application.domain.usecase

import com.example.pokemon_application.data.repository.UserRepository
import com.example.pokemon_application.domain.entity.User

class ConfirmRegistrationUseCase (
    private val userRepository : UserRepository
    ) {
        suspend fun invoke(newEmail: String): User? {
            return userRepository.confirmRegistration(newEmail)
    }
}
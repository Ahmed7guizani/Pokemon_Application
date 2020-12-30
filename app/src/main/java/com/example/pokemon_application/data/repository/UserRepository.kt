package com.example.pokemon_application.data.repository

import com.example.pokemon_application.data.local.DatabaseDao
import com.example.pokemon_application.data.local.models.toData
import com.example.pokemon_application.data.local.models.toEntity
import com.example.pokemon_application.domain.entity.User

class UserRepository(
    private val databaseDao: DatabaseDao
) {
    fun createAccount(user: User){
        databaseDao.insert(user.toData())
    }

    fun deleteAccount(user: User){
        databaseDao.delete(user.toData())
    }

    fun getAccount(email: String, password: String): User?{
        val emailLocal = databaseDao.findByName(email, password)
        return emailLocal?.toEntity()
    }

    fun confirmRegistration(newEmail: String): User?{
        val newEmailLocal = databaseDao.findByEmail(newEmail)
        return newEmailLocal?.toEntity()
    }

}
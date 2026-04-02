package com.example.keyraceapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class UserEntity(
    val name: String,
    @PrimaryKey val id: UUID? = null
)
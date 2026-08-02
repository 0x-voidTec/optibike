package com.optibike.fitting.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.optibike.fitting.domain.model.Bike
import com.optibike.fitting.domain.model.BikeType
import java.time.LocalDateTime

/**
 * Bike Entity for Room Database
 */
@Entity(tableName = "bikes")
data class BikeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: BikeType,
    val frameSize: Double? = null,
    val notes: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)

fun BikeEntity.toDomain(): Bike {
    return Bike(
        id = id,
        name = name,
        type = type,
        frameSize = frameSize,
        notes = notes,
        createdAt = createdAt
    )
}

fun Bike.toEntity(): BikeEntity {
    return BikeEntity(
        id = id,
        name = name,
        type = type,
        frameSize = frameSize,
        notes = notes,
        createdAt = createdAt
    )
}

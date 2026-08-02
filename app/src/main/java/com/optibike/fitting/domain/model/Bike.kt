package com.optibike.fitting.domain.model

import java.time.LocalDateTime

/**
 * Bike Profile Model
 * Represents a user's bike profile
 */
data class Bike(
    val id: Long = 0,
    val name: String,
    val type: BikeType,
    val frameSize: Double? = null,
    val notes: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)

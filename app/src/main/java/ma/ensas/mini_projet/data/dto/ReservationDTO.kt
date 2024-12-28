package ma.ensas.mini_projet.data.dto

import ma.ensas.mini_projet.utils.enumerations.ReservationStatus
import java.util.Date

data class ReservationDTO(
    val id: Int,
    val reservedAt: Date,
    val status: ReservationStatus,
    val userId: Int,
    val productName: String
)

package ma.ensas.mini_projet.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ma.ensas.mini_projet.utils.enumerations.ReservationStatus
import java.util.Date

@Entity(
    tableName = "reservations",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val reservedAt: Date,
    val status: ReservationStatus,
    // foreign keys
    val userId: Int,
    val productId: Int
)
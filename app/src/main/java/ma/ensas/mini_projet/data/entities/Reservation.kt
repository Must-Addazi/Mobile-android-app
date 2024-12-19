package ma.ensas.mini_projet.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ma.ensas.mini_projet.utils.enumerations.ReservationStatus
import java.util.Date

@Entity(
    tableName = "reservations",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"]),
        ForeignKey(entity = Product::class, parentColumns = ["productId"], childColumns = ["productId"])
    ],
    indices = [Index(value = ["userId"]), Index(value = ["productId"])]
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
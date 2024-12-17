package ma.ensas.mini_projet.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.data.entities.User

data class UserWithReservations(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val reservations: List<Reservation>
)
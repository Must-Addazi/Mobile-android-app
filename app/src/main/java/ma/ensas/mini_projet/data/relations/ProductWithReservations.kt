package ma.ensas.mini_projet.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation

data class ProductWithReservations(
    @Embedded
    val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    )
    val reservations: List<Reservation>
)

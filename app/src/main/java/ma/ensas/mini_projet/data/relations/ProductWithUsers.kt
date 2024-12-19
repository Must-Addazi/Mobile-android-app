package ma.ensas.mini_projet.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.data.entities.User

data class ProductWithUsers(
    @Embedded
    val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
        associateBy = Junction(Reservation::class, parentColumn = "productId", entityColumn = "userId")
    )
    val users: List<User>
)

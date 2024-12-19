package ma.ensas.mini_projet.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.data.entities.User

data class UserWithProducts(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(Reservation::class, parentColumn = "userId", entityColumn = "productId")
    )
    val products: List<Product>
)

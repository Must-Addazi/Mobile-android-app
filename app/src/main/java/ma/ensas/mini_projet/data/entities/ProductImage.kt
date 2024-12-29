package ma.ensas.mini_projet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "product_images",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val imageData: ByteArray
)

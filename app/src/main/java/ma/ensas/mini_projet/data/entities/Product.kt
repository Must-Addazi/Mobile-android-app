package ma.ensas.mini_projet.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.utils.enumerations.ProductTypes
import java.util.Date

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Int,
    val name: String,
    val description: String,
    val detailedDescription: String,
    val price: Double,
    val stock: Int,
    val expirationDate: Date,
    val type: ProductTypes,
    val imageResId: Int = R.drawable.default_prod_img
)

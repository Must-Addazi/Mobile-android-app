package ma.ensas.mini_projet.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ma.ensas.mini_projet.utils.enumerations.ProductTypes
import java.util.Date

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val detailedDescription: String,
    val price: Double,
    val stock: Int,
    val expirationDate: Date,
    val type: ProductTypes,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val productImage: ByteArray?,
)

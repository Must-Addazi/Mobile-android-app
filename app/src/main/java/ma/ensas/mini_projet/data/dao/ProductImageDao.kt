package ma.ensas.mini_projet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ma.ensas.mini_projet.data.entities.ProductImage

@Dao
interface ProductImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductImage(productImage: ProductImage)

    @Query("SELECT * FROM product_images WHERE productId = :productId LIMIT 1")
    suspend fun getImagesByProductId(productId: Int): ProductImage

    @Query("DELETE FROM product_images WHERE id = :id")
    suspend fun deleteProductImageById(id: Int)

    @Query("SELECT * FROM product_images")
    suspend fun getAllImages(): List<ProductImage>
}
package ma.ensas.mini_projet.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ma.ensas.mini_projet.data.entities.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertProduct(prod: Product): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertProducts(products: List<Product>)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Transaction
    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>
    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()
    @Query("SELECT * FROM products WHERE productId = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): Product?
//    @Query("SELECT * FROM products")
//    fun getAllProductsWithUsers() : Flow<List<ProductWithUsers>>

}
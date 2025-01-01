package ma.ensas.mini_projet.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.utils.enumerations.ProductTypes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val _products = MutableLiveData<List<Product>>()
    private val _filteredProducts = MutableLiveData<List<Product>>()

    val products: LiveData<List<Product>> get() = _products
    val filteredProducts: LiveData<List<Product>> get() = _filteredProducts

    private val productDao: ProductDao = MediMarketDatabase.getDatabase(app).productDao()

    init {
     //   removeProducts()
      //  insertRandomProducts()
        loadProductsFromDatabase()
    }

    private fun removeProducts() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                Log.i("mustapha","delete product")
                productDao.deleteAllProducts()
            } catch (ex: Exception) {
                Log.i("mustapha", "Failed To Delete Products")
            }
        }
    }

    private fun loadProductsFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i("mustapha","load product")
                val productsList = productDao.getAllProducts()
                _products.postValue(productsList)
                _filteredProducts.postValue(productsList)
            }
            catch (ex: Exception) {
                Log.i("mustapha", "Failed to load products ${ex.message}")
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun insertRandomProducts() {
        Log.i("mustapha","insert products")

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        val expirationDateStr = dateFormat.format(Date())

        viewModelScope.launch(Dispatchers.IO) {
            val productsList = mutableListOf<Product>()
            for (i in 1..10) {
                val product = Product(
                    productId = 0,
                    name = "Produit $i",
                    description = "Description du produit $i",
                    detailedDescription = """
                        Ce produit est conçu pour répondre à des besoins spécifiques en matière de santé et de bien-être. 
                        Il est fabriqué à partir de matières premières de haute qualité, garantissant son efficacité et sa durabilité.
                        Chaque unité est soi
                        """.trimIndent(),
                    price = String.format(Locale.US, "%.3f", Random.nextDouble(10.0, 200.0)).toDouble(),
                    stock = Random.nextInt(1, 100),
                    expirationDate = dateFormat.parse(expirationDateStr) ?: Date(),
                    type = if (i % 2 == 0) ProductTypes.MEDICAMENT else ProductTypes.VITAMIN,
                    imageResId = R.drawable.default_prod_img
                )

                val insertedId: Long = productDao.insertProduct(product)
                val productWithId = product.copy(productId = insertedId.toInt())
                productsList.add(productWithId)
            }
            _products.postValue(productsList)
        }

    }

    fun searchProducts(query: String) {
        val currentProducts = _products.value ?: emptyList()
        if (query.isBlank()) {
            _filteredProducts.postValue(currentProducts)
        } else {
            val filteredList =
                currentProducts.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            _filteredProducts.postValue(filteredList)
        }
    }


}
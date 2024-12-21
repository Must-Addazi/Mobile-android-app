package ma.ensas.mini_projet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.utils.enumerations.ProductTypes
import java.util.Date
import kotlin.random.Random

class HomeViewModel(private val productDao: ProductDao) : ViewModel() {

        private val _products = MutableLiveData<List<Product>>()
        val products: LiveData<List<Product>> get() = _products

        init {
            insertRandomProducts()
        }

    private fun insertRandomProducts() {
        GlobalScope.launch(Dispatchers.IO) {
            val productsList = mutableListOf<Product>()
            for (i in 1..15) {
                val product = Product(
                    productId = 0,
                    name = "Produit $i",
                    description = "Description du produit $i",
                    detailedDescription = "Détails supplémentaires pour le produit $i",
                    price = Random.nextDouble(10.0, 200.0),
                    stock = Random.nextInt(1, 100),
                    expirationDate = Date(),
                    type = if (i % 2 == 0) ProductTypes.MEDICAMENT else ProductTypes.VITAMIN,
                    productImage = null
                )
                productsList.add(product)
            }
            productDao.insertProducts(productsList)

            _products.postValue(productsList)
        }
    }

}


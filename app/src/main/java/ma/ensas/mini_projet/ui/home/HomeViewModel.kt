package ma.ensas.mini_projet.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.utils.enumerations.ProductTypes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val productDao: ProductDao = MediMarketDatabase.getDatabase(app).productDao()
        private val _products = MutableLiveData<List<Product>>()
        val products: LiveData<List<Product>> get() = _products

        init {
            Log.i("mustapha","insertion de produits")
         //   deleteAllProducts()
          //  insertRandomProducts()
            loadProductsFromDatabase()
        }
    private fun loadProductsFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {

            val productsList = productDao.getAllProducts()

            _products.postValue(productsList)
        }
    }
    private fun deleteAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.deleteAllProducts()

            _products.postValue(emptyList())
        }
    }

    private fun insertRandomProducts() {
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
    Chaque unité est soigneusement contrôlée pour assurer sa conformité aux normes de sécurité les plus strictes. 
    Son utilisation régulière permet de maintenir un équilibre optimal et d'améliorer les performances quotidiennes. 
    De plus, le produit est adapté à divers types de consommateurs, qu'ils soient jeunes ou adultes. 
    Il est recommandé de suivre les instructions d'utilisation pour obtenir les meilleurs résultats. 
    En raison de ses propriétés uniques, ce produit peut être utilisé dans différents contextes de santé.
    Il est également compatible avec d'autres traitements, mais il est conseillé de consulter un professionnel de santé.
    Ce produit ne présente aucun effet secondaire notable lorsqu'il est utilisé correctement. 
    Pour toute question supplémentaire, n'hésitez pas à contacter notre service client ou à consulter le mode d'emploi.
""".trimIndent(),
                    price = String.format(Locale.US, "%.3f", Random.nextDouble(10.0, 200.0)).toDouble(),
                    stock = Random.nextInt(1, 100),
                    expirationDate = dateFormat.parse(expirationDateStr) ?: Date(),
                    type = if (i % 2 == 0) ProductTypes.MEDICAMENT else ProductTypes.VITAMIN,
                    productImage = null
                )

                val insertedId: Long = productDao.insertProduct(product)
                val productWithId = product.copy(productId = insertedId.toInt())
                productsList.add(productWithId)
            }
            _products.postValue(productsList)
        }

    }
}


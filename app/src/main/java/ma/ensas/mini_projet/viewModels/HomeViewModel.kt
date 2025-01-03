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
import ma.ensas.mini_projet.data.dao.ReservationDao
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
    private val reservationDao: ReservationDao = MediMarketDatabase.getDatabase(app).reservationDao()


    init {
        insertRandomProducts()
        loadProductsFromDatabase()
    }


    private fun loadProductsFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val productsList = productDao.getAllProducts()
                _products.postValue(productsList)
                _filteredProducts.postValue(productsList)
            }
            catch (ex: Exception) {
                Log.i("loadProducts", "Failed to load products ${ex.message}")
            }
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

    @SuppressLint("SimpleDateFormat")
    private fun insertRandomProducts() {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        val expirationDate = dateFormat.parse("31/12/2025") ?: Date()
        viewModelScope.launch(Dispatchers.IO) {
            val products = productDao.getAllProducts()
            if(products.isNotEmpty()) return@launch
            val productsList = mutableListOf<Product>()
                val product1 = Product(
                    productId = 0,
                    name = "Paracetamol",
                    description = "Paracetamol 500mg Capsules",
                    detailedDescription = """
                      Ce paquet de 32 produits en pharmacie uniquement contient des gélules de paracétamol qui soulagent la douleur dans des affections telles que les maux de tête, les maux de dents et les maux de gorge. Réduit la température et soulage les symptômes du rhume et de la grippe.

                      Il s'agit d'un médicament générique. L'emballage, les marques et les saveurs peuvent différer de ceux affichés. Les images sont uniquement à des fins d’illustration.
                        """.trimIndent(),
                    price = String.format(Locale.US, "%.3f", 21.09).toDouble(),
                    stock = Random.nextInt(1, 10),
                    expirationDate = expirationDate,
                    type =  ProductTypes.MEDICAMENT ,
                    imageResId = R.drawable.paracetamol
                )

                val insertedId1: Long = productDao.insertProduct(product1)
                val productWithId1 = product1.copy(productId = insertedId1.toInt())
                productsList.add(productWithId1)
                val product6 = Product(
                    productId = 0,
                    name = "Vitamin C",
                    description = "Valupak Chewable Vitamin C 80mg Tablets x 60",
                    detailedDescription = """
                    Les comprimés à croquer Valupak de vitamine C 80 mg renforcent puissamment votre système immunitaire tout en favorisant la santé de la peau et les niveaux d'énergie.
    
                    Parfait pour maintenir le bien-être général.
                         """.trimIndent(),

                    price = String.format(Locale.US, "%.3f", 13.02).toDouble(),
                    stock = Random.nextInt(1, 10),
                    expirationDate = expirationDate,
                    type =  ProductTypes.VITAMIN ,
                    imageResId = R.drawable.c
                )

            val insertedId6: Long = productDao.insertProduct(product6)
            val productWithId6 = product6.copy(productId = insertedId6.toInt())
            productsList.add(productWithId6)
            val product2 = Product(
                productId = 0,
                name = "Ibuprofen",
                description = "Ibuprofen 10% Gel 50g",
                detailedDescription = """
                     Le gel d'ibuprofène est destiné au soulagement de la douleur dans les conditions arthritiques non graves. Fournit un soulagement efficace de la douleur lorsque vous en avez le plus besoin.

                     Il s'agit d'un médicament générique. L'emballage, les marques et les saveurs peuvent varier par rapport à ceux affichés. Les images sont uniquement à des fins d’illustration.
                            """.trimIndent(),
                price = String.format(Locale.US, "%.3f", 45.40).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.MEDICAMENT ,
                imageResId = R.drawable.ibuprofen
            )

            val insertedId2: Long = productDao.insertProduct(product2)
            val productWithId2 = product2.copy(productId = insertedId2.toInt())
            productsList.add(productWithId2)

            val product7 = Product(
                productId = 0,
                name = "Vitamin D3",
                description = "Valupak Vitamin D3 1000iu Tablets x 60",
                detailedDescription = """
                Les comprimés Valupak Vitamine D3 1000 UI soutiennent la santé des os, la fonction immunitaire et le bien-être général en favorisant l'absorption du calcium et du phosphore.

                Idéals pour les personnes dont l'exposition au soleil ou l'apport alimentaire sont limités, ces comprimés aident à maintenir des os et des dents sains tout en soutenant la fonction musculaire et un système immunitaire équilibré.
                     """.trimIndent(),
                price = String.format(Locale.US, "%.3f", 13.02).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.VITAMIN ,
                imageResId = R.drawable.d3
            )

            val insertedId7: Long = productDao.insertProduct(product7)
            val productWithId7 = product7.copy(productId = insertedId7.toInt())
            productsList.add(productWithId7)

            val product3 = Product(
                productId = 0,
                name = "Aspirin",
                description = "Aspirin Dispersible Tablets 75mg x 100",
                detailedDescription = """
                    Les comprimés dispersibles d'aspirine sont recommandés par votre médecin si vous présentez un risque élevé de crise cardiaque ou d'accident vasculaire cérébral. Ils peuvent être avalés entiers ou placés dans l'eau pour qu'ils se dissolvent.

                    Il s'agit d'un médicament générique. L'emballage, les marques et les saveurs peuvent différer de ceux affichés. Les images sont uniquement à des fins d’illustration.
                               """.trimIndent(),
                price = String.format(Locale.US, "%.3f", 20.08).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.MEDICAMENT ,
                imageResId = R.drawable.aspirin
            )

            val insertedId3: Long = productDao.insertProduct(product3)
            val productWithId3 = product3.copy(productId = insertedId3.toInt())
            productsList.add(productWithId3)

            val product8 = Product(
                productId = 0,
                name = "Calcium",
                description = "Valupak Calcium And Vitamin D 400mg Tablets x 30",
                detailedDescription = """
                Les comprimés de calcium et de vitamine D Valupak 400 mg sont essentiels au maintien de os et de dents solides.

                Ces comprimés combinent du calcium, crucial pour la densité osseuse, avec de la vitamine D pour améliorer l'absorption du calcium et soutenir un système immunitaire sain.

                Idéal pour ceux qui cherchent à soutenir la santé des os et le bien-être général dans un supplément quotidien pratique.
                                    """.trimIndent(),
                price = String.format(Locale.US, "%.3f", 11.00).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.VITAMIN ,
                imageResId = R.drawable.calcuim
            )

            val insertedId8: Long = productDao.insertProduct(product8)
            val productWithId8 = product8.copy(productId = insertedId8.toInt())
            productsList.add(productWithId8)

            val product4 = Product(
                productId = 0,
                name = "Amoxicilline",
                description = "Amoxicilline Sandoz cpr pell 500 mg 20 pce",
                detailedDescription = """
                  Il s'agit d'un médicament que nous ne pouvons pas envoyer par la poste. Après une consultation individuelle dans l'une de nos pharmacies, ce médicament peut être délivré sans ordonnance sous certaines conditions.
                      """.trimIndent(),
                price = String.format(Locale.US, "%.3f", 32.30).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.MEDICAMENT ,
                imageResId = R.drawable.amoxicilline
            )

            val insertedId4: Long = productDao.insertProduct(product4)
            val productWithId4 = product4.copy(productId = insertedId4.toInt())
            productsList.add(productWithId4)

            val product9 = Product(
                productId = 0,
                name = "Zinc",
                description = "Zinc Oxide Tape 2.5cm x 10m",
                detailedDescription = """
               Le ruban d'oxyde de zinc est utilisé par les athlètes pour prévenir les blessures, protéger les plaies et accélérer le temps de guérison.
    
               Il est principalement appliqué aux articulations telles que les poignets, les genoux et les chevilles.
                                    """.trimIndent(),

                price = String.format(Locale.US, "%.3f", 20.08).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.VITAMIN ,
                imageResId = R.drawable.zink
            )

            val insertedId9: Long = productDao.insertProduct(product9)
            val productWithId9 = product9.copy(productId = insertedId9.toInt())
            productsList.add(productWithId9)

            val product5 = Product(
                productId = 0,
                name = "Metformine",
                description = "Metformine Viatris 500 Mg, Comprimé Pelliculé",
                detailedDescription = """
                  METFORMINE VIATRIS 500 mg, comprimé pelliculé contient la substance active chlorhydrate de metformine, un médicament utilisé pour traiter le diabète. Il appartient à la classe des médicaments appelés les biguanides.

                  L'insuline est une hormone produite par le pancréas et permettant à votre corps de récupérer le glucose (sucre) qui est dans le sang. Votre corps utilise le glucose pour produire de l'énergie ou le stocke pour l'utiliser plus tard.
                      """.trimIndent(),
                price = String.format(Locale.US, "%.3f", 29.01).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.MEDICAMENT ,
                imageResId = R.drawable.metformine
            )

            val insertedId5: Long = productDao.insertProduct(product5)
            val productWithId5 = product5.copy(productId = insertedId5.toInt())
            productsList.add(productWithId5)

            val product10 = Product(
                productId = 0,
                name = "Magnesium",
                description = "Magnesium Sulfate Paste 50g",
                detailedDescription = """
              La pâte de sulfate de magnésium est une pâte à dessin et une solution simple pour traiter les problèmes cutanés gênants et aide à soulager l'inconfort.
    
              Il s'agit d'un médicament générique. L'emballage, les marques et les saveurs peuvent varier par rapport à ceux affichés. Les images sont uniquement à des fins d’illustration.
                                             """.trimIndent(),
                price = String.format(Locale.US, "%.3f", 70.52).toDouble(),
                stock = Random.nextInt(1, 10),
                expirationDate = expirationDate,
                type =  ProductTypes.VITAMIN ,
                imageResId = R.drawable.magnezium
            )

            val insertedId10: Long = productDao.insertProduct(product10)
            val productWithId10 = product10.copy(productId = insertedId10.toInt())
            productsList.add(productWithId10)

            _products.postValue(productsList)
        }

    }


}
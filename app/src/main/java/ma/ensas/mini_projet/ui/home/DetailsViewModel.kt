package ma.ensas.mini_projet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.data.dao.ProductDao
import ma.ensas.mini_projet.data.entities.Product

class DetailsViewModel(private val productDao:ProductDao) : ViewModel() {

        private val _product = MutableLiveData<Product?>()
        val product: LiveData<Product?> get() = _product

        // Méthode pour récupérer un produit par son ID
        fun getProductById(productId: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                // Récupérer le produit avec l'ID fourni
                val productById = productDao.getProductById(productId)

                // Mettre à jour la LiveData avec le produit récupéré
                _product.postValue(productById)
            }
        }
    }

package ma.ensas.mini_projet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: FragmentDetailsBinding? = null // Le binding pour le fragment
    private val binding get() = _binding!! // Accéder au binding en toute sécurité

    companion object {
        fun newInstance() = DetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialiser le ViewModel
        val productDao = MediMarketDatabase.getDatabase(requireContext()).productDao()
        detailsViewModel = ViewModelProvider(this, DetailsViewModelFactory(productDao))
            .get(DetailsViewModel::class.java)

        // Récupérer l'ID du produit passé en argument
        val productId = arguments?.getInt("product_id") ?: 0
        detailsViewModel.getProductById(productId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialisation du binding
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root // Retourner la racine du layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observer pour écouter les changements de données dans le ViewModel
        detailsViewModel.product.observe(viewLifecycleOwner) { product ->
            // Si le produit existe, mettre à jour l'UI avec ses informations
            product?.let {
                // Exemple de mise à jour de la vue avec les données du produit
                binding.type.text= it.type.toString()
                binding.name.text = it.name
                binding.expiredAt.text = it.expirationDate.toString()
                binding.description.text= it.detailedDescription
                binding.price.text = "${it.price} MAD"
                binding.stock.text= "${it.stock} in stock"
                // Vous pouvez ajouter plus de mises à jour d'UI ici
            } ?: run {
                Log.i("DetailsFragment", "Produit non trouvé")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Nettoyer le binding pour éviter les fuites de mémoire
        _binding = null
    }
}

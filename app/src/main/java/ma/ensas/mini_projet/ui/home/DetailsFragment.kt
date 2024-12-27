package ma.ensas.mini_projet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.databinding.FragmentDetailsBinding
import ma.ensas.mini_projet.utils.SessionManager
import java.text.SimpleDateFormat
import java.util.Locale

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var sessionManager: SessionManager
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    var productId:Int=-1
    var stock:Int= -1

    companion object {
        fun newInstance() = DetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        productId = arguments?.getInt("product_id") ?: 0
        detailsViewModel.getProductById(productId)
        sessionManager = SessionManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                val formattedDate = SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault()).format(product.expirationDate)
                binding.type.text= product.type.toString()
                binding.name.text = product.name
                binding.expiredAt.text = formattedDate
                binding.description.text= it.detailedDescription
                binding.price.text = "${it.price} MAD"
                binding.stock.text= "${it.stock} in stock"
                stock=it.stock
            } ?: run {
                Log.i("DetailsFragment", "Produit non trouvé")
            }
        }
        binding.reservebtn.setOnClickListener {
            val loggedUser = sessionManager.getUserId()

            lifecycleScope.launch {

                val reservationId = detailsViewModel.saveReservation(
                    userId =loggedUser ,productId = productId, stock = stock
                )
                    Toast.makeText(
                        requireContext(),
                        "Réservation insérée avec l'ID : $reservationId",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package ma.ensas.mini_projet.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ma.ensas.mini_projet.databinding.FragmentDetailsBinding
import ma.ensas.mini_projet.utils.SessionManager
import ma.ensas.mini_projet.viewModels.DetailsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var sessionManager: SessionManager
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    var productId:Int=-1
    var stock:Int= -1
    var unitPrice:Double=-1.0

    companion object {
        fun newInstance() = DetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        val args: DetailsFragmentArgs by navArgs()
             productId = args.productId

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(product.expirationDate)
                binding.type.text = product.type.toString()
                binding.name.text = product.name
                binding.expiredAt.text = formattedDate
                binding.description.text = it.detailedDescription
                binding.price.text = "${it.price} MAD"

                if (it.stock > 0) {
                    binding.stock.text = "${it.stock} in stock"
                } else {
                    binding.stock.text = "out of stock"
                }

                stock = it.stock
                productId = it.productId
                unitPrice=it.price
                binding.productImage.setImageResource(it.imageResId)
            } ?: run {
                Log.i("DetailsFragment", "Product Not Found")
            }
        }


        val seekBar: SeekBar = binding.seekBar
        val quantityText: TextView = binding.quantityText
        val totalPrice:TextView = binding.totalprice

        quantityText.text = "Selected Quantity: ${seekBar.progress}"

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                quantityText.text = "Selected Quantity: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //
            }

            @SuppressLint("DefaultLocale")
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    totalPrice.text = String.format("%.2f", seekBar.progress * unitPrice)
                }
            }
        })

        binding.reservebtn.setOnClickListener {

            val loggedUser = sessionManager.getUserId()

            val selectedQuantity = seekBar.progress


            if (selectedQuantity <= 0 ) {
                Toast.makeText(requireContext(), "Invalid quantity selected", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            stock -= selectedQuantity
                if (stock > 0) {
                    binding.stock.text = "$stock in stock"
                } else {
                    binding.stock.text = "out of stock"
                }
            lifecycleScope.launch {
                try {
                    val reservationId = detailsViewModel.saveReservation(
                        userId = loggedUser,
                        productId = productId,
                        stock = stock,
                        quantity = selectedQuantity
                    )
                    Toast.makeText(requireContext(), "Reservation Succeeded", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error saving reservation: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

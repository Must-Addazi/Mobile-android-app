package ma.ensas.mini_projet.ui.reservation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ma.ensas.mini_projet.data.dto.ReservationDTO
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.databinding.FragmentHomeBinding
import ma.ensas.mini_projet.databinding.FragmentReservationBinding
import ma.ensas.mini_projet.viewModels.HomeViewModel
import ma.ensas.mini_projet.viewModels.ReservationViewModel

class ReservationFragment : Fragment() {

    private var _binding: FragmentReservationBinding? = null
    private val binding get() = _binding!!

    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var reservationViewModel: ReservationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reservationViewModel = ViewModelProvider(this)[ReservationViewModel::class.java]

        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()

        observeProducts()


        return root
    }



    private fun handleEmptyState(reservations: List<ReservationDTO>) {
        if (reservations.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.emptyMessage.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyMessage.visibility = View.GONE
        }
    }
    private fun setupRecyclerView() {
        reservationAdapter = ReservationAdapter(emptyList()) { reservation ->
            Handler().postDelayed({
                reservationViewModel.loadProduct(reservation.productName)

                reservationViewModel.product.observe(viewLifecycleOwner) { productId ->
                    Log.i("mustapha", "product found $productId")
                    val action = ReservationFragmentDirections.actionFragmentReservationToDetailsFragment(productId)

                    findNavController().navigate(action)

                }
            }, 1000)
        }

        binding.recyclerView.apply {
            adapter = reservationAdapter
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun observeProducts() {
        reservationViewModel.reservationDTOs.observe(viewLifecycleOwner) { reservationDTO ->
            handleEmptyState(reservationDTO)
            reservationAdapter.updateReservation(reservationDTO)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



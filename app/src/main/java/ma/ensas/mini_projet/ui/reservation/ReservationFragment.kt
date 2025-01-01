package ma.ensas.mini_projet.ui.reservation

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ma.ensas.mini_projet.databinding.FragmentReservationBinding
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

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = reservationAdapter
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun observeProducts() {
        reservationViewModel.reservationDTOs.observe(viewLifecycleOwner) { reservationDTO ->
            reservationAdapter.updateReservation(reservationDTO)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



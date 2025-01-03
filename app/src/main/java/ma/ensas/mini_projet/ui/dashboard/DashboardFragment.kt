package ma.ensas.mini_projet.ui.dashboard

import ReservationAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ma.ensas.mini_projet.databinding.FragmentDashboardBinding
import ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters.UsersAdapter
import ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters.ProductAdapter
import ma.ensas.mini_projet.viewModels.DashboardViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var availableProductsAdapter:ProductAdapter
    private lateinit var outOfStockProductsAdapter:ProductAdapter
    private lateinit var reservationAdapter: ReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        availableProductsAdapter = ProductAdapter(emptyList())
        outOfStockProductsAdapter = ProductAdapter(emptyList())
        usersAdapter = UsersAdapter(emptyList())
        reservationAdapter = ReservationAdapter(emptyList())

        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = usersAdapter
        }
        binding.rvAvailableProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = availableProductsAdapter
        }
        binding.rvOutOfStock.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = outOfStockProductsAdapter
        }
        binding.rvReservations.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = reservationAdapter
        }
    }

    private fun observeViewModel() {
        dashboardViewModel.users.observe(viewLifecycleOwner) { users ->
            usersAdapter.updateUsers(users)
        }

        dashboardViewModel.availableProducts.observe(viewLifecycleOwner) { products ->
            availableProductsAdapter.updateProducts(products)
        }

        dashboardViewModel.outOfStockProducts.observe(viewLifecycleOwner) { products ->
            outOfStockProductsAdapter.updateProducts(products)
        }

        // Observer les réservations
        dashboardViewModel.reservations.observe(viewLifecycleOwner) { reservations ->
            reservationAdapter.updateReservation(reservations)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

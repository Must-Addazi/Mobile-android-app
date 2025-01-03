package ma.ensas.mini_projet.ui.dashboard

import ReservationAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ma.ensas.mini_projet.databinding.FragmentDashboardBinding
import ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters.ProductAdapter
import ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters.UsersAdapter
import ma.ensas.mini_projet.viewModels.DashboardViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var productsAdapter: ProductAdapter
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
        // Initialiser les adaptateurs avec des listes vides
        usersAdapter = UsersAdapter(emptyList())
        productsAdapter = ProductAdapter(emptyList())
        reservationAdapter = ReservationAdapter(emptyList())

        // Configurer les RecyclerViews pour une orientation horizontale
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = usersAdapter
        }
        binding.rvAvailableProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = productsAdapter
        }
        binding.rvOutOfStock.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = productsAdapter
        }
        binding.rvReservations.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = reservationAdapter
        }
    }

    private fun observeViewModel() {
        // Observer les données des utilisateurs
        dashboardViewModel.users.observe(viewLifecycleOwner) { users ->
            usersAdapter.updateData(users)
        }

        // Observer les produits disponibles
        dashboardViewModel.availableProducts.observe(viewLifecycleOwner) { products ->
            productsAdapter.updateProductList(products)
        }

        // Observer les produits en rupture de stock (ajouter cette méthode si nécessaire)
        dashboardViewModel.outOfStockProducts.observe(viewLifecycleOwner) { products ->
            productsAdapter.updateProductList(products)
        }

        // Observer les réservations
        dashboardViewModel.reservations.observe(viewLifecycleOwner) { reservations ->
            reservationAdapter.updateReservations(reservations)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

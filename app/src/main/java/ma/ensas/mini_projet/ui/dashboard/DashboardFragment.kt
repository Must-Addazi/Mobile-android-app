package ma.ensas.mini_projet.ui.dashboard

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
import ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters.ReservationAdapter

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
        usersAdapter = UsersAdapter(emptyList())
        productsAdapter = ProductAdapter(emptyList())
        reservationAdapter = ReservationAdapter(emptyList())

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
        dashboardViewModel.users.observe(viewLifecycleOwner) { users ->
            usersAdapter.updateData(users)
        }
//        dashboardViewModel.availableProducts.observe(viewLifecycleOwner) { products ->
//            productsAdapter.updateData(products)
//        }
        dashboardViewModel.users.observe(viewLifecycleOwner) { users ->
            usersAdapter.updateData(users)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
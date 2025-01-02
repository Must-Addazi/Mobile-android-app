package ma.ensas.mini_projet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.data.database.MediMarketDatabase
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.databinding.FragmentHomeBinding
import ma.ensas.mini_projet.ui.reservation.ReservationFragmentDirections
import ma.ensas.mini_projet.viewModels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupRecyclerView()


        homeViewModel.products.observe(viewLifecycleOwner) { products ->
            handleEmptyState(products)
            productAdapter.updateProducts(products)
        }

        homeViewModel.filteredProducts.observe(viewLifecycleOwner) { products ->
            handleEmptyState(products)
            productAdapter.updateProducts(products)
        }

        setupMenu()

        return binding.root
    }

    private fun handleEmptyState(products: List<Product>) {
        if (products.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.emptyMessage.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyMessage.visibility = View.GONE
        }
    }


    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main, menu)
                val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { homeViewModel.searchProducts(it) }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { homeViewModel.searchProducts(it) }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }

        }, viewLifecycleOwner)
    }


    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList()) { product ->
            android.os.Handler().postDelayed({
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment2(product.productId)
                findNavController().navigate(action)
            }, 1000)
        }

        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

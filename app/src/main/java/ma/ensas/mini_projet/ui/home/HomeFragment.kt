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
import ma.ensas.mini_projet.databinding.FragmentHomeBinding
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
        val productDao = MediMarketDatabase.getDatabase(requireContext()).productDao()
        setupRecyclerView()
            homeViewModel= ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(products)
        }

        homeViewModel.filteredProducts.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(products)
        }

        homeViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(products)
        }

        homeViewModel.filteredProducts.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(products)
        }

        setupMenu()

        setupMenu()

        return binding.root
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
                val bundle = Bundle().apply {
                    putInt("product_id", product.productId)
                }
                findNavController().navigate(R.id.action_homeFragment_to_detailsFragment2, bundle)
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

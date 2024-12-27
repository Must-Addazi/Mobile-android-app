package ma.ensas.mini_projet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.databinding.FragmentHomeBinding
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

        observeProducts()
        return binding.root
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList()) { product ->
            android.os.Handler().postDelayed({
                Log.i("mustapha", "authenticated")


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



    private fun observeProducts() {
        homeViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateProducts(products)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

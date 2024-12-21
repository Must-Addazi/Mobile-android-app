package ma.ensas.mini_projet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ma.ensas.mini_projet.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


        private lateinit var productAdapter: ProductAdapter
        private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            val root: View = binding.root

            setupRecyclerView()

            observeProducts()

            return root
        }

        private fun setupRecyclerView() {
            productAdapter = ProductAdapter(emptyList()) { product ->
                Toast.makeText(context, "Produit sélectionné : ${product.name}", Toast.LENGTH_SHORT).show()
            }
            binding.recyclerView.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }

        private fun observeProducts() {
            homeViewModel.products.observe(viewLifecycleOwner) { products ->
                productAdapter = ProductAdapter(products) { product ->
                    Toast.makeText(context, "Produit sélectionné : ${product.name}", Toast.LENGTH_SHORT).show()
                }
                binding.recyclerView.adapter = productAdapter
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

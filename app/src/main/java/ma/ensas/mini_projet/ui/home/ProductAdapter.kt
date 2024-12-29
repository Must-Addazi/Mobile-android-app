package ma.ensas.mini_projet.ui.home

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.databinding.ItemProductBinding


class ProductAdapter(private var productList:List<Product>,
                     private val onClick: (Product) -> Unit):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.name.text =  "${product.productId}"
            binding.price.text = "${product.price} MAD"
            binding.type.text = product.type.toString()
            binding.description.text = product.description
//            product.productImage?.let { byteArray ->
//                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//                binding.productImage.setImageBitmap(bitmap)
//            } ?: run {
//                binding.productImage.setImageResource(R.drawable.logo)
//            }
            binding.root.setOnClickListener { onClick(product) }
        }
    }
    fun updateProducts(newProducts: List<Product>) {
        productList = newProducts
        notifyDataSetChanged()
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ProductViewHolder(binding)
        }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size
    }

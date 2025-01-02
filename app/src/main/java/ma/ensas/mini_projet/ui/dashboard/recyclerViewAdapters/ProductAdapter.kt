package ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.entities.Product
import ma.ensas.mini_projet.databinding.DashboardItemProductBinding

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: DashboardItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.prodName.text = product.name
            binding.prodItemImg.setImageResource(product.imageResId)
            binding.prodStock.text = "stock: ${product.stock}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = DashboardItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size
}
package ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.entities.User
import ma.ensas.mini_projet.databinding.DashboardItemUserBinding

class UsersAdapter(private var users: List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = DashboardItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(private val binding: DashboardItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.tvUserId.text = "ID: ${user.userId}"
            binding.tvUserName.text = "Name: ${user.username}"
            binding.tvUserEmail.text = "Email: ${user.email}"
        }
    }
}
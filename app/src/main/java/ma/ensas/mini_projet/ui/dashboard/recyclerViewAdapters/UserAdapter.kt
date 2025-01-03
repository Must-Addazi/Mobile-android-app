package ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.entities.User
import ma.ensas.mini_projet.databinding.DashboardItemUserBinding

class UsersAdapter(private var userList: List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: DashboardItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {

            binding.tvUserId.text = "ID: ${user.userId}"
            binding.tvUserName.text = "Name: ${user.username}"
            binding.tvUserEmail.text = "Email: ${user.email}"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUsers(newUsers: List<User>) {
        userList = newUsers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder{
        val binding = DashboardItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size

}

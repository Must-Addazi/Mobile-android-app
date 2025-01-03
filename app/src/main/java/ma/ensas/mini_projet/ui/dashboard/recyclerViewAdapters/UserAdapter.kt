package ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.entities.User
import ma.ensas.mini_projet.databinding.DashboardItemUserBinding

class UsersAdapter(private var users: List<User>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    // Méthode pour mettre à jour les données avec DiffUtil
    fun updateData(newUsers: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UserDiffCallback(users, newUsers))
        users = newUsers
        diffResult.dispatchUpdatesTo(this)
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

    // Classe DiffUtil.Callback pour comparer les listes
    class UserDiffCallback(
        private val oldList: List<User>,
        private val newList: List<User>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].userId == newList[newItemPosition].userId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

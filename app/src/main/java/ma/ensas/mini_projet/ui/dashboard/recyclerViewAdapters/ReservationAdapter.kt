import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.databinding.DashboardItemReservationBinding

class ReservationAdapter(private var reservationList: List<Reservation>) :
    RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    // ViewHolder class
    inner class ReservationViewHolder(private val binding: DashboardItemReservationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(reservation: Reservation) {
            binding.reservationId.text = "ID: ${reservation.id}"
            binding.reservationDate.text = "Date: ${reservation.reservedAt}"
            binding.reservationStatus.text = "Status: ${reservation.status}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = DashboardItemReservationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(reservationList[position])
    }

    override fun getItemCount() = reservationList.size

    // Méthode pour mettre à jour la liste des réservations avec DiffUtil
    fun updateReservations(newReservations: List<Reservation>) {
        val diffResult = DiffUtil.calculateDiff(ReservationDiffCallback(reservationList, newReservations))
        reservationList = newReservations
        diffResult.dispatchUpdatesTo(this)
    }

    // DiffUtil Callback
    class ReservationDiffCallback(
        private val oldList: List<Reservation>,
        private val newList: List<Reservation>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

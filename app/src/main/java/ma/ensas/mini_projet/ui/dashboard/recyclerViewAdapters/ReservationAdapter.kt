package ma.ensas.mini_projet.ui.dashboard.recyclerViewAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.databinding.DashboardItemReservationBinding

class ReservationAdapter(private var reservationList: List<Reservation>)
    : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

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

    @SuppressLint("NotifyDataSetChanged")
    fun updateReservations(newReservations: List<Reservation>) {
        reservationList = newReservations
        notifyDataSetChanged()
    }
}
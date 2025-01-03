package ma.ensas.mini_projet.ui.reservation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.dto.ReservationDTO
import ma.ensas.mini_projet.databinding.ItemReservationBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationAdapter(private var reservationList:List<ReservationDTO>,
                         private val onClick: (ReservationDTO) -> Unit):RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {
        inner class ReservationViewHolder(private val binding: ItemReservationBinding) :
            RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(reservation: ReservationDTO) {
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(reservation.reservedAt)
                binding.name.text = reservation.productName
                binding.status.text = reservation.status.toString()
                binding.quantity.text= "Quantity: ${reservation.quantity}"
                binding.reservedAt.text=formattedDate

                binding.root.setOnClickListener { onClick(reservation) }
            }
        }
        @SuppressLint("NotifyDataSetChanged")
        fun updateReservation(newReservation: List<ReservationDTO>) {
            reservationList = newReservation
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
            val binding = ItemReservationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ReservationViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
            holder.bind(reservationList[position])
        }

        override fun getItemCount() = reservationList.size
    }

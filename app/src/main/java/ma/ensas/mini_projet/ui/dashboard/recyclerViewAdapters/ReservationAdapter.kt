import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ma.ensas.mini_projet.data.entities.Reservation
import ma.ensas.mini_projet.databinding.DashboardItemReservationBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationAdapter(private var reservationList:List<Reservation>):RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {
        inner class ReservationViewHolder(private val binding: DashboardItemReservationBinding) :
            RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(reservation: Reservation) {
                val formattedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(reservation.reservedAt)
                binding.reservationId.text = "ID: ${reservation.id}"
                binding.reservationDate.text = "Date: $formattedDate"
                binding.reservationStatus.text = "Status: ${reservation.status}"
            }
        }
        @SuppressLint("NotifyDataSetChanged")
        fun updateReservation(newReservation: List<Reservation>) {
            reservationList = newReservation
            notifyDataSetChanged()
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
    }

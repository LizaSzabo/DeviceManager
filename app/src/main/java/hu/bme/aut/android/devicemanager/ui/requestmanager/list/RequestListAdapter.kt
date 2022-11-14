package hu.bme.aut.android.devicemanager.ui.requestmanager.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.devicemanager.R
import hu.bme.aut.android.devicemanager.databinding.ItemRentalRequestBinding
import hu.bme.aut.android.devicemanager.domain.model.RentalRequest

class RequestListAdapter :
    ListAdapter<RentalRequest, RequestListAdapter.RequestListViewHolder>(ItemCallBack) {

    var rentalRequests = emptyList<RentalRequest>()

    inner class RequestListViewHolder(binding: ItemRentalRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDeviceName: TextView = binding.deviceName
        val itemCard: CardView = binding.cardView

        var rentalRequest: RentalRequest? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RequestListViewHolder(
        ItemRentalRequestBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: RequestListViewHolder, position: Int) {
        val rentalRequest = rentalRequests[position]

        holder.rentalRequest = rentalRequest
        holder.tvDeviceName.text = rentalRequest.device?.name

        holder.itemCard.setBackgroundResource(R.drawable.card_view_background_light)
    }

    override fun getItemCount(): Int {
        return rentalRequests.size
    }

    fun addAllRentalRequests(rentalRequest: List<RentalRequest>) {
        rentalRequests -= rentalRequests
        rentalRequests += rentalRequest
        submitList(rentalRequests)
    }

    companion object {

        object ItemCallBack : DiffUtil.ItemCallback<RentalRequest>() {
            override fun areItemsTheSame(oldItem: RentalRequest, newItem: RentalRequest): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: RentalRequest,
                newItem: RentalRequest
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
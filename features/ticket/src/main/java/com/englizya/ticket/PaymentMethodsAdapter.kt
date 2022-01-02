package com.englizya.ticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.ticket.databinding.CardViewPaymentTypeBinding

class PaymentMethodsAdapter(
    private val onMethodClicked: (Int) -> Unit,
) : RecyclerView.Adapter<PaymentMethodsAdapter.ViewHolder>() {

    private var currentMethodPosition = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewPaymentTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.updateUI(PaymentMethod.Card)
            }

            1 -> {
                holder.updateUI(PaymentMethod.Cash)
            }

            2 -> {
                holder.updateUI(PaymentMethod.QR)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(private val bind: CardViewPaymentTypeBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun updateUI(paymentMethod: PaymentMethod) {
            bind.paymentImage.setImageResource(paymentMethod.iconRes)
            bind.paymentText.setText(paymentMethod.titleRes)

            bind.root.setOnClickListener {
                onMethodClicked(paymentMethod.titleRes)
            }
        }

    }
}

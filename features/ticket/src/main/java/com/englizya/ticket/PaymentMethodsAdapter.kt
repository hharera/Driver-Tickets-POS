package com.englizya.ticket

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.ticket.ticket.databinding.CardViewPaymentTypeBinding

class PaymentMethodsAdapter(
    private val selectedPaymentMethod: PaymentMethod = PaymentMethod.Cash,
    private val onMethodClicked: (PaymentMethod) -> Unit,
) : RecyclerView.Adapter<PaymentMethodsAdapter.ViewHolder>() {

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
                holder.updateUI(PaymentMethod.Card, position)
            }

            1 -> {
                holder.updateUI(PaymentMethod.Cash, position)
            }

            2 -> {
                holder.updateUI(PaymentMethod.QR, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(private val bind: CardViewPaymentTypeBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun updateUI(paymentMethod: PaymentMethod, position: Int) {
            bind.root.setOnClickListener {
                onMethodClicked(paymentMethod)
            }

            bind.paymentImage.setImageResource(paymentMethod.iconRes)
            bind.paymentText.setText(paymentMethod.titleRes)

            if (paymentMethod.javaClass == selectedPaymentMethod.javaClass) {
                bind.paymentImage.apply {
                    setImageResource(paymentMethod.iconRes)
                    setColorFilter(Color.GRAY)
                }
                bind.paymentText.apply {
                    setText(paymentMethod.titleRes)
                    setTextColor(Color.GRAY)
                }
            }
        }

    }
}

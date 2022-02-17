package com.englizya.ticket

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.ticket.ticket.databinding.CardViewPaymentTypeBinding
import com.englizya.ticket.utils.Constant.DEFAULT_PAYMENT_CHOSEN

class PaymentMethodsAdapter(
    private val itemChosenPosition: Int = DEFAULT_PAYMENT_CHOSEN,
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
                //                TODO set max as const
                //                onMethodClicked(paymentMethod.titleRes)
            }

            if (position == itemChosenPosition) {
                bind.paymentImage.setImageResource(paymentMethod.iconRes)
                bind.paymentText.setText(paymentMethod.titleRes)
            } else {
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

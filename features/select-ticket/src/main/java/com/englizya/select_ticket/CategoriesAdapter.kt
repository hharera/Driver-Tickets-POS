package com.englizya.select_ticket

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.select_ticket.databinding.CardViewCategoryBinding

class CategoriesAdapter(
    private var selectedCategory: Int,
    private var categoryList: List<Int> ,
    private val onItemClicked: (Int) -> Unit,
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUI(categoryList.get(position))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setSelectedCategory(it: Int) {
        selectedCategory = it
        val list = categoryList.filter { true }
        categoryList = listOf()
        notifyDataSetChanged()
        categoryList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val bind: CardViewCategoryBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun updateUI(categoryValue : Int) {
            bind.root.setOnClickListener {
                onItemClicked(categoryValue)
            }

            if (selectedCategory == categoryValue) {
                bind.categoryValue.setTextColor(Color.BLACK)
                bind.categoryImage.setColorFilter(Color.BLACK)
            } else {
                bind.categoryValue.setTextColor(Color.WHITE)
                bind.categoryImage.setColorFilter(Color.WHITE)
            }
        }

    }
}

package com.incomeify.incomeifyapp.ui.dashboard.saved.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.data.local.SavedIncome
import com.incomeify.incomeifyapp.databinding.SavedItemBinding

class SavedAdapter(private val context: Context, private val savedIncomeList: List<SavedIncome>)
    : RecyclerView.Adapter<SavedAdapter.CustomViewHolder>() {

    lateinit var listener: OnItemClickListener

    inner class CustomViewHolder(private val binding: SavedItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bindList(savedIncome: SavedIncome){
            with(binding) {
                tvIncome.text = context.getString(R.string.income_text, savedIncome.income)
                tvCareerLevel.text = savedIncome.careerLevel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            SavedItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return savedIncomeList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindList(savedIncomeList[position])
        holder.itemView.setOnClickListener {
            listener.onItemClicked(savedIncomeList[position])
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: SavedIncome)
    }
}
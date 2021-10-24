package com.app.netrasehat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.netrasehat.R
import com.app.netrasehat.databinding.ItemRagamMakananBinding
import com.app.netrasehat.model.RagamMakanan
import com.bumptech.glide.Glide

class RagamMakananAdapter : RecyclerView.Adapter<RagamMakananAdapter.RagamMakananViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var listData = ArrayList<RagamMakanan>()

    fun setData(data: List<RagamMakanan>?) {
        if (data == null) return
        this.listData.clear()
        this.listData.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RagamMakananAdapter.RagamMakananViewHolder {
        val itemRagamMakananBinding =
            ItemRagamMakananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RagamMakananViewHolder(itemRagamMakananBinding)
    }

    override fun onBindViewHolder(
        holder: RagamMakananAdapter.RagamMakananViewHolder,
        position: Int
    ) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class RagamMakananViewHolder(private val binding: ItemRagamMakananBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RagamMakanan) {
            binding.apply {
                tvTitleMakanan.text = data.judul

                Glide.with(itemView.context)
                    .load(data.img)
                    .centerCrop()
                    .placeholder(R.color.purple_1)
                    .into(imgMakanan)

                itemView.setOnClickListener {
                    onItemClickListener.onItemClicked(data)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(data: RagamMakanan)
    }
}
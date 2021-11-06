package com.app.netrasehat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.netrasehat.databinding.ItemPhbsBinding
import com.app.netrasehat.model.Phbs

class PhbsAdapter : RecyclerView.Adapter<PhbsAdapter.PhbsViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var listPhbs = ArrayList<Phbs>()

    fun setDataPhbs(phbs: List<Phbs>?) {
        if (phbs == null) return
        this.listPhbs.clear()
        this.listPhbs.addAll(phbs)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhbsAdapter.PhbsViewHolder {
        val itemPhbsBinding =
            ItemPhbsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhbsViewHolder(itemPhbsBinding)
    }

    override fun onBindViewHolder(holder: PhbsAdapter.PhbsViewHolder, position: Int) {
        holder.bind(listPhbs[position])
    }

    override fun getItemCount(): Int = listPhbs.size

    inner class PhbsViewHolder(private val binding: ItemPhbsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(phbs: Phbs) {
            binding.apply {
                tvTitlePhbs.text = phbs.judul
                itemView.setOnClickListener {
                    onItemClickListener.onItemClicked(phbs)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(phbs: Phbs)
    }
}
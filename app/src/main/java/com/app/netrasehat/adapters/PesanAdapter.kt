package com.app.netrasehat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.netrasehat.databinding.ItemPesanGiziseimbangBinding
import com.app.netrasehat.model.Pesan

class PesanAdapter : RecyclerView.Adapter<PesanAdapter.PesanViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var listPesan = ArrayList<Pesan>()

    fun setDataPesan(pesan: List<Pesan>?) {
        if (pesan == null) return
        this.listPesan.clear()
        this.listPesan.addAll(pesan)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PesanAdapter.PesanViewHolder {
        val itemPesanBinding = ItemPesanGiziseimbangBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PesanViewHolder(itemPesanBinding)
    }

    override fun onBindViewHolder(holder: PesanAdapter.PesanViewHolder, position: Int) {
        holder.bind(listPesan[position])
    }

    override fun getItemCount(): Int = listPesan.size

    inner class PesanViewHolder(private val binding: ItemPesanGiziseimbangBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pesan: Pesan) {
            with(binding) {
                titlePesan.text = pesan.title
                itemView.setOnClickListener {
                    onItemClickListener.onPesanClicked(pesan)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onPesanClicked(pesan: Pesan)
    }
}
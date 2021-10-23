package com.app.netrasehat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.netrasehat.databinding.ItemJenisRagamMakananBinding
import com.app.netrasehat.model.JenisJenisMakanan

class JenisMakananAdapter : RecyclerView.Adapter<JenisMakananAdapter.JenisMakananViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    private var listJenisMakanan = ArrayList<JenisJenisMakanan>()

    fun setData(data: List<JenisJenisMakanan>?) {
        if (data == null) return
        this.listJenisMakanan.clear()
        this.listJenisMakanan.addAll(data)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JenisMakananAdapter.JenisMakananViewHolder {
        val itemJenisMakananinding =
            ItemJenisRagamMakananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JenisMakananViewHolder(itemJenisMakananinding)
    }

    override fun onBindViewHolder(
        holder: JenisMakananAdapter.JenisMakananViewHolder,
        position: Int
    ) {
        holder.bind(listJenisMakanan[position])
    }

    override fun getItemCount(): Int = listJenisMakanan.size

    inner class JenisMakananViewHolder(private val binding: ItemJenisRagamMakananBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: JenisJenisMakanan) {
            binding.apply {
                tvTitleJenis.text = data.judul
                itemView.setOnClickListener {
                    onItemClickListener.onItemClicked(data)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(data: JenisJenisMakanan)
    }
}
package com.example.teremotosrecycler.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.databinding.EqItemBinding

class TerremotoAdapter : RecyclerView.Adapter<TerremotoAdapter.TerremotoViewHolder>() {

    var terremotos = mutableListOf<Terremoto>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TerremotoViewHolder {
        val binding = EqItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //val view: View = LayoutInflater.from(parent.context).inflate(R.layout.eq_item, parent, false)
        return TerremotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return terremotos.size
    }

    override fun onBindViewHolder(holder: TerremotoViewHolder, position: Int) {
        val terremoto: Terremoto = terremotos[position]
        holder.bind(terremoto)

    }
    inner class TerremotoViewHolder(private var binding: EqItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(terremoto: Terremoto){
            binding.magnitudTxt.text = String.format("%.2f", terremoto.magnitud)
            binding.magnitudTxt.text = terremoto.magnitud.toString()
            binding.lugarTxt.text = terremoto.lugar
        }
    }

}
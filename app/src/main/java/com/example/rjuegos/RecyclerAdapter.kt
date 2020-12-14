package com.example.rjuegos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rjuegos.model.Juego
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.juego_row.view.*

class RecyclerAdapter(val context: Context, val listaJuegos:ArrayList<Juego>, private val itemClickListener: OnJuegoClickListener):RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return JuegosViewHolder(LayoutInflater.from(context).inflate(R.layout.juego_row, parent, false))
    }

    interface OnJuegoClickListener{
        fun onImagenClick(imagen:String)
        fun onItemClick(nombre:String, genero:String, formato:Boolean)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if(holder is JuegosViewHolder){
            holder.bind(listaJuegos[position], position)
        }
    }

    override fun getItemCount(): Int = listaJuegos.size

    inner class JuegosViewHolder(itemView: View):BaseViewHolder<Juego>(itemView){
        override fun bind(item: Juego, position: Int) {
            Glide.with(context).load(item.imagenURL).into(itemView.profile_image)
            itemView.marcaModelo.text = item.nombre
            itemView.genero.text = item.genero

            if(item.formato == true){
                itemView.formato.text = "Físico"
            }else{
                itemView.formato.text = "Digital"
            }

            itemView.setOnClickListener{
                itemClickListener.onItemClick(item.nombre, item.genero, item.formato)
            }

            itemView.profile_image.setOnClickListener {
                itemClickListener.onImagenClick(item.imagenURL)
            }
        }
    }
}
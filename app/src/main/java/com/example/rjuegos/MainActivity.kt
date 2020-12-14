package com.example.rjuegos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rjuegos.model.Juego
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.juego_row.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnJuegoClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpReciclerView()
    }

    fun setUpReciclerView(){
        val url = "http://iesayala.ddns.net/davidgr/juegos.php"

        val queue = Volley.newRequestQueue(this)

        var listaJue:List<Juego> = listOf()

        var lista = ArrayList<Juego>()

        val stringRequest = StringRequest(
            Request.Method.GET, url, Response.Listener {
                response ->
            val jsonArray = JSONArray(response)
            for(i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                val nombre = jsonObject.get("nombre")
                val descripcion = jsonObject.get("descripcion")
                val foto = jsonObject.get("foto")
                val precio = jsonObject.get("precio")
                val formato = jsonObject.get("formato")
                val genero = jsonObject.get("genero")

                var form = false
                if (formato == 1){
                    form = true
                }

                lista.add(Juego(nombre.toString(), descripcion.toString(), foto.toString(), Integer.parseInt(precio.toString()), form, genero.toString()))
                form = false
            }
                reciclerView.adapter=RecyclerAdapter(this, lista, this)
        },
        Response.ErrorListener{
            textView2.text = "Error"
        })

        queue.add(stringRequest)

        reciclerView.layoutManager = LinearLayoutManager(this)
        reciclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onImagenClick(imagen: String){
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("URL", imagen)
        startActivity(intent)
    }

    override fun onItemClick(nombre: String, genero:String, formato: Boolean){
        var forma = "fisico"
        if(formato != true){
            forma = "Digital"
        }

        Toast.makeText(this, nombre+" "+genero+" "+forma, Toast.LENGTH_LONG).show()
    }
}
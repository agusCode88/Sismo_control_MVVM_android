package com.example.teremotosrecycler.presentation.detail.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.teremotosrecycler.R
import com.example.teremotosrecycler.data.local.TerremotoDatabase
import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.data.repository.MainRepository
import com.example.teremotosrecycler.databinding.ActivityDetailBinding
import com.example.teremotosrecycler.domain.TerremotosUseCase
import com.example.teremotosrecycler.presentation.detail.viewmodel.DetailViewModel
import com.example.teremotosrecycler.presentation.detail.viewmodel.DetailViewModelFactory
import com.example.teremotosrecycler.presentation.home.viewmodel.MainViewModel
import com.example.teremotosrecycler.presentation.home.viewmodel.MainViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
       // supportActionBar?.title = getString(R.string.detail_activity_title) // Ajusta esto según tu necesidad

        val database = TerremotoDatabase.getDatabase(application)
        val repository = MainRepository(database.terremotoDao())
        val getTerremotosUseCase = TerremotosUseCase(repository)

        val viewModelFactory = DetailViewModelFactory(application, getTerremotosUseCase)
        detailViewModel = ViewModelProvider(this,viewModelFactory)[DetailViewModel::class.java]

        var idTerremoto: String? = intent.getStringExtra("idTerremoto")
        if (idTerremoto != null) {
            detailViewModel.getDetailTerremoto(idTerremoto)

        } else {
            Log.e("IDTERREMOTO", "El ID del terremoto es null")
        }

        detailViewModel.terremotoLv.observe(this){
            it.apply {
                if (it != null) {
                    bindDetailTerremoto(it)
                }
            }
        }
    }

    private fun bindDetailTerremoto(terremoto: Terremoto){
        binding.magnitudeTxt.text = terremoto.magnitud.toString()
        binding.longitudeDataTxt.text = terremoto.longitud.toString()
        binding.latitudeDataTxt.text = terremoto.latitud.toString()
        binding.adressDataTxt.text = terremoto.lugar
        binding.txtTime.text = terremoto.time.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
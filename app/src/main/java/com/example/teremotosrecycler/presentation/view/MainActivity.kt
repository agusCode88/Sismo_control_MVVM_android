package com.example.teremotosrecycler.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teremotosrecycler.data.repository.MainRepository
import com.example.teremotosrecycler.databinding.ActivityMainBinding
import com.example.teremotosrecycler.domain.TerremotosUseCase
import com.example.teremotosrecycler.presentation.viewmodel.MainViewModel
import com.example.teremotosrecycler.presentation.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an instance of the repository and use case
        val repository = MainRepository()
        val getTerremotosUseCase = TerremotosUseCase(repository)

        // Pass the use case to the ViewModelFactory
        val viewModelFactory = MainViewModelFactory(getTerremotosUseCase)

        val viewModel: MainViewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]

        val adaptador = TerremotoAdapter()
        binding.rclview.adapter = adaptador

        viewModel.terremotoLV.observe(this, Observer {
            binding.rclview.layoutManager = LinearLayoutManager(this)
            adaptador.terremotos = it
            isDataAdapter(adaptador)
        })

    }

    private fun isDataAdapter(adaptador: TerremotoAdapter) {
        if (adaptador.terremotos.isEmpty()) {
            binding.txtEmpty.visibility = View.VISIBLE
        } else {
            binding.txtEmpty.visibility = View.GONE
        }
    }

}
package com.example.teremotosrecycler.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teremotosrecycler.R
import com.example.teremotosrecycler.data.local.TerremotoDatabase
import com.example.teremotosrecycler.data.network.TerremotoApiResponseStatus
import com.example.teremotosrecycler.data.repository.MainRepository
import com.example.teremotosrecycler.databinding.ActivityMainBinding
import com.example.teremotosrecycler.domain.TerremotosUseCase
import com.example.teremotosrecycler.presentation.viewmodel.MainViewModel
import com.example.teremotosrecycler.presentation.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val database = TerremotoDatabase.getDatabase(application)
        // Create an instance of the repository and use case
        val repository = MainRepository(database.terremotoDao())
        val getTerremotosUseCase = TerremotosUseCase(repository)

        // Pass the use case to the ViewModelFactory
        val viewModelFactory = MainViewModelFactory(application, getTerremotosUseCase)

        viewModel = ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]

        viewModel.statusLv.observe(this, Observer {

            when(it!!){
                TerremotoApiResponseStatus.DONE -> binding.loading.visibility = View.GONE
                TerremotoApiResponseStatus.LOADING -> binding.loading.visibility = View.VISIBLE
                TerremotoApiResponseStatus.NOT_INTERNET_CONNECTION_ERROR -> binding.loading.visibility = View.VISIBLE
            }

        })

        val isByOrderSP = viewModel.getFilterSP()
        if(isByOrderSP)
            viewModel.setOrderListFilter(true)
        else
            viewModel.setOrderListFilter(false)


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemID = item.itemId
        return when(itemID){
            R.id.menu_sort_magnitude -> {
                viewModel.setOrderListFilter(true)
                viewModel.setFilterSharedPreferences(true)
                true
            }//Sort by magnitude
            R.id.menu_sort_time -> {
                viewModel.setOrderListFilter(false)
                viewModel.setFilterSharedPreferences(false)
                true
            } // Sort by time
            else -> super.onOptionsItemSelected(item)
        }

    }

}
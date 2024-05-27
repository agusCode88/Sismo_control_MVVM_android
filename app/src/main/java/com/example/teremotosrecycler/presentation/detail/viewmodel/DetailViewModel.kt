package com.example.teremotosrecycler.presentation.detail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teremotosrecycler.data.model.Terremoto
import com.example.teremotosrecycler.domain.TerremotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
    application: Application,
    private val useCase: TerremotosUseCase
): AndroidViewModel(application) {

    private var _terremotoSelected = MutableLiveData<Terremoto?>()
    val terremotoLv: LiveData<Terremoto?>
        get() =  _terremotoSelected


     fun getDetailTerremoto(idTerremoto: String){
        viewModelScope.launch(Dispatchers.IO) {
            val terremoto = useCase.getDetailTerremotoDB(idTerremoto)
            withContext(Dispatchers.Main){
                _terremotoSelected.value = terremoto
            }
        }
    }

}
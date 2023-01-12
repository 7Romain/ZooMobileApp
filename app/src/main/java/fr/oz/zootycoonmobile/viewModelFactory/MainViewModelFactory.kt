package fr.oz.zootycoonmobile.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.oz.zootycoonmobile.ActionRepository
import fr.oz.zootycoonmobile.viewModel.MainViewModel

class MainViewModelFactory(private val repository: ActionRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
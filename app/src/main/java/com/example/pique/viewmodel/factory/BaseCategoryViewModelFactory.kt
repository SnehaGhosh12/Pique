package com.example.pique.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pique.data.Category
import com.example.pique.viewmodel.CategoryViewModel
import com.google.firebase.firestore.FirebaseFirestore

class BaseCategoryViewModelFactory(
    private val firestore: FirebaseFirestore,
    private val category: Category
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        return CategoryViewModel(firestore, category) as T
    }
}
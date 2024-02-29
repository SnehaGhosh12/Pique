package com.example.pique.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pique.data.Products
import com.example.pique.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _specialProducts = MutableStateFlow<Resource<List<Products>>>(Resource.Unspecified())
    val specialProducts:StateFlow<Resource<List<Products>>> = _specialProducts

    private val _bestDeals = MutableStateFlow<Resource<List<Products>>>(Resource.Unspecified())
        val bestDeals:StateFlow<Resource<List<Products>>> = _bestDeals

    private val _bestProducts = MutableStateFlow<Resource<List<Products>>>(Resource.Unspecified())
        val bestProducts:StateFlow<Resource<List<Products>>> = _bestProducts

    private val pagingInfo = PagingInfo()

    init {
        fetchSpecialProducts()
        fetchBestDeals()
        fetchBestProducts()
    }

    fun fetchSpecialProducts(){
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }

        firestore.collection("Products")
            .whereEqualTo("category","SpecialProducts").get().addOnSuccessListener {result->
                val specialProductsList = result.toObjects(Products::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductsList))
                }
        }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message.toString()))
                }
        }
    }

    fun fetchBestDeals(){
        viewModelScope.launch {
            _bestDeals.emit(Resource.Loading())
        }

        firestore.collection("Products")
            .whereEqualTo("category","BestDeals").get().addOnSuccessListener {result->
                val bestDealsList = result.toObjects(Products::class.java)
                viewModelScope.launch {
                    _bestDeals.emit(Resource.Success(bestDealsList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDeals.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun fetchBestProducts(){
        if (!pagingInfo.isPageingEnd) {
            viewModelScope.launch {
                _bestProducts.emit(Resource.Loading())
            }

            firestore.collection("Products")
                .limit(pagingInfo.bestProductsPage * 10)
                .get()
                .addOnSuccessListener { result ->
                    val bestProductsList = result.toObjects(Products::class.java)
                    pagingInfo.isPageingEnd = bestProductsList == pagingInfo.oldBestProducts
                    pagingInfo.oldBestProducts = bestProductsList
                    viewModelScope.launch {
                        _bestProducts.emit(Resource.Success(bestProductsList))
                    }
                    pagingInfo.bestProductsPage++
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _bestProducts.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
    }

    internal data class PagingInfo(
        var bestProductsPage: Long =1,
        var oldBestProducts: List<Products> = emptyList(),
        var isPageingEnd: Boolean = false
    )
}
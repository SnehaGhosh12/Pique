package com.example.pique.fragments.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pique.data.Category
import com.example.pique.util.Resource
import com.example.pique.viewmodel.CategoryViewModel
import com.example.pique.viewmodel.factory.BaseCategoryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AppliancesCategoryFragment:BaseCategoryFragment() {


    @Inject
    lateinit var firestore: FirebaseFirestore

    val viewModel by viewModels<CategoryViewModel>{
        BaseCategoryViewModelFactory(firestore, Category.Appliances)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.offerProducts.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        showOfferLoading()
                    }
                    is Resource.Success ->{
                        offerAdapter.differ.submitList(it.data)
                        hideOfferLoading()
                    }
                    is Resource.Error ->{
                        hideOfferLoading()
                        Snackbar.make(requireView(),it.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        showBestProductLoading()
                    }
                    is Resource.Success ->{
                        bestProductsAdapter.differ.submitList(it.data)
                        hideBestProductLoading()
                    }
                    is Resource.Error ->{
                        hideBestProductLoading()
                        Snackbar.make(requireView(),it.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}
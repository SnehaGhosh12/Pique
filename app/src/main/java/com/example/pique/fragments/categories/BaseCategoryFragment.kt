package com.example.pique.fragments.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pique.R
import com.example.pique.adapters.BestDealsAdapter
import com.example.pique.adapters.BestProductsAdapter
import com.example.pique.databinding.FragmentBaseCategoryBinding

open class BaseCategoryFragment : Fragment() {

    private lateinit var binding: FragmentBaseCategoryBinding
    protected val offerAdapter: BestProductsAdapter by lazy { BestProductsAdapter() }
    protected val bestProductsAdapter: BestProductsAdapter by lazy { BestProductsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpOfferRv()
        setUpBestProductsRv()

        bestProductsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

        offerAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

    }

    private fun setUpOfferRv() {

        binding.rvOffers.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = offerAdapter
        }
    }

    private fun setUpBestProductsRv() {

        binding.rvBestProduct.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
            adapter = bestProductsAdapter
        }
    }

    fun showOfferLoading(){
        binding.offersProgressBar.visibility = View.VISIBLE
    }
    fun hideOfferLoading(){
        binding.offersProgressBar.visibility = View.GONE
    }
    fun showBestProductLoading(){
        binding.bestProductsProgressBar.visibility = View.VISIBLE
    }
    fun hideBestProductLoading(){
        binding.bestProductsProgressBar.visibility = View.GONE
    }


}
package com.example.pique.fragments.shopping

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pique.R
import com.example.pique.activities.PaymentActivity
import com.example.pique.adapters.AddressAdapter
import com.example.pique.adapters.BillingProductsAdapter
import com.example.pique.data.Address
import com.example.pique.data.CartProduct
import com.example.pique.data.order.Order
import com.example.pique.data.order.OrderStatus
import com.example.pique.databinding.FragmentBillingBinding
import com.example.pique.util.HorizontalItemDecoration
import com.example.pique.util.Resource
import com.example.pique.viewmodel.BillingViewModel
import com.example.pique.viewmodel.OrderViewModel
import com.google.android.material.snackbar.Snackbar
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.Serializable


@AndroidEntryPoint
class BillingFragment: Fragment() {
    private lateinit var binding: FragmentBillingBinding
    private val addressAdapter by lazy { AddressAdapter() }
    private val billingAdapter by lazy { BillingProductsAdapter() }
    private val billingViewModel by viewModels<BillingViewModel>()
    private val args by navArgs<BillingFragmentArgs>()
    private var products = emptyList<CartProduct>()
    private var totalPrice = 0f

    private var selectedAddress: Address? =null
    private val orderViewModel by viewModels<OrderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        products = args.products.toList()
        totalPrice = args.totalPrice

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBillingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBillingProductsRv()
        setUpAdressRv()

        binding.imageAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_billingFragment_to_addressFragment3)
        }


        lifecycleScope.launchWhenStarted {
            billingViewModel.address.collectLatest {
                when(it){
                    is Resource.Loading->{
                        binding.progressbarAddress.visibility = View.VISIBLE
                    }
                    is Resource.Success->{
                        binding.progressbarAddress.visibility = View.INVISIBLE
                        addressAdapter.differ.submitList(it.data)

                    }
                    is Resource.Error->{
                        binding.progressbarAddress.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(),"Error ${it.message.toString()}",Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            orderViewModel.order.collectLatest {
                when(it){
                    is Resource.Loading->{
                        binding.buttonPlaceOrder.startAnimation()
                    }
                    is Resource.Success->{
                        binding.buttonPlaceOrder.revertAnimation()
                        findNavController().navigateUp()
                        Snackbar.make(requireView(),"Your order was placed",Snackbar.LENGTH_LONG).show()

                    }
                    is Resource.Error->{
                        binding.buttonPlaceOrder.revertAnimation()
                        Toast.makeText(requireContext(),"Error ${it.message.toString()}",Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        billingAdapter.differ.submitList(products)
        binding.tvTotalPrice.text = "₹ ${totalPrice.toFloat()}"
        addressAdapter.onClick= {
            selectedAddress = it
        }

        binding.buttonPlaceOrder.setOnClickListener {
            if (selectedAddress == null){
                Toast.makeText(requireContext(),"Please select an address",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showOrderConfirmationDialog()
        }
    }

    private fun showOrderConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Order items")
            setMessage("Do you want to order your cart items")
            setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
            setPositiveButton("Yes"){dialog, _ ->
                val order = Order(OrderStatus.Ordered.status,
                    totalPrice,
                    products,
                    selectedAddress!!)
//                orderViewModel.placeOrder(order)
                val price = totalPrice.toString()
                val orderid = order.orderId.toString()

                val intent = Intent(activity,PaymentActivity::class.java)

                intent.putExtra("totalcost",price)
//                intent.putExtra("orderid",orderid)
                intent.putExtra("order",order)

                startActivity(intent)
                dialog.dismiss()

            }
        }
        alertDialog.create()
        alertDialog.show()
    }


    private fun setUpAdressRv() {
        binding.rvAddress.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            adapter = addressAdapter
            addItemDecoration(HorizontalItemDecoration())
        }
    }

    private fun setUpBillingProductsRv() {
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            adapter = billingAdapter
            addItemDecoration(HorizontalItemDecoration())
        }
    }


}
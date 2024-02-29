package com.example.pique.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pique.data.Address
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    fun addAddress(address: Address){

    }
}
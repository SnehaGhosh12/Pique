package com.example.pique.data

data class CartProduct(
    val products: Products,
    val quantity:Int,
    val selectedColor: Int? = null,
    val selectedSize: String? = null
){
    constructor(): this(Products(),1,null,null)
}

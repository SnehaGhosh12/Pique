package com.example.pique.data

sealed class Category(val category: String){
    object Laptop: Category("Laptop")
    object Tv: Category("Tv")
    object MobileAccessories: Category("MobileAccessories")
    object Watch: Category("Watch")
    object Appliances: Category("Appliances")
}

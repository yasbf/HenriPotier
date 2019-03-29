package com.ahmedbenfadhel.henripotier.ui.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedbenfadhel.henripotier.repository.BookRepository
import com.ahmedbenfadhel.henripotier.repository.DiscountRepository

class ViewModelFactoryBasket(private val bookRepository: BookRepository, private val discountRepository: DiscountRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BasketListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BasketListViewModel(bookRepository, discountRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.ahmedbenfadhel.henripotier.ui.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedbenfadhel.henripotier.repository.BookRepository

class ViewModelFactoryBookList(private val bookRepository: BookRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookListViewModel(bookRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
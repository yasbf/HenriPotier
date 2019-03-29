package com.ahmedbenfadhel.henripotier.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ahmedbenfadhel.henripotier.repository.BookRepository
import com.ahmedbenfadhel.henripotier.repository.DiscountRepository
import com.ahmedbenfadhel.henripotier.repository.local.BooksDatabase
import com.ahmedbenfadhel.henripotier.repository.remote.NetworkService
import com.ahmedbenfadhel.henripotier.ui.basket.ViewModelFactoryBasket
import com.ahmedbenfadhel.henripotier.ui.booklist.ViewModelFactoryBookList
import java.util.concurrent.Executors

object Injection {

    private fun provideBookRepo(context: Context): BookRepository {
        val database = BooksDatabase.getInstance(context)
        val service = NetworkService(context).service
        return BookRepository(service, database.bookDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideOfferRepo(context: Context): DiscountRepository {
        return DiscountRepository(NetworkService(context).service)
    }

    fun provideViewModelFactoryBookList(context: Context): ViewModelProvider.Factory {
        return ViewModelFactoryBookList(provideBookRepo(context))
    }

    fun provideViewModelFactoryBasketList(context: Context): ViewModelProvider.Factory {
        return ViewModelFactoryBasket(provideBookRepo(context), provideOfferRepo(context))
    }
}
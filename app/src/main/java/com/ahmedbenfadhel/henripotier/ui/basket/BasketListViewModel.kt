package com.ahmedbenfadhel.henripotier.ui.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.repository.BookRepository
import com.ahmedbenfadhel.henripotier.repository.DiscountRepository
import com.ahmedbenfadhel.henripotier.utils.OfferComparator

/**
 * ViewModel for the BasketActivity
 */
class BasketListViewModel(private val bookRepository: BookRepository, private val discountRepository: DiscountRepository) : ViewModel() {

    //list of all the books in the basket
    var bookList: LiveData<List<Book>> = bookRepository.getAllBasketBook()
    var price = MutableLiveData<Int>()
    var error = MutableLiveData<String>()

    /**
     * Add a book in basket
     */
    fun addBasket(book: Book) {
        if (book.nbInBasket < 1000) {
            bookRepository.updateBasket(book.isbn, book.nbInBasket + 1)
        }
    }

    /**
     * Remove a book in basket
     */
    fun removeBasket(book: Book) {
        if (book.nbInBasket > 0) {
            bookRepository.updateBasket(book.isbn, book.nbInBasket - 1)
        }
    }

    /**
     * Get the list of discounts for a list of books
     */
    fun getAllOffers(bookList: List<Book>) {
        discountRepository.getAllDiscounts(bookList, {
            price.postValue(OfferComparator.getBestOffer(it, OfferComparator.getPrice(bookList)))
        }, {
            error.postValue(it)
        })
    }
}
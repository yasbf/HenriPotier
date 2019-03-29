package com.ahmedbenfadhel.henripotier.ui.booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.repository.BookRepository

/**
 * ViewModel for the BookListActivity
 */
class BookListViewModel(private val bookRepository: BookRepository) : ViewModel() {

    //list of all the books
    var bookList : LiveData<List<Book>> = bookRepository.getAllBooks()

    /**
     * Add a book in basket
     */
    fun addBasket(book: Book?) {
        if (book != null && book.nbInBasket < 1000) {
            bookRepository.updateBasket(book.isbn, book.nbInBasket + 1)
        }
    }

    /**
     * Remove a book in basket
     */
    fun removeBasket(book: Book?) {
        if (book != null && book.nbInBasket > 0) {
            bookRepository.updateBasket(book.isbn, book.nbInBasket - 1)
        }
    }
}
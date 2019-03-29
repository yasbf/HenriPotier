package com.ahmedbenfadhel.henripotier.repository

import androidx.lifecycle.LiveData
import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.repository.local.BookDao
import com.ahmedbenfadhel.henripotier.repository.remote.HenriPotierService
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * Manage books sources of information (db or network)
 */
class BookRepository(private val bookListService: HenriPotierService, private val bookDao: BookDao, private val ioExecutor: Executor) {

    fun getAllBooks(): LiveData<List<Book>> {
        bookListService.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val books = response.body()
                if (books != null) {
                    val mutableIterator = books.iterator()

                    doAsync {
                        for (book in mutableIterator) {
                            bookDao.nbInBasket(book.isbn)
                        }
                        bookDao.insertAll(books)
                    }
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                // TODO Error handling
            }
        })
        return bookDao.getAllBooks()
    }

    /**
     * Get all the books in the basket
     */
    fun getAllBasketBook() : LiveData<List<Book>> {
        return bookDao.getAllBasketBooks()
    }

    /**
     * For a book, update the number in basket
     */
    fun updateBasket(isbn: String, nbInBasket: Int) {
        ioExecutor.execute {
            bookDao.updateBasket(isbn, nbInBasket)
        }
    }

}
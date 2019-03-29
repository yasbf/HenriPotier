package com.ahmedbenfadhel.henripotier.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmedbenfadhel.henripotier.model.Book

/**
 * DAO for books
 */
@Dao
interface BookDao{

    //Add a list of books
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bookList: List<Book>)

    //Get all the books
    @Query("SELECT isbn, title, price, cover, synopsis, nbInBasket FROM books")
    fun getAllBooks(): LiveData<List<Book>>

    //Get all the books in the basket
    @Query("SELECT isbn, title, price, cover, synopsis, nbInBasket FROM books WHERE nbInBasket != 0")
    fun getAllBasketBooks(): LiveData<List<Book>>

    //For a book isbn update the quantity in the basket
    @Query("UPDATE books SET nbInBasket = :nbInBasket WHERE isbn = :isbn")
    fun updateBasket(isbn : String, nbInBasket : Int)

    //For a book isbn get its quantity in the basket
    @Query("SELECT nbInBasket FROM books WHERE isbn = :isbn")
    fun nbInBasket(isbn : String): Int
}
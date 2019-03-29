package com.ahmedbenfadhel.henripotier.ui.booklist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedbenfadhel.henripotier.R
import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.ui.adapter.BookAdapter
import com.ahmedbenfadhel.henripotier.ui.basket.BasketActivity
import com.ahmedbenfadhel.henripotier.utils.Injection
import kotlinx.android.synthetic.main.activity_book_list.*

/**
 * Display the list of all books
 */
class BookListActivity : AppCompatActivity() {

    private lateinit var viewModelBookList: BookListViewModel

    private var adapter = BookAdapter(this, { view: View?, book: Book ->
        viewModelBookList.addBasket(book)
    }, { view: View?, book: Book ->
        viewModelBookList.removeBasket(book)
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        viewModelBookList = ViewModelProviders.of(this, Injection.provideViewModelFactoryBookList(this))
            .get(BookListViewModel::class.java)

        initAdapter()
    }

    private fun initAdapter() {
        bookList.layoutManager = LinearLayoutManager(applicationContext)
        bookList.adapter = adapter

        /**
         * Observe changes in the list of books (new books, update of the quantity)
         */
        viewModelBookList.bookList.observe(this, Observer<List<Book>> {
            adapter.submitList(it)
            if (it?.size == 0) {
                noBooksError.visibility = View.VISIBLE
                bookList.visibility = View.GONE
            } else {
                noBooksError.visibility = View.GONE
                bookList.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_checkout) {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

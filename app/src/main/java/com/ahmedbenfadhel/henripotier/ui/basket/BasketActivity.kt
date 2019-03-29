package com.ahmedbenfadhel.henripotier.ui.basket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedbenfadhel.henripotier.R
import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.ui.adapter.BookAdapter
import com.ahmedbenfadhel.henripotier.utils.Injection
import kotlinx.android.synthetic.main.activity_basket.*

/**
 * Display the list of books put in the basket by the user
 * Display the total price after applying the discount
 */
class BasketActivity : AppCompatActivity() {

    private lateinit var viewModelBasketList: BasketListViewModel

    private var adapter = BookAdapter(this, { view: View?, book: Book ->
        viewModelBasketList.addBasket(book)
    }, { view: View?, book: Book ->
        viewModelBasketList.removeBasket(book)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        //Observe books modification
        viewModelBasketList = ViewModelProviders.of(this, Injection.provideViewModelFactoryBasketList(this))
            .get(BasketListViewModel::class.java)

        //Observe price changes
        viewModelBasketList.price.observe(this, Observer<Int> {
            totalPriceTxt.text = resources.getString(R.string.total_price, it.toString())
        })

        //Observe network errors
        viewModelBasketList.error.observe(this, Observer<String> {
            totalPriceTxt.text = resources.getString(R.string.price_processing_error)
        })

        initAdapter()
    }

    private fun initAdapter() {
        basketList.layoutManager = LinearLayoutManager(applicationContext)
        basketList.adapter = adapter

        viewModelBasketList.bookList.observe(this, Observer<List<Book>> {
            adapter.submitList(it)
            if (it != null) {
                if (it.isNotEmpty()) {
                    totalPriceTxt.text = resources.getString(R.string.price_processing)
                    viewModelBasketList.getAllOffers(it)
                } else {
                    totalPriceTxt.text = resources.getString(R.string.empty_basket)
                }
            }
        })
    }
}
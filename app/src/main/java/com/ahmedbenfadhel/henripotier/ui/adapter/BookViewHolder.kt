package com.ahmedbenfadhel.henripotier.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmedbenfadhel.henripotier.R
import com.ahmedbenfadhel.henripotier.model.Book
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val bookTitle: AppCompatTextView = view.findViewById(R.id.bookTitle)
    private val priceTxt: AppCompatTextView = view.findViewById(R.id.bookPrice)
    private val ivCover: AppCompatImageView = view.findViewById(R.id.bookCover)
    private val ivAddBasket: AppCompatImageView = view.findViewById(R.id.addToBasket)
    private val ivRemoveBasket: AppCompatImageView = view.findViewById(R.id.removeFromBasket)
    private val basketNbTxt: AppCompatTextView = view.findViewById(R.id.bookQuantity)

    private var book: Book? = null


    fun bind(book: Book?, listenerAdd: (View?, Book) -> Unit, listenerRemove: (View?, Book) -> Unit) {
        if (book != null) {
            showBookData(book, listenerAdd, listenerRemove)
        }
    }

    private fun showBookData(book: Book, listenerAdd: (View?, Book) -> Unit, listenerRemove: (View?, Book) -> Unit) {
            this.book = book

        bookTitle.text = book.title
        priceTxt.text = priceTxt.context.getString(R.string.book_price, book.price.toString())

        Glide.with(ivCover.context)
            .load(book.cover)
            .apply(RequestOptions().placeholder(R.drawable.placeholder_book))
            .into(ivCover)

        ivAddBasket.setOnClickListener {
            listenerAdd(it, book)
        }

        ivRemoveBasket.setOnClickListener {
            listenerRemove(it, book)
        }

        basketNbTxt.text = book.nbInBasket.toString()
    }

    companion object {
        fun create(parent: ViewGroup): BookViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)
            return BookViewHolder(view)
        }
    }

}
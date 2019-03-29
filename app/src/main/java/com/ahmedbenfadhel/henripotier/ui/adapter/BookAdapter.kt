package com.ahmedbenfadhel.henripotier.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.ui.dialog.BookDetailDialog

class BookAdapter(private val context: Context, private val listenerAdd: (View?, Book) -> Unit, private val listenerRemove: (View?, Book) -> Unit) : ListAdapter<Book, RecyclerView.ViewHolder>(BOOK_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = getItem(position)
        if (book != null) {
            (holder as BookViewHolder).bind(book, listenerAdd, listenerRemove)
            holder.itemView.setOnClickListener {
                val ft = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                val prev = context.supportFragmentManager.findFragmentByTag(BookDetailDialog.TAG)

                if (prev != null) {
                    ft.remove(prev)
                }
                ft.addToBackStack(null)

                val dialog = BookDetailDialog.newInstance(book)
                dialog.show(ft, BookDetailDialog.TAG)
            }
        }
    }

    companion object {
        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.isbn == newItem.isbn

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem == newItem
        }
    }
}
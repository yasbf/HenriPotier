package com.ahmedbenfadhel.henripotier.ui.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.ahmedbenfadhel.henripotier.R
import com.ahmedbenfadhel.henripotier.model.Book
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.dialog_book_detail.*

/**
 * Dialog to show book details
 */
class BookDetailDialog : AppCompatDialogFragment() {
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        book = arguments!!.getParcelable(EXTRA_BOOK)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide
            .with(bookCover.context)
            .load(book.cover)
            .apply(RequestOptions().placeholder(R.drawable.placeholder_book))
            .into(bookCover)

        bookTitle.text = book.title
        bookSynopsis.text = TextUtils.join("\n\n", book.synopsis)
        closeDetailsBtn.setOnClickListener { v1 -> dismiss() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_book_detail, container, true)
    }

    companion object {

        var TAG = "BookDetail"

        var EXTRA_BOOK = "BOOK"

        fun newInstance(book: Book): BookDetailDialog {
            val f = BookDetailDialog()
            val args = Bundle()
            args.putParcelable(EXTRA_BOOK, book)
            f.setArguments(args)

            return f
        }
    }
}

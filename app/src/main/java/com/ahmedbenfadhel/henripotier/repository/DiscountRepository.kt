package com.ahmedbenfadhel.henripotier.repository

import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.model.DiscountResponse
import com.ahmedbenfadhel.henripotier.repository.remote.HenriPotierService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Manage discounts sources of information (only network)
 */
class DiscountRepository(private val discountService: HenriPotierService){

    fun getAllDiscounts(bookList: List<Book>, successDiscountsResponse : (DiscountResponse) -> Unit, failedDiscountsResponse : (String) -> Unit) {
        val isbns: MutableList<String> = ArrayList()
        val mutableIterator = bookList.iterator()
        for (book in mutableIterator) {
            isbns.add(book.isbn)
        }

        if (isbns.isNotEmpty()) {
            discountService.getDiscounts(isbns.joinToString()).enqueue(object : Callback<DiscountResponse> {
                override fun onResponse(call: Call<DiscountResponse>, response: Response<DiscountResponse>) {
                    successDiscountsResponse(response.body()!!)
                }

                override fun onFailure(call: Call<DiscountResponse>, t: Throwable) {
                    failedDiscountsResponse(t.localizedMessage)
                }
            })
        }
    }
}


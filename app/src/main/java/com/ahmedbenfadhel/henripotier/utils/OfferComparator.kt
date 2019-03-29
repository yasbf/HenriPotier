package com.ahmedbenfadhel.henripotier.utils

import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.model.DiscountResponse

object OfferComparator {

    /**
     * Get the price for a given full price and a list of discount
     */
    fun getBestOffer(discount: DiscountResponse?, price: Int): Int {
        var bestDiscount = 0
        discount?.offers?.let { list ->
            for (discount in list) {
                with(discount) {
                    bestDiscount = kotlin.math.max(
                        bestDiscount,
                        when (type) {
                            "percentage" -> price.times(value).div(100)
                            "minus" -> value
                            "slice" -> price.div(sliceValue).times(value)
                            else -> 0
                        }
                    )
                }
            }
        }
        return price - bestDiscount
    }

    /**
     * Get the full price for a list of books
     */
    fun getPrice(bookList: List<Book>): Int {
        var totalPrice = 0
        val mutableIterator = bookList.iterator()
        for (book in mutableIterator) {
            totalPrice += (book.price * book.nbInBasket)
        }
        return totalPrice
    }
}
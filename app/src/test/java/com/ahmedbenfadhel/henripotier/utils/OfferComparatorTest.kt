package com.ahmedbenfadhel.henripotier.utils

import com.ahmedbenfadhel.henripotier.model.Discount
import com.ahmedbenfadhel.henripotier.model.DiscountResponse
import org.junit.Test

import org.junit.Assert.*

class OfferComparatorTest {

    @Test
    fun getBestPrice1() {
        val discount1 = Discount("percentage", 4, 0)
        val discount2 = Discount("minus", 15, 0)
        val discount3 = Discount("slice", 12, 100)

        val discounts = DiscountResponse(listOf(discount1, discount2, discount3))

        assertEquals(85, OfferComparator.getBestOffer(discounts, 100))
    }

    @Test
    fun getBestPrice2() {
        val discount1 = Discount("percentage", 4, 0)
        val discounts = DiscountResponse(listOf(discount1))
        assertEquals(144, OfferComparator.getBestOffer(discounts, 150))
    }

    @Test
    fun getBestPrice3() {
        val discount1 = Discount("percentage", 4, 0)
        val discount2 = Discount("minus", 15, 0)
        val discount3 = Discount("slice", 12, 100)

        val discounts = DiscountResponse(listOf(discount1, discount2, discount3))
        assertEquals(50, OfferComparator.getBestOffer(discounts, 65))
    }

    @Test
    fun getBestPrice4() {
        val discount1 = Discount("percentage", 4, 0)
        val discount2 = Discount("minus", 15, 0)
        val discount3 = Discount("slice", 12, 100)

        val discounts = DiscountResponse(listOf(discount1, discount2, discount3))
        assertEquals(206, OfferComparator.getBestOffer(discounts, 230))
    }
}
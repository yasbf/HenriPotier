package com.ahmedbenfadhel.henripotier.repository.remote

import com.ahmedbenfadhel.henripotier.model.Book
import com.ahmedbenfadhel.henripotier.model.DiscountResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HenriPotierService {

    /**
     * Get repos ordered by stars.
     */
    @GET("books")
    fun getBooks(): Call<List<Book>>

    /**
     * Get repos ordered by stars.
     */
    @GET("books/{isbns}/commercialOffers")
    fun getDiscounts(@Path("isbns") isbns: String): Call<DiscountResponse>
}
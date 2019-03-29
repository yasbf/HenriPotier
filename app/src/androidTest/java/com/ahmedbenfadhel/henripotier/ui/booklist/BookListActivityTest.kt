package com.ahmedbenfadhel.henripotier.ui.booklist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import com.ahmedbenfadhel.henripotier.utils.Injection
import org.junit.Test

import org.junit.Rule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.rule.ActivityTestRule
import com.ahmedbenfadhel.henripotier.ui.adapter.BookViewHolder
import org.jetbrains.anko.find
import com.ahmedbenfadhel.henripotier.R

@LargeTest
class BookListActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<BookListActivity> = ActivityTestRule(BookListActivity::class.java)

    @Test
    fun mainScenario() {
        if (getBooksCount() > 0) {
            onView(withId(R.id.bookList))
                .check(matches(isDisplayed()))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<BookViewHolder>(
                        0, AddRemoveBookAction.clickChildViewWithId(R.id.addToBasket))
                )
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<BookViewHolder>(
                        1, AddRemoveBookAction.clickChildViewWithId(R.id.addToBasket))
                )
            Thread.sleep(5000)
            onView(withId(R.id.action_checkout))
                .perform(click())
            Thread.sleep(5000)
        } else {
            onView(withId(R.id.noBooksError))
                .check(matches(isDisplayed()))
        }
    }

    private fun getBooksCount(): Int {
        Injection.provideViewModelFactoryBookList(mActivityRule.activity)
        val bookList = mActivityRule.activity.find<RecyclerView>(R.id.bookList)
        return bookList.adapter!!.itemCount
    }
}
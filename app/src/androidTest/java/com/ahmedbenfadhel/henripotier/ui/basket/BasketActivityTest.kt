package com.ahmedbenfadhel.henripotier.ui.basket

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.ahmedbenfadhel.henripotier.R
import com.ahmedbenfadhel.henripotier.ui.adapter.BookViewHolder
import com.ahmedbenfadhel.henripotier.ui.booklist.AddRemoveBookAction
import com.ahmedbenfadhel.henripotier.utils.Injection
import org.hamcrest.CoreMatchers.containsString
import org.jetbrains.anko.find
import org.junit.Rule
import org.junit.Test

class BasketActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<BasketActivity> = ActivityTestRule(BasketActivity::class.java)

    @Test
    fun mainScenario() {
        if (getBasketSize() > 0) {
            Espresso.onView(withId(R.id.basketList))
                .check(ViewAssertions.matches(isDisplayed()))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<BookViewHolder>(
                        0, AddRemoveBookAction.clickChildViewWithId(R.id.removeFromBasket))
                )
            Thread.sleep(5000)
        } else {
            Espresso.onView(withId(R.id.totalPriceTxt))
                .check(ViewAssertions.matches(isDisplayed()))
                .check(matches(withText(containsString("Your basket is empty :("))))
            Thread.sleep(5000)
        }
    }

    private fun getBasketSize(): Int {
        Injection.provideViewModelFactoryBookList(mActivityRule.activity)
        val bookList = mActivityRule.activity.find<RecyclerView>(R.id.basketList)
        return bookList.adapter!!.itemCount
    }
}
package com.ahmedbenfadhel.henripotier.ui.booklist

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher
import org.jetbrains.anko.find


object AddRemoveBookAction {

    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on add book to basket button."
            }

            override fun perform(uiController: UiController, view: View) {
                val button = view.find<AppCompatImageView>(id)
                button.performClick()
            }
        }
    }

}
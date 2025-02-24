package com.smurzik.videoplayer

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher
import org.junit.Test

class NavigationTest : BaseTest() {

    @Test
    fun test_navigation() {
        var videoTitleString = ""
        val videoListPage = VideoListPage()
        Thread.sleep(1000)
        videoListPage.run {
            recycler.run {
                viewInRecycler(0, videoTitle).perform(object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return isAssignableFrom(TextView::class.java)
                    }

                    override fun getDescription(): String {
                        return "getting text"
                    }

                    override fun perform(uiController: UiController?, view: View?) {
                        val textView = view as TextView
                        videoTitleString = textView.text.toString()
                    }
                })
                viewInRecycler(0, itemLayout).click()
            }
        }

        PlayerPage().run {
            Thread.sleep(1000)
            videoTitle.view().checkText(videoTitleString)
        }

        pressBack()

        videoListPage.run {
            recycler.run {
                viewInRecycler(0, videoTitle).checkText(videoTitleString)
            }
        }
    }
}
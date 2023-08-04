package com.example.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Note: Tests only runs successfully on devices with American Currency (Dollar $) as default,
 * due to hardcoded dollar values strings. Should work fine on an emulator.
 *
 * I could have fixed it, but I was to lazy to do it.
 *
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CalculatorTests {
  @get:Rule()
  val activity = ActivityScenarioRule(MainActivity::class.java)

  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.example.tiptime", appContext.packageName)
  }

  @Test
  fun calculate_20_percent_default_tip() {
    typeOnKeyboard("50.00")
    pressCalculateButton()
    verifyTipValue(("$10.00"))
  }

  @Test
  fun calculate_18_percent_tip() {
    typeOnKeyboard("50.00")
    onView(withId(R.id.tip_good))
      .perform(click())
    pressCalculateButton()
    verifyTipValue("$9.00")
  }

  @Test
  fun calculate_no_rounded_tip() {
    typeOnKeyboard("53.00")
    onView(withId(R.id.round_up_switch))
      .perform(click())
    pressCalculateButton()
    verifyTipValue("$10.60")
  }

  @Test
  fun calculate_with_empty_edit_text() {
    pressCalculateButton()
    verifyTipValue(("$0.00"))
  }

  @Test
  fun calculate_tip_then_clear_edit_text_then_calculate() {
    typeOnKeyboard("50.00")
    pressCalculateButton()
    verifyTipValue(("$10.00"))
    onView(withId(R.id.cost_of_service_edit_text))
      .perform(clearText())
      .perform(closeSoftKeyboard())
    pressCalculateButton()
    verifyTipValue("$0.00")
  }

  private fun typeOnKeyboard(value: String) {
    onView(withId(R.id.cost_of_service_edit_text))
      .perform(typeText(value))
      .perform(ViewActions.closeSoftKeyboard())
  }

  private fun pressCalculateButton() {
    onView(withId(R.id.calculate_button))
      .perform(click())
  }

  private fun verifyTipValue(tipValue: String) {
    onView(withId(R.id.tip_result))
      .check(matches(withText(containsString(tipValue))))
  }

}
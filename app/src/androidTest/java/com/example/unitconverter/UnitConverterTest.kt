package com.example.unitconverter

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UnitConverterTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.unitconverter", appContext.packageName)
    }

    @Test
    fun testBasicConversion() {
        composeTestRule.setContent {
            UnitConverter()
        }

        // Enter input value
        composeTestRule.onNodeWithTag("inputValueTextField").performTextInput("10")

        // Select input unit (e.g., Meters)
        composeTestRule.onNodeWithTag("inputUnitButton").performClick() // Assuming "Meters" is the default
        composeTestRule.onNodeWithText("Centimeters").performClick() // Select "Centimeters"
        composeTestRule.onNodeWithTag("inputUnitButton").assertTextEquals("Centimeters")

        // Select output unit (e.g., Feet)
        composeTestRule.onNodeWithTag("outputUnitButton").performClick() // Assuming "Meters" is the default
        composeTestRule.onNodeWithText("Feet").performClick() // Select "Feet"
        composeTestRule.onNodeWithTag("outputUnitButton").assertTextEquals("Feet")


        // Assert the result
        composeTestRule.onNodeWithText("Result: 0.32 Feet").assertIsDisplayed()
    }

    @Test
    fun testInvalidInput() {
        composeTestRule.setContent {
            UnitConverter()
        }

        // Enter invalid input
        composeTestRule.onNodeWithTag("inputValueTextField").performTextInput("abc")

        // Assert the result
        composeTestRule.onNodeWithText("Result: 0.0 Meters").assertIsDisplayed()

    }
}
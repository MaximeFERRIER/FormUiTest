package fr.maxime.formuitest

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.maxime.formuitest.form.FormScreen
import fr.maxime.formuitest.form.Tags
import fr.maxime.formuitest.ui.theme.FormUiTestTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import kotlin.math.pow

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeRule = createComposeRule()

   @Test
   fun test_fill_form() {
       val height = 175.0
       val weight = 75.5
       val result = (weight / height.div(100).pow(2))

        composeRule.setContent {
            FormUiTestTheme {
                FormScreen()
            }
        }

       composeRule.onNodeWithTag(Tags.HeightEditText.tag).apply {
           performClick()
           assertIsFocused()
           performTextInput(height.toString())
           performImeAction()
           assertIsNotFocused()
       }

       composeRule.onNodeWithTag(Tags.WeightEditText.tag).apply {
           assertIsFocused()
           performTextInput(weight.toString())
           performImeAction()
           assertIsNotFocused()
       }

       composeRule.onNodeWithTag(Tags.BMIText.tag).apply {
           assert(hasText("BMI : $result"))
       }
   }
}
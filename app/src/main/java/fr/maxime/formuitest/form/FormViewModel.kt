package fr.maxime.formuitest.form

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.pow
import kotlin.math.roundToLong

class FormViewModel: ViewModel() {
    private val _bmiState = MutableStateFlow(0.0)
    val bmiState = _bmiState

    fun computeBmi(height: Double, weight: Double) {
        _bmiState.value = (weight / height.div(100).pow(2))
    }
}
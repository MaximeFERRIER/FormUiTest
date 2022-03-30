package fr.maxime.formuitest.form

sealed class Tags(val tag: String) {
    object HeightEditText: Tags("height_edit_text")
    object WeightEditText: Tags("weight_edit_text")
    object ValidateButton: Tags("validate_button")
    object BMIText: Tags("bmi_text")
}
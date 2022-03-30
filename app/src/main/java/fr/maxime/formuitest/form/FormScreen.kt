package fr.maxime.formuitest.form

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.maxime.formuitest.R

@Composable
fun FormScreen(viewModel: FormViewModel = viewModel()) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { FormAppBar() }
    ) {
        val height = remember { mutableStateOf(TextFieldValue()) }
        val weight = remember { mutableStateOf(TextFieldValue()) }
        val bmi by viewModel.bmiState.collectAsState()

        LazyColumn(modifier = Modifier.fillMaxSize(), state = rememberLazyListState()) {
            item {
                EditText(
                    modifier = Modifier.fillParentMaxWidth(),
                    data = height,
                    hint = "Entrez votre taille (cm)",
                    tag = Tags.HeightEditText.tag
                )
            }
            item {
                EditText(
                    modifier = Modifier.fillParentMaxWidth(),
                    data = weight,
                    hint = "Entrez votre poids (kg)",
                    isLast = true,
                    tag = Tags.WeightEditText.tag,
                    lastAction = {
                        viewModel.computeBmi(
                            height = height.value.text.toDouble(),
                            weight = weight.value.text.toDouble()
                        )
                    }
                )
            }
            item {
                Button(
                    modifier = Modifier.padding(16.dp).testTag(Tags.ValidateButton.tag),
                    onClick = {
                        viewModel.computeBmi(
                            height = height.value.text.toDouble(),
                            weight = weight.value.text.toDouble()
                        )
                    }) {
                    Text(
                        text = "Valider"
                    )
                }
            }
            item {
                Text(modifier = Modifier.padding(16.dp).testTag(Tags.BMIText.tag), text = "BMI : $bmi")
            }
        }
    }
}

@Composable
private fun FormAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h5
            )
        },
        elevation = 8.dp
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun EditText(
    modifier: Modifier,
    data: MutableState<TextFieldValue>,
    hint: String,
    tag: String,
    isLast: Boolean = false,
    lastAction: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp).testTag(tag),

        value = data.value,
        onValueChange = { data.value = it },
        placeholder = { Text(text = hint) },
        isError = (((data.value.text.toDoubleOrNull() ?: 0.0) <= 0.0)),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = if (isLast) ImeAction.Done else ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            },
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
                lastAction?.let {
                    it()
                }
            }
        )
    )
}
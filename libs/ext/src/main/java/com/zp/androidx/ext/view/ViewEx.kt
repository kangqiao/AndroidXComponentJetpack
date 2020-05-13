package com.zp.androidx.ext.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.height
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextDecoration
import androidx.ui.unit.dp
import androidx.ui.unit.ipx
import androidx.ui.unit.sp
import timber.log.Timber


/**
 * 自定义支持Compose的FrameLayout
 */
class ComposeFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        setContent {
            Button(onClick = {}) {
                Text("ComposeButton")
            }
        }
    }
}

val fontSize4 = 16.sp
val fontSize6 = 20.sp
val fontSize8 = 25.sp
val fontSize10 = 30.sp

@Composable
fun HintEditText2(
    hintText: String = "",
    modifier: Modifier = Modifier.None,
    textStyle: TextStyle = currentTextStyle()
) {
    val state = state { TextFieldValue("") }
    val inputField = @Composable {
        TextField(
            value = state.value,
            modifier = modifier,
            onValueChange = {
                Timber.d("zhaopan>>> " + it)
                state.value = it
            },
            textStyle = textStyle.merge(TextStyle(textDecoration = TextDecoration.None))
        )
    }

    Layout(
        children = @Composable {
            inputField()
            Text(
                text = hintText,
                modifier = modifier,
                style = textStyle.merge(TextStyle(color = Color.Gray))
            )
            Divider(color = Color.Black, modifier = Modifier.height(2.dp))
        },
        measureBlock = { measurables: List<Measurable>, constraints: Constraints, _ ->
            val inputFieldPlace = measurables[0].measure(constraints)
            val hintEditPlace = measurables[1].measure(constraints)
            val dividerEditPlace = measurables[2].measure(
                Constraints(constraints.minWidth, constraints.maxWidth, 2.ipx, 2.ipx)
            )
            layout(
                inputFieldPlace.width,
                inputFieldPlace.height + dividerEditPlace.height
            ) {
                inputFieldPlace.place(0.ipx, 0.ipx)
                if (state.value.text.isEmpty())
                    hintEditPlace.place(0.ipx, 0.ipx)
                dividerEditPlace.place(0.ipx, inputFieldPlace.height)
            }
        })
}

@Composable
fun HintEditText(hintText: @Composable() () -> Unit) {
    val state = state { TextFieldValue() }

    val inputField = @Composable {
        TextField(
            modifier = Modifier.tag("inputField"),
            value = state.value,
            onValueChange = { state.value = it },
            textStyle = TextStyle(fontSize = fontSize8)
        )
    }

    if (state.value.text.isNotEmpty()) {
        inputField()
    } else {
        Layout({
            inputField()
            Box(Modifier.tag("hintText"), children = hintText)
        }) { measurable, constraints, _ ->
            val inputFieldPlacable =
                measurable.first { it.tag == "inputField" }.measure(constraints)
            val hintTextPlacable = measurable.first { it.tag == "hintText" }.measure(constraints)
            layout(inputFieldPlacable.width, inputFieldPlacable.height) {
                inputFieldPlacable.place(0.ipx, 0.ipx)
                hintTextPlacable.place(0.ipx, 0.ipx)
            }
        }
    }
}
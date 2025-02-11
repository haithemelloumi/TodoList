package com.helloumi.ui.features.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.helloumi.domain.model.response.TodoResponse
import com.helloumi.ui.theme.Dimens.INLINE_SM
import com.helloumi.ui.R

@Composable
fun AddTodo(
    modifier: Modifier,
    onClickAdd: (TodoResponse.Todo) -> Unit
) {
    var textValue by remember { mutableStateOf("") }
    Row(
        modifier = modifier
    ) {
        TextField(
            value = textValue,
            onValueChange = {
                textValue = it
            },
            label = { Text(stringResource(id = R.string.new_todo)) }
        )

        IconButton(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = INLINE_SM),
            onClick = {
                if (textValue.isNotEmpty()) {
                    onClickAdd(TodoResponse.Todo(textValue))
                } else {
                    // TODO
                }
            }
        ) {
            Icon(Icons.Filled.Add, contentDescription = "add")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTodoPreview() {
    AddTodo(Modifier) {}
}

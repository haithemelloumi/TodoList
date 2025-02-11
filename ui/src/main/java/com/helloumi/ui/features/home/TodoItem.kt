package com.helloumi.ui.features.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.helloumi.domain.model.response.TodoResponse.Todo
import com.helloumi.todolist.ui.theme.PurpleGrey40
import com.helloumi.ui.theme.Dimens
import com.helloumi.ui.theme.Dimens.ITEM_HEIGHT


@Composable
fun TodoItem(
    todo: Todo,
    onUpdateCompleted: (Todo) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.ROUNDED_SHAPE_SMALL),
        colors = CardDefaults.cardColors(
            containerColor = PurpleGrey40,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.INLINE_SM)
        ) {
            Text(
                text = todo.todo,
                modifier = Modifier
                    .height(ITEM_HEIGHT)
                    .weight(0.8f)
                    // Center Text Vertically
                    .wrapContentHeight(align = Alignment.CenterVertically),
                color = Color.Black,
                style = TextStyle(
                    fontSize = Dimens.TEXT_SIZE_MEDIUM,
                    fontWeight = FontWeight.Bold
                ),
                textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )

            var checked by remember { mutableStateOf(true) }
            Switch(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight(),
                checked = !todo.isCompleted,
                onCheckedChange = {
                    checked = it
                    onUpdateCompleted(todo)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    val todo = Todo(true, "Test Todo")
    TodoItem(todo) {}
}

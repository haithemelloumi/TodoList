package com.helloumi.ui.features.home

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helloumi.domain.model.response.TodoResponse
import com.helloumi.domain.model.result.TodoResult
import com.helloumi.todolist.ui.theme.Purple40
import com.helloumi.ui.theme.Dimens.ADD_BUTTON_HEIGHT
import com.helloumi.ui.theme.Dimens.INLINE_SM
import com.helloumi.ui.theme.Dimens.STACK_MD
import com.helloumi.ui.theme.Dimens.STACK_SM
import com.helloumi.ui.R
import com.helloumi.ui.utils.extensions.displayToast

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    // Fetch todos only once when the composable is first displayed
    LaunchedEffect(Unit) {
        viewModel.callGetTodos()
    }

    val uiState by viewModel.getTodoResultMutableLiveData().observeAsState()
    val uiStateIsTodoAdded by viewModel.getIsAddedMutableLiveData().observeAsState()

    HomeScreenContent(
        modifier,
        uiState,
        uiStateIsTodoAdded,
        onClickAdd = { viewModel.addTodo(it) },
        updateTodo = { viewModel.updateTodo(it) },
        onClickRetry = { viewModel.callGetTodos() }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    uiState: TodoResult?,
    uiStateIsTodoAdded: Boolean?,
    onClickAdd: (TodoResponse.Todo) -> Unit,
    updateTodo: (TodoResponse.Todo) -> Unit,
    onClickRetry: () -> Unit,
) {

    //LazyList scroll position
    val scrollState = rememberLazyListState()

    //System UI state
    val systemUiController = rememberSystemUiController()

    val isDarkTheme = isSystemInDarkTheme()
    systemUiController.setStatusBarColor(
        color = Purple40,
        darkIcons =
        if (isDarkTheme) false
        else scrollState.firstVisibleItemScrollOffset != 0
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (uiState) {
            is TodoResult.Loading -> {
                CircularProgressIndicator()
            }

            is TodoResult.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = STACK_MD, vertical = STACK_SM)
                ) {
                    items(uiState.todoResponse.todos) {
                        TodoItem(it, updateTodo)
                        Spacer(modifier = Modifier.height(STACK_SM))
                    }
                }

                AddTodo(
                    Modifier
                        .fillMaxWidth()
                        .height(ADD_BUTTON_HEIGHT)
                        .padding(horizontal = STACK_MD, vertical = STACK_SM),
                    onClickAdd
                )
            }

            else -> {
                ErrorMessage(stringResource(id = R.string.server_unreachable))
                RetryButton(onClickRetry)
            }
        }

    }

    DisplayAddedTodoToast(uiStateIsTodoAdded)
}

@Composable
fun ErrorMessage(message: String) {
    Text(
        text = message,
        modifier = Modifier.padding(start = INLINE_SM, top = STACK_MD)
    )
}

@Composable
fun RetryButton(onClickRetry: () -> Unit) {
    Button(onClick = onClickRetry) {
        Text(text = stringResource(id = R.string.retry))
    }
}

@Composable
private fun DisplayAddedTodoToast(uiStateIsTodoAdded: Boolean?) {
    val context: Context = LocalContext.current
    if (uiStateIsTodoAdded != null) {
        context.displayToast(
            if (uiStateIsTodoAdded == true) R.string.success_added
            else R.string.failure_added
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(Modifier, TodoResult.ServerUnavailable, null, {}, {}, {})
}

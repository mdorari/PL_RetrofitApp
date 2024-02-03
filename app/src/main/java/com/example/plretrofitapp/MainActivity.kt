package com.example.plretrofitapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.widget.ContentLoadingProgressBar
import com.example.plretrofitapp.ui.theme.PLRetrofitAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PLRetrofitAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoList()
                }
            }
        }
    }
}

@Composable
fun TodoList(todoViewModel: TodoViewModel = TodoViewModel(), modifier: Modifier = Modifier) {

    val todoList by todoViewModel.allTodos.collectAsState()
    val totalTodos = todoList.size
    val completedTodosList by todoViewModel.completedTodos.collectAsState(initial = emptyList())
    val completedTodos = completedTodosList.size

    val percentage by todoViewModel.percentageCompleted.collectAsState(initial = 0f)


    Column(modifier = Modifier.padding(start = 16.dp, top = 32.dp)) {
        Row {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(end = 8.dp, top = 16.dp)
                    .size(32.dp),
                progress = percentage,
                color = Color.Red,
                trackColor = Color.LightGray,
                strokeWidth = 3.dp
            )
            Column {
                Text(
                    text = "My Tasks",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
                Text(
                    text = "$completedTodos of $totalTodos tasks",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(2.dp)
                        .background(Color.LightGray)
                )
            }
        }
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(todoList) { todo ->
                TodoItem(todo = todo)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

@Composable
fun TodoItem(todo: Todo) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.width(24.dp)) {
            if (todo.completed) {
                Icon(
                    painter = painterResource(id = R.drawable.empty_checkbox),
                    contentDescription = "check as complete"
                )
            }
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = todo.title,
                textDecoration = if (!todo.completed) TextDecoration.LineThrough else TextDecoration.None,
                color = if (todo.completed) Color.Black else Color.Red
            )
            Text(text = todo.userId.toString(), color = Color.LightGray)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PLRetrofitAppTheme {
        TodoList()
    }
}
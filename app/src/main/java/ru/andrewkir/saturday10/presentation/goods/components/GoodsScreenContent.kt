import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import ru.andrewkir.saturday10.presentation.goods.components.NoteCard
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsState

@Composable
fun GoodsScreenContent(
  uiState: GoodsState,
  onEvent: (GoodsEvent) -> Unit,
) {
  var isRefreshing by remember { mutableStateOf(false) }

  // trigger fetch after swipe
  LaunchedEffect(isRefreshing) {
    if (isRefreshing) {
      onEvent(GoodsEvent.FetchButtonClicked)
      isRefreshing = false
    }
  }
  SwipeRefresh(
    state = SwipeRefreshState(isRefreshing),
    onRefresh = { isRefreshing = true }
  ) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    item {
    TextField(
      value = uiState.title,
      onValueChange = { changedValue -> onEvent(GoodsEvent.UpdateGoodsTextField(changedValue)) },
      label = { Text("Enter title") },
      modifier = Modifier.fillMaxWidth(),
      keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
      textStyle = TextStyle(fontWeight = FontWeight.Bold)
    )

    Spacer(modifier = Modifier.height(8.dp))

    TextField(
      value = uiState.body,
      onValueChange = { changedValue -> onEvent(GoodsEvent.UpdateGoodsUrlField(changedValue)) },
      label = { Text("Enter body") },
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Button(
      onClick = { onEvent(GoodsEvent.AddButtonClicked) },
      modifier = Modifier.fillMaxWidth()
    ) {
      Icon (
        imageVector = Icons.Default.Add,
        contentDescription = "Add"
      )
      Text("Add")
    }

    Spacer(modifier = Modifier.height(8.dp))
      }
        uiState.notes.forEach { item ->
          item {
            Spacer(modifier = Modifier.height(9.dp))
            NoteCard(item, onEvent)
            Spacer(modifier = Modifier.height(9.dp))
          }
        }
    }
  }
}

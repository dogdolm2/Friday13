package ru.andrewkir.saturday10.presentation.details.component
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ramcosta.composedestinations.annotation.Destination
import ru.andrewkir.saturday10.data.models.NoteModel
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent
import ru.andrewkir.saturday10.presentation.goods.viewmodels.GoodsViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Destination
@Composable
fun DetailsScreenContent(
  note: NoteModel
) {
  val viewModel: GoodsViewModel = viewModel()

  var titleDetails by remember { mutableStateOf(note.title) }
  var bodyDetails by remember { mutableStateOf(note.body) }
  val scrollState = rememberScrollState()
  Column(modifier = Modifier
    .fillMaxSize()
    .verticalScroll(scrollState)
    .padding(16.dp)) {
    TextField(
      value = titleDetails,
      onValueChange = {
        titleDetails = it
        viewModel.handleEvent(GoodsEvent.UpdateNoteTextField(it))
      },
      label = { Text("Title") }
    )

    Spacer(modifier = Modifier.height(8.dp))

    TextField(
      value = bodyDetails,
      onValueChange = {
        bodyDetails = it
        viewModel.handleEvent(GoodsEvent.UpdateNoteUrlField(it))
      },
      label = { Text("Body") }
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(onClick = {
      viewModel.handleEvent(GoodsEvent.UpdateButtonClicked(NoteModel(note.title, note.id, note.body)))
    }) {
      Text("Update")
    }
  }
}

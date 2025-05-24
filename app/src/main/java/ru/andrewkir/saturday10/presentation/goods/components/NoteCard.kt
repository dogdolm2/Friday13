package ru.andrewkir.saturday10.presentation.goods.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import ru.andrewkir.saturday10.data.models.NoteModel
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent

@Composable
fun NoteCard(
  noteModel: NoteModel,
  onEvent: (GoodsEvent) -> Unit,
) {
  ElevatedCard(
    onClick = {
      onEvent(GoodsEvent.OnNoteItemClick(noteModel))
    }
  ) {
    Spacer(modifier = Modifier.height(9.dp))

    Text(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      text = noteModel.title,
      fontSize = 32.sp,
      fontWeight = FontWeight.Bold
    )
    Text(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      text = noteModel.body,
      fontSize = 16.sp
    )

    Text(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      text = "ID: ${noteModel.id}",
      fontSize = 8.sp
    )
    Spacer(modifier = Modifier.height(1.dp))
  }
}

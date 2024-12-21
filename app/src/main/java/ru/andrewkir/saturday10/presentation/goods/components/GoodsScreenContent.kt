package ru.andrewkir.saturday10.presentation.goods.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.andrewkir.saturday10.R
import ru.andrewkir.saturday10.data.models.GoodsItemModel
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsState

@Composable
fun GoodsScreenContent(
  uiState: GoodsState,
  onEvent: (GoodsEvent) -> Unit,
) {
  Column {
    Column(
      modifier = Modifier.fillMaxWidth(),
    ) {
      Button(
        modifier = Modifier.padding(16.dp),
        onClick = {
          onEvent(GoodsEvent.AddButtonClicked)
        }) {
        Text(text = "Update users list")
      }
    }
    LazyColumn (
      modifier = Modifier.padding(horizontal = 16.dp)
    ){
      uiState.users.forEach { item ->
        item {
          Spacer(modifier = Modifier.height(9.dp))
          UserCard(item, onEvent)
          Spacer(modifier = Modifier.height(9.dp))
        }
      }
    }
  }
}

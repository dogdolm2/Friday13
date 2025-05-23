package ru.andrewkir.saturday10.presentation.goods.contract

import ru.andrewkir.saturday10.data.models.NoteModel

data class GoodsState(
  val notes: List<NoteModel> = emptyList(),
  var title: String,
  var body: String,
  var id: String
)
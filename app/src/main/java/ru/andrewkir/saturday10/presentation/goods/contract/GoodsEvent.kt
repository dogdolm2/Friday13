package ru.andrewkir.saturday10.presentation.goods.contract

import ru.andrewkir.saturday10.data.models.NoteModel

sealed class GoodsEvent {
  data class UpdateGoodsTextField(val title: String): GoodsEvent()
  data class UpdateGoodsUrlField(val body: String): GoodsEvent()
  data class OnNoteItemClick(val note: NoteModel): GoodsEvent()
  data class UpdateButtonClicked(val note: NoteModel): GoodsEvent()
  data class UpdateNoteTextField(val title: String): GoodsEvent()
  data class UpdateNoteUrlField(val body: String): GoodsEvent()
  data object FetchButtonClicked: GoodsEvent()
  data object AddButtonClicked: GoodsEvent()
}
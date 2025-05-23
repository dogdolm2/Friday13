package ru.andrewkir.saturday10.presentation.goods.contract

import ru.andrewkir.saturday10.data.models.NoteModel

sealed interface GoodsEffect {

  data class OpenDetails(val item: NoteModel): GoodsEffect
}
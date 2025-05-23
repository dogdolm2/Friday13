package ru.andrewkir.saturday10.presentation.goods.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.andrewkir.saturday10.App
import ru.andrewkir.saturday10.data.api.ApiExample
import ru.andrewkir.saturday10.data.db.Note
import ru.andrewkir.saturday10.data.models.NoteDetailsModel
import ru.andrewkir.saturday10.data.models.NoteModel
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEffect
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.AddButtonClicked
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.OnNoteItemClick
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.UpdateGoodsTextField
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.UpdateGoodsUrlField
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsState


class GoodsViewModel : ViewModel() {

  val state = MutableStateFlow(GoodsState(
      notes = emptyList(),
      title = "",
      body = "",
      id = ""
  ))
  val noteState = MutableStateFlow(NoteModel(id = "", title = "", body = ""))
  private val _effect = Channel<GoodsEffect>()
  val effect = _effect.receiveAsFlow()

  private fun getClient(): ApiExample {
    val httpClient = Builder()
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    httpClient.addInterceptor(logging)
    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .client(httpClient.build())
      .build()

    return retrofit.create(ApiExample::class.java)
  }

  fun handleEvent(event: GoodsEvent) {
    when (event) {
      is UpdateGoodsTextField -> {
        state.value = state.value.copy(title = event.title)
      }
      is OnNoteItemClick -> {
        viewModelScope.launch {
          val noteToReturn = NoteModel(
            id = event.note.id,
            title = event.note.title,
            body = event.note.body
          )
          Log.e("state", "[VM] Redirected to ID: ${noteToReturn.id}")
          _effect.send(GoodsEffect.OpenDetails(noteToReturn))
        }
      }

      is AddButtonClicked -> {
        Log.e("state", "[VM] Add process started")
//        val client = getClient()
        App.getDatabase()?.noteDao()?.insert(
              Note(
                  id = java.util.UUID.randomUUID().toString(),
                  title = state.value.title,
                  body = state.value.body
              )
          )
        App.getDatabase()?.noteDao()?.let { dao ->
          val notelist = dao.getAll()
          state.value = state.value.copy(
            notes = notelist.map {
              NoteModel(
                id = it.id,
                title = it.title,
                body = it.body
              )
            }
          )
        }
        state.value.body = ""
        state.value.title = ""
        Log.e("state", "[VM] Added")
      }

      is GoodsEvent.UpdateButtonClicked -> {
        Log.e("state", "[VM] Update started")
        viewModelScope.launch {
          Log.e("state", "[VM] Updating '${noteState.value.title}' with '${noteState.value.body}'")
          if (noteState.value.title != "" && noteState.value.body != "") {
            Log.e("state", "[VM] Updated to '${noteState.value.title}' with '${noteState.value.body}'")
            App.getDatabase()?.noteDao()?.update(
              Note(
                id = event.note.id,
                title = noteState.value.title,
                body = noteState.value.body
              )
            )
          } else if (noteState.value.title != "") {
            Log.e("state", "[VM] Updated to '${event.note.title}' with '${noteState.value.body}'")
            App.getDatabase()?.noteDao()?.update(
              Note(
                id = event.note.id,
                title = noteState.value.title,
                body = event.note.body
              )
            )
          } else if (noteState.value.body != "") {
            Log.e("state", "[VM] Updated to '${noteState.value.title}' with '${event.note.body}'")
            App.getDatabase()?.noteDao()?.update(
              Note(
                id = event.note.id,
                title = event.note.title,
                body = noteState.value.body
              )
            )
          } else {
            Log.e("state", "[VM] Updated to '${event.note.title}' with '${event.note.body}'")
            App.getDatabase()?.noteDao()?.update(
              Note(
                id = event.note.id,
                title = event.note.title,
                body = event.note.body
              )
            )
          }
        }
        Log.e("state", "[VM] Updated")
      }
      is GoodsEvent.UpdateNoteTextField -> {
        noteState.value = noteState.value.copy(title = event.title)
      }
      is GoodsEvent.UpdateNoteUrlField -> {
        noteState.value = noteState.value.copy(body = event.body)
      }
      is GoodsEvent.FetchButtonClicked -> {
        Log.e("state", "[VM] Fetch started")
        App.getDatabase()?.noteDao()?.let { dao ->
          val notelist = dao.getAll()
          state.value = state.value.copy(
            notes = notelist.map {
              NoteModel(
                id = it.id,
                title = it.title,
                body = it.body
              )
            }
          )
        }
        Log.e("state", "[VM] Fetched")
      }
      is UpdateGoodsUrlField -> {
        state.value = state.value.copy(body = event.body)
      }
    }
  }

  // if(isNumeric(event.text)) {
  //  state.value = ...
  // }

  fun isNumeric(toCheck: String): Boolean {
    return toCheck.all { char -> char.isDigit() }
  }
}
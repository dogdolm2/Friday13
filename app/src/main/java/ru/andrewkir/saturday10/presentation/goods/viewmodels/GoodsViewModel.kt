package ru.andrewkir.saturday10.presentation.goods.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.andrewkir.saturday10.App
import ru.andrewkir.saturday10.data.api.ApiExample
import ru.andrewkir.saturday10.data.db.User
import ru.andrewkir.saturday10.data.models.UserDetailsModel
import ru.andrewkir.saturday10.data.models.UserModel
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEffect
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.AddButtonClicked
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.OnUserItemClick
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.UpdateGoodsTextField
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent.UpdateGoodsUrlField
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsState


class GoodsViewModel : ViewModel() {

  val state = MutableStateFlow(GoodsState())

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
        state.value = state.value.copy(goodsName = event.text)
      }

      is OnUserItemClick -> {
        val client = getClient()
        viewModelScope.launch {
          val userFollowers = client.getFollowers(event.user.id)
          val userToReturn = UserModel(
            id = event.user.id,
            login = event.user.login,
            imageUrl = event.user.imageUrl,
            followersList = (
              userFollowers.map {
                UserDetailsModel(
                  id = it.id,
                  login = it.login,
                  imageUrl = it.imageUrl,
                  followersUrl = it.followersUrl
                )
              }
            )
          )
          _effect.send(GoodsEffect.OpenDetails(userToReturn))
        }
      }

      is AddButtonClicked -> {
        val client = getClient()

        viewModelScope.launch {
          try {
            App.getDatabase()?.userDao()?.let { dao ->
              val users = client.getUsers()
              users.forEach { user ->
                dao.insert(
                  User(
                    id = user.id,
                    login = user.login,
                    imageUrl = user.imageUrl
                  )
                )
              }
            }
          } catch (e: Exception) {
            e.printStackTrace()
          }
        }
        App.getDatabase()?.userDao()?.let { dao ->
          val userlist = dao.getAll()
          state.value = state.value.copy(
            users = userlist.map {
              UserModel(
                id = it.id,
                login = it.login,
                imageUrl = it.imageUrl,
                followersList = emptyList()
              )
            },
            goodsName = ""
          )
        }
      }

      is UpdateGoodsUrlField -> {
        state.value = state.value.copy(goodsUrl = event.url)
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
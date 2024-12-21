package ru.andrewkir.saturday10.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import ru.andrewkir.saturday10.data.models.UserModel
import ru.andrewkir.saturday10.data.models.UserDetailsModel

interface ApiExample {
  @GET("/users?since=<string>&per_page=30")
  suspend fun getUsers(): List<UserDetailsModel>

  @GET("/users/{userId}/followers")
  suspend fun getFollowers(
    @Path("userId") userId: String,
  ): List<UserDetailsModel>
}
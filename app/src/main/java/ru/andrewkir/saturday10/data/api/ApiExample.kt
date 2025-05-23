package ru.andrewkir.saturday10.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.andrewkir.saturday10.data.models.NoteDetailsModel

interface ApiExample {
  @GET("/users?since=<string>&per_page=30")
  suspend fun getUsers(): List<NoteDetailsModel>

  @GET("/users/{userId}/followers")
  suspend fun getFollowers(
    @Path("userId") userId: String,
  ): List<NoteDetailsModel>
}
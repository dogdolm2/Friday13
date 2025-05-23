package ru.andrewkir.saturday10.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDetailsModel(

  @SerializedName("title")
  val title: String,

  @SerializedName("id")
  val id: String,

  @SerializedName("body")
  val body: String

)

@Serializable
data class NoteModel(

  @SerializedName("title")
  val title: String,

  @SerializedName("id")
  val id: String,

  @SerializedName("body")
  val body: String
)
package ru.andrewkir.saturday10.data.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Database(entities = [Note::class], version = 1, exportSchema = true)
abstract class UsersDatabase : RoomDatabase() {
  abstract fun noteDao(): UserDao
}

@Entity
data class Note(
  @PrimaryKey
  val id: String,
  val title: String,
  val body: String
)

@Dao
interface UserDao {
  @Query("SELECT * FROM note")
  fun getAll(): List<Note>

  @Insert
  fun insert(note: Note)

  @Insert
  fun insertAll(notes: List<Note>)

  @Delete
  fun delete(note: Note)

  // Обновить только title по id
  @Query("UPDATE note SET title = :title WHERE id = :id")
  fun updateTitle(id: String, title: String)

  // Обновить только body по id
  @Query("UPDATE note SET body = :body WHERE id = :id")
  fun updateBody(id: String, body: String)

  @Update
  fun update(note: Note)
}
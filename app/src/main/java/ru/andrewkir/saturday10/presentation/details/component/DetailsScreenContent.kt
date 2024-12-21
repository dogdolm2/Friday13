package ru.andrewkir.saturday10.presentation.details.component
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ramcosta.composedestinations.annotation.Destination
import ru.andrewkir.saturday10.data.models.UserModel
import ru.andrewkir.saturday10.presentation.goods.components.UserCard


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalGlideComposeApi::class)
@Destination
@Composable
fun DetailsScreenContent(
  user: UserModel,
) {
  Column {
    Column(
      modifier = Modifier
        .padding(horizontal = 6.dp)
    ) {
      Spacer(Modifier.height(25.dp))
      Text("Hello, ${user.login}!", fontSize = 27.sp)
      Spacer(Modifier.height(35.dp))
      Text("FSB has discovered the following information about you:", fontSize = 20.sp)
      Spacer(Modifier.height(25.dp))
      Text("We know, how you look...", fontSize = 15.sp)
      Spacer(Modifier.height(10.dp))
      GlideImage(
        model = user.imageUrl,
        contentDescription = null,
        modifier = Modifier
          .size(300.dp),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
      )
      Spacer(Modifier.height(25.dp))
      Text("Your SOOBSH'NIKI: ", fontSize = 15.sp)
      LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
      ) {
        user.followersList.forEach { item ->
          item {
            Spacer(modifier = Modifier.height(9.dp))
            Text("Name: ${item.login}. ID: ${item.id}. We will find you too...")
            Spacer(modifier = Modifier.height(9.dp))
          }
        }
      }
    }
    Column(
      modifier = Modifier.padding(horizontal = 6.dp)
    ) {
      Spacer(modifier = Modifier.height(5.dp))
      HorizontalDivider(thickness = 2.dp)
      Spacer(modifier = Modifier.height(7.dp))
      Text("Everyone, who was not mentioned before this line, were eliminated.")
    }
  }
}
package ru.andrewkir.saturday10.presentation.goods.components

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.andrewkir.saturday10.data.models.UserModel
import ru.andrewkir.saturday10.presentation.goods.contract.GoodsEvent

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserCard(
  userModel: UserModel,
  onEvent: (GoodsEvent) -> Unit,
) {
  ElevatedCard(
    onClick = {
      onEvent(GoodsEvent.OnUserItemClick(userModel))
    }
  ) {
    Spacer(modifier = Modifier.height(9.dp))
    GlideImage(
      model = userModel.imageUrl,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(106.dp)
        .padding(start=8.dp)
        .clip(CircleShape)
    )

    Text(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      text = "Username: ${userModel.login}",
      fontSize = 24.sp
    )

    Text(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      text = "ID: ${userModel.id}",
      fontSize = 24.sp
    )
    Spacer(modifier = Modifier.height(1.dp))
  }
}

package com.imnstudios.hellojetpackcompose

import androidx.compose.runtime.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.util.*

class PuppyAdoption : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        viewModel.puppyList.observe(this, {
            setContent {
                MyTheme {
                    ScreenContent(it)
                }
            }
        })
    }
}

@Composable
fun MyTheme(content: @Composable () -> Unit) {
    MaterialTheme() {
        content()
    }
}

@Composable
fun ScreenContent(puppies: List<Puppy>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(items = puppies) { puppy ->
            Card(puppy)
        }

    }
}

@Composable
fun Card(puppy: Puppy) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    Glide.with(LocalContext.current).asBitmap()
        .load(puppy.puppyImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
        shape = RoundedCornerShape(6.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
    ) {
        Column() {
            bitmap?.let {
                Image(
                    contentScale = ContentScale.Crop,
                    bitmap = it.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Divider(color = Color.Transparent, thickness = 2.dp)
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = puppy.puppyName.toUpperCase(Locale.ROOT), style = TextStyle(
                            fontSize = 24.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Divider(color = Color.Transparent, thickness = 5.dp)
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = puppy.age.toLowerCase(Locale.ROOT), style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Image(
                            painter = painterResource(R.drawable.ic_male_and_female),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.height(20.dp),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}



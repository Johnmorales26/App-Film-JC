package com.johndev.filmsjc

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cursosandroidant.filmsjcref.dataAccess.FakeDatabase.getAllFilms
import com.johndev.filmsjc.ui.theme.FilmsJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmsJCTheme {
                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //ListBasic(getAllFilms())
                    ListAdvance(getAllFilms())
                }*/
                Scaffold(
                    bottomBar = {
                        BottomAppBarList()
                    },
                    content = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = it.calculateBottomPadding())
                        ) {
                            ListAdvance(getAllFilms())
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomAppBarList() {
    BottomAppBar {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Cart"
            )
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Options"
            )
        }
    }
}

@Composable
fun ListAdvance(films: List<Film>) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(
                    id =
                    R.dimen.common_padding_default
                )
            )
    ) {
        items(films.size) {
            /*ItemListClick(
                film = films[it],
                modifier = Modifier
                    .clickable {
                        Toast.makeText(context, films[it].name, Toast.LENGTH_SHORT).show()
                    }
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )*/
            ItemListAdvance(
                films[it],
                modifier = Modifier
                    .clickable {
                        Toast.makeText(context, films[it].name, Toast.LENGTH_SHORT).show()
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ItemListAdvance(film: Film, modifier: Modifier) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            ListItem(
                text = {
                    Text(
                        text = film.name,
                        style = MaterialTheme.typography.h6
                    )
                },
                secondaryText = {
                    Text(
                        text = film.description,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                singleLineSecondaryText = false,
                icon = {
                    /*Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Cover Film"
                    )*/
                    GlideImage(
                        model = film.photoUrl,
                        contentDescription = "Cover Film",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(
                                dimensionResource(
                                    id = R.dimen.list_item_img_size
                                )
                            )
                            .clip(
                                RoundedCornerShape(
                                    dimensionResource(id = R.dimen.common_padding_default)
                                )
                            )
                    )
                },
                trailing = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(
                                top = dimensionResource(
                                    id = R.dimen.common_padding_default
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.list_item_trailing
                            ),
                            modifier = Modifier
                                .size(
                                    dimensionResource(
                                        id = R.dimen.list_item_trailing_size
                                    )
                                )
                        )
                        Text(
                            text = "${film.score}",
                            fontSize = 10.sp
                        )
                    }
                }
            )
        }
        //Divider()
    }
}


@Composable
fun ListBasic(films: List<Film>) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        films.forEach {
            ItemListBasic(it)
        }
    }
}

@Composable
fun ItemListBasic(film: Film) {
    Text(
        text = film.name,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.common_padding_default)),
        style = MaterialTheme.typography.h6
    )
}

@Composable
fun ItemListClick(film: Film, modifier: Modifier) {
    Text(
        text = film.name,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.common_padding_default)),
        style = MaterialTheme.typography.h6
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FilmsJCTheme {
        ListBasic(getAllFilms())
    }
}
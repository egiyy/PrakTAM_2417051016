package com.example.praktam_2417051016.fitur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktam_2417051016.model.CoffeeShop
import com.example.praktam_2417051016.model.Menu
import com.example.praktam_2417051016.ui.theme.*

@Composable
fun DashboardScreen() {

    var coffeeList by remember { mutableStateOf(Menu.coffeeList) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedNav by remember { mutableStateOf(1) }
    var selectedCoffee by remember { mutableStateOf<CoffeeShop?>(null) }

    val filteredList = if (searchQuery.isEmpty()) coffeeList
    else coffeeList.filter { it.name.contains(searchQuery, true) }

    if (selectedCoffee != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            DetailScreen(
                coffee = selectedCoffee!!,
                onBack = { selectedCoffee = null }
            )
        }
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavBar(selected = selectedNav, onSelect = { selectedNav = it })
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            item {
                HeaderBanner(
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "New in",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(coffeeList.take(4), key = { it.name }) { coffee ->
                        NewInCard(
                            coffee = coffee,
                            onAddClick = { selectedCoffee = coffee }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Frequently Ordered",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(filteredList, key = { it.name }) { coffee ->
                FrequentOrderItem(
                    coffee = coffee,
                    onAddClick = { selectedCoffee = coffee },
                    onItemClick = { selectedCoffee = coffee },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun HeaderBanner(searchQuery: String, onSearchChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.linearGradient(listOf(BannerStart, BannerEnd)),
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text("Good Morning", color = Color.White)
                Text("Coffee Lover ☕", color = Color.White)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    decorationBox = {
                        if (searchQuery.isEmpty()) Text("Search")
                        it()
                    }
                )
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }
    }
}

@Composable
fun NewInCard(coffee: CoffeeShop, onAddClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { onAddClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(coffee.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Text(coffee.name)
            Text(coffee.price)
        }
    }
}

@Composable
fun FrequentOrderItem(
    coffee: CoffeeShop,
    onAddClick: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onItemClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(coffee.imageRes),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(coffee.name)
                Text(coffee.price)
            }

            IconButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

@Composable
fun BottomNavBar(selected: Int, onSelect: (Int) -> Unit) {
    NavigationBar {
        val items = listOf(
            Icons.Default.ShoppingCart,
            Icons.Default.Home,
            Icons.Default.List,
            Icons.Default.Person
        )

        items.forEachIndexed { index, icon ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onSelect(index) },
                icon = { Icon(icon, contentDescription = null) }
            )
        }
    }
}
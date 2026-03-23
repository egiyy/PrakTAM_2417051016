package com.example.praktam_2417051016.fitur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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

@Composable
fun DashboardScreen(innerPadding: PaddingValues) {

    var coffeeList by remember { mutableStateOf(Menu.coffeeList) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedNav by remember { mutableStateOf(1) } // 0=Cart,1=Home,2=Menu,3=Profile

    val filteredList = if (searchQuery.isEmpty()) coffeeList
    else coffeeList.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Scaffold(
        containerColor = Color(0xFFF7F7F7),
        bottomBar = {
            BottomNavBar(selected = selectedNav, onSelect = { selectedNav = it })
        }
    ) { scaffoldPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
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
                    text = "New in",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(coffeeList.take(4)) { coffee ->
                        NewInCard(
                            coffee = coffee,
                            onAddClick = { /* TODO */ }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "frequently ordered",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(filteredList, key = { it.name }) { coffee ->
                FrequentOrderItem(
                    coffee = coffee,
                    onFavoriteClick = {
                        coffeeList = coffeeList.map {
                            if (it.name == coffee.name) it.copy(isFavorite = !it.isFavorite)
                            else it
                        }
                    },
                    onAddClick = { /* TODO */ },
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
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF2C4A6E), Color(0xFF4A7A9B))
                ),
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
    ) {

        Box(
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.TopEnd)
                .offset(x = 20.dp, y = (-15).dp)
                .background(Color.White.copy(alpha = 0.08f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-15).dp, y = 40.dp)
                .background(Color.White.copy(alpha = 0.07f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Good Morning",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Reggy!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "What do you want to drink today?",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(50.dp))
                    .background(Color.White, RoundedCornerShape(50.dp))
                    .padding(horizontal = 18.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 14.sp, color = Color(0xFF1A1A1A)),
                    decorationBox = { inner ->
                        if (searchQuery.isEmpty()) Text("Search", fontSize = 14.sp, color = Color(0xFFAAAAAA))
                        inner()
                    }
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color(0xFF2C4A6E), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NewInCard(coffee: CoffeeShop, onAddClick: () -> Unit) {
    Card(
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = coffee.imageRes),
                contentDescription = coffee.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = coffee.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                maxLines = 2,
                lineHeight = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rp${coffee.price.replace("Rp", "").replace(".", "").replace(",000", "k").trim()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(Color(0xFFE53935), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FrequentOrderItem(
    coffee: CoffeeShop,
    onFavoriteClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F0EB)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = coffee.imageRes),
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .size(72.dp),
                    contentScale = ContentScale.Fit   // FIT = gambar utuh
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coffee.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                if (coffee.size.isNotEmpty()) {
                    Text(
                        text = coffee.size,
                        fontSize = 12.sp,
                        color = Color(0xFF9E9E9E)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = coffee.price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A8A1A)
                )
            }

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(Color(0xFFE53935), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onAddClick,
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(selected: Int, onSelect: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Pair(Icons.Filled.ShoppingCart, "Cart"),
            Pair(Icons.Filled.Home, "Home"),
            Pair(Icons.Filled.List, "Menu"),
            Pair(Icons.Filled.Person, "Profile")
        )
        items.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onSelect(index) },
                icon = {
                    Icon(imageVector = icon, contentDescription = label)
                },
                label = { Text(label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2C4A6E),
                    selectedTextColor = Color(0xFF2C4A6E),
                    indicatorColor = Color(0xFFE8F0F7),
                    unselectedIconColor = Color(0xFFAAAAAA),
                    unselectedTextColor = Color(0xFFAAAAAA)
                )
            )
        }
    }
}
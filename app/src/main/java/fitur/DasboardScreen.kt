package com.example.praktam_2417051016.fitur

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.praktam_2417051016.data.model.CoffeeShop
import com.example.praktam_2417051016.data.repository.CoffeeRepository
import com.example.praktam_2417051016.ui.theme.BannerEnd
import com.example.praktam_2417051016.ui.theme.BannerStart
import com.example.praktam_2417051016.ui.theme.CoffeeAccent
import com.example.praktam_2417051016.ui.theme.CoffeeCardBg
import com.example.praktam_2417051016.ui.theme.CoffeePriceGreen
import com.example.praktam_2417051016.ui.theme.CoffeeSubtleText

@Composable
fun DashboardScreen(innerPadding: PaddingValues) {

    // ── Repository Pattern: UI tidak panggil Retrofit langsung ────
    val repository = remember { CoffeeRepository() }

    var coffeeList by remember { mutableStateOf<List<CoffeeShop>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedNav by remember { mutableStateOf(1) }
    var selectedCoffee by remember { mutableStateOf<CoffeeShop?>(null) }

    // Fetch data lewat Repository (bukan langsung RetrofitClient)
    LaunchedEffect(Unit) {
        isLoading = true
        val result = repository.getCoffees()
        if (result.isEmpty()) {
            isError = true
        } else {
            coffeeList = result
            isError = false
        }
        isLoading = false
    }

    val filteredList = if (searchQuery.isEmpty()) coffeeList
    else coffeeList.filter { it.name.contains(searchQuery, ignoreCase = true) }

    // Navigasi ke DetailScreen
    if (selectedCoffee != null) {
        DetailScreen(
            coffee = selectedCoffee!!,
            onBack = { selectedCoffee = null }
        )
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavBar(selected = selectedNav, onSelect = { selectedNav = it })
        }
    ) { scaffoldPadding ->

        // ── Loading State ─────────────────────────────────────────
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(scaffoldPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            return@Scaffold
        }

        // ── Error State ───────────────────────────────────────────
        if (isError || coffeeList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Gagal Memuat Data",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Pastikan koneksi internet Anda menyala",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            return@Scaffold
        }

        // ── Success State: Tampilan Utama ─────────────────────────
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
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
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
                            onAddClick = { selectedCoffee = coffee }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "frequently ordered",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
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
                    onAddClick = { selectedCoffee = coffee },
                    onItemClick = { selectedCoffee = coffee },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }
        }
    }
}

// ── HEADER BANNER ──────────────────────────────────────────────────
@Composable
fun HeaderBanner(searchQuery: String, onSearchChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.linearGradient(colors = listOf(BannerStart, BannerEnd)),
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
    ) {
        Box(modifier = Modifier.size(110.dp).align(Alignment.TopEnd).offset(x = 20.dp, y = (-15).dp).background(Color.White.copy(alpha = 0.08f), CircleShape))
        Box(modifier = Modifier.size(75.dp).align(Alignment.TopEnd).offset(x = (-15).dp, y = 40.dp).background(Color.White.copy(alpha = 0.07f), CircleShape))

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Good Morning", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Text(text = "Coffee Lover! ☕", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "What do you want to drink today?", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.8f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50.dp))
                    .padding(horizontal = 18.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface),
                    decorationBox = { inner ->
                        if (searchQuery.isEmpty()) Text("Search", style = MaterialTheme.typography.bodyMedium, color = CoffeeSubtleText)
                        inner()
                    }
                )
                Box(modifier = Modifier.size(30.dp).background(BannerStart, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

// ── NEW IN CARD — AsyncImage (Coil) ───────────────────────────────
@Composable
fun NewInCard(coffee: CoffeeShop, onAddClick: () -> Unit) {
    Card(
        modifier = Modifier.width(120.dp).clickable { onAddClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = coffee.imageUrl,
                contentDescription = coffee.name,
                placeholder = null,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = coffee.name, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface, maxLines = 2, lineHeight = 15.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = coffee.price, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Box(modifier = Modifier.size(26.dp).background(CoffeeAccent, CircleShape), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

// ── FREQUENT ORDER ITEM — AsyncImage (Coil) ───────────────────────
@Composable
fun FrequentOrderItem(
    coffee: CoffeeShop,
    onFavoriteClick: () -> Unit,
    onAddClick: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { onItemClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)).background(CoffeeCardBg),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = coffee.imageUrl,
                    contentDescription = coffee.name,
                    modifier = Modifier.size(72.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = coffee.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                if (coffee.size.isNotEmpty()) Text(text = coffee.size, style = MaterialTheme.typography.bodySmall, color = CoffeeSubtleText)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = coffee.price, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = CoffeePriceGreen)
            }
            Box(modifier = Modifier.size(38.dp).background(CoffeeAccent, CircleShape), contentAlignment = Alignment.Center) {
                IconButton(onClick = onAddClick, modifier = Modifier.size(38.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        }
    }
}

// ── BOTTOM NAV ─────────────────────────────────────────────────────
@Composable
fun BottomNavBar(selected: Int, onSelect: (Int) -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 8.dp) {
        val items = listOf(Pair(Icons.Filled.ShoppingCart, "Cart"), Pair(Icons.Filled.Home, "Home"), Pair(Icons.Filled.List, "Menu"), Pair(Icons.Filled.Person, "Profile"))
        items.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onSelect(index) },
                icon = { Icon(imageVector = icon, contentDescription = label) },
                label = { Text(label, style = MaterialTheme.typography.bodySmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    unselectedIconColor = CoffeeSubtleText,
                    unselectedTextColor = CoffeeSubtleText
                )
            )
        }
    }
}
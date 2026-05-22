package com.example.praktam_2417051016.fitur

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.praktam_2417051016.data.model.CoffeeShop
import com.example.praktam_2417051016.ui.theme.CoffeeAccent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    coffee: CoffeeShop,
    onBack: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(coffee.isFavorite) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = { Text(text = coffee.name, style = MaterialTheme.typography.titleMedium) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Kembali")
                        }
                    },
                    actions = {
                        IconButton(onClick = { isFavorite = !isFavorite }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) CoffeeAccent else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // ── Gambar dari URL (Coil AsyncImage) ─────────────
                AsyncImage(
                    model = coffee.imageUrl,
                    contentDescription = coffee.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentScale = ContentScale.Crop
                )

                // ── Info Card ──────────────────────────────────────
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Text(
                            text = coffee.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        if (coffee.size.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = coffee.size, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = coffee.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Harga: ${coffee.price}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

                        Spacer(modifier = Modifier.height(20.dp))

                        // ── Tombol Order + Coroutine ───────────────
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    isLoading = true
                                    delay(2000)
                                    snackbarHostState.showSnackbar(
                                        "Pesanan ${coffee.name} berhasil diproses!"
                                    )
                                    isLoading = false
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoading,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            )
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary, strokeWidth = 2.dp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Memproses...", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
                            } else {
                                Text(text = "Order Sekarang", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = onBack,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            enabled = !isLoading,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(text = "Kembali", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }

        // ── Snackbar ───────────────────────────────────────────────
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        )
    }
}
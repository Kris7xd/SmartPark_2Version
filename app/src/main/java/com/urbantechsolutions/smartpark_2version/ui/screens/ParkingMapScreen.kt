package com.urbantechsolutions.smartpark_2version.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkPrimary
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkSecondary
import androidx.compose.material3.TopAppBarDefaults

// Estado de parqueadero
enum class ParkingStatus {
    AVAILABLE,      // Verde - Disponible
    OCCUPIED,       // Rojo - Ocupado
    RESERVED        // Amarillo - Reservado
}

// Modelo de datos de parqueadero
data class ParkingSpot(
    val id: Int,
    val name: String,
    val address: String,
    val distance: Float,
    val status: ParkingStatus,
    val price: Double,
    val availability: Int,
    val total: Int,
    val location: Pair<Float, Float> = Pair(0f, 0f)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingMapScreen(
    onParkingSelected: (String, String) -> Unit = { _, _ -> },
    onReservationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    var selectedView by remember { mutableStateOf(ViewType.MAP) } // MAP o LIST
    val parkingSpots = remember { generateSampleParkingSpots() }
    var selectedParking by remember { mutableStateOf<ParkingSpot?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "SmartPark",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SmartParkPrimary,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onReservationsClick) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.3f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "📋",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                    IconButton(onClick = onProfileClick) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.3f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "U",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedView) {
                ViewType.MAP -> {
                    MapView(
                        parkingSpots = parkingSpots,
                        onParkingClick = { parking ->
                            onParkingSelected(parking.name, parking.address)
                        }
                    )
                }
                ViewType.LIST -> {
                    ParkingList(
                        parkingSpots = parkingSpots,
                        onParkingClick = { parking ->
                            onParkingSelected(parking.name, parking.address)
                        }
                    )
                }
            }

            // Toggle entre Map y List en la parte inferior
            FloatingActionButton(
                onClick = {
                    selectedView = if (selectedView == ViewType.MAP) ViewType.LIST else ViewType.MAP
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = SmartParkPrimary,
                contentColor = Color.White
            ) {
                Text(
                    text = if (selectedView == ViewType.MAP) "Lista" else "Mapa",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

enum class ViewType {
    MAP, LIST
}

@Composable
fun MapView(
    parkingSpots: List<ParkingSpot>,
    onParkingClick: (ParkingSpot) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        // Simulación de mapa de fondo
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // Dibujar calles
            drawRect(
                color = Color.Gray,
                topLeft = Offset(0f, size.height * 0.3f),
                size = Size(size.width, size.height * 0.1f)
            )
            drawRect(
                color = Color.Gray,
                topLeft = Offset(size.width * 0.2f, 0f),
                size = Size(size.width * 0.1f, size.height)
            )
            drawRect(
                color = Color.Gray,
                topLeft = Offset(size.width * 0.6f, 0f),
                size = Size(size.width * 0.1f, size.height)
            )
            drawRect(
                color = Color.Gray,
                topLeft = Offset(0f, size.height * 0.7f),
                size = Size(size.width, size.height * 0.1f)
            )
            
            // Dibujar edificios
            drawRect(
                color = Color(0xFFD3D3D3),
                topLeft = Offset(size.width * 0.1f, size.height * 0.1f),
                size = Size(size.width * 0.3f, size.height * 0.15f)
            )
            drawRect(
                color = Color(0xFFD3D3D3),
                topLeft = Offset(size.width * 0.5f, size.height * 0.05f),
                size = Size(size.width * 0.4f, size.height * 0.2f)
            )
            drawRect(
                color = Color(0xFFD3D3D3),
                topLeft = Offset(size.width * 0.75f, size.height * 0.25f),
                size = Size(size.width * 0.2f, size.height * 0.3f)
            )
            
            // Dibujar marcadores de parqueaderos
            parkingSpots.forEachIndexed { index, parking ->
                val x = size.width * 0.1f + (index % 4) * size.width * 0.2f
                val y = size.height * 0.5f + (index / 4) * size.height * 0.2f
                
                val color = when (parking.status) {
                    ParkingStatus.AVAILABLE -> Color(0xFF4CAF50)
                    ParkingStatus.OCCUPIED -> Color(0xFFF44336)
                    ParkingStatus.RESERVED -> Color(0xFFFFEB3B)
                }
                
                // Dibujar marcador
                drawCircle(
                    color = color,
                    radius = 30f,
                    center = Offset(x, y)
                )
                
                // Borde blanco para marcador
                drawCircle(
                    color = Color.White,
                    radius = 30f,
                    center = Offset(x, y),
                    style = Stroke(width = 4f)
                )
            }
        }
        
        // Leyenda de colores
        Card(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ParkingLegendItem("Disponible", Color(0xFF4CAF50))
                ParkingLegendItem("Ocupado", Color(0xFFF44336))
                ParkingLegendItem("Reservado", Color(0xFFFFEB3B))
            }
        }
        
        // Información de ubicación actual
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = SmartParkPrimary.copy(alpha = 0.95f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📍 Centro",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ParkingLegendItem(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, CircleShape)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}

@Composable
fun ParkingList(
    parkingSpots: List<ParkingSpot>,
    onParkingClick: (ParkingSpot) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(parkingSpots.size) { index ->
            ParkingCard(
                parking = parkingSpots[index],
                onClick = { onParkingClick(parkingSpots[index]) }
            )
        }
    }
}

@Composable
fun ParkingCard(
    parking: ParkingSpot,
    onClick: () -> Unit
) {
    val statusColor = when (parking.status) {
        ParkingStatus.AVAILABLE -> Color(0xFF4CAF50)
        ParkingStatus.OCCUPIED -> Color(0xFFF44336)
        ParkingStatus.RESERVED -> Color(0xFFFFEB3B)
    }
    
    val statusText = when (parking.status) {
        ParkingStatus.AVAILABLE -> "Disponible"
        ParkingStatus.OCCUPIED -> "Ocupado"
        ParkingStatus.RESERVED -> "Reservado"
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Indicador de estado
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(statusColor)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = parking.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = parking.address,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        color = statusColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = statusText,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusColor
                        )
                    }
                    
                    Text(
                        text = "🚶 ${String.format("%.1f", parking.distance)} km",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💵 $${String.format("%.2f", parking.price)}/hr",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SmartParkPrimary
                    )
                    
                    Text(
                        text = "📊 ${parking.availability}/${parking.total} espacios",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

fun generateSampleParkingSpots(): List<ParkingSpot> {
    return listOf(
        ParkingSpot(1, "Parqueadero Centro", "Calle 12 #3-45", 0.5f, ParkingStatus.AVAILABLE, 2000.0, 15, 20),
        ParkingSpot(2, "Estacionamiento Norte", "Avenida 68 #15-30", 1.2f, ParkingStatus.OCCUPIED, 2500.0, 0, 15),
        ParkingSpot(3, "Plaza del Sol", "Carrera 7 #32-10", 0.8f, ParkingStatus.RESERVED, 3000.0, 8, 12),
        ParkingSpot(4, "Parque Temático", "Diagonal 50 #24-40", 2.1f, ParkingStatus.AVAILABLE, 1800.0, 20, 25),
        ParkingSpot(5, "Mall Plaza", "Transversal 5 #10-20", 1.5f, ParkingStatus.OCCUPIED, 2200.0, 5, 30),
        ParkingSpot(6, "Supermercado Express", "Calle 25 #8-15", 0.9f, ParkingStatus.AVAILABLE, 2100.0, 12, 18),
        ParkingSpot(7, "Centro Médico", "Avenida 19 #100-50", 1.8f, ParkingStatus.RESERVED, 2800.0, 6, 10),
        ParkingSpot(8, "Universidad Nacional", "Ciudad Universitaria", 3.5f, ParkingStatus.AVAILABLE, 1500.0, 25, 40)
    )
}


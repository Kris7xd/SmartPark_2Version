package com.urbantechsolutions.smartpark_2version.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkPrimary
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkSecondary
import java.util.Calendar

// Estado de reserva
enum class ReservationStatus {
    PENDING,    // Pendiente
    CONFIRMED,  // Confirmada
    ACTIVE,     // Activa
    COMPLETED   // Completada
}

// Estado de pago
enum class PaymentStatus {
    PENDING,    // Pendiente
    PAID,       // Pagado
    CANCELLED   // Cancelado
}

// Modelo de datos de reserva
data class UserReservation(
    val id: Int,
    val parkingName: String,
    val parkingAddress: String,
    val date: String,
    val time: String,
    val duration: String,
    val totalPrice: Double,
    val reservationStatus: ReservationStatus,
    val paymentStatus: PaymentStatus,
    val paymentMethod: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReservationsScreen(
    onReservationClick: (UserReservation) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf(ReservationFilter.ALL) }
    val reservations = remember { generateSampleReservations() }
    
    val filteredReservations = remember(selectedFilter, reservations) {
        when (selectedFilter) {
            ReservationFilter.ALL -> reservations
            ReservationFilter.ACTIVE -> reservations.filter { 
                it.reservationStatus == ReservationStatus.ACTIVE || 
                it.reservationStatus == ReservationStatus.CONFIRMED 
            }
            ReservationFilter.PENDING -> reservations.filter { it.reservationStatus == ReservationStatus.PENDING }
            ReservationFilter.COMPLETED -> reservations.filter { it.reservationStatus == ReservationStatus.COMPLETED }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Mis Reservas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SmartParkPrimary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.3f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "←",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Filtros
            ReservationFilterBar(
                selectedFilter = selectedFilter,
                onFilterSelected = { selectedFilter = it }
            )
            
            // Lista de reservas
            if (filteredReservations.isEmpty()) {
                EmptyReservationsView()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredReservations) { reservation ->
                        ReservationCard(
                            reservation = reservation,
                            onClick = { onReservationClick(reservation) }
                        )
                    }
                }
            }
        }
    }
}

enum class ReservationFilter {
    ALL, ACTIVE, PENDING, COMPLETED
}

@Composable
fun ReservationFilterBar(
    selectedFilter: ReservationFilter,
    onFilterSelected: (ReservationFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            text = "Todas",
            isSelected = selectedFilter == ReservationFilter.ALL,
            onClick = { onFilterSelected(ReservationFilter.ALL) }
        )
        FilterChip(
            text = "Activas",
            isSelected = selectedFilter == ReservationFilter.ACTIVE,
            onClick = { onFilterSelected(ReservationFilter.ACTIVE) }
        )
        FilterChip(
            text = "Pendientes",
            isSelected = selectedFilter == ReservationFilter.PENDING,
            onClick = { onFilterSelected(ReservationFilter.PENDING) }
        )
        FilterChip(
            text = "Completadas",
            isSelected = selectedFilter == ReservationFilter.COMPLETED,
            onClick = { onFilterSelected(ReservationFilter.COMPLETED) }
        )
    }
}

@Composable
fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) SmartParkPrimary else Color.White,
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun ReservationCard(
    reservation: UserReservation,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header con status y precio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status de reserva
                ReservationStatusBadge(reservation.reservationStatus)
                
                // Precio
                Text(
                    text = "$${String.format("%.0f", reservation.totalPrice)}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SmartParkPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Nombre del parqueadero
            Text(
                text = reservation.parkingName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Dirección
            Text(
                text = reservation.parkingAddress,
                fontSize = 14.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Divider(color = Color.LightGray)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Información detallada
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ReservationDetailItem(
                        icon = "📅",
                        label = "Fecha",
                        value = reservation.date
                    )
                    ReservationDetailItem(
                        icon = "🕐",
                        label = "Hora",
                        value = reservation.time
                    )
                    ReservationDetailItem(
                        icon = "⏱️",
                        label = "Duración",
                        value = reservation.duration
                    )
                }
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    PaymentStatusBadge(reservation.paymentStatus)
                    Text(
                        text = reservation.paymentMethod,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun ReservationDetailItem(icon: String, label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 16.sp
        )
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ReservationStatusBadge(status: ReservationStatus) {
    val (text, color) = when (status) {
        ReservationStatus.PENDING -> "Pendiente" to Color(0xFFFFB74D)
        ReservationStatus.CONFIRMED -> "Confirmada" to Color(0xFF66BB6A)
        ReservationStatus.ACTIVE -> "Activa" to Color(0xFF42A5F5)
        ReservationStatus.COMPLETED -> "Completada" to Color(0xFF9E9E9E)
    }
    
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun PaymentStatusBadge(status: PaymentStatus) {
    val (text, color) = when (status) {
        PaymentStatus.PENDING -> "Pago Pendiente" to Color(0xFFFFB74D)
        PaymentStatus.PAID -> "Pagado" to Color(0xFF66BB6A)
        PaymentStatus.CANCELLED -> "Cancelado" to Color(0xFFE57373)
    }
    
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun EmptyReservationsView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = SmartParkPrimary.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "📋",
                        fontSize = 60.sp
                    )
                }
            }
            
            Text(
                text = "No tienes reservas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Text(
                text = "Aún no has realizado ninguna reserva",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

fun generateSampleReservations(): List<UserReservation> {
    return listOf(
        UserReservation(
            id = 1,
            parkingName = "Parqueadero Centro",
            parkingAddress = "Calle 12 #3-45",
            date = "15/11/2024",
            time = "08:00",
            duration = "4 horas",
            totalPrice = 8000.0,
            reservationStatus = ReservationStatus.ACTIVE,
            paymentStatus = PaymentStatus.PAID,
            paymentMethod = "Tarjeta"
        ),
        UserReservation(
            id = 2,
            parkingName = "Estacionamiento Norte",
            parkingAddress = "Avenida 68 #15-30",
            date = "16/11/2024",
            time = "14:00",
            duration = "Todo el día",
            totalPrice = 10000.0,
            reservationStatus = ReservationStatus.CONFIRMED,
            paymentStatus = PaymentStatus.PENDING,
            paymentMethod = "PSE"
        ),
        UserReservation(
            id = 3,
            parkingName = "Plaza del Sol",
            parkingAddress = "Carrera 7 #32-10",
            date = "12/11/2024",
            time = "09:00",
            duration = "2 horas",
            totalPrice = 4000.0,
            reservationStatus = ReservationStatus.COMPLETED,
            paymentStatus = PaymentStatus.PAID,
            paymentMethod = "Billetera Digital"
        ),
        UserReservation(
            id = 4,
            parkingName = "Supermercado Express",
            parkingAddress = "Calle 25 #8-15",
            date = "18/11/2024",
            time = "10:00",
            duration = "3 horas",
            totalPrice = 6000.0,
            reservationStatus = ReservationStatus.PENDING,
            paymentStatus = PaymentStatus.PENDING,
            paymentMethod = "Tarjeta"
        ),
        UserReservation(
            id = 5,
            parkingName = "Parque Temático",
            parkingAddress = "Diagonal 50 #24-40",
            date = "20/11/2024",
            time = "07:00",
            duration = "Todo el día",
            totalPrice = 10000.0,
            reservationStatus = ReservationStatus.CONFIRMED,
            paymentStatus = PaymentStatus.PAID,
            paymentMethod = "PSE"
        )
    )
}


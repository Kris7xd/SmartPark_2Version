package com.urbantechsolutions.smartpark_2version.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkPrimary
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkSecondary
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationScreen(
    parkingName: String,
    parkingAddress: String,
    onReservationConfirmed: () -> Unit
) {
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var selectedHour by remember { mutableStateOf<Int?>(null) }
    var selectedDuration by remember { mutableStateOf(DurationType.HOUR) }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    
    val basePrice = 2000.0 // Por hora
    val dayPrice = 10000.0 // Por día
    val totalPrice = remember(selectedDuration, selectedHour) {
        when (selectedDuration) {
            DurationType.HOUR -> basePrice * (selectedHour ?: 1)
            DurationType.DAY -> dayPrice
        }
    }
    
    val selectedDateString = remember(selectedDate) {
        selectedDate?.let {
            "${it.get(Calendar.DAY_OF_MONTH)}/${it.get(Calendar.MONTH) + 1}/${it.get(Calendar.YEAR)}"
        } ?: "Seleccionar fecha"
    }
    
    val selectedHourString = remember(selectedHour) {
        selectedHour?.let { "$it:00" } ?: "Seleccionar hora"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Reservar Espacio",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SmartParkPrimary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            SmartParkSecondary.copy(alpha = 0.1f)
                        )
                    )
                )
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Información del parqueadero
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = parkingName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SmartParkPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = parkingAddress,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            
            // Selector de fecha
            SectionTitle(title = "Fecha de Reserva")
            DateSelector(
                selectedDate = selectedDateString,
                onDateClick = { 
                    // En una app real, abrirías un DatePicker
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    selectedDate = calendar
                }
            )
            
            // Selector de hora
            SectionTitle(title = "Hora de Entrada")
            HourSelector(
                selectedHour = selectedHourString,
                onHourClick = { hour ->
                    selectedHour = hour
                }
            )
            
            // Tipo de reserva
            SectionTitle(title = "Duración")
            DurationSelector(
                selectedDuration = selectedDuration,
                onDurationChange = { selectedDuration = it }
            )
            
            // Resumen de costo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SmartParkPrimary.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "$${String.format("%.0f", totalPrice)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SmartParkPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total a pagar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "$${String.format("%.0f", totalPrice)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SmartParkPrimary
                        )
                    }
                }
            }
            
            // Métodos de pago
            SectionTitle(title = "Método de Pago")
            PaymentMethodSelector(
                selectedMethod = selectedPaymentMethod,
                onMethodSelect = { selectedPaymentMethod = it }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botón de reservar
            Button(
                onClick = {
                    // Validar que todo esté seleccionado
                    if (selectedDate != null && selectedHour != null && selectedPaymentMethod != null) {
                        onReservationConfirmed()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmartParkPrimary
                ),
                shape = RoundedCornerShape(30.dp),
                enabled = selectedDate != null && selectedHour != null && selectedPaymentMethod != null
            ) {
                Text(
                    text = "Confirmar Reserva",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
fun DateSelector(selectedDate: String, onDateClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onDateClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = SmartParkPrimary.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            "📅",
                            fontSize = 24.sp
                        )
                    }
                }
                Text(
                    text = selectedDate,
                    fontSize = 16.sp,
                    color = if (selectedDate == "Seleccionar fecha") Color.Gray else Color.Black,
                    fontWeight = if (selectedDate != "Seleccionar fecha") FontWeight.Bold else FontWeight.Normal
                )
            }
            IconButton(onClick = onDateClick) {
                Text(
                    "→",
                    fontSize = 24.sp,
                    color = SmartParkPrimary
                )
            }
        }
    }
}

@Composable
fun HourSelector(selectedHour: String, onHourClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        val hours = remember { (6..22).toList() }
        
        // Botón principal para mostrar hora seleccionada
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .clickable(onClick = {}),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        color = SmartParkPrimary.copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🕐", fontSize = 24.sp)
                        }
                    }
                    Text(
                        text = selectedHour,
                        fontSize = 16.sp,
                        color = if (selectedHour == "Seleccionar hora") Color.Gray else Color.Black,
                        fontWeight = if (selectedHour != "Seleccionar hora") FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
        
        // Grid de horas disponibles
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hours.size) { index ->
                val hour = hours[index]
                val isSelected = selectedHour == "$hour:00"
                
                Card(
                    modifier = Modifier
                        .width(70.dp)
                        .height(50.dp)
                        .clickable { onHourClick(hour) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) SmartParkPrimary else Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$hour:00",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}

enum class DurationType {
    HOUR, DAY
}

@Composable
fun DurationSelector(selectedDuration: DurationType, onDurationChange: (DurationType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DurationCard(
            title = "Por Hora",
            subtitle = "$2,000/hora",
            isSelected = selectedDuration == DurationType.HOUR,
            onClick = { onDurationChange(DurationType.HOUR) },
            modifier = Modifier.weight(1f)
        )
        DurationCard(
            title = "Todo el Día",
            subtitle = "$10,000/día",
            isSelected = selectedDuration == DurationType.DAY,
            onClick = { onDurationChange(DurationType.DAY) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DurationCard(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SmartParkPrimary else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = if (isSelected) Color.White.copy(alpha = 0.9f) else Color.Gray
            )
        }
    }
}

enum class PaymentMethod(val displayName: String, val icon: String) {
    CARD("Tarjeta", "💳"),
    PSE("PSE", "🏦"),
    WALLET("Billetera Digital", "📱")
}

@Composable
fun PaymentMethodSelector(selectedMethod: PaymentMethod?, onMethodSelect: (PaymentMethod) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PaymentMethod.values().forEach { method ->
            PaymentMethodCard(
                method = method,
                isSelected = selectedMethod == method,
                onClick = { onMethodSelect(method) }
            )
        }
    }
}

@Composable
fun PaymentMethodCard(method: PaymentMethod, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(3.dp, SmartParkPrimary)
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(45.dp),
                    shape = CircleShape,
                    color = SmartParkPrimary.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            method.icon,
                            fontSize = 24.sp
                        )
                    }
                }
                Text(
                    text = method.displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            
            if (isSelected) {
                Surface(
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    color = SmartParkPrimary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            "✓",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


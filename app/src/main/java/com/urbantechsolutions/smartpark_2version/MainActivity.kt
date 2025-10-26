package com.urbantechsolutions.smartpark_2version

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.urbantechsolutions.smartpark_2version.ui.screens.LoginScreen
import com.urbantechsolutions.smartpark_2version.ui.screens.MyReservationsScreen
import com.urbantechsolutions.smartpark_2version.ui.screens.ParkingMapScreen
import com.urbantechsolutions.smartpark_2version.ui.screens.ProfileScreen
import com.urbantechsolutions.smartpark_2version.ui.screens.RegisterScreen
import com.urbantechsolutions.smartpark_2version.ui.screens.ReservationScreen
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartPark_2VersionTheme
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkPrimary
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkSecondary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartPark_2VersionTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Start) }
    var reservationData by remember { mutableStateOf<ReservationData?>(null) }
    
    when (currentScreen) {
        is Screen.Start -> StartScreen(
            onLoginClick = { currentScreen = Screen.Login },
            onRegisterClick = { currentScreen = Screen.Register }
        )
        is Screen.Login -> LoginScreen(
            onLoginSuccess = { currentScreen = Screen.Map },
            onNavigateToRegister = { currentScreen = Screen.Register },
            onBack = { currentScreen = Screen.Start }
        )
        is Screen.Register -> RegisterScreen(
            onRegisterSuccess = { currentScreen = Screen.Map },
            onNavigateToLogin = { currentScreen = Screen.Login },
            onBack = { currentScreen = Screen.Start }
        )
        is Screen.Map -> ParkingMapScreen(
            onParkingSelected = { parkingName, parkingAddress ->
                reservationData = ReservationData(parkingName, parkingAddress)
                currentScreen = Screen.Reservation
            },
            onReservationsClick = {
                currentScreen = Screen.MyReservations
            },
            onProfileClick = {
                currentScreen = Screen.Profile
            }
        )
        is Screen.Reservation -> {
            reservationData?.let { data ->
                ReservationScreen(
                    parkingName = data.parkingName,
                    parkingAddress = data.parkingAddress,
                    onReservationConfirmed = {
                        currentScreen = Screen.Map
                        // Aquí podrías mostrar un mensaje de confirmación
                    }
                )
            }
        }
        is Screen.MyReservations -> MyReservationsScreen(
            onReservationClick = { reservation ->
                // Aquí podrías navegar a detalles de la reserva
            },
            onBackClick = {
                currentScreen = Screen.Map
            }
        )
        is Screen.Profile -> ProfileScreen(
            onLogout = {
                currentScreen = Screen.Start
            },
            onBackClick = {
                currentScreen = Screen.Map
            }
        )
    }
}

sealed class Screen {
    object Start : Screen()
    object Login : Screen()
    object Register : Screen()
    object Map : Screen()
    object Reservation : Screen()
    object MyReservations : Screen()
    object Profile : Screen()
}

data class ReservationData(
    val parkingName: String,
    val parkingAddress: String
)

@Composable
fun StartScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SmartParkSecondary.copy(alpha = 0.3f),
                        SmartParkPrimary.copy(alpha = 0.7f),
                        SmartParkSecondary.copy(alpha = 0.4f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Logo con la letra P estilizado
            SmartParkLogo()
            
            // Nombre de la app
            Text(
                text = "SmartPark",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )
            
            Text(
                text = "Encuentra tu lugar perfecto",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Botón de Registrarse
            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(8.dp, RoundedCornerShape(30.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SmartParkPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botón de Iniciar sesión
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(8.dp, RoundedCornerShape(30.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SmartParkPrimary
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SmartParkLogo() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        Color.White.copy(alpha = 0.9f)
                    )
                )
            )
            .shadow(20.dp, RoundedCornerShape(40.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            SmartParkSecondary,
                            SmartParkPrimary
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "P",
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    SmartPark_2VersionTheme {
        StartScreen(onLoginClick = {}, onRegisterClick = {})
    }
}
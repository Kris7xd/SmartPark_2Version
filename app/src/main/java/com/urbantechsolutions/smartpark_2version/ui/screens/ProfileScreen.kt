package com.urbantechsolutions.smartpark_2version.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkPrimary
import com.urbantechsolutions.smartpark_2version.ui.theme.SmartParkSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var userName by remember { mutableStateOf("Juan Pérez") }
    var userEmail by remember { mutableStateOf("juan.perez@email.com") }
    var userVehicle by remember { mutableStateOf("Carro") }
    var userPhone by remember { mutableStateOf("+57 300 123 4567") }
    
    var isEditing by remember { mutableStateOf(false) }
    var showChangePassword by remember { mutableStateOf(false) }
    
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Mi Perfil",
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
                },
                actions = {
                    if (!isEditing) {
                        TextButton(onClick = { isEditing = true }) {
                            Text(
                                "Editar",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Foto de perfil y nombre
            ProfileHeader(
                userName = userName,
                userEmail = userEmail,
                isEditing = isEditing,
                onNameChange = { userName = it }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Información del usuario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProfileSectionTitle("Información Personal")
                    
                    ProfileField(
                        label = "Nombre completo",
                        value = userName,
                        icon = "👤",
                        isEditable = isEditing,
                        onValueChange = { userName = it }
                    )
                    
                    Divider()
                    
                    ProfileField(
                        label = "Email",
                        value = userEmail,
                        icon = "📧",
                        isEditable = false
                    )
                    
                    Divider()
                    
                    ProfileField(
                        label = "Teléfono",
                        value = userPhone,
                        icon = "📱",
                        isEditable = isEditing,
                        onValueChange = { userPhone = it },
                        keyboardType = KeyboardType.Phone
                    )
                }
            }
            
            // Tipo de vehículo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProfileSectionTitle("Vehiculo")
                    
                    VehicleSelector(
                        selectedVehicle = userVehicle,
                        onVehicleChange = { userVehicle = it },
                        isEditable = isEditing
                    )
                }
            }
            
            // Cambiar contraseña
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileSectionTitle("Contraseña")
                    
                    OutlinedButton(
                        onClick = { showChangePassword = !showChangePassword },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SmartParkPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SmartParkPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Cambiar contraseña",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    if (showChangePassword) {
                        OutlinedTextField(
                            value = currentPassword,
                            onValueChange = { currentPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Contraseña actual") },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SmartParkPrimary,
                                focusedLabelColor = SmartParkPrimary
                            )
                        )
                        
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Nueva contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SmartParkPrimary,
                                focusedLabelColor = SmartParkPrimary
                            )
                        )
                        
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Confirmar nueva contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SmartParkPrimary,
                                focusedLabelColor = SmartParkPrimary
                            )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botones de acción
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isEditing) {
                    Button(
                        onClick = { 
                            isEditing = false
                            // Aquí guardarías los cambios
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SmartParkPrimary
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Guardar cambios",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    
                    OutlinedButton(
                        onClick = { isEditing = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        ),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color.LightGray),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                // Botón de cerrar sesión
                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE57373)
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Cerrar Sesión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProfileHeader(
    userName: String,
    userEmail: String,
    isEditing: Boolean,
    onNameChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SmartParkPrimary,
                        SmartParkSecondary
                    )
                )
            )
            .padding(vertical = 32.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Foto de perfil
            Surface(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "👤",
                        fontSize = 70.sp
                    )
                }
            }
            
            // Nombre
            if (isEditing) {
                TextField(
                    value = userName,
                    onValueChange = onNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black
                    ),
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            } else {
                Text(
                    text = userName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Text(
                text = userEmail,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    icon: String,
    isEditable: Boolean = false,
    onValueChange: ((String) -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Column {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                if (isEditable && onValueChange != null) {
                    TextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.width(200.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = SmartParkPrimary
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                } else {
                    Text(
                        text = value,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun VehicleSelector(
    selectedVehicle: String,
    onVehicleChange: (String) -> Unit,
    isEditable: Boolean
) {
    val vehicles = mapOf(
        "Carro" to "🚗",
        "Moto" to "🏍️",
        "Camión" to "🚚",
        "Bicicleta" to "🚲"
    )
    
    if (isEditable) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Selecciona el tipo de vehículo que usas:",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                vehicles.forEach { (vehicle, icon) ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onVehicleChange(vehicle) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(70.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = if (selectedVehicle == vehicle) 
                                SmartParkPrimary else Color.LightGray.copy(alpha = 0.3f),
                            border = if (selectedVehicle == vehicle) 
                                androidx.compose.foundation.BorderStroke(3.dp, SmartParkPrimary)
                            else
                                androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                            shadowElevation = if (selectedVehicle == vehicle) 8.dp else 2.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        icon,
                                        fontSize = 32.sp
                                    )
                                }
                            }
                        }
                        Text(
                            text = vehicle,
                            fontSize = 12.sp,
                            fontWeight = if (selectedVehicle == vehicle) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedVehicle == vehicle) SmartParkPrimary else Color.Gray
                        )
                    }
                }
            }
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                vehicles[selectedVehicle] ?: "🚗",
                fontSize = 32.sp
            )
            Column {
                Text(
                    text = "Tipo de vehículo",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = selectedVehicle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

